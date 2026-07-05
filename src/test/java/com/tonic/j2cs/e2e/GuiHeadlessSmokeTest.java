package com.tonic.j2cs.e2e;

import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.dotnet.DotnetLocator;
import com.tonic.j2cs.dotnet.DotnetRunner;
import com.tonic.j2cs.dotnet.ExecResult;
import com.tonic.j2cs.harness.Fixtures;
import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.j2cs.harness.TestJars;
import com.tonic.j2cs.pipeline.Transpiler;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Opt-in headless GUI smoke: gradlew test -Dj2cs.gui=true. Transpiles a fixture whose modal
 * dialog is closed via setVisible(false), compiles it together with an Avalonia.Headless driver
 * that clicks the Close button, and asserts the modal blocked and then unblocked (stdout order
 * before -> closing -> after).
 */
@EnabledIfSystemProperty(named = "j2cs.gui", matches = "true")
class GuiHeadlessSmokeTest {

    @Test
    void modalDialogBlocksAndUnblocks() throws Exception {
        Assumptions.assumeTrue(DotnetLocator.isAvailable(), "dotnet CLI not available");
        Path work = Path.of("build", "e2e", "GuiModal");
        Fixtures.deleteRecursively(work);

        Path source = Fixtures.materialize("GuiModal", work.resolve("src"));
        Path classes = work.resolve("classes");
        JavacHelper.compile(List.of(source), classes);
        Path jar = TestJars.jar(work.resolve("GuiModal.jar"), classes, "GuiModal");
        new Transpiler().transpile(CliOptions.noBuild(jar, work.resolve("out")));

        Path driverDir = work.resolve("out").resolve("Driver");
        Files.createDirectories(driverDir);
        copyResource("/guiharness/Driver.csproj", driverDir.resolve("Driver.csproj"));
        copyResource("/guiharness/Driver.cs", driverDir.resolve("Driver.cs"));

        ExecResult run = new DotnetRunner().run(
                List.of("dotnet", "run", "-c", "Release"), driverDir, 300_000);
        DotnetRunner.requireSuccess(run, "headless GUI smoke");
        String stdout = run.stdout();
        int before = stdout.indexOf("before");
        int closing = stdout.indexOf("closing");
        int after = stdout.indexOf("after");
        assertTrue(before >= 0 && closing > before && after > closing,
                "modal block/unblock sequence wrong:\n" + stdout);
    }

    private static void copyResource(String resource, Path target) throws IOException {
        try (InputStream in = GuiHeadlessSmokeTest.class.getResourceAsStream(resource)) {
            assertNotNull(in, "missing test resource: " + resource);
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
