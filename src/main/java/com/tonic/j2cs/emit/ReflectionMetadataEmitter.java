package com.tonic.j2cs.emit;

import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.ConstPool;
import com.tonic.parser.FieldEntry;
import com.tonic.parser.MethodEntry;
import java.util.ArrayList;
import java.util.List;

/**
 * Emits a class's {@code internal static void __RegisterReflection()} — the generated reflection
 * metadata for one transpiled class. Each field/method/constructor is registered with its original
 * Java name/descriptor, raw JVM modifiers, and an AOT-safe invoke/get/set thunk that calls the real
 * emitted member (box/unboxing at the java.lang.Object boundary; array-typed members marshal through
 * JRuntime.Box/Unbox, the same array-as-Object bridge the emitter uses everywhere).
 */
public final class ReflectionMetadataEmitter {

    private final NamingContext naming;
    private final AnnotationEmitter annotations;

    public ReflectionMetadataEmitter(NamingContext naming, AnnotationEmitter annotations) {
        this.naming = naming;
        this.annotations = annotations;
    }

    /**
     * App, non-bootstrapped classes carry generated reflection metadata, interfaces included: their
     * abstract members are reflectively looked up (e.g. LWJGL resolves {@code CallbackI.callback} to
     * bind its native dispatcher), and their static fields/default/static methods are equally visible.
     */
    public static boolean hasMetadata(NamingContext naming, String internalName) {
        return naming.isAppClass(internalName) && !naming.isBootstrapped(internalName);
    }

    public void emit(CsWriter w, String methodName, ClassFile classFile, MemberNamer namer, TypeMapper types) {
        String internalName = classFile.getClassName();
        String fqcn = CsNamer.fqcn(internalName);
        String superName = classFile.getSuperClassName();
        String superArg = superName == null ? "null" : CsStrings.quote(superName.replace('/', '.'));
        ConstPool pool = classFile.getConstPool();

        w.open("private static void " + methodName + "()");
        w.line("var __m = global::j2cs.reflect.ClassMeta.New("
                + CsStrings.quote(internalName.replace('/', '.')) + ", typeof(" + fqcn + "), " + superArg
                + ").WithAnnotations(" + annotations.arrayExpr(classFile.getClassAttributes(), pool) + ");");

        for (FieldEntry field : classFile.getFields()) {
            String csName = namer.findFieldName(field.getName(), field.getDesc());
            if (csName == null) {
                continue;
            }
            emitField(w, types, fqcn, field, csName, annotations.arrayExpr(field.getAttributes(), pool));
        }
        for (MethodEntry method : classFile.getMethods()) {
            String name = method.getName();
            if (name.equals("<init>") || name.equals("<clinit>")) {
                continue;
            }
            if (naming.isSuppressed(internalName, name, method.getDesc())) {
                continue;
            }
            String csName = namer.findMethodName(name, method.getDesc());
            if (csName == null) {
                continue;
            }
            emitMethod(w, types, fqcn, method, csName, annotations.arrayExpr(method.getAttributes(), pool));
        }
        boolean isAbstract = com.tonic.util.Modifiers.isAbstract(classFile.getAccess());
        for (MethodEntry ctor : classFile.getConstructors()) {
            emitConstructor(w, types, fqcn, ctor, isAbstract);
        }
        w.line("global::j2cs.reflect.Registry.Register(__m);");
        w.close();
    }

    private void emitField(CsWriter w, TypeMapper types, String fqcn, FieldEntry field, String csName, String annoExpr) {
        String desc = field.getDesc();
        boolean isStatic = com.tonic.util.Modifiers.isStatic(field.getAccess());
        String ref = (isStatic ? fqcn : "((" + fqcn + ")__o)") + "." + csName;
        String getter;
        String setter;
        if (isArray(desc)) {
            getter = "__o => global::java.lang.JRuntime.Box(" + ref + ", " + CsStrings.quote(desc) + ")";
            setter = "(__o, __v) => " + ref + " = (" + types.storageType(desc).csText()
                    + ")global::java.lang.JRuntime.Unbox(__v)";
        } else if (TypeMapper.isPrimitiveDescriptor(desc)) {
            getter = "__o => " + Boxing.box(desc, ref);
            setter = "(__o, __v) => " + ref + " = " + Boxing.unbox(desc, "__v");
        } else {
            getter = "__o => (global::java.lang.Object)(" + ref + ")";
            setter = "(__o, __v) => " + ref + " = (" + types.storageType(desc).csText() + ")__v";
        }
        w.line("__m.AddField(" + CsStrings.quote(field.getName()) + ", " + classOf(desc) + ", "
                + field.getAccess() + ", " + getter + ", " + setter + ", " + annoExpr + ");");
    }

