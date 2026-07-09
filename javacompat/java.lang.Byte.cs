namespace java.lang
{
    public sealed class Byte : Number
    {
        public static readonly global::java.lang.Class TYPE = global::java.lang.Class.Of("byte");
        public const sbyte MAX_VALUE = sbyte.MaxValue;
        public const sbyte MIN_VALUE = sbyte.MinValue;
        public const int SIZE = 8;
        public const int BYTES = 1;

        private static readonly Byte[] Cache = BuildCache();

        private sbyte value;

        public Byte(RawNew r) : base(r)
        {
        }

        public void __init_B_V(sbyte v)
        {
            value = v;
        }

        private Byte(sbyte v) : base(RawNew.I)
        {
            value = v;
        }

        private static Byte[] BuildCache()
        {
            var cache = new Byte[256];
            for (int i = 0; i < 256; i++)
            {
                cache[i] = new Byte((sbyte)(i - 128));
            }
            return cache;
        }

        public static Byte valueOf(sbyte v)
        {
            return Cache[v + 128];
        }

        public static Byte valueOf(String s)
        {
            return valueOf(parseByte(s));
        }

        public static Byte valueOf(String s, int radix)
        {
            return valueOf(parseByte(s, radix));
        }

        public static sbyte parseByte(String s)
        {
            return parseByte(s, 10);
        }

        public static sbyte parseByte(String s, int radix)
        {
            int i = Integer.parseInt(s, radix);
            if (i < sbyte.MinValue || i > sbyte.MaxValue)
            {
                throw JRuntime.NumberFormat("Value out of range. Value:\"" + (s == null ? "null" : s.Value) + "\" Radix:" + radix);
            }
            return (sbyte)i;
        }

        public static int compare(sbyte a, sbyte b) { return a - b; }
        public static int hashCode(sbyte v) { return v; }
        public static int toUnsignedInt(sbyte v) { return v & 0xff; }
        public static long toUnsignedLong(sbyte v) { return v & 0xffL; }

        public static String toString(sbyte v)
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

        public int compareTo(Byte other)
        {
            return value - other.value;
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Byte other && other.value == value ? 1 : 0;
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
