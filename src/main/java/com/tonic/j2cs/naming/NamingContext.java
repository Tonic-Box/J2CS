package com.tonic.j2cs.naming;

import com.tonic.j2cs.pipeline.ClassHierarchy;
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.j2cs.shims.ShimTarget;
import com.tonic.j2cs.types.CsType;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;
import com.tonic.util.Modifiers;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Per-run naming state: one MemberNamer per application class, built supertypes-first so
 * overrides adopt their ancestors' C# names, plus JVMS-style method and field resolution over
 * the closure. Classes whose supertype naming cannot be represented in C# get a class-level
 * unsupported reason instead of failing the run.
 */
public final class NamingContext {

    private static final String OBJECT_INTERNAL = "java/lang/Object";

    private final TypeMapper typeMapper;
    private final ClassHierarchy hierarchy;
    private final Map<String, MemberNamer> namers = new HashMap<>();
    private final Map<String, ClassFile> appClassFiles = new HashMap<>();
    private final Map<String, Set<String>> uniqueCtorDescs = new HashMap<>();
    private final Map<String, String> classUnsupportedReasons = new LinkedHashMap<>();
    private Set<String> bootstrapped = Set.of();
    private Set<String> suppressedMethodKeys = Set.of();

    public NamingContext(TypeMapper typeMapper, List<ClassFile> appClasses) {
        this(typeMapper, appClasses, new ClassHierarchy(appClasses));
    }

    public NamingContext(TypeMapper typeMapper, List<ClassFile> appClasses, ClassHierarchy hierarchy) {
        this.typeMapper = typeMapper;
        this.hierarchy = hierarchy;
        Map<String, ClassFile> byName = new HashMap<>();
        for (ClassFile cf : appClasses) {
            byName.put(cf.getClassName(), cf);
        }
        this.appClassFiles.putAll(byName);
        for (String name : hierarchy.topologicalOrder()) {
            ClassFile cf = byName.get(name);
            Map<String, String> inheritedAssignments = new LinkedHashMap<>();
            Set<String> inheritedTaken = new LinkedHashSet<>();
            String superName = hierarchy.superOf(name);
            if (superName != null && hierarchy.isAppClass(superName)) {
                mergeAncestor(name, namers.get(superName), inheritedAssignments, inheritedTaken);
            } else if (superName != null && ShimRegistry.isExtendable(superName)) {
                inheritedAssignments.putAll(ShimRegistry.EXTENDABLE_VIRTUALS);
                inheritedTaken.addAll(ShimRegistry.EXTENDABLE_MEMBER_NAMES);
            }
            for (String iface : hierarchy.interfacesOf(name)) {
                if (hierarchy.isAppClass(iface)) {
                    mergeAncestor(name, namers.get(iface), inheritedAssignments, inheritedTaken);
                } else if (ShimRegistry.isShimType(iface)) {
                    for (Map.Entry<String, String> shimVirtual
                            : ShimRegistry.instanceVirtualsOf(iface).entrySet()) {
                        inheritedAssignments.putIfAbsent(shimVirtual.getKey(), shimVirtual.getValue());
                        inheritedTaken.add(shimVirtual.getValue());
                    }
                }
            }
            MemberNamer namer = new MemberNamer(cf, typeMapper, inheritedAssignments, inheritedTaken);
            if (namer.hasClassNameConflict()) {
                classUnsupportedReasons.putIfAbsent(name,
                        "inherited member name collides with the class name");
            }
            namers.put(name, namer);
        }
    }

    private void mergeAncestor(String className, MemberNamer ancestor,
                               Map<String, String> assignments, Set<String> taken) {
        for (Map.Entry<String, String> entry : ancestor.overridableAssignments().entrySet()) {
            String previous = assignments.putIfAbsent(entry.getKey(), entry.getValue());
            if (previous != null && !previous.equals(entry.getValue())) {
                classUnsupportedReasons.putIfAbsent(className,
                        "interface method merging not supported: " + entry.getKey());
            }
        }
        taken.addAll(ancestor.allMemberNames());
    }

    public TypeMapper typeMapper() {
        return typeMapper;
    }

    public ClassHierarchy hierarchy() {
        return hierarchy;
    }

    public boolean isAppClass(String internalName) {
        return namers.containsKey(internalName);
    }

