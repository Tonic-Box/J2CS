namespace javax.swing
{
    public class JRadioButton : AbstractButton
    {
        internal global::Avalonia.Controls.RadioButton radio;

        public JRadioButton(global::java.lang.RawNew r) : base(r)
        {
        }

        private void Ensure()
        {
            if (radio == null)
            {
                radio = new global::Avalonia.Controls.RadioButton
                {
                    HorizontalAlignment = global::Avalonia.Layout.HorizontalAlignment.Left,
                    VerticalAlignment = global::Avalonia.Layout.VerticalAlignment.Center,
                    Foreground = global::java.awt.J2csTheme.MetalText,
                    Background = global::java.awt.J2csTheme.MetalGray,
                    Padding = new global::Avalonia.Thickness(4, 0, 0, 0),
                    MinHeight = 0
                };
                AvControl = radio;
            }
        }

        public void __init__V()
        {
            Ensure();
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String text)
        {
            Ensure();
            radio.Content = global::java.lang.JRuntime.Cs(text);
        }

        public void __init_Ljava_lang_String_Z_V(global::java.lang.String text, int selected)
        {
            Ensure();
            radio.Content = global::java.lang.JRuntime.Cs(text);
            radio.IsChecked = selected != 0;
        }

        public int isSelected()
        {
            return radio != null && radio.IsChecked == true ? 1 : 0;
        }

        public void setSelected(int selected)
        {
            Ensure();
            radio.IsChecked = selected != 0;
        }

        public void setText(global::java.lang.String text)
        {
            Ensure();
            radio.Content = global::java.lang.JRuntime.Cs(text);
        }

        public void addActionListener(global::java.awt.@event.ActionListener l)
        {
            Ensure();
            if (l != null)
            {
                radio.IsCheckedChanged += (s, e) =>
                        l.actionPerformed(new global::java.awt.@event.ActionEvent(global::java.lang.RawNew.I));
            }
        }
    }
}
