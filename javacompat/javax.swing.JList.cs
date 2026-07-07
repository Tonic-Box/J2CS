namespace javax.swing
{
    public class JList : global::java.awt.Component
    {
        private global::Avalonia.Controls.ListBox list;

        public JList(global::java.lang.RawNew r) : base(r)
        {
        }

        private void Ensure()
        {
            if (list == null)
            {
                list = new global::Avalonia.Controls.ListBox
                {
                    Foreground = global::java.awt.J2csTheme.MetalText,
                    Background = global::Avalonia.Media.Brushes.White
                };
                AvControl = list;
            }
        }

        public void __init__V()
        {
            Ensure();
        }

        public void __init_Ljavax_swing_ListModel__V(ListModel model)
        {
            Ensure();
            if (model != null)
            {
                list.ItemsSource = model.J2csItems();
            }
        }

        public void __init__Ljava_lang_Object__V(global::java.lang.Object[] items)
        {
            Ensure();
            var rows = new global::System.Collections.Generic.List<string>();
            if (items != null)
            {
                foreach (var it in items) { rows.Add(global::java.lang.JRuntime.Str(it)); }
            }
            list.ItemsSource = rows;
        }

        public int getSelectedIndex()
        {
            return list == null ? -1 : list.SelectedIndex;
        }
    }
}
