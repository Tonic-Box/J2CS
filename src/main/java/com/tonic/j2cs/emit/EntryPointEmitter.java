package com.tonic.j2cs.emit;

import com.tonic.j2cs.frontend.InputLoader;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.NamingContext;

/**
 * Emits Program.cs: the .NET entry point that converts args and calls the transpiled main.
 */
public final class EntryPointEmitter {

    public String emit(String entryClassInternalName, NamingContext naming) {
        String mainName = naming.namerOf(entryClassInternalName)
                .methodName("main", InputLoader.MAIN_DESCRIPTOR);
        return new CsWriter()
                .open("internal static class Program")
                .open("internal static int Main(string[] args)")
                .open("try")
                .line(CsNamer.fqcn(entryClassInternalName) + "." + mainName
                        + "(global::java.lang.JRuntime.Args(args));")
                .line("return 0;")
                .close()
                .open("catch (global::java.lang.JThrow __e)")
                .line("global::System.Console.Error.Write(\"Exception in thread \\\"main\\\" \""
                        + " + __e.payload.toString().Value + \"\\n\");")
                .line("return 1;")
                .close()
                .close()
                .close()
                .toString();
    }
}
