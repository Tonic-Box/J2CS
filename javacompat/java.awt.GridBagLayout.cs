namespace java.awt
{
    public class GridBagLayout : global::java.lang.Object, LayoutManager
    {
        public GridBagLayout(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public global::Avalonia.Controls.Panel J2csCreatePanel()
        {
            return new global::java.awt.J2csGridBagPanel();
        }

        public void J2csAdd(global::Avalonia.Controls.Panel panel, global::Avalonia.Controls.Control child,
                global::java.lang.Object constraint)
        {
            child.HorizontalAlignment = global::Avalonia.Layout.HorizontalAlignment.Stretch;
            child.VerticalAlignment = global::Avalonia.Layout.VerticalAlignment.Stretch;
            if (panel is global::java.awt.J2csGridBagPanel bag
                    && constraint is global::java.awt.GridBagConstraints gbc)
            {
                bag.SetConstraints(child, gbc);
            }
            panel.Children.Add(child);
        }
    }
}
