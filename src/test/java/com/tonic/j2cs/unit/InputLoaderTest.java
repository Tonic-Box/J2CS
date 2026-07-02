package com.tonic.j2cs.unit;

import com.tonic.j2cs.J2csException;
import com.tonic.j2cs.frontend.InputLoader;
import com.tonic.j2cs.frontend.LoadedInput;
import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.j2cs.harness.TestJars;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputLoaderTest {

    @TempDir
    Path dir;

    @Test
    void classInputWithMainResolvesAsEntry() throws IOException {
        Path classes = compile("Solo", "public class Solo { public static void main(String[] args) {} }");
        LoadedInput input = new InputLoader().load(classes.resolve("Solo.class"), null);
        assertEquals("Solo", input.entryClassInternalName());
        assertEquals(1, input.appClasses().size());
    }

    @Test
    void classInputWithoutMainIsRejected() throws IOException {
        Path classes = compile("NoMain", "public class NoMain { static int f() { return 1; } }");
        J2csException e = assertThrows(J2csException.class,
                () -> new InputLoader().load(classes.resolve("NoMain.class"), null));
        assertTrue(e.getMessage().contains("no class with a static main"));
    }

    @Test
    void jarManifestMainClassResolvesAsEntry() throws IOException {
        Path classes = compile("App",
                "package com.example; public class App { public static void main(String[] args) {} }",
                "package com.example; public class Helper { public static void main(String[] args) {} }");
        Path jar = TestJars.jar(dir.resolve("app.jar"), classes, "com.example.App");
        LoadedInput input = new InputLoader().load(jar, null);
        assertEquals("com/example/App", input.entryClassInternalName());
        assertEquals(2, input.appClasses().size());
    }

    @Test
    void mainOverrideBeatsManifest() throws IOException {
        Path classes = compile("App",
                "package com.example; public class App { public static void main(String[] args) {} }",
                "package com.example; public class Helper { public static void main(String[] args) {} }");
        Path jar = TestJars.jar(dir.resolve("app.jar"), classes, "com.example.App");
        LoadedInput input = new InputLoader().load(jar, "com.example.Helper");
        assertEquals("com/example/Helper", input.entryClassInternalName());
    }

    @Test
    void ambiguousEntryWithoutManifestIsRejected() throws IOException {
        Path classes = compile("A",
                "public class A { public static void main(String[] args) {} }",
                "public class B { public static void main(String[] args) {} }");
        Path jar = TestJars.jar(dir.resolve("app.jar"), classes, null);
        J2csException e = assertThrows(J2csException.class, () -> new InputLoader().load(jar, null));
        assertTrue(e.getMessage().contains("multiple classes"));
    }

    @Test
    void entryWithoutStaticMainIsRejected() throws IOException {
        Path classes = compile("App",
                "package com.example; public class App { public void main(String[] args) {} }");
        Path jar = TestJars.jar(dir.resolve("app.jar"), classes, "com.example.App");
        J2csException e = assertThrows(J2csException.class, () -> new InputLoader().load(jar, null));
        assertTrue(e.getMessage().contains("no static main"));
    }

    @Test
    void platformPackagesAreRejected() throws IOException {
        Path classes = compile("Sneaky",
                "package javax.demo; public class Sneaky { public static void main(String[] args) {} }");
        Path jar = TestJars.jar(dir.resolve("app.jar"), classes, "javax.demo.Sneaky");
        J2csException e = assertThrows(J2csException.class, () -> new InputLoader().load(jar, null));
        assertTrue(e.getMessage().contains("reserved platform packages"));
    }

    @Test
    void missingEntryClassIsRejected() throws IOException {
        Path classes = compile("App",
                "package com.example; public class App { public static void main(String[] args) {} }");
        Path jar = TestJars.jar(dir.resolve("app.jar"), classes, null);
        J2csException e = assertThrows(J2csException.class,
                () -> new InputLoader().load(jar, "com.example.Nope"));
        assertTrue(e.getMessage().contains("not found in input"));
    }

    private Path compile(String firstName, String... sources) throws IOException {
        Path srcDir = Files.createDirectories(dir.resolve("src-" + firstName));
        Path[] files = new Path[sources.length];
        for (int i = 0; i < sources.length; i++) {
            String source = sources[i];
            String className = source.replaceAll(".*public class (\\w+).*", "$1");
            files[i] = Files.writeString(srcDir.resolve(className + ".java"), source);
        }
        Path classes = dir.resolve("classes-" + firstName);
        JavacHelper.compile(List.of(files), classes);
        return classes;
    }
}
