package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.value.Constant;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.analysis.ssa.value.Value;
import java.util.Map;

/**
 * Renders IR operands as C# expressions: SSA values become their slot locals, constants
 * render inline.
 */
public final class ValueNames {

    private final Map<SSAValue, Integer> slotOf;

    public ValueNames(Map<SSAValue, Integer> slotOf) {
        this.slotOf = slotOf;
    }

    public String ref(Value value) {
        if (value instanceof SSAValue ssa) {
            Integer slot = slotOf.get(ssa);
            if (slot == null) {
                throw new UnsupportedBodyException("operand v" + ssa.getId() + " has no slot");
            }
            return "s" + slot;
        }
        if (value instanceof Constant constant) {
            return ConstRenderer.render(constant);
        }
        throw new UnsupportedBodyException("unsupported operand kind: "
                + (value == null ? "null" : value.getClass().getSimpleName()));
    }
}
