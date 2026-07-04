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
                Content = text == null ? "" : text.Value
            };
            AvControl = button;
        }

        public void addActionListener(global::java.awt.@event.ActionListener l)
        {
            if (button != null && l != null)
            {
                button.Click += (sender, e) =>
                        l.actionPerformed(new global::java.awt.@event.ActionEvent(global::java.lang.RawNew.I));
            }
        }
    }
}
