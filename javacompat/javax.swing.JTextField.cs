namespace javax.swing
{
    public class JTextField : global::java.awt.Component
    {
        internal global::Avalonia.Controls.TextBox field;

        public JTextField(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_I_V(int columns)
        {
            field = new global::Avalonia.Controls.TextBox { MinWidth = columns * 8 };
            AvControl = field;
        }

        public global::java.lang.String getText()
        {
            return global::java.lang.String.Wrap(field == null || field.Text == null ? "" : field.Text);
        }

        public void setText(global::java.lang.String text)
        {
            if (field != null)
            {
                field.Text = global::java.lang.JRuntime.Cs(text);
            }
        }
    }
}
