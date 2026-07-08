namespace java.io
{
    public class StringReader : Reader
    {
        private string s;
        private int pos;

        public StringReader(global::java.lang.RawNew r) : base(r)
        {
            s = "";
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String str)
        {
            s = str == null ? "" : str.Value;
            pos = 0;
        }

        public override int read()
        {
            return pos < s.Length ? s[pos++] : -1;
        }

        public override int read(char[] cbuf, int off, int len)
        {
            if (pos >= s.Length)
            {
                return -1;
            }
            int n = global::System.Math.Min(len, s.Length - pos);
            for (int i = 0; i < n; i++)
            {
                cbuf[off + i] = s[pos++];
            }
            return n;
        }
    }
}
