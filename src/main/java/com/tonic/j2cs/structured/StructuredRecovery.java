package com.tonic.j2cs.structured;

import com.tonic.analysis.source.ast.stmt.BlockStmt;
import com.tonic.analysis.source.ast.stmt.IRRegionStmt;
import com.tonic.analysis.source.ast.stmt.LabeledStmt;
import com.tonic.analysis.source.recovery.MethodRecoverer;
import com.tonic.analysis.ssa.SSA;
import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.ir.IRInstruction;
import com.tonic.analysis.ssa.ir.InvokeInstruction;
import com.tonic.analysis.ssa.ir.InvokeType;
import com.tonic.analysis.ssa.transform.ControlFlowReducibility;
import com.tonic.analysis.ssa.transform.DuplicateBlockMerging;
import com.tonic.analysis.ssa.value.Constant;
import com.tonic.analysis.ssa.value.MethodHandleConstant;
import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Recovers a structured AST body for one method via YABR's decompiler recovery: a dedicated
 * plain lift (exception-local resolution breaks try/catch recovery), the recoverer, then the
 * normalization chain. Empty when recovery fails or degrades (irreducible region, dispatch
 * loop) — the caller falls back to the goto emitter.
 */
final class StructuredRecovery {

    /**
     * A recovered body plus what lambda emission needs: the recoverer (its naming maps SSA
     * values to recovered variable names) and the method's lambda indy sites keyed by impl
     * method name+descriptor plus the functional interface — the same pair a recovered
     * LambdaExpr/MethodRefExpr carries (the interface disambiguates e.g. a bound and an
     * unbound reference to one method). A key shared by two sites maps to null (ambiguous;
     * the method degrades if it needs it).
     */
    record Recovered(BlockStmt body, IRMethod ir, MethodRecoverer recoverer,
                     Map<String, InvokeInstruction> indySites) {
    }

    static String siteKey(String implMethodKey, String ifaceInternal) {
        return implMethodKey + "|" + ifaceInternal;
    }

    Optional<Recovered> recover(ClassFile classFile, MethodEntry method) {
        if (method.getCodeAttribute() == null) {
            return Optional.empty();
        }
        try {
            IRMethod ir = new SSA(classFile.getConstPool()).lift(method);
            if (method.getName().equals("<init>") || method.getName().equals("<clinit>")) {
                new ControlFlowReducibility().run(ir);
                new DuplicateBlockMerging().run(ir);
            }
            Map<String, InvokeInstruction> indySites = collectLambdaSites(ir);
            MethodRecoverer recoverer = new MethodRecoverer(ir, method);
            BlockStmt body = recoverer.recover();
            new AstNormalizer().normalize(body, method.getName());
            if (!body.findAll(IRRegionStmt.class).isEmpty() || hasDispatchLoop(body)) {
                return Optional.empty();
            }
            return Optional.of(new Recovered(body, ir, recoverer, indySites));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    private static Map<String, InvokeInstruction> collectLambdaSites(IRMethod ir) {
        Map<String, InvokeInstruction> sites = new HashMap<>();
        for (IRBlock block : ir.getBlocks()) {
            for (IRInstruction instr : block.getInstructions()) {
                if (!(instr instanceof InvokeInstruction invoke)
                        || invoke.getInvokeType() != InvokeType.DYNAMIC
                        || invoke.getBootstrapInfo() == null
                        || !invoke.getBootstrapInfo().isLambdaMetafactory()) {
                    continue;
                }
                List<Constant> args = invoke.getBootstrapInfo().getBootstrapArguments();
                if (args.size() < 2 || !(args.get(1) instanceof MethodHandleConstant impl)) {
                    continue;
                }
                String iface = com.tonic.j2cs.types.TypeMapper.unwrapReference(
                        com.tonic.j2cs.types.TypeMapper.returnDescriptor(invoke.getDescriptor()));
                String key = siteKey(impl.getName() + impl.getDescriptor(), iface);
                sites.put(key, sites.containsKey(key) ? null : invoke);
            }
        }
        return sites;
    }

    private static boolean hasDispatchLoop(BlockStmt body) {
        return body.findAll(LabeledStmt.class).stream()
                .anyMatch(l -> "$dispatch$".equals(l.getLabel()));
    }
}
