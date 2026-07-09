namespace java.util.concurrent
{
    public sealed class ThreadLocalRandom : global::java.lang.Object
    {
        private static readonly ThreadLocalRandom Instance = new ThreadLocalRandom(global::java.lang.RawNew.I);
        private readonly global::System.Random rng = new global::System.Random();

        public ThreadLocalRandom(global::java.lang.RawNew r) : base(r)
        {
        }

        public static ThreadLocalRandom current()
        {
            return Instance;
        }

        private static global::java.lang.JThrow Iae(string message)
        {
            var ex = new global::java.lang.IllegalArgumentException(global::java.lang.RawNew.I);
            ex.__init_Ljava_lang_String__V(global::java.lang.String.Wrap(message));
            return global::java.lang.JThrow.of(ex);
        }

        public int nextInt()
        {
            return unchecked((int)((uint)rng.Next() ^ ((uint)rng.Next() << 1)));
        }

        public int nextInt(int bound)
        {
            if (bound <= 0) { throw Iae("bound must be positive"); }
            return rng.Next(bound);
        }

        public int nextInt(int origin, int bound)
        {
            if (origin >= bound) { throw Iae("bound must be greater than origin"); }
            return origin + rng.Next(bound - origin);
        }

        public long nextLong()
        {
            long hi = (long)rng.Next();
            long lo = (long)rng.Next();
            return (hi << 32) ^ lo;
        }

        public long nextLong(long bound)
        {
            if (bound <= 0) { throw Iae("bound must be positive"); }
            return (long)(rng.NextDouble() * bound);
        }

        public double nextDouble()
        {
            return rng.NextDouble();
        }

        public double nextDouble(double bound)
        {
            if (!(bound > 0.0)) { throw Iae("bound must be positive"); }
            return rng.NextDouble() * bound;
        }

        public float nextFloat()
        {
            return (float)rng.NextDouble();
        }

        public int nextBoolean()
        {
            return rng.Next(2);
        }
    }
}
