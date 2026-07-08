namespace java.io
{
    public class InputStreamReader : Reader
    {
        private string content;
        private int pos;

        public InputStreamReader(global::java.lang.RawNew r) : base(r)
        {
            content = "";
        }

        public void __init_Ljava_io_InputStream__V(InputStream input)
        {
            Decode(input, new global::System.Text.UTF8Encoding(false));
        }

        public void __init_Ljava_io_InputStream_Ljava_nio_charset_Charset__V(InputStream input,
                global::java.nio.charset.Charset cs)
        {
            Decode(input, cs == null ? new global::System.Text.UTF8Encoding(false) : cs.encoding);
        }

        private void Decode(InputStream input, global::System.Text.Encoding enc)
        {
            content = enc.GetString(global::java.lang.JRuntime.UnsignedBytes(input.readAllBytes()));
            pos = 0;
        }

        public override int read()
        {
            return pos < content.Length ? content[pos++] : -1;
        }

        public override int read(char[] cbuf, int off, int len)
        {
            if (pos >= content.Length)
            {
                return -1;
            }
            int n = global::System.Math.Min(len, content.Length - pos);
            for (int i = 0; i < n; i++)
            {
                cbuf[off + i] = content[pos++];
            }
            return n;
        }
    }
}
