namespace javax.swing.border
{
    public class Border : global::java.lang.Object
    {
        public enum Kind { Empty, Line, Etched, LoweredBevel, RaisedBevel, Titled, Compound }

        internal Kind BKind = Kind.Empty;
        internal int Top;
        internal int Left;
        internal int Bottom;
        internal int Right;
        internal global::java.awt.Color LineColor;
        internal Border Outer;
        internal Border Inner;

        public Border(global::java.lang.RawNew r) : base(r)
        {
        }
    }
}
