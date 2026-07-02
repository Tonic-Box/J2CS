package com.tonic.j2cs.model;

/**
 * Per-method emission decision: a lowered body, or a NotSupportedException stub with a reason.
 */
public sealed interface MethodPlan {

    record Supported(LoweredMethod method) implements MethodPlan {
    }

    record Unsupported(String reason) implements MethodPlan {
    }
}
