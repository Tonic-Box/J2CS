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
import com.tonic.analysis.ssa.type.IRType;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.analysis.ssa.value.Value;
import com.tonic.analysis.ssa.visitor.IRVisitor;
import com.tonic.j2cs.model.LoweredMethod;
import com.tonic.j2cs.model.SlotDecl;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.types.CsType;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.MethodEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Emits one method body as goto/label-style C#: slot declarations initialized to defaults,
 * parameter copies, then each block in reverse post order ending in an explicit terminator.
 * Throws UnsupportedBodyException for constructs outside the supported subset; the caller
 * degrades the method to a stub.
 */
public final class MethodBodyEmitter implements IRVisitor<Void> {

    private final NamingContext naming;
    private final LambdaExpander lambdaExpander;
    private final TypeReconciler reconciler;

    private CsWriter w;
    private ValueNames names;
    private LoweredMethod lowered;
    private CsType returnType;
    private Map<Integer, CsType> slotCs;
    private Map<Integer, IRType> slotIr;
    private boolean terminated;
    private ExceptionLayout layout;
    private InvokeRenderer invokes;
    private int multiArrayCounter;

    public MethodBodyEmitter(NamingContext naming, SyntheticClasses synthetics) {
        this.naming = naming;
        this.lambdaExpander = new LambdaExpander(naming, synthetics);
        this.reconciler = new TypeReconciler(naming);
    }

    public String emit(String ownerInternalName, MethodEntry method, LoweredMethod loweredMethod, int indentDepth) {
        this.lowered = loweredMethod;
        this.names = new ValueNames(loweredMethod.slotOf());
        this.returnType = naming.typeMapper().returnType(method.getDesc());
        this.w = new CsWriter(indentDepth);
        this.slotCs = new HashMap<>();
        Integer thisSlot = loweredMethod.ir().isStatic() || loweredMethod.ir().getParameters().isEmpty()
                ? null
                : loweredMethod.slotOf().get(loweredMethod.ir().getParameters().get(0));
        this.slotIr = new HashMap<>();
        for (SlotDecl slot : loweredMethod.slots()) {
            slotIr.put(slot.slotId(), slot.computeType());
        }
        this.invokes = new InvokeRenderer(naming, reconciler, lambdaExpander, names,
                ownerInternalName, thisSlot, loweredMethod.slotOf(), slotIr);

        this.layout = new ExceptionLayout(loweredMethod);
        TypeMapper types = naming.typeMapper();
        for (SlotDecl slot : loweredMethod.slots()) {
            CsType type = types.computeType(slot.computeType());
            slotCs.put(slot.slotId(), type);
            w.line(type.csText() + " s" + slot.slotId() + " = " + type.defaultLiteral() + ";");
        }
        emitParameterCopies();
        if (layout.hasHandlers()) {
            new ExceptionMachineEmitter(w, layout, lowered).emitWrappedBody(this::emitBlocks);
        } else {
            emitBlocks();
            if (returnType.kind() != CsType.Kind.VOID) {
                w.line("throw new global::System.InvalidOperationException(\"j2cs: fell off method end\");");
            }
        }
        return w.toString();
    }

    private void emitBlocks() {
        for (IRBlock block : lowered.blockOrder()) {
            w.line("B" + block.getId() + ": ;");
            if (layout.hasHandlers() && lowered.originalBlocks().contains(block)) {
                w.line("__region = " + layout.regionOf(block) + ";");
            }
            terminated = false;
            for (IRInstruction instr : block.getInstructions()) {
                instr.accept(this);
            }
            if (!terminated) {
                emitFallthrough(block);
            }
        }
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
        // Coerce the value to the slot's declared type. This is a no-op when they match (the common
        // case), but a slot merging mixed reference types is widened (e.g. to Object), so a narrower
        // reference value stored into it needs an explicit upcast the C# compiler cannot infer.
        String coerced = reconciler.coerce(slotCs.get(slot), result.getType(), expr);
        w.line("s" + slot + " = " + coerced + ";");
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
        if (source instanceof SSAValue ssa && targetSlot != null) {
            // Coerce once, from the source's slot (C#-visible) type — not the value's SSA type: a slot
            // widened to Object holds a value whose SSA type is narrower, and using the SSA type would
            // skip the downcast/unbox the slot-typed expression needs. Emit directly rather than through
            // assign(), whose own coerce keys on the result's un-widened SSA type and would re-apply the
            // box/unbox (e.g. Integer.valueOf(Float.valueOf(x))).
            String expr = reconciler.coerce(slotCs.get(targetSlot), slotIrOf(ssa), names.ref(source));
            w.line("s" + targetSlot + " = " + expr + ";");
            return null;
        }
        assign(instr, names.ref(source));
        return null;
    }

