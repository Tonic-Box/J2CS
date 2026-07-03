package com.tonic.j2cs.unit;

import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.naming.Resolved;
import com.tonic.j2cs.naming.ResolvedField;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.ClassPool;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HierarchyNamingTest {

    @TempDir
    Path dir;

    @Test
    void overrideAdoptsBaseName() throws IOException {
        NamingContext naming = context("ov",
                "class B { void m() {} void only() {} } class D extends B { void m() {} void m2() {} }");
        String baseName = naming.namerOf("B").methodName("m", "()V");
        assertEquals(baseName, naming.namerOf("D").methodName("m", "()V"));
        assertNotEquals(baseName, naming.namerOf("D").methodName("m2", "()V"));
    }

    @Test
    void fieldHidingGetsFreshName() throws IOException {
        NamingContext naming = context("fh",
                "class B { int f; } class D extends B { int f; }");
        assertEquals("f", naming.namerOf("B").fieldName("f", "I"));
        assertEquals("f__2", naming.namerOf("D").fieldName("f", "I"));
    }

    @Test
    void newOverloadAvoidsInheritedName() throws IOException {
        NamingContext naming = context("hj",
                "class B { void foo(int a) {} } class D extends B { void foo(long a) {} }");
        assertEquals("foo", naming.namerOf("B").methodName("foo", "(I)V"));
        assertEquals("foo__2", naming.namerOf("D").methodName("foo", "(J)V"));
    }

    @Test
    void trioNamesReservedEvenWithoutDeclaring() throws IOException {
        NamingContext naming = context("trio", "class K { int toString; int hashCode; }");
        assertEquals("toString__2", naming.namerOf("K").fieldName("toString", "I"));
        assertEquals("hashCode__2", naming.namerOf("K").fieldName("hashCode", "I"));
    }

    @Test
    void covariantBridgeAdoptsBaseNameAndSiblingGetsFresh() throws IOException {
        NamingContext naming = context("br",
                "class Maker { Object make() { return null; } }"
                        + " class StrMaker extends Maker { String make() { return \"s\"; } }");
        String baseName = naming.namerOf("Maker").methodName("make", "()Ljava/lang/Object;");
        assertEquals(baseName, naming.namerOf("StrMaker").methodName("make", "()Ljava/lang/Object;"));
        String covariant = naming.namerOf("StrMaker").methodName("make", "()Ljava/lang/String;");
        assertNotEquals(baseName, covariant);
        assertTrue(covariant.startsWith("make"));
    }

    @Test
    void interfaceImplementationAdoptsInterfaceName() throws IOException {
        NamingContext naming = context("if",
                "interface I { void x(); } class C implements I { public void x() {} }");
        assertEquals(naming.namerOf("I").methodName("x", "()V"),
                naming.namerOf("C").methodName("x", "()V"));
    }

    @Test
    void namingIsOrderIndependent() throws IOException {
        List<ClassFile> classes = compileAll("ord",
                "class B { void m() {} } class D extends B { void m() {} } class E extends D { void m() {} }");
        NamingContext forward = new NamingContext(new TypeMapper(), classes);
        List<ClassFile> reversed = new ArrayList<>(classes);
        Collections.reverse(reversed);
        NamingContext backward = new NamingContext(new TypeMapper(), reversed);
        assertEquals(forward.namerOf("E").methodName("m", "()V"),
                backward.namerOf("E").methodName("m", "()V"));
    }

    @Test
    void resolveVirtualWalksClassChain() throws IOException {
        NamingContext naming = context("rv",
                "class B { void m() {} } class D extends B { }");
        Resolved resolved = naming.resolveVirtual("D", "m", "()V");
        Resolved.AppMethod appMethod = assertInstanceOf(Resolved.AppMethod.class, resolved);
        assertEquals("B", appMethod.declaringInternal());
    }

    @Test
    void resolveVirtualFallsBackToObjectShim() throws IOException {
        NamingContext naming = context("ro", "class D { }");
        Resolved resolved = naming.resolveVirtual("D", "toString", "()Ljava/lang/String;");
        Resolved.ShimMethod shim = assertInstanceOf(Resolved.ShimMethod.class, resolved);
        assertEquals("java/lang/Object", shim.ownerInternal());
    }

    @Test
    void resolveVirtualFindsInterfaceDefault() throws IOException {
        NamingContext naming = context("rd",
                "interface G { default int v() { return 7; } } class C implements G { }");
        Resolved resolved = naming.resolveVirtual("C", "v", "()I");
        Resolved.AppMethod appMethod = assertInstanceOf(Resolved.AppMethod.class, resolved);
        assertEquals("G", appMethod.declaringInternal());
        assertTrue(appMethod.viaInterface());
    }

    @Test
    void resolveFieldPrefersSelfThenSuper() throws IOException {
        NamingContext naming = context("rf",
                "class B { int f; int g; } class D extends B { int f; }");
        ResolvedField own = naming.resolveField("D", "f", "I");
        assertEquals("D", assertInstanceOf(ResolvedField.AppField.class, own).declaringInternal());
        ResolvedField inherited = naming.resolveField("D", "g", "I");
        assertEquals("B", assertInstanceOf(ResolvedField.AppField.class, inherited).declaringInternal());
    }

    @Test
    void resolveStaticWalksClassChain() throws IOException {
        NamingContext naming = context("rs",
                "class B { static int s() { return 1; } } class D extends B { }");
        Resolved resolved = naming.resolveStatic("D", "s", "()I");
        assertEquals("B", assertInstanceOf(Resolved.AppMethod.class, resolved).declaringInternal());
    }

    @Test
    void conflictingInterfaceNamesDegradeTheClass() throws IOException {
        NamingContext naming = context("mc",
                "interface I1 { void p(); }"
                        + " interface I2 { int p = 1; static void q() {} }"
                        + " interface I3 extends I2 { void p(); }"
                        + " class Z implements I1, I3 { public void p() {} }");
        String i1Name = naming.namerOf("I1").methodName("p", "()V");
        String i3Name = naming.namerOf("I3").methodName("p", "()V");
        if (i1Name.equals(i3Name)) {
            assertNull(naming.classUnsupportedReason("Z"));
        } else {
            assertTrue(naming.classUnsupportedReason("Z").contains("interface method merging"));
        }
    }

    private NamingContext context(String tag, String source) throws IOException {
        return new NamingContext(new TypeMapper(), compileAll(tag, source));
    }

    private List<ClassFile> compileAll(String tag, String source) throws IOException {
        Path srcDir = Files.createDirectories(dir.resolve("src-" + tag));
        Path file = Files.writeString(srcDir.resolve("Fx" + tag + ".java"), source);
        Path classes = dir.resolve("classes-" + tag);
        JavacHelper.compile(List.of(file), classes);
        ClassPool pool = new ClassPool(true);
        List<ClassFile> result = new ArrayList<>();
        try (Stream<Path> files = Files.list(classes)) {
            for (Path classFile : files.sorted().toList()) {
                if (classFile.toString().endsWith(".class")) {
                    try (InputStream in = Files.newInputStream(classFile)) {
                        result.add(pool.loadClass(in));
                    }
                }
            }
        }
        return result;
    }
}
