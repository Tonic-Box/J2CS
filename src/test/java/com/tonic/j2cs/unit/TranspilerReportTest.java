package com.tonic.j2cs.unit;

import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.j2cs.harness.TestJars;
import com.tonic.j2cs.pipeline.TranspileResult;
import com.tonic.j2cs.pipeline.Transpiler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TranspilerReportTest {

    @Test
    void noBuildRunWritesReportListingClasses(@TempDir Path dir) throws Exception {
        Path src = Files.writeString(dir.resolve("App.java"),
                "package com.example; public class App { public static void main(String[] args) {} }");
        Path classes = dir.resolve("classes");
        JavacHelper.compile(List.of(src), classes);
        Path jar = TestJars.jar(dir.resolve("app.jar"), classes, "com.example.App");
        Path outDir = dir.resolve("out");

        CliOptions options = new CliOptions(jar, outDir, null, true, false, false, false);
        TranspileResult result = new Transpiler().transpile(options);

        assertTrue(Files.isRegularFile(result.reportPath()));
        String report = Files.readString(result.reportPath());
        assertTrue(report.contains("entry: com/example/App"));
        assertTrue(report.contains("com/example/App"));
    }
}
