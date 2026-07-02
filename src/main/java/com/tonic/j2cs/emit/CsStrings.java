package com.tonic.j2cs.emit;

/**
 * Renders Java strings as C# string literals, escaping everything outside printable ASCII.
 */
public final class CsStrings {

    private CsStrings() {
    }

    public static String quote(String value) {
        StringBuilder sb = new StringBuilder(value.length() + 2);
        sb.append('"');
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '\\' -> sb.append("\\\\");
                case '"' -> sb.append("\\\"");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                case '\0' -> sb.append("\\0");
                default -> {
                    if (c >= 0x20 && c < 0x7F) {
                        sb.append(c);
                    } else {
                        sb.append("\\u").append(String.format("%04X", (int) c));
                    }
                }
            }
        }
        sb.append('"');
        return sb.toString();
    }
}
