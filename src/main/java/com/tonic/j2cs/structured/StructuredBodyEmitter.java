package com.tonic.j2cs.structured;

import com.tonic.analysis.source.ast.expr.ArrayAccessExpr;
import com.tonic.analysis.source.ast.expr.BinaryExpr;
import com.tonic.analysis.source.ast.expr.BinaryOperator;
import com.tonic.analysis.source.ast.expr.CastExpr;
import com.tonic.analysis.source.ast.expr.ClassExpr;
import com.tonic.analysis.source.ast.expr.Expression;
import com.tonic.analysis.source.ast.expr.FieldAccessExpr;
import com.tonic.analysis.source.ast.expr.InstanceOfExpr;
import com.tonic.analysis.source.ast.expr.LambdaExpr;
import com.tonic.analysis.source.ast.expr.LiteralExpr;
import com.tonic.analysis.source.ast.expr.ArrayInitExpr;
import com.tonic.analysis.source.ast.expr.MethodCallExpr;
import com.tonic.analysis.source.ast.expr.MethodRefExpr;
import com.tonic.analysis.source.ast.expr.NewArrayExpr;
import com.tonic.analysis.source.ast.expr.NewExpr;
import com.tonic.analysis.source.ast.expr.SuperExpr;
import com.tonic.analysis.source.ast.expr.TernaryExpr;
import com.tonic.analysis.source.ast.expr.ThisExpr;
import com.tonic.analysis.source.ast.expr.UnaryExpr;
import com.tonic.analysis.source.ast.expr.VarRefExpr;
import com.tonic.analysis.source.ast.stmt.BlockStmt;
import com.tonic.analysis.source.ast.stmt.BreakStmt;
import com.tonic.analysis.source.ast.stmt.CatchClause;
import com.tonic.analysis.source.ast.stmt.ContinueStmt;
import com.tonic.analysis.source.ast.stmt.DoWhileStmt;
import com.tonic.analysis.source.ast.stmt.ExprStmt;
import com.tonic.analysis.source.ast.stmt.ForEachStmt;
import com.tonic.analysis.source.ast.stmt.ForStmt;
import com.tonic.analysis.source.ast.stmt.IfStmt;
import com.tonic.analysis.source.ast.stmt.LabeledStmt;
import com.tonic.analysis.source.ast.stmt.ReturnStmt;
import com.tonic.analysis.source.ast.stmt.Statement;
import com.tonic.analysis.source.ast.stmt.SwitchCase;
import com.tonic.analysis.source.ast.stmt.SwitchStmt;
import com.tonic.analysis.source.ast.stmt.SynchronizedStmt;
import com.tonic.analysis.source.ast.stmt.ThrowStmt;
import com.tonic.analysis.source.ast.stmt.TryCatchStmt;
import com.tonic.analysis.source.ast.stmt.VarDeclStmt;
import com.tonic.analysis.source.ast.stmt.WhileStmt;
import com.tonic.analysis.source.ast.type.ReferenceSourceType;
import com.tonic.analysis.source.ast.type.SourceType;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.j2cs.emit.CallRenderer;
import com.tonic.j2cs.emit.ConcatEmitter;
import com.tonic.j2cs.emit.ConstRenderer;
import com.tonic.j2cs.emit.CsWriter;
import com.tonic.j2cs.emit.LambdaExpander;
import com.tonic.j2cs.emit.SyntheticClasses;
import com.tonic.j2cs.emit.TypeReconciler;
import com.tonic.j2cs.emit.UnsupportedBodyException;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.types.CsType;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.ClassFile;
import com.tonic.parser.MethodEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Prints a recovered AST body as structured C#, reusing the shared call/field/cast rendering.
 * Java booleans stay int in the C# ABI: comparisons/instanceof/logical nodes are C#-bool and
 * bridge with `? 1 : 0` in value positions; int values bridge with `!= 0` in conditions.
 * Unsupported nodes throw UnsupportedBodyException; the caller falls back to the goto emitter.
 */
final class StructuredBodyEmitter {

    private final NamingContext naming;
    private final TypeReconciler reconciler;
    private final CallRenderer calls;
    private final LambdaExpander lambdaExpander;

    private CsWriter w;
    private String currentClass;
    private String returnDesc;
    private StructuredRecovery.Recovered recovered;
    private Map<String, String> names;
    private Map<String, String> declaredDesc;
    private Set<String> declaredEver;
    private Set<String> usedNames;
    private Map<String, String> breakLabels;
    private Map<SSAValue, String> paramNames;
    private java.util.Deque<Set<String>> scopes;
    private int hoistCounter;
    private boolean allowHoist;
    private boolean foldForInit;

    StructuredBodyEmitter(NamingContext naming, SyntheticClasses synthetics) {
        this.naming = naming;
        this.reconciler = new TypeReconciler(naming);
        this.calls = new CallRenderer(naming, reconciler);
        this.lambdaExpander = new LambdaExpander(naming, synthetics);
    }

    String emit(ClassFile classFile, MethodEntry method, StructuredRecovery.Recovered recovered,
                int indentDepth, boolean foldForInit) {
        this.w = new CsWriter(indentDepth);
        this.recovered = recovered;
        this.foldForInit = foldForInit;
        this.currentClass = classFile.getClassName();
        this.returnDesc = TypeMapper.returnDescriptor(method.getDesc());
        this.names = new HashMap<>();
        this.declaredDesc = new HashMap<>();
        this.declaredEver = new HashSet<>();
        this.usedNames = new HashSet<>();
        // A local must not shadow the enclosing class's own C# name: a static self-reference is
        // emitted as `ClassName.member`, and in C# a same-named local shadows the type in that
        // expression, so the reference would bind to the local instead of the class.
        usedNames.add(CsNamer.classNameOf(currentClass));
        this.breakLabels = new HashMap<>();
        this.scopes = new java.util.ArrayDeque<>();
        this.hoistCounter = 0;
        this.allowHoist = true;
        pushScope();
        mapParameters(recovered);
        List<Statement> body = recovered.body().getStatements();
        int count = body.size();
        // A void method's trailing bare `return;` is redundant — control falls off the end anyway.
        if ("V".equals(returnDesc) && count > 0
                && body.get(count - 1) instanceof ReturnStmt r && r.getValue() == null) {
            count--;
        }
        // Reject a degraded recovery so the caller falls back to the goto emitter (which yields a
        // real body or a clear stub) instead of a body that does nothing or only throws "fell off
        // method end": a non-void method with no return/throw anywhere (e.g. only hoisted
        // declarations), or any method that recovered to an empty body while its bytecode plainly
        // does real work (a genuinely empty method compiles to a handful of bytes).
        if (!"V".equals(returnDesc) && !containsTerminator(body)) {
            throw new UnsupportedBodyException("structured: non-void method has no terminating statement");
        }
        if (body.isEmpty() && hasNonTrivialBytecode(method)) {
            throw new UnsupportedBodyException("structured: empty recovered body over non-trivial bytecode");
        }
        for (int i = 0; i < count; i++) {
            stmt(body.get(i));
        }
        // Non-void methods need a guaranteed terminator: recovery may leave a path without a return
        // (or one C# cannot prove returns), so mirror the goto emitter's fall-off guard — but only
        // when the body doesn't already return on every path, else the guard is dead (CS0162) code.
        if (!"V".equals(returnDesc) && !alwaysReturns(body)) {
            w.line("throw new global::System.InvalidOperationException(\"j2cs: fell off method end\");");
        }
        return w.toString();
    }

