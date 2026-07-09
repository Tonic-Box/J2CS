namespace java.lang
{
    public sealed class Float : Number
    {
        public static readonly global::java.lang.Class TYPE = global::java.lang.Class.Of("float");
        public const float MAX_VALUE = float.MaxValue;
        public const float MIN_VALUE = float.Epsilon;
        public const float MIN_NORMAL = 1.17549435E-38f;
        public const float POSITIVE_INFINITY = float.PositiveInfinity;
        public const float NEGATIVE_INFINITY = float.NegativeInfinity;
        public const float NaN = float.NaN;
        public const int MAX_EXPONENT = 127;
        public const int MIN_EXPONENT = -126;
        public const int SIZE = 32;
        public const int BYTES = 4;

        private readonly float value;

        public Float(RawNew r) : base(r)
        {
        }

        private Float(float v) : base(RawNew.I)
        {
            value = v;
        }

        public static Float valueOf(float v)
        {
            return new Float(v);
        }

        public static Float valueOf(String s)
        {
            return new Float(parseFloat(s));
        }

        public static float parseFloat(String s)
        {
            if (s == null)
            {
                throw JRuntime.NumberFormat("Cannot parse null string");
            }
            float result;
            bool ok = float.TryParse(s.Value,
                    global::System.Globalization.NumberStyles.Float,
                    global::System.Globalization.CultureInfo.InvariantCulture,
                    out result);
            if (!ok)
            {
                throw JRuntime.NumberFormat("For input string: \"" + s.Value + "\"");
            }
            return result;
        }

        public static float sum(float a, float b) { return a + b; }
        public static float max(float a, float b) { return a > b ? a : b; }
        public static float min(float a, float b) { return a < b ? a : b; }

        public static int isNaN(float v) { return float.IsNaN(v) ? 1 : 0; }
        public static int isInfinite(float v) { return float.IsInfinity(v) ? 1 : 0; }
        public static int isFinite(float v) { return !float.IsNaN(v) && !float.IsInfinity(v) ? 1 : 0; }

        public static int compare(float a, float b)
        {
            if (a < b) { return -1; }
            if (a > b) { return 1; }
            int ba = floatToIntBits(a);
            int bb = floatToIntBits(b);
            return ba == bb ? 0 : (ba < bb ? -1 : 1);
        }

        public static int floatToIntBits(float v)
        {
            if (float.IsNaN(v)) { return 0x7fc00000; }
            return global::System.BitConverter.SingleToInt32Bits(v);
        }

        public static int floatToRawIntBits(float v)
        {
            return global::System.BitConverter.SingleToInt32Bits(v);
        }

        public static float intBitsToFloat(int bits)
        {
            return global::System.BitConverter.Int32BitsToSingle(bits);
        }

        public static int hashCode(float v)
        {
            return floatToIntBits(v);
        }

        public static String toString(float v)
        {
            return String.Wrap(JRuntime.JavaFloatToString(v));
        }

        public static String toHexString(float f)
        {
            if (global::System.MathF.Abs(f) < MIN_NORMAL && f != 0.0f)
            {
                string s = Double.toHexString(global::System.Math.ScaleB((double)f, -1022 - (-126))).Value;
                if (s.EndsWith("p-1022", global::System.StringComparison.Ordinal))
                {
                    s = s.Substring(0, s.Length - "p-1022".Length) + "p-126";
                }
                return String.Wrap(s);
            }
            return Double.toHexString((double)f);
        }

        public override int intValue()
        {
            return global::java.lang.JRuntime.F2I(value);
        }

        public override long longValue()
        {
            return global::java.lang.JRuntime.F2L(value);
        }

        public override float floatValue()
        {
            return value;
        }

        public override double doubleValue()
        {
            return value;
        }

        public int isNaN()
        {
            return float.IsNaN(value) ? 1 : 0;
        }

        public int isInfinite()
        {
            return float.IsInfinity(value) ? 1 : 0;
        }

        public int compareTo(Float other)
        {
            return compare(value, other.value);
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Float other && floatToIntBits(other.value) == floatToIntBits(value) ? 1 : 0;
        }

        public override int hashCode()
        {
            return floatToIntBits(value);
        }

        public override String toString()
        {
            return String.Wrap(JRuntime.JavaFloatToString(value));
        }
    }
}
