package com.tonic.j2cs.project;

import com.tonic.j2cs.J2csException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Extracts native shared libraries bundled in the input jar into the generated solution's
 * nativelibs/ directory. The transpiler's class loader discards non-class jar entries, so the
 * jar is re-opened here. Entries are keyed by base filename (first wins); returned paths are
 * relative to the App project and feed the csproj Content group.
 */
public final class NativeLibPackager {

    private static final String TARGET_DIR = "nativelibs";

    public List<String> copy(Path appDir, Path inputJar) {
        List<String> copied = new ArrayList<>();
        if (inputJar == null || !Files.isRegularFile(inputJar) || !isArchive(inputJar)) {
            return copied;
        }
        Path targetDir = appDir.resolve(TARGET_DIR);
        Set<String> seen = new HashSet<>();
        try (JarFile jar = new JarFile(inputJar.toFile())) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() || !isNativeLib(entry.getName())) {
                    continue;
                }
                String baseName = entry.getName().substring(entry.getName().lastIndexOf('/') + 1);
                if (!seen.add(baseName)) {
                    continue;
                }
                Files.createDirectories(targetDir);
                try (InputStream in = jar.getInputStream(entry)) {
                    Files.copy(in, targetDir.resolve(baseName), StandardCopyOption.REPLACE_EXISTING);
                }
                copied.add(TARGET_DIR + "/" + baseName);
            }
        } catch (IOException e) {
            throw new J2csException("failed to extract native libraries from " + inputJar + ": " + e.getMessage(), e);
        }
        return copied;
    }

    private static boolean isArchive(Path path) {
        String lower = path.getFileName().toString().toLowerCase(Locale.ROOT);
        return lower.endsWith(".jar") || lower.endsWith(".zip");
    }

    private static boolean isNativeLib(String name) {
        String lower = name.toLowerCase(Locale.ROOT);
        return lower.endsWith(".dll") || lower.endsWith(".so") || lower.endsWith(".dylib") || lower.endsWith(".jnilib");
    }
}
