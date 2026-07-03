package com.tonic.j2cs.shims;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The single source of truth for what the hand-written JavaCompat shim implements.
 * Keys are owner internal name + "." + member name + descriptor. A java/javax member
 * missing here makes the calling method Unsupported with a precise report line.
 */
public final class ShimRegistry {

    private static final Set<String> TYPES = Set.of(
            "java/lang/Object",
            "java/lang/String",
            "java/lang/StringBuilder",
            "java/lang/System",
            "java/lang/Math",
            "java/lang/Integer",
            "java/io/PrintStream");

    private static final Map<String, ShimTarget> METHODS = Map.ofEntries(
            Map.entry("java/lang/Object.toString()Ljava/lang/String;", instance("toString")),
            Map.entry("java/lang/Object.hashCode()I", instance("hashCode")),
            Map.entry("java/lang/Object.equals(Ljava/lang/Object;)Z", instance("equals")),
            Map.entry("java/lang/String.length()I", instance("length")),
            Map.entry("java/lang/String.charAt(I)C", instance("charAt")),
            Map.entry("java/lang/String.isEmpty()Z", instance("isEmpty")),
            Map.entry("java/lang/String.equals(Ljava/lang/Object;)Z", instance("equals")),
            Map.entry("java/lang/String.hashCode()I", instance("hashCode")),
            Map.entry("java/lang/String.toString()Ljava/lang/String;", instance("toString")),
            Map.entry("java/io/PrintStream.println()V", instance("println")),
            Map.entry("java/io/PrintStream.println(I)V", instance("println")),
            Map.entry("java/io/PrintStream.println(J)V", instance("println")),
            Map.entry("java/io/PrintStream.println(F)V", instance("println")),
            Map.entry("java/io/PrintStream.println(D)V", instance("println")),
            Map.entry("java/io/PrintStream.println(C)V", instance("println")),
            Map.entry("java/io/PrintStream.println(Z)V", instance("println_Z")),
            Map.entry("java/io/PrintStream.println(Ljava/lang/String;)V", instance("println")),
            Map.entry("java/io/PrintStream.println(Ljava/lang/Object;)V", instance("println")),
            Map.entry("java/io/PrintStream.print(I)V", instance("print")),
            Map.entry("java/io/PrintStream.print(J)V", instance("print")),
            Map.entry("java/io/PrintStream.print(F)V", instance("print")),
            Map.entry("java/io/PrintStream.print(D)V", instance("print")),
            Map.entry("java/io/PrintStream.print(C)V", instance("print")),
            Map.entry("java/io/PrintStream.print(Z)V", instance("print_Z")),
            Map.entry("java/io/PrintStream.print(Ljava/lang/String;)V", instance("print")),
            Map.entry("java/io/PrintStream.print(Ljava/lang/Object;)V", instance("print")),
            Map.entry("java/lang/StringBuilder.append(I)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(J)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(F)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(D)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(C)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(Z)Ljava/lang/StringBuilder;", instance("append_Z")),
            Map.entry("java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(Ljava/lang/Object;)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.length()I", instance("length")),
            Map.entry("java/lang/StringBuilder.toString()Ljava/lang/String;", instance("toString")),
            Map.entry("java/lang/String.substring(I)Ljava/lang/String;", instance("substring")),
            Map.entry("java/lang/String.substring(II)Ljava/lang/String;", instance("substring")),
            Map.entry("java/lang/String.indexOf(I)I", instance("indexOf")),
            Map.entry("java/lang/String.indexOf(Ljava/lang/String;)I", instance("indexOf")),
            Map.entry("java/lang/String.startsWith(Ljava/lang/String;)Z", instance("startsWith")),
            Map.entry("java/lang/Math.abs(I)I", statics("abs")),
            Map.entry("java/lang/Math.abs(D)D", statics("abs")),
            Map.entry("java/lang/Math.max(II)I", statics("max")),
            Map.entry("java/lang/Math.min(II)I", statics("min")),
            Map.entry("java/lang/Math.sqrt(D)D", statics("sqrt")),
            Map.entry("java/lang/Integer.parseInt(Ljava/lang/String;)I", statics("parseInt")),
            Map.entry("java/lang/Integer.toString(I)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/System.arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V", statics("arraycopy")));

    private static final Map<String, ShimTarget> FIELDS = Map.ofEntries(
            Map.entry("java/lang/System.out Ljava/io/PrintStream;", statics("@out")),
            Map.entry("java/lang/System.err Ljava/io/PrintStream;", statics("err")));

    private ShimRegistry() {
    }

    public static boolean isShimType(String internalName) {
        return TYPES.contains(internalName);
    }

    public static Map<String, ShimTarget> methods() {
        return METHODS;
    }

    public static Map<String, ShimTarget> fields() {
        return FIELDS;
    }

    public static Optional<ShimTarget> method(String owner, String name, String descriptor) {
        return Optional.ofNullable(METHODS.get(owner + "." + name + descriptor));
    }

    public static Optional<ShimTarget> field(String owner, String name, String descriptor) {
        return Optional.ofNullable(FIELDS.get(owner + "." + name + " " + descriptor));
    }

    private static ShimTarget instance(String csName) {
        return new ShimTarget(csName, false);
    }

    private static ShimTarget statics(String csName) {
        return new ShimTarget(csName, true);
    }
}
