namespace java.lang
{
    public sealed class Float : Number
    {
        public static readonly global::java.lang.Class TYPE = global::java.lang.Class.Of("float");

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

        public static String toString(float v)
        {
            return String.Wrap(JRuntime.JavaFloatToString(v));
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

        public int compareTo(Float other)
        {
            return value.CompareTo(other.value);
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Float other
                    && global::System.BitConverter.SingleToInt32Bits(other.value)
                            == global::System.BitConverter.SingleToInt32Bits(value) ? 1 : 0;
        }

        public override int hashCode()
        {
            return global::System.BitConverter.SingleToInt32Bits(value);
        }

        public override String toString()
        {
            return String.Wrap(JRuntime.JavaFloatToString(value));
        }
    }
}
