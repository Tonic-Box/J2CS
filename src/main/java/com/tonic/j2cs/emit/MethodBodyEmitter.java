package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.cfg.IRBlock;
import com.tonic.analysis.ssa.cfg.IRMethod;
import com.tonic.analysis.ssa.ir.ArrayAccessInstruction;
import com.tonic.analysis.ssa.ir.BinaryOpInstruction;
import com.tonic.analysis.ssa.ir.BranchInstruction;
import com.tonic.analysis.ssa.ir.CompareOp;
import com.tonic.analysis.ssa.ir.ConstantInstruction;
import com.tonic.analysis.ssa.ir.CopyInstruction;
import com.tonic.analysis.ssa.ir.FieldAccessInstruction;
import com.tonic.analysis.ssa.ir.IRInstruction;
import com.tonic.analysis.ssa.ir.InvokeInstruction;
import com.tonic.analysis.ssa.ir.InvokeType;
import com.tonic.analysis.ssa.ir.LoadLocalInstruction;
import com.tonic.analysis.ssa.ir.NewArrayInstruction;
import com.tonic.analysis.ssa.ir.NewInstruction;
import com.tonic.analysis.ssa.ir.PhiInstruction;
import com.tonic.analysis.ssa.ir.ReturnInstruction;
import com.tonic.analysis.ssa.ir.SimpleInstruction;
import com.tonic.analysis.ssa.ir.StoreLocalInstruction;
import com.tonic.analysis.ssa.ir.SwitchInstruction;
import com.tonic.analysis.ssa.ir.TypeCheckInstruction;
import com.tonic.analysis.ssa.ir.UnaryOpInstruction;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.analysis.ssa.value.Value;
import com.tonic.analysis.ssa.visitor.IRVisitor;
import com.tonic.j2cs.model.LoweredMethod;
import com.tonic.j2cs.model.SlotDecl;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.shims.ShimTarget;
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.j2cs.types.Coercions;
import com.tonic.j2cs.types.CsType;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.MethodEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Emits one method body as goto/label-style C#: slot declarations initialized to defaults,
 * parameter copies, then each block in reverse post order ending in an explicit terminator.
 * Throws UnsupportedBodyException for constructs outside M0 scope; the caller degrades the
 * method to a stub.
 */
public final class MethodBodyEmitter implements IRVisitor<Void> {

    private final NamingContext naming;

    private CsWriter w;
    private ValueNames names;
    private LoweredMethod lowered;
    private CsType returnType;
    private Map<Integer, String> slotCs;
    private boolean terminated;

    public MethodBodyEmitter(NamingContext naming) {
        this.naming = naming;
    }

    public String emit(MethodEntry method, LoweredMethod loweredMethod, int indentDepth) {
        this.lowered = loweredMethod;
        this.names = new ValueNames(loweredMethod.slotOf());
        this.returnType = naming.typeMapper().returnType(method.getDesc());
        this.w = new CsWriter(indentDepth);
        this.slotCs = new HashMap<>();

        TypeMapper types = naming.typeMapper();
        for (SlotDecl slot : loweredMethod.slots()) {
            CsType type = types.computeType(slot.computeType());
            slotCs.put(slot.slotId(), type.csText());
            w.line(type.csText() + " s" + slot.slotId() + " = " + type.defaultLiteral() + ";");
        }
        emitParameterCopies();
        for (IRBlock block : loweredMethod.ir().getReversePostOrder()) {
            w.line("B" + block.getId() + ": ;");
            terminated = false;
            for (IRInstruction instr : block.getInstructions()) {
                instr.accept(this);
            }
            if (!terminated) {
                emitFallthrough(block);
            }
        }
        if (returnType.kind() != CsType.Kind.VOID) {
            w.line("throw new global::System.InvalidOperationException(\"j2cs: fell off method end\");");
        }
        return w.toString();
    }

