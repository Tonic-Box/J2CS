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

/**
 * Drift guard for ShimRegistry.SEALED_SHIM_CLASSES: it must list exactly the shim sources whose C#
 * form is a sealed class (declared {@code public sealed class} in javacompat). isExtendable relies
 * on it to auto-extend the non-sealed shim classes while excluding the sealed ones, which cannot be
 * a C# base.
 */
class ShimSealedDriftTest {

    private static final Path SHIM_DIR = Path.of("javacompat");

    @Test
    void sealedSetMatchesSealedSourceFiles() throws IOException {
        Set<String> declared;
        try (Stream<Path> list = Files.list(SHIM_DIR)) {
            declared = list.filter(p -> p.getFileName().toString().endsWith(".cs"))
                    .filter(ShimSealedDriftTest::declaresSealedClass)
                    .map(p -> {
                        String file = p.getFileName().toString();
                        return file.substring(0, file.length() - ".cs".length()).replace('.', '/');
                    })
                    .collect(Collectors.toCollection(TreeSet::new));
        }
        assertEquals(declared, new TreeSet<>(ShimRegistry.sealedShimClasses()),
                "ShimRegistry.SEALED_SHIM_CLASSES must list exactly the shim .cs files declaring a public sealed class");
    }

    private static boolean declaresSealedClass(Path file) {
        try {
            return Files.readAllLines(file).stream().anyMatch(l -> l.contains("public sealed class"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
