namespace java.util.regex
{
    /// <summary>
    /// Wraps System.Text.RegularExpressions.Regex. Java and .NET regex dialects agree on the
    /// common syntax (groups, quantifiers, classes, backrefs); a few edge constructs differ.
    /// </summary>
    public sealed class Pattern : global::java.lang.Object
    {
        public const int UNIX_LINES = 0x01;
        public const int CASE_INSENSITIVE = 0x02;
        public const int COMMENTS = 0x04;
        public const int MULTILINE = 0x08;
        public const int LITERAL = 0x10;
        public const int DOTALL = 0x20;
        public const int UNICODE_CASE = 0x40;

        internal readonly global::System.Text.RegularExpressions.Regex Re;
        private readonly global::java.lang.String pat;

        public Pattern(global::java.lang.RawNew r) : base(r)
        {
        }

        private Pattern(global::java.lang.String p, global::System.Text.RegularExpressions.Regex re) : base(global::java.lang.RawNew.I)
        {
            pat = p;
            Re = re;
        }

        private static global::System.Text.RegularExpressions.RegexOptions MapFlags(int flags)
        {
            var o = global::System.Text.RegularExpressions.RegexOptions.None;
            if ((flags & CASE_INSENSITIVE) != 0) { o |= global::System.Text.RegularExpressions.RegexOptions.IgnoreCase; }
            if ((flags & MULTILINE) != 0) { o |= global::System.Text.RegularExpressions.RegexOptions.Multiline; }
            if ((flags & DOTALL) != 0) { o |= global::System.Text.RegularExpressions.RegexOptions.Singleline; }
            if ((flags & COMMENTS) != 0) { o |= global::System.Text.RegularExpressions.RegexOptions.IgnorePatternWhitespace; }
            return o;
        }

        public static Pattern compile(global::java.lang.String regex)
        {
            return new Pattern(regex, new global::System.Text.RegularExpressions.Regex(regex.Value));
        }

        public static Pattern compile(global::java.lang.String regex, int flags)
        {
            return new Pattern(regex, new global::System.Text.RegularExpressions.Regex(regex.Value, MapFlags(flags)));
        }

        public Matcher matcher(global::java.lang.CharSequence input)
        {
            return Matcher.Create(this, input == null ? "" : input.toString().Value);
        }

        public global::java.lang.String pattern()
        {
            return pat;
        }

        public override global::java.lang.String toString()
        {
            return pat;
        }

        public static int matches(global::java.lang.String regex, global::java.lang.CharSequence input)
        {
            return compile(regex).matcher(input).matches();
        }

        public static global::java.lang.String quote(global::java.lang.String s)
        {
            return global::java.lang.String.Wrap(global::System.Text.RegularExpressions.Regex.Escape(s.Value));
        }

        public global::java.lang.String[] split(global::java.lang.CharSequence input)
        {
            string s = input == null ? "" : input.toString().Value;
            if (!Re.IsMatch(s))
            {
                return new global::java.lang.String[] { global::java.lang.String.Wrap(s) };
            }
            string[] parts = Re.Split(s);
            int n = parts.Length;
            while (n > 0 && parts[n - 1].Length == 0)
            {
                n--;
            }
            var result = new global::java.lang.String[n];
            for (int i = 0; i < n; i++)
            {
                result[i] = global::java.lang.String.Wrap(parts[i]);
            }
            return result;
        }
    }
}
