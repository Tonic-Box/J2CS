namespace javax.swing
{
    public class JMenuBar : global::java.awt.Component
    {
        internal global::Avalonia.Controls.Menu AvMenu;

        public JMenuBar(global::java.lang.RawNew r) : base(r)
        {
            AvMenu = new global::Avalonia.Controls.Menu();
            AvMenu.Background = global::java.awt.Container.DefaultPanelBg;
            AvControl = AvMenu;
        }

        public void __init__V() { }

        public JMenu add(JMenu menu)
        {
            if (menu != null)
            {
                AvMenu.Items.Add(menu.AvMenuItem);
            }
            return menu;
        }
    }
}
