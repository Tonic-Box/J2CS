package com.tonic.j2cs.cli;

import com.tonic.j2cs.J2csException;
import com.tonic.j2cs.dotnet.DotnetLocator;
import com.tonic.j2cs.dotnet.DotnetRunner;
import com.tonic.j2cs.dotnet.ExecResult;
import com.tonic.j2cs.dotnet.PublishMode;
import com.tonic.j2cs.pipeline.TranspileResult;
import com.tonic.j2cs.pipeline.Transpiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Command-line entry point. Parses arguments and drives the transpiler pipeline.
 */
public final class Cli {

    private static final String USAGE = "usage: j2cs <input.class|input.jar> -o <outDir> [--main <fqcn>] [--no-build] [--self-contained] [--run] [--dump-ir] [--bootstrap <fqcn>[,<fqcn>...]]\n"
            + "       j2cs --bootstrap-report <fqcn>[,<fqcn>...]";

    public int run(String[] args) {
        String reportTarget = extractOption(args, "--bootstrap-report");
        if (reportTarget != null) {
            System.out.println(new com.tonic.j2cs.report.BootstrapCoverageReport().analyze(splitList(reportTarget)));
            return 0;
        }
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
            if (options.noBuild()) {
                return 0;
            }
            return publish(options, result);
        } catch (J2csException e) {
            System.err.println("error: " + e.getMessage());
            return 1;
        }
    }

    private int publish(CliOptions options, TranspileResult result) {
        if (!DotnetLocator.isAvailable()) {
            System.err.println("error: dotnet CLI not found on PATH; rerun with --no-build to keep the solution only");
            return 1;
        }
        DotnetRunner runner = new DotnetRunner();
        PublishMode mode = options.selfContained() ? PublishMode.SELF_CONTAINED : PublishMode.NATIVE_AOT;
        Path publishDir = options.outDir().resolve("publish");
        System.out.println("publishing (" + mode + ") ...");
        ExecResult published = runner.publish(result.appDir(), mode, publishDir.toAbsolutePath());
        DotnetRunner.requireSuccess(published, "dotnet publish (" + mode + ")");
        Path exe = copyNamedExe(options, publishDir);
        System.out.println("exe: " + exe);
        if (options.run()) {
            ExecResult run = runner.exec(exe.toAbsolutePath(), List.of(), options.outDir());
            System.out.print(run.stdout());
            System.err.print(run.stderr());
            return run.exitCode();
        }
        return 0;
    }

    private static Path copyNamedExe(CliOptions options, Path publishDir) {
        Path source = publishDir.resolve("App.exe");
        String fileName = options.input().getFileName().toString();
        int dot = fileName.lastIndexOf('.');
        String baseName = dot > 0 ? fileName.substring(0, dot) : fileName;
        Path target = options.outDir().resolve(baseName + ".exe");
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new J2csException("failed to copy exe to " + target + ": " + e.getMessage(), e);
        }
        return target;
    }

    public static CliOptions parse(String[] args) {
        Path input = null;
        Path outDir = null;
        String mainOverride = null;
        boolean noBuild = false;
        boolean selfContained = false;
        boolean run = false;
        boolean dumpIr = false;
        java.util.List<String> bootstrap = java.util.List.of();
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
                case "--bootstrap":
                    bootstrap = splitList(requireValue(args, i++, arg));
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
        return new CliOptions(input, outDir, mainOverride, noBuild, selfContained, run, dumpIr, bootstrap);
    }

    private static String requireValue(String[] args, int index, String option) {
        if (index + 1 >= args.length) {
            throw new IllegalArgumentException(option + " requires a value");
        }
        return args[index + 1];
    }

    private static String extractOption(String[] args, String option) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(option)) {
                return i + 1 < args.length ? args[i + 1] : "";
            }
        }
        return null;
    }

    static java.util.List<String> splitList(String csv) {
        java.util.List<String> out = new java.util.ArrayList<>();
        for (String part : csv.split(",")) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                out.add(trimmed);
            }
        }
        return out;
    }
}
