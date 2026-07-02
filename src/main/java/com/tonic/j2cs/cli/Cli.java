package com.tonic.j2cs.cli;

import com.tonic.j2cs.J2csException;
import com.tonic.j2cs.pipeline.TranspileResult;
import com.tonic.j2cs.pipeline.Transpiler;

import java.nio.file.Path;

/**
 * Command-line entry point. Parses arguments and drives the transpiler pipeline.
 */
public final class Cli {

    private static final String USAGE = "usage: j2cs <input.class|input.jar> -o <outDir> [--main <fqcn>] [--no-build] [--self-contained] [--run] [--dump-ir]";

    public int run(String[] args) {
        CliOptions options;
        try {
            options = parse(args);
        } catch (IllegalArgumentException e) {
            System.err.println("error: " + e.getMessage());
            System.err.println(USAGE);
            return 2;
        }
        try {
            TranspileResult result = new Transpiler().transpile(options);
            System.out.println("transpiled " + result.input().appClasses().size()
                    + " class(es), entry " + result.input().entryClassInternalName());
            System.out.println("solution: " + result.appDir());
            System.out.println("report: " + result.reportPath());
            return 0;
        } catch (J2csException e) {
            System.err.println("error: " + e.getMessage());
            return 1;
        }
    }

    public static CliOptions parse(String[] args) {
        Path input = null;
        Path outDir = null;
        String mainOverride = null;
        boolean noBuild = false;
        boolean selfContained = false;
        boolean run = false;
        boolean dumpIr = false;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-o":
                case "--out":
                    outDir = Path.of(requireValue(args, i++, arg));
                    break;
                case "--main":
                    mainOverride = requireValue(args, i++, arg);
                    break;
                case "--no-build":
                    noBuild = true;
                    break;
                case "--self-contained":
                    selfContained = true;
                    break;
                case "--run":
                    run = true;
                    break;
                case "--dump-ir":
                    dumpIr = true;
                    break;
                default:
                    if (arg.startsWith("-")) {
                        throw new IllegalArgumentException("unknown option: " + arg);
                    }
                    if (input != null) {
                        throw new IllegalArgumentException("multiple inputs given: " + input + ", " + arg);
                    }
                    input = Path.of(arg);
                    break;
            }
        }
        if (input == null) {
            throw new IllegalArgumentException("no input file given");
        }
        if (outDir == null) {
            throw new IllegalArgumentException("no output directory given (-o <outDir>)");
        }
        return new CliOptions(input, outDir, mainOverride, noBuild, selfContained, run, dumpIr);
    }

    private static String requireValue(String[] args, int index, String option) {
        if (index + 1 >= args.length) {
            throw new IllegalArgumentException(option + " requires a value");
        }
        return args[index + 1];
    }
}
