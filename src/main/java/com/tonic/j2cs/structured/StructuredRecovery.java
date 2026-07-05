package com.tonic.j2cs.structured;

import com.tonic.analysis.source.ast.stmt.BlockStmt;
import com.tonic.analysis.source.ast.stmt.IRRegionStmt;
import com.tonic.analysis.source.ast.stmt.LabeledStmt;
import com.tonic.analysis.source.recovery.MethodRecoverer;
import com.tonic.analysis.ssa.SSA;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.transform.ControlFlowReducibility;
import com.tonic.analysis.ssa.transform.DuplicateBlockMerging;
import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;
import java.util.Optional;

/**
 * Recovers a structured AST body for one method via YABR's decompiler recovery: a dedicated
 * plain lift (exception-local resolution breaks try/catch recovery), the recoverer, then the
 * normalization chain. Empty when recovery fails or degrades (irreducible region, dispatch
 * loop) — the caller falls back to the goto emitter.
 */
final class StructuredRecovery {

    record Recovered(BlockStmt body, IRMethod ir) {
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
            BlockStmt body = MethodRecoverer.recoverMethod(ir, method);
            new AstNormalizer(classFile).normalize(body, method.getName());
            if (!body.findAll(IRRegionStmt.class).isEmpty() || hasDispatchLoop(body)) {
                return Optional.empty();
            }
            return Optional.of(new Recovered(body, ir));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    private static boolean hasDispatchLoop(BlockStmt body) {
        return body.findAll(LabeledStmt.class).stream()
                .anyMatch(l -> "$dispatch$".equals(l.getLabel()));
    }
}
