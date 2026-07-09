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

        public static Long valueOf(String s)
        {
            return valueOf(parseLong(s));
        }

        public static Long valueOf(String s, int radix)
        {
            return valueOf(parseLong(s, radix));
        }

        private static int DigitOf(char c, int radix)
        {
            int d = -1;
            if (c >= '0' && c <= '9') { d = c - '0'; }
            else if (c >= 'a' && c <= 'z') { d = c - 'a' + 10; }
            else if (c >= 'A' && c <= 'Z') { d = c - 'A' + 10; }
            return d < radix ? d : -1;
        }

        private static char DigitChar(int d)
        {
            return (char)(d < 10 ? '0' + d : 'a' + d - 10);
        }

        public static long parseLong(String s)
        {
            return parseLong(s, 10);
        }

        public static long parseLong(String s, int radix)
        {
            if (s == null)
            {
                throw JRuntime.NumberFormat("Cannot parse null string");
            }
            if (radix < 2 || radix > 36)
            {
                throw JRuntime.NumberFormat("radix " + radix + " out of range");
            }
            string str = s.Value;
            int len = str.Length;
            if (len == 0)
            {
                throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
            }
            int i = 0;
            bool neg = false;
            char first = str[0];
            if (first == '-') { neg = true; i = 1; }
            else if (first == '+') { i = 1; }
            if (i == len)
            {
                throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
            }
            ulong limit = neg ? 9223372036854775808UL : 9223372036854775807UL;
            ulong acc = 0;
            for (; i < len; i++)
            {
                int d = DigitOf(str[i], radix);
                if (d < 0)
                {
                    throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
                }
                if (acc > (limit - (ulong)d) / (ulong)radix)
                {
                    throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
                }
                acc = acc * (ulong)radix + (ulong)d;
            }
            return neg ? unchecked((long)(0UL - acc)) : (long)acc;
        }

        public static long parseUnsignedLong(String s)
        {
            return parseUnsignedLong(s, 10);
        }

        public static long parseUnsignedLong(String s, int radix)
        {
            if (s == null)
            {
                throw JRuntime.NumberFormat("Cannot parse null string");
            }
            if (radix < 2 || radix > 36)
            {
                throw JRuntime.NumberFormat("radix " + radix + " out of range");
            }
            string str = s.Value;
            int len = str.Length;
            if (len == 0)
            {
                throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
            }
            int i = 0;
            if (str[0] == '+') { i = 1; }
            if (i == len)
            {
                throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
            }
            ulong acc = 0;
            for (; i < len; i++)
            {
                int d = DigitOf(str[i], radix);
                if (d < 0)
                {
                    throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
                }
                if (acc > (ulong.MaxValue - (ulong)d) / (ulong)radix)
                {
                    throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
                }
                acc = acc * (ulong)radix + (ulong)d;
            }
            return unchecked((long)acc);
        }

        public static long sum(long a, long b) { return a + b; }
        public static long max(long a, long b) { return a > b ? a : b; }
        public static long min(long a, long b) { return a < b ? a : b; }
        public static int compare(long a, long b) { return a < b ? -1 : (a > b ? 1 : 0); }

        public static int compareUnsigned(long a, long b)
        {
            ulong ua = (ulong)a;
            ulong ub = (ulong)b;
            return ua < ub ? -1 : (ua > ub ? 1 : 0);
        }

        public static long divideUnsigned(long dividend, long divisor)
        {
            return unchecked((long)((ulong)dividend / (ulong)divisor));
        }

        public static long remainderUnsigned(long dividend, long divisor)
        {
            return unchecked((long)((ulong)dividend % (ulong)divisor));
        }

        public static int hashCode(long v)
        {
            return (int)(v ^ (long)((ulong)v >> 32));
        }

        public static String toString(long v)
        {
            return String.Wrap(JRuntime.Str(v));
        }

        public static String toString(long v, int radix)
        {
            if (radix < 2 || radix > 36)
            {
                radix = 10;
            }
            if (radix == 10)
            {
                return String.Wrap(JRuntime.Str(v));
            }
            bool neg = v < 0;
            ulong u = neg ? (0UL - (ulong)v) : (ulong)v;
            var sb = new global::System.Text.StringBuilder();
            if (u == 0) { sb.Append('0'); }
            while (u > 0)
            {
                sb.Insert(0, DigitChar((int)(u % (ulong)radix)));
                u /= (ulong)radix;
            }
            if (neg) { sb.Insert(0, '-'); }
            return String.Wrap(sb.ToString());
        }

        private static String UnsignedToString(ulong u, int radix)
        {
            if (u == 0) { return String.Wrap("0"); }
            var sb = new global::System.Text.StringBuilder();
            while (u > 0)
            {
                sb.Insert(0, DigitChar((int)(u % (ulong)radix)));
                u /= (ulong)radix;
            }
            return String.Wrap(sb.ToString());
        }

        public static String toBinaryString(long v) { return UnsignedToString((ulong)v, 2); }
        public static String toOctalString(long v) { return UnsignedToString((ulong)v, 8); }
        public static String toHexString(long v) { return UnsignedToString((ulong)v, 16); }

        public static String toUnsignedString(long v) { return UnsignedToString((ulong)v, 10); }

        public static String toUnsignedString(long v, int radix)
        {
            if (radix < 2 || radix > 36) { radix = 10; }
            return UnsignedToString((ulong)v, radix);
        }

        public static int bitCount(long v)
        {
            return global::System.Numerics.BitOperations.PopCount((ulong)v);
        }

        public static int numberOfLeadingZeros(long v)
        {
            return global::System.Numerics.BitOperations.LeadingZeroCount((ulong)v);
        }

        public static int numberOfTrailingZeros(long v)
        {
            return v == 0 ? 64 : global::System.Numerics.BitOperations.TrailingZeroCount((ulong)v);
        }

        public static long highestOneBit(long v)
        {
            return v == 0
                    ? 0
                    : unchecked((long)(1UL << (63 - global::System.Numerics.BitOperations.LeadingZeroCount((ulong)v))));
        }

        public static long lowestOneBit(long v)
        {
            return v & (-v);
        }

        public static long reverse(long v)
        {
            ulong x = (ulong)v;
            x = ((x & 0x5555555555555555UL) << 1) | ((x >> 1) & 0x5555555555555555UL);
            x = ((x & 0x3333333333333333UL) << 2) | ((x >> 2) & 0x3333333333333333UL);
            x = ((x & 0x0f0f0f0f0f0f0f0fUL) << 4) | ((x >> 4) & 0x0f0f0f0f0f0f0f0fUL);
            x = global::System.Buffers.Binary.BinaryPrimitives.ReverseEndianness(x);
            return unchecked((long)x);
        }

        public static long reverseBytes(long v)
        {
            return global::System.Buffers.Binary.BinaryPrimitives.ReverseEndianness(v);
        }

        public static long rotateLeft(long v, int distance)
        {
            return unchecked((long)global::System.Numerics.BitOperations.RotateLeft((ulong)v, distance));
        }

        public static long rotateRight(long v, int distance)
        {
            return unchecked((long)global::System.Numerics.BitOperations.RotateRight((ulong)v, distance));
        }

        public static int signum(long v)
        {
            return v < 0 ? -1 : (v > 0 ? 1 : 0);
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
