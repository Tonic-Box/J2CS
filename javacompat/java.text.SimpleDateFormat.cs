namespace java.text
{
    public class SimpleDateFormat : global::java.lang.Object
    {
        private static readonly global::System.Globalization.CultureInfo Inv = global::System.Globalization.CultureInfo.InvariantCulture;

        private string pattern;
        private global::java.util.TimeZone tz;

        public SimpleDateFormat(global::java.lang.RawNew r) : base(r)
        {
            pattern = "yyyy-MM-dd";
            tz = global::java.util.TimeZone.getDefault();
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String p)
        {
            pattern = MapPattern(p == null ? "" : p.Value);
            tz = global::java.util.TimeZone.getDefault();
        }

        public void __init_Ljava_lang_String_Ljava_util_Locale__V(global::java.lang.String p, global::java.util.Locale locale)
        {
            pattern = MapPattern(p == null ? "" : p.Value);
            tz = global::java.util.TimeZone.getDefault();
        }

        public void setTimeZone(global::java.util.TimeZone zone) { tz = zone; }

        public global::java.lang.String format(global::java.util.Date d)
        {
            return global::java.lang.String.Wrap(tz.ToLocal(d.getTime()).ToString(pattern, Inv));
        }

        public global::java.util.Date parse(global::java.lang.String source)
        {
            var local = global::System.DateTime.ParseExact(source.Value, pattern, Inv,
                    global::System.Globalization.DateTimeStyles.None);
            var d = new global::java.util.Date(global::java.lang.RawNew.I);
            d.__init_J_V(tz.ToMillis(local));
            return d;
        }

        public global::java.lang.String toPattern() { return global::java.lang.String.Wrap(pattern); }

        private static string MapPattern(string p)
        {
            var sb = new global::System.Text.StringBuilder();
            int i = 0;
            while (i < p.Length)
            {
                char c = p[i];
                if (c == '\'')
                {
                    sb.Append('\'');
                    i++;
                    while (i < p.Length && p[i] != '\'') { sb.Append(p[i]); i++; }
                    if (i < p.Length) { sb.Append('\''); i++; }
                    continue;
                }
                if (char.IsLetter(c))
                {
                    int j = i;
                    while (j < p.Length && p[j] == c) { j++; }
                    int len = j - i;
                    AppendToken(sb, c, len);
                    i = j;
                }
                else
                {
                    sb.Append(c);
                    i++;
                }
            }
            return sb.ToString();
        }

        private static void AppendToken(global::System.Text.StringBuilder sb, char c, int len)
        {
            switch (c)
            {
                case 'y':
                case 'M':
                case 'd':
                case 'H':
                case 'h':
                case 'm':
                case 's':
                    sb.Append(new string(c, len));
                    break;
                case 'E':
                    sb.Append(len <= 3 ? "ddd" : "dddd");
                    break;
                case 'a':
                    sb.Append("tt");
                    break;
                case 'S':
                    sb.Append(new string('f', len > 7 ? 7 : len));
                    break;
                default:
                    break;
            }
        }
    }
}
