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
            "java/io/PrintStream",
            "java/lang/Throwable",
            "java/lang/Exception",
            "java/lang/RuntimeException",
            "java/lang/Error",
            "java/lang/NullPointerException",
            "java/lang/ArithmeticException",
            "java/lang/ClassCastException",
            "java/lang/IndexOutOfBoundsException",
            "java/lang/ArrayIndexOutOfBoundsException",
            "java/lang/ArrayStoreException",
            "java/lang/IllegalArgumentException",
            "java/lang/NumberFormatException",
            "java/lang/IllegalStateException",
            "java/util/Objects",
            "java/lang/Number",
            "java/lang/Long",
            "java/lang/Double",
            "java/lang/Float",
            "java/lang/Boolean",
            "java/lang/Character",
            "java/lang/Short",
            "java/lang/Byte",
            "java/util/Iterable",
            "java/util/Iterator",
            "java/util/Collection",
            "java/util/List",
            "java/util/ArrayList");

    private static final Map<String, String> SHIM_SUPERS = Map.ofEntries(
            Map.entry("java/lang/String", "java/lang/Object"),
            Map.entry("java/lang/StringBuilder", "java/lang/Object"),
            Map.entry("java/lang/System", "java/lang/Object"),
            Map.entry("java/lang/Math", "java/lang/Object"),
            Map.entry("java/lang/Number", "java/lang/Object"),
            Map.entry("java/lang/Integer", "java/lang/Number"),
            Map.entry("java/lang/Long", "java/lang/Number"),
            Map.entry("java/lang/Double", "java/lang/Number"),
            Map.entry("java/lang/Float", "java/lang/Number"),
            Map.entry("java/lang/Short", "java/lang/Number"),
            Map.entry("java/lang/Byte", "java/lang/Number"),
            Map.entry("java/lang/Boolean", "java/lang/Object"),
            Map.entry("java/lang/Character", "java/lang/Object"),
            Map.entry("java/util/Iterable", "java/lang/Object"),
            Map.entry("java/util/Iterator", "java/lang/Object"),
            Map.entry("java/util/Collection", "java/util/Iterable"),
            Map.entry("java/util/List", "java/util/Collection"),
            Map.entry("java/util/ArrayList", "java/util/List"),
            Map.entry("java/io/PrintStream", "java/lang/Object"),
            Map.entry("java/lang/Throwable", "java/lang/Object"),
            Map.entry("java/lang/Exception", "java/lang/Throwable"),
            Map.entry("java/lang/RuntimeException", "java/lang/Exception"),
            Map.entry("java/lang/Error", "java/lang/Throwable"),
            Map.entry("java/lang/NullPointerException", "java/lang/RuntimeException"),
            Map.entry("java/lang/ArithmeticException", "java/lang/RuntimeException"),
            Map.entry("java/lang/ClassCastException", "java/lang/RuntimeException"),
            Map.entry("java/lang/IndexOutOfBoundsException", "java/lang/RuntimeException"),
            Map.entry("java/lang/ArrayIndexOutOfBoundsException", "java/lang/IndexOutOfBoundsException"),
            Map.entry("java/lang/ArrayStoreException", "java/lang/RuntimeException"),
            Map.entry("java/lang/IllegalArgumentException", "java/lang/RuntimeException"),
            Map.entry("java/lang/NumberFormatException", "java/lang/IllegalArgumentException"),
            Map.entry("java/lang/IllegalStateException", "java/lang/RuntimeException"));

    private static final Set<String> EXTENDABLE = Set.of(
            "java/lang/Throwable",
            "java/lang/Exception",
            "java/lang/RuntimeException",
            "java/lang/Error",
            "java/lang/NullPointerException",
            "java/lang/ArithmeticException",
            "java/lang/ClassCastException",
            "java/lang/IndexOutOfBoundsException",
            "java/lang/ArrayIndexOutOfBoundsException",
            "java/lang/ArrayStoreException",
            "java/lang/IllegalArgumentException",
            "java/lang/NumberFormatException",
            "java/lang/IllegalStateException");

    public static final Map<String, String> EXTENDABLE_VIRTUALS = Map.of(
            "getMessage()Ljava/lang/String;", "getMessage");

    public static final Set<String> EXTENDABLE_MEMBER_NAMES = Set.of(
            "getMessage", "JavaClassName", "message", "__origin");

    public record WalkResult(String declaringInternal, ShimTarget target) {
    }

    private static final Map<String, ShimTarget> METHODS = Map.<String, ShimTarget>ofEntries(
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
            Map.entry("java/lang/Integer.valueOf(I)Ljava/lang/Integer;", statics("valueOf")),
            Map.entry("java/lang/Integer.compareTo(Ljava/lang/Integer;)I", instance("compareTo")),
            Map.entry("java/lang/Long.valueOf(J)Ljava/lang/Long;", statics("valueOf")),
            Map.entry("java/lang/Long.parseLong(Ljava/lang/String;)J", statics("parseLong")),
            Map.entry("java/lang/Long.toString(J)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Long.compareTo(Ljava/lang/Long;)I", instance("compareTo")),
            Map.entry("java/lang/Double.valueOf(D)Ljava/lang/Double;", statics("valueOf")),
            Map.entry("java/lang/Double.parseDouble(Ljava/lang/String;)D", statics("parseDouble")),
            Map.entry("java/lang/Double.toString(D)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Double.compareTo(Ljava/lang/Double;)I", instance("compareTo")),
            Map.entry("java/lang/Float.valueOf(F)Ljava/lang/Float;", statics("valueOf")),
            Map.entry("java/lang/Float.parseFloat(Ljava/lang/String;)F", statics("parseFloat")),
            Map.entry("java/lang/Float.toString(F)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Float.compareTo(Ljava/lang/Float;)I", instance("compareTo")),
            Map.entry("java/lang/Short.valueOf(S)Ljava/lang/Short;", statics("valueOf")),
            Map.entry("java/lang/Short.toString(S)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Short.compareTo(Ljava/lang/Short;)I", instance("compareTo")),
            Map.entry("java/lang/Byte.valueOf(B)Ljava/lang/Byte;", statics("valueOf")),
            Map.entry("java/lang/Byte.toString(B)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Byte.compareTo(Ljava/lang/Byte;)I", instance("compareTo")),
            Map.entry("java/lang/Boolean.valueOf(Z)Ljava/lang/Boolean;", statics("valueOf")),
            Map.entry("java/lang/Boolean.parseBoolean(Ljava/lang/String;)Z", statics("parseBoolean")),
            Map.entry("java/lang/Boolean.toString(Z)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Boolean.booleanValue()Z", instance("booleanValue")),
            Map.entry("java/lang/Boolean.compareTo(Ljava/lang/Boolean;)I", instance("compareTo")),
            Map.entry("java/lang/Character.valueOf(C)Ljava/lang/Character;", statics("valueOf")),
            Map.entry("java/lang/Character.toString(C)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Character.charValue()C", instance("charValue")),
            Map.entry("java/lang/Character.compareTo(Ljava/lang/Character;)I", instance("compareTo")),
            Map.entry("java/lang/Number.intValue()I", instance("intValue")),
            Map.entry("java/lang/Number.longValue()J", instance("longValue")),
            Map.entry("java/lang/Number.floatValue()F", instance("floatValue")),
            Map.entry("java/lang/Number.doubleValue()D", instance("doubleValue")),
            Map.entry("java/lang/Number.shortValue()S", instance("shortValue")),
            Map.entry("java/lang/Number.byteValue()B", instance("byteValue")),
            Map.entry("java/lang/System.arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V", statics("arraycopy")),
            Map.entry("java/lang/Throwable.getMessage()Ljava/lang/String;", instance("getMessage")),
            Map.entry("java/lang/Throwable.toString()Ljava/lang/String;", instance("toString")),
            Map.entry("java/util/Objects.requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;", statics("requireNonNull")),
            Map.entry("java/util/Iterable.iterator()Ljava/util/Iterator;", instance("iterator")),
            Map.entry("java/util/Iterator.hasNext()Z", instance("hasNext")),
            Map.entry("java/util/Iterator.next()Ljava/lang/Object;", instance("next")),
            Map.entry("java/util/Collection.add(Ljava/lang/Object;)Z", instance("add")),
            Map.entry("java/util/Collection.size()I", instance("size")),
            Map.entry("java/util/Collection.isEmpty()Z", instance("isEmpty")),
            Map.entry("java/util/Collection.contains(Ljava/lang/Object;)Z", instance("contains")),
            Map.entry("java/util/Collection.remove(Ljava/lang/Object;)Z", instance("remove")),
            Map.entry("java/util/List.get(I)Ljava/lang/Object;", instance("get")),
            Map.entry("java/util/List.set(ILjava/lang/Object;)Ljava/lang/Object;", instance("set")),
            Map.entry("java/util/List.add(ILjava/lang/Object;)V", instance("add")),
            Map.entry("java/util/List.remove(I)Ljava/lang/Object;", instance("remove")),
            Map.entry("java/util/List.indexOf(Ljava/lang/Object;)I", instance("indexOf")));

    private static final Map<String, ShimTarget> FIELDS = Map.ofEntries(
            Map.entry("java/lang/System.out Ljava/io/PrintStream;", statics("@out")),
            Map.entry("java/lang/System.err Ljava/io/PrintStream;", statics("err")));

    private ShimRegistry() {
    }

    public static boolean isShimType(String internalName) {
        return TYPES.contains(internalName);
    }

    public static boolean isExtendable(String internalName) {
        return EXTENDABLE.contains(internalName);
    }

    public static String superOf(String internalName) {
        return SHIM_SUPERS.get(internalName);
    }

    public static Optional<WalkResult> resolveMethodWalking(String owner, String name, String descriptor) {
        String current = owner;
        while (current != null) {
            Optional<ShimTarget> target = method(current, name, descriptor);
            if (target.isPresent()) {
                return Optional.of(new WalkResult(current, target.get()));
            }
            current = SHIM_SUPERS.get(current);
        }
        return Optional.empty();
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
