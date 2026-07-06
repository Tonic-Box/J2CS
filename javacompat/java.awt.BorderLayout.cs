namespace java.awt
{
    public class BorderLayout : global::java.lang.Object, LayoutManager
    {
        public static readonly global::java.lang.String CENTER = global::java.lang.String.Wrap("Center");
        public static readonly global::java.lang.String NORTH = global::java.lang.String.Wrap("North");
        public static readonly global::java.lang.String SOUTH = global::java.lang.String.Wrap("South");
        public static readonly global::java.lang.String EAST = global::java.lang.String.Wrap("East");
        public static readonly global::java.lang.String WEST = global::java.lang.String.Wrap("West");
        public static readonly global::java.lang.String PAGE_START = global::java.lang.String.Wrap("North");
        public static readonly global::java.lang.String PAGE_END = global::java.lang.String.Wrap("South");
        public static readonly global::java.lang.String LINE_START = global::java.lang.String.Wrap("West");
        public static readonly global::java.lang.String LINE_END = global::java.lang.String.Wrap("East");

        private int hgap;
        private int vgap;

        public BorderLayout(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_II_V(int hgap, int vgap)
        {
            this.hgap = hgap;
            this.vgap = vgap;
        }

        public global::Avalonia.Controls.Panel J2csCreatePanel()
        {
            return new global::java.awt.J2csBorderPanel { Hgap = hgap, Vgap = vgap };
        }

        public void J2csAdd(global::Avalonia.Controls.Panel panel, global::Avalonia.Controls.Control child,
                global::java.lang.Object constraint)
        {
            string region = constraint == null ? "Center" : global::java.lang.JRuntime.Str(constraint);
            if (region != "North" && region != "South" && region != "East" && region != "West")
            {
                region = "Center";
            }
            child.HorizontalAlignment = global::Avalonia.Layout.HorizontalAlignment.Stretch;
            child.VerticalAlignment = global::Avalonia.Layout.VerticalAlignment.Stretch;
            if (panel is global::java.awt.J2csBorderPanel border)
            {
                border.SetRegion(child, region);
            }
            panel.Children.Add(child);
        }
    }
}
