namespace java.awt
{
    /// <summary>
    /// Builds Avalonia visuals approximating Swing borders. Line borders use an Avalonia Border;
    /// etched and raised/lowered bevel borders use J2csEdgeBorder (two-tone highlight/shadow edges,
    /// like Metal); TitledBorder is a square outlined box with the title breaking the top edge.
    /// Empty borders are handled as padding by Container and never reach here.
    /// </summary>
    internal static class J2csBorders
    {
        private static global::Avalonia.Media.IBrush Rgb(byte r, byte g, byte b) =>
            new global::Avalonia.Media.SolidColorBrush(global::Avalonia.Media.Color.FromRgb(r, g, b));

        private static readonly global::Avalonia.Media.IBrush Line = Rgb(0x9E, 0x9E, 0x9E);
        private static readonly global::Avalonia.Media.IBrush Shadow = Rgb(0x96, 0x96, 0x96);
        private static readonly global::Avalonia.Media.IBrush DkShadow = Rgb(0x7A, 0x7A, 0x7A);
        private static readonly global::Avalonia.Media.IBrush Highlight = Rgb(0xFF, 0xFF, 0xFF);
        private static readonly global::Avalonia.Media.IBrush LtHighlight = Rgb(0xE0, 0xE0, 0xE0);

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
                case global::javax.swing.border.Border.Kind.Etched:
                    wrapped = new J2csEdgeBorder(Shadow, Highlight, Highlight, Shadow, panelBg) { Child = inner };
                    break;
                case global::javax.swing.border.Border.Kind.LoweredBevel:
                    wrapped = new J2csEdgeBorder(Shadow, Highlight, DkShadow, LtHighlight, panelBg) { Child = inner };
                    break;
                case global::javax.swing.border.Border.Kind.RaisedBevel:
                    wrapped = new J2csEdgeBorder(LtHighlight, DkShadow, Highlight, Shadow, panelBg) { Child = inner };
                    break;
                default:
                    wrapped = Outlined(inner, border, panelBg);
                    break;
            }
            wrapped.Tag = global::java.awt.J2csPanel.StretchTag;
            if (wrapped is global::Avalonia.Controls.Panel wp) { wp.Background = panelBg; }
            else if (wrapped is global::Avalonia.Controls.Border wb) { wb.Background = panelBg; }
            return wrapped;
        }

        private static global::Avalonia.Controls.Control Outlined(
            global::Avalonia.Controls.Control inner, global::javax.swing.border.Border border,
            global::Avalonia.Media.IBrush panelBg)
        {
            var brush = border.LineColor != null ? border.LineColor.Brush : Line;
            double t = border.Top > 0 ? border.Top : 1;
            return new global::Avalonia.Controls.Border
            {
                BorderBrush = brush,
                BorderThickness = new global::Avalonia.Thickness(t),
                Padding = new global::Avalonia.Thickness(2),
                Background = panelBg,
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
                CornerRadius = new global::Avalonia.CornerRadius(0),
                Margin = new global::Avalonia.Thickness(0, 7, 0, 0),
                Padding = new global::Avalonia.Thickness(6, 8, 6, 6),
                Background = panelBg,
                Child = inner
            };
            var label = new global::Avalonia.Controls.TextBlock
            {
                Text = global::java.lang.JRuntime.Cs(title),
                FontSize = global::java.awt.J2csTheme.UiFontSize,
                FontFamily = global::java.awt.J2csTheme.UiFont,
                Foreground = global::java.awt.J2csTheme.MetalText,
                Margin = new global::Avalonia.Thickness(8, 0, 0, 0),
                Padding = new global::Avalonia.Thickness(3, 0, 3, 0),
                Background = panelBg,
                HorizontalAlignment = global::Avalonia.Layout.HorizontalAlignment.Left,
                VerticalAlignment = global::Avalonia.Layout.VerticalAlignment.Top
            };
            var grid = new global::Avalonia.Controls.Grid { Background = panelBg };
            grid.Children.Add(box);
            grid.Children.Add(label);
            return grid;
        }
    }

    /// <summary>Draws two 1px edge rings (Metal etched/bevel): each ring paints its top+left edges
    /// in one brush and bottom+right in another, over a filled background, insetting the child by 2px.</summary>
    internal sealed class J2csEdgeBorder : global::Avalonia.Controls.Decorator
    {
        private readonly global::Avalonia.Media.IBrush outerTl;
        private readonly global::Avalonia.Media.IBrush outerBr;
        private readonly global::Avalonia.Media.IBrush innerTl;
        private readonly global::Avalonia.Media.IBrush innerBr;
        private readonly global::Avalonia.Media.IBrush bg;

        internal global::Avalonia.Media.IBrush FillBrush => bg;

        internal J2csEdgeBorder(global::Avalonia.Media.IBrush outerTl, global::Avalonia.Media.IBrush outerBr,
                global::Avalonia.Media.IBrush innerTl, global::Avalonia.Media.IBrush innerBr,
                global::Avalonia.Media.IBrush bg)
        {
            this.outerTl = outerTl;
            this.outerBr = outerBr;
            this.innerTl = innerTl;
            this.innerBr = innerBr;
            this.bg = bg;
            Padding = new global::Avalonia.Thickness(2);
        }

        public override void Render(global::Avalonia.Media.DrawingContext ctx)
        {
            double w = Bounds.Width;
            double h = Bounds.Height;
            if (bg != null)
            {
                ctx.FillRectangle(bg, new global::Avalonia.Rect(0, 0, w, h));
            }
            Ring(ctx, 0, w, h, outerTl, outerBr);
            Ring(ctx, 1, w, h, innerTl, innerBr);
            base.Render(ctx);
        }

        private static void Ring(global::Avalonia.Media.DrawingContext ctx, double i, double w, double h,
                global::Avalonia.Media.IBrush tl, global::Avalonia.Media.IBrush br)
        {
            ctx.FillRectangle(tl, new global::Avalonia.Rect(i, i, w - 2 * i, 1));
            ctx.FillRectangle(tl, new global::Avalonia.Rect(i, i, 1, h - 2 * i));
            ctx.FillRectangle(br, new global::Avalonia.Rect(i, h - i - 1, w - 2 * i, 1));
            ctx.FillRectangle(br, new global::Avalonia.Rect(w - i - 1, i, 1, h - 2 * i));
        }
    }
}
