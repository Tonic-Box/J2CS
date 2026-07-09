namespace java.lang
{
    public sealed class StrictMath : Object
    {
        private StrictMath() : base(RawNew.I)
        {
        }

        public const double PI = global::System.Math.PI;
        public const double E = global::System.Math.E;

        public static int abs(int v) { return Math.abs(v); }
        public static long abs(long v) { return Math.abs(v); }
        public static float abs(float v) { return Math.abs(v); }
        public static double abs(double v) { return Math.abs(v); }

        public static int max(int a, int b) { return Math.max(a, b); }
        public static long max(long a, long b) { return Math.max(a, b); }
        public static float max(float a, float b) { return Math.max(a, b); }
        public static double max(double a, double b) { return Math.max(a, b); }

        public static int min(int a, int b) { return Math.min(a, b); }
        public static long min(long a, long b) { return Math.min(a, b); }
        public static float min(float a, float b) { return Math.min(a, b); }
        public static double min(double a, double b) { return Math.min(a, b); }

        public static double sqrt(double v) { return Math.sqrt(v); }
        public static double cbrt(double v) { return Math.cbrt(v); }
        public static double pow(double a, double b) { return Math.pow(a, b); }
        public static double exp(double v) { return Math.exp(v); }
        public static double expm1(double v) { return Math.expm1(v); }
        public static double log(double v) { return Math.log(v); }
        public static double log10(double v) { return Math.log10(v); }
        public static double log1p(double v) { return Math.log1p(v); }

        public static double sin(double v) { return Math.sin(v); }
        public static double cos(double v) { return Math.cos(v); }
        public static double tan(double v) { return Math.tan(v); }
        public static double asin(double v) { return Math.asin(v); }
        public static double acos(double v) { return Math.acos(v); }
        public static double atan(double v) { return Math.atan(v); }
        public static double atan2(double y, double x) { return Math.atan2(y, x); }
        public static double sinh(double v) { return Math.sinh(v); }
        public static double cosh(double v) { return Math.cosh(v); }
        public static double tanh(double v) { return Math.tanh(v); }

        public static double toRadians(double deg) { return Math.toRadians(deg); }
        public static double toDegrees(double rad) { return Math.toDegrees(rad); }
        public static double hypot(double x, double y) { return Math.hypot(x, y); }
        public static double IEEEremainder(double f1, double f2) { return Math.IEEEremainder(f1, f2); }

        public static double floor(double v) { return Math.floor(v); }
        public static double ceil(double v) { return Math.ceil(v); }
        public static double rint(double v) { return Math.rint(v); }
        public static long round(double a) { return Math.round(a); }
        public static int round(float a) { return Math.round(a); }
        public static double random() { return Math.random(); }

        public static double signum(double v) { return Math.signum(v); }
        public static float signum(float v) { return Math.signum(v); }
        public static double copySign(double magnitude, double sign) { return Math.copySign(magnitude, sign); }
        public static float copySign(float magnitude, float sign) { return Math.copySign(magnitude, sign); }

        public static double nextUp(double d) { return Math.nextUp(d); }
        public static float nextUp(float f) { return Math.nextUp(f); }
        public static double nextDown(double d) { return Math.nextDown(d); }
        public static float nextDown(float f) { return Math.nextDown(f); }
        public static double nextAfter(double start, double direction) { return Math.nextAfter(start, direction); }
        public static float nextAfter(float start, double direction) { return Math.nextAfter(start, direction); }
        public static double ulp(double d) { return Math.ulp(d); }
        public static float ulp(float f) { return Math.ulp(f); }
        public static double scalb(double d, int scaleFactor) { return Math.scalb(d, scaleFactor); }
        public static float scalb(float f, int scaleFactor) { return Math.scalb(f, scaleFactor); }
        public static int getExponent(double d) { return Math.getExponent(d); }
        public static int getExponent(float f) { return Math.getExponent(f); }

        public static int floorDiv(int x, int y) { return Math.floorDiv(x, y); }
        public static long floorDiv(long x, int y) { return Math.floorDiv(x, y); }
        public static long floorDiv(long x, long y) { return Math.floorDiv(x, y); }
        public static int floorMod(int x, int y) { return Math.floorMod(x, y); }
        public static int floorMod(long x, int y) { return Math.floorMod(x, y); }
        public static long floorMod(long x, long y) { return Math.floorMod(x, y); }

        public static int addExact(int x, int y) { return Math.addExact(x, y); }
        public static long addExact(long x, long y) { return Math.addExact(x, y); }
        public static int subtractExact(int x, int y) { return Math.subtractExact(x, y); }
        public static long subtractExact(long x, long y) { return Math.subtractExact(x, y); }
        public static int multiplyExact(int x, int y) { return Math.multiplyExact(x, y); }
        public static long multiplyExact(long x, int y) { return Math.multiplyExact(x, y); }
        public static long multiplyExact(long x, long y) { return Math.multiplyExact(x, y); }
        public static int incrementExact(int a) { return Math.incrementExact(a); }
        public static long incrementExact(long a) { return Math.incrementExact(a); }
        public static int decrementExact(int a) { return Math.decrementExact(a); }
        public static long decrementExact(long a) { return Math.decrementExact(a); }
        public static int negateExact(int a) { return Math.negateExact(a); }
        public static long negateExact(long a) { return Math.negateExact(a); }
        public static int toIntExact(long value) { return Math.toIntExact(value); }
    }
}
