package com.tonic.j2cs.pipeline;

import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.frontend.InputLoader;
import com.tonic.j2cs.frontend.LoadedInput;
import com.tonic.j2cs.report.ReportWriter;
import com.tonic.j2cs.report.TranspileReport;
import com.tonic.parser.ClassFile;

import java.nio.file.Path;

/**
 * Orchestrates one run: load input, transpile classes, generate the solution, write the report.
 */
public final class Transpiler {

    public TranspileResult transpile(CliOptions options) {
        TranspileReport report = new TranspileReport();
        LoadedInput input = new InputLoader().load(options.input(), options.mainOverride());
        report.setInput(options.input().toString());
        report.setEntryClass(input.entryClassInternalName());
        for (ClassFile cf : input.appClasses()) {
            report.classDiscovered(cf.getClassName());
        }
        Path reportPath = new ReportWriter().write(report, options.outDir());
        return new TranspileResult(input, report, reportPath);
    }
}