    private void emitParameterCopies() {
        IRMethod ir = lowered.ir();
        List<SSAValue> params = ir.getParameters();
        boolean isStatic = ir.isStatic();
        for (int i = 0; i < params.size(); i++) {
            Integer slot = lowered.slotOf().get(params.get(i));
            if (slot == null) {
                continue;
            }
            String expr = isStatic ? "p" + i : (i == 0 ? "this" : "p" + (i - 1));
            w.line("s" + slot + " = " + expr + ";");
        }
    }

    private void emitFallthrough(IRBlock block) {
        Set<IRBlock> successors = block.getSuccessors();
        if (successors.size() == 1) {
            w.line("goto B" + successors.iterator().next().getId() + ";");
        } else {
            throw new UnsupportedBodyException("block B" + block.getId()
                    + " has no terminator and " + successors.size() + " successors");
        }
    }

    private void assign(IRInstruction instr, String expr) {
        SSAValue result = instr.getResult();
        if (result == null) {
            w.line(expr + ";");
            return;
        }
        Integer slot = lowered.slotOf().get(result);
        if (slot == null) {
            throw new UnsupportedBodyException("result v" + result.getId() + " has no slot");
        }
        w.line("s" + slot + " = " + expr + ";");
    }

    @Override
    public Void visitConstant(ConstantInstruction instr) {
        assign(instr, ConstRenderer.render(instr.getConstant()));
        return null;
    }

    @Override
    public Void visitCopy(CopyInstruction instr) {
        SSAValue result = instr.getResult();
        Value source = instr.getSource();
        Integer targetSlot = result == null ? null : lowered.slotOf().get(result);
        if (source instanceof SSAValue ssa && targetSlot != null
                && targetSlot.equals(lowered.slotOf().get(ssa))) {
            return null;
        }
        String expr = names.ref(source);
        if (source instanceof SSAValue ssa && targetSlot != null) {
            String targetCs = slotCs.get(targetSlot);
            String sourceCs = naming.typeMapper().computeType(ssa.getType()).csText();
            if (!targetCs.equals(sourceCs)) {
                expr = "(" + targetCs + ")(" + expr + ")";
            }
        }
        assign(instr, expr);
        return null;
    }

    @Override
    public Void visitBinaryOp(BinaryOpInstruction instr) {
        String l = names.ref(instr.getLeft());
        String r = names.ref(instr.getRight());
        String resultCs = naming.typeMapper().computeType(instr.getResult().getType()).csText();
        String expr = switch (instr.getOp()) {
            case ADD -> l + " + " + r;
            case SUB -> l + " - " + r;
            case MUL -> l + " * " + r;
            case DIV -> switch (resultCs) {
                case "int" -> "global::java.lang.JRuntime.Idiv(" + l + ", " + r + ")";
                case "long" -> "global::java.lang.JRuntime.Ldiv(" + l + ", " + r + ")";
                default -> l + " / " + r;
            };
            case REM -> switch (resultCs) {
                case "int" -> "global::java.lang.JRuntime.Irem(" + l + ", " + r + ")";
                case "long" -> "global::java.lang.JRuntime.Lrem(" + l + ", " + r + ")";
                default -> l + " % " + r;
            };
            case SHL -> l + " << " + r;
            case SHR -> l + " >> " + r;
            case USHR -> l + " >>> " + r;
            case AND -> l + " & " + r;
            case OR -> l + " | " + r;
            case XOR -> l + " ^ " + r;
            case LCMP -> "global::java.lang.JRuntime.Lcmp(" + l + ", " + r + ")";
            case FCMPL -> "global::java.lang.JRuntime.Fcmpl(" + l + ", " + r + ")";
            case FCMPG -> "global::java.lang.JRuntime.Fcmpg(" + l + ", " + r + ")";
            case DCMPL -> "global::java.lang.JRuntime.Dcmpl(" + l + ", " + r + ")";
            case DCMPG -> "global::java.lang.JRuntime.Dcmpg(" + l + ", " + r + ")";
        };
        assign(instr, expr);
        return null;
    }

