package com.tonic.j2cs.structured;

import com.tonic.analysis.source.ast.stmt.BlockStmt;
import com.tonic.analysis.source.ast.transform.ArrayInitializerReconstructor;
import com.tonic.analysis.source.ast.transform.ControlFlowSimplifier;
import com.tonic.analysis.source.ast.transform.DeadStoreEliminator;
import com.tonic.analysis.source.ast.transform.DeadVariableEliminator;
import com.tonic.analysis.source.ast.transform.DeclarationHoister;
import com.tonic.analysis.source.ast.transform.ForLoopCounterFolder;
import com.tonic.analysis.source.ast.transform.PatternInstanceOfReconstructor;
import com.tonic.analysis.source.ast.transform.PatternSwitchReconstructor;
import com.tonic.analysis.source.ast.transform.RedundantAssignmentEliminator;
import com.tonic.analysis.source.ast.transform.ScopeEscapeHoister;
import com.tonic.analysis.source.ast.transform.SingleUseInliner;
import com.tonic.analysis.source.ast.transform.SwitchExpressionReconstructor;

/**
 * Applies YABR's AST cleanup transforms in ClassDecompiler's fixed order, per method kind.
 */
final class AstNormalizer {

    private final ControlFlowSimplifier simplifier = new ControlFlowSimplifier();
    private final PatternInstanceOfReconstructor patternInstanceOf = new PatternInstanceOfReconstructor();
    private final SingleUseInliner inliner = new SingleUseInliner();
    private final DeadStoreEliminator deadStores = new DeadStoreEliminator();
    private final DeadVariableEliminator deadVars = new DeadVariableEliminator();
    private final DeclarationHoister hoister = new DeclarationHoister();
    private final ArrayInitializerReconstructor arrayInit = new ArrayInitializerReconstructor();
    private final ForLoopCounterFolder forCounters = new ForLoopCounterFolder();
    private final RedundantAssignmentEliminator redundantAssigns = new RedundantAssignmentEliminator();
    private final PatternSwitchReconstructor patternSwitch = new PatternSwitchReconstructor();
    private final SwitchExpressionReconstructor switchExpr = new SwitchExpressionReconstructor();
    private final ScopeEscapeHoister scopeEscapes = new ScopeEscapeHoister();

    void normalize(BlockStmt body, String methodName) {
        if (methodName.equals("<init>")) {
            normalizeConstructor(body);
        } else if (methodName.equals("<clinit>")) {
            normalizeClinit(body);
        } else {
            normalizeMethod(body);
        }
    }

    private void normalizeMethod(BlockStmt body) {
        simplifier.transform(body);
        patternInstanceOf.transform(body);
        inliner.transform(body);
        deadStores.transform(body);
        deadVars.transform(body);
        simplifier.transform(body);
        hoister.transform(body);
        inliner.transform(body);
        arrayInit.transform(body);
        forCounters.transform(body);
        deadStores.transform(body);
        deadVars.transform(body);
        redundantAssigns.transform(body);
        patternSwitch.transform(body);
        switchExpr.transform(body);
        scopeEscapes.transform(body);
    }

    private void normalizeConstructor(BlockStmt body) {
        simplifier.transform(body);
        patternInstanceOf.transform(body);
        inliner.transform(body);
        deadStores.transform(body);
        deadVars.transform(body);
        hoister.transform(body);
        inliner.transform(body);
        patternSwitch.transform(body);
        switchExpr.transform(body);
        scopeEscapes.transform(body);
    }

    private void normalizeClinit(BlockStmt body) {
        simplifier.transform(body);
        arrayInit.transform(body);
        patternInstanceOf.transform(body);
        inliner.transform(body);
        deadStores.transform(body);
        deadVars.transform(body);
        hoister.transform(body);
        inliner.transform(body);
        patternSwitch.transform(body);
        switchExpr.transform(body);
        scopeEscapes.transform(body);
    }
}
