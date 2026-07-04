namespace javax.swing
{
    public class DefaultListModel : global::java.lang.Object, ListModel
    {
        internal readonly global::System.Collections.ObjectModel.ObservableCollection<object> Items =
                new global::System.Collections.ObjectModel.ObservableCollection<object>();

        public DefaultListModel(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public global::System.Collections.ObjectModel.ObservableCollection<object> J2csItems()
        {
            return Items;
        }

        public void addElement(global::java.lang.Object element)
        {
            Items.Add(global::java.lang.JRuntime.Str(element));
        }

        public void clear()
        {
            Items.Clear();
        }
    }
}
