package com.tonic.j2cs.dotnet;

import java.io.IOException;

/**
 * Cached probe for a working dotnet CLI on the PATH.
 */
public final class DotnetLocator {

    private static volatile Boolean available;

    private DotnetLocator() {
    }

    public static boolean isAvailable() {
        Boolean cached = available;
        if (cached == null) {
            cached = probe();
            available = cached;
        }
        return cached;
    }

    private static boolean probe() {
        try {
            Process process = new ProcessBuilder("dotnet", "--version")
                    .redirectErrorStream(true)
                    .start();
            process.getInputStream().readAllBytes();
            return process.waitFor() == 0;
        } catch (IOException e) {
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
