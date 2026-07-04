package com.tonic.j2cs.pipeline;

import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.ir.IRInstruction;
import com.tonic.analysis.ssa.ir.InvokeInstruction;
import com.tonic.analysis.ssa.ir.TypeCheckInstruction;
import com.tonic.analysis.ssa.value.SSAValue;
import java.util.List;

/**
 * Collapses the Object round-trip of {@code (T[]) someArray.clone()}. Array clone is typed
 * {@code ()Ljava/lang/Object;} in bytecode, so its result lands in an Object slot and the array
 * receiver is otherwise rejected. When the clone result is used solely by a checkcast to an array
 * type, this retypes the invoke result to that array type: the checkcast becomes an identity, the
 * slot becomes a real C# array, and the emitter can render {@code (T[]) receiver.Clone()} directly.
 */
public final class ArrayCloneRetypePass implements IrPass {

    @Override
    public String name() {
        return "array-clone-retype";
    }

    @Override
    public void run(IRMethod ir) {
        for (IRBlock block : ir.getBlocks()) {
            for (IRInstruction instr : block.getInstructions()) {
                if (!(instr instanceof InvokeInstruction invoke)) {
                    continue;
                }
                String owner = invoke.getOwner();
                if (owner == null || !owner.startsWith("[")
                        || !"clone".equals(invoke.getName())
                        || !"()Ljava/lang/Object;".equals(invoke.getDescriptor())) {
                    continue;
                }
                SSAValue result = invoke.getResult();
                if (result == null) {
                    continue;
                }
                List<IRInstruction> uses = result.getUses();
                if (uses.size() != 1 || !(uses.get(0) instanceof TypeCheckInstruction cast)
                        || !cast.isCast() || cast.getTargetType() == null
                        || !cast.getTargetType().isArray()) {
                    continue;
                }
                SSAValue retyped = new SSAValue(cast.getTargetType());
                retyped.setDefinition(invoke);
                invoke.setResult(retyped);
                result.replaceAllUsesWith(retyped);
                retyped.addUse(cast);
            }
        }
    }
}
