package com.tonic.j2cs.emit;

import com.tonic.analysis.ssa.ir.BootstrapMethodInfo;
import com.tonic.analysis.ssa.ir.InvokeInstruction;
import com.tonic.analysis.ssa.value.Constant;
import com.tonic.analysis.ssa.value.MethodHandleConstant;
import com.tonic.analysis.ssa.value.MethodTypeConstant;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.naming.Resolved;
import com.tonic.j2cs.types.TypeMapper;
import com.tonic.util.Modifiers;
import java.util.ArrayList;
import java.util.List;

/**
 * Expands a LambdaMetafactory invokedynamic call site into a synthetic C# class implementing the
 * functional interface, forwarding the single abstract method to the lambda's implementation
 * method. The call site becomes a `new` of that class capturing the indy arguments.
 */
public final class LambdaExpander {

    public record Expansion(String fqcn) {
    }

    private final NamingContext naming;
    private final SyntheticClasses synthetics;

    public LambdaExpander(NamingContext naming, SyntheticClasses synthetics) {
        this.naming = naming;
        this.synthetics = synthetics;
    }

    public Expansion expand(InvokeInstruction instr, String enclosingInternalName) {
        BootstrapMethodInfo bsi = instr.getBootstrapInfo();
        if (!bsi.getBootstrapMethod().getName().equals("metafactory")) {
            throw new UnsupportedBodyException("altMetafactory lambda not supported");
        }
        List<Constant> bootstrapArgs = bsi.getBootstrapArguments();
        if (bootstrapArgs.size() < 3
                || !(bootstrapArgs.get(0) instanceof MethodTypeConstant samType)
                || !(bootstrapArgs.get(1) instanceof MethodHandleConstant impl)
                || !(bootstrapArgs.get(2) instanceof MethodTypeConstant)) {
            throw new UnsupportedBodyException("unexpected lambda bootstrap arguments");
        }
        String indyDesc = instr.getDescriptor();
        String ifaceInternal = returnReference(indyDesc);
        if (ifaceInternal == null || !naming.hierarchy().isAppInterface(ifaceInternal)) {
            throw new UnsupportedBodyException("functional interface not in input: "
                    + (ifaceInternal == null ? indyDesc : ifaceInternal));
        }
        String samDesc = samType.getDescriptor();
        Resolved samResolved = naming.resolveVirtual(ifaceInternal, instr.getName(), samDesc);
        if (!(samResolved instanceof Resolved.AppMethod samMethod)) {
            throw new UnsupportedBodyException("abstract method not found on functional interface: "
                    + ifaceInternal + "." + instr.getName() + samDesc);
        }

        int cpIndex = instr.getOriginalCpIndex();
        String className = synthetics.claimClassName(enclosingInternalName, cpIndex);
        if (!synthetics.isRegistered(enclosingInternalName, cpIndex)) {
            String source = buildSynthetic(className, ifaceInternal, samMethod.csName(), samDesc,
                    indyDesc, impl);
            synthetics.register(enclosingInternalName, cpIndex, className, source);
        }
        return new Expansion(synthetics.fqcnOf(className));
    }

