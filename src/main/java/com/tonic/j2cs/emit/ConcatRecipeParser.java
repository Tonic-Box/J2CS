package com.tonic.j2cs.emit;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses a StringConcatFactory makeConcatWithConstants recipe: U+0001 marks the next dynamic
 * argument, U+0002 splices the next bootstrap constant into the surrounding literal text, and
 * every other char is a literal. Bootstrap constants fold into adjacent literals.
 */
public final class ConcatRecipeParser {

    public static final char ARG_MARKER = (char) 1;
    public static final char CONST_MARKER = (char) 2;

    public sealed interface Part {

        record Literal(String text) implements Part {
        }

        record Arg(int index) implements Part {
        }
    }

    private ConcatRecipeParser() {
    }

    public static List<Part> parse(String recipe, List<String> constants) {
        List<Part> parts = new ArrayList<>();
        StringBuilder literal = new StringBuilder();
        int argIndex = 0;
        int constIndex = 0;
        for (int i = 0; i < recipe.length(); i++) {
            char c = recipe.charAt(i);
            if (c == ARG_MARKER) {
                flush(parts, literal);
                parts.add(new Part.Arg(argIndex++));
            } else if (c == CONST_MARKER) {
                if (constIndex >= constants.size()) {
                    throw new IllegalArgumentException("recipe references more constants than provided");
                }
                literal.append(constants.get(constIndex++));
            } else {
                literal.append(c);
            }
        }
        flush(parts, literal);
        if (constIndex != constants.size()) {
            throw new IllegalArgumentException("recipe uses " + constIndex + " of "
                    + constants.size() + " bootstrap constants");
        }
        return parts;
    }

    private static void flush(List<Part> parts, StringBuilder literal) {
        if (!literal.isEmpty()) {
            parts.add(new Part.Literal(literal.toString()));
            literal.setLength(0);
        }
    }
}
