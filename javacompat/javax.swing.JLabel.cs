namespace javax.swing
{
    public class JLabel : global::java.awt.Component
    {
        private global::Avalonia.Controls.TextBlock label;

        public JLabel(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String text)
        {
            label = new global::Avalonia.Controls.TextBlock
            {
                Text = text == null ? "" : text.Value
            };
            AvControl = label;
        }

        public void setText(global::java.lang.String text)
        {
            if (label != null)
            {
                label.Text = text == null ? "" : text.Value;
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
                label.FontFamily = new global::Avalonia.Media.FontFamily(font.Family);
                label.FontSize = font.Size;
                label.FontWeight = (font.Style & 1) != 0
                        ? global::Avalonia.Media.FontWeight.Bold
                        : global::Avalonia.Media.FontWeight.Normal;
            }
        }
    }
}
