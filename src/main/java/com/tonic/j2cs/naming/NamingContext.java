package com.tonic.j2cs.naming;

import com.tonic.j2cs.pipeline.ClassHierarchy;
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.j2cs.shims.ShimTarget;
import com.tonic.j2cs.types.CsType;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;
import com.tonic.util.Modifiers;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
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
    private final Map<String, Map<String, Integer>> enumOrdinals = new HashMap<>();
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

    /** A super-interface method that a C# default method must explicitly implement to satisfy it. */
    public record InterfaceMethodDecl(String csType, String csMember) {
    }

    /**
     * Super-interface declarations that a concrete (default) method on the given interface overrides
     * in Java but only <em>hides</em> in C#. A Java default method silently satisfies the abstract
     * method it overrides in every super-interface; a C# default interface method does not, so a
     * class implementing this interface is left with an unimplemented member (CS0535). Emitting an
     * explicit implementation for each returned declaration restores the linkage. Both app and shim
     * super-interfaces are walked; only abstract (non-default) declarations are returned.
     */
    public List<InterfaceMethodDecl> overriddenInterfaceDecls(String ifaceInternal, String name, String desc) {
        Set<String> roots = new LinkedHashSet<>();
        Set<String> visited = new HashSet<>();
        for (String sup : hierarchy.interfacesOf(ifaceInternal)) {
            collectAbstractInterfaceDecls(sup, name, desc, roots, visited);
        }
        List<InterfaceMethodDecl> decls = new ArrayList<>();
        for (String root : roots) {
            decls.add(new InterfaceMethodDecl(CsNamer.fqcn(root), interfaceMemberName(root, name, desc)));
        }
        return decls;
    }

    private void collectAbstractInterfaceDecls(String type, String name, String desc,
                                               Set<String> roots, Set<String> visited) {
        if (!visited.add(type)) {
            return;
        }
        if (ShimRegistry.isShimType(type)) {
            ShimRegistry.resolveMethodWalking(type, name, desc).ifPresent(walk -> {
                if (!ShimRegistry.isDefaultInterfaceMethod(walk.declaringInternal(), name, desc)) {
                    roots.add(walk.declaringInternal());
                }
            });
            return;
        }
        ClassFile cf = appClassFiles.get(type);
        if (cf == null || !Modifiers.isInterface(cf.getAccess())) {
            return;
        }
        for (MethodEntry m : cf.getMethods()) {
            if (m.getName().equals(name) && m.getDesc().equals(desc)) {
                if (Modifiers.isAbstract(m.getAccess())) {
                    roots.add(type);
                }
                return;
            }
        }
        for (String sup : hierarchy.interfacesOf(type)) {
            collectAbstractInterfaceDecls(sup, name, desc, roots, visited);
        }
    }

    /** An interface method an abstract class must re-declare as abstract to satisfy C#. */
    public record AbstractMethodDecl(String csName, String desc) {
    }

    /**
     * Abstract interface methods that the given class implements but neither it nor any ancestor
     * provides a body for. Java lets an abstract class inherit the obligation silently; C# requires
     * the class to re-declare each such member as {@code abstract}, or it reports CS0535. Interface
     * methods with a default, and those implemented by the class or an ancestor, are excluded.
     */
    public List<AbstractMethodDecl> unimplementedInterfaceMethods(String classInternal) {
        Map<String, MethodEntry> requiredMethod = new LinkedHashMap<>();
        Map<String, String> requiredIface = new LinkedHashMap<>();
        Set<String> provided = new HashSet<>();
        for (String iface : hierarchy.allSuperInterfaces(classInternal)) {
            ClassFile cf = appClassFiles.get(iface);
            if (cf == null) {
                continue;
            }
            for (MethodEntry m : cf.getMethods()) {
                String n = m.getName();
                if (n.equals("<clinit>") || Modifiers.isStatic(m.getAccess()) || Modifiers.isPrivate(m.getAccess())
                        || MemberNamer.isObjectOverride(n, m.getDesc())) {
                    continue;
                }
                String k = n + m.getDesc();
                if (Modifiers.isAbstract(m.getAccess())) {
                    if (requiredMethod.putIfAbsent(k, m) == null) {
                        requiredIface.put(k, iface);
                    }
                } else {
                    provided.add(k);
                }
            }
        }
        if (requiredMethod.isEmpty()) {
            return List.of();
        }
        addDeclaredMethods(classInternal, provided);
        for (String ancestor : hierarchy.classAncestors(classInternal)) {
            addDeclaredMethods(ancestor, provided);
        }
        String external = hierarchy.firstExternalSuper(classInternal);
        if (external != null && ShimRegistry.isExtendable(external)) {
            provided.addAll(ShimRegistry.EXTENDABLE_VIRTUALS.keySet());
        }
        List<AbstractMethodDecl> out = new ArrayList<>();
        for (Map.Entry<String, MethodEntry> e : requiredMethod.entrySet()) {
            MethodEntry m = e.getValue();
            if (provided.contains(e.getKey())
                    || ancestorImplementsInterfaceMethod(classInternal, m.getName(), m.getDesc())) {
                continue;
            }
            MemberNamer ifaceNamer = namers.get(requiredIface.get(e.getKey()));
            String csName = ifaceNamer == null ? null
                    : ifaceNamer.findMethodName(e.getValue().getName(), e.getValue().getDesc());
            if (csName != null) {
                out.add(new AbstractMethodDecl(csName, e.getValue().getDesc()));
            }
        }
        return out;
    }

    /**
     * Whether a strict class ancestor of the given class transitively implements an interface that
     * declares this instance method. When true, that ancestor owns the C# member slot for it, so an
     * implementation here overrides (rather than introduces) it and a re-abstraction is redundant.
     */
    public boolean ancestorImplementsInterfaceMethod(String classInternal, String name, String desc) {
        for (String ancestor : hierarchy.classAncestors(classInternal)) {
            if (typeImplementsInterfaceMethod(ancestor, name, desc)) {
                return true;
            }
        }
        return false;
    }

    private boolean typeImplementsInterfaceMethod(String classInternal, String name, String desc) {
        boolean hasAbstract = false;
        boolean hasDefault = false;
        for (String iface : hierarchy.allSuperInterfaces(classInternal)) {
            ClassFile cf = appClassFiles.get(iface);
            if (cf == null) {
                continue;
            }
            for (MethodEntry m : cf.getMethods()) {
                if (!m.getName().equals(name) || !m.getDesc().equals(desc)
                        || Modifiers.isStatic(m.getAccess()) || Modifiers.isPrivate(m.getAccess())) {
                    continue;
                }
                if (Modifiers.isAbstract(m.getAccess())) {
                    hasAbstract = true;
                } else {
                    hasDefault = true;
                }
            }
        }
        return hasAbstract && !hasDefault;
    }

    private void addDeclaredMethods(String classInternal, Set<String> provided) {
        ClassFile cf = appClassFiles.get(classInternal);
        if (cf == null) {
            return;
        }
        for (MethodEntry m : cf.getMethods()) {
            provided.add(m.getName() + m.getDesc());
        }
    }

    private String interfaceMemberName(String root, String name, String desc) {
        if (ShimRegistry.isShimType(root)) {
            return ShimRegistry.method(root, name, desc).map(ShimTarget::csMemberName).orElse(name);
        }
        MemberNamer namer = namers.get(root);
        String csName = namer == null ? null : namer.findMethodName(name, desc);
        return csName == null ? name : csName;
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
     * Ordinal of an app enum's constant: its index among the enum's own-typed static fields in
     * declaration order — the same order values() is synthesized from. -1 when the class is not
     * an app enum or the constant is unknown.
     */
    public int enumConstantOrdinal(String enumInternal, String constantName) {
        Map<String, Integer> ordinals = enumOrdinals.computeIfAbsent(enumInternal, this::computeEnumOrdinals);
        return ordinals.getOrDefault(constantName, -1);
    }

    private Map<String, Integer> computeEnumOrdinals(String enumInternal) {
        ClassFile cf = appClassFiles.get(enumInternal);
        if (cf == null || !"java/lang/Enum".equals(cf.getSuperClassName())) {
            return Map.of();
        }
        String constantDesc = "L" + enumInternal + ";";
        Map<String, Integer> ordinals = new LinkedHashMap<>();
        int ordinal = 0;
        for (com.tonic.parser.FieldEntry field : cf.getFields()) {
            if (Modifiers.isStatic(field.getAccess()) && constantDesc.equals(field.getDesc())) {
                ordinals.put(field.getName(), ordinal++);
            }
        }
        return ordinals;
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
            Resolved shimInherited = resolveThroughShimSuperInterfaces(refOwner, name, desc);
            if (shimInherited != null) {
                return shimInherited;
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

    /**
     * Resolves a method an app interface inherits from a shim super-interface (e.g. a functional
     * interface extending java.util.function.Function inherits its SAM). {@code allSuperInterfaces}
     * only spans closure-internal interfaces, so shim supers — reachable by name via interfacesOf —
     * are walked here. Returns null when no shim super-interface declares the method.
     */
    private Resolved resolveThroughShimSuperInterfaces(String appInterface, String name, String desc) {
        Set<String> visited = new HashSet<>();
        Deque<String> work = new ArrayDeque<>(hierarchy.interfacesOf(appInterface));
        while (!work.isEmpty()) {
            String iface = work.poll();
            if (!visited.add(iface)) {
                continue;
            }
            if (ShimRegistry.isShimType(iface)) {
                Optional<ShimRegistry.WalkResult> walked = ShimRegistry.resolveMethodWalking(iface, name, desc);
                if (walked.isPresent()) {
                    return new Resolved.ShimMethod(walked.get().declaringInternal(), walked.get().target());
                }
            } else if (appClassFiles.containsKey(iface)) {
                work.addAll(hierarchy.interfacesOf(iface));
            }
        }
        return null;
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
