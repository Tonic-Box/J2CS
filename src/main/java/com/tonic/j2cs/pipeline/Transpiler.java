package com.tonic.j2cs.pipeline;

import com.tonic.j2cs.bootstrap.BootstrapPolicy;
import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.emit.ClassEmitter;
import com.tonic.j2cs.emit.EntryPointEmitter;
import com.tonic.j2cs.emit.SyntheticClasses;
import com.tonic.j2cs.emit.StubEmitter;
import com.tonic.j2cs.frontend.BootstrapLoader;
import com.tonic.j2cs.frontend.InputLoader;
import com.tonic.j2cs.frontend.IrLifter;
import com.tonic.j2cs.frontend.LoadedInput;
import com.tonic.j2cs.model.MethodPlan;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.project.GeneratedSolution;
import com.tonic.j2cs.project.NativeFragmentPackager;
import com.tonic.j2cs.project.SolutionGenerator;
import com.tonic.j2cs.report.ReportWriter;
import com.tonic.j2cs.report.TranspileReport;
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

        List<ClassFile> bootstrapClasses = options.bootstrap().isEmpty()
                ? List.of()
                : new BootstrapLoader().load(input.pool(), options.bootstrap());
        List<ClassFile> allClasses = new ArrayList<>(input.appClasses());
        allClasses.addAll(bootstrapClasses);
        Set<String> bootstrappedInternal = new TreeSet<>();
        for (ClassFile cf : bootstrapClasses) {
            bootstrappedInternal.add(cf.getClassName());
        }

        TypeMapper typeMapper = new TypeMapper();
        ClassHierarchy hierarchy = new ClassHierarchy(allClasses);
        NamingContext naming = new NamingContext(typeMapper, allClasses, hierarchy);
        naming.setBootstrapped(bootstrappedInternal);
        naming.setSuppressedMethods(BootstrapPolicy.suppressedKeys(bootstrappedInternal));
        MethodPlanner planner = new MethodPlanner(new IrLifter(typeMapper, options.dumpIr()), naming);
        Set<String> interfacePositionStubs = collectInterfacePositionStubs(allClasses, naming);
        SyntheticClasses synthetics = new SyntheticClasses();
        ClassEmitter classEmitter = new ClassEmitter(naming, report, interfacePositionStubs, synthetics);
        Map<String, String> genFiles = new LinkedHashMap<>();
        Set<String> referenced = new TreeSet<>();
        for (ClassFile cf : allClasses) {
            report.classDiscovered(cf.getClassName());
            Map<MethodEntry, MethodPlan> plans = planMethods(cf, planner);
            ClosureScanner.collectReferencedTypes(cf, plans, referenced);
            genFiles.put(dottedName(cf.getClassName()), classEmitter.emit(cf, plans));
        }
        genFiles.putAll(synthetics.files());
        Map<String, String> stubFiles = emitStubs(referenced, naming, interfacePositionStubs, report);
        addStandingDivergences(report);
        boolean usesGui = referenced.stream()
                .anyMatch(name -> name.startsWith("javax/swing/") || name.startsWith("java/awt/"));
        String programCs = new EntryPointEmitter().emit(input.entryClassInternalName(), naming, usesGui);
        Path appDir = new SolutionGenerator().generate(options.outDir(),
                new GeneratedSolution(genFiles, stubFiles, programCs, bootstrappedInternal, usesGui));
        new NativeFragmentPackager().copy(appDir, BootstrapPolicy.nativeFragments(bootstrappedInternal));

        Path reportPath = new ReportWriter().write(report, options.outDir());
        return new TranspileResult(input, report, reportPath, appDir);
    }

    private static Set<String> collectInterfacePositionStubs(List<ClassFile> classes, NamingContext naming) {
        Set<String> positions = new TreeSet<>();
        for (ClassFile cf : classes) {
            for (String iface : cf.getInterfaceNames()) {
                if (!naming.isAppClass(iface)) {
                    positions.add(iface);
                }
            }
        }
        return positions;
    }

    private static Map<String, String> emitStubs(Set<String> referenced, NamingContext naming,
                                                 Set<String> interfacePositionStubs,
                                                 TranspileReport report) {
        StubEmitter stubEmitter = new StubEmitter();
        Map<String, String> stubFiles = new LinkedHashMap<>();
        for (String internalName : referenced) {
            if (naming.isAppClass(internalName) || ShimRegistry.isShimType(internalName)) {
                continue;
            }
            boolean asInterface = interfacePositionStubs.contains(internalName);
            report.stubbedType(internalName + (asInterface ? " (interface)" : ""));
            stubFiles.put(dottedName(internalName),
                    asInterface ? stubEmitter.emitInterface(internalName) : stubEmitter.emit(internalName));
        }
        return stubFiles;
    }

    private static void addStandingDivergences(TranspileReport report) {
        report.divergence("float/double formatting uses .NET shortest round-trip digits; rare values differ from Java");
        report.divergence("static initializers use CLR precise timing; matches Java for field/method-triggered init");
        report.divergence("NullPointerException/ClassCastException/ArrayIndexOutOfBoundsException carry no message; helpful-NPE text is unavailable");
        report.divergence("transpiler stub failures throw System.NotSupportedException, which is not catchable as java.lang.Throwable");
        report.divergence("System.arraycopy failure message text differs from the JVM");
        report.divergence("lambdas without captures allocate a fresh instance per evaluation; the JVM may reuse one");
        report.divergence("wrapper valueOf caches match the JVM defaults (Integer/Long/Short/Byte -128..127, Character 0..127, Boolean both); Double/Float are never cached");
        report.divergence("wrapper toString for Double/Float uses the shim float formatting, which can differ from the JVM on rare values");
        report.divergence("HashMap does not treeify large buckets; iteration order matches the JVM for the un-treeified case");
        report.divergence("Map keySet/values/entrySet return snapshots, not live views backed by the map");
        report.divergence("collection index and key exceptions carry no message text");
        report.divergence("shimmed TreeMap/LinkedHashMap use HashMap ordering, not sorted/insertion order; iteration order differs");
        report.divergence("java time/date, UUID, and SecureRandom values are runtime-dependent and do not match a specific JVM run");
        report.divergence("Swing/AWT is rendered via Avalonia; widget structure and behavior are preserved but visual styling and pixel layout differ from the JVM");
    }

    private static Map<MethodEntry, MethodPlan> planMethods(ClassFile cf, MethodPlanner planner) {
        Map<MethodEntry, MethodPlan> plans = new LinkedHashMap<>();
        for (MethodEntry method : cf.getMethods()) {
            plans.put(method, planner.plan(cf, method));
        }
        return plans;
    }

    private static String dottedName(String internalName) {
        return CsNamer.namespaceOf(internalName) + "." + CsNamer.classNameOf(internalName);
    }
}
