package com.tonic.j2cs.naming;

import java.util.Set;

/**
 * The reserved C# keywords that must be escaped with '@' when used as identifiers.
 */
public final class CsKeywords {

    private static final Set<String> KEYWORDS = Set.of(
            "abstract", "as", "base", "bool", "break", "byte", "case", "catch", "char", "checked",
            "class", "const", "continue", "decimal", "default", "delegate", "do", "double", "else",
            "enum", "event", "explicit", "extern", "false", "finally", "fixed", "float", "for",
            "foreach", "goto", "if", "implicit", "in", "int", "interface", "internal", "is", "lock",
            "long", "namespace", "new", "null", "object", "operator", "out", "override", "params",
            "private", "protected", "public", "readonly", "ref", "return", "sbyte", "sealed",
            "short", "sizeof", "stackalloc", "static", "string", "struct", "switch", "this",
            "throw", "true", "try", "typeof", "uint", "ulong", "unchecked", "unsafe", "ushort",
            "using", "virtual", "void", "volatile", "while");

    private CsKeywords() {
    }

    public static boolean isKeyword(String name) {
        return KEYWORDS.contains(name);
    }
}
