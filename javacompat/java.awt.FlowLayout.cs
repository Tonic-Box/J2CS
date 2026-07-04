namespace java.awt
{
    public class FlowLayout : global::java.lang.Object, LayoutManager
    {
        private int align = 1;
        private int hgap = 5;
        private int vgap = 5;

        public FlowLayout(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_I_V(int align)
        {
            this.align = align;
        }

        public void __init_III_V(int align, int hgap, int vgap)
        {
            this.align = align;
            this.hgap = hgap;
            this.vgap = vgap;
        }

        public global::Avalonia.Controls.Panel J2csCreatePanel()
        {
            return new global::java.awt.J2csFlowPanel { Align = align, Hgap = hgap, Vgap = vgap };
        }

        public void J2csAdd(global::Avalonia.Controls.Panel panel, global::Avalonia.Controls.Control child,
                global::java.lang.Object constraint)
        {
            panel.Children.Add(child);
        }
    }
}
