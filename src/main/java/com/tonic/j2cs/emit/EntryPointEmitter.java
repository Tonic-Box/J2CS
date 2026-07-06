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
                .line("global::j2cs.reflect.__Bootstrap.InitAll();")
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

    private static final String TEMPLATED_FONT =
            "global::Avalonia.Controls.Primitives.TemplatedControl.FontSizeProperty";

    private static void compactStyle(CsWriter w, String type, String fontProp, String padding, boolean minZero) {
        String v = "st" + type.substring(type.lastIndexOf('.') + 1);
        w.line("var " + v + " = new global::Avalonia.Styling.Style(x => "
                + "global::Avalonia.Styling.Selectors.OfType<" + type + ">(x));");
        w.line(v + ".Setters.Add(new global::Avalonia.Styling.Setter(" + fontProp + ", 12.0));");
        if (padding != null) {
            w.line(v + ".Setters.Add(new global::Avalonia.Styling.Setter("
                    + "global::Avalonia.Controls.Primitives.TemplatedControl.PaddingProperty, "
                    + "new global::Avalonia.Thickness(" + padding + ")));");
        }
        if (minZero) {
            w.line(v + ".Setters.Add(new global::Avalonia.Styling.Setter("
                    + "global::Avalonia.Layout.Layoutable.MinHeightProperty, 0.0));");
        }
        w.line("Styles.Add(" + v + ");");
    }

    private String guiProgram(String mainCall) {
        CsWriter w = new CsWriter();
        w.line("using Avalonia;");
        w.line("");
        w.open("internal sealed class J2csApp : global::Avalonia.Application");
        w.open("public override void Initialize()");
        w.line("Styles.Add(new global::Avalonia.Themes.Fluent.FluentTheme());");
        w.line("RequestedThemeVariant = global::Avalonia.Styling.ThemeVariant.Light;");
        compactStyle(w, "global::Avalonia.Controls.TextBlock",
                "global::Avalonia.Controls.TextBlock.FontSizeProperty", null, false);
        compactStyle(w, "global::Avalonia.Controls.Button", TEMPLATED_FONT, "8.0, 3.0", true);
        compactStyle(w, "global::Avalonia.Controls.TextBox", TEMPLATED_FONT, "5.0, 2.0", true);
        compactStyle(w, "global::Avalonia.Controls.ListBoxItem", TEMPLATED_FONT, "6.0, 1.0", true);
        w.close();
        return w
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
                .line("global::j2cs.reflect.__Bootstrap.InitAll();")
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
