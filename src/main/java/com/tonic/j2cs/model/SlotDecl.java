package com.tonic.j2cs.model;

import com.tonic.analysis.ssa.type.IRType;

/**
 * One C# local variable to declare at the top of an emitted method body.
 */
public record SlotDecl(int slotId, IRType computeType) {
}
