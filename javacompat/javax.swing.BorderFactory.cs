namespace javax.swing
{
    public class BorderFactory : global::java.lang.Object
    {
        private BorderFactory(global::java.lang.RawNew r) : base(r)
        {
        }

        public static global::javax.swing.border.Border createEmptyBorder(int top, int left, int bottom, int right)
        {
            var border = new global::javax.swing.border.Border(global::java.lang.RawNew.I);
            border.BKind = global::javax.swing.border.Border.Kind.Empty;
            border.Top = top;
            border.Left = left;
            border.Bottom = bottom;
            border.Right = right;
            return border;
        }

        public static global::javax.swing.border.TitledBorder createTitledBorder(global::java.lang.String title)
        {
            var b = new global::javax.swing.border.TitledBorder(global::java.lang.RawNew.I);
            b.BKind = global::javax.swing.border.Border.Kind.Titled;
            b.Title = title;
            return b;
        }

        public static global::javax.swing.border.TitledBorder createTitledBorder(
            global::javax.swing.border.Border border, global::java.lang.String title)
        {
            return createTitledBorder(title);
        }

        public static global::javax.swing.border.Border createLineBorder(global::java.awt.Color color, int thickness)
        {
            var b = new global::javax.swing.border.Border(global::java.lang.RawNew.I);
            b.BKind = global::javax.swing.border.Border.Kind.Line;
            b.LineColor = color;
            b.Top = b.Left = b.Bottom = b.Right = thickness;
            return b;
        }

        public static global::javax.swing.border.Border createLineBorder(global::java.awt.Color color)
        {
            return createLineBorder(color, 1);
        }

        public static global::javax.swing.border.Border createEtchedBorder()
        {
            var b = new global::javax.swing.border.Border(global::java.lang.RawNew.I);
            b.BKind = global::javax.swing.border.Border.Kind.Etched;
            return b;
        }

        public static global::javax.swing.border.Border createLoweredBevelBorder()
        {
            var b = new global::javax.swing.border.Border(global::java.lang.RawNew.I);
            b.BKind = global::javax.swing.border.Border.Kind.LoweredBevel;
            return b;
        }

        public static global::javax.swing.border.Border createRaisedBevelBorder()
        {
            var b = new global::javax.swing.border.Border(global::java.lang.RawNew.I);
            b.BKind = global::javax.swing.border.Border.Kind.RaisedBevel;
            return b;
        }

        public static global::javax.swing.border.Border createCompoundBorder(
            global::javax.swing.border.Border outside, global::javax.swing.border.Border inside)
        {
            var b = new global::javax.swing.border.Border(global::java.lang.RawNew.I);
            b.BKind = global::javax.swing.border.Border.Kind.Compound;
            b.Outer = outside;
            b.Inner = inside;
            return b;
        }
    }
}
