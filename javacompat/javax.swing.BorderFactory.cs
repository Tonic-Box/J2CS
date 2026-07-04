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
    }
}
