package com.tonic.j2cs.model;

import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.value.SSAValue;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A method after SSA destruction and phi coalescing, ready for C# body emission. blockOrder
 * covers every emittable block including exception-handler subgraphs, which plain reverse post
 * order from the entry never reaches; originalBlocks distinguishes lifted blocks from the
 * synthetic split blocks phi elimination inserts.
 */
public record LoweredMethod(
        IRMethod ir,
        Map<SSAValue, Integer> slotOf,
        List<SlotDecl> slots,
        List<IRBlock> blockOrder,
        Set<IRBlock> originalBlocks) {
}
