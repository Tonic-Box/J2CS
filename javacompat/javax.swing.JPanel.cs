namespace javax.swing
{
    public class JPanel : global::java.awt.Container
    {
        public JPanel(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
            var surface = new global::java.awt.J2csPaintSurface();
            surface.OnRender = ctx =>
            {
                var g = new global::java.awt.Graphics2D(global::java.lang.RawNew.I);
                g.Ctx = ctx;
                paintComponent(g);
            };
            AvControl = surface;
        }

        public void __init_Ljava_awt_LayoutManager__V(global::java.awt.LayoutManager lm)
        {
            setLayout(lm);
        }

        public virtual void paintComponent(global::java.awt.Graphics g)
        {
        }
    }
}
