namespace java.awt
{
    /// <summary>Single source of truth for the app-level theme applied to both the emitted
    /// Program.cs and the visual-fidelity harness. FluentTheme provides control templates;
    /// these style overrides pull sizing/color toward Java's Metal Look-and-Feel (12px font,
    /// #333333 text, square corners, compact gray controls). Lives in java.awt so it is treated
    /// as a GUI-only shim (not copied into console apps that lack Avalonia references).</summary>
    public static class J2csTheme
    {
        internal static readonly global::Avalonia.Media.IBrush MetalGray =
            new global::Avalonia.Media.SolidColorBrush(global::Avalonia.Media.Color.FromRgb(0xEE, 0xEE, 0xEE));
        internal static readonly global::Avalonia.Media.IBrush MetalText =
            new global::Avalonia.Media.SolidColorBrush(global::Avalonia.Media.Color.FromRgb(0x33, 0x33, 0x33));
        internal static readonly global::Avalonia.Media.IBrush MetalBorder =
            new global::Avalonia.Media.SolidColorBrush(global::Avalonia.Media.Color.FromRgb(0x8E, 0x8E, 0x8E));
        internal static readonly global::Avalonia.Media.FontFamily UiFont =
            new global::Avalonia.Media.FontFamily("Segoe UI");
        internal const double UiFontSize = 12.0;

        public static void Apply(global::Avalonia.Application app)
        {
            app.Styles.Add(new global::Avalonia.Themes.Fluent.FluentTheme());
            app.RequestedThemeVariant = global::Avalonia.Styling.ThemeVariant.Light;

            var tc = new global::Avalonia.Styling.Style(x =>
                global::Avalonia.Styling.Selectors.OfType<global::Avalonia.Controls.Primitives.TemplatedControl>(x));
            tc.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.Primitives.TemplatedControl.FontSizeProperty, UiFontSize));
            tc.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.Primitives.TemplatedControl.FontFamilyProperty, UiFont));
            tc.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.Primitives.TemplatedControl.ForegroundProperty, MetalText));
            tc.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.Primitives.TemplatedControl.CornerRadiusProperty,
                new global::Avalonia.CornerRadius(0)));
            app.Styles.Add(tc);

            var tb = new global::Avalonia.Styling.Style(x =>
                global::Avalonia.Styling.Selectors.OfType<global::Avalonia.Controls.TextBlock>(x));
            tb.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.TextBlock.FontSizeProperty, UiFontSize));
            tb.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.TextBlock.FontFamilyProperty, UiFont));
            tb.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.TextBlock.ForegroundProperty, MetalText));
            app.Styles.Add(tb);

            var btn = new global::Avalonia.Styling.Style(x =>
                global::Avalonia.Styling.Selectors.OfType<global::Avalonia.Controls.Button>(x));
            btn.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.Primitives.TemplatedControl.PaddingProperty,
                new global::Avalonia.Thickness(14, 2, 14, 2)));
            btn.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.Primitives.TemplatedControl.BackgroundProperty, MetalGray));
            btn.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.Primitives.TemplatedControl.BorderBrushProperty, MetalBorder));
            btn.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.Primitives.TemplatedControl.BorderThicknessProperty,
                new global::Avalonia.Thickness(1)));
            btn.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Layout.Layoutable.MinHeightProperty, 0.0));
            app.Styles.Add(btn);
        }
    }
}
