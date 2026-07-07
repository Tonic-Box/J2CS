using System;
using System.Globalization;
using System.IO;
using System.Reflection;
using System.Text;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Headless;
using Avalonia.Media;
using Avalonia.Threading;
using Avalonia.VisualTree;

internal sealed class J2csApp : Application
{
    public override void Initialize()
    {
        global::java.awt.J2csTheme.Apply(this);
    }

    public override void OnFrameworkInitializationCompleted()
    {
        base.OnFrameworkInitializationCompleted();
        Dispatcher.UIThread.Post(VisDriver.Begin);
    }
}

internal static class VisDriver
{
    private static readonly string Dir = Environment.GetEnvironmentVariable("VIS_DIR") ?? ".";
    private static readonly string Scenario = Environment.GetEnvironmentVariable("VIS_SCENARIO") ?? "";
    private static readonly string SizeSpec = Environment.GetEnvironmentVariable("VIS_SIZE") ?? "400x300";

    private static Window win;

    internal static void Begin()
    {
        try
        {
            global::j2cs.reflect.__Bootstrap.InitAll();

            var type = Type.GetType(Scenario);
            if (type == null) { Console.WriteLine("[VIS] type not found: " + Scenario); Environment.Exit(2); }
            var buildMethod = type.GetMethod("build",
                    BindingFlags.Public | BindingFlags.NonPublic | BindingFlags.Static);
            if (buildMethod == null) { Console.WriteLine("[VIS] build() not found on " + Scenario); Environment.Exit(2); }
            var built = buildMethod.Invoke(null, null);
            var comp = (global::java.awt.Component)built;
            var control = comp.AvControl;

            var parts = SizeSpec.Split('x');
            double w = double.Parse(parts[0], CultureInfo.InvariantCulture);
            double h = double.Parse(parts[1], CultureInfo.InvariantCulture);

            win = new Window { Width = w, Height = h, SystemDecorations = SystemDecorations.None, Content = control };
            win.Show();
        }
        catch (Exception e)
        {
            var inner = e.InnerException ?? e;
            Console.WriteLine("[VIS][CRASH] " + inner.GetType().FullName + ": " + inner.Message);
            Console.WriteLine(inner.StackTrace);
            Environment.Exit(3);
        }

        var timer = new DispatcherTimer { Interval = TimeSpan.FromMilliseconds(300) };
        int ticks = 0;
        timer.Tick += (s, e) =>
        {
            ticks++;
            if (ticks < 2) { return; }
            timer.Stop();
            try { Capture(); }
            catch (Exception ex) { Console.WriteLine("[VIS][CAP] " + ex.Message); }
            Environment.Exit(0);
        };
        timer.Start();
    }

    private static void Capture()
    {
        var frame = win.CaptureRenderedFrame();
        if (frame != null) { frame.Save(Path.Combine(Dir, "cand.png")); }
        Console.WriteLine("[VIS] captured " + Math.Round(win.ClientSize.Width) + "x" + Math.Round(win.ClientSize.Height));

        var sb = new StringBuilder();
        Dump(win, sb);
        File.WriteAllText(Path.Combine(Dir, "cand.txt"), sb.ToString());
    }

    private static void Dump(Visual root, StringBuilder sb)
    {
        if (root is Control c && !string.IsNullOrEmpty(c.Name) && !c.Name.StartsWith("PART_"))
        {
            var p = c.TranslatePoint(new Point(0, 0), win) ?? new Point(0, 0);
            sb.Append(c.Name).Append('|')
              .Append(c.GetType().Name).Append('|')
              .Append(R(p.X)).Append('|').Append(R(p.Y)).Append('|')
              .Append(R(c.Bounds.Width)).Append('|').Append(R(c.Bounds.Height)).Append('|')
              .Append(Argb(Bg(c))).Append('|')
              .Append(Argb(Fg(c))).Append('\n');
        }
        foreach (var child in root.GetVisualChildren()) { Dump(child, sb); }
    }

    private static IBrush Bg(Control c)
    {
        if (c is Panel p) { return p.Background; }
        if (c is Border b) { return b.Background; }
        if (c is Avalonia.Controls.Primitives.TemplatedControl t) { return t.Background; }
        if (c is TextBlock tb) { return tb.Background; }
        if (c is global::java.awt.J2csEdgeBorder eb) { return eb.FillBrush; }
        return null;
    }

    private static IBrush Fg(Control c)
    {
        if (c is TextBlock tb) { return tb.Foreground; }
        if (c is Avalonia.Controls.Primitives.TemplatedControl t) { return t.Foreground; }
        return null;
    }

    private static string Argb(IBrush brush)
    {
        if (brush is ISolidColorBrush s)
        {
            var col = s.Color;
            return col.A.ToString("X2") + col.R.ToString("X2") + col.G.ToString("X2") + col.B.ToString("X2");
        }
        return "none";
    }

    private static int R(double v) { return (int)Math.Round(v); }
}

internal static class Program
{
    private static void Main(string[] args)
    {
        AppBuilder.Configure<J2csApp>()
            .UseSkia()
            .UseHeadless(new AvaloniaHeadlessPlatformOptions { UseHeadlessDrawing = false })
            .StartWithClassicDesktopLifetime(args);
    }
}
