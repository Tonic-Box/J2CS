package com.tonic.j2cs.unit;

import com.tonic.j2cs.harness.JavacHelper;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.ClassPool;
import com.tonic.parser.FieldEntry;
import com.tonic.parser.MethodEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberNamerTest {

    @TempDir
    Path dir;

    @Test
    void fieldClaimsNameBeforeMethod() throws IOException {
        ClassFile cf = compile("C", "public class C { public int value; public int value() { return value; } }");
        MemberNamer namer = new MemberNamer(cf, new TypeMapper());
        assertEquals("value", namer.fieldName(field(cf, "value")));
        assertEquals("value__2", namer.methodName(method(cf, "value", "()I")));
    }

    @Test
    void returnOnlyOverloadsGetReturnSuffix() throws IOException {
        ClassFile cf = compile("G",
                "public class G implements java.util.function.Supplier<String> { public String get() { return \"s\"; } }");
        MemberNamer namer = new MemberNamer(cf, new TypeMapper());
        String stringGet = namer.methodName(method(cf, "get", "()Ljava/lang/String;"));
        String objectGet = namer.methodName(method(cf, "get", "()Ljava/lang/Object;"));
        assertTrue(stringGet.startsWith("get__r"));
        assertTrue(objectGet.startsWith("get__r"));
        assertNotEquals(stringGet, objectGet);
    }

    @Test
    void keywordMembersAreEscaped() throws IOException {
        ClassFile cf = compile("K", "public class K { public int out; public void lock() {} }");
        MemberNamer namer = new MemberNamer(cf, new TypeMapper());
        assertEquals("@out", namer.fieldName(field(cf, "out")));
        assertEquals("@lock", namer.methodName(method(cf, "lock", "()V")));
    }

    @Test
    void memberNamedLikeClassIsRenamed() throws IOException {
        ClassFile cf = compile("Self", "public class Self { public void Self() {} }");
        MemberNamer namer = new MemberNamer(cf, new TypeMapper());
        assertEquals("Self__2", namer.methodName(method(cf, "Self", "()V")));
    }

    @Test
    void userMethodCollidingWithInitProtocolIsRenamed() throws IOException {
        ClassFile cf = compile("I2", "public class I2 { public void __init__V() {} }");
        MemberNamer namer = new MemberNamer(cf, new TypeMapper());
        assertEquals("__init__V", MemberNamer.initMethodName("()V"));
        assertEquals("__init__V__2", namer.methodName(method(cf, "__init__V", "()V")));
    }

    @Test
    void regularOverloadsShareTheirName() throws IOException {
        ClassFile cf = compile("Ov", "public class Ov { public void f(int a) {} public void f(long a) {} }");
        MemberNamer namer = new MemberNamer(cf, new TypeMapper());
        assertEquals("f", namer.methodName(method(cf, "f", "(I)V")));
        assertEquals("f", namer.methodName(method(cf, "f", "(J)V")));
    }

    @Test
    void overloadOfInheritedMethodSharesName() throws IOException {
        ClassFile cf = compile("D",
                "public class D { public boolean add(Object o) { return false; } public void add(int i, Object o) {} }");
        MemberNamer namer = new MemberNamer(cf, new TypeMapper(),
                java.util.Map.of("add(Ljava/lang/Object;)Z", "add"), java.util.Set.of("add"));
        assertEquals("add", namer.methodName(method(cf, "add", "(Ljava/lang/Object;)Z")));
        assertEquals("add", namer.methodName(method(cf, "add", "(ILjava/lang/Object;)V")));
    }

    @Test
    void inheritedOverrideKeepsNameFieldYields() throws IOException {
        ClassFile cf = compile("S2", "public class S2 { int size; public int size() { return size; } }");
        MemberNamer namer = new MemberNamer(cf, new TypeMapper(),
                java.util.Map.of("size()I", "size"), java.util.Set.of("size"));
        assertEquals("size__2", namer.fieldName(field(cf, "size")));
        assertEquals("size", namer.methodName(method(cf, "size", "()I")));
    }

    @Test
    void descriptorSuffixIsStable() {
        assertEquals("_ILjava_lang_String__V", MemberNamer.descriptorSuffix("(ILjava/lang/String;)V"));
        assertEquals("__V", MemberNamer.descriptorSuffix("()V"));
        assertEquals("__I_V", MemberNamer.descriptorSuffix("([I)V"));
    }

    private ClassFile compile(String className, String source) throws IOException {
        Path src = Files.writeString(dir.resolve(className + ".java"), source);
        Path classes = dir.resolve("classes-" + className);
        JavacHelper.compile(List.of(src), classes);
        ClassPool pool = new ClassPool(true);
        try (InputStream in = Files.newInputStream(classes.resolve(className + ".class"))) {
            return pool.loadClass(in);
        }
    }

    private static MethodEntry method(ClassFile cf, String name, String desc) {
        List<String> seen = new ArrayList<>();
        for (MethodEntry m : cf.getMethods()) {
            if (m.getName().equals(name) && m.getDesc().equals(desc)) {
                return m;
            }
            seen.add(m.getName() + m.getDesc());
        }
        throw new AssertionError("method " + name + desc + " not found; methods: " + seen);
    }

    private static FieldEntry field(ClassFile cf, String name) {
        for (FieldEntry f : cf.getFields()) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        throw new AssertionError("field " + name + " not found");
    }
}
