package com.tonic.j2cs.report;

import com.tonic.j2cs.J2csException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Renders a TranspileReport as j2cs-report.txt in the output directory.
 */
public final class ReportWriter {

    public Path write(TranspileReport report, Path outDir) {
        StringBuilder sb = new StringBuilder();
        sb.append("j2cs transpile report\n");
        sb.append("input: ").append(report.getInput()).append('\n');
        sb.append("entry: ").append(report.getEntryClass()).append('\n');
        if (report.getStructuredBodies() + report.getClassicBodies() > 0) {
            sb.append("bodies: structured ").append(report.getStructuredBodies())
                    .append(", classic fallback ").append(report.getClassicBodies()).append('\n');
        }
        if (!report.getClassicFallbackReasons().isEmpty()) {
            section(sb, "structured fallbacks (" + report.getClassicFallbackReasons().size() + ")",
                    report.getClassicFallbackReasons());
        }
        section(sb, "classes (" + report.getClasses().size() + ")", report.getClasses());
        section(sb, "unsupported methods (" + report.getUnsupportedMethods().size() + ")", report.getUnsupportedMethods());
        section(sb, "unsupported by reason (" + report.getUnsupportedMethods().size() + ")", report.unsupportedSummary());
        section(sb, "stubbed types (" + report.getStubbedTypes().size() + ")", report.getStubbedTypes());
        section(sb, "failed classes (" + report.getFailedClasses().size() + ")", report.getFailedClasses());
        section(sb, "known divergences (" + report.getDivergences().size() + ")", report.getDivergences());
        Path reportPath = outDir.resolve("j2cs-report.txt");
        try {
            Files.createDirectories(outDir);
            Files.writeString(reportPath, sb.toString());
        } catch (IOException e) {
            throw new J2csException("failed to write report " + reportPath + ": " + e.getMessage(), e);
        }
        return reportPath;
    }

    private static void section(StringBuilder sb, String title, List<String> lines) {
        sb.append('\n').append(title).append('\n');
        for (String line : lines) {
            sb.append("  ").append(line).append('\n');
        }
    }
}
