package com.tonic.j2cs.types;

/**
 * A rendered C# type together with its coarse kind.
 */
public record CsType(String csText, Kind kind) {

    public enum Kind {
        PRIMITIVE, REF, ARRAY, VOID
    }

    public boolean isReference() {
        return kind == Kind.REF || kind == Kind.ARRAY;
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
