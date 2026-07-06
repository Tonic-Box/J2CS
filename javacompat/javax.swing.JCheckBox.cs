namespace javax.swing
{
    public class JCheckBox : global::java.awt.Component
    {
        private global::Avalonia.Controls.CheckBox box;

        public JCheckBox(global::java.lang.RawNew r) : base(r)
        {
        }

        private void Ensure()
        {
            if (box == null)
            {
                box = new global::Avalonia.Controls.CheckBox();
                AvControl = box;
            }
        }

        public void __init__V()
        {
            Ensure();
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String text)
        {
            Ensure();
            box.Content = global::java.lang.JRuntime.Cs(text);
        }

        public void __init_Ljava_lang_String_Z_V(global::java.lang.String text, int selected)
        {
            Ensure();
            box.Content = global::java.lang.JRuntime.Cs(text);
            box.IsChecked = selected != 0;
        }

        public int isSelected()
        {
            return box != null && box.IsChecked == true ? 1 : 0;
        }

        public void setSelected(int selected)
        {
            Ensure();
            box.IsChecked = selected != 0;
        }

        public void setText(global::java.lang.String text)
        {
            Ensure();
            box.Content = global::java.lang.JRuntime.Cs(text);
        }

        public void addActionListener(global::java.awt.@event.ActionListener l)
        {
            if (l == null)
            {
                return;
            }
            Ensure();
            box.IsCheckedChanged += (s, e) =>
                    l.actionPerformed(new global::java.awt.@event.ActionEvent(global::java.lang.RawNew.I));
        }

        public void setToolTipText(global::java.lang.String text)
        {
            Ensure();
            global::Avalonia.Controls.ToolTip.SetTip(box, global::java.lang.JRuntime.Cs(text));
        }
    }
}