    private String buildSynthetic(String className, String ifaceInternal, String samCsName,
                                  String samDesc, String indyDesc, MethodHandleConstant impl) {
        TypeMapper types = naming.typeMapper();
        List<String> capturedDescs = TypeMapper.splitParams(indyDesc);
        List<String> samParamDescs = TypeMapper.splitParams(samDesc);
        String samRetDesc = returnDescriptor(samDesc);

        List<String> effectiveImplParams = new ArrayList<>();
        boolean implIsInstance = impl.getReferenceKind() != MethodHandleConstant.REF_invokeStatic
                && impl.getReferenceKind() != MethodHandleConstant.REF_newInvokeSpecial;
        if (implIsInstance) {
            effectiveImplParams.add("L" + impl.getOwner() + ";");
        }
        effectiveImplParams.addAll(TypeMapper.splitParams(impl.getDescriptor()));

        int capturedCount = capturedDescs.size();
        if (capturedCount + samParamDescs.size() != effectiveImplParams.size()) {
            throw new UnsupportedBodyException("lambda arity mismatch for " + impl.getName());
        }
        for (int i = 0; i < effectiveImplParams.size(); i++) {
            String source = i < capturedCount ? capturedDescs.get(i) : samParamDescs.get(i - capturedCount);
            requireNoBoxing(source, effectiveImplParams.get(i), impl.getName());
        }
        String implRetDesc = impl.getReferenceKind() == MethodHandleConstant.REF_newInvokeSpecial
                ? "L" + impl.getOwner() + ";"
                : returnDescriptor(impl.getDescriptor());
        if (!samRetDesc.equals("V")) {
            requireNoBoxing(implRetDesc, samRetDesc, impl.getName());
        }

        CsWriter w = new CsWriter();
        w.open("namespace j2cs.synthetic");
        w.open("internal class " + className + " : global::java.lang.Object, " + CsNamer.fqcn(ifaceInternal));
        for (int i = 0; i < capturedCount; i++) {
            w.line("private readonly " + types.storageType(capturedDescs.get(i)).csText() + " c" + i + ";");
        }
        w.line();
        StringBuilder ctorParams = new StringBuilder();
        for (int i = 0; i < capturedCount; i++) {
            if (i > 0) {
                ctorParams.append(", ");
            }
            ctorParams.append(types.storageType(capturedDescs.get(i)).csText()).append(" a").append(i);
        }
        w.open("internal " + className + "(" + ctorParams + ")");
        for (int i = 0; i < capturedCount; i++) {
            w.line("c" + i + " = a" + i + ";");
        }
        w.close();
        w.line();

        StringBuilder samParams = new StringBuilder();
        for (int i = 0; i < samParamDescs.size(); i++) {
            if (i > 0) {
                samParams.append(", ");
            }
            samParams.append(types.storageType(samParamDescs.get(i)).csText()).append(" p").append(i);
        }
        w.open("public " + types.returnType(samDesc).csText() + " " + samCsName + "(" + samParams + ")");
        emitForward(w, impl, effectiveImplParams, capturedDescs, samParamDescs, implRetDesc, samRetDesc);
        w.close();
        w.close();
        w.close();
        return w.toString();
    }

    private void emitForward(CsWriter w, MethodHandleConstant impl, List<String> effectiveImplParams,
                             List<String> capturedDescs, List<String> samParamDescs,
                             String implRetDesc, String samRetDesc) {
        TypeMapper types = naming.typeMapper();
        int capturedCount = capturedDescs.size();
        List<String> argExprs = new ArrayList<>();
        for (int i = 0; i < effectiveImplParams.size(); i++) {
            String raw = i < capturedCount ? "c" + i : "p" + (i - capturedCount);
            String sourceDesc = i < capturedCount
                    ? capturedDescs.get(i)
                    : samParamDescs.get(i - capturedCount);
            argExprs.add(adapt(raw, types.storageType(sourceDesc).csText(),
                    types.storageType(effectiveImplParams.get(i)).csText()));
        }

        int kind = impl.getReferenceKind();
        String call;
        if (kind == MethodHandleConstant.REF_invokeStatic) {
            call = staticCall(impl, argExprs);
        } else if (kind == MethodHandleConstant.REF_invokeSpecial) {
            call = specialCall(impl, argExprs);
        } else if (kind == MethodHandleConstant.REF_invokeVirtual
                || kind == MethodHandleConstant.REF_invokeInterface) {
            call = virtualCall(impl, argExprs);
        } else {
            throw new UnsupportedBodyException("lambda implementation kind not supported: " + kind);
        }

        if (samRetDesc.equals("V")) {
            w.line(call + ";");
        } else {
            w.line("return " + adapt(call, types.storageType(implRetDesc).csText(),
                    types.storageType(samRetDesc).csText()) + ";");
        }
    }

    private String staticCall(MethodHandleConstant impl, List<String> argExprs) {
        String owner = impl.getOwner();
        if (owner.startsWith("java/") || owner.startsWith("javax/")) {
            return CsNamer.fqcn(owner) + "." + shimStatic(impl) + "(" + join(argExprs) + ")";
        }
        Resolved resolved = naming.resolveStatic(owner, impl.getName(), impl.getDescriptor());
        if (!(resolved instanceof Resolved.AppMethod method)) {
            throw new UnsupportedBodyException("lambda static target not in input: "
                    + owner + "." + impl.getName());
        }
        return CsNamer.fqcn(method.declaringInternal()) + "." + method.csName() + "(" + join(argExprs) + ")";
    }

