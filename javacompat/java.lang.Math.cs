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

        public static double log(double v)
        {
            return global::System.Math.Log(v);
        }

        public const double PI = global::System.Math.PI;
        public const double E = global::System.Math.E;

        public static long abs(long v) { return v < 0 ? -v : v; }
        public static float abs(float v) { return global::System.Math.Abs(v); }
        public static long max(long a, long b) { return global::System.Math.Max(a, b); }
        public static double max(double a, double b) { return global::System.Math.Max(a, b); }
        public static float max(float a, float b) { return global::System.Math.Max(a, b); }
        public static long min(long a, long b) { return global::System.Math.Min(a, b); }
        public static double min(double a, double b) { return global::System.Math.Min(a, b); }
        public static float min(float a, float b) { return global::System.Math.Min(a, b); }
        public static double pow(double a, double b) { return global::System.Math.Pow(a, b); }
        public static double random() { return SharedRandom.NextDouble(); }
        public static double sin(double v) { return global::System.Math.Sin(v); }
        public static double cos(double v) { return global::System.Math.Cos(v); }
        public static double tan(double v) { return global::System.Math.Tan(v); }
        public static double atan2(double y, double x) { return global::System.Math.Atan2(y, x); }
        public static double exp(double v) { return global::System.Math.Exp(v); }
        public static double log10(double v) { return global::System.Math.Log10(v); }
        public static double cbrt(double v) { return global::System.Math.Cbrt(v); }
        public static double hypot(double x, double y) { return global::System.Math.Sqrt(x * x + y * y); }
        public static double floor(double v) { return global::System.Math.Floor(v); }
        public static double ceil(double v) { return global::System.Math.Ceiling(v); }
        public static long round(double v) { return (long)global::System.Math.Round(v, global::System.MidpointRounding.AwayFromZero); }
        public static int round(float v) { return (int)global::System.MathF.Round(v, global::System.MidpointRounding.AwayFromZero); }
        public static double toRadians(double deg) { return deg * global::System.Math.PI / 180.0; }
        public static double toDegrees(double rad) { return rad * 180.0 / global::System.Math.PI; }
        public static double signum(double v) { return global::System.Math.Sign(v); }

        private static readonly global::System.Random SharedRandom = new global::System.Random();
    }
}
