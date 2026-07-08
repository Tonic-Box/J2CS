namespace java.lang
{
    public sealed class Long : Number
    {
        public static readonly global::java.lang.Class TYPE = global::java.lang.Class.Of("long");
        public const long MAX_VALUE = long.MaxValue;
        public const long MIN_VALUE = long.MinValue;
        public const int SIZE = 64;
        public const int BYTES = 8;

        private static readonly Long[] Cache = BuildCache();

        private long value;

        public Long(RawNew r) : base(r)
        {
        }

        public void __init_J_V(long v)
        {
            value = v;
        }

        private Long(long v) : base(RawNew.I)
        {
            value = v;
        }

        private static Long[] BuildCache()
        {
            var cache = new Long[256];
            for (int i = 0; i < 256; i++)
            {
                cache[i] = new Long(i - 128);
            }
            return cache;
        }

        public static Long valueOf(long v)
        {
            if (v >= -128 && v <= 127)
            {
                return Cache[(int)v + 128];
            }
            return new Long(v);
        }

        public static long sum(long a, long b) { return a + b; }
        public static long max(long a, long b) { return a > b ? a : b; }
        public static long min(long a, long b) { return a < b ? a : b; }

        public static long parseLong(String s)
        {
            if (s == null)
            {
                throw JRuntime.NumberFormat("Cannot parse null string");
            }
            long result;
            bool ok = long.TryParse(s.Value,
                    global::System.Globalization.NumberStyles.AllowLeadingSign,
                    global::System.Globalization.CultureInfo.InvariantCulture,
                    out result);
            if (!ok)
            {
                throw JRuntime.NumberFormat("For input string: \"" + s.Value + "\"");
            }
            return result;
        }

        public static String toString(long v)
        {
            return String.Wrap(JRuntime.Str(v));
        }

        public override int intValue()
        {
            return (int)value;
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

        public int compareTo(Long other)
        {
            return value < other.value ? -1 : value > other.value ? 1 : 0;
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Long other && other.value == value ? 1 : 0;
        }

        public override int hashCode()
        {
            return (int)(value ^ (long)((ulong)value >> 32));
        }

        public override String toString()
        {
            return String.Wrap(JRuntime.Str(value));
        }
    }
}
