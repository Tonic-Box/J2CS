package com.tonic.j2cs.emit;

import com.tonic.j2cs.frontend.InputLoader;
import com.tonic.j2cs.naming.CsNamer;
import com.tonic.j2cs.naming.NamingContext;

/**
 * Emits Program.cs: the .NET entry point that converts args and calls the transpiled main.
 * A GUI app (Swing/AWT shims in use) instead boots an Avalonia application and runs the
 * transpiled main on the UI thread, keeping the process alive until the app shuts down.
 */
public final class EntryPointEmitter {

    public String emit(String entryClassInternalName, NamingContext naming) {
        return emit(entryClassInternalName, naming, false);
    }

    public String emit(String entryClassInternalName, NamingContext naming, boolean usesGui) {
        String mainName = naming.namerOf(entryClassInternalName)
                .methodName("main", InputLoader.MAIN_DESCRIPTOR);
        String mainCall = CsNamer.fqcn(entryClassInternalName) + "." + mainName;
        return usesGui ? guiProgram(mainCall) : consoleProgram(mainCall);
    }

    private String consoleProgram(String mainCall) {
        return new CsWriter()
                .open("internal static class Program")
                .open("internal static int Main(string[] args)")
                .open("try")
                .line(mainCall + "(global::java.lang.JRuntime.Args(args));")
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

    private String guiProgram(String mainCall) {
        return new CsWriter()
                .line("using Avalonia;")
                .line("")
                .open("internal sealed class J2csApp : global::Avalonia.Application")
                .open("public override void Initialize()")
                .line("Styles.Add(new global::Avalonia.Themes.Fluent.FluentTheme());")
                .close()
                .open("public override void OnFrameworkInitializationCompleted()")
                .open("if (ApplicationLifetime is global::Avalonia.Controls.ApplicationLifetimes"
                        + ".IClassicDesktopStyleApplicationLifetime __desktop)")
                .line("__desktop.ShutdownMode = global::Avalonia.Controls.ShutdownMode.OnExplicitShutdown;")
                .close()
                .line("base.OnFrameworkInitializationCompleted();")
                .line("global::Avalonia.Threading.Dispatcher.UIThread.Post(Program.RunMain);")
                .close()
                .close()
                .open("internal static class Program")
                .line("private static string[] __args;")
                .open("internal static void RunMain()")
                .open("try")
                .line(mainCall + "(global::java.lang.JRuntime.Args(__args));")
                .close()
                .open("catch (global::java.lang.JThrow __e)")
                .line("global::System.Console.Error.Write(\"Exception in thread \\\"main\\\" \""
                        + " + __e.payload.toString().Value + \"\\n\");")
                .close()
                .close()
                .open("internal static int Main(string[] args)")
                .line("__args = args;")
                .line("global::Avalonia.AppBuilder.Configure<J2csApp>()"
                        + ".UsePlatformDetect().StartWithClassicDesktopLifetime(args);")
                .line("return 0;")
                .close()
                .close()
                .toString();
    }
}
