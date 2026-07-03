package com.tonic.j2cs.frontend;

import com.tonic.analysis.ssa.cfg.ExceptionHandler;
import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.ir.PhiInstruction;
import com.tonic.analysis.ssa.type.IRType;
import com.tonic.analysis.ssa.type.ReferenceType;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.analysis.ssa.value.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Pre-phi-elimination captures that exception emission depends on. Handler-block phi results
 * are unioned with their incomings so handler reads see the value written before the throwing
 * instruction rather than a copy inserted after it; handler values shared by several table
 * entries with different catch types are widened to Throwable for slot typing.
 */
public final class HandlerSupport {

    public record UnionPair(SSAValue a, SSAValue b) {
    }

    public record Captures(
            Set<IRBlock> originalBlocks,
            List<UnionPair> unionPairs,
            Map<SSAValue, IRType> typeOverrides) {

        public static Captures empty() {
            return new Captures(Set.of(), List.of(), Map.of());
        }
    }

    private HandlerSupport() {
    }

    public static Captures capture(IRMethod ir) {
        Set<IRBlock> originalBlocks = new LinkedHashSet<>(ir.getBlocks());
        List<UnionPair> pairs = new ArrayList<>();
        for (ExceptionHandler handler : ir.getExceptionHandlers()) {
            IRBlock handlerBlock = handler.getHandlerBlock();
            if (handlerBlock == null) {
                continue;
            }
            for (PhiInstruction phi : handlerBlock.getPhiInstructions()) {
                SSAValue result = phi.getResult();
                if (result == null) {
                    continue;
                }
                for (Value incoming : phi.getIncomingValues().values()) {
                    if (incoming instanceof SSAValue ssa && !ssa.equals(result)) {
                        pairs.add(new UnionPair(result, ssa));
                    }
                }
            }
        }
        return new Captures(originalBlocks, pairs, multiCatchOverrides(ir));
    }

    private static Map<SSAValue, IRType> multiCatchOverrides(IRMethod ir) {
        Map<IRBlock, Set<String>> catchTypesByBlock = new HashMap<>();
        for (ExceptionHandler handler : ir.getExceptionHandlers()) {
            IRBlock block = handler.getHandlerBlock();
            if (block == null) {
                continue;
            }
            String key = handler.getCatchType() == null
                    ? "<any>"
                    : handler.getCatchType().getInternalName();
            catchTypesByBlock.computeIfAbsent(block, b -> new LinkedHashSet<>()).add(key);
        }
        Map<SSAValue, IRType> overrides = new LinkedHashMap<>();
        for (Map.Entry<IRBlock, Set<String>> entry : catchTypesByBlock.entrySet()) {
            if (entry.getValue().size() > 1) {
                SSAValue value = ir.getHandlerExceptionValue(entry.getKey());
                if (value != null) {
                    overrides.put(value, new ReferenceType("java/lang/Throwable"));
                }
            }
        }
        return overrides;
    }

    public static List<IRBlock> buildBlockOrder(IRMethod ir) {
        LinkedHashSet<IRBlock> order = new LinkedHashSet<>(ir.getReversePostOrder());
        for (ExceptionHandler handler : ir.getExceptionHandlers()) {
            IRBlock handlerBlock = handler.getHandlerBlock();
            if (handlerBlock != null && !order.contains(handlerBlock)) {
                appendReversePostOrderFrom(handlerBlock, order);
            }
        }
        return List.copyOf(order);
    }

    private static void appendReversePostOrderFrom(IRBlock root, LinkedHashSet<IRBlock> order) {
        List<IRBlock> postOrder = new ArrayList<>();
        Set<IRBlock> visited = new LinkedHashSet<>(order);
        depthFirst(root, visited, postOrder);
        for (int i = postOrder.size() - 1; i >= 0; i--) {
            order.add(postOrder.get(i));
        }
    }

    private static void depthFirst(IRBlock block, Set<IRBlock> visited, List<IRBlock> postOrder) {
        if (!visited.add(block)) {
            return;
        }
        for (IRBlock successor : block.getSuccessors()) {
            depthFirst(successor, visited, postOrder);
        }
        postOrder.add(Objects.requireNonNull(block));
    }
}
