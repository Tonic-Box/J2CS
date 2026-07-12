package com.tonic.j2cs.pipeline;

import com.tonic.j2cs.bootstrap.BootstrapPolicy;
import com.tonic.j2cs.cli.CliOptions;
import com.tonic.j2cs.emit.AnnotationEmitter;
import com.tonic.j2cs.emit.ClassEmitter;
import com.tonic.j2cs.emit.CsWriter;
import com.tonic.j2cs.emit.EntryPointEmitter;
import com.tonic.j2cs.emit.ReflectionMetadataEmitter;
import com.tonic.j2cs.emit.SyntheticClasses;
import com.tonic.j2cs.emit.StubEmitter;
import com.tonic.j2cs.emit.UsingRewriter;
import com.tonic.util.Modifiers;
import com.tonic.j2cs.frontend.BootstrapLoader;
import com.tonic.j2cs.frontend.InputLoader;
import com.tonic.j2cs.frontend.IrLifter;
import com.tonic.j2cs.frontend.LoadedInput;
import com.tonic.j2cs.model.MethodPlan;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.project.GeneratedSolution;
import com.tonic.j2cs.project.NativeFragmentPackager;
import com.tonic.j2cs.project.SolutionGenerator;
import com.tonic.j2cs.report.ReportWriter;
import com.tonic.j2cs.report.TranspileReport;
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.j2cs.structured.StructuredEmission;
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
        StructuredEmission structured = options.classicBodies()
                ? null
                : new StructuredEmission(naming, synthetics, report);
        if (structured != null) {
            registerEnumSwitchMaps(allClasses);
        }
        ClassEmitter classEmitter = new ClassEmitter(naming, report, interfacePositionStubs, synthetics, structured);
        Map<String, String> genFiles = new LinkedHashMap<>();
        Set<String> referenced = new TreeSet<>();
        List<ClassFile> reflectClasses = new ArrayList<>();
        StubEmitter classStub = new StubEmitter();
        for (ClassFile cf : allClasses) {
            report.classDiscovered(cf.getClassName());
            try {
                Map<MethodEntry, MethodPlan> plans = planMethods(cf, planner);
                ClosureScanner.collectReferencedTypes(cf, plans, referenced);
                genFiles.put(dottedName(cf.getClassName()), classEmitter.emit(cf, plans));
                if (ReflectionMetadataEmitter.hasMetadata(naming, cf.getClassName(),
                        Modifiers.isInterface(cf.getAccess()))) {
                    reflectClasses.add(cf);
                }
            } catch (RuntimeException e) {
                // One malformed class must never abort the whole run: record it and emit a bare
                // placeholder so the type still resolves, mirroring the per-method stub net.
                report.classFailed(cf.getClassName(),
                        e.getClass().getSimpleName() + ": " + e.getMessage());
                genFiles.put(dottedName(cf.getClassName()),
                        Modifiers.isInterface(cf.getAccess())
                                ? classStub.emitInterface(cf.getClassName())
                                : classStub.emit(cf.getClassName()));
            }
        }
        genFiles.putAll(synthetics.files());
        Map<String, ClassFile> byInternal = new LinkedHashMap<>();
        for (ClassFile cf : allClasses) {
            byInternal.put(cf.getClassName(), cf);
        }
        AnnotationEmitter annoEmitter = new AnnotationEmitter(naming, byInternal);
        genFiles.put("j2cs.reflect.__Bootstrap", reflectionBootstrap(reflectClasses, naming, annoEmitter));
        if (annoEmitter.hasImpls()) {
            CsWriter aw = new CsWriter();
            annoEmitter.emitImpls(aw);
            genFiles.put("j2cs.anno.__Impls", aw.toString());
        }
        Map<String, String> stubFiles = emitStubs(referenced, naming, interfacePositionStubs, report);
        addStandingDivergences(report);
        boolean usesGui = referenced.stream()
                .anyMatch(name -> name.startsWith("javax/swing/") || name.startsWith("java/awt/"));
        String programCs = new EntryPointEmitter().emit(input.entryClassInternalName(), naming, usesGui);

        Set<String> ownSimpleNames = new java.util.HashSet<>();
        Map<String, Set<String>> namespaceTypes = new java.util.HashMap<>();
        for (String internal : ShimRegistry.types()) {
            String simple = CsNamer.classNameOf(internal);
            ownSimpleNames.add(simple);
            namespaceTypes.computeIfAbsent(CsNamer.namespaceOf(internal), k -> new java.util.HashSet<>()).add(simple);
        }
        for (String dotted : genFiles.keySet()) {
            addNamespaceType(ownSimpleNames, namespaceTypes, dotted);
        }
        for (String dotted : stubFiles.keySet()) {
            addNamespaceType(ownSimpleNames, namespaceTypes, dotted);
        }
        // Per-file member names so the using-rewriter never shortens a qualified type reference to a
        // simple name that a member of that file's class shadows (e.g. an enum constant named Float
        // shadowing java.lang.Float, which would break Float.TYPE).
        Map<String, Set<String>> membersByFile = new java.util.HashMap<>();
        for (ClassFile cf : allClasses) {
            MemberNamer namer = naming.namerOf(cf.getClassName());
            membersByFile.put(dottedName(cf.getClassName()), namer.declaredMemberCsNames());
        }
        UsingRewriter usingRewriter = new UsingRewriter(ownSimpleNames, namespaceTypes);
        genFiles = usingRewriter.rewriteAll(genFiles, membersByFile);
        stubFiles = usingRewriter.rewriteAll(stubFiles, membersByFile);

        Path appDir = new SolutionGenerator().generate(options.outDir(),
                new GeneratedSolution(genFiles, stubFiles, programCs, bootstrappedInternal, usesGui),
                options.input());
        new NativeFragmentPackager().copy(appDir, BootstrapPolicy.nativeFragments(bootstrappedInternal));

        Path reportPath = new ReportWriter().write(report, options.outDir());
        return new TranspileResult(input, report, reportPath, appDir);
    }

    /**
     * Populates YABR's enum-switch-map registry from every $SwitchMap$ holder's static
     * initializer. ClassDecompiler does this itself, but J2CS drives MethodRecoverer directly;
     * without it, enum switches never resolve to constant labels and recovery falls back to a
     * miscompiled ordinal switch.
     */
    private static void registerEnumSwitchMaps(List<ClassFile> classes) {
        for (ClassFile cf : classes) {
            boolean hasSwitchMap = cf.getFields().stream()
                    .anyMatch(f -> f.getName() != null && f.getName().startsWith("$SwitchMap$"));
            if (!hasSwitchMap) {
                continue;
            }
            List<com.tonic.analysis.ssa.cfg.IRMethod> methods = new ArrayList<>();
            for (MethodEntry method : cf.getMethods()) {
                if (method.getName().equals("<clinit>") && method.getCodeAttribute() != null) {
                    methods.add(new com.tonic.analysis.ssa.SSA(cf.getConstPool()).lift(method));
                }
            }
            com.tonic.analysis.source.recovery.SwitchMapAnalyzer.analyzeClass(
                    cf.getClassName(), cf.getFields(), methods);
        }
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
        report.divergence("TreeMap iterates in natural key order and LinkedHashMap in insertion order, but a TreeMap constructed with a custom Comparator still uses natural ordering");
        report.divergence("java time/date, UUID, and SecureRandom values are runtime-dependent and do not match a specific JVM run");
        report.divergence("Swing/AWT is rendered via Avalonia; widget structure and behavior are preserved but visual styling and pixel layout differ from the JVM");
        report.divergence("volatile long/double fields are emitted non-volatile (C# forbids the modifier on 64-bit types); their use as a memory barrier diverges");
        report.divergence("ConcurrentHashMap is backed by the plain HashMap shim and is not thread-safe");
        report.divergence("ExecutorService/CompletableFuture run submitted work synchronously on the calling thread; there is no real parallelism");
        report.divergence("StrictMath delegates to .NET System.Math and is not fdlibm bit-exact for transcendental functions");
        report.divergence("strictfp is ignored; floating-point uses the default .NET evaluation");
        report.divergence("sealed-class permits are not enforced");
    }

    /**
     * The reflection registrars live here, not on the app classes: a static method on a class
     * triggers that class's clinit, so calling them from the app class would force every clinit to
     * run eagerly at startup (breaking classes whose clinit uses unshimmed types). All emitted
     * members are internal/public, so cross-class thunks in the same assembly reach them; typeof and
     * the delegate bodies never run the target clinit until reflection actually invokes them.
     */
    private static String reflectionBootstrap(List<ClassFile> reflectClasses, NamingContext naming,
                                              AnnotationEmitter annoEmitter) {
        ReflectionMetadataEmitter emitter = new ReflectionMetadataEmitter(naming, annoEmitter);
        CsWriter w = new CsWriter();
        w.open("namespace j2cs.reflect");
        w.open("internal static class __Bootstrap");
        w.open("internal static void InitAll()");
        for (int i = 0; i < reflectClasses.size(); i++) {
            w.line("Register_" + i + "();");
        }
        w.close();
        for (int i = 0; i < reflectClasses.size(); i++) {
            ClassFile cf = reflectClasses.get(i);
            w.line();
            emitter.emit(w, "Register_" + i, cf, naming.namerOf(cf.getClassName()), naming.typeMapper());
        }
        w.close();
        w.close();
        return w.toString();
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

    private static void addNamespaceType(Set<String> ownSimpleNames,
                                         Map<String, Set<String>> namespaceTypes, String dotted) {
        int dot = dotted.lastIndexOf('.');
        String simple = dotted.substring(dot + 1);
        ownSimpleNames.add(simple);
        if (dot > 0) {
            namespaceTypes.computeIfAbsent(dotted.substring(0, dot), k -> new java.util.HashSet<>()).add(simple);
        }
    }
}
