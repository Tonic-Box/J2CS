namespace java.lang
{
    public sealed class Math : Object
    {
        private Math() : base(RawNew.I)
        {
        }

        public const double PI = global::System.Math.PI;
        public const double E = global::System.Math.E;

        private static JThrow Overflow(string message)
        {
            var e = new ArithmeticException(RawNew.I);
            e.__init_Ljava_lang_String__V(String.Wrap(message));
            return JThrow.of(e);
        }

        public static int abs(int v) { return v < 0 ? -v : v; }
        public static long abs(long v) { return v < 0 ? -v : v; }
        public static float abs(float v) { return global::System.Math.Abs(v); }
        public static double abs(double v) { return global::System.Math.Abs(v); }

        public static int max(int a, int b) { return global::System.Math.Max(a, b); }
        public static long max(long a, long b) { return global::System.Math.Max(a, b); }
        public static float max(float a, float b) { return global::System.Math.Max(a, b); }
        public static double max(double a, double b) { return global::System.Math.Max(a, b); }

        public static int min(int a, int b) { return global::System.Math.Min(a, b); }
        public static long min(long a, long b) { return global::System.Math.Min(a, b); }
        public static float min(float a, float b) { return global::System.Math.Min(a, b); }
        public static double min(double a, double b) { return global::System.Math.Min(a, b); }

        public static double sqrt(double v) { return global::System.Math.Sqrt(v); }
        public static double cbrt(double v) { return global::System.Math.Cbrt(v); }
        public static double pow(double a, double b) { return global::System.Math.Pow(a, b); }
        public static double exp(double v) { return global::System.Math.Exp(v); }
        public static double expm1(double v) { return global::System.Math.Exp(v) - 1.0; }
        public static double log(double v) { return global::System.Math.Log(v); }
        public static double log10(double v) { return global::System.Math.Log10(v); }
        public static double log1p(double v) { return global::System.Math.Log(1.0 + v); }

        public static double sin(double v) { return global::System.Math.Sin(v); }
        public static double cos(double v) { return global::System.Math.Cos(v); }
        public static double tan(double v) { return global::System.Math.Tan(v); }
        public static double asin(double v) { return global::System.Math.Asin(v); }
        public static double acos(double v) { return global::System.Math.Acos(v); }
        public static double atan(double v) { return global::System.Math.Atan(v); }
        public static double atan2(double y, double x) { return global::System.Math.Atan2(y, x); }
        public static double sinh(double v) { return global::System.Math.Sinh(v); }
        public static double cosh(double v) { return global::System.Math.Cosh(v); }
        public static double tanh(double v) { return global::System.Math.Tanh(v); }

        public static double toRadians(double deg) { return deg * global::System.Math.PI / 180.0; }
        public static double toDegrees(double rad) { return rad * 180.0 / global::System.Math.PI; }
        public static double hypot(double x, double y) { return global::System.Math.Sqrt(x * x + y * y); }
        public static double IEEEremainder(double f1, double f2) { return global::System.Math.IEEERemainder(f1, f2); }

        public static double floor(double v) { return global::System.Math.Floor(v); }
        public static double ceil(double v) { return global::System.Math.Ceiling(v); }
        public static double rint(double v) { return global::System.Math.Round(v, global::System.MidpointRounding.ToEven); }

        public static long round(double a)
        {
            long bits = global::System.BitConverter.DoubleToInt64Bits(a);
            long biasedExp = (bits & 0x7FF0000000000000L) >> 52;
            long shift = 1074L - biasedExp;
            if ((shift & -64L) == 0L)
            {
                long r = (bits & 0x000FFFFFFFFFFFFFL) | 0x0010000000000000L;
                if (bits < 0) { r = -r; }
                return ((r >> (int)shift) + 1) >> 1;
            }
            return (long)a;
        }

        public static int round(float a)
        {
            int bits = global::System.BitConverter.SingleToInt32Bits(a);
            int biasedExp = (bits & 0x7F800000) >> 23;
            int shift = 149 - biasedExp;
            if ((shift & -32) == 0)
            {
                int r = (bits & 0x007FFFFF) | 0x00800000;
                if (bits < 0) { r = -r; }
                return ((r >> shift) + 1) >> 1;
            }
            return (int)a;
        }

        public static double random() { return SharedRandom.NextDouble(); }

        public static double signum(double v)
        {
            return double.IsNaN(v) ? double.NaN : (v == 0.0 ? v : (v > 0.0 ? 1.0 : -1.0));
        }

        public static float signum(float v)
        {
            return float.IsNaN(v) ? float.NaN : (v == 0.0f ? v : (v > 0.0f ? 1.0f : -1.0f));
        }

        public static double copySign(double magnitude, double sign) { return global::System.Math.CopySign(magnitude, sign); }
        public static float copySign(float magnitude, float sign) { return global::System.MathF.CopySign(magnitude, sign); }

        public static double nextUp(double d) { return global::System.Math.BitIncrement(d); }
        public static float nextUp(float f) { return global::System.MathF.BitIncrement(f); }
        public static double nextDown(double d) { return global::System.Math.BitDecrement(d); }
        public static float nextDown(float f) { return global::System.MathF.BitDecrement(f); }

        public static double nextAfter(double start, double direction)
        {
            if (double.IsNaN(start) || double.IsNaN(direction)) { return start + direction; }
            if (start == direction) { return direction; }
            return direction > start ? global::System.Math.BitIncrement(start) : global::System.Math.BitDecrement(start);
        }

        public static float nextAfter(float start, double direction)
        {
            if (float.IsNaN(start) || double.IsNaN(direction)) { return start + (float)direction; }
            if (start == direction) { return (float)direction; }
            return direction > start ? global::System.MathF.BitIncrement(start) : global::System.MathF.BitDecrement(start);
        }

        public static double ulp(double d)
        {
            if (double.IsNaN(d)) { return double.NaN; }
            if (double.IsInfinity(d)) { return double.PositiveInfinity; }
            if (d == 0.0) { return double.Epsilon; }
            d = global::System.Math.Abs(d);
            double up = global::System.Math.BitIncrement(d);
            return double.IsInfinity(up) ? d - global::System.Math.BitDecrement(d) : up - d;
        }

        public static float ulp(float f)
        {
            if (float.IsNaN(f)) { return float.NaN; }
            if (float.IsInfinity(f)) { return float.PositiveInfinity; }
            if (f == 0.0f) { return float.Epsilon; }
            f = global::System.MathF.Abs(f);
            float up = global::System.MathF.BitIncrement(f);
            return float.IsInfinity(up) ? f - global::System.MathF.BitDecrement(f) : up - f;
        }

        public static double scalb(double d, int scaleFactor) { return global::System.Math.ScaleB(d, scaleFactor); }
        public static float scalb(float f, int scaleFactor) { return global::System.MathF.ScaleB(f, scaleFactor); }

        public static int getExponent(double d)
        {
            return (int)(((global::System.BitConverter.DoubleToInt64Bits(d) & 0x7FF0000000000000L) >> 52) - 1023L);
        }

        public static int getExponent(float f)
        {
            return ((global::System.BitConverter.SingleToInt32Bits(f) & 0x7F800000) >> 23) - 127;
        }

        public static int floorDiv(int x, int y)
        {
            long q = (long)x / y;
            if ((x ^ y) < 0 && q * y != x) { q--; }
            return unchecked((int)q);
        }

        public static long floorDiv(long x, int y) { return floorDiv(x, (long)y); }

        public static long floorDiv(long x, long y)
        {
            if (y == -1L) { return unchecked(-x); }
            long q = x / y;
            if ((x ^ y) < 0 && q * y != x) { q--; }
            return q;
        }

        public static int floorMod(int x, int y)
        {
            long r = (long)x % y;
            if ((x ^ y) < 0 && r != 0) { r += y; }
            return (int)r;
        }

        public static int floorMod(long x, int y) { return (int)floorMod(x, (long)y); }

        public static long floorMod(long x, long y)
        {
            if (y == -1L) { return 0L; }
            long r = x % y;
            if ((x ^ y) < 0 && r != 0) { r += y; }
            return r;
        }

        public static int addExact(int x, int y)
        {
            long r = (long)x + y;
            if (r != (int)r) { throw Overflow("integer overflow"); }
            return (int)r;
        }

        public static long addExact(long x, long y)
        {
            long r = unchecked(x + y);
            if (((x ^ r) & (y ^ r)) < 0) { throw Overflow("long overflow"); }
            return r;
        }

        public static int subtractExact(int x, int y)
        {
            long r = (long)x - y;
            if (r != (int)r) { throw Overflow("integer overflow"); }
            return (int)r;
        }

        public static long subtractExact(long x, long y)
        {
            long r = unchecked(x - y);
            if (((x ^ y) & (x ^ r)) < 0) { throw Overflow("long overflow"); }
            return r;
        }

        public static int multiplyExact(int x, int y)
        {
            long r = (long)x * y;
            if (r != (int)r) { throw Overflow("integer overflow"); }
            return (int)r;
        }

        public static long multiplyExact(long x, int y) { return multiplyExact(x, (long)y); }

        public static long multiplyExact(long x, long y)
        {
            long r = unchecked(x * y);
            long ax = global::System.Math.Abs(x);
            long ay = global::System.Math.Abs(y);
            if (((ax | ay) >> 31) != 0)
            {
                if ((y != 0 && (r / y != x || (x == long.MinValue && y == -1L))))
                {
                    throw Overflow("long overflow");
                }
            }
            return r;
        }

        public static int incrementExact(int a)
        {
            if (a == int.MaxValue) { throw Overflow("integer overflow"); }
            return a + 1;
        }

        public static long incrementExact(long a)
        {
            if (a == long.MaxValue) { throw Overflow("long overflow"); }
            return a + 1;
        }

        public static int decrementExact(int a)
        {
            if (a == int.MinValue) { throw Overflow("integer overflow"); }
            return a - 1;
        }

        public static long decrementExact(long a)
        {
            if (a == long.MinValue) { throw Overflow("long overflow"); }
            return a - 1;
        }

        public static int negateExact(int a)
        {
            if (a == int.MinValue) { throw Overflow("integer overflow"); }
            return -a;
        }

        public static long negateExact(long a)
        {
            if (a == long.MinValue) { throw Overflow("long overflow"); }
            return -a;
        }

        public static int toIntExact(long value)
        {
            if ((int)value != value) { throw Overflow("integer overflow"); }
            return (int)value;
        }

        private static readonly global::System.Random SharedRandom = new global::System.Random();
    }
}
