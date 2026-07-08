namespace java.lang
{
    public sealed class Double : Number
    {
        public static readonly global::java.lang.Class TYPE = global::java.lang.Class.Of("double");
        public const double MAX_VALUE = double.MaxValue;
        public const double MIN_VALUE = double.Epsilon;
        public const double POSITIVE_INFINITY = double.PositiveInfinity;
        public const double NEGATIVE_INFINITY = double.NegativeInfinity;
        public const double NaN = double.NaN;

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

        public static double sum(double a, double b) { return a + b; }
        public static double max(double a, double b) { return a > b ? a : b; }
        public static double min(double a, double b) { return a < b ? a : b; }

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

        public static String toString(double v)
        {
            return String.Wrap(JRuntime.JavaDoubleToString(v));
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

        public int compareTo(Double other)
        {
            return value.CompareTo(other.value);
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Double other
                    && global::System.BitConverter.DoubleToInt64Bits(other.value)
                            == global::System.BitConverter.DoubleToInt64Bits(value) ? 1 : 0;
        }

        public override int hashCode()
        {
            long bits = global::System.BitConverter.DoubleToInt64Bits(value);
            return (int)(bits ^ (long)((ulong)bits >> 32));
        }

        public override String toString()
        {
            return String.Wrap(JRuntime.JavaDoubleToString(value));
        }
    }
}
