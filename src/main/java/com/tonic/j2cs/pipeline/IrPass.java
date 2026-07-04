package com.tonic.j2cs.pipeline;

import com.tonic.analysis.ssa.cfg.IRMethod;

/**
 * A normalization pass over a lifted SSA method, run between lifting and phi/slot lowering so it
 * can rewrite the graph while it is still in SSA form. Passes must be sound and self-contained;
 * they mutate the given IRMethod in place.
 */
public interface IrPass {

    String name();

    void run(IRMethod ir);
}
