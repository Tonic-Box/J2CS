package com.tonic.j2cs.cli;

import java.nio.file.Path;

/**
 * Parsed command-line options for one transpile run.
 */
public record CliOptions(
        Path input,
        Path outDir,
        String mainOverride,
        boolean noBuild,
        boolean selfContained,
        boolean run,
        boolean dumpIr) {
}
