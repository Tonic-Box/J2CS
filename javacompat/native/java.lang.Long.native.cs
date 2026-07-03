namespace java.lang
{
    partial class Long
    {
        private static readonly Long[] __cache = __buildCache();

        private static Long[] __buildCache()
        {
            var c = new Long[256];
            for (int i = 0; i < 256; i++)
            {
                var x = new Long(RawNew.I);
                x.__init_J_V(i - 128);
                c[i] = x;
            }
            return c;
        }

        public static Long valueOf(long v)
        {
            if (v >= -128 && v <= 127)
            {
                return __cache[(int)v + 128];
            }
            var x = new Long(RawNew.I);
            x.__init_J_V(v);
            return x;
        }

        public static long parseLong(String s)
        {
            if (s == null)
            {
                throw JRuntime.NumberFormat("Cannot parse null string");
            }
            long r;
            if (!long.TryParse(s.Value, global::System.Globalization.NumberStyles.AllowLeadingSign,
                    global::System.Globalization.CultureInfo.InvariantCulture, out r))
            {
                throw JRuntime.NumberFormat("For input string: \"" + s.Value + "\"");
            }
            return r;
        }

        public override String toString()
        {
            return String.Wrap(JRuntime.Str(value));
        }
    }
}