    @Override
    public Void visitUnaryOp(UnaryOpInstruction instr) {
        String x = names.ref(instr.getOperand());
        String expr = switch (instr.getOp()) {
            case NEG -> "-(" + x + ")";
            case I2L -> "(long)(" + x + ")";
            case I2F, L2F, D2F -> "(float)(" + x + ")";
            case I2D, L2D, F2D -> "(double)(" + x + ")";
            case L2I -> "(int)(" + x + ")";
            case F2I -> "global::java.lang.JRuntime.F2I(" + x + ")";
            case F2L -> "global::java.lang.JRuntime.F2L(" + x + ")";
            case D2I -> "global::java.lang.JRuntime.D2I(" + x + ")";
            case D2L -> "global::java.lang.JRuntime.D2L(" + x + ")";
            case I2B -> "(sbyte)(" + x + ")";
            case I2C -> "(char)(" + x + ")";
            case I2S -> "(short)(" + x + ")";
        };
        assign(instr, expr);
        return null;
    }

    @Override
    public Void visitBranch(BranchInstruction instr) {
        String condition = renderCondition(instr);
        w.line("if (" + condition + ") goto B" + instr.getTrueTarget().getId() + ";");
        w.line("goto B" + instr.getFalseTarget().getId() + ";");
        terminated = true;
        return null;
    }

    private String renderCondition(BranchInstruction instr) {
        CompareOp op = instr.getCondition();
        String l = names.ref(instr.getLeft());
        return switch (op) {
            case ACMPEQ -> "global::System.Object.ReferenceEquals(" + l + ", " + names.ref(instr.getRight()) + ")";
            case ACMPNE -> "!global::System.Object.ReferenceEquals(" + l + ", " + names.ref(instr.getRight()) + ")";
            case IFNULL -> "global::System.Object.ReferenceEquals(" + l + ", null)";
            case IFNONNULL -> "!global::System.Object.ReferenceEquals(" + l + ", null)";
            default -> {
                String r = instr.getRight() == null ? "0" : names.ref(instr.getRight());
                yield l + " " + comparisonSymbol(op) + " " + r;
            }
        };
    }

    private static String comparisonSymbol(CompareOp op) {
        return switch (op) {
            case EQ, IFEQ -> "==";
            case NE, IFNE -> "!=";
            case LT, IFLT -> "<";
            case GE, IFGE -> ">=";
            case GT, IFGT -> ">";
            case LE, IFLE -> "<=";
            default -> throw new UnsupportedBodyException("comparison not supported: " + op);
        };
    }

    @Override
    public Void visitReturn(ReturnInstruction instr) {
        if (instr.getReturnValue() == null) {
            w.line("return;");
        } else {
            w.line("return " + Coercions.coerce(returnType, names.ref(instr.getReturnValue())) + ";");
        }
        terminated = true;
        return null;
    }

    @Override
    public Void visitInvoke(InvokeInstruction instr) {
        if (instr.getInvokeType() == InvokeType.DYNAMIC) {
            throw new UnsupportedBodyException("invokedynamic not supported yet: "
                    + instr.getName() + instr.getDescriptor());
        }
        if (instr.getInvokeType() == InvokeType.INTERFACE) {
            throw new UnsupportedBodyException("invokeinterface not supported in M0: "
                    + instr.getOwner() + "." + instr.getName());
        }
        String owner = instr.getOwner();
        String name = instr.getName();
        String desc = instr.getDescriptor();
        String call;
        if (name.equals("<init>")) {
            call = names.ref(instr.getReceiver()) + "." + MemberNamer.initMethodName(desc)
                    + "(" + renderArguments(instr, desc) + ")";
        } else if (owner.startsWith("java/") || owner.startsWith("javax/")) {
            Optional<ShimTarget> target = ShimRegistry.method(owner, name, desc);
            if (target.isEmpty()) {
                throw new UnsupportedBodyException("shim member not implemented: "
                        + owner + "." + name + desc);
            }
            String receiver = target.get().isStatic()
                    ? CsNamer.fqcn(owner)
                    : names.ref(instr.getReceiver());
            call = receiver + "." + target.get().csMemberName() + "(" + renderArguments(instr, desc) + ")";
        } else if (naming.isAppClass(owner)) {
            String member = naming.namerOf(owner).methodName(name, desc);
            String receiver = instr.getInvokeType() == InvokeType.STATIC
                    ? CsNamer.fqcn(owner)
                    : names.ref(instr.getReceiver());
            call = receiver + "." + member + "(" + renderArguments(instr, desc) + ")";
        } else {
            throw new UnsupportedBodyException("call target not in input: " + owner + "." + name + desc);
        }
        assign(instr, call);
        return null;
    }