    public void setBootstrapped(Set<String> bootstrapped) {
        this.bootstrapped = Set.copyOf(bootstrapped);
    }

    public boolean isBootstrapped(String internalName) {
        return bootstrapped.contains(internalName);
    }

    public void setSuppressedMethods(Set<String> suppressedMethodKeys) {
        this.suppressedMethodKeys = Set.copyOf(suppressedMethodKeys);
    }

    public boolean isSuppressed(String ownerInternal, String name, String descriptor) {
        return suppressedMethodKeys.contains(ownerInternal + "." + name + descriptor);
    }

    public boolean isShimType(String internalName) {
        return ShimRegistry.isShimType(internalName);
    }

    public Optional<ShimTarget> shimMethod(String owner, String name, String desc) {
        return ShimRegistry.method(owner, name, desc);
    }

    public Optional<ShimTarget> shimField(String owner, String name, String desc) {
        return ShimRegistry.field(owner, name, desc);
    }

    /** Resolves a java/javax member against the shim registry, walking shim supertypes. */
    public Resolved resolveShim(String owner, String name, String desc) {
        return ShimRegistry.resolveMethodWalking(owner, name, desc)
                .<Resolved>map(w -> new Resolved.ShimMethod(w.declaringInternal(), w.target()))
                .orElseGet(() -> new Resolved.Unresolved(
                        "shim member not implemented: " + owner + "." + name + desc));
    }

    public MemberNamer namerOf(String internalName) {
        MemberNamer namer = namers.get(internalName);
        if (namer == null) {
            throw new IllegalArgumentException("no namer for class " + internalName);
        }
        return namer;
    }

    public String classUnsupportedReason(String internalName) {
        return classUnsupportedReasons.get(internalName);
    }

    /**
     * True if the app class declares a constructor with this descriptor whose C# parameter
     * signature is unique among its constructors — the condition under which a real C#
     * constructor is emitted (descriptor erasure can otherwise collide two Java ctors).
     */
    public boolean hasRealConstructor(String internalName, String desc) {
        return uniqueCtorDescs.computeIfAbsent(internalName, this::computeUniqueCtorDescs).contains(desc);
    }

    private Set<String> computeUniqueCtorDescs(String internalName) {
        ClassFile cf = appClassFiles.get(internalName);
        if (cf == null || Modifiers.isInterface(cf.getAccess())) {
            return Set.of();
        }
        Map<String, Integer> signatureCounts = new HashMap<>();
        Map<String, String> descToSignature = new LinkedHashMap<>();
        for (MethodEntry method : cf.getMethods()) {
            if (!method.getName().equals("<init>")
                    || isSuppressed(internalName, method.getName(), method.getDesc())) {
                continue;
            }
            String signature = typeMapper.paramTypes(method.getDesc()).stream()
                    .map(CsType::csText)
                    .collect(Collectors.joining(","));
            signatureCounts.merge(signature, 1, Integer::sum);
            descToSignature.put(method.getDesc(), signature);
        }
        Set<String> unique = new LinkedHashSet<>();
        descToSignature.forEach((desc, signature) -> {
            if (signatureCounts.get(signature) == 1) {
                unique.add(desc);
            }
        });
        return unique;
    }

