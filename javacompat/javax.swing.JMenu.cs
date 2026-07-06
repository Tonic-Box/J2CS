namespace javax.swing
{
    public class JMenu : JMenuItem
    {
        public JMenu(global::java.lang.RawNew r) : base(r)
        {
        }

        public JMenuItem add(JMenuItem item)
        {
            if (item != null)
            {
                AvMenuItem.Items.Add(item.AvMenuItem);
            }
            return item;
        }

        public JMenuItem add(global::java.lang.String text)
        {
            var item = new JMenuItem(global::java.lang.RawNew.I);
            item.setText(text);
            AvMenuItem.Items.Add(item.AvMenuItem);
            return item;
        }

        public void addSeparator()
        {
            AvMenuItem.Items.Add(new global::Avalonia.Controls.Separator());
        }
    }
}
