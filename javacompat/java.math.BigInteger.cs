namespace java.math
{
    public sealed class BigInteger : global::java.lang.Object
    {
        internal global::System.Numerics.BigInteger v;

        public static readonly BigInteger ZERO = new BigInteger(global::System.Numerics.BigInteger.Zero);
        public static readonly BigInteger ONE = new BigInteger(global::System.Numerics.BigInteger.One);
        public static readonly BigInteger TWO = new BigInteger(new global::System.Numerics.BigInteger(2));
        public static readonly BigInteger TEN = new BigInteger(new global::System.Numerics.BigInteger(10));

        internal BigInteger(global::System.Numerics.BigInteger x) : base(global::java.lang.RawNew.I) { v = x; }
        public BigInteger(global::java.lang.RawNew r) : base(r) { }

        public void __init_Ljava_lang_String__V(global::java.lang.String s)
        {
            v = global::System.Numerics.BigInteger.Parse(s.Value, global::System.Globalization.CultureInfo.InvariantCulture);
        }

        public static BigInteger valueOf(long val) { return new BigInteger(new global::System.Numerics.BigInteger(val)); }

        public BigInteger add(BigInteger o) { return new BigInteger(v + o.v); }
        public BigInteger subtract(BigInteger o) { return new BigInteger(v - o.v); }
        public BigInteger multiply(BigInteger o) { return new BigInteger(v * o.v); }
        public BigInteger divide(BigInteger o) { return new BigInteger(v / o.v); }
        public BigInteger remainder(BigInteger o) { return new BigInteger(v % o.v); }

        public BigInteger mod(BigInteger o)
        {
            var r = v % o.v;
            if (r.Sign < 0) { r += global::System.Numerics.BigInteger.Abs(o.v); }
            return new BigInteger(r);
        }

        public BigInteger pow(int e) { return new BigInteger(global::System.Numerics.BigInteger.Pow(v, e)); }
        public BigInteger gcd(BigInteger o) { return new BigInteger(global::System.Numerics.BigInteger.GreatestCommonDivisor(v, o.v)); }
        public BigInteger abs() { return new BigInteger(global::System.Numerics.BigInteger.Abs(v)); }
        public BigInteger negate() { return new BigInteger(-v); }
        public BigInteger modPow(BigInteger e, BigInteger m) { return new BigInteger(global::System.Numerics.BigInteger.ModPow(v, e.v, m.v)); }
        public BigInteger min(BigInteger o) { return v <= o.v ? this : o; }
        public BigInteger max(BigInteger o) { return v >= o.v ? this : o; }

        public int compareTo(BigInteger o)
        {
            int c = v.CompareTo(o.v);
            return c < 0 ? -1 : c > 0 ? 1 : 0;
        }

        public int signum() { return v.Sign; }
        public int intValue() { return (int)v; }
        public long longValue() { return (long)v; }
        public double doubleValue() { return (double)v; }

        public override int equals(global::java.lang.Object o)
        {
            return o is BigInteger b && b.v == v ? 1 : 0;
        }

        public override int hashCode() { return v.GetHashCode(); }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(v.ToString(global::System.Globalization.CultureInfo.InvariantCulture));
        }
    }
}
