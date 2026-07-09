package com.tonic.j2cs.emit;

import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.parser.ClassFile;
import com.tonic.parser.FieldEntry;
import com.tonic.type.AccessFlags;

/**
 * Synthesizes enum values()/valueOf() bodies from the enum's constant fields, replacing the
 * bytecode's $VALUES.clone() implementation, which routes an array through an Object slot.
 */
final class EnumSynthesis {

    private EnumSynthesis() {
    }

    /** Emits the body when the method is an enum synthetic; returns false to emit normally. */
    static boolean emit(CsWriter w, ClassFile classFile, String name, String desc, MemberNamer namer) {
        if (!"java/lang/Enum".equals(classFile.getSuperClassName())) {
            return false;
        }
        String self = classFile.getClassName();
        String selfFqcn = CsNamer.fqcn(self);
        if (name.equals("values") && desc.equals("()[L" + self + ";")) {
            w.line("return new " + selfFqcn + "[] { " + constants(classFile, namer) + " };");
            return true;
        }
        if (name.equals("valueOf") && desc.equals("(Ljava/lang/String;)L" + self + ";")) {
            w.line(selfFqcn + "[] __vals = new " + selfFqcn + "[] { " + constants(classFile, namer) + " };");
            w.open("for (int __i = 0; __i < __vals.Length; __i++)");
            w.open("if (p0 != null && __vals[__i].name().Value == p0.Value)");
            w.line("return __vals[__i];");
            w.close();
            w.close();
            w.line("if (p0 == null) throw global::java.lang.JRuntime.NullPointer("
                    + CsStrings.quote("Name is null") + ");");
            String canonical = self.replace('/', '.').replace('$', '.');
            w.line("throw global::java.lang.JRuntime.IllegalArgument("
                    + CsStrings.quote("No enum constant " + canonical + ".") + " + p0.Value);");
            return true;
        }
        return false;
    }

    private static String constants(ClassFile classFile, MemberNamer namer) {
        String constantDesc = "L" + classFile.getClassName() + ";";
        StringBuilder sb = new StringBuilder();
        for (FieldEntry field : classFile.getFields()) {
            if ((field.getAccess() & AccessFlags.ACC_STATIC) == 0 || !field.getDesc().equals(constantDesc)) {
                continue;
            }
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(namer.fieldName(field));
        }
        return sb.toString();
    }
}
