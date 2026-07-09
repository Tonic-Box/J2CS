namespace java.text
{
    public class NumberFormat : global::java.lang.Object
    {
        private static readonly global::System.Globalization.CultureInfo Inv = global::System.Globalization.CultureInfo.InvariantCulture;

        protected int maxFrac = 3;
        protected int minFrac = 0;
        protected bool grouping = true;

        public NumberFormat(global::java.lang.RawNew r) : base(r) { }
        internal NumberFormat() : base(global::java.lang.RawNew.I) { }

        public static NumberFormat getInstance() { return new NumberFormat(); }
        public static NumberFormat getInstance(global::java.util.Locale locale) { return new NumberFormat(); }
        public static NumberFormat getNumberInstance() { return new NumberFormat(); }

        public static NumberFormat getIntegerInstance()
        {
            var n = new NumberFormat();
            n.maxFrac = 0;
            return n;
        }

        private string Pattern()
        {
            string g = grouping ? "#,##0" : "0";
            if (maxFrac == 0) { return g; }
            var sb = new global::System.Text.StringBuilder(g).Append('.');
            for (int i = 0; i < minFrac; i++) { sb.Append('0'); }
            for (int i = minFrac; i < maxFrac; i++) { sb.Append('#'); }
            return sb.ToString();
        }

        public global::java.lang.String format(double v) { return global::java.lang.String.Wrap(v.ToString(Pattern(), Inv)); }
        public global::java.lang.String format(long v) { return global::java.lang.String.Wrap(v.ToString(Pattern(), Inv)); }

        public void setMaximumFractionDigits(int digits) { maxFrac = digits; }
        public void setMinimumFractionDigits(int digits) { minFrac = digits; }
        public void setGroupingUsed(int used) { grouping = used != 0; }
        public int isGroupingUsed() { return grouping ? 1 : 0; }
    }
}
