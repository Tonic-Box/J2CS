package com.tonic.j2cs.emit;

/**
 * Java autobox/unbox expression helpers, shared by lambda adaptation (LambdaExpander) and
 * reflection invoke/get/set thunks (ReflectionMetadataEmitter). Boolean is int in the C# ABI,
 * so Z round-trips through java.lang.Boolean.
 */
public final class Boxing {

    private Boxing() {
    }

    public static String box(String primDesc, String expr) {
        String type = switch (primDesc) {
            case "I" -> "Integer";
            case "J" -> "Long";
            case "D" -> "Double";
            case "F" -> "Float";
            case "S" -> "Short";
            case "B" -> "Byte";
            case "C" -> "Character";
            case "Z" -> "Boolean";
            default -> throw new IllegalArgumentException("cannot box " + primDesc);
        };
        return "global::java.lang." + type + ".valueOf(" + expr + ")";
    }

    public static String unbox(String primDesc, String expr) {
        return switch (primDesc) {
            case "I" -> "((global::java.lang.Number)" + expr + ").intValue()";
            case "J" -> "((global::java.lang.Number)" + expr + ").longValue()";
            case "D" -> "((global::java.lang.Number)" + expr + ").doubleValue()";
            case "F" -> "((global::java.lang.Number)" + expr + ").floatValue()";
            case "S" -> "((global::java.lang.Number)" + expr + ").shortValue()";
            case "B" -> "((global::java.lang.Number)" + expr + ").byteValue()";
            case "C" -> "((global::java.lang.Character)" + expr + ").charValue()";
            case "Z" -> "((global::java.lang.Boolean)" + expr + ").booleanValue()";
            default -> throw new IllegalArgumentException("cannot unbox " + primDesc);
        };
    }
}
