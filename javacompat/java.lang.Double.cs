namespace java.lang
{
    public sealed class Double : Number
    {
        public static readonly global::java.lang.Class TYPE = global::java.lang.Class.Of("double");
        public const double MAX_VALUE = double.MaxValue;
        public const double MIN_VALUE = double.Epsilon;
        public const double MIN_NORMAL = 2.2250738585072014E-308;
        public const double POSITIVE_INFINITY = double.PositiveInfinity;
        public const double NEGATIVE_INFINITY = double.NegativeInfinity;
        public const double NaN = double.NaN;
        public const int MAX_EXPONENT = 1023;
        public const int MIN_EXPONENT = -1022;
        public const int SIZE = 64;
        public const int BYTES = 8;

        private readonly double value;

        public Double(RawNew r) : base(r)
        {
        }

        private Double(double v) : base(RawNew.I)
        {
            value = v;
        }

        public static Double valueOf(double v)
        {
            return new Double(v);
        }

        public static Double valueOf(String s)
        {
            return new Double(parseDouble(s));
        }

        public static double parseDouble(String s)
        {
            if (s == null)
            {
                throw JRuntime.NumberFormat("Cannot parse null string");
            }
            double result;
            bool ok = double.TryParse(s.Value,
                    global::System.Globalization.NumberStyles.Float,
                    global::System.Globalization.CultureInfo.InvariantCulture,
                    out result);
            if (!ok)
            {
                throw JRuntime.NumberFormat("For input string: \"" + s.Value + "\"");
            }
            return result;
        }

        public static double sum(double a, double b) { return a + b; }
        public static double max(double a, double b) { return a > b ? a : b; }
        public static double min(double a, double b) { return a < b ? a : b; }

        public static int isNaN(double v) { return double.IsNaN(v) ? 1 : 0; }
        public static int isInfinite(double v) { return double.IsInfinity(v) ? 1 : 0; }
        public static int isFinite(double v) { return !double.IsNaN(v) && !double.IsInfinity(v) ? 1 : 0; }

        public static int compare(double a, double b)
        {
            if (a < b) { return -1; }
            if (a > b) { return 1; }
            long ba = doubleToLongBits(a);
            long bb = doubleToLongBits(b);
            return ba == bb ? 0 : (ba < bb ? -1 : 1);
        }

        public static long doubleToLongBits(double v)
        {
            if (double.IsNaN(v)) { return 0x7ff8000000000000L; }
            return global::System.BitConverter.DoubleToInt64Bits(v);
        }

        public static long doubleToRawLongBits(double v)
        {
            return global::System.BitConverter.DoubleToInt64Bits(v);
        }

        public static double longBitsToDouble(long bits)
        {
            return global::System.BitConverter.Int64BitsToDouble(bits);
        }

        public static int hashCode(double v)
        {
            long bits = doubleToLongBits(v);
            return (int)(bits ^ (long)((ulong)bits >> 32));
        }

        public static String toString(double v)
        {
            return String.Wrap(JRuntime.JavaDoubleToString(v));
        }

        public static String toHexString(double d)
        {
            if (double.IsNaN(d) || double.IsInfinity(d))
            {
                return String.Wrap(JRuntime.JavaDoubleToString(d));
            }
            var sb = new global::System.Text.StringBuilder();
            if (global::System.Math.CopySign(1.0, d) == -1.0) { sb.Append('-'); }
            sb.Append("0x");
            d = global::System.Math.Abs(d);
            if (d == 0.0)
            {
                sb.Append("0.0p0");
            }
            else
            {
                bool subnormal = d < MIN_NORMAL;
                long signifBits = global::System.BitConverter.DoubleToInt64Bits(d) & 0x000FFFFFFFFFFFFFL;
                sb.Append(subnormal ? "0." : "1.");
                string signif = global::System.Convert.ToString(signifBits | 0x0010000000000000L, 16).Substring(1);
                signif = signif.TrimEnd('0');
                sb.Append(signif.Length == 0 ? "0" : signif);
                sb.Append('p');
                sb.Append(subnormal ? MIN_EXPONENT : Math.getExponent(d));
            }
            return String.Wrap(sb.ToString());
        }

        public override int intValue()
        {
            return global::java.lang.JRuntime.D2I(value);
        }

        public override long longValue()
        {
            return global::java.lang.JRuntime.D2L(value);
        }

        public override float floatValue()
        {
            return (float)value;
        }

        public override double doubleValue()
        {
            return value;
        }

        public int isNaN()
        {
            return double.IsNaN(value) ? 1 : 0;
        }

        public int isInfinite()
        {
            return double.IsInfinity(value) ? 1 : 0;
        }

        public int compareTo(Double other)
        {
            return compare(value, other.value);
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Double other && doubleToLongBits(other.value) == doubleToLongBits(value) ? 1 : 0;
        }

        public override int hashCode()
        {
            long bits = doubleToLongBits(value);
            return (int)(bits ^ (long)((ulong)bits >> 32));
        }

        public override String toString()
        {
            return String.Wrap(JRuntime.JavaDoubleToString(value));
        }
    }
}
