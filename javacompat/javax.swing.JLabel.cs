namespace javax.swing
{
    public class JLabel : global::java.awt.Component
    {
        private global::Avalonia.Controls.TextBlock label;

        public JLabel(global::java.lang.RawNew r) : base(r)
        {
        }

        private void Ensure()
        {
            if (label == null)
            {
                label = new global::Avalonia.Controls.TextBlock();
                AvControl = label;
            }
        }

        public void __init__V()
        {
            Ensure();
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String text)
        {
            Ensure();
            label.Text = global::java.lang.JRuntime.Cs(text);
        }

        public void __init_Ljava_lang_String_I_V(global::java.lang.String text, int horizontalAlignment)
        {
            Ensure();
            label.Text = global::java.lang.JRuntime.Cs(text);
            setHorizontalAlignment(horizontalAlignment);
        }

        public void setHorizontalAlignment(int alignment)
        {
            Ensure();
            label.TextAlignment = alignment switch
            {
                0 => global::Avalonia.Media.TextAlignment.Center,
                4 => global::Avalonia.Media.TextAlignment.Right,
                11 => global::Avalonia.Media.TextAlignment.Right,
                _ => global::Avalonia.Media.TextAlignment.Left,
            };
        }

        public void setText(global::java.lang.String text)
        {
            if (label != null)
            {
                label.Text = global::java.lang.JRuntime.Cs(text);
            }
        }

        public void setForeground(global::java.awt.Color color)
        {
            if (label != null && color != null)
            {
                label.Foreground = color.Brush;
            }
        }

        public void setFont(global::java.awt.Font font)
        {
            if (label != null && font != null)
            {
                label.FontFamily = font.CsFamily();
                label.FontSize = font.Size;
                label.FontWeight = (font.Style & 1) != 0
                        ? global::Avalonia.Media.FontWeight.Bold
                        : global::Avalonia.Media.FontWeight.Normal;
            }
        }
    }
}
