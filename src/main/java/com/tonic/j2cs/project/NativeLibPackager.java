package com.tonic.j2cs.project;

import com.tonic.j2cs.J2csException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Extracts native shared libraries bundled in the input jar into the generated solution's
 * nativelibs/ directory. The transpiler's class loader discards non-class jar entries, so the
 * jar is re-opened here. Multi-platform jars (e.g. LWJGL) bundle the same base filename for
 * several os/arch combinations; entries are grouped by base filename and the one matching the
 * host platform is chosen (falling back to first-seen when nothing classifies). Returned paths
 * are relative to the App project and feed the csproj Content group.
 */
public final class NativeLibPackager {

    private static final String TARGET_DIR = "nativelibs";

    public List<String> copy(Path appDir, Path inputJar) {
        List<String> copied = new ArrayList<>();
        if (inputJar == null || !Files.isRegularFile(inputJar) || !isArchive(inputJar)) {
            return copied;
        }
        Path targetDir = appDir.resolve(TARGET_DIR);
        try (JarFile jar = new JarFile(inputJar.toFile())) {
            Map<String, JarEntry> chosen = new LinkedHashMap<>();
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() || !isNativeLib(entry.getName())) {
                    continue;
                }
                String baseName = entry.getName().substring(entry.getName().lastIndexOf('/') + 1);
                JarEntry existing = chosen.get(baseName);
                if (existing == null || platformRank(entry.getName()) < platformRank(existing.getName())) {
                    chosen.put(baseName, entry);
                }
            }
            for (Map.Entry<String, JarEntry> e : chosen.entrySet()) {
                Files.createDirectories(targetDir);
                try (InputStream in = jar.getInputStream(e.getValue())) {
                    Files.copy(in, targetDir.resolve(e.getKey()), StandardCopyOption.REPLACE_EXISTING);
                }
                copied.add(TARGET_DIR + "/" + e.getKey());
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

    private static final boolean HOST_WINDOWS;
    private static final boolean HOST_MAC;
    private static final boolean HOST_ARM64;

    static {
        String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        String arch = System.getProperty("os.arch", "").toLowerCase(Locale.ROOT);
        HOST_WINDOWS = os.contains("win");
        HOST_MAC = os.contains("mac") || os.contains("darwin") || os.contains("osx");
        HOST_ARM64 = arch.contains("aarch64") || arch.contains("arm64");
    }

    /**
     * Ranks a jar entry path by how well its embedded os/arch tokens match the host that this
     * transpile targets: 0 = os and arch both match (or the path carries no platform tokens at
     * all, e.g. a single-arch jar), 1 = partial match, 2 = a classified mismatch. Lower wins.
     */
    private static int platformRank(String path) {
        String lower = path.toLowerCase(Locale.ROOT);
        String[] segments = lower.split("[/\\\\]");
        Boolean osWindows = null, osMac = null;
        Boolean arm64 = null, is32 = null;
        for (String seg : segments) {
            switch (seg) {
                case "windows", "win32", "win" -> osWindows = true;
                case "linux" -> {
                    osWindows = false;
                    osMac = false;
                }
                case "macos", "macosx", "osx", "darwin" -> {
                    osMac = true;
                    osWindows = false;
                }
            }
            switch (seg) {
                case "arm64", "aarch64" -> arm64 = true;
                case "x64", "x86_64", "x86-64", "amd64", "x8664" -> {
                    arm64 = false;
                    is32 = false;
                }
                case "x86", "i386", "i586", "i686", "win32" -> is32 = true;
                case "arm", "arm32", "armhf", "armel" -> {
                    arm64 = false;
                    is32 = true;
                }
            }
        }
        boolean osKnown = osWindows != null || osMac != null;
        boolean osMatch = osKnown && matchesHostOs(osWindows, osMac);
        boolean archKnown = arm64 != null || is32 != null;
        boolean archMatch = archKnown && matchesHostArch(arm64, is32);
        if (!osKnown && !archKnown) {
            return 0;
        }
        if ((!osKnown || osMatch) && (!archKnown || archMatch)) {
            return (osMatch && archMatch) ? 0 : 1;
        }
        return 2;
    }

    private static boolean matchesHostOs(Boolean osWindows, Boolean osMac) {
        if (Boolean.TRUE.equals(osWindows)) return HOST_WINDOWS;
        if (Boolean.TRUE.equals(osMac)) return HOST_MAC;
        return !HOST_WINDOWS && !HOST_MAC;
    }

    private static boolean matchesHostArch(Boolean arm64, Boolean is32) {
        if (Boolean.TRUE.equals(is32)) return false;
        if (Boolean.TRUE.equals(arm64)) return HOST_ARM64;
        return !HOST_ARM64;
    }
}