    private void mapParameters(StructuredRecovery.Recovered recovered) {
        paramNames = new IdentityHashMap<>();
        List<SSAValue> params = recovered.ir().getParameters();
        boolean isStatic = recovered.ir().isStatic();
        for (int i = 0; i < params.size(); i++) {
            SSAValue param = params.get(i);
            String pname = isStatic ? "p" + i : (i == 0 ? "this" : "p" + (i - 1));
            paramNames.put(param, pname);
            usedNames.add(pname);
            declareInScope(pname);
            // Map the parameter's recovered name straight to its slot. A body reference to a captured
            // parameter (common in lambda synthetic methods) can carry a mismatched or null SSA value,
            // so the SSA-identity walk below would miss it and the name would resolve out of scope.
            String rec = recovered.recoverer().getRecoveryContext().getVariableName(param);
            if (rec != null && !"this".equals(rec)) {
                names.putIfAbsent(rec, pname);
            }
        }
        recovered.body().walk(node -> {
            if (node instanceof VarRefExpr ref && ref.getSsaValue() != null) {
                String target = paramNames.get(ref.getSsaValue());
                if (target != null) {
                    names.put(ref.getName(), target);
                }
            }
        });
        usedNames.add("this");
        declareInScope("this");
    }

    private void pushScope() {
        scopes.push(new HashSet<>());
    }

    private void popScope() {
        scopes.pop();
    }

    private void declareInScope(String csName) {
        scopes.getFirst().add(csName);
    }

    private boolean inScope(String csName) {
        for (Set<String> scope : scopes) {
            if (scope.contains(csName)) {
                return true;
            }
        }
        return false;
    }

    /** Emits a braced body as its own C# scope. */
    private void scoped(Statement body) {
        pushScope();
        try {
            stmt(body);
        } finally {
            popScope();
        }
    }

    private void emitVarDecl(VarDeclStmt v) {
        String targetDesc = descOf(v.getType());
        CsType type = naming.typeMapper().storageType(targetDesc);
        String name = localName(v.getName());
        boolean declare = !inScope(name);
        if (declare && declaredEver.contains(name)) {
            // A distinct same-named local was already declared in another (now-closed) scope. C#
            // forbids a nested scope reusing an enclosing scope's local name, so give this one a
            // fresh unique name and remap; its in-scope references follow the remap.
            String unique = CsNamer.unique(name, usedNames);
            usedNames.add(unique);
            names.put(v.getName(), unique);
            name = unique;
        }
        declaredDesc.put(name, targetDesc);
        if (declare) {
            declaredEver.add(name);
            declareInScope(name);
            if (v.getInitializer() != null
                    && emitTwoPhaseNewInto(name, type.csText() + " ", type, v.getInitializer())) {
                return;
            }
        }
        String init = v.getInitializer() != null
                ? coerced(targetDesc, v.getInitializer())
                : type.defaultLiteral();
        w.line((declare ? type.csText() + " " : "") + name + " = " + init + ";");
    }

    private void stmt(Statement s) {
        if (s instanceof BlockStmt block) {
            for (Statement child : block.getStatements()) {
                stmt(child);
            }
        } else if (s instanceof VarDeclStmt v) {
            emitVarDecl(v);
        } else if (s instanceof ExprStmt e) {
            statementExpr(e.getExpression());
        } else if (s instanceof IfStmt i) {
            w.open("if (" + condition(i.getCondition()) + ")");
            scoped(i.getThenBranch());
            w.close();
            if (i.getElseBranch() != null) {
                w.open("else");
                scoped(i.getElseBranch());
                w.close();
            }
        } else if (s instanceof WhileStmt l) {
            String cond = noHoist(() -> condition(l.getCondition()));
            emitLoop(l.getLabel(), () -> {
                w.open("while (" + cond + ")");
                scoped(l.getBody());
                w.close();
            });
        } else if (s instanceof DoWhileStmt l) {
            emitLoop(l.getLabel(), () -> {
                w.open("do");
                scoped(l.getBody());
                w.closeWith("while (" + noHoist(() -> condition(l.getCondition())) + ");");
            });
        } else if (s instanceof ForStmt f) {
            emitFor(f);
        } else if (s instanceof ForEachStmt f) {
            emitForEach(f);
        } else if (s instanceof SwitchStmt sw) {
            emitSwitch(sw);
        } else if (s instanceof LabeledStmt labeled) {
            emitLabeled(labeled);
        } else if (s instanceof BreakStmt b) {
            w.line(b.getTargetLabel() == null ? "break;" : "goto " + breakLabel(b.getTargetLabel()) + ";");
        } else if (s instanceof ContinueStmt c) {
            if (c.getTargetLabel() != null) {
                throw new UnsupportedBodyException("structured: labeled continue");
            }
            w.line("continue;");
        } else if (s instanceof TryCatchStmt t) {
            emitTryCatch(t);
        } else if (s instanceof SynchronizedStmt sync) {
            w.open("lock (" + expr(sync.getLock()) + ")");
            scoped(sync.getBody());
            w.close();
        } else if (s instanceof ReturnStmt r) {
            if (r.getValue() == null) {
                w.line("return;");
            } else {
                w.line("return " + coerced(returnDesc, r.getValue()) + ";");
            }
        } else if (s instanceof ThrowStmt t) {
            w.line("throw global::java.lang.JThrow.of(" + expr(t.getException()) + ");");
        } else {
            throw new UnsupportedBodyException("structured: " + s.getClass().getSimpleName());
        }
    }

    /**
     * Java catch clauses become one CLR catch that normalizes the exception and dispatches an
     * is-chain, mirroring the goto path's machine: CLR exceptions map to their java.lang
     * equivalents first, and stub markers always propagate.
     */
    private void emitTryCatch(TryCatchStmt t) {
        if (!t.getResources().isEmpty()) {
            throw new UnsupportedBodyException("structured: try-with-resources");
        }
        w.open("try");
        scoped(t.getTryBlock());
        w.close();
        if (!t.getCatches().isEmpty()) {
            String raw = freshName("__e");
            String normalized = freshName("__ex");
            pushScope();
            declareInScope(raw);
            declareInScope(normalized);
            w.open("catch (global::System.Exception " + raw + ")");
            w.line("if (" + raw + " is global::System.NotSupportedException) throw;");
            w.line("global::java.lang.Throwable " + normalized
                    + " = global::java.lang.JRuntime.Normalize(" + raw + ");");
            boolean first = true;
            for (CatchClause clause : t.getCatches()) {
                StringBuilder test = new StringBuilder();
                for (SourceType type : clause.exceptionTypes()) {
                    if (!test.isEmpty()) {
                        test.append(" || ");
                    }
                    test.append(normalized).append(" is ").append(catchTypeText(type));
                }
                w.open((first ? "if (" : "else if (") + test + ")");
                emitCatchBody(clause, normalized);
                w.close();
                first = false;
            }
            w.open("else");
            w.line("throw;");
            w.close();
            w.close();
            popScope();
        }
        if (t.getFinallyBlock() != null) {
            w.open("finally");
            scoped(t.getFinallyBlock());
            w.close();
        }
    }

    private void emitCatchBody(CatchClause clause, String normalized) {
        pushScope();
        String name = localName(clause.variableName());
        declareInScope(name);
        if (clause.isMultiCatch()) {
            w.line("global::java.lang.Throwable " + name + " = " + normalized + ";");
        } else {
            String text = catchTypeText(clause.getPrimaryType());
            w.line(text + " " + name + " = (" + text + ")" + normalized + ";");
        }
        stmt(clause.body());
        popScope();
    }

    private String catchTypeText(SourceType type) {
        String internal = internalOf(type);
        if (internal == null || (!naming.isAppClass(internal) && !naming.isShimType(internal)
                && !naming.isBootstrapped(internal))) {
            throw new UnsupportedBodyException("structured: catch type " + type);
        }
        return naming.typeMapper().computeType(descOf(type)).csText();
    }

