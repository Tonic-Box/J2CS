namespace java.lang
{
    public sealed class Integer : Number
    {
        public static readonly global::java.lang.Class TYPE = global::java.lang.Class.Of("int");
        public const int MAX_VALUE = int.MaxValue;
        public const int MIN_VALUE = int.MinValue;
        public const int SIZE = 32;
        public const int BYTES = 4;

        private static readonly Integer[] Cache = BuildCache();

        private int value;

        public Integer(RawNew r) : base(r)
        {
        }

        public void __init_I_V(int v)
        {
            value = v;
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

        public static Integer valueOf(String s)
        {
            return valueOf(parseInt(s));
        }

        public static Integer valueOf(String s, int radix)
        {
            return valueOf(parseInt(s, radix));
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

        public static int parseInt(String s)
        {
            return parseInt(s, 10);
        }

        public static int parseInt(String s, int radix)
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
            long acc = 0;
            for (; i < len; i++)
            {
                int d = DigitOf(str[i], radix);
                if (d < 0)
                {
                    throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
                }
                acc = acc * radix + d;
                if (acc > 2147483648L)
                {
                    throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
                }
            }
            if (neg)
            {
                if (acc > 2147483648L)
                {
                    throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
                }
                return unchecked((int)(-acc));
            }
            if (acc > 2147483647L)
            {
                throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
            }
            return (int)acc;
        }

        public static int parseUnsignedInt(String s)
        {
            return parseUnsignedInt(s, 10);
        }

        public static int parseUnsignedInt(String s, int radix)
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
            long acc = 0;
            for (; i < len; i++)
            {
                int d = DigitOf(str[i], radix);
                if (d < 0)
                {
                    throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
                }
                acc = acc * radix + d;
                if (acc > 4294967295L)
                {
                    throw JRuntime.NumberFormat("For input string: \"" + str + "\"");
                }
            }
            return unchecked((int)acc);
        }

        public static int sum(int a, int b) { return a + b; }
        public static int max(int a, int b) { return a > b ? a : b; }
        public static int min(int a, int b) { return a < b ? a : b; }
        public static int compare(int a, int b) { return a < b ? -1 : (a > b ? 1 : 0); }

        public static int compareUnsigned(int a, int b)
        {
            uint ua = (uint)a;
            uint ub = (uint)b;
            return ua < ub ? -1 : (ua > ub ? 1 : 0);
        }

        public static int divideUnsigned(int dividend, int divisor)
        {
            return unchecked((int)((uint)dividend / (uint)divisor));
        }

        public static int remainderUnsigned(int dividend, int divisor)
        {
            return unchecked((int)((uint)dividend % (uint)divisor));
        }

        public static int hashCode(int v) { return v; }

        public static long toUnsignedLong(int v) { return v & 0xffffffffL; }

        public static String toString(int v)
        {
            return String.Wrap(JRuntime.Str(v));
        }

        public static String toString(int v, int radix)
        {
            if (radix < 2 || radix > 36)
            {
                radix = 10;
            }
            if (radix == 10)
            {
                return String.Wrap(JRuntime.Str(v));
            }
            long u = v;
            bool neg = u < 0;
            if (neg) { u = -u; }
            var sb = new global::System.Text.StringBuilder();
            if (u == 0) { sb.Append('0'); }
            while (u > 0)
            {
                sb.Insert(0, DigitChar((int)(u % radix)));
                u /= radix;
            }
            if (neg) { sb.Insert(0, '-'); }
            return String.Wrap(sb.ToString());
        }

        private static String UnsignedToString(uint u, int radix)
        {
            if (u == 0) { return String.Wrap("0"); }
            var sb = new global::System.Text.StringBuilder();
            while (u > 0)
            {
                sb.Insert(0, DigitChar((int)(u % (uint)radix)));
                u /= (uint)radix;
            }
            return String.Wrap(sb.ToString());
        }

        public static String toBinaryString(int v) { return UnsignedToString((uint)v, 2); }
        public static String toOctalString(int v) { return UnsignedToString((uint)v, 8); }
        public static String toHexString(int v) { return UnsignedToString((uint)v, 16); }

        public static String toUnsignedString(int v) { return UnsignedToString((uint)v, 10); }

        public static String toUnsignedString(int v, int radix)
        {
            if (radix < 2 || radix > 36) { radix = 10; }
            return UnsignedToString((uint)v, radix);
        }

        public static int bitCount(int v)
        {
            return global::System.Numerics.BitOperations.PopCount((uint)v);
        }

        public static int numberOfLeadingZeros(int v)
        {
            return global::System.Numerics.BitOperations.LeadingZeroCount((uint)v);
        }

        public static int numberOfTrailingZeros(int v)
        {
            return v == 0 ? 32 : global::System.Numerics.BitOperations.TrailingZeroCount((uint)v);
        }

        public static int highestOneBit(int v)
        {
            return v == 0
                    ? 0
                    : unchecked((int)(1u << (31 - global::System.Numerics.BitOperations.LeadingZeroCount((uint)v))));
        }

        public static int lowestOneBit(int v)
        {
            return v & (-v);
        }

        public static int reverse(int v)
        {
            uint x = (uint)v;
            x = ((x & 0x55555555u) << 1) | ((x >> 1) & 0x55555555u);
            x = ((x & 0x33333333u) << 2) | ((x >> 2) & 0x33333333u);
            x = ((x & 0x0f0f0f0fu) << 4) | ((x >> 4) & 0x0f0f0f0fu);
            x = (x << 24) | ((x & 0xff00u) << 8) | ((x >> 8) & 0xff00u) | (x >> 24);
            return unchecked((int)x);
        }

        public static int reverseBytes(int v)
        {
            return global::System.Buffers.Binary.BinaryPrimitives.ReverseEndianness(v);
        }

        public static int rotateLeft(int v, int distance)
        {
            return unchecked((int)global::System.Numerics.BitOperations.RotateLeft((uint)v, distance));
        }

        public static int rotateRight(int v, int distance)
        {
            return unchecked((int)global::System.Numerics.BitOperations.RotateRight((uint)v, distance));
        }

        public static int signum(int v)
        {
            return v < 0 ? -1 : (v > 0 ? 1 : 0);
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
