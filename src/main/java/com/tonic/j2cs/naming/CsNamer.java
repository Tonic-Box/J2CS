package com.tonic.j2cs.naming;

/**
 * Maps JVM internal names to C# identifiers, namespaces, and fully qualified type references.
 * Every type reference is emitted with global:: so generated code never needs using directives.
 */
public final class CsNamer {

    public static final String DEFAULT_NAMESPACE = "jdefault";

    private CsNamer() {
    }

    public static String identifier(String name) {
        StringBuilder sb = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '$') {
                sb.append("_S_");
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_') {
                sb.append(c);
            } else {
                sb.append("_u").append(String.format("%04X", (int) c)).append('_');
            }
        }
        if (sb.isEmpty() || Character.isDigit(sb.charAt(0))) {
            sb.insert(0, '_');
        }
        String result = sb.toString();
        return CsKeywords.isKeyword(result) ? "@" + result : result;
    }

    public static String namespaceOf(String internalName) {
        int lastSlash = internalName.lastIndexOf('/');
        if (lastSlash < 0) {
            return DEFAULT_NAMESPACE;
        }
        String[] segments = internalName.substring(0, lastSlash).split("/");
        StringBuilder sb = new StringBuilder();
        for (String segment : segments) {
            if (!sb.isEmpty()) {
                sb.append('.');
            }
            sb.append(identifier(segment));
        }
        return sb.toString();
    }

    public static String classNameOf(String internalName) {
        return identifier(internalName.substring(internalName.lastIndexOf('/') + 1));
    }

    public static String fqcn(String internalName) {
        return "global::" + namespaceOf(internalName) + "." + classNameOf(internalName);
    }
}