    private String renderArguments(InvokeInstruction instr, String desc) {
        List<String> paramDescs = TypeMapper.splitParams(desc);
        List<Value> args = instr.getMethodArguments();
        if (args.size() != paramDescs.size()) {
            throw new UnsupportedBodyException("argument count mismatch for " + instr.getName() + desc);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            CsType storage = naming.typeMapper().storageType(paramDescs.get(i));
            sb.append(Coercions.coerce(storage, names.ref(args.get(i))));
        }
        return sb.toString();
    }

    @Override
    public Void visitFieldAccess(FieldAccessInstruction instr) {
        String owner = instr.getOwner();
        if (owner.startsWith("java/") || owner.startsWith("javax/")) {
            Optional<ShimTarget> target = ShimRegistry.field(owner, instr.getName(), instr.getDescriptor());
            if (target.isEmpty()) {
                throw new UnsupportedBodyException("shim field not implemented: "
                        + owner + "." + instr.getName());
            }
            if (!instr.isLoad() || !target.get().isStatic()) {
                throw new UnsupportedBodyException("shim field access mode not supported: "
                        + owner + "." + instr.getName());
            }
            assign(instr, CsNamer.fqcn(owner) + "." + target.get().csMemberName());
            return null;
        }
        throw new UnsupportedBodyException("field access not supported yet: "
                + owner + "." + instr.getName());
    }

    @Override
    public Void visitSimple(SimpleInstruction instr) {
        switch (instr.getOp()) {
            case GOTO -> {
                w.line("goto B" + instr.getTarget().getId() + ";");
                terminated = true;
            }
            case ARRAYLENGTH -> throw new UnsupportedBodyException("arrays not supported yet");
            case ATHROW -> throw new UnsupportedBodyException("athrow not supported in M0");
            case MONITORENTER, MONITOREXIT ->
                    throw new UnsupportedBodyException("monitors not supported in M0");
        }
        return null;
    }

    @Override
    public Void visitSwitch(SwitchInstruction instr) {
        w.open("switch (" + names.ref(instr.getKey()) + ")");
        for (Map.Entry<Integer, IRBlock> entry : instr.getCases().entrySet()) {
            w.line("case " + entry.getKey() + ": goto B" + entry.getValue().getId() + ";");
        }
        w.line("default: goto B" + instr.getDefaultTarget().getId() + ";");
        w.close();
        terminated = true;
        return null;
    }

    @Override
    public Void visitNew(NewInstruction instr) {
        throw new UnsupportedBodyException("object allocation not supported yet");
    }

    @Override
    public Void visitNewArray(NewArrayInstruction instr) {
        throw new UnsupportedBodyException("arrays not supported yet");
    }

    @Override
    public Void visitArrayAccess(ArrayAccessInstruction instr) {
        throw new UnsupportedBodyException("arrays not supported yet");
    }

    @Override
    public Void visitTypeCheck(TypeCheckInstruction instr) {
        throw new UnsupportedBodyException("checkcast/instanceof not supported yet");
    }

    @Override
    public Void visitPhi(PhiInstruction instr) {
        throw new UnsupportedBodyException("unexpected phi after SSA destruction");
    }

    @Override
    public Void visitLoadLocal(LoadLocalInstruction instr) {
        throw new UnsupportedBodyException("unexpected load-local artifact");
    }

    @Override
    public Void visitStoreLocal(StoreLocalInstruction instr) {
        throw new UnsupportedBodyException("unexpected store-local artifact");
    }
}
