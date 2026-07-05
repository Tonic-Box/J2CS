package com.tonic.j2cs.emit;

import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.parser.ClassFile;
import com.tonic.util.Modifiers;
import java.util.Set;

/**
 * Decides how a class's supertype situation maps to C#: the base clause to emit, or an
 * unsupported reason that degrades every method body to a stub while keeping the class shell.
 */
final class ClassPolicyResolver {

    record ClassPolicy(String baseFqcn, String unsupportedReason) {
    }

    private static final String OBJECT_INTERNAL = "java/lang/Object";

    private final NamingContext naming;
    private final Set<String> interfacePositionStubs;

    ClassPolicyResolver(NamingContext naming, Set<String> interfacePositionStubs) {
        this.naming = naming;
        this.interfacePositionStubs = interfacePositionStubs;
    }

    ClassPolicy resolve(ClassFile classFile) {
        String internalName = classFile.getClassName();
        String reason = naming.classUnsupportedReason(internalName);
        if (Modifiers.isInterface(classFile.getAccess())) {
            return new ClassPolicy("", reason);
        }
        if (internalName.equals(OBJECT_INTERNAL)) {
            return new ClassPolicy(null, reason);
        }
        String superName = classFile.getSuperClassName();
        if (superName == null || superName.equals(OBJECT_INTERNAL)) {
            return new ClassPolicy("global::java.lang.Object", reason);
        }
        if (naming.isAppClass(superName)) {
            return new ClassPolicy(CsNamer.fqcn(superName), reason);
        }
        if (ShimRegistry.isExtendable(superName)) {
            return new ClassPolicy(CsNamer.fqcn(superName), reason);
        }
        if (ShimRegistry.isShimType(superName) || interfacePositionStubs.contains(superName)) {
            return new ClassPolicy("global::java.lang.Object",
                    reason != null ? reason : "superclass is not an input class: " + superName);
        }
        return new ClassPolicy(CsNamer.fqcn(superName),
                reason != null ? reason : "superclass not in input: " + superName);
    }
}
