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
            border.Top = top;
            border.Left = left;
            border.Bottom = bottom;
            border.Right = right;
            return border;
        }

        public static global::javax.swing.border.TitledBorder createTitledBorder(global::java.lang.String title)
        {
            var b = new global::javax.swing.border.TitledBorder(global::java.lang.RawNew.I);
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
            return new global::javax.swing.border.Border(global::java.lang.RawNew.I);
        }

        public static global::javax.swing.border.Border createLineBorder(global::java.awt.Color color)
        {
            return new global::javax.swing.border.Border(global::java.lang.RawNew.I);
        }

        public static global::javax.swing.border.Border createEtchedBorder()
        {
            return new global::javax.swing.border.Border(global::java.lang.RawNew.I);
        }

        public static global::javax.swing.border.Border createLoweredBevelBorder()
        {
            return new global::javax.swing.border.Border(global::java.lang.RawNew.I);
        }

        public static global::javax.swing.border.Border createRaisedBevelBorder()
        {
            return new global::javax.swing.border.Border(global::java.lang.RawNew.I);
        }

        public static global::javax.swing.border.Border createCompoundBorder(
            global::javax.swing.border.Border outside, global::javax.swing.border.Border inside)
        {
            return new global::javax.swing.border.Border(global::java.lang.RawNew.I);
        }
    }
}
