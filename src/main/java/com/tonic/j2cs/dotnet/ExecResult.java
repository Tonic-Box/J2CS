package com.tonic.j2cs.dotnet;

import java.util.ArrayList;
import java.util.List;

/**
 * Captured outcome of an external process run.
 */
public record ExecResult(int exitCode, String stdout, String stderr) {

    public boolean succeeded() {
        return exitCode == 0;
    }

    public String combinedTail(int maxLines) {
        List<String> lines = new ArrayList<>();
        stdout.lines().forEach(lines::add);
        stderr.lines().forEach(lines::add);
        int from = Math.max(0, lines.size() - maxLines);
        return String.join("\n", lines.subList(from, lines.size()));
    }
}
