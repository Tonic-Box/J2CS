package com.tonic.j2cs.frontend;

import com.tonic.analysis.ssa.cfg.ExceptionHandler;
import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.ir.IRInstruction;
import com.tonic.analysis.ssa.lower.CopyInfo;
import com.tonic.analysis.ssa.type.IRType;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.j2cs.J2csException;
import com.tonic.j2cs.model.LoweredMethod;
import com.tonic.j2cs.model.SlotDecl;
import com.tonic.j2cs.types.TypeMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Groups SSA values into C# local slots. A phi result and every copy that feeds it share one
 * slot (union-find over the phi-copy mapping written by PhiEliminator), handler-phi webs are
 * merged via the captured union pairs, and every other value gets its own slot. Slot ids are
 * dense and assigned in deterministic traversal order over the full block order, which also
 * covers exception-handler subgraphs unreachable by normal edges.
 */
public final class PhiCoalescer {

    private PhiCoalescer() {
    }

    public static LoweredMethod coalesce(IRMethod ir, TypeMapper typeMapper) {
        return coalesce(ir, typeMapper, HandlerSupport.Captures.empty());
    }

    public static LoweredMethod coalesce(IRMethod ir, TypeMapper typeMapper,
                                         HandlerSupport.Captures captures) {
        Map<SSAValue, SSAValue> parent = new HashMap<>();
        for (Map.Entry<SSAValue, List<CopyInfo>> entry : ir.getPhiCopyMapping().entrySet()) {
            for (CopyInfo copy : entry.getValue()) {
                union(parent, entry.getKey(), copy.copyValue());
            }
        }
        for (HandlerSupport.UnionPair pair : captures.unionPairs()) {
            union(parent, pair.a(), pair.b());
        }

        List<IRBlock> blockOrder = HandlerSupport.buildBlockOrder(ir);
        Set<SSAValue> members = new LinkedHashSet<>(ir.getParameters());
        for (IRBlock block : blockOrder) {
            for (IRInstruction instr : block.getInstructions()) {
                if (instr.getResult() != null) {
                    members.add(instr.getResult());
                }
            }
        }
        for (Map.Entry<SSAValue, List<CopyInfo>> entry : ir.getPhiCopyMapping().entrySet()) {
            members.add(entry.getKey());
            for (CopyInfo copy : entry.getValue()) {
                members.add(copy.copyValue());
            }
        }
        for (ExceptionHandler handler : ir.getExceptionHandlers()) {
            SSAValue handlerValue = ir.getHandlerExceptionValue(handler.getHandlerBlock());
            if (handlerValue != null) {
                members.add(handlerValue);
            }
        }

        Map<SSAValue, Integer> slotByRoot = new LinkedHashMap<>();
        Map<SSAValue, Integer> slotOf = new LinkedHashMap<>();
        List<SlotDecl> slots = new ArrayList<>();
        Map<Integer, String> slotTypes = new HashMap<>();
        for (SSAValue value : members) {
            SSAValue root = find(parent, value);
            Integer slot = slotByRoot.get(root);
            IRType effectiveType = captures.typeOverrides().getOrDefault(value, value.getType());
            if (effectiveType == null) {
                throw new J2csException("value v" + value.getId() + " has no type");
            }
            String csText = typeMapper.computeType(effectiveType).csText();
            if (slot == null) {
                slot = slots.size();
                slotByRoot.put(root, slot);
                slots.add(new SlotDecl(slot, effectiveType));
                slotTypes.put(slot, csText);
            } else if (!slotTypes.get(slot).equals(csText)) {
                throw new J2csException("phi group type conflict in slot s" + slot + ": "
                        + slotTypes.get(slot) + " vs " + csText + " (v" + value.getId() + ")");
            }
            slotOf.put(value, slot);
        }
        return new LoweredMethod(ir, slotOf, slots, blockOrder,
                Set.copyOf(captures.originalBlocks().isEmpty()
                        ? new LinkedHashSet<>(ir.getBlocks())
                        : captures.originalBlocks()));
    }

    private static SSAValue find(Map<SSAValue, SSAValue> parent, SSAValue value) {
        SSAValue root = value;
        while (parent.containsKey(root)) {
            root = parent.get(root);
        }
        SSAValue current = value;
        while (parent.containsKey(current)) {
            SSAValue next = parent.get(current);
            parent.put(current, root);
            current = next;
        }
        return root;
    }

    private static void union(Map<SSAValue, SSAValue> parent, SSAValue a, SSAValue b) {
        SSAValue rootA = find(parent, a);
        SSAValue rootB = find(parent, b);
        if (!rootA.equals(rootB)) {
            parent.put(rootB, rootA);
        }
    }
}
