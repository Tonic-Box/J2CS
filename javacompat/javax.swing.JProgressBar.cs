namespace javax.swing
{
    public class JProgressBar : global::java.awt.Component
    {
        private global::Avalonia.Controls.ProgressBar bar;

        public JProgressBar(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
            bar = Configure(new global::Avalonia.Controls.ProgressBar());
            AvControl = bar;
        }

        public void __init_II_V(int min, int max)
        {
            bar = Configure(new global::Avalonia.Controls.ProgressBar
            {
                Minimum = min,
                Maximum = max
            });
            AvControl = bar;
        }

        // Java's Metal JProgressBar is a bordered rectangle with a solid fill. The Fluent ProgressBar
        // has no border, so its gray track vanishes on the gray status panel; the visibility comes from
        // the border and fill, so add a Metal border, square corners, and the accent fill explicitly
        // while keeping the Metal-gray track to match the Java look.
        private static global::Avalonia.Controls.ProgressBar Configure(global::Avalonia.Controls.ProgressBar b)
        {
            b.Background = global::java.awt.J2csTheme.MetalGray;
            b.Foreground = global::java.awt.J2csTheme.MetalAccent;
            b.BorderBrush = global::java.awt.J2csTheme.MetalBorder;
            b.BorderThickness = new global::Avalonia.Thickness(1);
            b.CornerRadius = new global::Avalonia.CornerRadius(0);
            b.MinWidth = 0;
            b.MinHeight = 0;
            b.FontWeight = global::Avalonia.Media.FontWeight.Bold;
            return b;
        }

        public void setIndeterminate(int indeterminate)
        {
            if (bar != null)
            {
                bar.IsIndeterminate = indeterminate != 0;
            }
        }

        public void setValue(int value)
        {
            if (bar != null)
            {
                bar.Value = value;
            }
        }

        public int getValue()
        {
            return bar == null ? 0 : (int)bar.Value;
        }

        public void setMinimum(int min)
        {
            if (bar != null)
            {
                bar.Minimum = min;
            }
        }

        public void setMaximum(int max)
        {
            if (bar != null)
            {
                bar.Maximum = max;
            }
        }

        public void setStringPainted(int painted)
        {
            if (bar != null)
            {
                bar.ShowProgressText = painted != 0;
            }
        }

        public void setString(global::java.lang.String s)
        {
            if (bar != null)
            {
                bar.ProgressTextFormat = global::java.lang.JRuntime.Cs(s);
            }
        }
    }
}
