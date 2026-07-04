namespace java.awt
{
    public class Font : global::java.lang.Object
    {
        internal string Family = "Segoe UI";
        internal int Size = 12;
        internal int Style;

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
