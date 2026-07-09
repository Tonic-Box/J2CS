namespace java.text
{
    public class MessageFormat : global::java.lang.Object
    {
        private string pattern;

        public MessageFormat(global::java.lang.RawNew r) : base(r) { pattern = ""; }

        public void __init_Ljava_lang_String__V(global::java.lang.String p) { pattern = p == null ? "" : p.Value; }

        public static global::java.lang.String format(global::java.lang.String pat, global::java.lang.Object[] args)
        {
            return global::java.lang.String.Wrap(Apply(pat == null ? "" : pat.Value, args));
        }

        public global::java.lang.String format(global::java.lang.Object[] args)
        {
            return global::java.lang.String.Wrap(Apply(pattern, args));
        }

        private static string Apply(string p, global::java.lang.Object[] args)
        {
            var sb = new global::System.Text.StringBuilder();
            int i = 0;
            while (i < p.Length)
            {
                char c = p[i];
                if (c == '{')
                {
                    int j = i + 1;
                    int idx = 0;
                    bool has = false;
                    while (j < p.Length && p[j] >= '0' && p[j] <= '9') { idx = idx * 10 + (p[j] - '0'); has = true; j++; }
                    while (j < p.Length && p[j] != '}') { j++; }
                    if (has && args != null && idx < args.Length) { sb.Append(global::java.lang.JRuntime.Str(args[idx])); }
                    i = j + 1;
                }
                else if (c == '\'' && i + 1 < p.Length && p[i + 1] == '\'')
                {
                    sb.Append('\'');
                    i += 2;
                }
                else
                {
                    sb.Append(c);
                    i++;
                }
            }
            return sb.ToString();
        }
    }
}
