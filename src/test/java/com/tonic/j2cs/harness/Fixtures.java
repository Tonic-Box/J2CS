package com.tonic.j2cs.harness;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Materializes fixture Java sources from test resources into a working directory.
 */
public final class Fixtures {

    private Fixtures() {
    }

    public static Path materialize(String name, Path targetDir) throws IOException {
        String resource = "/fixtures/" + name + "/" + name + ".java";
        try (InputStream in = Fixtures.class.getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalArgumentException("fixture resource missing: " + resource);
            }
            Files.createDirectories(targetDir);
            Path target = targetDir.resolve(name + ".java");
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            return target;
        }
    }

    public static void deleteRecursively(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            return;
        }
        try (var files = Files.walk(dir)) {
            files.sorted(java.util.Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.delete(p);
                } catch (IOException e) {
                    throw new RuntimeException("failed to delete " + p, e);
                }
            });
        }
    }
}
