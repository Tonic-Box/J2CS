package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.type.IRType;
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
            return Coercions.coerce(target, sourceExpr);
        }
        if (sourceType == null) {
            return sourceExpr;
        }
        CsType source = naming.typeMapper().computeType(sourceType);
        if (source.csText().equals(target.csText()) || isAssignable(source, target)) {
            return sourceExpr;
        }
        return "(" + target.csText() + ")" + bridge(target) + "(" + sourceExpr + ")";
    }

    /** Emit an explicit checkcast to the target from a source of the given type. */
    public String cast(CsType target, IRType sourceType, String sourceExpr) {
        if (sourceType != null) {
            CsType source = naming.typeMapper().computeType(sourceType);
            if (source.csText().equals(target.csText()) || isAssignable(source, target)) {
                return "(" + target.csText() + ")(" + sourceExpr + ")";
            }
        }
        return "(" + target.csText() + ")" + bridge(target) + "(" + sourceExpr + ")";
    }

    private static String bridge(CsType target) {
        if (target.isArray()) {
            return "(object)";
        }
        if (target.csText().equals("global::java.lang.Object")) {
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
}
