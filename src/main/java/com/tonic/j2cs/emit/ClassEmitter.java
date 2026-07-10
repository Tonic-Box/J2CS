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

    private final NamingContext naming;
    private final TranspileReport report;
    private final MethodBodyEmitter bodyEmitter;
    private final ClassPolicyResolver policyResolver;
    private final BodyOverride structured;

    public ClassEmitter(NamingContext naming, TranspileReport report, Set<String> interfacePositionStubs,
                        SyntheticClasses synthetics, BodyOverride structured) {
        this.naming = naming;
        this.report = report;
        this.bodyEmitter = new MethodBodyEmitter(naming, synthetics);
        this.policyResolver = new ClassPolicyResolver(naming, interfacePositionStubs);
        this.structured = structured;
    }

    public String emit(ClassFile classFile, Map<MethodEntry, MethodPlan> plans) {
        String internalName = classFile.getClassName();
        String csClassName = CsNamer.classNameOf(internalName);
        MemberNamer namer = naming.namerOf(internalName);
        TypeMapper types = naming.typeMapper();
        boolean isInterface = Modifiers.isInterface(classFile.getAccess());
        ClassPolicyResolver.ClassPolicy policy = policyResolver.resolve(classFile);

        CsWriter w = new CsWriter();
        w.open("namespace " + CsNamer.namespaceOf(internalName));
        if (isInterface) {
            w.open("internal interface " + csClassName + heritageOf(classFile, null));
            for (FieldEntry field : classFile.getFields()) {
                CsType type = types.storageType(field.getDesc());
                w.line("public static " + type.csText() + " " + namer.fieldName(field) + ";");
            }
        } else {
            String accessPrefix = naming.isBootstrapped(internalName) ? "public " : "internal ";
            String abstractPrefix = Modifiers.isAbstract(classFile.getAccess()) ? "abstract " : "";
            String partialPrefix = naming.isBootstrapped(internalName) ? "partial " : "";
            String heritage = policy.unsupportedReason() != null
                    ? " : " + policy.baseFqcn()
                    : heritageOf(classFile, policy.baseFqcn());
            w.open(accessPrefix + abstractPrefix + partialPrefix + "class " + csClassName + heritage);
            for (FieldEntry field : classFile.getFields()) {
                CsType type = types.storageType(field.getDesc());
                boolean isStatic = (field.getAccess() & AccessFlags.ACC_STATIC) != 0;
                // C# forbids `volatile` on 64-bit types; a volatile long/double stays plain (reported).
                boolean isVolatile = (field.getAccess() & AccessFlags.ACC_VOLATILE) != 0
                        && !field.getDesc().equals("J") && !field.getDesc().equals("D");
                w.line("internal " + (isStatic ? "static " : "") + (isVolatile ? "volatile " : "")
                        + type.csText() + " " + namer.fieldName(field) + ";");
            }
            w.line();
            boolean isRoot = policy.baseFqcn() == null;
            w.open("public " + csClassName + "(global::java.lang.RawNew r)"
                    + (isRoot ? "" : " : base(r)"));
            if (extendsShimThrowable(internalName)) {
                w.line("JavaClassName = " + CsStrings.quote(internalName.replace('/', '.')) + ";");
            }
            w.close();
        }
        for (MethodEntry method : classFile.getMethods()) {
            if (naming.isSuppressed(internalName, method.getName(), method.getDesc())) {
                continue;
            }
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

    private void emitMethod(CsWriter w, ClassFile classFile, String csClassName, MethodEntry method,
                            MethodPlan plan, MemberNamer namer, TypeMapper types, boolean isInterface) {
        String name = method.getName();
        String desc = method.getDesc();
        if (!isInterface && Modifiers.isNative(method.getAccess())) {
            emitNativeMethod(w, classFile, method, namer, types);
            return;
        }
        String header;
        if (name.equals("<clinit>")) {
            header = "static " + csClassName + "()";
        } else if (name.equals("<init>")) {
            if (structured != null && naming.hasRealConstructor(classFile.getClassName(), desc)) {
                emitRealConstructor(w, csClassName, desc, types);
            }
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
        // A synchronized method holds the receiver's (instance) or the class's (static) monitor for
        // its whole body; the ACC_SYNCHRONIZED flag carries no bytecode, so mirror it with `lock`.
        boolean synchronizedMethod = !isInterface
                && !name.equals("<init>") && !name.equals("<clinit>")
                && (method.getAccess() & AccessFlags.ACC_SYNCHRONIZED) != 0;
        if (synchronizedMethod) {
            boolean isStatic = (method.getAccess() & AccessFlags.ACC_STATIC) != 0;
            w.open(isStatic ? "lock (typeof(" + csClassName + "))" : "lock (this)");
        }
        emitBody(w, classFile, method, plan, namer, name, desc);
        if (synchronizedMethod) {
            w.close();
        }
        w.close();
    }

    /** Emits the method body content (no enclosing braces): enum synthesis, structured, or classic. */
    private void emitBody(CsWriter w, ClassFile classFile, MethodEntry method, MethodPlan plan,
                          MemberNamer namer, String name, String desc) {
        if (EnumSynthesis.emit(w, classFile, name, desc, namer)) {
            return;
        }
        // Bootstrapped JDK classes stay on the goto emitter: their bytecode is the hairiest input
        // and structured recovery can miscompile it silently (dropped returns, non-terminating
        // loops) where per-method fallback cannot see the error. App code gets structured bodies.
        if (structured != null && plan instanceof MethodPlan.Supported
                && !naming.isBootstrapped(classFile.getClassName())) {
            java.util.Optional<String> body = structured.tryEmit(classFile, method, 3);
            if (body.isPresent()) {
                w.raw(body.get());
                return;
            }
        }
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
    }

    private void emitRealConstructor(CsWriter w, String csClassName, String desc, TypeMapper types) {
        List<CsType> params = types.paramTypes(desc);
        String args = argNames(params.size());
        w.open("internal " + csClassName + "(" + paramList(desc, types)
                + ") : this(global::java.lang.RawNew.I)");
        w.line(MemberNamer.initMethodName(desc) + "(" + args + ");");
        w.close();
        w.line();
    }

    private static String argNames(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("p").append(i);
        }
        return sb.toString();
    }

    private void emitStubBody(CsWriter w, ClassFile classFile, String name, String desc, String reason) {
        String owner = classFile.getClassName();
        report.unsupportedMethod(owner, name, desc, reason);
        w.line("throw new global::System.NotSupportedException("
                + CsStrings.quote("j2cs: " + owner + "." + name + desc + ": " + reason) + ");");
    }

    /**
     * Bridges a classic-JNI native method to the loaded library's exported symbol. A static native
     * whose parameters and return are all primitive is emitted as a real P/Invoke through a cached
     * unmanaged function pointer; the JNIEnv/jclass leading arguments are passed as the synthesized
     * env (J2csNative.Env) and a null class handle, which the pure probes ignore. Anything needing
     * object marshalling or the instance receiver degrades to the native-method stub for now.
     */
    private void emitNativeMethod(CsWriter w, ClassFile classFile, MethodEntry method,
                                  MemberNamer namer, TypeMapper types) {
        String owner = classFile.getClassName();
        String name = method.getName();
        String desc = method.getDesc();
        String csName = namer.methodName(method);
        CsType returnType = types.returnType(desc);
        String signature = returnType.csText() + " " + csName + "(" + paramList(desc, types) + ")";

        List<String> paramDescs = TypeMapper.splitParams(desc);
        String retDesc = TypeMapper.returnDescriptor(desc);
        boolean eligible = Modifiers.isStatic(method.getAccess())
                && TypeMapper.isPrimitiveDescriptor(retDesc)
                && paramDescs.stream().allMatch(TypeMapper::isPrimitiveDescriptor);
        if (!eligible) {
            w.open(MethodModifiers.prefixFor(naming, owner, method) + signature);
            emitStubBody(w, classFile, name, desc, "native method");
            w.close();
            return;
        }

        String symbol = JniMangler.entryPoint(owner, name, desc, isOverloadedNative(classFile, name));
        String fnPtrType = nativeFnPtrType(paramDescs, retDesc);
        String field = "__np_" + symbol;
        w.line("private static unsafe " + fnPtrType + " " + field + ";");
        w.open("internal static unsafe " + signature);
        w.open("if (" + field + " == null)");
        w.line(field + " = (" + fnPtrType + ")global::java.lang.J2csNative.Export("
                + CsStrings.quote(symbol) + ");");
        w.close();
        StringBuilder call = new StringBuilder(field
                + "(global::java.lang.J2csNative.Env, global::System.IntPtr.Zero");
        for (int i = 0; i < paramDescs.size(); i++) {
            call.append(", ").append(toNativeArg(paramDescs.get(i), "p" + i));
        }
        call.append(")");
        if (retDesc.equals("V")) {
            w.line(call + ";");
        } else {
            w.line(nativeAbiType(retDesc) + " __r = " + call + ";");
            w.line("return " + fromNativeReturn(retDesc, "__r") + ";");
        }
        w.close();
    }

    private static boolean isOverloadedNative(ClassFile classFile, String name) {
        int count = 0;
        for (MethodEntry other : classFile.getMethods()) {
            if (Modifiers.isNative(other.getAccess()) && other.getName().equals(name)) {
                count++;
            }
        }
        return count > 1;
    }

    private static String nativeFnPtrType(List<String> paramDescs, String retDesc) {
        StringBuilder sb = new StringBuilder(
                "delegate* unmanaged[Cdecl]<global::System.IntPtr, global::System.IntPtr");
        for (String paramDesc : paramDescs) {
            sb.append(", ").append(nativeAbiType(paramDesc));
        }
        sb.append(", ").append(nativeAbiType(retDesc)).append(">");
        return sb.toString();
    }

    /** The C# type used at the native ABI boundary for a JNI primitive (jboolean is unsigned 8-bit). */
    private static String nativeAbiType(String desc) {
        return switch (desc.charAt(0)) {
            case 'Z' -> "byte";
            case 'B' -> "sbyte";
            case 'C' -> "ushort";
            case 'S' -> "short";
            case 'I' -> "int";
            case 'J' -> "long";
            case 'F' -> "float";
            case 'D' -> "double";
            case 'V' -> "void";
            default -> throw new IllegalArgumentException("not a primitive descriptor: " + desc);
        };
    }

    private static String toNativeArg(String desc, String expr) {
        return switch (desc.charAt(0)) {
            case 'Z' -> "(byte)(" + expr + " != 0 ? 1 : 0)";
            case 'C' -> "(ushort)" + expr;
            default -> expr;
        };
    }

    private static String fromNativeReturn(String desc, String expr) {
        return switch (desc.charAt(0)) {
            case 'Z' -> expr + " != 0 ? 1 : 0";
            case 'C' -> "(char)" + expr;
            default -> expr;
        };
    }

    private boolean extendsShimThrowable(String internalName) {
        String external = naming.hierarchy().firstExternalSuper(internalName);
        return external != null && ShimRegistry.isThrowableSubtype(external);
    }

    private static String interfacePrefix(MethodEntry method) {
        int access = method.getAccess();
        // Private interface members (e.g. a default method's lambda impl) must be internal, not
        // private: the synthetic lambda class calls them through the interface reference and C#
        // forbids that on a private member. Matches the class path (MethodModifiers.prefixFor).
        if (Modifiers.isPrivate(access)) {
            return Modifiers.isStatic(access) ? "internal static " : "internal ";
        }
        if (Modifiers.isStatic(access)) {
            return "static ";
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
