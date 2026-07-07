namespace java.awt.@event
{
    public class KeyEvent : global::java.lang.Object
    {
        public const int KEY_TYPED = 400;
        public const int KEY_PRESSED = 401;
        public const int KEY_RELEASED = 402;

        internal int KeyCode;
        internal int Id;
        internal char KeyChar = '￿';
        internal bool Shift;
        internal bool Control;
        internal bool Alt;
        internal bool Meta;

        public KeyEvent(global::java.lang.RawNew r) : base(r)
        {
        }

        public int getKeyCode()
        {
            return KeyCode;
        }

        public int getID()
        {
            return Id;
        }

        public char getKeyChar()
        {
            return KeyChar;
        }

        public int isShiftDown()
        {
            return Shift ? 1 : 0;
        }

        public int isControlDown()
        {
            return Control ? 1 : 0;
        }

        public int isAltDown()
        {
            return Alt ? 1 : 0;
        }

        public int isMetaDown()
        {
            return Meta ? 1 : 0;
        }
    }
}