    /** The source value's C#-visible type: its slot's declared type, which a widened slot makes wider than the SSA type. */
    private IRType slotIrOf(SSAValue value) {
        Integer slot = lowered.slotOf().get(value);
        IRType slotType = slot == null ? null : slotIr.get(slot);
        return slotType != null ? slotType : value.getType();
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
            w.line("return " + storageAdjusted(returnType, instr.getReturnValue()) + ";");
        }
        terminated = true;
        return null;
    }

    @Override
    public Void visitInvoke(InvokeInstruction instr) {
        assign(instr, invokes.render(instr));
        return null;
    }

    private String storageAdjusted(CsType storage, Value value) {
        return invokes.storageAdjusted(storage, value);
    }

    @Override
    public Void visitFieldAccess(FieldAccessInstruction instr) {
        String ref = invokes.fieldRef(instr);
        if (instr.isLoad()) {
            assign(instr, ref);
        } else {
            CsType storage = naming.typeMapper().storageType(instr.getDescriptor());
            w.line(ref + " = " + storageAdjusted(storage, instr.getValue()) + ";");
        }
        return null;
    }

    @Override
    public Void visitSimple(SimpleInstruction instr) {
        switch (instr.getOp()) {
            case GOTO -> {
                w.line("goto B" + instr.getTarget().getId() + ";");
                terminated = true;
            }
            case ARRAYLENGTH -> assign(instr, names.ref(instr.getOperand()) + ".Length");
            case ATHROW -> {
                // JThrow.of takes a Throwable; the thrown value's slot can be a wider reference type
                // (e.g. an Object slot merged across a phi), so coerce it to Throwable.
                CsType throwable = naming.typeMapper().storageType("Ljava/lang/Throwable;");
                w.line("throw global::java.lang.JThrow.of(" + storageAdjusted(throwable, instr.getOperand()) + ");");
                terminated = true;
            }
            // The goto-form body preserves the bytecode's monitorenter/monitorexit pairing (javac emits
            // the unlock on both the normal and exception paths), so the calls stay balanced; both mirror
            // synchronized's re-entrant, thread-affine locking via Monitor under JRuntime.
            case MONITORENTER ->
                    w.line("global::java.lang.JRuntime.MonitorEnter(" + names.ref(instr.getOperand()) + ");");
            case MONITOREXIT ->
                    w.line("global::java.lang.JRuntime.MonitorExit(" + names.ref(instr.getOperand()) + ");");
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
        String className = instr.getClassName();
        if (!naming.isAppClass(className) && !naming.isShimType(className)) {
            throw new UnsupportedBodyException("allocation of type not in input: " + className);
        }
        assign(instr, "new " + CsNamer.fqcn(className) + "(global::java.lang.RawNew.I)");
        return null;
    }

    @Override
    public Void visitNewArray(NewArrayInstruction instr) {
        CsType element = naming.typeMapper().storageType(instr.getElementType().getDescriptor());
        List<Value> dims = instr.getDimensions();
        if (dims.size() > 1) {
            SSAValue result = instr.getResult();
            Integer slot = lowered.slotOf().get(result);
            if (slot == null) {
                throw new UnsupportedBodyException("result v" + result.getId() + " has no slot");
            }
            emitMultiArrayLevel("s" + slot, element.csText().replace("[]", ""), dims, 0);
            return null;
        }
        String length = names.ref(dims.get(0));
        String csText = element.csText();
        int bracket = csText.indexOf('[');
        String expr = bracket < 0
                ? "new " + csText + "[" + length + "]"
                : "new " + csText.substring(0, bracket) + "[" + length + "]" + csText.substring(bracket);
        // A dead array allocation (unused result) is not a valid C# statement expression as a bare
        // `new T[n];`; discard it so the allocation — and any NegativeArraySizeException — is kept.
        if (instr.getResult() == null) {
            w.line("_ = " + expr + ";");
            return null;
        }
        assign(instr, expr);
        return null;
    }

    private void emitMultiArrayLevel(String target, String baseCs, List<Value> dims, int level) {
        int remaining = dims.size() - level;
        w.line(target + " = new " + baseCs + "[" + names.ref(dims.get(level)) + "]"
                + "[]".repeat(remaining - 1) + ";");
        if (remaining > 1) {
            String v = "__mv" + multiArrayCounter++;
            w.open("for (int " + v + " = 0; " + v + " < " + names.ref(dims.get(level)) + "; " + v + "++)");
            emitMultiArrayLevel(target + "[" + v + "]", baseCs, dims, level + 1);
            w.close();
        }
    }

    @Override
    public Void visitArrayAccess(ArrayAccessInstruction instr) {
        String array = names.ref(instr.getArray());
        String index = names.ref(instr.getIndex());
        if (instr.isLoad()) {
            assign(instr, array + "[" + index + "]");
        } else {
            CsType elementStorage = elementStorageOf(instr.getArray());
            w.line(array + "[" + index + "] = " + storageAdjusted(elementStorage, instr.getValue()) + ";");
        }
        return null;
    }

    private CsType elementStorageOf(Value arrayValue) {
        String descriptor = arrayValue.getType() == null ? null : arrayValue.getType().getDescriptor();
        if (descriptor == null || !descriptor.startsWith("[")) {
            throw new UnsupportedBodyException("array store on non-array-typed value: " + descriptor);
        }
        return naming.typeMapper().storageType(descriptor.substring(1));
    }

    @Override
    public Void visitTypeCheck(TypeCheckInstruction instr) {
        Value operandValue = instr.getOperand();
        String operand = names.ref(operandValue);
        CsType target = naming.typeMapper().computeType(instr.getTargetType());
        if (instr.isCast()) {
            IRType sourceType = operandValue instanceof SSAValue ssa ? ssa.getType() : null;
            assign(instr, reconciler.cast(target, sourceType, operand));
        } else {
            assign(instr, "(" + operand + " is " + target.csText() + ") ? 1 : 0");
        }
        return null;
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
