namespace javax.swing
{
    public class JPopupMenu : global::java.awt.Component
    {
        internal readonly global::Avalonia.Controls.ContextMenu AvContextMenu;

        public JPopupMenu(global::java.lang.RawNew r) : base(r)
        {
            AvContextMenu = new global::Avalonia.Controls.ContextMenu();
        }

        public void __init__V() { }
        public void __init_Ljava_lang_String__V(global::java.lang.String label) { }

        public JMenuItem add(JMenuItem item)
        {
            if (item != null)
            {
                AvContextMenu.Items.Add(item.AvMenuItem);
            }
            return item;
        }

        public void addSeparator()
        {
            AvContextMenu.Items.Add(new global::Avalonia.Controls.Separator());
        }

        public void show(global::java.awt.Component invoker, int x, int y)
        {
            if (invoker != null && invoker.AvControl != null)
            {
                AvContextMenu.Open(invoker.AvControl);
            }
        }
    }
}
