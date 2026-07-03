package com.tonic.j2cs.emit;

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
        throw new UnsupportedBodyException("constant not supported in M0: "
                + constant.getClass().getSimpleName());
    }
}
