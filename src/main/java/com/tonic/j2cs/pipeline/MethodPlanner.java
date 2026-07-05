package com.tonic.j2cs.pipeline;

import com.tonic.analysis.ssa.cfg.ExceptionHandler;
import com.tonic.j2cs.frontend.IrLifter;
import com.tonic.j2cs.model.LoweredMethod;
import com.tonic.j2cs.model.MethodPlan;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;

/**
 * Produces the emission plan for one method: lift to the lowered SSA form, then demote to
 * Unsupported when a handler catches a type C# cannot represent.
 */
public final class MethodPlanner {

    private final IrLifter lifter;
    private final NamingContext naming;

    public MethodPlanner(IrLifter lifter, NamingContext naming) {
        this.lifter = lifter;
        this.naming = naming;
    }

    public MethodPlan plan(ClassFile classFile, MethodEntry method) {
        MethodPlan plan = lifter.lower(classFile, method);
        if (plan instanceof MethodPlan.Supported supported) {
            String invalidCatch = invalidCatchType(supported.method());
            if (invalidCatch != null) {
                plan = new MethodPlan.Unsupported("catch type not supported: " + invalidCatch);
            }
        }
        return plan;
    }

    private String invalidCatchType(LoweredMethod lowered) {
        for (ExceptionHandler handler : lowered.ir().getExceptionHandlers()) {
            if (handler.getCatchType() == null) {
                continue;
            }
            String internalName = handler.getCatchType().getInternalName();
            if (!naming.isAppClass(internalName) && !ShimRegistry.isExtendable(internalName)) {
                return internalName;
            }
        }
        return null;
    }
}
