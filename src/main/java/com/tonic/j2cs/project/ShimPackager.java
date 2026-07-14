package com.tonic.j2cs.project;

import com.tonic.j2cs.J2csException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Copies the JavaCompat shim sources from classpath resources into a generated solution.
 * The manifest.txt index exists because classpath resources cannot be listed portably.
 */
public final class ShimPackager {

    private static final String RESOURCE_ROOT = "/javacompat/";

    public void copyShim(Path targetDir, java.util.Set<String> bootstrappedInternal, boolean usesGui) {
        try {
            Files.createDirectories(targetDir);
            for (String fileName : readManifest()) {
                if (bootstrappedInternal.contains(internalNameOf(fileName))) {
                    continue;
                }
                if (!usesGui && isGuiShim(fileName)) {
                    continue;
                }
                try (InputStream in = openResource(RESOURCE_ROOT + fileName)) {
                    Files.copy(in, targetDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            throw new J2csException("failed to copy shim sources: " + e.getMessage(), e);
        }
    }

    private static boolean isGuiShim(String fileName) {
        return fileName.startsWith("javax.swing.")
                || fileName.startsWith("java.awt.")
                || fileName.startsWith("javax.imageio.");
    }

    private static String internalNameOf(String fileName) {
        String dotted = fileName.endsWith(".cs") ? fileName.substring(0, fileName.length() - 3) : fileName;
        return dotted.replace('.', '/');
    }

    private java.util.List<String> readManifest() throws IOException {
        try (InputStream in = openResource(RESOURCE_ROOT + "manifest.txt")) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8).lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(java.util.stream.Collectors.toList());
        }
    }

    private InputStream openResource(String path) {
        InputStream in = ShimPackager.class.getResourceAsStream(path);
        if (in == null) {
            throw new J2csException("shim resource missing from classpath: " + path);
        }
        return in;
    }
}
