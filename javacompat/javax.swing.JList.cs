namespace javax.swing
{
    public class JList : global::java.awt.Component
    {
        private global::Avalonia.Controls.ListBox list;

        public JList(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljavax_swing_ListModel__V(ListModel model)
        {
            list = new global::Avalonia.Controls.ListBox();
            if (model != null)
            {
                list.ItemsSource = model.J2csItems();
            }
            AvControl = list;
        }

        public int getSelectedIndex()
        {
            return list == null ? -1 : list.SelectedIndex;
        }
    }
}
