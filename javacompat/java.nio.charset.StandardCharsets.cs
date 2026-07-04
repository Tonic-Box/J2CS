namespace java.nio.charset
{
    public class StandardCharsets : global::java.lang.Object
    {
        public static readonly Charset UTF_8 = new Charset(new global::System.Text.UTF8Encoding(false));
        public static readonly Charset US_ASCII = new Charset(global::System.Text.Encoding.ASCII);
        public static readonly Charset ISO_8859_1 = new Charset(global::System.Text.Encoding.Latin1);

        private StandardCharsets(global::java.lang.RawNew r) : base(r)
        {
        }
    }
}
