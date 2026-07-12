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
    private final Map<Integer, IRType> slotTypes;

    InvokeRenderer(NamingContext naming, TypeReconciler reconciler, LambdaExpander lambdaExpander,
                   ValueNames names, String currentClass, Integer thisSlot,
                   Map<SSAValue, Integer> slotOf, Map<Integer, IRType> slotTypes) {
        this.naming = naming;
        this.reconciler = reconciler;
        this.lambdaExpander = lambdaExpander;
        this.names = names;
        this.concat = new ConcatEmitter(names);
        this.calls = new CallRenderer(naming, reconciler);
        this.currentClass = currentClass;
        this.thisSlot = thisSlot;
        this.slotOf = slotOf;
        this.slotTypes = slotTypes;
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
            List<Value> a = instr.getMethodArguments();
            return "global::java.lang.System.arraycopy("
                    + arrayCopyArray(a.get(0)) + ", " + names.ref(a.get(1)) + ", "
                    + arrayCopyArray(a.get(2)) + ", " + names.ref(a.get(3)) + ", "
                    + names.ref(a.get(4)) + ")";
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
        // A slot widened to java.lang.Object (a mixed-type phi) makes the C# variable Object even
        // though this value's own SSA type is narrower. The receiver cast must be decided from the
        // storage type, so the call's declaring type is reached with an explicit narrowing cast.
        SSAValue sv = (SSAValue) receiver;
        if (slotOf.containsKey(sv)) {
            IRType slotType = slotTypes.get(slotOf.get(sv));
            if (slotType != null && "Ljava/lang/Object;".equals(slotType.getDescriptor())) {
                return new CallRenderer.Receiver(expr, "java/lang/Object");
            }
        }
        String descriptor = type.getDescriptor();
        if (descriptor.startsWith("[")) {
            // A method on an array receiver is a java.lang.Object method (clone/length handled
            // elsewhere); box the native array to reach the shim Object.
            return new CallRenderer.Receiver(
                    "global::java.lang.JRuntime.Box(" + expr + ", \"" + descriptor + "\")",
                    "java/lang/Object");
        }
        String internal = TypeMapper.unwrapReference(descriptor);
        if (internal == null) {
            throw new UnsupportedBodyException("non-reference receiver: " + descriptor);
        }
        return new CallRenderer.Receiver(expr, internal);
    }

    /**
     * An arraycopy array argument coerced to System.Array (the shim parameter type). A native array
     * passes through — every C# array derives from System.Array — while an array carried as a boxed
     * java.lang.Object is unwrapped to the underlying array.
     */
    private String arrayCopyArray(Value arg) {
        IRType type = arg instanceof SSAValue ssa ? ssa.getType() : null;
        String expr = names.ref(arg);
        String descriptor = type == null ? null : type.getDescriptor();
        if (descriptor != null && descriptor.startsWith("[")) {
            return expr;
        }
        return "global::java.lang.JRuntime.Unbox(" + expr + ")";
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
            sb.append(storageAdjusted(storage, args.get(i)));
        }
        return sb.toString();
    }


    String storageAdjusted(CsType storage, Value value) {
        IRType sourceType = value instanceof SSAValue ssa ? slotType(ssa) : null;
        return reconciler.coerce(storage, sourceType, names.ref(value));
    }

    /**
     * The value's C# variable type — its slot's declared type, which is what {@code names.ref}
     * renders. This is usually the SSA value's own type, but a slot merging mixed reference types
     * is widened (e.g. to Object), and coercion must see that wider type to insert the narrowing
     * cast a parameter/return position needs.
     */
    private IRType slotType(SSAValue ssa) {
        Integer slot = slotOf.get(ssa);
        IRType declared = slot == null ? null : slotTypes.get(slot);
        return declared != null ? declared : ssa.getType();
    }
}
