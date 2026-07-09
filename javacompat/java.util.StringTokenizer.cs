namespace java.util
{
    public sealed class StringTokenizer : global::java.lang.Object
    {
        private string[] tokens;
        private int idx;

        public StringTokenizer(global::java.lang.RawNew r) : base(r) { tokens = new string[0]; }

        public void __init_Ljava_lang_String__V(global::java.lang.String str)
        {
            Init(str == null ? "" : str.Value, " \t\n\r\f");
        }

        public void __init_Ljava_lang_String_Ljava_lang_String__V(global::java.lang.String str, global::java.lang.String delim)
        {
            Init(str == null ? "" : str.Value, delim == null ? "" : delim.Value);
        }

        private void Init(string s, string delim)
        {
            tokens = s.Split(delim.ToCharArray(), global::System.StringSplitOptions.RemoveEmptyEntries);
            idx = 0;
        }

        public int countTokens() { return tokens.Length - idx; }
        public int hasMoreTokens() { return idx < tokens.Length ? 1 : 0; }
        public int hasMoreElements() { return idx < tokens.Length ? 1 : 0; }
        public global::java.lang.String nextToken() { return global::java.lang.String.Wrap(tokens[idx++]); }
        public global::java.lang.Object nextElement() { return global::java.lang.String.Wrap(tokens[idx++]); }
    }
}
