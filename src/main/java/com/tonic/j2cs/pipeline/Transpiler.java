package com.tonic.j2cs.pipeline;

import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.emit.ClassEmitter;
import com.tonic.j2cs.emit.EntryPointEmitter;
import com.tonic.j2cs.frontend.InputLoader;
import com.tonic.j2cs.frontend.IrLifter;
import com.tonic.j2cs.frontend.LoadedInput;
import com.tonic.j2cs.model.MethodPlan;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.project.GeneratedSolution;
import com.tonic.j2cs.project.SolutionGenerator;
import com.tonic.j2cs.report.ReportWriter;
import com.tonic.j2cs.report.TranspileReport;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Orchestrates one run: load input, transpile classes, generate the solution, write the report.
 */
public final class Transpiler {

    public TranspileResult transpile(CliOptions options) {
        TranspileReport report = new TranspileReport();
        LoadedInput input = new InputLoader().load(options.input(), options.mainOverride());
        report.setInput(options.input().toString());
        report.setEntryClass(input.entryClassInternalName());

        TypeMapper typeMapper = new TypeMapper();
        NamingContext naming = new NamingContext(typeMapper, input.appClasses());
        IrLifter lifter = new IrLifter(typeMapper, options.dumpIr());
        ClassEmitter classEmitter = new ClassEmitter(naming, report);
        Map<String, String> genFiles = new LinkedHashMap<>();
        for (ClassFile cf : input.appClasses()) {
            report.classDiscovered(cf.getClassName());
            genFiles.put(dottedName(cf.getClassName()), classEmitter.emit(cf, planMethods(cf, lifter)));
        }
        String programCs = new EntryPointEmitter().emit(input.entryClassInternalName(), naming);
        Path appDir = new SolutionGenerator().generate(options.outDir(),
                new GeneratedSolution(genFiles, Map.of(), programCs));

        Path reportPath = new ReportWriter().write(report, options.outDir());
        return new TranspileResult(input, report, reportPath, appDir);
    }

    private static Map<MethodEntry, MethodPlan> planMethods(ClassFile cf, IrLifter lifter) {
        Map<MethodEntry, MethodPlan> plans = new LinkedHashMap<>();
        for (MethodEntry method : cf.getMethods()) {
            plans.put(method, lifter.lower(cf, method));
        }
        return plans;
    }

    private static String dottedName(String internalName) {
        return CsNamer.namespaceOf(internalName) + "." + CsNamer.classNameOf(internalName);
    }
}
