package com.tonic.j2cs.frontend;

import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.ir.IRInstruction;
import com.tonic.analysis.ssa.lower.CopyInfo;
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
 * slot (union-find over the phi-copy mapping written by PhiEliminator); every other value gets
 * its own slot. Slot ids are dense and assigned in deterministic traversal order: parameters
 * first, then instruction results in reverse post order.
 */
public final class PhiCoalescer {

    private PhiCoalescer() {
    }

    public static LoweredMethod coalesce(IRMethod ir, TypeMapper typeMapper) {
        Map<SSAValue, SSAValue> parent = new HashMap<>();
        for (Map.Entry<SSAValue, List<CopyInfo>> entry : ir.getPhiCopyMapping().entrySet()) {
            for (CopyInfo copy : entry.getValue()) {
                union(parent, entry.getKey(), copy.copyValue());
            }
        }

        Set<SSAValue> members = new LinkedHashSet<>(ir.getParameters());
        for (IRBlock block : ir.getReversePostOrder()) {
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

        Map<SSAValue, Integer> slotByRoot = new LinkedHashMap<>();
        Map<SSAValue, Integer> slotOf = new LinkedHashMap<>();
        List<SlotDecl> slots = new ArrayList<>();
        Map<Integer, String> slotTypes = new HashMap<>();
        for (SSAValue value : members) {
            SSAValue root = find(parent, value);
            Integer slot = slotByRoot.get(root);
            if (value.getType() == null) {
                throw new J2csException("value v" + value.getId() + " has no type");
            }
            String csText = typeMapper.computeType(value.getType()).csText();
            if (slot == null) {
                slot = slots.size();
                slotByRoot.put(root, slot);
                slots.add(new SlotDecl(slot, value.getType()));
                slotTypes.put(slot, csText);
            } else if (!slotTypes.get(slot).equals(csText)) {
                throw new J2csException("phi group type conflict in slot s" + slot + ": "
                        + slotTypes.get(slot) + " vs " + csText + " (v" + value.getId() + ")");
            }
            slotOf.put(value, slot);
        }
        return new LoweredMethod(ir, slotOf, slots);
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
