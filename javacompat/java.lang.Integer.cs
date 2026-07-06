namespace java.lang
{
    public sealed class Integer : Number
    {
        public static readonly global::java.lang.Class TYPE = global::java.lang.Class.Of("int");

        private static readonly Integer[] Cache = BuildCache();

        private readonly int value;

        public Integer(RawNew r) : base(r)
        {
        }

        private Integer(int v) : base(RawNew.I)
        {
            value = v;
        }

        private static Integer[] BuildCache()
        {
            var cache = new Integer[256];
            for (int i = 0; i < 256; i++)
            {
                cache[i] = new Integer(i - 128);
            }
            return cache;
        }

        public static Integer valueOf(int v)
        {
            if (v >= -128 && v <= 127)
            {
                return Cache[v + 128];
            }
            return new Integer(v);
        }

        public static int parseInt(String s)
        {
            if (s == null)
            {
                throw JRuntime.NumberFormat("Cannot parse null string");
            }
            int result;
            bool ok = int.TryParse(s.Value,
                    global::System.Globalization.NumberStyles.AllowLeadingSign,
                    global::System.Globalization.CultureInfo.InvariantCulture,
                    out result);
            if (!ok)
            {
                throw JRuntime.NumberFormat("For input string: \"" + s.Value + "\"");
            }
            return result;
        }

        public static int sum(int a, int b) { return a + b; }
        public static int max(int a, int b) { return a > b ? a : b; }
        public static int min(int a, int b) { return a < b ? a : b; }
        public static int compare(int a, int b) { return a < b ? -1 : (a > b ? 1 : 0); }

        public static String toString(int v)
        {
            return String.Wrap(JRuntime.Str(v));
        }

        public override int intValue()
        {
            return value;
        }

        public override long longValue()
        {
            return value;
        }

        public override float floatValue()
        {
            return value;
        }

        public override double doubleValue()
        {
            return value;
        }

        public int compareTo(Integer other)
        {
            return value < other.value ? -1 : value > other.value ? 1 : 0;
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Integer other && other.value == value ? 1 : 0;
        }

        public override int hashCode()
        {
            return value;
        }

        public override String toString()
        {
            return String.Wrap(JRuntime.Str(value));
        }
    }
}
