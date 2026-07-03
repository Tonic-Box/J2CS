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
            new Entry(Set.of("<clinit>()V"), "java.lang.Boolean.native.cs"));

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