    private String specialCall(MethodHandleConstant impl, List<String> argExprs) {
        String owner = impl.getOwner();
        if (!naming.isAppClass(owner)) {
            throw new UnsupportedBodyException("invokespecial method reference not supported: "
                    + owner + "." + impl.getName());
        }
        MemberNamer namer = naming.namerOf(owner);
        Integer access = namer.methodAccessOf(impl.getName(), impl.getDescriptor());
        if (access == null || !Modifiers.isPrivate(access)) {
            throw new UnsupportedBodyException("invokespecial method reference not supported: "
                    + owner + "." + impl.getName());
        }
        String receiver = "((" + CsNamer.fqcn(owner) + ")" + argExprs.get(0) + ")";
        List<String> rest = argExprs.subList(1, argExprs.size());
        return receiver + "." + namer.methodName(impl.getName(), impl.getDescriptor())
                + "(" + join(rest) + ")";
    }

    private String virtualCall(MethodHandleConstant impl, List<String> argExprs) {
        String owner = impl.getOwner();
        String receiver = argExprs.get(0);
        List<String> rest = argExprs.subList(1, argExprs.size());
        if (owner.startsWith("java/") || owner.startsWith("javax/")) {
            com.tonic.j2cs.shims.ShimRegistry.WalkResult walked =
                    com.tonic.j2cs.shims.ShimRegistry.resolveMethodWalking(owner, impl.getName(), impl.getDescriptor())
                            .orElseThrow(() -> new UnsupportedBodyException("lambda shim target not implemented: "
                                    + owner + "." + impl.getName()));
            return "((" + CsNamer.fqcn(walked.declaringInternal()) + ")" + receiver + ")."
                    + walked.target().csMemberName() + "(" + join(rest) + ")";
        }
        Resolved resolved = naming.resolveVirtual(owner, impl.getName(), impl.getDescriptor());
        if (resolved instanceof Resolved.AppMethod method) {
            return "((" + CsNamer.fqcn(method.declaringInternal()) + ")" + receiver + ")."
                    + method.csName() + "(" + join(rest) + ")";
        }
        if (resolved instanceof Resolved.ShimMethod shim) {
            return "((" + CsNamer.fqcn(shim.ownerInternal()) + ")" + receiver + ")."
                    + shim.target().csMemberName() + "(" + join(rest) + ")";
        }
        throw new UnsupportedBodyException("lambda virtual target not in input: " + owner + "." + impl.getName());
    }

    private String shimStatic(MethodHandleConstant impl) {
        return com.tonic.j2cs.shims.ShimRegistry.method(impl.getOwner(), impl.getName(), impl.getDescriptor())
                .filter(com.tonic.j2cs.shims.ShimTarget::isStatic)
                .map(com.tonic.j2cs.shims.ShimTarget::csMemberName)
                .orElseThrow(() -> new UnsupportedBodyException("lambda shim target not implemented: "
                        + impl.getOwner() + "." + impl.getName()));
    }

    private static String adapt(String expr, String fromCs, String toCs) {
        return fromCs.equals(toCs) ? expr : "(" + toCs + ")(" + expr + ")";
    }

    private static void requireNoBoxing(String sourceDesc, String targetDesc, String implName) {
        boolean sourcePrimitive = isPrimitive(sourceDesc);
        boolean targetPrimitive = isPrimitive(targetDesc);
        if (sourcePrimitive != targetPrimitive) {
            throw new UnsupportedBodyException("boxing adaptation in lambda not supported: " + implName);
        }
    }

    private static boolean isPrimitive(String descriptor) {
        char c = descriptor.charAt(0);
        return c != 'L' && c != '[';
    }

    private static String returnDescriptor(String methodDescriptor) {
        return methodDescriptor.substring(methodDescriptor.indexOf(')') + 1);
    }

    private static String returnReference(String methodDescriptor) {
        String ret = returnDescriptor(methodDescriptor);
        if (ret.startsWith("L") && ret.endsWith(";")) {
            return ret.substring(1, ret.length() - 1);
        }
        return null;
    }

    private static String join(List<String> parts) {
        return String.join(", ", parts);
    }
}
