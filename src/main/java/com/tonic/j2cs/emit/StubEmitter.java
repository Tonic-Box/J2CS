package com.tonic.j2cs.emit;

import com.tonic.j2cs.naming.CsNamer;

/**
 * Emits an empty placeholder class for a referenced type that is neither in the input nor in
 * the shim, so signatures and casts still compile. Any behavioral use fails per-method.
 */
public final class StubEmitter {

    public String emit(String internalName) {
        String csClassName = CsNamer.classNameOf(internalName);
        return new CsWriter()
                .open("namespace " + CsNamer.namespaceOf(internalName))
                .open("internal class " + csClassName + " : global::java.lang.Object")
                .open("public " + csClassName + "(global::java.lang.RawNew r) : base(r)")
                .close()
                .close()
                .close()
                .toString();
    }
}
