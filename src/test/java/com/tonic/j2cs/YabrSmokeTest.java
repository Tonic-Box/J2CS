package com.tonic.j2cs;

import com.tonic.analysis.ssa.SSA;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.ClassPool;
import com.tonic.parser.MethodEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class YabrSmokeTest {

    @Test
    void liftsFreshlyCompiledClassToSsa(@TempDir Path dir) throws Exception {
        Path source = dir.resolve("Smoke.java");
        Files.writeString(source,
                "public class Smoke {\n"
                        + "    static int add(int a, int b) {\n"
                        + "        return a + b;\n"
                        + "    }\n"
                        + "}\n");
        Path classesDir = dir.resolve("classes");
        JavacHelper.compile(List.of(source), classesDir);

        ClassPool pool = new ClassPool(true);
        ClassFile classFile;
        try (InputStream in = Files.newInputStream(classesDir.resolve("Smoke.class"))) {
            classFile = pool.loadClass(in);
        }
        assertNotNull(classFile);

        MethodEntry add = classFile.getMethods().stream()
                .filter(m -> m.getName().equals("add"))
                .findFirst()
                .orElseThrow();

        IRMethod ir = new SSA(classFile.getConstPool()).lift(add);
        assertNotNull(ir.getEntryBlock());
        assertFalse(ir.getBlocks().isEmpty());
    }
}
