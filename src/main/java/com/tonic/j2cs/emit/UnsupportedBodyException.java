package com.tonic.j2cs.emit;

/**
 * Raised mid-emission when a body uses a construct outside M0 scope; the caller degrades the
 * method to a NotSupportedException stub with this reason.
 */
public final class UnsupportedBodyException extends RuntimeException {

    public UnsupportedBodyException(String reason) {
        super(reason);
    }
}
