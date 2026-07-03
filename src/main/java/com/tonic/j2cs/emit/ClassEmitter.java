package com.tonic.j2cs.emit;

import com.tonic.j2cs.model.MethodPlan;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.report.TranspileReport;
import com.tonic.j2cs.types.CsType;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.FieldEntry;
import com.tonic.parser.MethodEntry;
import com.tonic.type.AccessFlags;
import java.util.List;
import java.util.Map;

/**
 * Emits one generated class: field declarations, the RawNew marker constructor, __init methods
 * for each Java constructor, a static constructor for clinit, and one member per method whose
 * body comes from its MethodPlan.
 */
public final class ClassEmitter {

    private static final String OBJECT_INTERNAL = "java/lang/Object";

    private final NamingContext naming;
    private final TranspileReport report;
    private final MethodBodyEmitter bodyEmitter;

    public ClassEmitter(NamingContext naming, TranspileReport report) {
        this.naming = naming;
        this.report = report;
        this.bodyEmitter = new MethodBodyEmitter(naming);
    }

    public String emit(ClassFile classFile, Map<MethodEntry, MethodPlan> plans) {
        String internalName = classFile.getClassName();
        String csClassName = CsNamer.classNameOf(internalName);
        MemberNamer namer = naming.namerOf(internalName);
        TypeMapper types = naming.typeMapper();
        String classReason = unsupportedClassReason(classFile);

        CsWriter w = new CsWriter();
        w.open("namespace " + CsNamer.namespaceOf(internalName));
        w.open("internal class " + csClassName + " : global::java.lang.Object");
        for (FieldEntry field : classFile.getFields()) {
            CsType type = types.storageType(field.getDesc());
            boolean isStatic = (field.getAccess() & AccessFlags.ACC_STATIC) != 0;
            w.line("internal " + (isStatic ? "static " : "") + type.csText() + " " + namer.fieldName(field) + ";");
        }
        w.line();
        w.open("public " + csClassName + "(global::java.lang.RawNew r) : base(r)");
        w.close();
        for (MethodEntry method : classFile.getMethods()) {
            MethodPlan plan = plans.get(method);
            if (classReason != null) {
                plan = new MethodPlan.Unsupported(classReason);
            }
            if (plan == null) {
                plan = new MethodPlan.Unsupported("no emission plan");
            }
            w.line();
            emitMethod(w, classFile, csClassName, method, plan, namer, types);
        }
        w.close();
        w.close();
        return w.toString();
    }

    private String unsupportedClassReason(ClassFile classFile) {
        if ((classFile.getAccess() & AccessFlags.ACC_INTERFACE) != 0) {
            return "interfaces are not supported in M0";
        }
        String superName = classFile.getSuperClassName();
        if (superName != null && !superName.equals(OBJECT_INTERNAL)) {
            return "superclass other than java/lang/Object is not supported in M0: " + superName;
        }
        return null;
    }

    private void emitMethod(CsWriter w, ClassFile classFile, String csClassName, MethodEntry method,
                            MethodPlan plan, MemberNamer namer, TypeMapper types) {
        String name = method.getName();
        String desc = method.getDesc();
        String header;
        if (name.equals("<clinit>")) {
            header = "static " + csClassName + "()";
        } else if (name.equals("<init>")) {
            header = "internal void " + MemberNamer.initMethodName(desc) + "(" + paramList(desc, types) + ")";
        } else {
            boolean isStatic = (method.getAccess() & AccessFlags.ACC_STATIC) != 0;
            String prefix = MemberNamer.isObjectOverride(name, desc)
                    ? "public override "
                    : "internal " + (isStatic ? "static " : "");
            CsType returnType = types.returnType(desc);
            header = prefix + returnType.csText() + " " + namer.methodName(method) + "(" + paramList(desc, types) + ")";
        }
        w.open(header);
        if (plan instanceof MethodPlan.Unsupported unsupported) {
            emitStubBody(w, classFile, name, desc, unsupported.reason());
        } else if (plan instanceof MethodPlan.Supported supported) {
            String body;
            try {
                body = bodyEmitter.emit(method, supported.method(), 3);
            } catch (UnsupportedBodyException e) {
                body = null;
                emitStubBody(w, classFile, name, desc, e.getMessage());
            }
            if (body != null) {
                w.raw(body);
            }
        }
        w.close();
    }

    private void emitStubBody(CsWriter w, ClassFile classFile, String name, String desc, String reason) {
        String owner = classFile.getClassName();
        report.unsupportedMethod(owner, name, desc, reason);
        w.line("throw new global::System.NotSupportedException("
                + CsStrings.quote("j2cs: " + owner + "." + name + desc + ": " + reason) + ");");
    }

    private static String paramList(String methodDescriptor, TypeMapper types) {
        List<CsType> params = types.paramTypes(methodDescriptor);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(params.get(i).csText()).append(" p").append(i);
        }
        return sb.toString();
    }
}
