namespace java.io
{
    public class OutputStreamWriter : Writer
    {
        private OutputStream outStream;
        private global::System.Text.Encoding enc;
        private readonly global::System.Text.StringBuilder sb = new global::System.Text.StringBuilder();

        public OutputStreamWriter(global::java.lang.RawNew r) : base(r)
        {
            enc = new global::System.Text.UTF8Encoding(false);
        }

        internal OutputStreamWriter(OutputStream target) : base(global::java.lang.RawNew.I)
        {
            outStream = target;
            enc = new global::System.Text.UTF8Encoding(false);
        }

        public void __init_Ljava_io_OutputStream__V(OutputStream target)
        {
            outStream = target;
            enc = new global::System.Text.UTF8Encoding(false);
        }

        public void __init_Ljava_io_OutputStream_Ljava_nio_charset_Charset__V(OutputStream target,
                global::java.nio.charset.Charset cs)
        {
            outStream = target;
            enc = cs == null ? new global::System.Text.UTF8Encoding(false) : cs.encoding;
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
            if (sb.Length > 0)
            {
                outStream.write(global::java.lang.JRuntime.SignedBytes(enc.GetBytes(sb.ToString())));
                sb.Clear();
            }
            outStream.flush();
        }

        public override void close()
        {
            flush();
            outStream.close();
        }
    }
}
