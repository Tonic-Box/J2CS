package com.tonic.j2cs.unit;

import com.tonic.j2cs.emit.UsingRewriter;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsingRewriterTest {

    private static String rewrite(String cs, String... ownSimple) {
        return new UsingRewriter(Set.of(ownSimple)).rewrite(cs);
    }

    private static String wrap(String ns, String body) {
        return "namespace " + ns + "\n{\n    internal class Holder\n    {\n" + body + "\n    }\n}\n";
    }

    @Test
    void shortensOwnNamespacesAndInjectsUsings() {
        String out = rewrite(wrap("jdefault",
                "        global::java.lang.Object o = new global::java.util.ArrayList();"));
        assertTrue(out.contains("    using java.lang;\n"), out);
        assertTrue(out.contains("    using java.util;\n"), out);
        assertTrue(out.contains("Object o = new ArrayList();"), out);
        assertFalse(out.contains("global::java.lang.Object"), out);
        assertFalse(out.contains("global::java.util.ArrayList"), out);
    }

    @Test
    void preservesMembersAndGenericsAndArrays() {
        String out = rewrite(wrap("jdefault",
                "        var a = global::java.lang.Class.Of(\"x\"); var b = new global::java.lang.Class[0];"));
        assertTrue(out.contains("Class.Of(\"x\")"), out);
        assertTrue(out.contains("new Class[0]"), out);
    }

    @Test
    void neverTouchesStringLiterals() {
        String lit = "\"global::java.lang.String\"";
        String out = rewrite(wrap("jdefault", "        var s = " + lit + ";"));
        assertTrue(out.contains(lit), out);
    }

    @Test
    void neverTouchesComments() {
        String out = rewrite(wrap("jdefault", "        // uses global::java.lang.String here\n        int x = 0;"));
        assertTrue(out.contains("// uses global::java.lang.String here"), out);
    }

    @Test
    void keepsAmbiguousSimpleNamesGlobal() {
        String out = rewrite(wrap("jdefault",
                "        var a = new global::java.awt.Timer(); var b = new global::javax.swing.Timer();"));
        assertTrue(out.contains("global::java.awt.Timer"), out);
        assertTrue(out.contains("global::javax.swing.Timer"), out);
        assertFalse(out.contains("using java.awt;"), out);
    }

    @Test
    void keepsRootSystemGlobal() {
        String out = rewrite(wrap("jdefault",
                "        throw new global::System.InvalidOperationException(\"x\");"));
        assertTrue(out.contains("global::System.InvalidOperationException"), out);
    }

    @Test
    void shortensDeepBclWhenNotOwned() {
        String out = rewrite(wrap("jdefault",
                "        var sb = new global::System.Text.StringBuilder();"));
        assertTrue(out.contains("    using System.Text;\n"), out);
        assertTrue(out.contains("new StringBuilder()"), out);
    }

    @Test
    void keepsDeepBclGlobalWhenNameCollidesWithOwnType() {
        String out = rewrite(wrap("jdefault",
                "        var sb = new global::System.Text.StringBuilder();"),
                "StringBuilder");
        assertTrue(out.contains("global::System.Text.StringBuilder"), out);
        assertFalse(out.contains("using System.Text;"), out);
    }

    @Test
    void sameNamespaceNeedsNoUsing() {
        String out = rewrite(wrap("jdefault",
                "        var v = global::jdefault.Holder.make();"));
        assertTrue(out.contains("Holder.make()"), out);
        assertFalse(out.contains("using jdefault;"), out);
        assertFalse(out.contains("global::jdefault.Holder"), out);
    }

    @Test
    void keepsGlobalWhenShadowedByDeclaredType() {
        // The file declares 'Component'; a foreign java.awt.Component must stay global::.
        String cs = "namespace jdefault\n{\n    internal class Component\n    {\n"
                + "        global::java.awt.Component other;\n    }\n}\n";
        String out = rewrite(cs);
        assertTrue(out.contains("global::java.awt.Component"), out);
    }
}
