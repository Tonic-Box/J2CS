using System;
using System.Linq;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Controls.ApplicationLifetimes;
using Avalonia.Headless;
using Avalonia.Interactivity;
using Avalonia.LogicalTree;
using Avalonia.Threading;

internal sealed class HarnessApp : Application
{
    public override void Initialize()
    {
        Styles.Add(new Avalonia.Themes.Fluent.FluentTheme());
    }

    public override void OnFrameworkInitializationCompleted()
    {
        base.OnFrameworkInitializationCompleted();
        Dispatcher.UIThread.Post(Harness.Begin);
    }
}

internal static class Harness
{
    private static int ticks;

    internal static void Begin()
    {
        Dispatcher.UIThread.Post(() =>
        {
            try
            {
                global::jdefault.GuiModal.main(global::java.lang.JRuntime.Args(new string[0]));
            }
            catch (Exception e)
            {
                Console.WriteLine("[CRASH] " + (e.InnerException ?? e));
                Environment.Exit(2);
            }
        });
        var timer = new DispatcherTimer { Interval = TimeSpan.FromMilliseconds(200) };
        timer.Tick += (s, e) => Step();
        timer.Start();
    }

    private static void Step()
    {
        if (++ticks > 50)
        {
            Console.WriteLine("[TIMEOUT]");
            Environment.Exit(3);
        }
        var life = Application.Current?.ApplicationLifetime as IClassicDesktopStyleApplicationLifetime;
        var windows = life?.Windows;
        if (windows == null || windows.Count == 0)
        {
            return;
        }
        var top = windows[windows.Count - 1];
        var close = top.GetLogicalDescendants().OfType<Button>()
                .FirstOrDefault(b => (b.Content?.ToString() ?? "") == "Close");
        close?.RaiseEvent(new RoutedEventArgs(Button.ClickEvent));
    }
}

internal static class Program
{
    private static void Main(string[] args)
    {
        AppBuilder.Configure<HarnessApp>()
                .UseHeadless(new AvaloniaHeadlessPlatformOptions())
                .StartWithClassicDesktopLifetime(args);
    }
}
