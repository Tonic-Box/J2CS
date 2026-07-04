package com.tonic.j2cs.bootstrap;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Per-class bootstrap policy: which methods of a generated JDK class are replaced by a
 * hand-written native fragment (because they bottom out in native calls the transpiler cannot
 * follow), and which fragment source supplies the replacements.
 */
public final class BootstrapPolicy {

    private record Entry(Set<String> suppressedMethodKeys, String fragmentResource) {
    }

    private static final Map<String, Entry> POLICY = Map.ofEntries(
            Map.entry("java/lang/Object",
                    new Entry(Set.of("hashCode()I", "toString()Ljava/lang/String;"),
                            "java.lang.Object.native.cs")),
            Map.entry("java/lang/Throwable",
                    new Entry(Set.of(
                            "<clinit>()V",
                            "<init>()V",
                            "<init>(Ljava/lang/String;)V",
                            "<init>(Ljava/lang/String;Ljava/lang/Throwable;)V",
                            "<init>(Ljava/lang/Throwable;)V",
                            "<init>(Ljava/lang/String;Ljava/lang/Throwable;ZZ)V",
                            "fillInStackTrace()Ljava/lang/Throwable;",
                            "fillInStackTrace(I)Ljava/lang/Throwable;",
                            "toString()Ljava/lang/String;"),
                            "java.lang.Throwable.native.cs")),
            Map.entry("java/lang/NullPointerException",
                    new Entry(Set.of(
                            "getMessage()Ljava/lang/String;",
                            "getExtendedNPEMessage()Ljava/lang/String;",
                            "fillInStackTrace()Ljava/lang/Throwable;"),
                            null)),
            Map.entry("java/lang/Boolean",
                    new Entry(Set.of("<clinit>()V"), "java.lang.Boolean.native.cs")),
            Map.entry("java/lang/Integer",
                    new Entry(Set.of(
                            "<clinit>()V",
                            "valueOf(I)Ljava/lang/Integer;",
                            "toString()Ljava/lang/String;",
                            "parseInt(Ljava/lang/String;)I"),
                            "java.lang.Integer.native.cs")),
            Map.entry("java/lang/Long",
                    new Entry(Set.of(
                            "<clinit>()V",
                            "valueOf(J)Ljava/lang/Long;",
                            "toString()Ljava/lang/String;",
                            "parseLong(Ljava/lang/String;)J"),
                            "java.lang.Long.native.cs")),
            Map.entry("java/lang/Short",
                    new Entry(Set.of(
                            "<clinit>()V",
                            "valueOf(S)Ljava/lang/Short;",
                            "toString()Ljava/lang/String;"),
                            "java.lang.Short.native.cs")),
            Map.entry("java/lang/Byte",
                    new Entry(Set.of(
                            "<clinit>()V",
                            "valueOf(B)Ljava/lang/Byte;",
                            "toString()Ljava/lang/String;"),
                            "java.lang.Byte.native.cs")),
            Map.entry("java/lang/Double",
                    new Entry(Set.of(
                            "<clinit>()V",
                            "toString()Ljava/lang/String;",
                            "parseDouble(Ljava/lang/String;)D",
                            "doubleToRawLongBits(D)J",
                            "longBitsToDouble(J)D"),
                            "java.lang.Double.native.cs")),
            Map.entry("java/lang/Float",
                    new Entry(Set.of(
                            "<clinit>()V",
                            "toString()Ljava/lang/String;",
                            "parseFloat(Ljava/lang/String;)F",
                            "floatToRawIntBits(F)I",
                            "intBitsToFloat(I)F"),
                            "java.lang.Float.native.cs")),
            Map.entry("java/lang/Character",
                    new Entry(Set.of(
                            "<clinit>()V",
                            "valueOf(C)Ljava/lang/Character;",
                            "toString()Ljava/lang/String;"),
                            "java.lang.Character.native.cs")));

    private BootstrapPolicy() {
    }

    public static Set<String> suppressedKeys(Set<String> bootstrapped) {
        Set<String> keys = new LinkedHashSet<>();
        for (String internal : bootstrapped) {
            Entry entry = POLICY.get(internal);
            if (entry != null) {
                for (String sig : entry.suppressedMethodKeys()) {
                    keys.add(internal + "." + sig);
                }
            }
        }
        return keys;
    }

    public static List<String> nativeFragments(Set<String> bootstrapped) {
        List<String> fragments = new java.util.ArrayList<>();
        for (String internal : bootstrapped) {
            Entry entry = POLICY.get(internal);
            if (entry != null && entry.fragmentResource() != null) {
                fragments.add(entry.fragmentResource());
            }
        }
        return fragments;
    }
}
