package com.tonic.j2cs.emit;

import com.tonic.j2cs.naming.CsNamer;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Collects the synthetic C# classes generated for lambda call sites. Keyed by enclosing class
 * plus the call site's constant-pool index, which is the correct dedup unit: one InvokeDynamic
 * constant may be referenced by several methods of a class, while distinct lambdas always get
 * distinct indexes.
 */
public final class SyntheticClasses {

    private record Entry(String dottedName, String source) {
    }

    private final Map<String, Entry> byKey = new LinkedHashMap<>();
    private final Map<String, String> claimedNames = new LinkedHashMap<>();
    private final Set<String> takenNames = new LinkedHashSet<>();

    public String claimClassName(String enclosingInternalName, int cpIndex) {
        String key = enclosingInternalName + "#" + cpIndex;
        String existing = claimedNames.get(key);
        if (existing != null) {
            return existing;
        }
        String base = "Lambda_" + CsNamer.identifier(enclosingInternalName.replace('/', '_')) + "_" + cpIndex;
        String candidate = base;
        int n = 2;
        while (takenNames.contains(candidate)) {
            candidate = base + "__" + n++;
        }
        takenNames.add(candidate);
        claimedNames.put(key, candidate);
        return candidate;
    }

    public boolean isRegistered(String enclosingInternalName, int cpIndex) {
        return byKey.containsKey(enclosingInternalName + "#" + cpIndex);
    }

    public String fqcnOf(String simpleName) {
        return "global::j2cs.synthetic." + simpleName;
    }

    public void register(String enclosingInternalName, int cpIndex, String simpleName, String source) {
        byKey.put(enclosingInternalName + "#" + cpIndex,
                new Entry("j2cs.synthetic." + simpleName, source));
    }

    public Map<String, String> files() {
        Map<String, String> files = new LinkedHashMap<>();
        for (Entry entry : byKey.values()) {
            files.put(entry.dottedName(), entry.source());
        }
        return files;
    }
}
