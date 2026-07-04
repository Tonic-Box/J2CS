namespace java.awt
{
    public class FlowLayout : global::java.lang.Object, LayoutManager
    {
        private int align;

        public FlowLayout(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
            align = 1;
        }

        public void __init_I_V(int align)
        {
            this.align = align;
        }

        public global::Avalonia.Controls.Panel J2csCreatePanel()
        {
            var panel = new global::Avalonia.Controls.WrapPanel
            {
                Orientation = global::Avalonia.Layout.Orientation.Horizontal
            };
            panel.HorizontalAlignment = align == 0
                    ? global::Avalonia.Layout.HorizontalAlignment.Left
                    : align == 2
                            ? global::Avalonia.Layout.HorizontalAlignment.Right
                            : global::Avalonia.Layout.HorizontalAlignment.Center;
            return panel;
        }

        public void J2csAdd(global::Avalonia.Controls.Panel panel, global::Avalonia.Controls.Control child,
                global::java.lang.Object constraint)
        {
            panel.Children.Add(child);
        }
    }
}
