package com.tonic.j2cs.emit;

import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.ConstPool;
import com.tonic.parser.MethodEntry;
import com.tonic.parser.attribute.AnnotationDefaultAttribute;
import com.tonic.parser.attribute.Attribute;
import com.tonic.parser.attribute.RuntimeVisibleAnnotationsAttribute;
import com.tonic.parser.attribute.annotation.Annotation;
import com.tonic.parser.attribute.annotation.ElementValue;
import com.tonic.parser.attribute.annotation.ElementValuePair;
import com.tonic.parser.attribute.annotation.EnumConst;
import com.tonic.parser.constpool.DoubleItem;
import com.tonic.parser.constpool.FloatItem;
import com.tonic.parser.constpool.IntegerItem;
import com.tonic.parser.constpool.Item;
import com.tonic.parser.constpool.LongItem;
import com.tonic.parser.constpool.Utf8Item;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Emits runtime annotation support: for each runtime-retained annotation type an impl class in
 * namespace j2cs.anno implementing the annotation interface with its element accessors, and, per
 * annotated element, a `new Impl(...)` expression whose values come from the applied element-value
 * pairs (or the annotation type's AnnotationDefault). Only annotations whose type is in the input
 * are materialised; others (JDK annotations without a shim) are skipped.
 */
public final class AnnotationEmitter {

    private static final String EMPTY = "global::System.Array.Empty<global::java.lang.annotation.Annotation>()";

    private final NamingContext naming;
    private final TypeMapper types;
    private final Map<String, ClassFile> byInternal;
    private final Map<String, String> neededImpls = new LinkedHashMap<>();

    public AnnotationEmitter(NamingContext naming, Map<String, ClassFile> byInternal) {
        this.naming = naming;
        this.types = naming.typeMapper();
        this.byInternal = byInternal;
    }

    /** C# expression for the runtime-visible annotations on an element, as an Annotation[]. */
    public String arrayExpr(List<Attribute> attributes, ConstPool pool) {
        if (attributes == null) {
            return EMPTY;
        }
        List<String> exprs = new ArrayList<>();
        for (Attribute attr : attributes) {
            if (attr instanceof RuntimeVisibleAnnotationsAttribute anns) {
                for (Annotation ann : anns.getAnnotations()) {
                    String expr = instanceExpr(ann, pool);
                    if (expr != null) {
                        exprs.add(expr);
                    }
                }
            }
        }
        if (exprs.isEmpty()) {
            return EMPTY;
        }
        return "new global::java.lang.annotation.Annotation[]{ " + String.join(", ", exprs) + " }";
    }

    /** Emits the collected annotation impl classes into namespace j2cs.anno. */
    public void emitImpls(CsWriter w) {
        w.open("namespace j2cs.anno");
        for (Map.Entry<String, String> e : neededImpls.entrySet()) {
            emitImpl(w, e.getKey(), e.getValue());
        }
        w.close();
    }

    public boolean hasImpls() {
        return !neededImpls.isEmpty();
    }

    private String instanceExpr(Annotation ann, ConstPool pool) {
        String typeInternal = descriptorInternal(resolveUtf8(pool, ann.getTypeIndex()));
        ClassFile typeCf = typeInternal == null ? null : byInternal.get(typeInternal);
        if (typeCf == null) {
            return null;
        }
        String mangled = mangle(typeInternal);
        neededImpls.put(mangled, typeInternal);

        Map<String, ElementValue> applied = new LinkedHashMap<>();
        for (ElementValuePair pair : ann.getElementValuePairs()) {
            applied.put(pair.getElementName(), pair.getValue());
        }

        List<String> args = new ArrayList<>();
        for (MethodEntry element : elementMethods(typeCf)) {
            String retDesc = TypeMapper.returnDescriptor(element.getDesc());
            ElementValue ev = applied.get(element.getName());
            ConstPool valuePool = pool;
            if (ev == null) {
                AnnotationDefaultAttribute def = defaultOf(element);
                if (def != null) {
                    ev = def.getDefaultValue();
                    valuePool = typeCf.getConstPool();
                }
            }
            args.add(ev == null ? defaultZero(retDesc) : valueExpr(retDesc, ev, valuePool));
        }
        return "new global::j2cs.anno." + mangled + "(" + String.join(", ", args) + ")";
    }

    private void emitImpl(CsWriter w, String mangled, String typeInternal) {
        ClassFile typeCf = byInternal.get(typeInternal);
        String typeFqcn = CsNamer.fqcn(typeInternal);
        MemberNamer typeNamer = naming.namerOf(typeInternal);
        List<MethodEntry> elements = elementMethods(typeCf);

        w.open("internal sealed class " + mangled + " : global::java.lang.Object, " + typeFqcn);
        for (int i = 0; i < elements.size(); i++) {
            w.line("private readonly " + types.storageType(TypeMapper.returnDescriptor(elements.get(i).getDesc())).csText()
                    + " f" + i + ";");
        }
        StringBuilder ctorParams = new StringBuilder();
        for (int i = 0; i < elements.size(); i++) {
            if (i > 0) {
                ctorParams.append(", ");
            }
            ctorParams.append(types.storageType(TypeMapper.returnDescriptor(elements.get(i).getDesc())).csText())
                    .append(" a").append(i);
        }
        w.open("internal " + mangled + "(" + ctorParams + ") : base(global::java.lang.RawNew.I)");
        for (int i = 0; i < elements.size(); i++) {
            w.line("f" + i + " = a" + i + ";");
        }
        w.close();
        for (int i = 0; i < elements.size(); i++) {
            MethodEntry element = elements.get(i);
            String csRet = types.storageType(TypeMapper.returnDescriptor(element.getDesc())).csText();
            w.open("public " + csRet + " " + typeNamer.methodName(element) + "()");
            w.line("return f" + i + ";");
            w.close();
        }
        w.open("public global::java.lang.Class annotationType()");
        w.line("return global::java.lang.Class.Of(" + CsStrings.quote(typeInternal.replace('/', '.')) + ");");
        w.close();
        w.open("public override global::java.lang.String toString()");
        w.line("return global::java.lang.String.Wrap(" + CsStrings.quote("@" + typeInternal.replace('/', '.')) + ");");
        w.close();
        w.close();
    }

    private String valueExpr(String retDesc, ElementValue ev, ConstPool pool) {
        int tag = ev.getTag();
        return switch (tag) {
            case 'I' -> String.valueOf(intConst(pool, ev));
            case 'S' -> "(short)" + intConst(pool, ev);
            case 'B' -> "(sbyte)" + intConst(pool, ev);
            case 'C' -> "(char)" + intConst(pool, ev);
            case 'Z' -> intConst(pool, ev) != 0 ? "1" : "0";
            case 'J' -> ((LongItem) pool.getItem((Integer) ev.getValue())).getValue() + "L";
            case 'F' -> floatExpr(((FloatItem) pool.getItem((Integer) ev.getValue())).getValue());
            case 'D' -> doubleExpr(((DoubleItem) pool.getItem((Integer) ev.getValue())).getValue());
            case 's' -> "global::java.lang.String.Wrap(" + CsStrings.quote(stringConst(pool, ev)) + ")";
            case 'e' -> enumExpr((EnumConst) ev.getValue(), pool);
            case 'c' ->
                    "global::java.lang.Class.Of(" + CsStrings.quote(classNameOf(resolveUtf8(pool, (Integer) ev.getValue()))) + ")";
            case '@' -> instanceExpr((Annotation) ev.getValue(), pool);
            case '[' -> arrayValueExpr(retDesc, ev, pool);
            default -> defaultZero(retDesc);
        };
    }

    private String arrayValueExpr(String retDesc, ElementValue ev, ConstPool pool) {
        String componentDesc = retDesc.startsWith("[") ? retDesc.substring(1) : "Ljava/lang/Object;";
        String csComponent = types.storageType(componentDesc).csText();
        @SuppressWarnings("unchecked")
        List<ElementValue> values = (List<ElementValue>) ev.getValue();
        List<String> parts = new ArrayList<>();
        for (ElementValue sub : values) {
            parts.add(valueExpr(componentDesc, sub, pool));
        }
        return "new " + csComponent + "[]{ " + String.join(", ", parts) + " }";
    }

    private String enumExpr(EnumConst ec, ConstPool pool) {
        String typeInternal = descriptorInternal(resolveUtf8(pool, ec.getTypeNameIndex()));
        String constName = resolveUtf8(pool, ec.getConstNameIndex());
        if (typeInternal == null || !naming.isAppClass(typeInternal)) {
            return "null";
        }
        String csField = naming.namerOf(typeInternal).findFieldName(constName, "L" + typeInternal + ";");
        if (csField == null) {
            return "null";
        }
        return CsNamer.fqcn(typeInternal) + "." + csField;
    }

    private int intConst(ConstPool pool, ElementValue ev) {
        return ((IntegerItem) pool.getItem((Integer) ev.getValue())).getValue();
    }

    private static String stringConst(ConstPool pool, ElementValue ev) {
        String s = resolveUtf8(pool, (Integer) ev.getValue());
        return s == null ? "" : s;
    }

    private static String floatExpr(float f) {
        if (Float.isNaN(f)) {
            return "global::System.Single.NaN";
        }
        if (f == Float.POSITIVE_INFINITY) {
            return "global::System.Single.PositiveInfinity";
        }
        if (f == Float.NEGATIVE_INFINITY) {
            return "global::System.Single.NegativeInfinity";
        }
        return f + "f";
    }

    private static String doubleExpr(double d) {
        if (Double.isNaN(d)) {
            return "global::System.Double.NaN";
        }
        if (d == Double.POSITIVE_INFINITY) {
            return "global::System.Double.PositiveInfinity";
        }
        if (d == Double.NEGATIVE_INFINITY) {
            return "global::System.Double.NegativeInfinity";
        }
        return String.valueOf(d);
    }

    private AnnotationDefaultAttribute defaultOf(MethodEntry element) {
        if (element.getAttributes() == null) {
            return null;
        }
        for (Attribute attr : element.getAttributes()) {
            if (attr instanceof AnnotationDefaultAttribute def) {
                return def;
            }
        }
        return null;
    }

    private static List<MethodEntry> elementMethods(ClassFile typeCf) {
        List<MethodEntry> out = new ArrayList<>();
        for (MethodEntry m : typeCf.getMethods()) {
            if (!m.getName().equals("<clinit>") && !m.getName().equals("<init>")) {
                out.add(m);
            }
        }
        return out;
    }

    private static String defaultZero(String desc) {
        return switch (desc) {
            case "I", "S", "B", "C", "Z" -> "0";
            case "J" -> "0L";
            case "F" -> "0f";
            case "D" -> "0.0";
            default -> "null";
        };
    }

    private static String resolveUtf8(ConstPool pool, int index) {
        Item<?> item = pool.getItem(index);
        return item instanceof Utf8Item u ? u.getValue() : null;
    }

    private static String descriptorInternal(String descriptor) {
        if (descriptor == null || !descriptor.startsWith("L") || !descriptor.endsWith(";")) {
            return null;
        }
        return descriptor.substring(1, descriptor.length() - 1);
    }

    private static String classNameOf(String descriptor) {
        if (descriptor == null) {
            return "java.lang.Object";
        }
        if (descriptor.startsWith("[")) {
            return descriptor.replace('/', '.');
        }
        String internal = TypeMapper.unwrapReference(descriptor);
        if (internal != null) {
            return internal.replace('/', '.');
        }
        return switch (descriptor) {
            case "I" -> "int";
            case "J" -> "long";
            case "Z" -> "boolean";
            case "B" -> "byte";
            case "S" -> "short";
            case "C" -> "char";
            case "F" -> "float";
            case "D" -> "double";
            case "V" -> "void";
            default -> descriptor;
        };
    }

    private static String mangle(String internal) {
        return internal.replaceAll("[^A-Za-z0-9]", "_");
    }
}
