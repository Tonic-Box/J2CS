package com.tonic.j2cs.types;

/**
 * Narrowing casts applied where a compute-typed expression flows into a storage position,
 * reproducing the JVM's implicit truncation on putfield, array stores, calls, and returns.
 */
public final class Coercions {

    private Coercions() {
    }

    public static String coerce(CsType storage, String expr) {
        return switch (storage.csText()) {
            case "sbyte" -> "(sbyte)(" + expr + ")";
            case "short" -> "(short)(" + expr + ")";
            case "char" -> "(char)(" + expr + ")";
            default -> expr;
        };
    }
}
