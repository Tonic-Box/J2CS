namespace java.lang
{
    partial class Integer
    {
        private static readonly Integer[] __cache = __buildCache();

        private static Integer[] __buildCache()
        {
            var c = new Integer[256];
            for (int i = 0; i < 256; i++)
            {
                var x = new Integer(RawNew.I);
                x.__init_I_V(i - 128);
                c[i] = x;
            }
            return c;
        }

        public static Integer valueOf(int v)
        {
            if (v >= -128 && v <= 127)
            {
                return __cache[v + 128];
            }
            var x = new Integer(RawNew.I);
            x.__init_I_V(v);
            return x;
        }

        public static int parseInt(String s)
        {
            if (s == null)
            {
                throw JRuntime.NumberFormat("Cannot parse null string");
            }
            int r;
            if (!int.TryParse(s.Value, global::System.Globalization.NumberStyles.AllowLeadingSign,
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
