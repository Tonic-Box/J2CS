package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.type.IRType;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.types.Coercions;
import com.tonic.j2cs.types.CsType;

/**
 * Single owner of C# cast/bridge decisions. Given a source value's JVM type and a target C# type
 * it produces the coerced expression: primitive narrowing, an implicit upcast (no cast) when the
 * source is provably assignable to the target, or an explicit cast routed through a common-ancestor
 * bridge for unrelated or downcast reference conversions (arrays via object, other reference types
 * via java.lang.Object). Assignability spans the app class hierarchy and the shim supertype chain,
 * so redundant casts the previous per-site string-equality test emitted are dropped. Bridges are
 * always legal, so the assignability check only needs to be sound (never claim a non-assignable
 * pair is assignable).
 */
public final class TypeReconciler {

    private final NamingContext naming;

    public TypeReconciler(NamingContext naming) {
        this.naming = naming;
    }

    /** Coerce a source expression into a storage/assignment target (return, argument, field, slot). */
    public String coerce(CsType target, IRType sourceType, String sourceExpr) {
        if (!target.isReference()) {
            // A boxed value read from an Object-widened slot into a primitive one must be unboxed; C#
            // has no auto-unboxing at the java.lang.Object boundary. Restricted to Object — the merged
            // type such a slot carries — so a genuine non-wrapper reference in a primitive position is
            // left as the pre-existing type error rather than a nonsensical unbox.
            if (sourceType != null) {
                CsType source = naming.typeMapper().computeType(sourceType);
                String prim = unboxDescriptor(target.csText());
                if (prim != null && "java/lang/Object".equals(source.referenceInternalName())) {
                    return Boxing.unbox(prim, "(" + sourceExpr + ")");
                }
            }
            return Coercions.coerce(target, sourceExpr);
        }
        if (sourceType == null) {
            return sourceExpr;
        }
        CsType source = naming.typeMapper().computeType(sourceType);
        if (source.csText().equals(target.csText()) || isAssignable(source, target)) {
            return sourceExpr;
        }
        return bridgedCast(target, source, sourceExpr);
    }

    /** Coerce between two descriptor-typed storage positions (lambda adaptation). */
    public String coerce(String targetDesc, String sourceDesc, String expr) {
        CsType target = naming.typeMapper().storageType(targetDesc);
        if (!target.isReference()) {
            return Coercions.coerce(target, expr);
        }
        CsType source = naming.typeMapper().storageType(sourceDesc);
        if (source.csText().equals(target.csText()) || isAssignable(source, target)) {
            return expr;
        }
        return bridgedCast(target, source, expr);
    }

    /** Parenthesized cast of an expression to the named type. */
    public String castTo(String internalName, String expr) {
        return "((" + CsNamer.fqcn(internalName) + ")" + expr + ")";
    }

    /** Upcast a receiver to the member's declaring type unless its static type already exposes it;
     *  null internal name means statically unknown and always casts. */
    public String receiver(String declaringInternal, String receiverInternal, String expr) {
        if (receiverInternal == null) {
            return castTo(declaringInternal, expr);
        }
        return naming.hierarchy().staticallyHasMember(receiverInternal, declaringInternal)
                ? expr
                : castTo(declaringInternal, expr);
    }

    /** Emit an explicit checkcast to the target from a source of the given type. */
    public String cast(CsType target, IRType sourceType, String sourceExpr) {
        CsType source = sourceType != null ? naming.typeMapper().computeType(sourceType) : null;
        if (source != null && source.csText().equals(target.csText())) {
            return sourceExpr;
        }
        if (source != null && isAssignable(source, target)) {
            return "(" + target.csText() + ")(" + sourceExpr + ")";
        }
        return bridgedCast(target, source, sourceExpr);
    }

