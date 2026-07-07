namespace java.awt
{
    /// <summary>
    /// Builds Avalonia visuals approximating Swing borders: a 1px outline for line/etched/bevel,
    /// and a titled box (outline with the title text breaking the top edge) for TitledBorder.
    /// Empty borders are handled as padding by Container and never reach here.
    /// </summary>
    internal static class J2csBorders
    {
        private static readonly global::Avalonia.Media.IBrush Line =
            new global::Avalonia.Media.SolidColorBrush(global::Avalonia.Media.Color.FromRgb(0x9E, 0x9E, 0x9E));

        internal static global::Avalonia.Controls.Control Wrap(
            global::Avalonia.Controls.Control inner,
            global::javax.swing.border.Border border,
            global::Avalonia.Media.IBrush panelBg)
        {
            if (border == null)
            {
                return inner;
            }
            global::Avalonia.Controls.Control wrapped;
            switch (border.BKind)
            {
                case global::javax.swing.border.Border.Kind.Titled:
                    wrapped = Titled(inner, ((global::javax.swing.border.TitledBorder)border).Title, panelBg);
                    break;
                case global::javax.swing.border.Border.Kind.Compound:
                    return Wrap(Wrap(inner, border.Inner, panelBg), border.Outer, panelBg);
                case global::javax.swing.border.Border.Kind.Empty:
                    return inner;
                default:
                    wrapped = Outlined(inner, border);
                    break;
            }
            // Keep it fill-eligible for BoxLayout, like the bare panel it wraps.
            wrapped.Tag = global::java.awt.J2csPanel.StretchTag;
            // Paint the panel background across the bordered area (Metal fills the whole region),
            // so the outer wrapper also carries the panel color.
            if (wrapped is global::Avalonia.Controls.Panel wp) { wp.Background = panelBg; }
            else if (wrapped is global::Avalonia.Controls.Border wb) { wb.Background = panelBg; }
            return wrapped;
        }

        private static global::Avalonia.Controls.Control Outlined(
            global::Avalonia.Controls.Control inner, global::javax.swing.border.Border border)
        {
            var brush = border.LineColor != null ? border.LineColor.Brush : Line;
            double t = border.Top > 0 ? border.Top : 1;
            return new global::Avalonia.Controls.Border
            {
                BorderBrush = brush,
                BorderThickness = new global::Avalonia.Thickness(t),
                Padding = new global::Avalonia.Thickness(2),
                Child = inner
            };
        }

        private static global::Avalonia.Controls.Control Titled(
            global::Avalonia.Controls.Control inner, global::java.lang.String title, global::Avalonia.Media.IBrush panelBg)
        {
            var box = new global::Avalonia.Controls.Border
            {
                BorderBrush = Line,
                BorderThickness = new global::Avalonia.Thickness(1),
                CornerRadius = new global::Avalonia.CornerRadius(2),
                Margin = new global::Avalonia.Thickness(0, 7, 0, 0),
                Padding = new global::Avalonia.Thickness(6, 8, 6, 6),
                Child = inner
            };
            var label = new global::Avalonia.Controls.TextBlock
            {
                Text = global::java.lang.JRuntime.Cs(title),
                FontSize = 11,
                Margin = new global::Avalonia.Thickness(8, 0, 0, 0),
                Padding = new global::Avalonia.Thickness(3, 0, 3, 0),
                Background = panelBg,
                HorizontalAlignment = global::Avalonia.Layout.HorizontalAlignment.Left,
                VerticalAlignment = global::Avalonia.Layout.VerticalAlignment.Top
            };
            var grid = new global::Avalonia.Controls.Grid();
            grid.Children.Add(box);
            grid.Children.Add(label);
            return grid;
        }
    }
}