    /** A loop that may carry a Java label; break-to-label lowers to a goto past a trailing label. */
    private void emitLoop(String label, Runnable body) {
        if (label == null) {
            body.run();
            return;
        }
        String endLabel = freshName(CsNamer.identifier(label) + "_end");
        breakLabels.put(label, endLabel);
        body.run();
        breakLabels.remove(label);
        w.line(endLabel + ": ;");
    }

    private void emitLabeled(LabeledStmt labeled) {
        Statement inner = labeled.getStatement();
        if (inner instanceof WhileStmt || inner instanceof DoWhileStmt
                || inner instanceof ForStmt || inner instanceof ForEachStmt) {
            String label = labeled.getLabel();
            String endLabel = freshName(CsNamer.identifier(label) + "_end");
            breakLabels.put(label, endLabel);
            stmt(inner);
            breakLabels.remove(label);
            w.line(endLabel + ": ;");
            return;
        }
        throw new UnsupportedBodyException("structured: label on non-loop statement");
    }

    private String breakLabel(String javaLabel) {
        String csLabel = breakLabels.get(javaLabel);
        if (csLabel == null) {
            throw new UnsupportedBodyException("structured: break to unknown label " + javaLabel);
        }
        return csLabel;
    }

    private void emitFor(ForStmt f) {
        // Scope the for-init to the whole for statement (as C# does), so a loop-local counter's
        // declaration lands in the loop rather than leaking into the enclosing block. This keeps
        // disjoint same-slot counters (which the recovery already split) independent per loop.
        // Skipped when folding is off (the retry path after a fold made a counter that is actually
        // live past its loop go out of scope), preserving the original before-loop emission.
        if (foldForInit) {
            pushScope();
            List<String> initClauses = new ArrayList<>();
            if (renderForInits(f.getInit(), initClauses)) {
                emitForLoop(f, String.join(", ", initClauses));
                popScope();
                return;
            }
            popScope();
        }
        for (Statement init : f.getInit()) {
            stmt(init);
        }
        emitForLoop(f, "");
    }

    private void emitForLoop(ForStmt f, String initClause) {
        String cond = f.getCondition() == null ? "" : noHoist(() -> condition(f.getCondition()));
        List<String> updates = new ArrayList<>();
        for (Expression u : f.getUpdate()) {
            updates.add(noHoist(() -> updateExpr(u)));
        }
        emitLoop(f.getLabel(), () -> {
            w.open("for (" + initClause + "; " + cond + "; " + String.join(", ", updates) + ")");
            scoped(f.getBody());
            w.close();
        });
    }

    /**
     * Renders the for-loop init statements as C# for-init clauses (populating {@code out}),
     * declaring any variable into the current for-scope. Returns false — emitting nothing — when the
     * inits are not safely inlinable (a non-declaration/expression statement, an init whose value
     * would need hoisting, or a multi-init shape C# forbids), so the caller falls back to emitting
     * them before the loop.
     */
    private boolean renderForInits(List<Statement> inits, List<String> out) {
        boolean multi = inits.size() > 1;
        for (Statement init : inits) {
            // C# allows a single declaration or a list of statement-expressions in a for-init, but
            // not a mix; keep multi-init inlining to expression statements only.
            if (multi && !(init instanceof ExprStmt)) {
                return false;
            }
            String clause;
            try {
                clause = renderForInit(init);
            } catch (UnsupportedBodyException e) {
                return false;
            }
            if (clause == null) {
                return false;
            }
            out.add(clause);
        }
        return true;
    }

    private String renderForInit(Statement init) {
        if (init instanceof VarDeclStmt v) {
            String targetDesc = descOf(v.getType());
            CsType type = naming.typeMapper().storageType(targetDesc);
            String value = noHoist(() -> v.getInitializer() != null
                    ? coerced(targetDesc, v.getInitializer())
                    : type.defaultLiteral());
            String name = localName(v.getName());
            if (inScope(name)) {
                return name + " = " + value;
            }
            declareInScope(name);
            return type.csText() + " " + name + " = " + value;
        }
        if (init instanceof ExprStmt e) {
            return noHoist(() -> updateExpr(e.getExpression()));
        }
        return null;
    }

    private String updateExpr(Expression e) {
        if (e instanceof UnaryExpr u && isIncDec(u.getOperator())) {
            return expr(u.getOperand()) + u.getOperator().getSymbol();
        }
        if (e instanceof BinaryExpr b && isAssignOp(b.getOperator())) {
            return assignmentText(b);
        }
        return expr(e);
    }

    private void emitForEach(ForEachStmt f) {
        String iterableDesc = descOf(f.getIterable().getType());
        if (iterableDesc == null || !iterableDesc.startsWith("[")) {
            throw new UnsupportedBodyException("structured: for-each over non-array");
        }
        VarDeclStmt var = f.getVariable();
        String elemDesc = descOf(var.getType());
        CsType elemType = naming.typeMapper().storageType(elemDesc);
        String arr = freshName("__a");
        String idx = freshName("__i");
        CsType arrType = naming.typeMapper().computeType(iterableDesc);
        declareInScope(arr);
        w.line(arrType.csText() + " " + arr + " = " + expr(f.getIterable()) + ";");
        String elemName = localName(var.getName());
        emitLoop(f.getLabel(), () -> {
            w.open("for (int " + idx + " = 0; " + idx + " < " + arr + ".Length; " + idx + "++)");
            pushScope();
            declareInScope(elemName);
            w.line(elemType.csText() + " " + elemName + " = " + arr + "[" + idx + "];");
            stmt(f.getBody());
            popScope();
            w.close();
        });
    }

    private void emitSwitch(SwitchStmt sw) {
        boolean enumLabels = sw.getCases().stream().anyMatch(SwitchCase::hasExpressionLabels);
        String selector;
        List<SwitchCase> cases;
        Map<Integer, String> labelNames;
        if (enumLabels) {
            LoweredEnumSwitch lowered = lowerEnumSwitch(sw);
            selector = lowered.selector();
            cases = lowered.cases();
            labelNames = lowered.labelNames();
        } else {
            selector = expr(sw.getSelector());
            cases = sw.getCases();
            labelNames = Map.of();
            // JVM switch keys are ints; a char/byte/short selector keeps its narrower C# type, and C#
            // rejects int case labels against it. Widen the selector to int so the labels match.
            String selDesc = sw.getSelector().getType() == null ? null : descOf(sw.getSelector().getType());
            if ("C".equals(selDesc) || "B".equals(selDesc) || "S".equals(selDesc)) {
                selector = "(int)(" + selector + ")";
            }
        }
        pushScope();
        w.open("switch (" + selector + ")");
        for (int i = 0; i < cases.size(); i++) {
            SwitchCase c = cases.get(i);
            if (c.hasExpressionLabels()) {
                throw new UnsupportedBodyException("structured: expression-label switch case");
            }
            if (c.isDefault()) {
                w.line("default:");
            } else {
                for (int label : c.labels()) {
                    String name = labelNames.get(label);
                    w.line("case " + label + ":" + (name == null ? "" : " // " + name));
                }
            }
            w.indent();
            for (Statement st : c.statements()) {
                stmt(st);
            }
            emitCaseTerminator(cases, i, c);
            w.dedent();
        }
        w.close();
        popScope();
    }

    private record LoweredEnumSwitch(String selector, List<SwitchCase> cases,
                                     Map<Integer, String> labelNames) {
    }

