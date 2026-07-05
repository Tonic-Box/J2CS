package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.ir.InvokeInstruction;
import com.tonic.analysis.ssa.ir.InvokeType;
import com.tonic.analysis.ssa.type.IRType;
import com.tonic.analysis.ssa.value.SSAValue;
import com.tonic.analysis.ssa.value.Value;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.naming.Resolved;
import com.tonic.j2cs.shims.ShimTarget;
import com.tonic.j2cs.types.CsType;
import com.tonic.j2cs.types.TypeMapper;
import java.util.List;
import java.util.Map;

/**
 * Renders one invoke instruction as a C# expression: indy expansion (concat, lambda), two-phase
 * construction, super/static/virtual/interface dispatch through JVMS resolution, shim routing,
 * and argument/receiver coercion. Also owns the storage/receiver adjustment helpers the other
 * member-access emissions share.
 */
final class InvokeRenderer {

    private final NamingContext naming;
    private final TypeReconciler reconciler;
    private final LambdaExpander lambdaExpander;
    private final ValueNames names;
    private final ConcatEmitter concat;
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
            return reconciler.castTo(owner, names.ref(instr.getReceiver())) + "."
                    + MemberNamer.initMethodName(desc) + "(" + renderArguments(instr, desc) + ")";
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
        if ((owner.startsWith("java/") || owner.startsWith("javax/")) && !naming.isBootstrapped(owner)) {
            Resolved resolved = naming.resolveShim(owner, name, desc);
            if (!(resolved instanceof Resolved.ShimMethod shim)) {
                throw new UnsupportedBodyException(((Resolved.Unresolved) resolved).reason());
            }
            ShimTarget target = shim.target();
            String receiver = target.isStatic()
                    ? CsNamer.fqcn(owner)
                    : receiverAdjusted(shim.ownerInternal(), instr.getReceiver());
            return receiver + "." + target.csMemberName() + "(" + renderArguments(instr, desc) + ")";
        }
        return switch (instr.getInvokeType()) {
            case STATIC -> renderStatic(instr, owner, name, desc);
            case VIRTUAL -> renderVirtual(instr, owner, name, desc);
            case INTERFACE -> renderInterface(instr, owner, name, desc);
            default -> throw new UnsupportedBodyException("unexpected invoke kind: " + instr.getInvokeType());
        };
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
                || !thisSlot.equals(slotOf.get(ssa))) {
            throw new UnsupportedBodyException("invokespecial on non-this receiver: "
                    + instr.getOwner() + "." + instr.getName());
        }
    }

    String receiverAdjusted(String declaringInternal, Value receiver) {
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
