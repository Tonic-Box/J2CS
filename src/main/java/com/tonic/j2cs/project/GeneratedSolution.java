package com.tonic.j2cs.project;

import java.util.Map;
import java.util.Set;

/**
 * Everything the solution generator writes to disk. Keys of the file maps are dotted class
 * names; values are C# source text. bootstrappedInternal names the classes generated from JDK
 * bytecode, whose hand-written shim source must not also be copied.
 */
public record GeneratedSolution(
        Map<String, String> genFiles,
        Map<String, String> stubFiles,
        String programCs,
        Set<String> bootstrappedInternal,
        boolean usesGui) {
}
