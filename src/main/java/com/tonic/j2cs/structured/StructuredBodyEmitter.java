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
    private Set<String> usedNames;
    private Map<String, String> breakLabels;
    private Map<SSAValue, String> paramNames;
    private java.util.Deque<Set<String>> scopes;
    private int hoistCounter;
    private boolean allowHoist;

    StructuredBodyEmitter(NamingContext naming, SyntheticClasses synthetics) {
        this.naming = naming;
        this.reconciler = new TypeReconciler(naming);
        this.calls = new CallRenderer(naming, reconciler);
        this.lambdaExpander = new LambdaExpander(naming, synthetics);
    }

    String emit(ClassFile classFile, MethodEntry method, StructuredRecovery.Recovered recovered, int indentDepth) {
        this.w = new CsWriter(indentDepth);
        this.recovered = recovered;
        this.currentClass = classFile.getClassName();
        this.returnDesc = TypeMapper.returnDescriptor(method.getDesc());
        this.names = new HashMap<>();
        this.usedNames = new HashSet<>();
        this.breakLabels = new HashMap<>();
        this.scopes = new java.util.ArrayDeque<>();
        this.hoistCounter = 0;
        this.allowHoist = true;
        pushScope();
        mapParameters(recovered);
        for (Statement s : recovered.body().getStatements()) {
            stmt(s);
        }
        // Non-void methods need a guaranteed terminator: recovery may leave a path without a
        // return (or one C# cannot prove returns), so mirror the goto emitter's fall-off guard.
        // Unreachable-code (CS0162) when the body already returns everywhere is suppressed.
        if (!"V".equals(returnDesc)) {
            w.line("throw new global::System.InvalidOperationException(\"j2cs: fell off method end\");");
        }
        return w.toString();
    }

    private void mapParameters(StructuredRecovery.Recovered recovered) {
        paramNames = new IdentityHashMap<>();
        List<SSAValue> params = recovered.ir().getParameters();
        boolean isStatic = recovered.ir().isStatic();
        for (int i = 0; i < params.size(); i++) {
            String pname = isStatic ? "p" + i : (i == 0 ? "this" : "p" + (i - 1));
            paramNames.put(params.get(i), pname);
            usedNames.add(pname);
            declareInScope(pname);
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

    private void stmt(Statement s) {
        if (s instanceof BlockStmt block) {
            for (Statement child : block.getStatements()) {
                stmt(child);
            }
        } else if (s instanceof VarDeclStmt v) {
            String targetDesc = descOf(v.getType());
            CsType type = naming.typeMapper().storageType(targetDesc);
            String init = v.getInitializer() != null
                    ? coerced(targetDesc, v.getInitializer())
                    : type.defaultLiteral();
            String name = localName(v.getName());
            if (inScope(name)) {
                w.line(name + " = " + init + ";");
            } else {
                declareInScope(name);
                w.line(type.csText() + " " + name + " = " + init + ";");
            }
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
        for (Statement init : f.getInit()) {
            stmt(init);
        }
        String cond = f.getCondition() == null ? "" : noHoist(() -> condition(f.getCondition()));
        List<String> updates = new ArrayList<>();
        for (Expression u : f.getUpdate()) {
            updates.add(noHoist(() -> updateExpr(u)));
        }
        emitLoop(f.getLabel(), () -> {
            w.open("for (; " + cond + "; " + String.join(", ", updates) + ")");
            scoped(f.getBody());
            w.close();
        });
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
        String targetDesc = descOf(assign.getLeft().getType());
        if (assign.getOperator() == BinaryOperator.ASSIGN) {
            return lhs + " = " + coerced(targetDesc, assign.getRight());
        }
        BinaryOperator base = compoundBase(assign.getOperator());
        String value = binaryValue(base, lhs, expr(assign.getRight()), targetDesc);
        return lhs + " = " + value;
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
            return "((" + condition(e) + ") ? 1 : 0)";
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
            return "!(" + condition(u.getOperand()) + ")";
        }
        if (e instanceof InstanceOfExpr i) {
            return instanceOf(i);
        }
        return "(" + value(e) + ") != 0";
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
            StringBuilder raw = new StringBuilder();
            for (Expression arg : call.getArguments()) {
                if (!raw.isEmpty()) {
                    raw.append(", ");
                }
                raw.append(expr(arg));
            }
            return "global::java.lang.System.arraycopy(" + raw + ")";
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
        if (paramDesc.startsWith("L") && sourceDesc != null && sourceDesc.startsWith("[")) {
            throw new UnsupportedBodyException(
                    "array passed as object reference not supported (arrays are native C# arrays)");
        }
        String raw;
        if (value instanceof com.tonic.analysis.ssa.value.Constant constant) {
            raw = ConstRenderer.render(constant);
        } else if (value instanceof SSAValue ssa
                && ssa.getDefinition() instanceof com.tonic.analysis.ssa.ir.ConstantInstruction constDef) {
            raw = ConstRenderer.render(constDef.getConstant());
        } else if (value instanceof SSAValue ssa && paramNames.containsKey(ssa)) {
            raw = paramNames.get(ssa);
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
        w.line(fqcn + " " + temp + " = new " + fqcn + "(global::java.lang.RawNew.I);");
        w.line(temp + "." + MemberNamer.initMethodName(desc) + "(" + args + ");");
        return temp;
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
        return "global::java.lang.String.Wrap(global::System.String.Concat(new string[] { "
                + String.join(", ", parts) + " }))";
    }

    private void flattenConcat(Expression e, List<String> parts) {
        if (e instanceof BinaryExpr b && b.getOperator() == BinaryOperator.ADD
                && "Ljava/lang/String;".equals(descOf(b.getType()))) {
            flattenConcat(b.getLeft(), parts);
            flattenConcat(b.getRight(), parts);
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
            return primitiveCast(targetDesc, sourceDesc, operand);
        }
        CsType target = naming.typeMapper().computeType(targetDesc);
        return reconciler.cast(target,
                sourceDesc == null ? null : com.tonic.analysis.ssa.type.IRType.fromDescriptor(sourceDesc),
                operand);
    }

    private String primitiveCast(String targetDesc, String sourceDesc, String operand) {
        String jr = "global::java.lang.JRuntime";
        boolean fromFloat = "F".equals(sourceDesc) || "D".equals(sourceDesc);
        return switch (targetDesc) {
            case "I" -> fromFloat
                    ? jr + ("F".equals(sourceDesc) ? ".F2I(" : ".D2I(") + operand + ")"
                    : "unchecked((int)(" + operand + "))";
            case "J" -> fromFloat
                    ? jr + ("F".equals(sourceDesc) ? ".F2L(" : ".D2L(") + operand + ")"
                    : "unchecked((long)(" + operand + "))";
            case "F" -> "(float)(" + operand + ")";
            case "D" -> "(double)(" + operand + ")";
            case "B" -> "unchecked((sbyte)(" + operand + "))";
            case "C" -> "unchecked((char)(" + operand + "))";
            case "S" -> "unchecked((short)(" + operand + "))";
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

    private String coerced(String targetDesc, Expression e) {
        String raw = expr(e);
        String sourceDesc = descOf(e.getType());
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
                return primitiveCast(targetDesc, sourceDesc, raw);
            }
            return raw;
        }
        if (targetDesc.startsWith("L") && sourceDesc.startsWith("[")) {
            throw new UnsupportedBodyException(
                    "array passed as object reference not supported (arrays are native C# arrays)");
        }
        return reconciler.coerce(targetDesc, sourceDesc, raw);
    }

    private CallRenderer.Receiver receiverOf(Expression receiver) {
        if (receiver == null) {
            return new CallRenderer.Receiver("this", currentClass);
        }
        return new CallRenderer.Receiver(atom(receiver), internalOf(receiver.getType()));
    }

    /** Parenthesizes compound sub-expressions for precedence safety. */
    private String atom(Expression e) {
        String text = expr(e);
        if (e instanceof BinaryExpr || e instanceof CastExpr || e instanceof UnaryExpr) {
            return "(" + text + ")";
        }
        return text;
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
