package com.tonic.j2cs.harness;

import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.dotnet.DotnetLocator;
import com.tonic.j2cs.dotnet.DotnetRunner;
import com.tonic.j2cs.dotnet.ExecResult;
import com.tonic.j2cs.pipeline.TranspileResult;
import com.tonic.j2cs.pipeline.Transpiler;
import org.junit.jupiter.api.Assumptions;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The differential loop: compile a fixture, run it on the JVM, transpile it, build and run the
 * .NET output (framework-dependent for speed; NativeAOT has its own opt-in smoke test), and
 * assert identical stdout. Work dirs under build/e2e are retained for post-mortem inspection.
 */
public final class Differential {

    private Differential() {
    }

    public static void assertSameOutput(String fixtureName) throws Exception {
        Assumptions.assumeTrue(DotnetLocator.isAvailable(), "dotnet CLI not available");
        Path work = Path.of("build", "e2e", fixtureName);
        Fixtures.deleteRecursively(work);

        Path source = Fixtures.materialize(fixtureName, work.resolve("src"));
        Path classes = work.resolve("classes");
        JavacHelper.compile(List.of(source), classes);
        String expected = JvmRunner.runMain(classes, fixtureName);

        CliOptions options = new CliOptions(
                classes.resolve(fixtureName + ".class"), work.resolve("out"), null, true, false, false, false);
        TranspileResult result = new Transpiler().transpile(options);

        DotnetRunner runner = new DotnetRunner();
        ExecResult build = runner.build(result.appDir());
        DotnetRunner.requireSuccess(build, "dotnet build of " + fixtureName);

        Path dll = result.appDir().resolve(Path.of("bin", "Release", "net9.0", "App.dll")).toAbsolutePath();
        ExecResult run = runner.run(List.of("dotnet", dll.toString()), result.appDir(), 60_000);
        DotnetRunner.requireSuccess(run, "running transpiled " + fixtureName);

        assertEquals(normalize(expected), normalize(run.stdout()),
                "stdout mismatch for " + fixtureName + " (report: "
                        + result.reportPath().toAbsolutePath() + ")");
    }

    private static String normalize(String text) {
        return text.replace("\r\n", "\n");
    }
}
