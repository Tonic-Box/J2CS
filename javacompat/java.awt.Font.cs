namespace java.awt
{
    public class Font : global::java.lang.Object
    {
        internal string Family = "Segoe UI";
        internal int Size = 12;
        internal int Style;

        public const int PLAIN = 0;
        public const int BOLD = 1;
        public const int ITALIC = 2;
        public static readonly global::java.lang.String MONOSPACED = global::java.lang.String.Wrap("Monospaced");
        public static readonly global::java.lang.String SANS_SERIF = global::java.lang.String.Wrap("SansSerif");
        public static readonly global::java.lang.String SERIF = global::java.lang.String.Wrap("Serif");
        public static readonly global::java.lang.String DIALOG = global::java.lang.String.Wrap("Dialog");

        public Font(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_lang_String_II_V(global::java.lang.String name, int style, int size)
        {
            Family = name == null ? "Segoe UI" : name.Value;
            Style = style;
            Size = size;
        }
    }
}
