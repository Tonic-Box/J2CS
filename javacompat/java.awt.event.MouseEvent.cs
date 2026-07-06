namespace java.awt.@event
{
    public class MouseEvent : global::java.lang.Object
    {
        internal int X;
        internal int Y;
        internal int Button;
        internal int ClickCount;
        internal bool Shift;
        internal bool Control;
        internal bool Alt;
        internal bool Meta;

        public MouseEvent(global::java.lang.RawNew r) : base(r)
        {
        }

        public int getX() { return X; }
        public int getY() { return Y; }
        public int getButton() { return Button; }
        public int getClickCount() { return ClickCount; }
        public int isShiftDown() { return Shift ? 1 : 0; }
        public int isControlDown() { return Control ? 1 : 0; }
        public int isAltDown() { return Alt ? 1 : 0; }
        public int isMetaDown() { return Meta ? 1 : 0; }
        public global::java.awt.Point getPoint()
        {
            var p = new global::java.awt.Point(global::java.lang.RawNew.I);
            p.x = X;
            p.y = Y;
            return p;
        }
    }
}
