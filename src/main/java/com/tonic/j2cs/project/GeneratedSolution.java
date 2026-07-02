package com.tonic.j2cs.project;

import java.util.Map;

/**
 * Everything the solution generator writes to disk. Keys of the file maps are dotted class
 * names; values are C# source text.
 */
public record GeneratedSolution(
        Map<String, String> genFiles,
        Map<String, String> stubFiles,
        String programCs) {
}
