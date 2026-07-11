package com.tonic.j2cs.emit;

import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.MemberNamer;
import com.tonic.j2cs.naming.NamingContext;
import com.tonic.j2cs.naming.Resolved;
import com.tonic.j2cs.naming.ResolvedField;
import com.tonic.j2cs.shims.ShimRegistry;
import com.tonic.j2cs.shims.ShimTarget;
import java.util.Optional;

/**
 * Member-access rendering shared by the IR and AST body emitters: shim routing, JVMS
 * resolution, and receiver upcasts. Callers supply already-rendered receiver/argument text.
 */
public final class CallRenderer {

    /** A rendered receiver and its static internal name; null name means unknown (always cast). */
    public record Receiver(String expr, String internalName) {
    }

    private final NamingContext naming;
    private final TypeReconciler reconciler;

    public CallRenderer(NamingContext naming, TypeReconciler reconciler) {
        this.naming = naming;
        this.reconciler = reconciler;
    }

    public boolean isShimOwner(String owner) {
        if (naming.isBootstrapped(owner)) {
            return false;
        }
        // java/* and javax/* route to shims by prefix (an unimplemented member then degrades to a
        // clear stub rather than an app-resolution failure); registered shim types outside those
        // roots (e.g. sun/misc/Unsafe) are recognized explicitly.
        return owner.startsWith("java/") || owner.startsWith("javax/") || naming.isShimType(owner);
    }

    public String initCall(String owner, String desc, String receiverExpr, String args) {
        return reconciler.castTo(owner, receiverExpr) + "."
                + MemberNamer.initMethodName(desc) + "(" + args + ")";
    }

    public String shimCall(String owner, String name, String desc, Receiver receiver, String args) {
        Resolved resolved = naming.resolveShim(owner, name, desc);
        if (!(resolved instanceof Resolved.ShimMethod shim)) {
            throw new UnsupportedBodyException(((Resolved.Unresolved) resolved).reason());
        }
        ShimTarget target = shim.target();
        String ref;
        if (target.isStatic()) {
            ref = CsNamer.fqcn(owner);
        } else if (ShimRegistry.isDefaultInterfaceMethod(shim.ownerInternal(), name, desc)) {
            ref = reconciler.castTo(shim.ownerInternal(), receiver.expr());
        } else {
            ref = adjusted(shim.ownerInternal(), receiver);
        }
        return ref + "." + target.csMemberName() + "(" + args + ")";
    }

    public String staticCall(String owner, String name, String desc, String args) {
        Resolved resolved = naming.resolveStatic(owner, name, desc);
        if (resolved instanceof Resolved.AppMethod appMethod) {
            return CsNamer.fqcn(appMethod.declaringInternal()) + "." + appMethod.csName()
                    + "(" + args + ")";
        }
        throw new UnsupportedBodyException(((Resolved.Unresolved) resolved).reason());
    }

    public String virtualCall(String owner, String name, String desc, Receiver receiver, String args) {
        Resolved resolved = naming.resolveVirtual(owner, name, desc);
        if (resolved instanceof Resolved.AppMethod appMethod) {
            String ref = appMethod.viaInterface()
                    ? reconciler.castTo(appMethod.declaringInternal(), receiver.expr())
                    : adjusted(appMethod.declaringInternal(), receiver);
            return ref + "." + appMethod.csName() + "(" + args + ")";
        }
        if (resolved instanceof Resolved.ShimMethod shim) {
            return adjusted(shim.ownerInternal(), receiver) + "."
                    + shim.target().csMemberName() + "(" + args + ")";
        }
        throw new UnsupportedBodyException(((Resolved.Unresolved) resolved).reason());
    }

    public String interfaceCall(String owner, String name, String desc, Receiver receiver, String args) {
        if (!naming.hierarchy().isAppInterface(owner)) {
            if (naming.isAppClass(owner)) {
                return virtualCall(owner, name, desc, receiver, args);
            }
            throw new UnsupportedBodyException("call target not in input: " + owner + "." + name + desc);
        }
        Resolved resolved = naming.resolveVirtual(owner, name, desc);
        if (resolved instanceof Resolved.AppMethod appMethod) {
            String castTo = appMethod.viaInterface() ? owner : appMethod.declaringInternal();
            return reconciler.castTo(castTo, receiver.expr()) + "."
                    + appMethod.csName() + "(" + args + ")";
        }
        if (resolved instanceof Resolved.ShimMethod shim) {
            // The method resolves on a shim supertype: the Object trio (owner java/lang/Object) or a
            // method inherited from a shim super-interface such as java.util.function.Function. Cast
            // to that declaring type so the member is in scope.
            return reconciler.castTo(shim.ownerInternal(), receiver.expr()) + "."
                    + shim.target().csMemberName() + "(" + args + ")";
        }
        throw new UnsupportedBodyException(((Resolved.Unresolved) resolved).reason());
    }

    /** Super-call dispatch on the C# base reference. */
    public String superCall(String owner, String name, String desc, String args) {
        if (naming.isShimType(owner)
                && naming.resolveShim(owner, name, desc) instanceof Resolved.ShimMethod shimSuper) {
            return "base." + shimSuper.target().csMemberName() + "(" + args + ")";
        }
        Resolved resolved = naming.resolveVirtual(owner, name, desc);
        if (resolved instanceof Resolved.AppMethod appMethod) {
            if (appMethod.viaInterface()) {
                throw new UnsupportedBodyException("super-call resolving to interface default: "
                        + owner + "." + name);
            }
            return "base." + appMethod.csName() + "(" + args + ")";
        }
        if (resolved instanceof Resolved.ShimMethod shim) {
            return "base." + shim.target().csMemberName() + "(" + args + ")";
        }
        throw new UnsupportedBodyException(((Resolved.Unresolved) resolved).reason());
    }

    /** Field reference text (no assignment/load distinction; caller appends context). */
    public String fieldRef(String owner, String name, String desc, boolean isStatic, Receiver receiver) {
        if (isShimOwner(owner)) {
            Optional<ShimTarget> target = naming.shimField(owner, name, desc);
            if (target.isEmpty()) {
                throw new UnsupportedBodyException("shim field not implemented: " + owner + "." + name);
            }
            String ref = target.get().isStatic() ? CsNamer.fqcn(owner) : adjusted(owner, receiver);
            return ref + "." + target.get().csMemberName();
        }
        ResolvedField resolved = naming.resolveField(owner, name, desc);
        if (!(resolved instanceof ResolvedField.AppField field)) {
            throw new UnsupportedBodyException(((ResolvedField.Unresolved) resolved).reason());
        }
        String ref = isStatic
                ? CsNamer.fqcn(field.declaringInternal())
                : adjusted(field.declaringInternal(), receiver);
        return ref + "." + field.csName();
    }

    private String adjusted(String declaring, Receiver receiver) {
        return reconciler.receiver(declaring, receiver.internalName(), receiver.expr());
    }
}
