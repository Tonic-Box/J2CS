package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.ir.FieldAccessInstruction;
import com.tonic.analysis.ssa.ir.InvokeInstruction;
import com.tonic.analysis.ssa.ir.InvokeType;
import com.tonic.analysis.ssa.type.IRType;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.analysis.ssa.value.Value;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.types.CsType;
import com.tonic.j2cs.types.TypeMapper;
import java.util.List;
import java.util.Map;

/**
 * Renders invoke and field-access instructions as C# expressions: indy expansion (concat,
 * lambda), two-phase construction, arraycopy/array-clone special cases, and dispatch through
 * CallRenderer. Also owns the storage/receiver adjustment helpers the other member-access
 * emissions share.
 */
final class InvokeRenderer {

    private final NamingContext naming;
    private final TypeReconciler reconciler;
    private final LambdaExpander lambdaExpander;
    private final ValueNames names;
    private final ConcatEmitter concat;
    private final CallRenderer calls;
    private final String currentClass;
    private final Integer thisSlot;
    private final Map<SSAValue, Integer> slotOf;

    InvokeRenderer(NamingContext naming, TypeReconciler reconciler, LambdaExpander lambdaExpander,
                   ValueNames names, String currentClass, Integer thisSlot,
                   Map<SSAValue, Integer> slotOf) {
        this.naming = naming;
        this.reconciler = reconciler;
        this.lambdaExpander = lambdaExpander;
        this.names = names;
        this.concat = new ConcatEmitter(names);
        this.calls = new CallRenderer(naming, reconciler);
        this.currentClass = currentClass;
        this.thisSlot = thisSlot;
        this.slotOf = slotOf;
    }

    String render(InvokeInstruction instr) {
        if (instr.getInvokeType() == InvokeType.DYNAMIC) {
            if (instr.getBootstrapInfo() != null && instr.getBootstrapInfo().isStringConcatFactory()) {
                return concat.render(instr);
            }
            if (instr.getBootstrapInfo() != null && instr.getBootstrapInfo().isLambdaMetafactory()) {
                LambdaExpander.Expansion expansion = lambdaExpander.expand(instr, currentClass);
                return "new " + expansion.fqcn() + "(" + renderArguments(instr, instr.getDescriptor()) + ")";
            }
            throw new UnsupportedBodyException("invokedynamic not supported: "
                    + instr.getName() + instr.getDescriptor());
        }
        String owner = instr.getOwner();
        String name = instr.getName();
        String desc = instr.getDescriptor();
        if (name.equals("<init>")) {
            return calls.initCall(owner, desc, names.ref(instr.getReceiver()), renderArguments(instr, desc));
        }
        if (instr.getInvokeType() == InvokeType.SPECIAL) {
            return renderSpecial(instr, owner, name, desc);
        }
        if (owner.equals("java/lang/System") && name.equals("arraycopy")
                && desc.equals("(Ljava/lang/Object;ILjava/lang/Object;II)V")) {
            return "global::java.lang.System.arraycopy(" + rawArguments(instr) + ")";
        }
        if (owner.startsWith("[") && name.equals("clone") && desc.equals("()Ljava/lang/Object;")
                && instr.getResult() != null && instr.getResult().getType() != null
                && instr.getResult().getType().isArray()) {
            CsType arrayType = naming.typeMapper().computeType(instr.getResult().getType());
            return "(" + arrayType.csText() + ")(" + names.ref(instr.getReceiver()) + ".Clone())";
        }
        if (calls.isShimOwner(owner)) {
            CallRenderer.Receiver receiver = instr.getReceiver() == null
                    ? new CallRenderer.Receiver("", null)
                    : receiverOf(instr.getReceiver());
            return calls.shimCall(owner, name, desc, receiver, renderArguments(instr, desc));
        }
        return switch (instr.getInvokeType()) {
            case STATIC -> calls.staticCall(owner, name, desc, renderArguments(instr, desc));
            case VIRTUAL -> calls.virtualCall(owner, name, desc, receiverOf(instr.getReceiver()),
                    renderArguments(instr, desc));
            case INTERFACE -> calls.interfaceCall(owner, name, desc, receiverOf(instr.getReceiver()),
                    renderArguments(instr, desc));
            default -> throw new UnsupportedBodyException("unexpected invoke kind: " + instr.getInvokeType());
        };
    }

    String fieldRef(FieldAccessInstruction instr) {
        Value objectRef = instr.getObjectRef();
        CallRenderer.Receiver receiver = objectRef == null
                ? new CallRenderer.Receiver("", null)
                : receiverOf(objectRef);
        return calls.fieldRef(instr.getOwner(), instr.getName(), instr.getDescriptor(),
                instr.isStatic(), receiver);
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
        return calls.superCall(owner, name, desc, renderArguments(instr, desc));
    }

    private void requireThisReceiver(InvokeInstruction instr) {
        Value receiver = instr.getReceiver();
        if (!(receiver instanceof SSAValue ssa) || thisSlot == null
                || !thisSlot.equals(slotOf.get(ssa))) {
            throw new UnsupportedBodyException("invokespecial on non-this receiver: "
                    + instr.getOwner() + "." + instr.getName());
        }
    }

    private CallRenderer.Receiver receiverOf(Value receiver) {
        String expr = names.ref(receiver);
        IRType type = receiver instanceof SSAValue ssa ? ssa.getType() : null;
        if (type == null) {
            return new CallRenderer.Receiver(expr, null);
        }
        String descriptor = type.getDescriptor();
        if (descriptor.startsWith("[")) {
            throw new UnsupportedBodyException(
                    "array used as method receiver not supported (arrays are native C# arrays)");
        }
        String internal = TypeMapper.unwrapReference(descriptor);
        if (internal == null) {
            throw new UnsupportedBodyException("non-reference receiver: " + descriptor);
        }
        return new CallRenderer.Receiver(expr, internal);
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

    static void requireNoArrayAsObject(String paramDesc, Value arg) {
        if (paramDesc.startsWith("L") && arg.getType() != null && arg.getType().isArray()) {
            throw new UnsupportedBodyException(
                    "array passed as object reference not supported (arrays are native C# arrays)");
        }
    }

    String storageAdjusted(CsType storage, Value value) {
        IRType sourceType = value instanceof SSAValue ssa ? ssa.getType() : null;
        return reconciler.coerce(storage, sourceType, names.ref(value));
    }
}
