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
            J2csBuildField(columns);
        }

        /// <summary>Shared construction so JPasswordField inherits the same Metal fg/bg and
        /// font-metrics-based column width instead of a bare TextBox.</summary>
        internal void J2csBuildField(int columns)
        {
            field = new global::Avalonia.Controls.TextBox
            {
                MinWidth = columns * global::java.awt.J2csTheme.ColumnWidth(),
                Foreground = global::java.awt.J2csTheme.MetalText,
                Background = global::java.awt.J2csTheme.White,
                // Text components use Metal's plain "user" font, not the bold control font the
                // app theme applies to every other TemplatedControl.
                FontWeight = global::Avalonia.Media.FontWeight.Normal
            };
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
