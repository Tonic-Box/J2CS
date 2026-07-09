namespace java.lang
{
    public sealed class Short : Number
    {
        public static readonly global::java.lang.Class TYPE = global::java.lang.Class.Of("short");
        public const short MAX_VALUE = short.MaxValue;
        public const short MIN_VALUE = short.MinValue;
        public const int SIZE = 16;
        public const int BYTES = 2;

        private static readonly Short[] Cache = BuildCache();

        private short value;

        public Short(RawNew r) : base(r)
        {
        }

        public void __init_S_V(short v)
        {
            value = v;
        }

        private Short(short v) : base(RawNew.I)
        {
            value = v;
        }

        private static Short[] BuildCache()
        {
            var cache = new Short[256];
            for (int i = 0; i < 256; i++)
            {
                cache[i] = new Short((short)(i - 128));
            }
            return cache;
        }

        public static Short valueOf(short v)
        {
            if (v >= -128 && v <= 127)
            {
                return Cache[v + 128];
            }
            return new Short(v);
        }

        public static Short valueOf(String s)
        {
            return valueOf(parseShort(s));
        }

        public static Short valueOf(String s, int radix)
        {
            return valueOf(parseShort(s, radix));
        }

        public static short parseShort(String s)
        {
            return parseShort(s, 10);
        }

        public static short parseShort(String s, int radix)
        {
            int i = Integer.parseInt(s, radix);
            if (i < short.MinValue || i > short.MaxValue)
            {
                throw JRuntime.NumberFormat("Value out of range. Value:\"" + (s == null ? "null" : s.Value) + "\" Radix:" + radix);
            }
            return (short)i;
        }

        public static int compare(short a, short b) { return a - b; }
        public static int hashCode(short v) { return v; }
        public static int toUnsignedInt(short v) { return v & 0xffff; }
        public static long toUnsignedLong(short v) { return v & 0xffffL; }
        public static short reverseBytes(short v)
        {
            return global::System.Buffers.Binary.BinaryPrimitives.ReverseEndianness(v);
        }

        public static String toString(short v)
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

        public int compareTo(Short other)
        {
            return value - other.value;
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Short other && other.value == value ? 1 : 0;
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
