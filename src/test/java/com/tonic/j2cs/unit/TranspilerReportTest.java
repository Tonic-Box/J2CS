package com.tonic.j2cs.unit;

import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.j2cs.harness.TestJars;
import com.tonic.j2cs.pipeline.TranspileResult;
import com.tonic.j2cs.pipeline.Transpiler;
import com.tonic.j2cs.report.TranspileReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        CliOptions options = CliOptions.noBuild(jar, outDir);
        TranspileResult result = new Transpiler().transpile(options);

        assertTrue(Files.isRegularFile(result.reportPath()));
        String report = Files.readString(result.reportPath());
        assertTrue(report.contains("entry: com/example/App"));
        assertTrue(report.contains("com/example/App"));
    }

    @Test
    void unsupportedSummaryCategorizesAndRanks() {
        TranspileReport report = new TranspileReport();
        report.unsupportedMethod("A", "m", "()V", "shim member not implemented: java/util/Map.get");
        report.unsupportedMethod("B", "n", "()V", "shim member not implemented: java/util/Map.get");
        report.unsupportedMethod("C", "p", "()V", "shim member not implemented: java/awt/G.draw");
        report.unsupportedMethod("D", "q", "()V", "monitors not supported");

        List<String> summary = report.unsupportedSummary();

        assertEquals("shim member not implemented (3)", summary.get(0));
        assertEquals("  java/util/Map.get x2", summary.get(1));
        assertEquals("  java/awt/G.draw", summary.get(2));
        assertTrue(summary.contains("monitors not supported (1)"));
    }
}