    /**
     * Resolves an instance method reference: class chain first (JVMS 5.4.3.3), then Object's
     * public methods via the shim, then superinterfaces. Interface refs (5.4.3.4) check the
     * interface hierarchy and Object.
     */
    public Resolved resolveVirtual(String refOwner, String name, String desc) {
        if (!hierarchy.isAppClass(refOwner)) {
            return new Resolved.Unresolved("owner not in input: " + refOwner);
        }
        if (hierarchy.isAppInterface(refOwner)) {
            Resolved viaInterface = findInInterface(refOwner, name, desc);
            if (viaInterface != null) {
                return viaInterface;
            }
            for (String iface : hierarchy.allSuperInterfaces(refOwner)) {
                Resolved found = findInInterface(iface, name, desc);
                if (found != null) {
                    return found;
                }
            }
            return objectShim(name, desc, "interface method not found in closure: "
                    + refOwner + "." + name + desc);
        }
        String current = refOwner;
        while (current != null && hierarchy.isAppClass(current)) {
            MemberNamer namer = namers.get(current);
            if (namer.declaresKey(name, desc)) {
                return new Resolved.AppMethod(current, namer.findMethodName(name, desc), false);
            }
            current = hierarchy.superOf(current);
        }
        if (current != null && !current.equals(OBJECT_INTERNAL)) {
            Optional<ShimRegistry.WalkResult> walked = ShimRegistry.resolveMethodWalking(current, name, desc);
            if (walked.isPresent()) {
                return new Resolved.ShimMethod(walked.get().declaringInternal(), walked.get().target());
            }
            return new Resolved.Unresolved("method resolves through non-input superclass "
                    + current + ": " + name + desc);
        }
        Optional<ShimTarget> objectMethod = ShimRegistry.method(OBJECT_INTERNAL, name, desc);
        if (objectMethod.isPresent()) {
            return new Resolved.ShimMethod(OBJECT_INTERNAL, objectMethod.get());
        }
        for (String iface : hierarchy.allSuperInterfaces(refOwner)) {
            Resolved found = findInInterface(iface, name, desc);
            if (found != null) {
                return found;
            }
        }
        return new Resolved.Unresolved("method not found in closure: " + refOwner + "." + name + desc);
    }

    private Resolved findInInterface(String iface, String name, String desc) {
        MemberNamer namer = namers.get(iface);
        if (namer != null && namer.declaresKey(name, desc)) {
            Integer access = namer.methodAccessOf(name, desc);
            boolean isStatic = access != null && Modifiers.isStatic(access);
            if (!isStatic) {
                return new Resolved.AppMethod(iface, namer.findMethodName(name, desc), true);
            }
        }
        return null;
    }

    private Resolved objectShim(String name, String desc, String failReason) {
        Optional<ShimTarget> target = ShimRegistry.method(OBJECT_INTERNAL, name, desc);
        return target.<Resolved>map(t -> new Resolved.ShimMethod(OBJECT_INTERNAL, t))
                .orElseGet(() -> new Resolved.Unresolved(failReason));
    }

    /**
     * Resolves a static method reference: the class chain for class owners (static methods are
     * inherited), the exact interface for interface owners (interface statics are not).
     */
    public Resolved resolveStatic(String refOwner, String name, String desc) {
        if (!hierarchy.isAppClass(refOwner)) {
            return new Resolved.Unresolved("owner not in input: " + refOwner);
        }
        if (hierarchy.isAppInterface(refOwner)) {
            MemberNamer namer = namers.get(refOwner);
            if (namer.declaresKey(name, desc)) {
                return new Resolved.AppMethod(refOwner, namer.findMethodName(name, desc), true);
            }
            return new Resolved.Unresolved("static interface method not found: "
                    + refOwner + "." + name + desc);
        }
        String current = refOwner;
        while (current != null && hierarchy.isAppClass(current)) {
            MemberNamer namer = namers.get(current);
            if (namer.declaresKey(name, desc)) {
                return new Resolved.AppMethod(current, namer.findMethodName(name, desc), false);
            }
            current = hierarchy.superOf(current);
        }
        return new Resolved.Unresolved("static method not found in closure: "
                + refOwner + "." + name + desc);
    }

    /**
     * Resolves a field reference per JVMS 5.4.3.2: the type itself, then its superinterfaces
     * recursively, then the superclass.
     */
    public ResolvedField resolveField(String refOwner, String name, String desc) {
        if (!hierarchy.isAppClass(refOwner)) {
            return new ResolvedField.Unresolved("field owner not in input: " + refOwner);
        }
        ResolvedField found = findField(refOwner, name, desc);
        return found != null ? found
                : new ResolvedField.Unresolved("field not found in closure: "
                        + refOwner + "." + name + " " + desc);
    }

    private ResolvedField findField(String type, String name, String desc) {
        if (!hierarchy.isAppClass(type)) {
            return null;
        }
        MemberNamer namer = namers.get(type);
        String csName = namer.findFieldName(name, desc);
        if (csName != null) {
            return new ResolvedField.AppField(type, csName);
        }
        for (String iface : hierarchy.interfacesOf(type)) {
            ResolvedField viaInterface = findField(iface, name, desc);
            if (viaInterface != null) {
                return viaInterface;
            }
        }
        String superName = hierarchy.superOf(type);
        return superName == null ? null : findField(superName, name, desc);
    }
}
