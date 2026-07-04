package com.tonic.j2cs.types;

/**
 * A rendered C# type together with its coarse kind.
 */
public record CsType(String csText, Kind kind, String descriptor) {

    public enum Kind {
        PRIMITIVE, REF, ARRAY, VOID
    }

    public CsType(String csText, Kind kind) {
        this(csText, kind, null);
    }

    public boolean isReference() {
        return kind == Kind.REF || kind == Kind.ARRAY;
    }

    public boolean isArray() {
        return kind == Kind.ARRAY;
    }

    /**
     * The JVM internal name when this is a plain reference type (L...;), else null (arrays,
     * primitives, or a type built without a descriptor). Used for assignability queries.
     */
    public String referenceInternalName() {
        if (kind == Kind.REF && descriptor != null
                && descriptor.startsWith("L") && descriptor.endsWith(";")) {
            return descriptor.substring(1, descriptor.length() - 1);
        }
        return null;
    }

    public String defaultLiteral() {
        if (kind == Kind.REF || kind == Kind.ARRAY) {
            return "null";
        }
        if (kind == Kind.VOID) {
            throw new IllegalStateException("void has no default value");
        }
        return switch (csText) {
            case "long" -> "0L";
            case "float" -> "0f";
            case "double" -> "0d";
            case "char" -> "'\\0'";
            default -> "0";
        };
    }
}
