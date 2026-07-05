package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.value.ClassConstant;
import com.tonic.analysis.ssa.value.Constant;
import com.tonic.analysis.ssa.value.DoubleConstant;
import com.tonic.analysis.ssa.value.FloatConstant;
import com.tonic.analysis.ssa.value.IntConstant;
import com.tonic.analysis.ssa.value.LongConstant;
import com.tonic.analysis.ssa.value.NullConstant;
import com.tonic.analysis.ssa.value.StringConstant;

/**
 * Renders IR constants as C# expressions. Floating-point constants are emitted bit-exact via
 * BitConverter so NaN payloads, infinities, and negative zero always round-trip.
 */
public final class ConstRenderer {

    private ConstRenderer() {
    }

    public static String render(Constant constant) {
        if (constant instanceof IntConstant i) {
            int v = i.getValue();
            return v == Integer.MIN_VALUE ? "(-2147483647 - 1)" : String.valueOf(v);
        }
        if (constant instanceof LongConstant l) {
            long v = l.getValue();
            return v == Long.MIN_VALUE ? "(-9223372036854775807L - 1L)" : v + "L";
        }
        if (constant instanceof FloatConstant f) {
            return "global::System.BitConverter.Int32BitsToSingle("
                    + Float.floatToRawIntBits(f.getValue()) + ")";
        }
        if (constant instanceof DoubleConstant d) {
            return "global::System.BitConverter.Int64BitsToDouble("
                    + Double.doubleToRawLongBits(d.getValue()) + "L)";
        }
        if (constant instanceof StringConstant s) {
            return "global::java.lang.String.Intern(" + CsStrings.quote(s.getValue()) + ")";
        }
        if (constant instanceof NullConstant) {
            return "null";
        }
        if (constant instanceof ClassConstant c) {
            return "global::java.lang.Class.Of(" + CsStrings.quote(c.getClassName().replace('/', '.')) + ")";
        }
        throw new UnsupportedBodyException("constant not supported: "
                + constant.getClass().getSimpleName());
    }

    /** Renders a boxed literal value with the same bit-exact strategy. */
    public static String renderValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof Integer i) {
            return i == Integer.MIN_VALUE ? "(-2147483647 - 1)" : String.valueOf(i);
        }
        if (value instanceof Long l) {
            return l == Long.MIN_VALUE ? "(-9223372036854775807L - 1L)" : l + "L";
        }
        if (value instanceof Float f) {
            return "global::System.BitConverter.Int32BitsToSingle(" + Float.floatToRawIntBits(f) + ")";
        }
        if (value instanceof Double d) {
            return "global::System.BitConverter.Int64BitsToDouble(" + Double.doubleToRawLongBits(d) + "L)";
        }
        if (value instanceof String s) {
            return "global::java.lang.String.Intern(" + CsStrings.quote(s) + ")";
        }
        if (value instanceof Character c) {
            return String.valueOf((int) c);
        }
        if (value instanceof Boolean b) {
            return b ? "1" : "0";
        }
        throw new UnsupportedBodyException("literal not supported: " + value.getClass().getSimpleName());
    }
}
