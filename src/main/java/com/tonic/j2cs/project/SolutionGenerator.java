package com.tonic.j2cs.project;

import com.tonic.j2cs.J2csException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Writes the generated C# solution: &lt;name&gt;.csproj, Program.cs, gen/*.cs, stubs/*.cs, and the
 * copied shim under javacompat/. The project, its directory, and the produced assembly are all
 * named after the input's base name so distinct inputs yield distinctly named exes. Returns the
 * project directory.
 */
public final class SolutionGenerator {

    private static final String[] GENERATED_DIRS = {"gen", "stubs", "javacompat", "native", "nativelibs", "resources"};

    private final ShimPackager shimPackager = new ShimPackager();
    private final NativeLibPackager nativeLibPackager = new NativeLibPackager();
    private final ResourcePackager resourcePackager = new ResourcePackager();

    public Path generate(Path outDir, GeneratedSolution solution, Path inputJar) {
        String appName = deriveAppName(inputJar);
        Path appDir = outDir.resolve(appName);
        try {
            Files.createDirectories(appDir);
            for (String generated : GENERATED_DIRS) {
                deleteRecursively(appDir.resolve(generated));
            }
            java.util.List<String> nativeLibs = nativeLibPackager.copy(appDir, inputJar);
            boolean hasResources = resourcePackager.copy(appDir, inputJar);
            Files.writeString(appDir.resolve(appName + ".csproj"),
                    CsprojTemplate.csproj(appName, solution.usesGui(), nativeLibs, hasResources));
            Files.writeString(appDir.resolve("Program.cs"), solution.programCs());
            writeSources(appDir.resolve("gen"), solution.genFiles());
            writeSources(appDir.resolve("stubs"), solution.stubFiles());
        } catch (IOException e) {
            throw new J2csException("failed to write solution to " + appDir + ": " + e.getMessage(), e);
        }
        shimPackager.copyShim(appDir.resolve("javacompat"), solution.bootstrappedInternal(), solution.usesGui());
        return appDir;
    }

    /**
     * Derives the project/assembly name from the input's file name minus its extension, keeping only
     * characters valid in a .NET assembly name and on-disk directory. Falls back to {@code App} when
     * nothing usable remains.
     */
    static String deriveAppName(Path inputJar) {
        String fileName = inputJar.getFileName().toString();
        int dot = fileName.lastIndexOf('.');
        String base = dot > 0 ? fileName.substring(0, dot) : fileName;
        String sanitized = base.replaceAll("[^A-Za-z0-9._-]", "_");
        return sanitized.isEmpty() ? "App" : sanitized;
    }

    private static void deleteRecursively(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            return;
        }
        try (Stream<Path> walk = Files.walk(dir)) {
            for (Path path : (Iterable<Path>) walk.sorted(Comparator.reverseOrder())::iterator) {
                Files.delete(path);
            }
        }
    }

    private static void writeSources(Path dir, Map<String, String> files) throws IOException {
        if (files.isEmpty()) {
            return;
        }
        Files.createDirectories(dir);
        for (Map.Entry<String, String> entry : files.entrySet()) {
            Files.writeString(dir.resolve(entry.getKey() + ".cs"), entry.getValue());
        }
    }
}
