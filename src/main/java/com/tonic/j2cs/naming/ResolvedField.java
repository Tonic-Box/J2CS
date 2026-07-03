package com.tonic.j2cs.naming;

/**
 * Outcome of resolving a field reference against the closure, JVMS 5.4.3.2-style.
 */
public sealed interface ResolvedField {

    record AppField(String declaringInternal, String csName) implements ResolvedField {
    }

    record Unresolved(String reason) implements ResolvedField {
    }
}
