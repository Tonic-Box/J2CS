namespace java.util.regex
{
    public sealed class Matcher : global::java.lang.Object
    {
        private global::System.Text.RegularExpressions.Regex re;
        private string input;
        private global::System.Text.RegularExpressions.Match cur;
        private int nextAt;

        public Matcher(global::java.lang.RawNew r) : base(r)
        {
        }

        internal static Matcher Create(Pattern p, string input)
        {
            var m = new Matcher(global::java.lang.RawNew.I);
            m.re = p.Re;
            m.input = input;
            m.nextAt = 0;
            return m;
        }

        public int matches()
        {
            var anchored = new global::System.Text.RegularExpressions.Regex(
                    "\\A(?:" + re.ToString() + ")\\z", re.Options);
            cur = anchored.Match(input);
            return cur.Success ? 1 : 0;
        }

        public int lookingAt()
        {
            var mm = re.Match(input);
            if (mm.Success && mm.Index == 0)
            {
                cur = mm;
                return 1;
            }
            return 0;
        }

        public int find()
        {
            if (nextAt > input.Length)
            {
                return 0;
            }
            cur = re.Match(input, nextAt);
            if (cur.Success)
            {
                nextAt = cur.Index + (cur.Length == 0 ? 1 : cur.Length);
                return 1;
            }
            return 0;
        }

        public int find(int start)
        {
            nextAt = start;
            return find();
        }

        public global::java.lang.String group()
        {
            return global::java.lang.String.Wrap(cur.Value);
        }

        public global::java.lang.String group(int g)
        {
            var grp = cur.Groups[g];
            return grp.Success ? global::java.lang.String.Wrap(grp.Value) : null;
        }

        public int groupCount()
        {
            return re.GetGroupNumbers().Length - 1;
        }

        public int start()
        {
            return cur.Index;
        }

        public int start(int g)
        {
            return cur.Groups[g].Index;
        }

        public int end()
        {
            return cur.Index + cur.Length;
        }

        public int end(int g)
        {
            var grp = cur.Groups[g];
            return grp.Index + grp.Length;
        }

        public global::java.lang.String replaceAll(global::java.lang.String replacement)
        {
            return global::java.lang.String.Wrap(re.Replace(input, replacement.Value));
        }

        public global::java.lang.String replaceFirst(global::java.lang.String replacement)
        {
            return global::java.lang.String.Wrap(re.Replace(input, replacement.Value, 1));
        }

        public Matcher reset()
        {
            nextAt = 0;
            cur = null;
            return this;
        }

        public Matcher reset(global::java.lang.CharSequence in2)
        {
            input = in2 == null ? "" : in2.toString().Value;
            nextAt = 0;
            cur = null;
            return this;
        }
    }
}
