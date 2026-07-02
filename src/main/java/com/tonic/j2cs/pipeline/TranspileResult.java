package com.tonic.j2cs.pipeline;

import com.tonic.j2cs.frontend.LoadedInput;
import com.tonic.j2cs.report.TranspileReport;
import java.nio.file.Path;

/**
 * Outcome of one transpile run.
 */
public record TranspileResult(
        LoadedInput input,
        TranspileReport report,
        Path reportPath,
        Path appDir) {
}
