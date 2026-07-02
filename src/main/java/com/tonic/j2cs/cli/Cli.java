package com.tonic.j2cs.cli;

/**
 * Command-line entry point. Parses arguments and drives the transpiler pipeline.
 */
public final class Cli {

    private static final String USAGE =
            "usage: j2cs <input.class|input.jar> -o <outDir> [--main <fqcn>] [--no-build] [--self-contained] [--run] [--dump-ir]";

    public int run(String[] args) {
        System.err.println(USAGE);
        return 2;
    }
}
