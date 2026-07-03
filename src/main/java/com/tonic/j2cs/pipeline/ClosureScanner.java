package com.tonic.j2cs.pipeline;

import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.ir.IRInstruction;
import com.tonic.analysis.ssa.ir.NewArrayInstruction;
import com.tonic.analysis.ssa.ir.TypeCheckInstruction;
import com.tonic.j2cs.model.LoweredMethod;
import com.tonic.j2cs.model.MethodPlan;
import com.tonic.j2cs.model.SlotDecl;
import com.tonic.parser.ClassFile;
import com.tonic.parser.FieldEntry;
import com.tonic.parser.MethodEntry;
import java.util.Map;
import java.util.Set;

/**
 * Collects every class internal name a generated class will reference: field and method
 * descriptors (signatures are emitted even for unsupported bodies), slot types, cast targets,
 * and array element types of supported bodies. Types that resolve neither to the input nor to
 * the shim become empty stubs so the solution always compiles.
 */
public final class ClosureScanner {

    private ClosureScanner() {
    }

    public static void collectReferencedTypes(ClassFile classFile,
                                              Map<MethodEntry, MethodPlan> plans,
                                              Set<String> out) {
        out.add(classFile.getSuperClassName());
        out.addAll(classFile.getInterfaceNames());
        for (FieldEntry field : classFile.getFields()) {
            collectFromDescriptor(field.getDesc(), out);
        }
        for (MethodEntry method : classFile.getMethods()) {
            collectFromDescriptor(method.getDesc(), out);
            MethodPlan plan = plans.get(method);
            if (plan instanceof MethodPlan.Supported supported) {
                collectFromBody(supported.method(), out);
            }
        }
    }

    private static void collectFromBody(LoweredMethod lowered, Set<String> out) {
        for (SlotDecl slot : lowered.slots()) {
            collectFromDescriptor(slot.computeType().getDescriptor(), out);
        }
        for (com.tonic.analysis.ssa.cfg.ExceptionHandler handler : lowered.ir().getExceptionHandlers()) {
            if (handler.getCatchType() != null) {
                out.add(handler.getCatchType().getInternalName());
            }
        }
        for (IRBlock block : lowered.ir().getBlocks()) {
            for (IRInstruction instr : block.getInstructions()) {
                if (instr instanceof TypeCheckInstruction typeCheck) {
                    collectFromDescriptor(typeCheck.getTargetType().getDescriptor(), out);
                } else if (instr instanceof NewArrayInstruction newArray) {
                    collectFromDescriptor(newArray.getElementType().getDescriptor(), out);
                }
            }
        }
    }

    static void collectFromDescriptor(String descriptor, Set<String> out) {
        int i = 0;
        while ((i = descriptor.indexOf('L', i)) >= 0) {
            int end = descriptor.indexOf(';', i);
            if (end < 0) {
                return;
            }
            out.add(descriptor.substring(i + 1, end));
            i = end + 1;
        }
    }
}
