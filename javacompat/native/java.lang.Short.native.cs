namespace java.lang
{
    partial class Short
    {
        private static readonly Short[] __cache = __buildCache();

        private static Short[] __buildCache()
        {
            var c = new Short[256];
            for (int i = 0; i < 256; i++)
            {
                var x = new Short(RawNew.I);
                x.__init_S_V((short)(i - 128));
                c[i] = x;
            }
            return c;
        }

        public static Short valueOf(short v)
        {
            if (v >= -128 && v <= 127)
            {
                return __cache[v + 128];
            }
            var x = new Short(RawNew.I);
            x.__init_S_V(v);
            return x;
        }

        public override String toString()
        {
            return String.Wrap(JRuntime.Str(value));
        }
    }
}
