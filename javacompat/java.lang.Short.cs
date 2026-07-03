namespace java.lang
{
    public sealed class Short : Number
    {
        private static readonly Short[] Cache = BuildCache();

        private readonly short value;

        public Short(RawNew r) : base(r)
        {
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
