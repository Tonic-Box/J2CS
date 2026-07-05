package com.tonic.j2cs.unit;

import com.tonic.j2cs.shims.ShimRegistry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Drift guard for the shim packaging index: every shim source must be listed in manifest.txt
 * (ShimPackager copies only listed files, so an unlisted shim is silently dropped from the
 * generated solution) and every listed file must exist.
 */
class ShimManifestDriftTest {

    private static final Path SHIM_DIR = Path.of("javacompat");

    @Test
    void manifestMatchesShimSourceFiles() throws IOException {
        Set<String> manifest = readManifest();
        Set<String> files;
        try (Stream<Path> list = Files.list(SHIM_DIR)) {
            files = list.map(p -> p.getFileName().toString())
                    .filter(n -> n.endsWith(".cs"))
                    .collect(Collectors.toCollection(TreeSet::new));
        }
        assertEquals(files, manifest,
                "javacompat/manifest.txt must list exactly the .cs files in javacompat/");
    }

    @Test
    void everyShimTypeHasManifestEntry() throws IOException {
        Set<String> manifest = readManifest();
        for (String internalName : ShimRegistry.types()) {
            String fileName = internalName.replace('/', '.') + ".cs";
            assertTrue(manifest.contains(fileName),
                    "shim type " + internalName + " has no manifest entry " + fileName);
        }
    }

    private static Set<String> readManifest() throws IOException {
        return Files.readAllLines(SHIM_DIR.resolve("manifest.txt")).stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
