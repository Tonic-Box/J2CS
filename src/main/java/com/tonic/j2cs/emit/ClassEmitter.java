package com.tonic.j2cs.emit;

import com.tonic.j2cs.model.MethodPlan;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.report.TranspileReport;
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.j2cs.types.CsType;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.FieldEntry;
import com.tonic.parser.MethodEntry;
import com.tonic.type.AccessFlags;
import com.tonic.util.Modifiers;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Emits one generated class: base clause from the closure hierarchy, field declarations, the
 * RawNew marker constructor, __init methods for each Java constructor, a static constructor for
 * clinit, and one member per method whose body comes from its MethodPlan. Classes whose
 * supertype situation cannot be represented degrade to stub bodies with a report reason.
 */
public final class ClassEmitter {

    private static final String OBJECT_INTERNAL = "java/lang/Object";

    private record ClassPolicy(String baseFqcn, String unsupportedReason) {
    }

    private final NamingContext naming;
    private final TranspileReport report;
    private final MethodBodyEmitter bodyEmitter;
    private final Set<String> interfacePositionStubs;

    public ClassEmitter(NamingContext naming, TranspileReport report, Set<String> interfacePositionStubs) {
        this.naming = naming;
        this.report = report;
        this.bodyEmitter = new MethodBodyEmitter(naming);
        this.interfacePositionStubs = interfacePositionStubs;
    }

    public String emit(ClassFile classFile, Map<MethodEntry, MethodPlan> plans) {
        String internalName = classFile.getClassName();
        String csClassName = CsNamer.classNameOf(internalName);
        MemberNamer namer = naming.namerOf(internalName);
        TypeMapper types = naming.typeMapper();
        boolean isInterface = Modifiers.isInterface(classFile.getAccess());
        ClassPolicy policy = classPolicy(classFile);

        CsWriter w = new CsWriter();
        w.open("namespace " + CsNamer.namespaceOf(internalName));
        if (isInterface) {
            w.open("internal interface " + csClassName + heritageOf(classFile, null));
            for (FieldEntry field : classFile.getFields()) {
                CsType type = types.storageType(field.getDesc());
                w.line("public static " + type.csText() + " " + namer.fieldName(field) + ";");
            }
        } else {
            String abstractPrefix = Modifiers.isAbstract(classFile.getAccess()) ? "abstract " : "";
            String heritage = policy.unsupportedReason() != null
                    ? " : " + policy.baseFqcn()
                    : heritageOf(classFile, policy.baseFqcn());
            w.open("internal " + abstractPrefix + "class " + csClassName + heritage);
            for (FieldEntry field : classFile.getFields()) {
                CsType type = types.storageType(field.getDesc());
                boolean isStatic = (field.getAccess() & AccessFlags.ACC_STATIC) != 0;
                w.line("internal " + (isStatic ? "static " : "") + type.csText() + " "
                        + namer.fieldName(field) + ";");
            }
            w.line();
            w.open("public " + csClassName + "(global::java.lang.RawNew r) : base(r)");
            if (extendsShimThrowable(internalName)) {
                w.line("JavaClassName = " + CsStrings.quote(internalName.replace('/', '.')) + ";");
            }
            w.close();
        }
        for (MethodEntry method : classFile.getMethods()) {
            MethodPlan plan = plans.get(method);
            if (policy.unsupportedReason() != null) {
                plan = new MethodPlan.Unsupported(policy.unsupportedReason());
            }
            if (plan == null) {
                plan = new MethodPlan.Unsupported("no emission plan");
            }
            w.line();
            emitMethod(w, classFile, csClassName, method, plan, namer, types, isInterface);
        }
        w.close();
        w.close();
        return w.toString();
    }

    private String heritageOf(ClassFile classFile, String baseFqcn) {
        StringBuilder sb = new StringBuilder();
        if (baseFqcn != null) {
            sb.append(baseFqcn);
        }
        for (String iface : classFile.getInterfaceNames()) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(CsNamer.fqcn(iface));
        }
        return sb.isEmpty() ? "" : " : " + sb;
    }

    private ClassPolicy classPolicy(ClassFile classFile) {
        String internalName = classFile.getClassName();
        String reason = naming.classUnsupportedReason(internalName);
        if (Modifiers.isInterface(classFile.getAccess())) {
            return new ClassPolicy("", reason);
        }
        String superName = classFile.getSuperClassName();
        if (superName.equals(OBJECT_INTERNAL)) {
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

    private void emitMethod(CsWriter w, ClassFile classFile, String csClassName, MethodEntry method,
                            MethodPlan plan, MemberNamer namer, TypeMapper types, boolean isInterface) {
        String name = method.getName();
        String desc = method.getDesc();
        String header;
        if (name.equals("<clinit>")) {
            header = "static " + csClassName + "()";
        } else if (name.equals("<init>")) {
            header = "internal void " + MemberNamer.initMethodName(desc) + "(" + paramList(desc, types) + ")";
        } else {
            String prefix = isInterface
                    ? interfacePrefix(method)
                    : MethodModifiers.prefixFor(naming, classFile.getClassName(), method);
            CsType returnType = types.returnType(desc);
            if (Modifiers.isAbstract(method.getAccess())) {
                w.line(prefix + returnType.csText() + " " + namer.methodName(method)
                        + "(" + paramList(desc, types) + ");");
                return;
            }
            header = prefix + returnType.csText() + " " + namer.methodName(method)
                    + "(" + paramList(desc, types) + ")";
        }
        w.open(header);
        if (plan instanceof MethodPlan.Unsupported unsupported) {
            emitStubBody(w, classFile, name, desc, unsupported.reason());
        } else if (plan instanceof MethodPlan.Supported supported) {
            String body;
            try {
                body = bodyEmitter.emit(classFile.getClassName(), method, supported.method(), 3);
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

    private boolean extendsShimThrowable(String internalName) {
        String external = naming.hierarchy().firstExternalSuper(internalName);
        return external != null && ShimRegistry.isExtendable(external);
    }

    private static String interfacePrefix(MethodEntry method) {
        if (Modifiers.isStatic(method.getAccess())) {
            return "static ";
        }
        if (Modifiers.isPrivate(method.getAccess())) {
            return "private ";
        }
        return "";
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
