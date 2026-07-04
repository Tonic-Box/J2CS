namespace javax.swing
{
    public class BoxLayout : global::java.lang.Object, global::java.awt.LayoutManager
    {
        private int axis;

        public BoxLayout(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_awt_Container_I_V(global::java.awt.Container target, int axis)
        {
            this.axis = axis;
        }

        public global::Avalonia.Controls.Panel J2csCreatePanel()
        {
            return new global::java.awt.J2csBoxPanel { Axis = axis };
        }

        public void J2csAdd(global::Avalonia.Controls.Panel panel, global::Avalonia.Controls.Control child,
                global::java.lang.Object constraint)
        {
            panel.Children.Add(child);
        }
    }
}
