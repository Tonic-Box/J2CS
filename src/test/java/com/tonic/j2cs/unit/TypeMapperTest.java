package com.tonic.j2cs.unit;

import com.tonic.j2cs.types.Coercions;
import com.tonic.j2cs.types.CsType;
import com.tonic.j2cs.types.TypeMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypeMapperTest {

    private final TypeMapper mapper = new TypeMapper();

    @Test
    void primitiveStorageTypes() {
        assertEquals("int", mapper.storageType("Z").csText());
        assertEquals("sbyte", mapper.storageType("B").csText());
        assertEquals("short", mapper.storageType("S").csText());
        assertEquals("char", mapper.storageType("C").csText());
        assertEquals("int", mapper.storageType("I").csText());
        assertEquals("long", mapper.storageType("J").csText());
        assertEquals("float", mapper.storageType("F").csText());
        assertEquals("double", mapper.storageType("D").csText());
        assertEquals("void", mapper.storageType("V").csText());
        assertEquals(CsType.Kind.VOID, mapper.storageType("V").kind());
    }

    @Test
    void subIntTypesComputeAsInt() {
        assertEquals("int", mapper.computeType("Z").csText());
        assertEquals("int", mapper.computeType("B").csText());
        assertEquals("int", mapper.computeType("S").csText());
        assertEquals("int", mapper.computeType("C").csText());
        assertEquals("long", mapper.computeType("J").csText());
    }

    @Test
    void referenceTypesAreGloballyQualified() {
        CsType string = mapper.storageType("Ljava/lang/String;");
        assertEquals("global::java.lang.String", string.csText());
        assertEquals(CsType.Kind.REF, string.kind());
    }

    @Test
    void arrayTypesUseElementStorage() {
        assertEquals("int[]", mapper.storageType("[I").csText());
        assertEquals("sbyte[]", mapper.storageType("[B").csText());
        assertEquals("double[][]", mapper.storageType("[[D").csText());
        assertEquals("global::java.lang.String[]", mapper.storageType("[Ljava/lang/String;").csText());
        assertEquals(CsType.Kind.ARRAY, mapper.storageType("[I").kind());
        assertEquals("int[]", mapper.computeType("[Z").csText());
    }

    @Test
    void methodDescriptorsSplitIntoParamsAndReturn() {
        assertEquals("int", mapper.returnType("(II)I").csText());
        assertEquals("void", mapper.returnType("()V").csText());
        List<CsType> params = mapper.paramTypes("(ILjava/lang/String;[J)V");
        assertEquals(3, params.size());
        assertEquals("int", params.get(0).csText());
        assertEquals("global::java.lang.String", params.get(1).csText());
        assertEquals("long[]", params.get(2).csText());
        assertEquals(0, mapper.paramTypes("()V").size());
    }

    @Test
    void defaultLiteralsMatchComputeTypes() {
        assertEquals("0", mapper.computeType("I").defaultLiteral());
        assertEquals("0L", mapper.computeType("J").defaultLiteral());
        assertEquals("0f", mapper.computeType("F").defaultLiteral());
        assertEquals("0d", mapper.computeType("D").defaultLiteral());
        assertEquals("null", mapper.computeType("Ljava/lang/String;").defaultLiteral());
        assertEquals("null", mapper.computeType("[I").defaultLiteral());
    }

    @Test
    void coercionsNarrowSubIntStores() {
        assertEquals("(sbyte)(v1)", Coercions.coerce(new TypeMapper().storageType("B"), "v1"));
        assertEquals("(short)(v1)", Coercions.coerce(new TypeMapper().storageType("S"), "v1"));
        assertEquals("(char)(v1)", Coercions.coerce(new TypeMapper().storageType("C"), "v1"));
        assertEquals("v1", Coercions.coerce(new TypeMapper().storageType("Z"), "v1"));
        assertEquals("v1", Coercions.coerce(new TypeMapper().storageType("I"), "v1"));
        assertEquals("v1", Coercions.coerce(new TypeMapper().storageType("Ljava/lang/String;"), "v1"));
    }
}
