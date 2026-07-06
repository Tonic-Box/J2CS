namespace javax.swing
{
    public class Box : global::java.lang.Object
    {
        private Box(global::java.lang.RawNew r) : base(r)
        {
        }

        public static global::java.awt.Component createVerticalStrut(int height)
        {
            var strut = new global::java.awt.Component(global::java.lang.RawNew.I);
            strut.AvControl = new global::Avalonia.Controls.Border { Height = height };
            return strut;
        }

        public static global::java.awt.Component createHorizontalStrut(int width)
        {
            var strut = new global::java.awt.Component(global::java.lang.RawNew.I);
            strut.AvControl = new global::Avalonia.Controls.Border { Width = width };
            return strut;
        }

        public static global::java.awt.Component createVerticalGlue()
        {
            return Glue();
        }

        public static global::java.awt.Component createHorizontalGlue()
        {
            return Glue();
        }

        public static global::java.awt.Component createGlue()
        {
            return Glue();
        }

        public static global::java.awt.Component createRigidArea(global::java.awt.Dimension d)
        {
            var c = new global::java.awt.Component(global::java.lang.RawNew.I);
            c.AvControl = new global::Avalonia.Controls.Border
            {
                Width = d == null ? 0 : d.W,
                Height = d == null ? 0 : d.H,
            };
            return c;
        }

        private static global::java.awt.Component Glue()
        {
            var c = new global::java.awt.Component(global::java.lang.RawNew.I);
            c.AvControl = new global::Avalonia.Controls.Border { Tag = global::java.awt.J2csPanel.GlueTag };
            return c;
        }
    }
}
