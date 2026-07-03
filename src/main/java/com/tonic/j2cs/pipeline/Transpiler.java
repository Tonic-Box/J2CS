package com.tonic.j2cs.pipeline;

import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.emit.ClassEmitter;
import com.tonic.j2cs.emit.EntryPointEmitter;
import com.tonic.j2cs.emit.StubEmitter;
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
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
        ClassHierarchy hierarchy = new ClassHierarchy(input.appClasses());
        NamingContext naming = new NamingContext(typeMapper, input.appClasses(), hierarchy);
        IrLifter lifter = new IrLifter(typeMapper, options.dumpIr());
        ClassEmitter classEmitter = new ClassEmitter(naming, report);
        Map<String, String> genFiles = new LinkedHashMap<>();
        Set<String> referenced = new TreeSet<>();
        for (ClassFile cf : input.appClasses()) {
            report.classDiscovered(cf.getClassName());
            Map<MethodEntry, MethodPlan> plans = planMethods(cf, lifter);
            ClosureScanner.collectReferencedTypes(cf, plans, referenced);
            genFiles.put(dottedName(cf.getClassName()), classEmitter.emit(cf, plans));
        }
        Map<String, String> stubFiles = emitStubs(referenced, naming, report);
        addStandingDivergences(report);
        String programCs = new EntryPointEmitter().emit(input.entryClassInternalName(), naming);
        Path appDir = new SolutionGenerator().generate(options.outDir(),
                new GeneratedSolution(genFiles, stubFiles, programCs));

        Path reportPath = new ReportWriter().write(report, options.outDir());
        return new TranspileResult(input, report, reportPath, appDir);
    }

    private static Map<String, String> emitStubs(Set<String> referenced, NamingContext naming,
                                                 TranspileReport report) {
        StubEmitter stubEmitter = new StubEmitter();
        Map<String, String> stubFiles = new LinkedHashMap<>();
        for (String internalName : referenced) {
            if (naming.isAppClass(internalName) || ShimRegistry.isShimType(internalName)) {
                continue;
            }
            report.stubbedType(internalName);
            stubFiles.put(dottedName(internalName), stubEmitter.emit(internalName));
        }
        return stubFiles;
    }

    private static void addStandingDivergences(TranspileReport report) {
        report.divergence("checkcast failures throw System.InvalidCastException, not java.lang.ClassCastException");
        report.divergence("integer division by zero throws System.DivideByZeroException, not java.lang.ArithmeticException");
        report.divergence("float/double formatting uses .NET shortest round-trip digits; rare values differ from Java");
        report.divergence("array covariance violations throw System.ArrayTypeMismatchException, not java.lang.ArrayStoreException");
        report.divergence("static initializers use CLR precise timing; matches Java for field/method-triggered init");
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