    private void emitMethod(CsWriter w, TypeMapper types, String fqcn, MethodEntry method, String csName, String annoExpr) {
        String desc = method.getDesc();
        List<String> params = TypeMapper.splitParams(desc);
        String ret = TypeMapper.returnDescriptor(desc);
        boolean isStatic = com.tonic.util.Modifiers.isStatic(method.getAccess());
        String receiver = isStatic ? fqcn : "((" + fqcn + ")__o)";

        String invoker;
        String call = receiver + "." + csName + "(" + String.join(", ", marshalArgs(types, params)) + ")";
        if (ret.equals("V")) {
            invoker = "(__o, __a) => { " + call + "; return null; }";
        } else if (TypeMapper.isPrimitiveDescriptor(ret)) {
            invoker = "(__o, __a) => " + Boxing.box(ret, call);
        } else if (isArray(ret)) {
            invoker = "(__o, __a) => global::java.lang.JRuntime.Box(" + call + ", " + CsStrings.quote(ret) + ")";
        } else {
            invoker = "(__o, __a) => (global::java.lang.Object)(" + call + ")";
        }
        w.line("__m.AddMethod(" + CsStrings.quote(method.getName()) + ", " + classArray(params) + ", "
                + classOf(ret) + ", " + method.getAccess() + ", " + invoker + ", " + annoExpr + ");");
    }

    private void emitConstructor(CsWriter w, TypeMapper types, String fqcn, MethodEntry ctor, boolean isAbstract) {
        String desc = ctor.getDesc();
        List<String> params = TypeMapper.splitParams(desc);
        String factory;
        if (isAbstract) {
            factory = "__a => throw new global::System.NotSupportedException("
                    + "\"j2cs: cannot instantiate abstract class\")";
        } else {
            factory = "__a => { var __o = new " + fqcn + "(global::java.lang.RawNew.I); __o."
                    + MemberNamer.initMethodName(desc) + "(" + String.join(", ", marshalArgs(types, params))
                    + "); return __o; }";
        }
        w.line("__m.AddCtor(" + classArray(params) + ", " + ctor.getAccess() + ", " + factory + ");");
    }

    private List<String> marshalArgs(TypeMapper types, List<String> params) {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            String d = params.get(i);
            String slot = "__a[" + i + "]";
            String cs = types.storageType(d).csText();
            if (TypeMapper.isPrimitiveDescriptor(d)) {
                out.add(Boxing.unbox(d, slot));
            } else if (isArray(d)) {
                out.add("(" + cs + ")global::java.lang.JRuntime.Unbox(" + slot + ")");
            } else {
                out.add("(" + cs + ")" + slot);
            }
        }
        return out;
    }

    private String classArray(List<String> params) {
        if (params.isEmpty()) {
            return "global::System.Array.Empty<global::java.lang.Class>()";
        }
        List<String> parts = new ArrayList<>();
        for (String d : params) {
            parts.add(classOf(d));
        }
        return "new global::java.lang.Class[]{ " + String.join(", ", parts) + " }";
    }

    private String classOf(String descriptor) {
        return "global::java.lang.Class.Of(" + CsStrings.quote(classNameOf(descriptor)) + ")";
    }

    private static String classNameOf(String descriptor) {
        if (descriptor.startsWith("[")) {
            return descriptor.replace('/', '.');
        }
        return switch (descriptor) {
            case "V" -> "void";
            case "I" -> "int";
            case "J" -> "long";
            case "Z" -> "boolean";
            case "B" -> "byte";
            case "S" -> "short";
            case "C" -> "char";
            case "F" -> "float";
            case "D" -> "double";
            default -> {
                String internal = TypeMapper.unwrapReference(descriptor);
                yield internal == null ? descriptor : internal.replace('/', '.');
            }
        };
    }

    private static boolean isArray(String descriptor) {
        return descriptor.startsWith("[");
    }
}
