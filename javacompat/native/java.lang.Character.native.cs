namespace java.lang
{
    partial class Character
    {
        private static readonly Character[] __cache = __buildCache();

        private static Character[] __buildCache()
        {
            var c = new Character[128];
            for (int i = 0; i < 128; i++)
            {
                var x = new Character(RawNew.I);
                x.__init_C_V((char)i);
                c[i] = x;
            }
            return c;
        }

        public static Character valueOf(char v)
        {
            if (v <= 127)
            {
                return __cache[v];
            }
            var x = new Character(RawNew.I);
            x.__init_C_V(v);
            return x;
        }

        public override String toString()
        {
            return String.Wrap(value.ToString());
        }
    }
}
