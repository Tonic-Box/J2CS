package com.tonic.j2cs;

/**
 * Failure with a user-facing message; aborts the current transpile run.
 */
public class J2csException extends RuntimeException {

    public J2csException(String message) {
        super(message);
    }

    public J2csException(String message, Throwable cause) {
        super(message, cause);
    }
}
