package com.tonic.j2cs.project;

import com.tonic.j2cs.J2csException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Writes the generated C# solution: App.csproj, Program.cs, gen/*.cs, stubs/*.cs, and the
 * copied shim under javacompat/. Returns the App project directory.
 */
public final class SolutionGenerator {

    private final ShimPackager shimPackager = new ShimPackager();

    public Path generate(Path outDir, GeneratedSolution solution) {
        Path appDir = outDir.resolve("App");
        try {
            Files.createDirectories(appDir);
            Files.writeString(appDir.resolve("App.csproj"), CsprojTemplate.csproj());
            Files.writeString(appDir.resolve("Program.cs"), solution.programCs());
            writeSources(appDir.resolve("gen"), solution.genFiles());
            writeSources(appDir.resolve("stubs"), solution.stubFiles());
        } catch (IOException e) {
            throw new J2csException("failed to write solution to " + appDir + ": " + e.getMessage(), e);
        }
        shimPackager.copyShim(appDir.resolve("javacompat"), solution.bootstrappedInternal());
        return appDir;
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
