namespace java.lang
{
    partial class Byte
    {
        private static readonly Byte[] __cache = __buildCache();

        private static Byte[] __buildCache()
        {
            var c = new Byte[256];
            for (int i = 0; i < 256; i++)
            {
                var x = new Byte(RawNew.I);
                x.__init_B_V((sbyte)(i - 128));
                c[i] = x;
            }
            return c;
        }

        public static Byte valueOf(sbyte v)
        {
            return __cache[v + 128];
        }

        public override String toString()
        {
            return String.Wrap(JRuntime.Str(value));
        }
    }
}
