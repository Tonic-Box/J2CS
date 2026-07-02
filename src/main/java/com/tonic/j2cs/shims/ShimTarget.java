package com.tonic.j2cs.shims;

/**
 * The C# member a shim-mapped Java member resolves to.
 */
public record ShimTarget(String csMemberName, boolean isStatic) {
}
