namespace java.awt
{
    public class GridLayout : global::java.lang.Object, LayoutManager
    {
        private int rows = 1;
        private int cols;
        private int hgap;
        private int vgap;

        public GridLayout(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
            rows = 1;
            cols = 0;
        }

        public void __init_II_V(int rows, int cols)
        {
            this.rows = rows;
            this.cols = cols;
        }

        public void __init_IIII_V(int rows, int cols, int hgap, int vgap)
        {
            this.rows = rows;
            this.cols = cols;
            this.hgap = hgap;
            this.vgap = vgap;
        }

        public global::Avalonia.Controls.Panel J2csCreatePanel()
        {
            return new global::java.awt.J2csGridPanel { Rows = rows, Cols = cols, Hgap = hgap, Vgap = vgap };
        }

        public void J2csAdd(global::Avalonia.Controls.Panel panel, global::Avalonia.Controls.Control child,
                global::java.lang.Object constraint)
        {
            child.HorizontalAlignment = global::Avalonia.Layout.HorizontalAlignment.Stretch;
            child.VerticalAlignment = global::Avalonia.Layout.VerticalAlignment.Stretch;
            panel.Children.Add(child);
        }
    }
}