    private static String bridgedCast(CsType target, CsType source, String expr) {
        // Java arrays are Objects but native C# arrays are not the java.lang.Object shim class, so
        // cross the Object boundary by boxing (array -> Object) / unboxing (Object -> array). Only a
        // scalar java.lang.Object source holds a boxed array; array->array downcasts and unknown
        // sources keep the plain via-object cast so existing native-array conversions are unchanged.
        boolean objectSource = source != null && source.csText().equals("global::java.lang.Object");
        if (target.isArray()) {
            if (objectSource) {
                return "((" + target.csText() + ")global::java.lang.JRuntime.Unbox(" + expr + "))";
            }
            // An interface-element array (e.g. List[]) is not C#-covariant to the shim Object class
            // array, so a plain cast throws; widen through a helper that copies only when needed.
            if (target.csText().equals("global::java.lang.Object[]")) {
                return "global::java.lang.JRuntime.ToObjectArray(" + expr + ")";
            }
            return "((" + target.csText() + ")(object)(" + expr + "))";
        }
        if (source != null && source.isArray()) {
            String boxed = "global::java.lang.JRuntime.Box(" + expr + ", \"" + source.descriptor() + "\")";
            return target.csText().equals("global::java.lang.Object")
                    ? boxed
                    : "((" + target.csText() + ")" + boxed + ")";
        }
        // A primitive value cannot be cast to the java.lang.Object shim class (e.g. `(Object)(float)`);
        // it must be boxed to its wrapper, then downcast if the reference target is more specific. This
        // arises when a slot merges a primitive with a reference type across control flow.
        if (source != null && source.kind() == CsType.Kind.PRIMITIVE) {
            String primDesc = primitiveDescriptor(source.csText());
            if (primDesc != null) {
                String boxed = Boxing.box(primDesc, expr);
                return target.csText().equals("global::java.lang.Object")
                        ? boxed
                        : "((" + target.csText() + ")" + boxed + ")";
            }
        }
        return "(" + target.csText() + ")" + bridge(target, source) + "(" + expr + ")";
    }

    /**
     * The detour making an explicit reference conversion legal: array targets via object (a C#
     * array never derives from the java.lang.Object shim class); no detour needed when either
     * side already is java.lang.Object.
     */
    private static String primitiveDescriptor(String csText) {
        return switch (csText) {
            case "int" -> "I";
            case "long" -> "J";
            case "float" -> "F";
            case "double" -> "D";
            case "boolean" -> "Z";
            case "byte", "sbyte" -> "B";
            case "char" -> "C";
            case "short" -> "S";
            default -> null;
        };
    }

    private static String bridge(CsType target, CsType source) {
        if (target.isArray()) {
            return "(object)";
        }
        if (target.csText().equals("global::java.lang.Object")
                || (source != null && source.csText().equals("global::java.lang.Object"))) {
            return "";
        }
        return "(global::java.lang.Object)";
    }

    /**
     * Whether a value of {@code sub} is implicitly C#-assignable to {@code sup}. Sound and
     * deliberately conservative: only the app-class hierarchy (class ancestors and implemented
     * interfaces, which C# converts implicitly) skips the cast. java.lang.Object is never skipped
     * because shim interface types are C# interfaces and are NOT implicitly convertible to the
     * java.lang.Object class; shim supertype chains likewise fall through to an explicit (but
     * always legal) bridge cast rather than risking an unsound bare assignment.
     */
    public boolean isAssignable(CsType sub, CsType sup) {
        if (sub.csText().equals(sup.csText())) {
            return true;
        }
        String subInternal = sub.referenceInternalName();
        String supInternal = sup.referenceInternalName();
        if (subInternal == null || supInternal == null || supInternal.equals("java/lang/Object")) {
            return false;
        }
        return naming.hierarchy().isAppClass(subInternal)
                && naming.hierarchy().staticallyHasMember(subInternal, supInternal);
    }

    /** The JVM primitive descriptor for a C# primitive type name, or null if it is not a primitive. */
    private static String unboxDescriptor(String csText) {
        return switch (csText) {
            case "int" -> "I";
            case "long" -> "J";
            case "float" -> "F";
            case "double" -> "D";
            case "short" -> "S";
            case "sbyte" -> "B";
            case "char" -> "C";
            case "bool" -> "Z";
            default -> null;
        };
    }
}
