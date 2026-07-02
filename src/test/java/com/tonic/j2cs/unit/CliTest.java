package com.tonic.j2cs.unit;

import com.tonic.j2cs.cli.Cli;
import com.tonic.j2cs.cli.CliOptions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CliTest {

    @Test
    void parsesFullArgumentSet() {
        CliOptions options = Cli.parse(new String[]{
                "app.jar", "-o", "out", "--main", "com.example.App",
                "--no-build", "--self-contained", "--run", "--dump-ir"});
        assertEquals(Path.of("app.jar"), options.input());
        assertEquals(Path.of("out"), options.outDir());
        assertEquals("com.example.App", options.mainOverride());
        assertTrue(options.noBuild());
        assertTrue(options.selfContained());
        assertTrue(options.run());
        assertTrue(options.dumpIr());
    }

    @Test
    void parsesMinimalArguments() {
        CliOptions options = Cli.parse(new String[]{"Foo.class", "-o", "out"});
        assertEquals(Path.of("Foo.class"), options.input());
        assertNull(options.mainOverride());
        assertFalse(options.noBuild());
    }

    @Test
    void rejectsMissingInput() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Cli.parse(new String[]{"-o", "out"}));
        assertTrue(e.getMessage().contains("no input"));
    }

    @Test
    void rejectsMissingOutDir() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Cli.parse(new String[]{"app.jar"}));
        assertTrue(e.getMessage().contains("no output directory"));
    }

    @Test
    void rejectsUnknownOption() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Cli.parse(new String[]{"app.jar", "-o", "out", "--frobnicate"}));
        assertTrue(e.getMessage().contains("unknown option"));
    }

    @Test
    void rejectsOptionWithoutValue() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Cli.parse(new String[]{"app.jar", "-o"}));
        assertTrue(e.getMessage().contains("requires a value"));
    }
}
