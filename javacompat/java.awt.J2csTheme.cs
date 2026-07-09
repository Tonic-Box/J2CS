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
        internal static readonly global::Avalonia.Media.IBrush MetalAccent =
            new global::Avalonia.Media.SolidColorBrush(global::Avalonia.Media.Color.FromRgb(0xA3, 0xB8, 0xCC));
        internal static readonly global::Avalonia.Media.IBrush White =
            global::Avalonia.Media.Brushes.White;
        internal static readonly global::Avalonia.Media.FontFamily UiFont =
            new global::Avalonia.Media.FontFamily("Segoe UI");
        // Metal's default control font. The reference renders 12pt at ~12px em, so map 1:1.
        internal const double UiFontSize = 12.0;

        /// <summary>Advance width of one text-field "column" (the width of 'm' in the default,
        /// plain UI font), mirroring Swing's FontMetrics-based column sizing so JTextField/
        /// JPasswordField widths scale with the font instead of a fixed constant.</summary>
        internal static double ColumnWidth()
        {
            var ft = new global::Avalonia.Media.FormattedText(
                "m",
                global::System.Globalization.CultureInfo.InvariantCulture,
                global::Avalonia.Media.FlowDirection.LeftToRight,
                new global::Avalonia.Media.Typeface(UiFont),
                UiFontSize,
                MetalText);
            return ft.Width;
        }

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
            tb.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.TextBlock.FontWeightProperty,
                global::Avalonia.Media.FontWeight.Bold));
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

            // Fluent's ListBoxItem template is tall (generous padding + min height); tighten it
            // toward Metal's cell height, which is essentially the font line height.
            var lbi = new global::Avalonia.Styling.Style(x =>
                global::Avalonia.Styling.Selectors.OfType<global::Avalonia.Controls.ListBoxItem>(x));
            lbi.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.Primitives.TemplatedControl.PaddingProperty,
                new global::Avalonia.Thickness(4, 0, 4, 0)));
            lbi.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Layout.Layoutable.MinHeightProperty, 0.0));
            app.Styles.Add(lbi);

            // Fluent's TextBox is ~30px tall with generous padding; Metal's field is ~font height
            // plus a thin inset. Tighten padding and drop the min height toward the Metal look.
            var txt = new global::Avalonia.Styling.Style(x =>
                global::Avalonia.Styling.Selectors.OfType<global::Avalonia.Controls.TextBox>(x));
            txt.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Controls.Primitives.TemplatedControl.PaddingProperty,
                new global::Avalonia.Thickness(4, 2, 4, 2)));
            txt.Setters.Add(new global::Avalonia.Styling.Setter(
                global::Avalonia.Layout.Layoutable.MinHeightProperty, 0.0));
            app.Styles.Add(txt);
        }
    }
}
