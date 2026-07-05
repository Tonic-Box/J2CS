package com.tonic.j2cs.unit;

import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.j2cs.shims.ShimTarget;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Drift guard: every ShimRegistry entry's C# member must be declared in the corresponding shim
 * source file, so the registry cannot silently outrun the shim. Methods match the name at a
 * non-member call/declaration position followed by '('; fields match a type-preceded declaration.
 */
class ShimRegistryDriftTest {

    private final Map<String, String> sources = new HashMap<>();

    @Test
    void everyRegisteredMethodExistsInShimSource() throws IOException {
        for (Map.Entry<String, ShimTarget> entry : ShimRegistry.methods().entrySet()) {
            String name = entry.getValue().csMemberName();
            assertMemberPresent(entry.getKey(), name,
                    Pattern.compile("(?<![\\w@.])" + Pattern.quote(name) + "\\s*\\("));
        }
    }

    @Test
    void everyRegisteredFieldExistsInShimSource() throws IOException {
        for (Map.Entry<String, ShimTarget> entry : ShimRegistry.fields().entrySet()) {
            String name = entry.getValue().csMemberName();
            assertMemberPresent(entry.getKey(), name,
                    Pattern.compile("\\w\\s+" + Pattern.quote(name) + "\\s*[;=]"));
        }
    }

    private void assertMemberPresent(String key, String name, Pattern declaration) throws IOException {
        String owner = key.substring(0, key.indexOf('.'));
        String source = shimSource(owner);
        assertTrue(declaration.matcher(source).find(),
                "shim source for " + owner + " lacks member '" + name
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
