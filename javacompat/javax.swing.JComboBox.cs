namespace javax.swing
{
    public class JComboBox : global::java.awt.Component
    {
        private global::Avalonia.Controls.ComboBox combo;
        private readonly global::System.Collections.Generic.List<global::java.lang.Object> model =
            new global::System.Collections.Generic.List<global::java.lang.Object>();

        public JComboBox(global::java.lang.RawNew r) : base(r)
        {
        }

        private void Ensure()
        {
            if (combo == null)
            {
                combo = new global::Avalonia.Controls.ComboBox
                {
                    Foreground = global::java.awt.J2csTheme.MetalText,
                    Background = global::java.awt.J2csTheme.MetalGray
                };
                AvControl = combo;
            }
        }

        public void __init__V()
        {
            Ensure();
        }

        public void __init__Ljava_lang_Object__V(global::java.lang.Object[] items)
        {
            Ensure();
            if (items != null)
            {
                foreach (var it in items) { Add(it); }
            }
            if (model.Count > 0) { combo.SelectedIndex = 0; }
        }

        private void Add(global::java.lang.Object item)
        {
            model.Add(item);
            combo.Items.Add(global::java.lang.JRuntime.Str(item));
        }

        public void addItem(global::java.lang.Object item)
        {
            Ensure();
            Add(item);
            if (combo.SelectedIndex < 0) { combo.SelectedIndex = 0; }
        }

        public global::java.lang.Object getItemAt(int i)
        {
            return i >= 0 && i < model.Count ? model[i] : null;
        }

        public int getItemCount()
        {
            return model.Count;
        }

        public int getSelectedIndex()
        {
            return combo == null ? -1 : combo.SelectedIndex;
        }

        public global::java.lang.Object getSelectedItem()
        {
            int i = combo == null ? -1 : combo.SelectedIndex;
            return i >= 0 && i < model.Count ? model[i] : null;
        }

        public void setSelectedIndex(int i)
        {
            Ensure();
            combo.SelectedIndex = i;
        }

        public void removeAllItems()
        {
            Ensure();
            model.Clear();
            combo.Items.Clear();
        }

        public void addActionListener(global::java.awt.@event.ActionListener l)
        {
            Ensure();
            if (l != null)
            {
                combo.SelectionChanged += (s, e) =>
                        l.actionPerformed(new global::java.awt.@event.ActionEvent(global::java.lang.RawNew.I));
            }
        }
    }
}
