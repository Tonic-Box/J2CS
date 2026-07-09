namespace javax.swing
{
    public class JButton : global::java.awt.Component
    {
        private global::Avalonia.Controls.Button button;

        public JButton(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String text)
        {
            button = new global::Avalonia.Controls.Button
            {
                Content = global::java.lang.JRuntime.Cs(text),
                Foreground = global::java.awt.J2csTheme.MetalText,
                // Metal control font is bold; set locally because Fluent's ControlTheme pins the
                // templated default and an app-level Style can't override it.
                FontWeight = global::Avalonia.Media.FontWeight.Bold
            };
            AvControl = button;
        }

        public void addActionListener(global::java.awt.@event.ActionListener l)
        {
            if (button != null && l != null)
            {
                button.Click += (sender, e) =>
                {
                    var ae = new global::java.awt.@event.ActionEvent(global::java.lang.RawNew.I);
                    ae.Source = this;
                    l.actionPerformed(ae);
                };
            }
        }
    }
}
