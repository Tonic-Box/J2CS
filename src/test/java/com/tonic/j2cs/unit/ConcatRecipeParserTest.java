package com.tonic.j2cs.unit;

import com.tonic.j2cs.emit.ConcatRecipeParser;
import com.tonic.j2cs.emit.ConcatRecipeParser.Part;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConcatRecipeParserTest {

    private static final String ARG = String.valueOf(ConcatRecipeParser.ARG_MARKER);
    private static final String CONST = String.valueOf(ConcatRecipeParser.CONST_MARKER);

    @Test
    void plainArgsOnly() {
        List<Part> parts = ConcatRecipeParser.parse(ARG + ARG, List.of());
        assertEquals(List.of(new Part.Arg(0), new Part.Arg(1)), parts);
    }

    @Test
    void literalsBetweenArgs() {
        List<Part> parts = ConcatRecipeParser.parse(ARG + ": " + ARG + "!", List.of());
        assertEquals(List.of(new Part.Arg(0), new Part.Literal(": "), new Part.Arg(1),
                new Part.Literal("!")), parts);
    }

    @Test
    void bootstrapConstantsFoldIntoLiterals() {
        List<Part> parts = ConcatRecipeParser.parse("a" + CONST + "b" + ARG, List.of("X"));
        assertEquals(List.of(new Part.Literal("aXb"), new Part.Arg(0)), parts);
    }

    @Test
    void adjacentConstantAndArgKeepOrder() {
        List<Part> parts = ConcatRecipeParser.parse(CONST + ARG + CONST, List.of("<", ">"));
        assertEquals(List.of(new Part.Literal("<"), new Part.Arg(0), new Part.Literal(">")), parts);
    }

    @Test
    void missingConstantsAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> ConcatRecipeParser.parse(CONST + CONST, List.of("only-one")));
    }

    @Test
    void unusedConstantsAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> ConcatRecipeParser.parse(ARG, List.of("unused")));
    }

    @Test
    void emptyRecipeYieldsNoParts() {
        assertEquals(List.of(), ConcatRecipeParser.parse("", List.of()));
    }
}