    /**
     * An enum switch (expression labels naming the constants) lowers to an int switch on
     * ordinal(): C# case labels must be compile-time constants, and the ordinal call also keeps
     * Java's NullPointerException on a null selector. Ordinals come from the enum's constant
     * field order — the same source values() is synthesized from.
     */
    private LoweredEnumSwitch lowerEnumSwitch(SwitchStmt sw) {
        List<SwitchCase> lowered = new ArrayList<>();
        Map<Integer, String> labelNames = new HashMap<>();
        String enumInternal = null;
        for (SwitchCase c : sw.getCases()) {
            if (c.isDefault()) {
                lowered.add(c);
                continue;
            }
            List<Integer> ordinals = new ArrayList<>();
            for (Expression label : c.expressionLabels()) {
                if (!(label instanceof FieldAccessExpr field) || !field.isStatic()) {
                    throw new UnsupportedBodyException("structured: non-enum switch label");
                }
                if (enumInternal == null) {
                    enumInternal = field.getOwnerClass();
                } else if (!enumInternal.equals(field.getOwnerClass())) {
                    throw new UnsupportedBodyException("structured: mixed switch label owners");
                }
                int ordinal = naming.enumConstantOrdinal(field.getOwnerClass(), field.getFieldName());
                if (ordinal < 0) {
                    throw new UnsupportedBodyException("structured: switch label is not an app enum constant: "
                            + field.getOwnerClass() + "." + field.getFieldName());
                }
                ordinals.add(ordinal);
                labelNames.put(ordinal, field.getFieldName());
            }
            if (ordinals.isEmpty()) {
                throw new UnsupportedBodyException("structured: switch case without labels");
            }
            lowered.add(SwitchCase.of(ordinals, c.statements()).withFallsThrough(c.fallsThrough()));
        }
        String selector = calls.shimCall("java/lang/Enum", "ordinal", "()I",
                receiverOf(sw.getSelector()), "");
        return new LoweredEnumSwitch(selector, lowered, labelNames);
    }

    private void emitCaseTerminator(List<SwitchCase> cases, int i, SwitchCase c) {
        if (endsInTerminator(c.statements())) {
            return;
        }
        if (!c.fallsThrough()) {
            w.line("break;");
            return;
        }
        if (i + 1 >= cases.size()) {
            throw new UnsupportedBodyException("structured: fall-through on final switch case");
        }
        SwitchCase next = cases.get(i + 1);
        w.line(next.isDefault() ? "goto default;" : "goto case " + next.labels().get(0) + ";");
    }

    private static boolean endsInTerminator(List<Statement> statements) {
        if (statements.isEmpty()) {
            return false;
        }
        Statement last = statements.get(statements.size() - 1);
        return last instanceof ReturnStmt || last instanceof ThrowStmt
                || last instanceof BreakStmt || last instanceof ContinueStmt;
    }

    /**
     * Whether a statement (list) leaves the method via return/throw on every path — C#'s definite-
     * return rule (CS0161). Deliberately conservative: unknown shapes answer false, so the fall-off
     * guard is only ever kept, never wrongly dropped. Break/continue don't count here (unlike
     * {@link #endsInTerminator}); they exit a loop/switch, not the method.
     */
    private static boolean alwaysReturns(List<Statement> statements) {
        return !statements.isEmpty() && alwaysReturns(statements.get(statements.size() - 1));
    }

    /**
     * Whether the method's bytecode does enough that an empty recovered body must be a recovery
     * failure rather than a genuinely empty method. A real no-op compiles to a lone {@code return}
     * (one byte); anything meaningfully longer that recovered to nothing dropped its body.
     */
    private static boolean hasNonTrivialBytecode(MethodEntry method) {
        com.tonic.parser.attribute.CodeAttribute code = method.getCodeAttribute();
        if (code == null) {
            return false;
        }
        byte[] bytes = code.getCode();
        return bytes != null && bytes.length > 8;
    }

