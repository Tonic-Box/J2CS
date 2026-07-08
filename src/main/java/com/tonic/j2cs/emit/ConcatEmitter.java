package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.ir.InvokeInstruction;
import com.tonic.analysis.ssa.value.Constant;
import com.tonic.analysis.ssa.value.StringConstant;
import com.tonic.analysis.ssa.value.Value;
import com.tonic.j2cs.types.TypeMapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Renders a string-concat invokedynamic (makeConcat/makeConcatWithConstants) as a
 * String.Wrap(System.String.Concat(...)) expression over the recipe's parts.
 */
public final class ConcatEmitter {

    private final ValueNames names;

    ConcatEmitter(ValueNames names) {
        this.names = names;
    }

    String render(InvokeInstruction instr) {
        List<String> paramDescs = TypeMapper.splitParams(instr.getDescriptor());
        List<Value> args = instr.getMethodArguments();
        if (args.size() != paramDescs.size()) {
            throw new UnsupportedBodyException("concat argument count mismatch: " + instr.getDescriptor());
        }
        List<ConcatRecipeParser.Part> parts = parts(instr, args.size());
        StringBuilder sb = new StringBuilder("global::java.lang.String.Wrap(global::System.String.Concat(new string[] { ");
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            ConcatRecipeParser.Part part = parts.get(i);
            if (part instanceof ConcatRecipeParser.Part.Literal literal) {
                sb.append(CsStrings.quote(literal.text()));
            } else if (part instanceof ConcatRecipeParser.Part.Arg arg) {
                if (arg.index() >= args.size()) {
                    throw new UnsupportedBodyException("concat recipe references argument " + arg.index()
                            + " but only " + args.size() + " are supplied");
                }
                sb.append(conversion(paramDescs.get(arg.index()), args.get(arg.index())));
            }
        }
        sb.append(" }))");
        return sb.toString();
    }

    private List<ConcatRecipeParser.Part> parts(InvokeInstruction instr, int argCount) {
        if (instr.getName().equals("makeConcat")) {
            List<ConcatRecipeParser.Part> parts = new ArrayList<>();
            for (int i = 0; i < argCount; i++) {
                parts.add(new ConcatRecipeParser.Part.Arg(i));
            }
            return parts;
        }
        List<Constant> bootstrapArgs = instr.getBootstrapInfo().getBootstrapArguments();
        if (bootstrapArgs.isEmpty() || !(bootstrapArgs.get(0) instanceof StringConstant recipe)) {
            throw new UnsupportedBodyException("concat bootstrap has no recipe string");
        }
        List<String> constants = new ArrayList<>();
        for (int i = 1; i < bootstrapArgs.size(); i++) {
            if (!(bootstrapArgs.get(i) instanceof StringConstant s)) {
                throw new UnsupportedBodyException("non-string concat bootstrap constant: "
                        + bootstrapArgs.get(i).getClass().getSimpleName());
            }
            constants.add(s.getValue());
        }
        try {
            return ConcatRecipeParser.parse(recipe.getValue(), constants);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedBodyException("unparseable concat recipe: " + e.getMessage());
        }
    }

    private String conversion(String desc, Value arg) {
        if (desc.charAt(0) == '[') {
            // Java concatenates an array via its Object.toString ("[I@hash"); box then stringify.
            return "global::java.lang.JRuntime.Str(global::java.lang.JRuntime.Box("
                    + names.ref(arg) + ", \"" + desc + "\"))";
        }
        return stringConversion(desc, names.ref(arg));
    }

    /** JRuntime string conversion of a value by its JVM descriptor. */
    public static String stringConversion(String desc, String expr) {
        String jr = "global::java.lang.JRuntime";
        return switch (desc.charAt(0)) {
            case 'Z' -> jr + ".StrZ(" + expr + ")";
            case 'C' -> jr + ".Str((char)(" + expr + "))";
            case 'B', 'S', 'I', 'J', 'F', 'D', 'L' -> jr + ".Str(" + expr + ")";
            default -> throw new UnsupportedBodyException("array in string concat not supported");
        };
    }
}
