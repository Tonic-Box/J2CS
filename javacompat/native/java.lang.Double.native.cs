namespace java.lang
{
    partial class Double
    {
        public override String toString()
        {
            return String.Wrap(JRuntime.JavaDoubleToString(value));
        }

        public static double parseDouble(String s)
        {
            if (s == null)
            {
                throw JRuntime.NumberFormat("Cannot parse null string");
            }
            double r;
            if (!double.TryParse(s.Value, global::System.Globalization.NumberStyles.Float,
                    global::System.Globalization.CultureInfo.InvariantCulture, out r))
            {
                throw JRuntime.NumberFormat("For input string: \"" + s.Value + "\"");
            }
            return r;
        }

        public static long doubleToRawLongBits(double v)
        {
            return global::System.BitConverter.DoubleToInt64Bits(v);
        }

        public static double longBitsToDouble(long v)
        {
            return global::System.BitConverter.Int64BitsToDouble(v);
        }
    }
}
