package com.tonic.j2cs.report;

import com.tonic.j2cs.bootstrap.BootstrapPolicy;
import com.tonic.j2cs.emit.MethodBodyEmitter;
import com.tonic.j2cs.emit.SyntheticClasses;
import com.tonic.j2cs.emit.UnsupportedBodyException;
import com.tonic.j2cs.frontend.BootstrapLoader;
import com.tonic.j2cs.frontend.IrLifter;
import com.tonic.j2cs.model.MethodPlan;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.pipeline.ClassHierarchy;
import com.tonic.j2cs.pipeline.MethodPlanner;
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.ClassPool;
import com.tonic.parser.MethodEntry;
import com.tonic.parser.constpool.ClassRefItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Analyzes JDK classes for bootstrap feasibility: how many methods emit a full C# body versus
 * degrade to a stub (and why), and how each referenced type would resolve (bootstrapped,
 * shim-covered, or stubbed). Runs lift and emission so unresolved shim calls and native seams
 * surface exactly as they would during a real transpile. No dotnet invocation.
 */
public final class BootstrapCoverageReport {

    public String coverage() {
        Set<String> generated = new TreeSet<>(BootstrapPolicy.bootstrappable());
        Set<String> shim = new TreeSet<>();
        for (String type : ShimRegistry.types()) {
            if (!generated.contains(type)) {
                shim.add(type);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("j2cs bootstrap coverage\n");
        sb.append("generated from JDK bytecode (").append(generated.size()).append("):\n");
        for (String g : generated) {
            sb.append("  ").append(g).append('\n');
        }
        sb.append("hand-shim remaining (").append(shim.size()).append("):\n");
        for (String s : shim) {
            sb.append("  ").append(s).append('\n');
        }
        return sb.toString();
    }

    public String analyze(List<String> fqcns) {
        ClassPool pool = new ClassPool(true);
        List<ClassFile> classes = new BootstrapLoader().load(pool, fqcns);
        TypeMapper types = new TypeMapper();
        ClassHierarchy hierarchy = new ClassHierarchy(classes);
        NamingContext naming = new NamingContext(types, classes, hierarchy);
        MethodPlanner planner = new MethodPlanner(new IrLifter(types, false), naming);
        Set<String> requested = new TreeSet<>();
        for (ClassFile cf : classes) {
            requested.add(cf.getClassName());
        }
        naming.setBootstrapped(requested);

        StringBuilder sb = new StringBuilder();
        sb.append("j2cs bootstrap coverage report\n");
        sb.append("jdk: ").append(System.getProperty("java.version")).append('\n');
        sb.append("requested: ").append(String.join(", ", fqcns)).append('\n');
        sb.append("closure: ").append(requested).append('\n');
        for (ClassFile cf : classes) {
            reportClass(cf, planner, naming, requested, sb);
        }
        return sb.toString();
    }

    private void reportClass(ClassFile cf, MethodPlanner planner, NamingContext naming,
                             Set<String> requested, StringBuilder sb) {
        sb.append('\n').append("class ").append(cf.getClassName())
                .append("  (super ").append(cf.getSuperClassName());
        if (!cf.getInterfaceNames().isEmpty()) {
            sb.append("; implements ").append(String.join(", ", cf.getInterfaceNames()));
        }
        sb.append(")\n");

        int total = 0;
        int emitted = 0;
        List<String> stubbed = new ArrayList<>();
        for (MethodEntry method : cf.getMethods()) {
            total++;
            String reason = wallReason(cf, method, planner, naming);
            if (reason == null) {
                emitted++;
            } else {
                stubbed.add("      " + method.getName() + method.getDesc() + "   " + reason);
            }
        }
        sb.append("  methods ").append(total).append("  emitted ").append(emitted)
                .append("  stubbed ").append(stubbed.size()).append('\n');
        if (!stubbed.isEmpty()) {
            sb.append("    stubbed:\n");
            for (String line : stubbed) {
                sb.append(line).append('\n');
            }
        }

        Set<String> bootstrapped = new TreeSet<>();
        Set<String> shimCovered = new TreeSet<>();
        Set<String> wouldStub = new TreeSet<>();
        for (String ref : referencedTypes(cf)) {
            if (requested.contains(ref)) {
                bootstrapped.add(ref);
            } else if (ShimRegistry.isShimType(ref)) {
                shimCovered.add(ref);
            } else {
                wouldStub.add(ref);
            }
        }
        sb.append("  closure types:\n");
        sb.append("    bootstrapped: ").append(bootstrapped).append('\n');
        sb.append("    shim-covered: ").append(shimCovered).append('\n');
        sb.append("    would-stub:   ").append(wouldStub).append('\n');
    }

    private String wallReason(ClassFile cf, MethodEntry method, MethodPlanner planner, NamingContext naming) {
        MethodPlan plan = planner.plan(cf, method);
        if (plan instanceof MethodPlan.Unsupported unsupported) {
            return unsupported.reason();
        }
        MethodPlan.Supported supported = (MethodPlan.Supported) plan;
        try {
            new MethodBodyEmitter(naming, new SyntheticClasses())
                    .emit(cf.getClassName(), method, supported.method(), 0);
            return null;
        } catch (UnsupportedBodyException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return "emit failed (" + e.getClass().getSimpleName() + "): " + e.getMessage();
        }
    }

    private Set<String> referencedTypes(ClassFile cf) {
        Set<String> refs = new TreeSet<>();
        refs.add(cf.getSuperClassName());
        refs.addAll(cf.getInterfaceNames());
        cf.getConstPool().getItems().forEach(item -> {
            if (item instanceof ClassRefItem classRef) {
                String name = classRef.getClassName();
                if (name != null && !name.startsWith("[")) {
                    refs.add(name);
                }
            }
        });
        refs.remove(cf.getClassName());
        return refs;
    }
}
