package com.tonic.j2cs.unit;

import com.tonic.j2cs.bootstrap.BootstrapPolicy;
import com.tonic.parser.ClassPool;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Guards internal consistency of the bootstrap surface: fragment files exist and declare the
 * owning partial class, no fragment is orphaned, every allowlisted class is loadable, every
 * policy owner is on the allowlist, and every suppressed key is well-formed. Turns a late
 * dotnet-build failure in one run into a fast, precise unit failure.
 */
class BootstrapDriftTest {

    @Test
    void fragmentsExistAndDeclareOwningPartial() throws IOException {
        for (Map.Entry<String, String> entry : BootstrapPolicy.fragmentByClass().entrySet()) {
            String internal = entry.getKey();
            String simple = internal.substring(internal.lastIndexOf('/') + 1);
            String resource = "/javacompat/native/" + entry.getValue();
            try (InputStream in = BootstrapDriftTest.class.getResourceAsStream(resource)) {
                assertNotNull(in, "native fragment missing from classpath: " + resource);
                String body = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                assertTrue(body.contains("partial class " + simple),
                        resource + " must declare 'partial class " + simple + "'");
            }
        }
    }

    @Test
    void noOrphanFragments() throws IOException {
        Path dir = Path.of("javacompat", "native");
        if (!Files.isDirectory(dir)) {
            fail("javacompat/native directory not found at " + dir.toAbsolutePath());
        }
        Set<String> referenced = Set.copyOf(BootstrapPolicy.fragmentByClass().values());
        List<String> files;
        try (var stream = Files.list(dir)) {
            files = stream.map(p -> p.getFileName().toString())
                    .filter(n -> n.endsWith(".native.cs"))
                    .collect(Collectors.toList());
        }
        for (String file : files) {
            assertTrue(referenced.contains(file),
                    "orphan native fragment (no BootstrapPolicy entry references it): " + file);
        }
    }

    @Test
    void allowlistClassesAreLoadable() {
        ClassPool pool = new ClassPool(true);
        for (String internal : BootstrapPolicy.bootstrappable()) {
            try {
                pool.loadPlatformClass(internal + ".class");
            } catch (IOException e) {
                fail("allowlisted class not loadable from the platform image: " + internal);
            }
        }
    }

    @Test
    void policyOwnersAreAllowlistedWithWellFormedSuppressions() {
        for (String owner : BootstrapPolicy.policyOwners()) {
            assertTrue(BootstrapPolicy.bootstrappable().contains(owner),
                    "policy owner not on the allowlist: " + owner);
            for (String sig : BootstrapPolicy.suppressedMethodSignatures(owner)) {
                assertTrue(sig.indexOf('(') > 0 && sig.indexOf(')') > sig.indexOf('('),
                        "malformed suppressed method signature for " + owner + ": " + sig);
            }
        }
    }
}
