package com.tonic.j2cs.unit;

import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.j2cs.shims.ShimTarget;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Drift guard: every ShimRegistry entry's C# member name must appear textually in the
 * corresponding shim source file, so the registry cannot silently outrun the shim.
 */
class ShimRegistryDriftTest {

    private final Map<String, String> sources = new HashMap<>();

    @Test
    void everyRegisteredMethodExistsInShimSource() throws IOException {
        for (Map.Entry<String, ShimTarget> entry : ShimRegistry.methods().entrySet()) {
            assertMemberPresent(entry.getKey(), entry.getValue());
        }
    }

    @Test
    void everyRegisteredFieldExistsInShimSource() throws IOException {
        for (Map.Entry<String, ShimTarget> entry : ShimRegistry.fields().entrySet()) {
            assertMemberPresent(entry.getKey(), entry.getValue());
        }
    }

    private void assertMemberPresent(String key, ShimTarget target) throws IOException {
        String owner = key.substring(0, key.indexOf('.'));
        String source = shimSource(owner);
        assertTrue(source.contains(target.csMemberName()),
                "shim source for " + owner + " lacks member '" + target.csMemberName()
                        + "' required by registry entry " + key);
    }

    private String shimSource(String ownerInternalName) throws IOException {
        String cached = sources.get(ownerInternalName);
        if (cached != null) {
            return cached;
        }
        String resource = "/javacompat/" + ownerInternalName.replace('/', '.') + ".cs";
        try (InputStream in = ShimRegistryDriftTest.class.getResourceAsStream(resource)) {
            assertNotNull(in, "missing shim source resource: " + resource);
            String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            sources.put(ownerInternalName, text);
            return text;
        }
    }
}
