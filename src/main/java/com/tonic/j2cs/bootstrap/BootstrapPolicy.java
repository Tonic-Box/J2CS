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

    private static final Map<String, Entry> POLICY = Map.of(
            "java/lang/Boolean",
            new Entry(Set.of("<clinit>()V"), "java.lang.Boolean.native.cs"),
            "java/lang/Integer",
            new Entry(Set.of(
                    "<clinit>()V",
                    "valueOf(I)Ljava/lang/Integer;",
                    "toString()Ljava/lang/String;",
                    "parseInt(Ljava/lang/String;)I"),
                    "java.lang.Integer.native.cs"),
            "java/lang/Long",
            new Entry(Set.of(
                    "<clinit>()V",
                    "valueOf(J)Ljava/lang/Long;",
                    "toString()Ljava/lang/String;",
                    "parseLong(Ljava/lang/String;)J"),
                    "java.lang.Long.native.cs"),
            "java/lang/Short",
            new Entry(Set.of(
                    "<clinit>()V",
                    "valueOf(S)Ljava/lang/Short;",
                    "toString()Ljava/lang/String;"),
                    "java.lang.Short.native.cs"),
            "java/lang/Byte",
            new Entry(Set.of(
                    "<clinit>()V",
                    "valueOf(B)Ljava/lang/Byte;",
                    "toString()Ljava/lang/String;"),
                    "java.lang.Byte.native.cs"),
            "java/lang/Double",
            new Entry(Set.of(
                    "<clinit>()V",
                    "toString()Ljava/lang/String;",
                    "parseDouble(Ljava/lang/String;)D",
                    "doubleToRawLongBits(D)J",
                    "longBitsToDouble(J)D"),
                    "java.lang.Double.native.cs"),
            "java/lang/Float",
            new Entry(Set.of(
                    "<clinit>()V",
                    "toString()Ljava/lang/String;",
                    "parseFloat(Ljava/lang/String;)F",
                    "floatToRawIntBits(F)I",
                    "intBitsToFloat(I)F"),
                    "java.lang.Float.native.cs"),
            "java/lang/Character",
            new Entry(Set.of(
                    "<clinit>()V",
                    "valueOf(C)Ljava/lang/Character;",
                    "toString()Ljava/lang/String;"),
                    "java.lang.Character.native.cs"));

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
            if (entry != null) {
                fragments.add(entry.fragmentResource());
            }
        }
        return fragments;
    }
}
