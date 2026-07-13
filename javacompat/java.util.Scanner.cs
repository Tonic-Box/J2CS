namespace java.util
{
    public sealed class Scanner : global::java.lang.Object
    {
        private static readonly global::System.Globalization.CultureInfo Inv = global::System.Globalization.CultureInfo.InvariantCulture;

        private string content;
        private int pos;

        public Scanner(global::java.lang.RawNew r) : base(r) { content = ""; }

        public void __init_Ljava_lang_String__V(global::java.lang.String source)
        {
            content = source == null ? "" : source.Value;
        }

        public void __init_Ljava_io_InputStream__V(global::java.io.InputStream input)
        {
            content = new global::System.Text.UTF8Encoding(false).GetString(
                    global::java.lang.JRuntime.UnsignedBytes(input.readAllBytes()));
        }

        public void __init_Ljava_io_InputStream_Ljava_lang_String__V(
            global::java.io.InputStream input, global::java.lang.String charsetName)
        {
            byte[] bytes = global::java.lang.JRuntime.UnsignedBytes(input.readAllBytes());
            global::System.Text.Encoding encoding;
            try
            {
                encoding = charsetName == null
                    ? new global::System.Text.UTF8Encoding(false)
                    : global::System.Text.Encoding.GetEncoding(charsetName.Value);
            }
            catch
            {
                encoding = new global::System.Text.UTF8Encoding(false);
            }
            content = encoding.GetString(bytes);
        }

        private void SkipWs()
        {
            while (pos < content.Length && char.IsWhiteSpace(content[pos])) { pos++; }
        }

        private string ReadToken()
        {
            SkipWs();
            int start = pos;
            while (pos < content.Length && !char.IsWhiteSpace(content[pos])) { pos++; }
            return content.Substring(start, pos - start);
        }

        private string PeekToken()
        {
            int save = pos;
            string t = ReadToken();
            pos = save;
            return t;
        }

        public int hasNext() { return PeekToken().Length > 0 ? 1 : 0; }
        public global::java.lang.String next() { return global::java.lang.String.Wrap(ReadToken()); }

        public int hasNextInt()
        {
            string t = PeekToken();
            return t.Length > 0 && int.TryParse(t, global::System.Globalization.NumberStyles.Integer, Inv, out _) ? 1 : 0;
        }

        public int nextInt() { return int.Parse(ReadToken(), Inv); }

        public int hasNextLong()
        {
            string t = PeekToken();
            return t.Length > 0 && long.TryParse(t, global::System.Globalization.NumberStyles.Integer, Inv, out _) ? 1 : 0;
        }

        public long nextLong() { return long.Parse(ReadToken(), Inv); }

        public int hasNextDouble()
        {
            string t = PeekToken();
            return t.Length > 0 && double.TryParse(t, global::System.Globalization.NumberStyles.Float, Inv, out _) ? 1 : 0;
        }

        public double nextDouble() { return double.Parse(ReadToken(), Inv); }

        public int hasNextLine() { return pos < content.Length ? 1 : 0; }

        public global::java.lang.String nextLine()
        {
            int nl = content.IndexOf('\n', pos);
            string line;
            if (nl < 0)
            {
                line = content.Substring(pos);
                pos = content.Length;
            }
            else
            {
                line = content.Substring(pos, nl - pos);
                pos = nl + 1;
            }
            if (line.EndsWith("\r"))
            {
                line = line.Substring(0, line.Length - 1);
            }
            return global::java.lang.String.Wrap(line);
        }

        public void close() { }

        // Parsing already uses invariant culture, so pinning a locale is a no-op that keeps the chain.
        public Scanner useLocale(global::java.util.Locale locale) { return this; }
    }
}
