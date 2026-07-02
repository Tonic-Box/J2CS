package com.tonic.j2cs.unit;

import com.tonic.j2cs.naming.CsNamer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsNamerTest {

    @Test
    void plainIdentifierPassesThrough() {
        assertEquals("value", CsNamer.identifier("value"));
        assertEquals("Main", CsNamer.identifier("Main"));
        assertEquals("_private", CsNamer.identifier("_private"));
    }

    @Test
    void csharpKeywordsAreEscaped() {
        assertEquals("@out", CsNamer.identifier("out"));
        assertEquals("@string", CsNamer.identifier("string"));
        assertEquals("@lock", CsNamer.identifier("lock"));
        assertEquals("@params", CsNamer.identifier("params"));
    }

    @Test
    void javaKeywordsThatAreNotCsharpKeywordsPassThrough() {
        assertEquals("strictfp", CsNamer.identifier("strictfp"));
        assertEquals("synchronized", CsNamer.identifier("synchronized"));
    }

    @Test
    void dollarSignsBecomeMarkers() {
        assertEquals("Outer_S_Inner", CsNamer.identifier("Outer$Inner"));
        assertEquals("val_S_", CsNamer.identifier("val$"));
    }

    @Test
    void nonAsciiCharactersAreEscaped() {
        assertEquals("na_u00EF_ve", CsNamer.identifier("naïve"));
        assertEquals("_u002D_", CsNamer.identifier("-"));
    }

    @Test
    void leadingDigitsGetUnderscorePrefix() {
        assertEquals("_3d", CsNamer.identifier("3d"));
    }

    @Test
    void namespaceOfMapsPackages() {
        assertEquals("com.example", CsNamer.namespaceOf("com/example/Main"));
        assertEquals("jdefault", CsNamer.namespaceOf("Main"));
        assertEquals("@out.@in", CsNamer.namespaceOf("out/in/X"));
    }

    @Test
    void classNameOfTakesLastSegment() {
        assertEquals("Main", CsNamer.classNameOf("com/example/Main"));
        assertEquals("Outer_S_Inner", CsNamer.classNameOf("com/example/Outer$Inner"));
    }

    @Test
    void fqcnIsGloballyQualified() {
        assertEquals("global::com.example.Main", CsNamer.fqcn("com/example/Main"));
        assertEquals("global::jdefault.Solo", CsNamer.fqcn("Solo"));
        assertEquals("global::java.lang.String", CsNamer.fqcn("java/lang/String"));
    }
}
