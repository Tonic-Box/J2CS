namespace java.lang
{
    public sealed class Byte : Number
    {
        private static readonly Byte[] Cache = BuildCache();

        private readonly sbyte value;

        public Byte(RawNew r) : base(r)
        {
        }

        private Byte(sbyte v) : base(RawNew.I)
        {
            value = v;
        }

        private static Byte[] BuildCache()
        {
            var cache = new Byte[256];
            for (int i = 0; i < 256; i++)
            {
                cache[i] = new Byte((sbyte)(i - 128));
            }
            return cache;
        }

        public static Byte valueOf(sbyte v)
        {
            return Cache[v + 128];
        }

        public static String toString(sbyte v)
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

        public int compareTo(Byte other)
        {
            return value - other.value;
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Byte other && other.value == value ? 1 : 0;
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
