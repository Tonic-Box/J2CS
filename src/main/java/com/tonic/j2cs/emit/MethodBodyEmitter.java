package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.cfg.ExceptionHandler;
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
import com.tonic.analysis.ssa.value.Constant;
import com.tonic.analysis.ssa.type.IRType;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.analysis.ssa.value.StringConstant;
import com.tonic.analysis.ssa.value.Value;
import com.tonic.analysis.ssa.visitor.IRVisitor;
import com.tonic.j2cs.model.LoweredMethod;
import com.tonic.j2cs.model.SlotDecl;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.naming.Resolved;
import com.tonic.j2cs.naming.ResolvedField;
import com.tonic.j2cs.shims.ShimTarget;
import com.tonic.j2cs.types.CsType;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.parser.MethodEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private boolean terminated;
    private String currentClass;
    private Integer thisSlot;
    private ExceptionLayout layout;
    private int multiArrayCounter;

    public MethodBodyEmitter(NamingContext naming, SyntheticClasses synthetics) {
        this.naming = naming;
        this.lambdaExpander = new LambdaExpander(naming, synthetics);
        this.reconciler = new TypeReconciler(naming);
    }

    public String emit(String ownerInternalName, MethodEntry method, LoweredMethod loweredMethod, int indentDepth) {
        this.currentClass = ownerInternalName;
        this.lowered = loweredMethod;
        this.names = new ValueNames(loweredMethod.slotOf());
        this.returnType = naming.typeMapper().returnType(method.getDesc());
        this.w = new CsWriter(indentDepth);
        this.slotCs = new HashMap<>();
        this.thisSlot = loweredMethod.ir().isStatic() || loweredMethod.ir().getParameters().isEmpty()
                ? null
                : loweredMethod.slotOf().get(loweredMethod.ir().getParameters().get(0));

        this.layout = new ExceptionLayout(loweredMethod);
        TypeMapper types = naming.typeMapper();
        for (SlotDecl slot : loweredMethod.slots()) {
            CsType type = types.computeType(slot.computeType());
            slotCs.put(slot.slotId(), type);
            w.line(type.csText() + " s" + slot.slotId() + " = " + type.defaultLiteral() + ";");
        }
        emitParameterCopies();
        if (layout.hasHandlers()) {
            emitWrappedBody();
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

    private void emitWrappedBody() {
        w.line("int __state = 0;");
        w.line("int __region = -1;");
        w.line("global::java.lang.Throwable __p = null;");
        w.open("while (true)");
        w.open("try");
        w.open("switch (__state)");
        for (Map.Entry<IRBlock, Integer> entry : layout.stateOf().entrySet()) {
            w.line("case " + entry.getValue() + ": goto B" + entry.getKey().getId() + ";");
        }
        w.close();
        emitBlocks();
        w.close();
        w.open("catch (global::System.Exception __e)");
        w.line("if (__e is global::System.NotSupportedException) throw;");
        w.line("__p = global::java.lang.JRuntime.Normalize(__e);");
        w.open("switch (__region)");
        emitCatchArms();
        w.close();
        w.line("throw;");
        w.close();
        w.close();
    }

    private void emitCatchArms() {
        List<List<ExceptionHandler>> regions = layout.regions();
        for (int region = 0; region < regions.size(); region++) {
            w.line("case " + region + ":");
            for (ExceptionHandler handler : regions.get(region)) {
                Integer state = layout.stateOf().get(handler.getHandlerBlock());
                SSAValue excValue = lowered.ir().getHandlerExceptionValue(handler.getHandlerBlock());
                Integer excSlot = excValue == null ? null : lowered.slotOf().get(excValue);
                String assign = "";
                if (excSlot != null) {
                    String cast = handler.getCatchType() == null
                            ? "__p"
                            : "(" + CsNamer.fqcn(handler.getCatchType().getInternalName()) + ")__p";
                    assign = "s" + excSlot + " = " + cast + "; ";
                }
                String action = assign + "__state = " + state + "; continue;";
                if (handler.getCatchType() == null) {
                    w.line("    { " + action + " }");
                } else {
                    w.line("    if (__p is " + CsNamer.fqcn(handler.getCatchType().getInternalName())
                            + ") { " + action + " }");
                }
            }
            w.line("    break;");
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
            expr = reconciler.coerce(slotCs.get(targetSlot), ssa.getType(), expr);
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
            w.line("return " + storageAdjusted(returnType, instr.getReturnValue()) + ";");
        }
        terminated = true;
        return null;
    }

    @Override
    public Void visitInvoke(InvokeInstruction instr) {
        if (instr.getInvokeType() == InvokeType.DYNAMIC) {
            if (instr.getBootstrapInfo() != null && instr.getBootstrapInfo().isStringConcatFactory()) {
                assign(instr, renderConcat(instr));
                return null;
            }
            if (instr.getBootstrapInfo() != null && instr.getBootstrapInfo().isLambdaMetafactory()) {
                LambdaExpander.Expansion expansion = lambdaExpander.expand(instr, currentClass);
                assign(instr, "new " + expansion.fqcn() + "(" + renderArguments(instr, instr.getDescriptor()) + ")");
                return null;
            }
            throw new UnsupportedBodyException("invokedynamic not supported: "
                    + instr.getName() + instr.getDescriptor());
        }
        String owner = instr.getOwner();
        String name = instr.getName();
        String desc = instr.getDescriptor();
        if (name.equals("<init>")) {
            assign(instr, reconciler.castTo(owner, names.ref(instr.getReceiver())) + "."
                    + MemberNamer.initMethodName(desc) + "(" + renderArguments(instr, desc) + ")");
            return null;
        }
        if (instr.getInvokeType() == InvokeType.SPECIAL) {
            assign(instr, renderSpecial(instr, owner, name, desc));
            return null;
        }
        if (owner.equals("java/lang/System") && name.equals("arraycopy")
                && desc.equals("(Ljava/lang/Object;ILjava/lang/Object;II)V")) {
            assign(instr, "global::java.lang.System.arraycopy(" + rawArguments(instr) + ")");
            return null;
        }
        if (owner.startsWith("[") && name.equals("clone") && desc.equals("()Ljava/lang/Object;")
                && instr.getResult() != null && instr.getResult().getType() != null
                && instr.getResult().getType().isArray()) {
            CsType arrayType = naming.typeMapper().computeType(instr.getResult().getType());
            assign(instr, "(" + arrayType.csText() + ")(" + names.ref(instr.getReceiver()) + ".Clone())");
            return null;
        }
        if ((owner.startsWith("java/") || owner.startsWith("javax/")) && !naming.isBootstrapped(owner)) {
            Resolved resolved = naming.resolveShim(owner, name, desc);
            if (!(resolved instanceof Resolved.ShimMethod shim)) {
                throw new UnsupportedBodyException(((Resolved.Unresolved) resolved).reason());
            }
            ShimTarget target = shim.target();
            String receiver = target.isStatic()
                    ? CsNamer.fqcn(owner)
                    : receiverAdjusted(shim.ownerInternal(), instr.getReceiver());
            assign(instr, receiver + "." + target.csMemberName()
                    + "(" + renderArguments(instr, desc) + ")");
            return null;
        }
        String call = switch (instr.getInvokeType()) {
            case STATIC -> renderStatic(instr, owner, name, desc);
            case VIRTUAL -> renderVirtual(instr, owner, name, desc);
            case INTERFACE -> renderInterface(instr, owner, name, desc);
            default -> throw new UnsupportedBodyException("unexpected invoke kind: " + instr.getInvokeType());
        };
        assign(instr, call);
        return null;
    }

    private String renderSpecial(InvokeInstruction instr, String owner, String name, String desc) {
        if (owner.equals(currentClass)) {
            String member = naming.namerOf(currentClass).findMethodName(name, desc);
            if (member == null) {
                throw new UnsupportedBodyException("invokespecial target not declared: "
                        + owner + "." + name + desc);
            }
            return names.ref(instr.getReceiver()) + "." + member + "(" + renderArguments(instr, desc) + ")";
        }
        if (naming.hierarchy().isAppInterface(owner)) {
            throw new UnsupportedBodyException("interface super-call not supported: "
                    + owner + "." + name);
        }
        requireThisReceiver(instr);
        Resolved resolved = naming.resolveVirtual(owner, name, desc);
        if (resolved instanceof Resolved.AppMethod appMethod) {
            if (appMethod.viaInterface()) {
                throw new UnsupportedBodyException("super-call resolving to interface default: "
                        + owner + "." + name);
            }
            return "base." + appMethod.csName() + "(" + renderArguments(instr, desc) + ")";
        }
        if (resolved instanceof Resolved.ShimMethod shim) {
            return "base." + shim.target().csMemberName() + "(" + renderArguments(instr, desc) + ")";
        }
        throw new UnsupportedBodyException(((Resolved.Unresolved) resolved).reason());
    }

    private String renderStatic(InvokeInstruction instr, String owner, String name, String desc) {
        Resolved resolved = naming.resolveStatic(owner, name, desc);
        if (resolved instanceof Resolved.AppMethod appMethod) {
            return CsNamer.fqcn(appMethod.declaringInternal()) + "." + appMethod.csName()
                    + "(" + renderArguments(instr, desc) + ")";
        }
        throw new UnsupportedBodyException(((Resolved.Unresolved) resolved).reason());
    }

    private String renderVirtual(InvokeInstruction instr, String owner, String name, String desc) {
        Resolved resolved = naming.resolveVirtual(owner, name, desc);
        if (resolved instanceof Resolved.AppMethod appMethod) {
            String receiver = appMethod.viaInterface()
                    ? reconciler.castTo(appMethod.declaringInternal(), names.ref(instr.getReceiver()))
                    : receiverAdjusted(appMethod.declaringInternal(), instr.getReceiver());
            return receiver + "." + appMethod.csName() + "(" + renderArguments(instr, desc) + ")";
        }
        if (resolved instanceof Resolved.ShimMethod shim) {
            return receiverAdjusted(shim.ownerInternal(), instr.getReceiver()) + "."
                    + shim.target().csMemberName() + "(" + renderArguments(instr, desc) + ")";
        }
        throw new UnsupportedBodyException(((Resolved.Unresolved) resolved).reason());
    }

    private String renderInterface(InvokeInstruction instr, String owner, String name, String desc) {
        if (!naming.hierarchy().isAppInterface(owner)) {
            if (naming.isAppClass(owner)) {
                return renderVirtual(instr, owner, name, desc);
            }
            throw new UnsupportedBodyException("call target not in input: " + owner + "." + name + desc);
        }
        Resolved resolved = naming.resolveVirtual(owner, name, desc);
        if (resolved instanceof Resolved.AppMethod appMethod) {
            String castTo = appMethod.viaInterface() ? owner : appMethod.declaringInternal();
            return reconciler.castTo(castTo, names.ref(instr.getReceiver())) + "."
                    + appMethod.csName() + "(" + renderArguments(instr, desc) + ")";
        }
        if (resolved instanceof Resolved.ShimMethod shim) {
            return reconciler.castTo("java/lang/Object", names.ref(instr.getReceiver())) + "."
                    + shim.target().csMemberName() + "(" + renderArguments(instr, desc) + ")";
        }
        throw new UnsupportedBodyException(((Resolved.Unresolved) resolved).reason());
    }

    private void requireThisReceiver(InvokeInstruction instr) {
        Value receiver = instr.getReceiver();
        if (!(receiver instanceof SSAValue ssa) || thisSlot == null
                || !thisSlot.equals(lowered.slotOf().get(ssa))) {
            throw new UnsupportedBodyException("invokespecial on non-this receiver: "
                    + instr.getOwner() + "." + instr.getName());
        }
    }

    private String receiverAdjusted(String declaringInternal, Value receiver) {
        IRType type = receiver instanceof SSAValue ssa ? ssa.getType() : null;
        return reconciler.receiver(declaringInternal, type, names.ref(receiver));
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
            requireNoArrayAsObject(paramDescs.get(i), args.get(i));
            CsType storage = naming.typeMapper().storageType(paramDescs.get(i));
            sb.append(storageAdjusted(storage, args.get(i)));
        }
        return sb.toString();
    }

    private String rawArguments(InvokeInstruction instr) {
        StringBuilder sb = new StringBuilder();
        for (Value arg : instr.getMethodArguments()) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(names.ref(arg));
        }
        return sb.toString();
    }

    private static void requireNoArrayAsObject(String paramDesc, Value arg) {
        if (paramDesc.startsWith("L") && arg.getType() != null && arg.getType().isArray()) {
            throw new UnsupportedBodyException(
                    "array passed as object reference not supported (arrays are native C# arrays)");
        }
    }

    private String storageAdjusted(CsType storage, Value value) {
        IRType sourceType = value instanceof SSAValue ssa ? ssa.getType() : null;
        return reconciler.coerce(storage, sourceType, names.ref(value));
    }

    private String renderConcat(InvokeInstruction instr) {
        List<String> paramDescs = TypeMapper.splitParams(instr.getDescriptor());
        List<Value> args = instr.getMethodArguments();
        if (args.size() != paramDescs.size()) {
            throw new UnsupportedBodyException("concat argument count mismatch: " + instr.getDescriptor());
        }
        List<ConcatRecipeParser.Part> parts = concatParts(instr, args.size());
        StringBuilder sb = new StringBuilder("global::java.lang.String.Wrap(global::System.String.Concat(new string[] { ");
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            ConcatRecipeParser.Part part = parts.get(i);
            if (part instanceof ConcatRecipeParser.Part.Literal literal) {
                sb.append(CsStrings.quote(literal.text()));
            } else if (part instanceof ConcatRecipeParser.Part.Arg arg) {
                if (arg.index() >= args.size()) {
                    throw new UnsupportedBodyException("concat recipe references argument " + arg.index()
                            + " but only " + args.size() + " are supplied");
                }
                sb.append(concatConversion(paramDescs.get(arg.index()), args.get(arg.index())));
            }
        }
        sb.append(" }))");
        return sb.toString();
    }

    private List<ConcatRecipeParser.Part> concatParts(InvokeInstruction instr, int argCount) {
        if (instr.getName().equals("makeConcat")) {
            List<ConcatRecipeParser.Part> parts = new ArrayList<>();
            for (int i = 0; i < argCount; i++) {
                parts.add(new ConcatRecipeParser.Part.Arg(i));
            }
            return parts;
        }
        List<Constant> bootstrapArgs = instr.getBootstrapInfo().getBootstrapArguments();
        if (bootstrapArgs.isEmpty() || !(bootstrapArgs.get(0) instanceof StringConstant recipe)) {
            throw new UnsupportedBodyException("concat bootstrap has no recipe string");
        }
        List<String> constants = new ArrayList<>();
        for (int i = 1; i < bootstrapArgs.size(); i++) {
            if (!(bootstrapArgs.get(i) instanceof StringConstant s)) {
                throw new UnsupportedBodyException("non-string concat bootstrap constant: "
                        + bootstrapArgs.get(i).getClass().getSimpleName());
            }
            constants.add(s.getValue());
        }
        try {
            return ConcatRecipeParser.parse(recipe.getValue(), constants);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedBodyException("unparseable concat recipe: " + e.getMessage());
        }
    }

    private String concatConversion(String desc, Value arg) {
        String jr = "global::java.lang.JRuntime";
        String expr = names.ref(arg);
        return switch (desc.charAt(0)) {
            case 'Z' -> jr + ".StrZ(" + expr + ")";
            case 'C' -> jr + ".Str((char)(" + expr + "))";
            case 'B', 'S', 'I' -> jr + ".Str(" + expr + ")";
            case 'J', 'F', 'D' -> jr + ".Str(" + expr + ")";
            case 'L' -> {
                requireNoArrayAsObject(desc, arg);
                yield jr + ".Str(" + expr + ")";
            }
            default -> throw new UnsupportedBodyException("array in string concat not supported");
        };
    }

    @Override
    public Void visitFieldAccess(FieldAccessInstruction instr) {
        String owner = instr.getOwner();
        String name = instr.getName();
        String desc = instr.getDescriptor();
        if ((owner.startsWith("java/") || owner.startsWith("javax/")) && !naming.isBootstrapped(owner)) {
            Optional<ShimTarget> target = naming.shimField(owner, name, desc);
            if (target.isEmpty()) {
                throw new UnsupportedBodyException("shim field not implemented: " + owner + "." + name);
            }
            String shimRef = target.get().isStatic()
                    ? CsNamer.fqcn(owner)
                    : receiverAdjusted(owner, instr.getObjectRef());
            if (instr.isLoad()) {
                assign(instr, shimRef + "." + target.get().csMemberName());
            } else {
                CsType shimStorage = naming.typeMapper().storageType(desc);
                w.line(shimRef + "." + target.get().csMemberName() + " = "
                        + storageAdjusted(shimStorage, instr.getValue()) + ";");
            }
            return null;
        }
        ResolvedField resolved = naming.resolveField(owner, name, desc);
        if (!(resolved instanceof ResolvedField.AppField field)) {
            throw new UnsupportedBodyException(((ResolvedField.Unresolved) resolved).reason());
        }
        String ref = instr.isStatic()
                ? CsNamer.fqcn(field.declaringInternal())
                : receiverAdjusted(field.declaringInternal(), instr.getObjectRef());
        if (instr.isLoad()) {
            assign(instr, ref + "." + field.csName());
        } else {
            CsType storage = naming.typeMapper().storageType(desc);
            w.line(ref + "." + field.csName() + " = " + storageAdjusted(storage, instr.getValue()) + ";");
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
                w.line("throw global::java.lang.JThrow.of(" + names.ref(instr.getOperand()) + ");");
                terminated = true;
            }
            case MONITORENTER, MONITOREXIT ->
                    throw new UnsupportedBodyException("monitors not supported");
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
