package com.tonic.j2cs.cli;

import java.nio.file.Path;
import java.util.List;

/**
 * Parsed command-line options for one transpile run. bootstrap names JDK classes to load and
 * generate from platform bytecode instead of routing to the hand-written shim. classicBodies
 * opts out of structured emission back to the goto/label bodies.
 */
public record CliOptions(
        Path input,
        Path outDir,
        String mainOverride,
        boolean noBuild,
        boolean nativeAot,
        boolean run,
        boolean dumpIr,
        boolean classicBodies,
        List<String> bootstrap) {

    public static CliOptions noBuild(Path input, Path outDir) {
        return noBuild(input, outDir, List.of());
    }

    public static CliOptions noBuild(Path input, Path outDir, List<String> bootstrap) {
        return new CliOptions(input, outDir, null, true, false, false, false, false, bootstrap);
    }

    public static CliOptions noBuildClassic(Path input, Path outDir, List<String> bootstrap) {
        return new CliOptions(input, outDir, null, true, false, false, false, true, bootstrap);
    }
}
