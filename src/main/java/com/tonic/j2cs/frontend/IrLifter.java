package com.tonic.j2cs.frontend;

import com.tonic.analysis.ssa.IRPrinter;
import com.tonic.analysis.ssa.SSA;
import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.ir.IRInstruction;
import com.tonic.analysis.ssa.ir.LoadLocalInstruction;
import com.tonic.analysis.ssa.ir.StoreLocalInstruction;
import com.tonic.analysis.ssa.lower.PhiEliminator;
import com.tonic.analysis.ssa.transform.DeadCodeElimination;
import com.tonic.j2cs.model.LoweredMethod;
import com.tonic.j2cs.model.MethodPlan;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;
import com.tonic.util.Modifiers;
import java.util.ArrayList;
import java.util.List;

/**
 * Lifts a method to SSA and destructs it for C# emission: strip load/store-local artifacts,
 * drop unreachable blocks, eliminate phis into predecessor copies, then coalesce phi groups
 * into slots. Mirrors YABR's BytecodeLowerer steps 1-3 and swaps the bytecode backend for ours.
 * Any failure degrades to an Unsupported plan; it never aborts the run.
 */
public final class IrLifter {

    private final TypeMapper typeMapper;
    private final boolean dumpIr;

    public IrLifter(TypeMapper typeMapper, boolean dumpIr) {
        this.typeMapper = typeMapper;
        this.dumpIr = dumpIr;
    }

    public MethodPlan lower(ClassFile classFile, MethodEntry method) {
        if (method.getCodeAttribute() == null) {
            return new MethodPlan.Unsupported(Modifiers.isNative(method.getAccess())
                    ? "native method"
                    : "no bytecode (abstract)");
        }
        try {
            IRMethod ir = new SSA(classFile.getConstPool())
                    .withExceptionLocalResolution()
                    .lift(method);
            if (dumpIr) {
                System.out.println(IRPrinter.format(ir));
            }
            if (!ir.getExceptionHandlers().isEmpty()) {
                return new MethodPlan.Unsupported("try/catch not supported yet");
            }
            stripLocalArtifacts(ir);
            DeadCodeElimination.removeUnreachableBlocks(ir);
            HandlerSupport.Captures captures = HandlerSupport.capture(ir);
            new PhiEliminator().eliminate(ir);
            LoweredMethod lowered = PhiCoalescer.coalesce(ir, typeMapper, captures);
            return new MethodPlan.Supported(lowered);
        } catch (RuntimeException e) {
            return new MethodPlan.Unsupported(
                    "lift failed (" + e.getClass().getSimpleName() + "): " + e.getMessage());
        }
    }

    private static void stripLocalArtifacts(IRMethod method) {
        for (IRBlock block : method.getBlocks()) {
            List<IRInstruction> toRemove = new ArrayList<>();
            for (IRInstruction instr : block.getInstructions()) {
                if (instr instanceof LoadLocalInstruction || instr instanceof StoreLocalInstruction) {
                    toRemove.add(instr);
                }
            }
            for (IRInstruction instr : toRemove) {
                block.removeInstruction(instr);
            }
        }
    }
}
