namespace java.lang
{
    public sealed class Math : Object
    {
        private Math() : base(RawNew.I)
        {
        }

        public static int abs(int v)
        {
            return v < 0 ? -v : v;
        }

        public static double abs(double v)
        {
            return global::System.Math.Abs(v);
        }

        public static int max(int a, int b)
        {
            return global::System.Math.Max(a, b);
        }

        public static int min(int a, int b)
        {
            return global::System.Math.Min(a, b);
        }

        public static double sqrt(double v)
        {
            return global::System.Math.Sqrt(v);
        }
    }
}
