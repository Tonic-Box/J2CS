package com.tonic.j2cs.harness;

import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.dotnet.DotnetLocator;
import com.tonic.j2cs.dotnet.DotnetRunner;
import com.tonic.j2cs.dotnet.ExecResult;
import com.tonic.j2cs.pipeline.TranspileResult;
import com.tonic.j2cs.pipeline.Transpiler;
import com.tonic.j2cs.project.CsprojTemplate;
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
        assertSameOutput(fixtureName, List.of());
    }

    public static void assertSameOutput(String fixtureName, List<String> bootstrap) throws Exception {
        assertSameOutput(fixtureName, bootstrap, false);
    }

    /** Runs the fixture through the transpiler (structured by default; {@code classic} forces the
     * goto/label bodies) and asserts stdout matches the JVM. */
    public static void assertSameOutput(String fixtureName, List<String> bootstrap, boolean classic)
            throws Exception {
        Assumptions.assumeTrue(DotnetLocator.isAvailable(), "dotnet CLI not available");
        Path work = Path.of("build", "e2e", classic ? fixtureName + "-classic" : fixtureName);
        Fixtures.deleteRecursively(work);

        Path source = Fixtures.materialize(fixtureName, work.resolve("src"));
        Path classes = work.resolve("classes");
        JavacHelper.compile(List.of(source), classes);
        String expected = JvmRunner.runMain(classes, fixtureName);

        Path jar = TestJars.jar(work.resolve(fixtureName + ".jar"), classes, fixtureName);
        CliOptions options = classic
                ? CliOptions.noBuildClassic(jar, work.resolve("out"), bootstrap)
                : CliOptions.noBuild(jar, work.resolve("out"), bootstrap);
        TranspileResult result = new Transpiler().transpile(options);

        DotnetRunner runner = new DotnetRunner();
        ExecResult build = runner.build(result.appDir());
        DotnetRunner.requireSuccess(build, "dotnet build of " + fixtureName);

        Path dll = result.appDir()
                .resolve(Path.of("bin", "Release", CsprojTemplate.TARGET_FRAMEWORK,
                        result.appDir().getFileName().toString() + ".dll"))
                .toAbsolutePath();
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
