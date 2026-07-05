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

    private static final String USAGE = String.join("\n",
            "J2CS - transpiles JVM bytecode (.class/.jar) to C# and builds a native Windows exe.",
            "",
            "usage: java -jar J2CS.jar <input.class|input.jar> -o <outDir> [options]",
            "       java -jar J2CS.jar --bootstrap-report <fqcn>[,<fqcn>...]",
            "       java -jar J2CS.jar --bootstrap-coverage",
            "       java -jar J2CS.jar --help",
            "",
            "arguments:",
            "  <input.class|input.jar>   the class or jar to transpile (jar entry point is its Main-Class)",
            "  -o, --out <outDir>        output directory for the generated solution and exe (required)",
            "",
            "options:",
            "  --main <fqcn>             entry-point class override (dotted name), e.g. com.example.App",
            "  --no-build                emit the C# solution only; do not invoke dotnet",
            "  --aot                     publish with NativeAOT (minimal native binary) instead of the default",
            "  --self-contained          publish a self-contained single-file exe (the default)",
            "  --classic-bodies          emit goto/label method bodies instead of structured control flow",
            "  --run                     run the produced exe after publishing",
            "  --dump-ir                 dump the lifted IR for debugging",
            "  --bootstrap <fqcn>[,...]  generate the named JDK classes from platform bytecode (else shimmed)",
            "  -h, --help                show this help",
            "  --version                 print the j2cs version",
            "",
            "By default the publish produces a self-contained single-file exe (bundled runtime,",
            "no .NET install required). GUI (Swing/AWT) apps are rendered via Avalonia.");

    public int run(String[] args) {
        if (args.length == 0) {
            System.err.println(USAGE);
            return 2;
        }
        for (String arg : args) {
            if (arg.equals("--help") || arg.equals("-h")) {
                System.out.println(USAGE);
                return 0;
            }
            if (arg.equals("--version")) {
                String v = Cli.class.getPackage().getImplementationVersion();
                System.out.println("j2cs " + (v != null ? v : "dev"));
                return 0;
            }
        }
        for (String arg : args) {
            if (arg.equals("--bootstrap-coverage")) {
                System.out.print(new com.tonic.j2cs.report.BootstrapCoverageReport().coverage());
                return 0;
            }
        }
        String reportTarget = extractOption(args, "--bootstrap-report");
        if (reportTarget != null) {
            if (reportTarget.isBlank()) {
                System.err.println("error: --bootstrap-report requires a value");
                System.err.println(USAGE);
                return 2;
            }
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
        PublishMode mode = options.nativeAot() ? PublishMode.NATIVE_AOT : PublishMode.SELF_CONTAINED;
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
        boolean nativeAot = false;
        boolean classicBodies = false;
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
                case "--aot":
                    nativeAot = true;
                    break;
                case "--classic-bodies":
                    classicBodies = true;
                    break;
                case "--self-contained":
                    nativeAot = false;
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
        return new CliOptions(input, outDir, mainOverride, noBuild, nativeAot, run, dumpIr, classicBodies, bootstrap);
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
