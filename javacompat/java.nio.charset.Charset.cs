namespace java.nio.charset
{
    public class Charset : global::java.lang.Object
    {
        internal readonly global::System.Text.Encoding encoding;

        public Charset(global::java.lang.RawNew r) : base(r)
        {
            encoding = global::System.Text.Encoding.UTF8;
        }

        internal Charset(global::System.Text.Encoding encoding) : base(global::java.lang.RawNew.I)
        {
            this.encoding = encoding;
        }
    }
}
