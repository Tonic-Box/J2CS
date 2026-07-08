namespace java.io
{
    public class StringWriter : Writer
    {
        private readonly global::System.Text.StringBuilder sb = new global::System.Text.StringBuilder();

        public StringWriter(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_I_V(int initialSize)
        {
        }

        public override void write(int c)
        {
            sb.Append((char)c);
        }

        public override void write(char[] cbuf, int off, int len)
        {
            sb.Append(cbuf, off, len);
        }

        public override void write(global::java.lang.String str)
        {
            if (str != null)
            {
                sb.Append(str.Value);
            }
        }

        public override void flush()
        {
        }

        public override void close()
        {
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(sb.ToString());
        }
    }
}
