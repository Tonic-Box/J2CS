package com.tonic.j2cs.structured;

import com.tonic.analysis.source.ast.expr.ArrayAccessExpr;
import com.tonic.analysis.source.ast.expr.BinaryExpr;
import com.tonic.analysis.source.ast.expr.BinaryOperator;
import com.tonic.analysis.source.ast.expr.CastExpr;
import com.tonic.analysis.source.ast.expr.ClassExpr;
import com.tonic.analysis.source.ast.expr.Expression;
import com.tonic.analysis.source.ast.expr.FieldAccessExpr;
import com.tonic.analysis.source.ast.expr.InstanceOfExpr;
import com.tonic.analysis.source.ast.expr.LiteralExpr;
import com.tonic.analysis.source.ast.expr.MethodCallExpr;
import com.tonic.analysis.source.ast.expr.NewExpr;
import com.tonic.analysis.source.ast.expr.SuperExpr;
import com.tonic.analysis.source.ast.expr.TernaryExpr;
import com.tonic.analysis.source.ast.expr.ThisExpr;
import com.tonic.analysis.source.ast.expr.UnaryExpr;
import com.tonic.analysis.source.ast.expr.VarRefExpr;
import com.tonic.analysis.source.ast.stmt.BlockStmt;
import com.tonic.analysis.source.ast.stmt.DoWhileStmt;
import com.tonic.analysis.source.ast.stmt.ExprStmt;
import com.tonic.analysis.source.ast.stmt.IfStmt;
import com.tonic.analysis.source.ast.stmt.ReturnStmt;
import com.tonic.analysis.source.ast.stmt.Statement;
import com.tonic.analysis.source.ast.stmt.ThrowStmt;
import com.tonic.analysis.source.ast.stmt.VarDeclStmt;
import com.tonic.analysis.source.ast.stmt.WhileStmt;
import com.tonic.analysis.source.ast.type.ReferenceSourceType;
import com.tonic.analysis.source.ast.type.SourceType;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.j2cs.emit.CallRenderer;
import com.tonic.j2cs.emit.ConcatEmitter;
import com.tonic.j2cs.emit.ConstRenderer;
import com.tonic.j2cs.emit.CsWriter;
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

    private CsWriter w;
    private String currentClass;
    private String returnDesc;
    private Map<String, String> names;
    private Set<String> usedNames;
    private int hoistCounter;

    StructuredBodyEmitter(NamingContext naming) {
        this.naming = naming;
        this.reconciler = new TypeReconciler(naming);
        this.calls = new CallRenderer(naming, reconciler);
    }

    String emit(ClassFile classFile, MethodEntry method, StructuredRecovery.Recovered recovered, int indentDepth) {
        this.w = new CsWriter(indentDepth);
        this.currentClass = classFile.getClassName();
        this.returnDesc = TypeMapper.returnDescriptor(method.getDesc());
        this.names = new HashMap<>();
        this.usedNames = new HashSet<>();
        this.hoistCounter = 0;
        mapParameters(recovered);
        for (Statement s : recovered.body().getStatements()) {
            stmt(s);
        }
        return w.toString();
    }

    private void mapParameters(StructuredRecovery.Recovered recovered) {
        Map<SSAValue, String> targets = new IdentityHashMap<>();
        List<SSAValue> params = recovered.ir().getParameters();
        boolean isStatic = recovered.ir().isStatic();
        for (int i = 0; i < params.size(); i++) {
            targets.put(params.get(i), isStatic ? "p" + i : (i == 0 ? "this" : "p" + (i - 1)));
        }
        recovered.body().walk(node -> {
            if (node instanceof VarRefExpr ref && ref.getSsaValue() != null) {
                String target = targets.get(ref.getSsaValue());
                if (target != null) {
                    names.put(ref.getName(), target);
                    usedNames.add(target);
                }
            }
        });
        usedNames.add("this");
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
            w.line(type.csText() + " " + localName(v.getName()) + " = " + init + ";");
        } else if (s instanceof ExprStmt e) {
            statementExpr(e.getExpression());
        } else if (s instanceof IfStmt i) {
            w.open("if (" + condition(i.getCondition()) + ")");
            stmt(i.getThenBranch());
            w.close();
            if (i.getElseBranch() != null) {
                w.open("else");
                stmt(i.getElseBranch());
                w.close();
            }
        } else if (s instanceof WhileStmt l) {
            requireNoLabel(l.getLabel());
            w.open("while (" + condition(l.getCondition()) + ")");
            stmt(l.getBody());
            w.close();
        } else if (s instanceof DoWhileStmt l) {
            requireNoLabel(l.getLabel());
            w.open("do");
            stmt(l.getBody());
            w.closeWith("while (" + condition(l.getCondition()) + ");");
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

    private void requireNoLabel(String label) {
        if (label != null) {
            throw new UnsupportedBodyException("structured: labeled loop");
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
        String lhs = lvalue(assign.getLeft());
        String targetDesc = descOf(assign.getLeft().getType());
        if (assign.getOperator() == BinaryOperator.ASSIGN) {
            w.line(lhs + " = " + coerced(targetDesc, assign.getRight()) + ";");
            return;
        }
        BinaryOperator base = compoundBase(assign.getOperator());
        String value = binaryValue(base, lhs, targetDesc,
                expr(assign.getRight()), descOf(assign.getRight().getType()), targetDesc);
        w.line(lhs + " = " + value + ";");
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
            return localName(ref.getName());
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
                            + " (" + condition(b.getRight()) + ")";
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
            return localName(ref.getName());
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
            return hoistNew(n);
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
            return "((" + condition(t.getCondition()) + ") ? (" + expr(t.getThenExpr())
                    + ") : (" + expr(t.getElseExpr()) + "))";
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

    private String hoistNew(NewExpr n) {
        if (n.getEnclosingInstance() != null) {
            throw new UnsupportedBodyException("structured: inner-class construction");
        }
        String className = n.getClassName();
        if (!naming.isAppClass(className) && !naming.isShimType(className)) {
            throw new UnsupportedBodyException("allocation of type not in input: " + className);
        }
        String desc = require(n.getDescriptor(), "constructor descriptor for " + className);
        String args = arguments(desc, n.getArguments());
        String temp = freshName("__t");
        String fqcn = CsNamer.fqcn(className);
        w.line(fqcn + " " + temp + " = new " + fqcn + "(global::java.lang.RawNew.I);");
        w.line(temp + "." + MemberNamer.initMethodName(desc) + "(" + args + ");");
        return temp;
    }

    private String binary(BinaryExpr b) {
        String resultDesc = descOf(b.getType());
        if (b.getOperator() == BinaryOperator.ADD && "Ljava/lang/String;".equals(resultDesc)) {
            return concat(b);
        }
        return binaryValue(b.getOperator(), atom(b.getLeft()), descOf(b.getLeft().getType()),
                atom(b.getRight()), descOf(b.getRight().getType()), resultDesc);
    }

    private String binaryValue(BinaryOperator op, String left, String leftDesc,
                               String right, String rightDesc, String resultDesc) {
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
