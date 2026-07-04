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
    }
}
