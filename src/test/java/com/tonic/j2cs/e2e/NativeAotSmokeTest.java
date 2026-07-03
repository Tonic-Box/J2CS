package com.tonic.j2cs.e2e;

import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.dotnet.DotnetLocator;
import com.tonic.j2cs.dotnet.DotnetRunner;
import com.tonic.j2cs.dotnet.ExecResult;
import com.tonic.j2cs.dotnet.PublishMode;
import com.tonic.j2cs.harness.Fixtures;
import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.j2cs.harness.JvmRunner;
import com.tonic.j2cs.harness.TestJars;
import com.tonic.j2cs.pipeline.TranspileResult;
import com.tonic.j2cs.pipeline.Transpiler;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Opt-in full NativeAOT publish: gradlew test -Dj2cs.aot=true. Requires the MSVC C++ build
 * tools; the fast differential loop uses framework-dependent builds instead.
 */
@EnabledIfSystemProperty(named = "j2cs.aot", matches = "true")
class NativeAotSmokeTest {

    @Test
    void fizzBuzzPublishesAndRunsAsNativeExe() throws Exception {
        Assumptions.assumeTrue(DotnetLocator.isAvailable(), "dotnet CLI not available");
        Path work = Path.of("build", "e2e", "aot-FizzBuzz");
        Fixtures.deleteRecursively(work);

        Path source = Fixtures.materialize("FizzBuzz", work.resolve("src"));
        Path classes = work.resolve("classes");
        JavacHelper.compile(List.of(source), classes);
        String expected = JvmRunner.runMain(classes, "FizzBuzz");

        Path jar = TestJars.jar(work.resolve("FizzBuzz.jar"), classes, "FizzBuzz");
        CliOptions options = new CliOptions(jar, work.resolve("out"), null, true, false, false, false);
        TranspileResult result = new Transpiler().transpile(options);

        DotnetRunner runner = new DotnetRunner();
        Path publishDir = work.resolve("out").resolve("publish").toAbsolutePath();
        ExecResult published = runner.publish(result.appDir(), PublishMode.NATIVE_AOT, publishDir);
        DotnetRunner.requireSuccess(published, "NativeAOT publish of FizzBuzz");

        ExecResult run = runner.exec(publishDir.resolve("App.exe"), List.of(), publishDir);
        DotnetRunner.requireSuccess(run, "running native FizzBuzz.exe");
        assertEquals(expected.replace("\r\n", "\n"), run.stdout().replace("\r\n", "\n"));
    }
}
