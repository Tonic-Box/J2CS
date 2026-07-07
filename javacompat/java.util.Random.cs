namespace java.util
{
    /// <summary>
    /// Reimplements java.util.Random's exact 48-bit linear congruential algorithm so a seeded
    /// stream matches the JVM bit-for-bit (next/nextInt/nextLong/nextBoolean/nextDouble/nextFloat).
    /// </summary>
    public class Random : global::java.lang.Object
    {
        private const long Multiplier = 0x5DEECE66DL;
        private const long Addend = 0xBL;
        private const long Mask = (1L << 48) - 1;

        private static long seedUniquifier = 8682522807148012L;

        private long seed;

        public Random(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
            seedUniquifier = unchecked(seedUniquifier * 1181783497276652981L);
            SetSeed(seedUniquifier);
        }

        public void __init_J_V(long seed)
        {
            SetSeed(seed);
        }

        public void setSeed(long s)
        {
            SetSeed(s);
        }

        private void SetSeed(long s)
        {
            seed = (s ^ Multiplier) & Mask;
        }

        protected int next(int bits)
        {
            seed = unchecked(seed * Multiplier + Addend) & Mask;
            return (int)(seed >> (48 - bits));
        }

        public int nextInt()
        {
            return next(32);
        }

        public int nextInt(int bound)
        {
            if (bound <= 0)
            {
                var ex = new global::java.lang.IllegalArgumentException(global::java.lang.RawNew.I);
                ex.__init_Ljava_lang_String__V(global::java.lang.String.Wrap("bound must be positive"));
                throw global::java.lang.JThrow.of(ex);
            }
            int r = next(31);
            int m = bound - 1;
            if ((bound & m) == 0)
            {
                r = (int)((bound * (long)r) >> 31);
            }
            else
            {
                for (int u = r; u - (r = u % bound) + m < 0; u = next(31))
                {
                }
            }
            return r;
        }

        public long nextLong()
        {
            return ((long)next(32) << 32) + next(32);
        }

        public int nextBoolean()
        {
            return next(1) != 0 ? 1 : 0;
        }

        public double nextDouble()
        {
            return (((long)next(26) << 27) + next(27)) * (1.0 / (1L << 53));
        }

        public float nextFloat()
        {
            return next(24) / (float)(1 << 24);
        }
    }
}
