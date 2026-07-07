namespace java.awt
{
    /// <summary>Single source of truth for the app-level theme applied to both the emitted
    /// Program.cs and the visual-fidelity harness. Tuned toward Java's Metal Look-and-Feel.
    /// Lives in the java.awt namespace so it is treated as a GUI-only shim (not copied into
    /// console apps that lack Avalonia references).</summary>
    public static class J2csTheme
    {
        public static void Apply(global::Avalonia.Application app)
        {
            app.Styles.Add(new global::Avalonia.Themes.Fluent.FluentTheme());
            app.RequestedThemeVariant = global::Avalonia.Styling.ThemeVariant.Light;
        }
    }
}
