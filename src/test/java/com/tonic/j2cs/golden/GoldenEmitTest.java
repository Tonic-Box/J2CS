package com.tonic.j2cs.golden;

import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.harness.Fixtures;
import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.j2cs.harness.TestJars;
import com.tonic.j2cs.pipeline.TranspileResult;
import com.tonic.j2cs.pipeline.Transpiler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Locks the emitted C# text for representative fixtures against golden files, guaranteeing
 * deterministic emission. Regenerate with gradlew test -Dj2cs.regenGolden=true, then copy the
 * files from build/golden-regen into src/test/resources/golden.
 */
class GoldenEmitTest {

    @Test
    void fizzBuzzEmissionIsStable() throws Exception {
        assertGolden("FizzBuzz");
    }

    @Test
    void shapesEmissionIsStable() throws Exception {
        assertGolden("Shapes");
    }

    @Test
    void exceptions2EmissionIsStable() throws Exception {
        assertGolden("Exceptions2");
    }

    @Test
    void lambdas1EmissionIsStable() throws Exception {
        assertGolden("Lambdas1");
    }

    @Test
    void guiSmokeEmissionIsStable() throws Exception {
        assertGolden("GuiSmoke", true);
    }

    private void assertGolden(String fixtureName) throws Exception {
        assertGolden(fixtureName, false);
    }

    private void assertGolden(String fixtureName, boolean includeCsproj) throws Exception {
        Path work = Path.of("build", "golden", fixtureName);
        Fixtures.deleteRecursively(work);

        Path source = Fixtures.materialize(fixtureName, work.resolve("src"));
        Path classes = work.resolve("classes");
        JavacHelper.compile(List.of(source), classes);
        Path jar = TestJars.jar(work.resolve(fixtureName + ".jar"), classes, fixtureName);
        CliOptions options = CliOptions.noBuild(jar, work.resolve("out"));
        TranspileResult result = new Transpiler().transpile(options);

        String actual = canonicalText(result.appDir(), includeCsproj);
        if (Boolean.getBoolean("j2cs.regenGolden")) {
            Path regen = Path.of("build", "golden-regen", fixtureName + ".cs.txt");
            Files.createDirectories(regen.getParent());
            Files.writeString(regen, actual);
            System.out.println("golden regenerated: " + regen.toAbsolutePath());
            return;
        }
        assertEquals(golden(fixtureName), actual, "emitted C# for " + fixtureName
                + " changed; regenerate goldens with -Dj2cs.regenGolden=true if intentional");
    }

    private static String canonicalText(Path appDir, boolean includeCsproj) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (includeCsproj) {
            appendFile(sb, "App.csproj", Files.readString(appDir.resolve("App.csproj")));
        }
        appendFile(sb, "Program.cs", Files.readString(appDir.resolve("Program.cs")));
        Path gen = appDir.resolve("gen");
        try (Stream<Path> files = Files.list(gen)) {
            for (Path file : files.sorted().toList()) {
                appendFile(sb, "gen/" + file.getFileName(), Files.readString(file));
            }
        }
        return sb.toString().replace("\r\n", "\n");
    }

    private static void appendFile(StringBuilder sb, String label, String content) {
        sb.append("// ==== ").append(label).append(" ====\n").append(content);
    }

    private static String golden(String fixtureName) throws IOException {
        String resource = "/golden/" + fixtureName + ".cs.txt";
        try (InputStream in = GoldenEmitTest.class.getResourceAsStream(resource)) {
            assertNotNull(in, "missing golden file " + resource
                    + "; generate with -Dj2cs.regenGolden=true and copy from build/golden-regen");
            return new String(in.readAllBytes(), StandardCharsets.UTF_8).replace("\r\n", "\n");
        }
    }
}
