namespace java.awt
{
    public class KeyboardFocusManager : global::java.lang.Object
    {
        private static readonly KeyboardFocusManager INSTANCE = new KeyboardFocusManager(global::java.lang.RawNew.I);

        public KeyboardFocusManager(global::java.lang.RawNew r) : base(r)
        {
        }

        public static KeyboardFocusManager getCurrentKeyboardFocusManager()
        {
            return INSTANCE;
        }

        public void addKeyEventDispatcher(KeyEventDispatcher dispatcher) { }
        public void removeKeyEventDispatcher(KeyEventDispatcher dispatcher) { }
    }
}
