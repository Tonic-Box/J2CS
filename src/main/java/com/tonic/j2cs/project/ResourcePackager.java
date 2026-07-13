package com.tonic.j2cs.project;

import com.tonic.j2cs.J2csException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Extracts the input jar's non-class resources into the generated solution's resources/ directory,
 * preserving each entry's path so they resolve under the transpiled runtime's classpath root. The
 * transpiler's class loader discards non-class entries, so the jar is re-opened here (as with the
 * native-library packager). Only class files are skipped (they are transpiled); everything else,
 * including bundled native libraries, is a classpath resource — engine loaders such as jME's
 * NativeLibraryLoader locate their native libs via getResourceAsStream before extracting and loading
 * them. Runtime lookups (Class/ClassLoader.getResource*) read from this directory.
 */
public final class ResourcePackager {

    private static final String TARGET_DIR = "resources";

    /** Extracts resources into {@code appDir/resources}; returns true if at least one was written. */
    public boolean copy(Path appDir, Path inputJar) {
        if (inputJar == null || !Files.isRegularFile(inputJar) || !isArchive(inputJar)) {
            return false;
        }
        Path targetDir = appDir.resolve(TARGET_DIR).normalize();
        boolean wroteAny = false;
        try (JarFile jar = new JarFile(inputJar.toFile())) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() || !isResource(entry.getName())) {
                    continue;
                }
                Path dest = targetDir.resolve(entry.getName()).normalize();
                // Reject entries that escape the target directory (zip-slip).
                if (!dest.startsWith(targetDir)) {
                    continue;
                }
                Files.createDirectories(dest.getParent());
                try (InputStream in = jar.getInputStream(entry)) {
                    Files.copy(in, dest, StandardCopyOption.REPLACE_EXISTING);
                }
                wroteAny = true;
            }
        } catch (IOException e) {
            throw new J2csException("failed to extract resources from " + inputJar + ": " + e.getMessage(), e);
        }
        return wroteAny;
    }

    private static boolean isArchive(Path path) {
        String lower = path.getFileName().toString().toLowerCase(Locale.ROOT);
        return lower.endsWith(".jar") || lower.endsWith(".zip");
    }

    private static boolean isResource(String name) {
        return !name.toLowerCase(Locale.ROOT).endsWith(".class");
    }
}
