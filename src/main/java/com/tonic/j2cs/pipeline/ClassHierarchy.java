package com.tonic.j2cs.pipeline;

import com.tonic.j2cs.J2csException;
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.parser.ClassFile;
import com.tonic.util.Modifiers;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Supertype relations over the input classes. Edges leaving the closure (supertypes not in the
 * input) are recorded by name but not traversed. Topological order guarantees supertypes are
 * processed before subtypes, which the naming coordination relies on.
 */
public final class ClassHierarchy {

    private record Info(String superName, List<String> interfaces, boolean isInterface) {
    }

    private final Map<String, Info> infos = new LinkedHashMap<>();
    private final List<String> topological = new ArrayList<>();

    public ClassHierarchy(List<ClassFile> appClasses) {
        for (ClassFile cf : appClasses) {
            infos.put(cf.getClassName(), new Info(
                    cf.getSuperClassName(),
                    List.copyOf(cf.getInterfaceNames()),
                    Modifiers.isInterface(cf.getAccess())));
        }
        Set<String> visited = new HashSet<>();
        Set<String> onStack = new HashSet<>();
        for (String name : infos.keySet()) {
            visit(name, visited, onStack);
        }
    }

    private void visit(String name, Set<String> visited, Set<String> onStack) {
        if (!infos.containsKey(name) || visited.contains(name)) {
            return;
        }
        if (onStack.contains(name)) {
            throw new J2csException("class hierarchy cycle involving " + name);
        }
        onStack.add(name);
        Info info = infos.get(name);
        visit(info.superName(), visited, onStack);
        for (String iface : info.interfaces()) {
            visit(iface, visited, onStack);
        }
        onStack.remove(name);
        visited.add(name);
        topological.add(name);
    }

    public List<String> topologicalOrder() {
        return topological;
    }

    public boolean isAppClass(String internalName) {
        return infos.containsKey(internalName);
    }

    public boolean isAppInterface(String internalName) {
        Info info = infos.get(internalName);
        return info != null && info.isInterface();
    }

    public String superOf(String internalName) {
        Info info = infos.get(internalName);
        return info == null ? null : info.superName();
    }

    public List<String> interfacesOf(String internalName) {
        Info info = infos.get(internalName);
        return info == null ? List.of() : info.interfaces();
    }

    /**
     * Closure-internal superclass chain, nearest first, excluding the class itself. The first
     * supertype outside the input terminates the walk and is not included.
     */
    public List<String> classAncestors(String internalName) {
        List<String> ancestors = new ArrayList<>();
        String current = superOf(internalName);
        while (current != null && infos.containsKey(current)) {
            ancestors.add(current);
            current = superOf(current);
        }
        return ancestors;
    }

    /**
     * The first supertype outside the input when walking the superclass chain, or null when the
     * chain stays in the closure all the way to its last in-closure class.
     */
    public String firstExternalSuper(String internalName) {
        String current = superOf(internalName);
        while (current != null && infos.containsKey(current)) {
            current = superOf(current);
        }
        return current;
    }

    /**
     * Whether a receiver of the given static type exposes members declared by the target type,
     * so a call needs no upcast: true when the types match, the target is Object, the target is a
     * class ancestor or superinterface of the receiver type, or the target is a shim class ancestor
     * reached once the superclass chain leaves the input (an app subclass of a shim class inherits
     * its members in C# too). Shim interface relationships are not modeled, so an interface-declared
     * shim member conservatively keeps its cast.
     */
    public boolean staticallyHasMember(String receiverInternal, String declaringInternal) {
        if (receiverInternal.equals(declaringInternal) || declaringInternal.equals("java/lang/Object")) {
            return true;
        }
        if (classAncestors(receiverInternal).contains(declaringInternal)
                || allSuperInterfaces(receiverInternal).contains(declaringInternal)) {
            return true;
        }
        String shimRoot = ShimRegistry.isShimType(receiverInternal)
                ? receiverInternal
                : firstExternalSuper(receiverInternal);
        return shimRoot != null && ShimRegistry.isShimSubtype(shimRoot, declaringInternal);
    }

    /**
     * All closure-internal interfaces reachable from the type: its own, its class ancestors',
     * and transitively their superinterfaces.
     */
    public Set<String> allSuperInterfaces(String internalName) {
        Set<String> result = new LinkedHashSet<>();
        Deque<String> worklist = new ArrayDeque<>();
        worklist.add(internalName);
        worklist.addAll(classAncestors(internalName));
        while (!worklist.isEmpty()) {
            String current = worklist.poll();
            for (String iface : interfacesOf(current)) {
                if (infos.containsKey(iface) && result.add(iface)) {
                    worklist.add(iface);
                }
            }
        }
        return result;
    }
}