    /** Whether any return or throw appears anywhere in the statement tree. */
    private static boolean containsTerminator(List<Statement> statements) {
        for (Statement s : statements) {
            if (containsTerminator(s)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsTerminator(Statement s) {
        if (s instanceof ReturnStmt || s instanceof ThrowStmt) {
            return true;
        }
        if (s instanceof BlockStmt b) {
            return containsTerminator(b.getStatements());
        }
        if (s instanceof IfStmt i) {
            return containsTerminator(i.getThenBranch())
                    || (i.getElseBranch() != null && containsTerminator(i.getElseBranch()));
        }
        if (s instanceof ForStmt f) {
            return containsTerminator(f.getBody());
        }
        if (s instanceof WhileStmt w) {
            return containsTerminator(w.getBody());
        }
        if (s instanceof DoWhileStmt d) {
            return containsTerminator(d.getBody());
        }
        if (s instanceof ForEachStmt fe) {
            return containsTerminator(fe.getBody());
        }
        if (s instanceof SynchronizedStmt sy) {
            return containsTerminator(sy.getBody());
        }
        if (s instanceof LabeledStmt l) {
            return containsTerminator(l.getStatement());
        }
        if (s instanceof TryCatchStmt t) {
            if (containsTerminator(t.getTryBlock())
                    || (t.getFinallyBlock() != null && containsTerminator(t.getFinallyBlock()))) {
                return true;
            }
            for (CatchClause c : t.getCatches()) {
                if (containsTerminator(c.body())) {
                    return true;
                }
            }
            return false;
        }
        if (s instanceof SwitchStmt sw) {
            for (SwitchCase c : sw.getCases()) {
                if (c.statements() != null && containsTerminator(c.statements())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private static boolean alwaysReturns(Statement s) {
        if (s instanceof ReturnStmt || s instanceof ThrowStmt) {
            return true;
        }
        if (s instanceof BlockStmt block) {
            return alwaysReturns(block.getStatements());
        }
        if (s instanceof IfStmt i) {
            return i.getElseBranch() != null
                    && alwaysReturns(i.getThenBranch()) && alwaysReturns(i.getElseBranch());
        }
        if (s instanceof TryCatchStmt t) {
            if (!alwaysReturns(t.getTryBlock())) {
                return false;
            }
            for (CatchClause c : t.getCatches()) {
                if (!alwaysReturns(c.body())) {
                    return false;
                }
            }
            return true;
        }
        if (s instanceof SwitchStmt sw) {
            boolean hasDefault = false;
            for (SwitchCase c : sw.getCases()) {
                hasDefault |= c.isDefault();
                if (!alwaysReturns(c.statements())) {
                    return false;
                }
            }
            return hasDefault;
        }
        return false;
    }

    /** Runs the supplier with hoisting disabled — for re-evaluated/conditional positions. */
    private String noHoist(java.util.function.Supplier<String> f) {
        boolean prev = allowHoist;
        allowHoist = false;
        try {
            return f.get();
        } finally {
            allowHoist = prev;
        }
    }

    private void statementExpr(Expression e) {
        if (e instanceof BinaryExpr b && isAssignOp(b.getOperator())) {
            emitAssignment(b);
            return;
        }
        if (e instanceof UnaryExpr u && isIncDec(u.getOperator())) {
            w.line(expr(u.getOperand()) + u.getOperator().getSymbol() + ";");
            return;
        }
        if (e instanceof CastExpr c) {
            // A discarded value's checkcast is a no-op as a statement, and a bare cast expression
            // is not a valid C# statement; emit the inner (call/new that carries the side effect).
            statementExpr(c.getExpression());
            return;
        }
        if (e instanceof NewExpr) {
            // A two-phase (RawNew) constructor hoists its allocation + init and yields a bare temp;
            // that temp alone is a discarded no-op and not a valid C# statement (e.g. a dead
            // `x = new StringBuffer(...)` whose store was eliminated). A one-phase `new X(args)` is a
            // valid object-creation statement.
            String rendered = expr(e);
            if (rendered.startsWith("new ")) {
                w.line(rendered + ";");
            }
            return;
        }
        if (e instanceof NewArrayExpr) {
            // A bare `new T[n];` is not a valid C# statement expression; discard it so the allocation
            // (and any NegativeArraySizeException) is preserved when the result is otherwise unused.
            w.line("_ = " + expr(e) + ";");
            return;
        }
        w.line(expr(e) + ";");
    }

    private static boolean isAssignOp(BinaryOperator op) {
        return switch (op) {
            case ASSIGN, ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN,
                    BAND_ASSIGN, BOR_ASSIGN, BXOR_ASSIGN, SHL_ASSIGN, SHR_ASSIGN, USHR_ASSIGN -> true;
            default -> false;
        };
    }

    private static boolean isIncDec(com.tonic.analysis.source.ast.expr.UnaryOperator op) {
        return switch (op) {
            case PRE_INC, PRE_DEC, POST_INC, POST_DEC -> true;
            default -> false;
        };
    }

    private void emitAssignment(BinaryExpr assign) {
        w.line(assignmentText(assign) + ";");
    }

    private String assignmentText(BinaryExpr assign) {
        String lhs = lvalue(assign.getLeft());
        String targetDesc = lvalueDesc(assign.getLeft());
        if (assign.getOperator() == BinaryOperator.ASSIGN) {
            return lhs + " = " + coerced(targetDesc, assign.getRight());
        }
        BinaryOperator base = compoundBase(assign.getOperator());
        String value = binaryValue(base, lhs, expr(assign.getRight()), targetDesc);
        return lhs + " = " + value;
    }

    /**
     * The assignment target's descriptor: a declared local's C# variable holds its declared type, so
     * coercion must target that (the lvalue reference's AST type can be a narrower phi-merge type, which
     * would drop a needed cast — e.g. an interface value stored into an Object slot). Falls back to the
     * expression's own type for fields and other lvalues.
     */
    private String lvalueDesc(Expression lhs) {
        if (lhs instanceof VarRefExpr v) {
            String declared = declaredDesc.get(localName(v.getName()));
            if (declared != null) {
                return declared;
            }
        }
        return descOf(lhs.getType());
    }

    private static BinaryOperator compoundBase(BinaryOperator op) {
        return switch (op) {
            case ADD_ASSIGN -> BinaryOperator.ADD;
            case SUB_ASSIGN -> BinaryOperator.SUB;
            case MUL_ASSIGN -> BinaryOperator.MUL;
            case DIV_ASSIGN -> BinaryOperator.DIV;
            case MOD_ASSIGN -> BinaryOperator.MOD;
            case BAND_ASSIGN -> BinaryOperator.BAND;
            case BOR_ASSIGN -> BinaryOperator.BOR;
            case BXOR_ASSIGN -> BinaryOperator.BXOR;
            case SHL_ASSIGN -> BinaryOperator.SHL;
            case SHR_ASSIGN -> BinaryOperator.SHR;
            case USHR_ASSIGN -> BinaryOperator.USHR;
            default -> throw new UnsupportedBodyException("structured: assignment op " + op);
        };
    }

    private String lvalue(Expression e) {
        if (e instanceof VarRefExpr ref) {
            return scopedRef(ref);
        }
        if (e instanceof FieldAccessExpr field) {
            return fieldRef(field);
        }
        if (e instanceof ArrayAccessExpr access) {
            return expr(access.getArray()) + "[" + expr(access.getIndex()) + "]";
        }
        throw new UnsupportedBodyException("structured: assignment target "
                + e.getClass().getSimpleName());
    }

    /** Renders in value position: C#-bool results bridge to the int ABI. */
    private String expr(Expression e) {
        if (isBoolNative(e)) {
            return "(" + condition(e) + " ? 1 : 0)";
        }
        return value(e);
    }

    /** Renders in condition position: int values bridge with != 0. */
    private String condition(Expression e) {
        if (e instanceof BinaryExpr b) {
            switch (b.getOperator()) {
                case EQ, NE -> {
                    if (isReference(b.getLeft()) || isReference(b.getRight())) {
                        String test = "global::System.Object.ReferenceEquals("
                                + expr(b.getLeft()) + ", " + expr(b.getRight()) + ")";
                        return b.getOperator() == BinaryOperator.EQ ? test : "!" + test;
                    }
                    return atom(b.getLeft()) + " " + b.getOperator().getSymbol() + " " + atom(b.getRight());
                }
                case LT, LE, GT, GE -> {
                    return atom(b.getLeft()) + " " + b.getOperator().getSymbol() + " " + atom(b.getRight());
                }
                case AND, OR -> {
                    return "(" + condition(b.getLeft()) + ") " + b.getOperator().getSymbol()
                            + " (" + noHoist(() -> condition(b.getRight())) + ")";
                }
                default -> {
                }
            }
        }
        if (e instanceof UnaryExpr u
                && u.getOperator() == com.tonic.analysis.source.ast.expr.UnaryOperator.NOT) {
            Expression operand = u.getOperand();
            // Negating an int operand's != 0 bridge is just == 0; avoid the !((x) != 0) double-negation.
            return isBoolNative(operand) ? "!(" + condition(operand) + ")" : atom(operand) + " == 0";
        }
        if (e instanceof InstanceOfExpr i) {
            return instanceOf(i);
        }
        return atom(e) + " != 0";
    }

    private static boolean isBoolNative(Expression e) {
        if (e instanceof BinaryExpr b) {
            return switch (b.getOperator()) {
                case EQ, NE, LT, LE, GT, GE, AND, OR -> true;
                default -> false;
            };
        }
        return e instanceof InstanceOfExpr
                || (e instanceof UnaryExpr u
                        && u.getOperator() == com.tonic.analysis.source.ast.expr.UnaryOperator.NOT);
    }

    private boolean isReference(Expression e) {
        String desc = descOf(e.getType());
        return desc != null && (desc.startsWith("L") || desc.startsWith("["));
    }

    private String value(Expression e) {
        if (e instanceof LiteralExpr literal) {
            return ConstRenderer.renderValue(literal.getValue());
        }
        if (e instanceof VarRefExpr ref) {
            return scopedRef(ref);
        }
        if (e instanceof ThisExpr) {
            return "this";
        }
        if (e instanceof FieldAccessExpr field) {
            return fieldRef(field);
        }
        if (e instanceof MethodCallExpr call) {
            return call(call);
        }
        if (e instanceof NewExpr n) {
            return newInstance(n);
        }
        if (e instanceof NewArrayExpr n) {
            return newArray(n);
        }
        if (e instanceof LambdaExpr lambda) {
            return lambdaSite(require(lambda.getImplMethodKey(), "lambda impl method key"),
                    internalOf(lambda.getType()));
        }
        if (e instanceof MethodRefExpr ref) {
            String implName = ref.isConstructorRef() ? "<init>" : ref.getMethodName();
            return lambdaSite(implName + require(ref.getDescriptor(),
                    "method-ref descriptor for " + ref.getMethodName()), internalOf(ref.getType()));
        }
        if (e instanceof BinaryExpr b) {
            return binary(b);
        }
        if (e instanceof UnaryExpr u) {
            return unary(u);
        }
        if (e instanceof CastExpr c) {
            return cast(c);
        }
        if (e instanceof TernaryExpr t) {
            return "((" + condition(t.getCondition()) + ") ? (" + noHoist(() -> expr(t.getThenExpr()))
                    + ") : (" + noHoist(() -> expr(t.getElseExpr())) + "))";
        }
        if (e instanceof ArrayAccessExpr access) {
            return expr(access.getArray()) + "[" + expr(access.getIndex()) + "]";
        }
        if (e instanceof ClassExpr c) {
            String internal = internalOf(c.getClassType());
            if (internal == null) {
                throw new UnsupportedBodyException("structured: class literal " + c.getClassType());
            }
            return "global::java.lang.Class.Of(" + com.tonic.j2cs.emit.CsStrings.quote(internal.replace('/', '.')) + ")";
        }
        throw new UnsupportedBodyException("structured: " + e.getClass().getSimpleName());
    }

    private String fieldRef(FieldAccessExpr field) {
        if ("[]".equals(field.getOwnerClass())) {
            return atom(field.getReceiver()) + ".Length";
        }
        String desc = require(field.getDescriptor(), "field descriptor for " + field.getFieldName());
        return calls.fieldRef(field.getOwnerClass(), field.getFieldName(), desc,
                field.isStatic(), receiverOf(field.getReceiver()));
    }

    private String call(MethodCallExpr call) {
        String owner = call.getOwnerClass();
        String name = call.getMethodName();
        String desc = require(call.getDescriptor(), "call descriptor for " + owner + "." + name);
        String args = arguments(desc, call.getArguments());
        if (name.equals("super")) {
            return calls.initCall(owner, desc, "this", args);
        }
        if (name.equals("this")) {
            return "this." + MemberNamer.initMethodName(desc) + "(" + args + ")";
        }
        if (call.isSuperCall() || call.getReceiver() instanceof SuperExpr) {
            return calls.superCall(owner, name, desc, args);
        }
        if (owner.equals("java/lang/System") && name.equals("arraycopy")
                && desc.equals("(Ljava/lang/Object;ILjava/lang/Object;II)V")) {
            List<Expression> a = call.getArguments();
            return "global::java.lang.System.arraycopy("
                    + arrayCopyArg(a.get(0)) + ", " + expr(a.get(1)) + ", "
                    + arrayCopyArg(a.get(2)) + ", " + expr(a.get(3)) + ", "
                    + expr(a.get(4)) + ")";
        }
        String receiverDesc = call.getReceiver() == null ? null : descOf(call.getReceiver().getType());
        if (name.equals("clone") && receiverDesc != null && receiverDesc.startsWith("[")) {
            CsType arrayType = naming.typeMapper().computeType(receiverDesc);
            return "(" + arrayType.csText() + ")(" + atom(call.getReceiver()) + ".Clone())";
        }
        if (calls.isShimOwner(owner)) {
            return calls.shimCall(owner, name, desc, receiverOf(call.getReceiver()), args);
        }
        if (call.isStatic()) {
            return calls.staticCall(owner, name, desc, args);
        }
        if (naming.hierarchy().isAppInterface(owner)) {
            return calls.interfaceCall(owner, name, desc, receiverOf(call.getReceiver()), args);
        }
        return calls.virtualCall(owner, name, desc, receiverOf(call.getReceiver()), args);
    }

    /**
     * A recovered lambda or method reference maps back to its invokedynamic site by impl-method
     * key; the site expands to the same synthetic class the goto path builds (shared cp-index
     * keying), constructed with the site's captured arguments rendered through the recovery's
     * SSA-value naming.
     */
    private String lambdaSite(String implMethodKey, String ifaceInternal) {
        String key = StructuredRecovery.siteKey(implMethodKey, ifaceInternal);
        com.tonic.analysis.ssa.ir.InvokeInstruction site = recovered.indySites().get(key);
        if (site == null) {
            throw new UnsupportedBodyException("structured: lambda site not correlated: " + key);
        }
        LambdaExpander.Expansion expansion = lambdaExpander.expand(site, currentClass);
        List<String> paramDescs = TypeMapper.splitParams(site.getDescriptor());
        List<com.tonic.analysis.ssa.value.Value> args = site.getMethodArguments();
        if (args.size() != paramDescs.size()) {
            throw new UnsupportedBodyException("structured: captured argument count mismatch");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(capturedArg(paramDescs.get(i), args.get(i)));
        }
        return "new " + expansion.fqcn() + "(" + sb + ")";
    }

    private String capturedArg(String paramDesc, com.tonic.analysis.ssa.value.Value value) {
        String sourceDesc = value.getType() == null ? null : value.getType().getDescriptor();
        String raw;
        if (value instanceof com.tonic.analysis.ssa.value.Constant constant) {
            raw = ConstRenderer.render(constant);
        } else if (value instanceof SSAValue ssa
                && ssa.getDefinition() instanceof com.tonic.analysis.ssa.ir.ConstantInstruction constDef) {
            raw = ConstRenderer.render(constDef.getConstant());
        } else if (value instanceof SSAValue ssa && paramNames.containsKey(ssa)) {
            raw = paramNames.get(ssa);
        } else if (value instanceof SSAValue fieldSsa
                && fieldSsa.getDefinition() instanceof com.tonic.analysis.ssa.ir.FieldAccessInstruction fa
                && fa.isStatic()) {
            // Bound method-ref receiver that is a static field (e.g. System.out::println): render the
            // field access directly rather than expecting a scoped recovered local.
            raw = calls.fieldRef(fa.getOwner(), fa.getName(), fa.getDescriptor(), true,
                    new CallRenderer.Receiver("", null));
        } else if (value instanceof SSAValue ssa) {
            String recoveredName = recovered.recoverer().getRecoveryContext().getVariableName(ssa);
            if (recoveredName == null) {
                throw new UnsupportedBodyException("structured: captured value has no recovered name");
            }
            raw = "this".equals(recoveredName) ? "this" : localName(recoveredName);
            if (!"this".equals(raw) && !inScope(raw)) {
                throw new UnsupportedBodyException("structured: captured value out of scope: " + recoveredName);
            }
        } else {
            throw new UnsupportedBodyException("structured: captured value kind "
                    + value.getClass().getSimpleName());
        }
        return sourceDesc == null ? raw : reconciler.coerce(paramDesc, sourceDesc, raw);
    }

    private String newInstance(NewExpr n) {
        if (n.getEnclosingInstance() != null) {
            throw new UnsupportedBodyException("structured: inner-class construction");
        }
        String className = n.getClassName();
        if (!naming.isAppClass(className) && !naming.isShimType(className)) {
            throw new UnsupportedBodyException("allocation of type not in input: " + className);
        }
        String desc = require(n.getDescriptor(), "constructor descriptor for " + className);
        String args = arguments(desc, n.getArguments());
        String fqcn = CsNamer.fqcn(className);
        if (naming.isAppClass(className) && naming.hasRealConstructor(className, desc)) {
            return "new " + fqcn + "(" + args + ")";
        }
        if (!allowHoist) {
            throw new UnsupportedBodyException("structured: allocation in conditional context");
        }
        String temp = freshName("__t");
        declareInScope(temp);
        emitRawNewAndInit(fqcn + " ", temp, fqcn, desc, args);
        return temp;
    }

    /** Emits the two-phase RawNew allocation and its __init call against an already-rendered target. */
    private void emitRawNewAndInit(String declPrefix, String target, String fqcn, String desc, String args) {
        w.line(declPrefix + target + " = new " + fqcn + "(global::java.lang.RawNew.I);");
        w.line(target + "." + MemberNamer.initMethodName(desc) + "(" + args + ");");
    }

    /**
     * When {@code rhs} is exactly a two-phase (RawNew) allocation of {@code targetType}, construct it
     * straight into {@code target} and return true, dropping the __t temp newInstance would otherwise
     * introduce. Only used for fresh local declarations: there is no prior target value and no
     * out-of-scope observer, so the store-before-__init ordering is unobservable (a throwing __init
     * leaves the local scoped-out, matching the abandoned store on the JVM). A differing target type,
     * a real constructor, or a hoist-forbidden context all fall back to the generic path.
     */
    private boolean emitTwoPhaseNewInto(String target, String declPrefix, CsType targetType, Expression rhs) {
        if (!allowHoist || !(rhs instanceof NewExpr n) || n.getEnclosingInstance() != null) {
            return false;
        }
        String className = n.getClassName();
        if (!naming.isAppClass(className) && !naming.isShimType(className)) {
            return false;
        }
        String desc = n.getDescriptor();
        if (desc == null || (naming.isAppClass(className) && naming.hasRealConstructor(className, desc))) {
            return false;
        }
        String fqcn = CsNamer.fqcn(className);
        if (!fqcn.equals(targetType.csText())) {
            return false;
        }
        emitRawNewAndInit(declPrefix, target, fqcn, desc, arguments(desc, n.getArguments()));
        return true;
    }

    private String newArray(NewArrayExpr n) {
        String fullDesc = descOf(n.getType());
        if (fullDesc == null || !fullDesc.startsWith("[")) {
            throw new UnsupportedBodyException("structured: array type " + n.getType());
        }
        if (n.getInitializer() != null) {
            return arrayLiteral(fullDesc, n.getInitializer());
        }
        List<Expression> dims = n.getDimensions();
        if (dims.size() == 1) {
            String csText = naming.typeMapper().computeType(fullDesc).csText();
            int bracket = csText.indexOf('[');
            return "new " + csText.substring(0, bracket) + "[" + expr(dims.get(0)) + "]"
                    + csText.substring(bracket + 2);
        }
        if (!allowHoist) {
            throw new UnsupportedBodyException("structured: multi-dim array in conditional context");
        }
        return hoistMultiArray(fullDesc, dims);
    }

    private String arrayLiteral(String arrayDesc, ArrayInitExpr init) {
        String elemDesc = arrayDesc.substring(1);
        CsType csType = naming.typeMapper().computeType(arrayDesc);
        List<String> parts = new ArrayList<>();
        for (Expression el : init.getElements()) {
            parts.add(el instanceof ArrayInitExpr nested
                    ? arrayLiteral(elemDesc, nested)
                    : coerced(elemDesc, el));
        }
        return "new " + csType.csText() + " { " + String.join(", ", parts) + " }";
    }

    private String hoistMultiArray(String fullDesc, List<Expression> dims) {
        String temp = freshName("__t");
        declareInScope(temp);
        String csFull = naming.typeMapper().computeType(fullDesc).csText();
        w.line(csFull + " " + temp + ";");
        String base = csFull.substring(0, csFull.indexOf('['));
        emitMultiArrayLevel(temp, base, dims, 0);
        return temp;
    }

    private void emitMultiArrayLevel(String target, String base, List<Expression> dims, int level) {
        int remaining = dims.size() - level;
        String size = freshName("__n");
        declareInScope(size);
        w.line("int " + size + " = " + expr(dims.get(level)) + ";");
        w.line(target + " = new " + base + "[" + size + "]" + "[]".repeat(remaining - 1) + ";");
        if (remaining > 1) {
            String idx = freshName("__i");
            w.open("for (int " + idx + " = 0; " + idx + " < " + size + "; " + idx + "++)");
            pushScope();
            declareInScope(idx);
            emitMultiArrayLevel(target + "[" + idx + "]", base, dims, level + 1);
            popScope();
            w.close();
        }
    }

    private String binary(BinaryExpr b) {
        String resultDesc = descOf(b.getType());
        if (b.getOperator() == BinaryOperator.ADD && "Ljava/lang/String;".equals(resultDesc)) {
            return concat(b);
        }
        return binaryValue(b.getOperator(), atom(b.getLeft()), atom(b.getRight()), resultDesc);
    }

    private String binaryValue(BinaryOperator op, String left, String right, String resultDesc) {
        String jr = "global::java.lang.JRuntime";
        boolean wraps = "I".equals(resultDesc) || "J".equals(resultDesc);
        return switch (op) {
            case ADD -> wrapping(wraps, left + " + " + right);
            case SUB -> wrapping(wraps, left + " - " + right);
            case MUL -> wrapping(wraps, left + " * " + right);
            case DIV -> "J".equals(resultDesc) ? jr + ".Ldiv(" + left + ", " + right + ")"
                    : "I".equals(resultDesc) ? jr + ".Idiv(" + left + ", " + right + ")"
                    : left + " / " + right;
            case MOD -> "J".equals(resultDesc) ? jr + ".Lrem(" + left + ", " + right + ")"
                    : "I".equals(resultDesc) ? jr + ".Irem(" + left + ", " + right + ")"
                    : left + " % " + right;
            case SHL -> left + " << " + right;
            case SHR -> left + " >> " + right;
            case USHR -> left + " >>> " + right;
            case BAND -> left + " & " + right;
            case BOR -> left + " | " + right;
            case BXOR -> left + " ^ " + right;
            default -> throw new UnsupportedBodyException("structured: binary op " + op);
        };
    }

    /** Java integer arithmetic wraps; unchecked also silences C# compile-time constant checks. */
    private static String wrapping(boolean wraps, String expr) {
        return wraps ? "unchecked(" + expr + ")" : expr;
    }

    private String concat(BinaryExpr b) {
        List<String> parts = new ArrayList<>();
        flattenConcat(b, parts);
        // Every part is a C# string (a quoted literal or a JRuntime.Str/StrZ call), so a '+' chain
        // is string concatenation and reads more naturally than String.Concat(new string[] {...}).
        return "global::java.lang.String.Wrap(" + String.join(" + ", parts) + ")";
    }

    private void flattenConcat(Expression e, List<String> parts) {
        if (e instanceof BinaryExpr b && b.getOperator() == BinaryOperator.ADD
                && "Ljava/lang/String;".equals(descOf(b.getType()))) {
            flattenConcat(b.getLeft(), parts);
            flattenConcat(b.getRight(), parts);
            return;
        }
        if (e instanceof LiteralExpr lit && lit.getValue() instanceof String s) {
            // A string-literal operand: emit the bare C# string instead of round-tripping through
            // java.lang.String (JRuntime.Str(String.Intern("x")) is just "x" as a C# string).
            parts.add(com.tonic.j2cs.emit.CsStrings.quote(s));
            return;
        }
        String desc = descOf(e.getType());
        if (desc == null) {
            desc = "Ljava/lang/Object;";
        }
        if (desc.startsWith("[")) {
            throw new UnsupportedBodyException("array in string concat not supported");
        }
        String conversionDesc = desc.startsWith("L") ? "L" : desc;
        parts.add(ConcatEmitter.stringConversion(conversionDesc, expr(e)));
    }

    private String unary(UnaryExpr u) {
        return switch (u.getOperator()) {
            case NEG -> "unchecked(-(" + expr(u.getOperand()) + "))";
            case POS -> expr(u.getOperand());
            case BNOT -> "~(" + expr(u.getOperand()) + ")";
            default -> throw new UnsupportedBodyException("structured: unary " + u.getOperator());
        };
    }

    private String cast(CastExpr c) {
        String targetDesc = descOf(c.getTargetType());
        String sourceDesc = descOf(c.getExpression().getType());
        String operand = expr(c.getExpression());
        if (targetDesc == null) {
            throw new UnsupportedBodyException("structured: cast to " + c.getTargetType());
        }
        if (TypeMapper.isPrimitiveDescriptor(targetDesc)) {
            return primitiveCast(targetDesc, sourceDesc, operand, needsParens(c.getExpression()));
        }
        // (T[]) arr.clone() already renders the clone cast to the array type, but the recovered
        // clone expression is typed Object; skip the redundant Object->array bridge (no unbox).
        if (targetDesc.startsWith("[") && isArrayClone(c.getExpression())) {
            return operand;
        }
        CsType target = naming.typeMapper().computeType(targetDesc);
        return reconciler.cast(target,
                sourceDesc == null ? null : com.tonic.analysis.ssa.type.IRType.fromDescriptor(sourceDesc),
                operand);
    }

    private boolean isArrayClone(Expression e) {
        if (!(e instanceof MethodCallExpr mc) || !"clone".equals(mc.getMethodName())
                || mc.getReceiver() == null) {
            return false;
        }
        String rd = descOf(mc.getReceiver().getType());
        return rd != null && rd.startsWith("[");
    }

    private String primitiveCast(String targetDesc, String sourceDesc, String operand, boolean parenNeeded) {
        String jr = "global::java.lang.JRuntime";
        boolean fromFloat = "F".equals(sourceDesc) || "D".equals(sourceDesc);
        // A prefix cast binds tighter than a binary/unary operand, so only those need wrapping; the
        // F2I/D2L helpers take the operand as a call argument, where the call parens already isolate it.
        String op = parenNeeded ? "(" + operand + ")" : operand;
        return switch (targetDesc) {
            case "I" -> fromFloat
                    ? jr + ("F".equals(sourceDesc) ? ".F2I(" : ".D2I(") + operand + ")"
                    : "unchecked((int)" + op + ")";
            case "J" -> fromFloat
                    ? jr + ("F".equals(sourceDesc) ? ".F2L(" : ".D2L(") + operand + ")"
                    : "unchecked((long)" + op + ")";
            case "F" -> "(float)" + op;
            case "D" -> "(double)" + op;
            case "B" -> "unchecked((sbyte)" + op + ")";
            case "C" -> "unchecked((char)" + op + ")";
            case "S" -> "unchecked((short)" + op + ")";
            default -> throw new UnsupportedBodyException("structured: cast to " + targetDesc);
        };
    }

    private String instanceOf(InstanceOfExpr i) {
        if (i.getPatternVariable() != null) {
            throw new UnsupportedBodyException("structured: pattern instanceof");
        }
        String internal = internalOf(i.getCheckType());
        if (internal == null) {
            throw new UnsupportedBodyException("structured: instanceof " + i.getCheckType());
        }
        CsType target = naming.typeMapper().computeType(descOf(i.getCheckType()));
        return "(" + expr(i.getExpression()) + " is " + target.csText() + ")";
    }

    private String arguments(String methodDesc, List<Expression> args) {
        List<String> paramDescs = TypeMapper.splitParams(methodDesc);
        if (args.size() != paramDescs.size()) {
            throw new UnsupportedBodyException("structured: argument count mismatch for " + methodDesc);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(coerced(paramDescs.get(i), args.get(i)));
        }
        return sb.toString();
    }

    /**
     * The descriptor C# actually sees for an expression. For a local variable that is its declared
     * (possibly slot-merged, widened) storage type rather than the AST's flow-narrowed type: C# has
     * no flow narrowing, so a use of a widened local needs an explicit cast the narrowed type would
     * otherwise hide.
     */
    private String effectiveDesc(Expression e) {
        if (e instanceof VarRefExpr ref) {
            String declared = declaredDesc.get(localName(ref.getName()));
            if (declared != null) {
                return declared;
            }
        }
        if (e instanceof TernaryExpr t) {
            // C# infers a conditional's type from its branches; when one branch is the null literal
            // the result type is the other branch's, not the widened (often Object) type YABR gave
            // the node. Using the node type would drive a spurious bridge cast — e.g. coercing the
            // whole `cond ? array : null` to its array return type via Unbox, which a raw array fails.
            boolean thenNull = isNullLiteral(t.getThenExpr());
            boolean elseNull = isNullLiteral(t.getElseExpr());
            if (thenNull ^ elseNull) {
                return effectiveDesc(thenNull ? t.getElseExpr() : t.getThenExpr());
            }
        }
        return descOf(e.getType());
    }

    private static boolean isNullLiteral(Expression e) {
        return e instanceof LiteralExpr lit && lit.getValue() == null;
    }

    private static String internalFromDesc(String desc) {
        return desc != null && desc.startsWith("L") && desc.endsWith(";")
                ? desc.substring(1, desc.length() - 1)
                : null;
    }

    private String coerced(String targetDesc, Expression e) {
        String raw = expr(e);
        String sourceDesc = effectiveDesc(e);
        if (sourceDesc == null) {
            return raw;
        }
        if (TypeMapper.isPrimitiveDescriptor(targetDesc) && TypeMapper.isPrimitiveDescriptor(sourceDesc)) {
            if (targetDesc.equals("Z") || sourceDesc.equals("Z")) {
                return raw;
            }
            // char/byte/short are real C# types but constants render as int, so a value entering
            // such a slot always needs an explicit cast; wider slots only when the type differs.
            boolean subInt = targetDesc.equals("C") || targetDesc.equals("B") || targetDesc.equals("S");
            if (subInt || !sourceDesc.equals(targetDesc)) {
                return primitiveCast(targetDesc, sourceDesc, raw, needsParens(e));
            }
            return raw;
        }
        return reconciler.coerce(targetDesc, sourceDesc, raw);
    }

    private CallRenderer.Receiver receiverOf(Expression receiver) {
        if (receiver == null) {
            return new CallRenderer.Receiver("this", currentClass);
        }
        // A method invoked on an array receiver is always a java.lang.Object method (clone and
        // length are handled elsewhere), so box the native array to reach the shim Object.
        String desc = effectiveDesc(receiver);
        if (desc != null && desc.startsWith("[")) {
            return new CallRenderer.Receiver(
                    "global::java.lang.JRuntime.Box(" + atom(receiver) + ", \"" + desc + "\")",
                    "java/lang/Object");
        }
        return new CallRenderer.Receiver(atom(receiver), internalFromDesc(desc));
    }

    /** Parenthesizes compound sub-expressions for precedence safety. */
    private String atom(Expression e) {
        String text = expr(e);
        if (needsParens(e)) {
            return "(" + text + ")";
        }
        return text;
    }

    /** Whether a rendered expression binds looser than a prefix cast/unary operator would want. */
    private static boolean needsParens(Expression e) {
        return e instanceof BinaryExpr || e instanceof CastExpr || e instanceof UnaryExpr;
    }

    private String descOf(SourceType type) {
        if (type == null) {
            return null;
        }
        try {
            return type.toIRType().getDescriptor();
        } catch (RuntimeException e) {
            return null;
        }
    }

    private String internalOf(SourceType type) {
        return type instanceof ReferenceSourceType ref ? ref.getInternalName() : null;
    }

    /** A variable read/write must resolve to an active scope or the emitted C# won't compile. */
    private String scopedRef(VarRefExpr ref) {
        String name = localName(ref.getName());
        if (!inScope(name)) {
            throw new UnsupportedBodyException("structured: variable used out of scope: " + ref.getName());
        }
        return name;
    }

    /**
     * An arraycopy array argument coerced to System.Array (the shim parameter type). A native array
     * passes through — every C# array derives from System.Array — while an array carried as a boxed
     * java.lang.Object is unwrapped to the underlying array.
     */
    private String arrayCopyArg(Expression arg) {
        String descriptor = descOf(arg.getType());
        if (descriptor != null && descriptor.startsWith("[")) {
            return expr(arg);
        }
        return "global::java.lang.JRuntime.Unbox(" + expr(arg) + ")";
    }

    private String localName(String recovered) {
        String mapped = names.get(recovered);
        if (mapped != null) {
            return mapped;
        }
        String candidate = CsNamer.unique(CsNamer.identifier(recovered), usedNames);
        usedNames.add(candidate);
        names.put(recovered, candidate);
        return candidate;
    }

    private String freshName(String base) {
        String candidate = CsNamer.unique(base + hoistCounter++, usedNames);
        usedNames.add(candidate);
        return candidate;
    }

    private static String require(String value, String what) {
        if (value == null) {
            throw new UnsupportedBodyException("structured: missing " + what);
        }
        return value;
    }
}
