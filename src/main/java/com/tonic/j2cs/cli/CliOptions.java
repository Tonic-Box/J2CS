package com.tonic.j2cs.cli;

import java.nio.file.Path;
import java.util.List;

/**
 * Parsed command-line options for one transpile run. bootstrap names JDK classes to load and
 * generate from platform bytecode instead of routing to the hand-written shim.
 */
public record CliOptions(
        Path input,
        Path outDir,
        String mainOverride,
        boolean noBuild,
        boolean selfContained,
        boolean run,
        boolean dumpIr,
        List<String> bootstrap) {

    public CliOptions(Path input, Path outDir, String mainOverride, boolean noBuild,
                      boolean selfContained, boolean run, boolean dumpIr) {
        this(input, outDir, mainOverride, noBuild, selfContained, run, dumpIr, List.of());
    }
}
