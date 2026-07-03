package com.tonic.j2cs.naming;

import com.tonic.j2cs.shims.ShimTarget;

/**
 * Outcome of resolving a method reference against the closure, JVMS-style.
 */
public sealed interface Resolved {

    record AppMethod(String declaringInternal, String csName, boolean viaInterface) implements Resolved {
    }

    record ShimMethod(String ownerInternal, ShimTarget target) implements Resolved {
    }

    record Unresolved(String reason) implements Resolved {
    }
}
