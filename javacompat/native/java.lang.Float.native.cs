namespace java.lang
{
    partial class Float
    {
        public override String toString()
        {
            return String.Wrap(JRuntime.JavaFloatToString(value));
        }

        public static float parseFloat(String s)
        {
            if (s == null)
            {
                throw JRuntime.NumberFormat("Cannot parse null string");
            }
            float r;
            if (!float.TryParse(s.Value, global::System.Globalization.NumberStyles.Float,
                    global::System.Globalization.CultureInfo.InvariantCulture, out r))
            {
                throw JRuntime.NumberFormat("For input string: \"" + s.Value + "\"");
            }
            return r;
        }

        public static int floatToRawIntBits(float v)
        {
            return global::System.BitConverter.SingleToInt32Bits(v);
        }

        public static float intBitsToFloat(int v)
        {
            return global::System.BitConverter.Int32BitsToSingle(v);
        }
    }
}
