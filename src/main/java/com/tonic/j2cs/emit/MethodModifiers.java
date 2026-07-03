package com.tonic.j2cs.emit;

import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.parser.MethodEntry;
import com.tonic.util.Modifiers;

/**
 * C# modifier prefix for an emitted class method. Readability is a non-goal, so the rule is
 * uniform: every non-private instance method is public virtual, becoming public override when
 * its (name, descriptor) is declared by a closure ancestor class or belongs to the Object trio.
 * Interface implementations stay virtual (C# implicit implementation requires public).
 */
public final class MethodModifiers {

    private MethodModifiers() {
    }

    public static String prefixFor(NamingContext naming, String classInternalName, MethodEntry method) {
        int access = method.getAccess();
        if (Modifiers.isStatic(access)) {
            return "internal static ";
        }
        if (Modifiers.isPrivate(access)) {
            return "internal ";
        }
        boolean isAbstract = Modifiers.isAbstract(access);
        boolean isOverride = MemberNamer.isObjectOverride(method.getName(), method.getDesc())
                || ancestorClassDeclares(naming, classInternalName, method.getName(), method.getDesc());
        if (isOverride) {
            return isAbstract ? "public abstract override " : "public override ";
        }
        return isAbstract ? "public abstract " : "public virtual ";
    }

    private static boolean ancestorClassDeclares(NamingContext naming, String classInternalName,
                                                 String name, String desc) {
        for (String ancestor : naming.hierarchy().classAncestors(classInternalName)) {
            MemberNamer namer = naming.namerOf(ancestor);
            if (namer.declaresKey(name, desc)) {
                Integer access = namer.methodAccessOf(name, desc);
                if (access != null && !Modifiers.isPrivate(access) && !Modifiers.isStatic(access)) {
                    return true;
                }
            }
        }
        return false;
    }
}
