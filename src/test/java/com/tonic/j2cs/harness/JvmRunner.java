package com.tonic.j2cs.harness;

import com.tonic.j2cs.dotnet.DotnetRunner;
import com.tonic.j2cs.dotnet.ExecResult;
import java.nio.file.Path;
import java.util.List;

/**
 * Runs a compiled fixture on the JVM that is running the tests, capturing stdout as the
 * reference output for differential comparison.
 */
public final class JvmRunner {

    private JvmRunner() {
    }

    public static String runMain(Path classesDir, String mainClass) {
        Path java = Path.of(System.getProperty("java.home"), "bin", "java");
        Path absoluteClasses = classesDir.toAbsolutePath();
        ExecResult result = new DotnetRunner().run(
                List.of(java.toString(), "-cp", absoluteClasses.toString(), mainClass),
                absoluteClasses, 60_000);
        DotnetRunner.requireSuccess(result, "JVM reference run of " + mainClass);
        return result.stdout();
    }
}
