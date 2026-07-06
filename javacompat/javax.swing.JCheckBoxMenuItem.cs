namespace javax.swing
{
    public class JCheckBoxMenuItem : JMenuItem
    {
        public JCheckBoxMenuItem(global::java.lang.RawNew r) : base(r)
        {
            AvMenuItem.ToggleType = global::Avalonia.Controls.MenuItemToggleType.CheckBox;
        }

        public int isSelected()
        {
            return AvMenuItem.IsChecked ? 1 : 0;
        }

        public void setSelected(int selected)
        {
            AvMenuItem.IsChecked = selected != 0;
        }
    }
}
