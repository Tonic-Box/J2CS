package com.tonic.j2cs.model;

import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.value.SSAValue;
import java.util.List;
import java.util.Map;

/**
 * A method after SSA destruction and phi coalescing, ready for C# body emission.
 */
public record LoweredMethod(
        IRMethod ir,
        Map<SSAValue, Integer> slotOf,
        List<SlotDecl> slots) {
}
