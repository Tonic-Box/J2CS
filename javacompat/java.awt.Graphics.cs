namespace java.awt
{
    /// <summary>
    /// Bridges Swing custom painting to Avalonia: wraps a DrawingContext with a current transform
    /// (translate/scale/rotate accumulate a matrix), brush (setColor) and font. Draw calls push the
    /// transform. create() clones the state; dispose() is a no-op.
    /// </summary>
    public class Graphics : global::java.lang.Object
    {
        internal global::Avalonia.Media.DrawingContext Ctx;
        internal global::Avalonia.Matrix Transform = global::Avalonia.Matrix.Identity;
        internal global::Avalonia.Media.IBrush Brush = global::Avalonia.Media.Brushes.Black;
        internal double FontSize = 12;

        public Graphics(global::java.lang.RawNew r) : base(r)
        {
        }

        public virtual Graphics create()
        {
            var g = new Graphics2D(global::java.lang.RawNew.I);
            g.Ctx = Ctx;
            g.Transform = Transform;
            g.Brush = Brush;
            g.FontSize = FontSize;
            return g;
        }

        public virtual void dispose()
        {
        }

        public void setColor(global::java.awt.Color c)
        {
            if (c != null)
            {
                Brush = c.Brush;
            }
        }

        public void setFont(global::java.awt.Font f)
        {
            if (f != null)
            {
                FontSize = f.Size;
            }
        }

        public void fillRect(int x, int y, int width, int height)
        {
            if (Ctx == null) { return; }
            using (Ctx.PushTransform(Transform))
            {
                Ctx.DrawRectangle(Brush, null, new global::Avalonia.Rect(x, y, width, height));
            }
        }

        public void drawRect(int x, int y, int width, int height)
        {
            if (Ctx == null) { return; }
            var pen = new global::Avalonia.Media.Pen(Brush, 1);
            using (Ctx.PushTransform(Transform))
            {
                Ctx.DrawRectangle(null, pen, new global::Avalonia.Rect(x, y, width, height));
            }
        }

        public void fillOval(int x, int y, int width, int height)
        {
            if (Ctx == null) { return; }
            using (Ctx.PushTransform(Transform))
            {
                Ctx.DrawEllipse(Brush, null,
                    new global::Avalonia.Point(x + width / 2.0, y + height / 2.0), width / 2.0, height / 2.0);
            }
        }

        public void drawOval(int x, int y, int width, int height)
        {
            if (Ctx == null) { return; }
            var pen = new global::Avalonia.Media.Pen(Brush, 1);
            using (Ctx.PushTransform(Transform))
            {
                Ctx.DrawEllipse(null, pen,
                    new global::Avalonia.Point(x + width / 2.0, y + height / 2.0), width / 2.0, height / 2.0);
            }
        }

        public void drawLine(int x1, int y1, int x2, int y2)
        {
            if (Ctx == null) { return; }
            var pen = new global::Avalonia.Media.Pen(Brush, 1);
            using (Ctx.PushTransform(Transform))
            {
                Ctx.DrawLine(pen, new global::Avalonia.Point(x1, y1), new global::Avalonia.Point(x2, y2));
            }
        }

        public void drawString(global::java.lang.String s, int x, int y)
        {
            if (Ctx == null) { return; }
            var ft = new global::Avalonia.Media.FormattedText(
                global::java.lang.JRuntime.Cs(s),
                global::System.Globalization.CultureInfo.CurrentCulture,
                global::Avalonia.Media.FlowDirection.LeftToRight,
                new global::Avalonia.Media.Typeface("Segoe UI"),
                FontSize, Brush);
            using (Ctx.PushTransform(Transform))
            {
                Ctx.DrawText(ft, new global::Avalonia.Point(x, y - FontSize));
            }
        }
    }

    /// <summary>Avalonia control that forwards its Render to a Swing paintComponent callback
    /// (Panel.Render is sealed, so this is a plain Control; custom-painted panels have no children).</summary>
    internal sealed class J2csPaintSurface : global::Avalonia.Controls.Control
    {
        internal global::System.Action<global::Avalonia.Media.DrawingContext> OnRender;
        internal global::Avalonia.Media.IBrush Bg;

        public override void Render(global::Avalonia.Media.DrawingContext context)
        {
            base.Render(context);
            if (Bg != null)
            {
                context.DrawRectangle(Bg, null, new global::Avalonia.Rect(Bounds.Size));
            }
            OnRender?.Invoke(context);
        }
    }
}
