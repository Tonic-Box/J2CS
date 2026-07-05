package com.tonic.j2cs.e2e;

import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.dotnet.DotnetLocator;
import com.tonic.j2cs.dotnet.DotnetRunner;
import com.tonic.j2cs.dotnet.ExecResult;
import com.tonic.j2cs.harness.Fixtures;
import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.j2cs.pipeline.TranspileResult;
import com.tonic.j2cs.pipeline.Transpiler;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SkeletonBuildTest {

    @Test
    void helloWorldSolutionBuilds() throws Exception {
        Assumptions.assumeTrue(DotnetLocator.isAvailable(), "dotnet CLI not available");
        Path work = Path.of("build", "e2e", "skeleton");
        Fixtures.deleteRecursively(work);

        Path source = Fixtures.materialize("HelloWorld", work.resolve("src"));
        Path classes = work.resolve("classes");
        JavacHelper.compile(List.of(source), classes);

        CliOptions options = CliOptions.noBuild(classes.resolve("HelloWorld.class"), work.resolve("out"));
        TranspileResult result = new Transpiler().transpile(options);

        ExecResult build = new DotnetRunner().build(result.appDir());
        assertEquals(0, build.exitCode(), () -> "dotnet build failed:\n" + build.combinedTail(50));
    }
}
