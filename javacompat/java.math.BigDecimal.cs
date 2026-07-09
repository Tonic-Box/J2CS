namespace java.math
{
    public sealed class BigDecimal : global::java.lang.Object
    {
        private static readonly global::System.Globalization.CultureInfo Inv = global::System.Globalization.CultureInfo.InvariantCulture;

        internal global::System.Numerics.BigInteger unscaled;
        internal int scaleVal;

        internal BigDecimal(global::System.Numerics.BigInteger u, int sc) : base(global::java.lang.RawNew.I)
        {
            unscaled = u;
            scaleVal = sc;
        }

        public BigDecimal(global::java.lang.RawNew r) : base(r) { }

        public void __init_Ljava_lang_String__V(global::java.lang.String s) { Parse(s.Value); }
        public void __init_D_V(double d) { Parse(d.ToString("R", Inv)); }
        public void __init_I_V(int i) { unscaled = new global::System.Numerics.BigInteger(i); scaleVal = 0; }
        public void __init_J_V(long l) { unscaled = new global::System.Numerics.BigInteger(l); scaleVal = 0; }
        public void __init_Ljava_math_BigInteger__V(BigInteger b) { unscaled = b.v; scaleVal = 0; }

        private void Parse(string str)
        {
            str = str.Trim();
            int dot = str.IndexOf('.');
            if (dot < 0)
            {
                unscaled = global::System.Numerics.BigInteger.Parse(str, Inv);
                scaleVal = 0;
            }
            else
            {
                string frac = str.Substring(dot + 1);
                unscaled = global::System.Numerics.BigInteger.Parse(str.Substring(0, dot) + frac, Inv);
                scaleVal = frac.Length;
            }
        }

        public static BigDecimal valueOf(long val) { return new BigDecimal(new global::System.Numerics.BigInteger(val), 0); }

        public static BigDecimal valueOf(double val)
        {
            var b = new BigDecimal(global::java.lang.RawNew.I);
            b.Parse(val.ToString("R", Inv));
            return b;
        }

        private static global::System.Numerics.BigInteger Pow10(int e)
        {
            return global::System.Numerics.BigInteger.Pow(new global::System.Numerics.BigInteger(10), e);
        }

        private static global::System.Numerics.BigInteger DivRound(global::System.Numerics.BigInteger num,
                global::System.Numerics.BigInteger den, RoundingMode mode)
        {
            int sign = num.Sign * den.Sign;
            var n = global::System.Numerics.BigInteger.Abs(num);
            var d = global::System.Numerics.BigInteger.Abs(den);
            var q = n / d;
            var r = n % d;
            if (r.Sign != 0)
            {
                var twice = r * 2;
                string m = mode == null ? "UNNECESSARY" : mode.name;
                bool up;
                switch (m)
                {
                    case "UP": up = true; break;
                    case "DOWN": up = false; break;
                    case "CEILING": up = sign > 0; break;
                    case "FLOOR": up = sign < 0; break;
                    case "HALF_UP": up = twice >= d; break;
                    case "HALF_DOWN": up = twice > d; break;
                    case "HALF_EVEN": up = twice > d || (twice == d && !q.IsEven); break;
                    default:
                        throw global::java.lang.JThrow.of(new global::java.lang.ArithmeticException(global::java.lang.RawNew.I));
                }
                if (up) { q += global::System.Numerics.BigInteger.One; }
            }
            return sign < 0 ? -q : q;
        }

        public BigDecimal add(BigDecimal o)
        {
            int s = global::System.Math.Max(scaleVal, o.scaleVal);
            return new BigDecimal(unscaled * Pow10(s - scaleVal) + o.unscaled * Pow10(s - o.scaleVal), s);
        }

        public BigDecimal subtract(BigDecimal o)
        {
            int s = global::System.Math.Max(scaleVal, o.scaleVal);
            return new BigDecimal(unscaled * Pow10(s - scaleVal) - o.unscaled * Pow10(s - o.scaleVal), s);
        }

        public BigDecimal multiply(BigDecimal o)
        {
            return new BigDecimal(unscaled * o.unscaled, scaleVal + o.scaleVal);
        }

        public BigDecimal divide(BigDecimal o, int targetScale, RoundingMode mode)
        {
            int e = o.scaleVal - scaleVal + targetScale;
            global::System.Numerics.BigInteger num = unscaled;
            global::System.Numerics.BigInteger den = o.unscaled;
            if (e >= 0) { num *= Pow10(e); }
            else { den *= Pow10(-e); }
            return new BigDecimal(DivRound(num, den, mode), targetScale);
        }

        public BigDecimal divide(BigDecimal o, RoundingMode mode)
        {
            return divide(o, scaleVal, mode);
        }

        public BigDecimal setScale(int newScale, RoundingMode mode)
        {
            if (newScale >= scaleVal)
            {
                return new BigDecimal(unscaled * Pow10(newScale - scaleVal), newScale);
            }
            return new BigDecimal(DivRound(unscaled, Pow10(scaleVal - newScale), mode), newScale);
        }

        public int scale() { return scaleVal; }

        public BigDecimal abs() { return new BigDecimal(global::System.Numerics.BigInteger.Abs(unscaled), scaleVal); }
        public BigDecimal negate() { return new BigDecimal(-unscaled, scaleVal); }
        public int signum() { return unscaled.Sign; }

        public int compareTo(BigDecimal o)
        {
            int s = global::System.Math.Max(scaleVal, o.scaleVal);
            var a = unscaled * Pow10(s - scaleVal);
            var b = o.unscaled * Pow10(s - o.scaleVal);
            int c = a.CompareTo(b);
            return c < 0 ? -1 : c > 0 ? 1 : 0;
        }

        public BigInteger toBigInteger() { return new BigInteger(unscaled / Pow10(scaleVal)); }
        public int intValue() { return (int)(unscaled / Pow10(scaleVal)); }
        public long longValue() { return (long)(unscaled / Pow10(scaleVal)); }
        public double doubleValue() { return double.Parse(toString().Value, Inv); }

        public override int equals(global::java.lang.Object o)
        {
            return o is BigDecimal b && b.unscaled == unscaled && b.scaleVal == scaleVal ? 1 : 0;
        }

        public override int hashCode() { return unscaled.GetHashCode() * 31 + scaleVal; }

        public override global::java.lang.String toString()
        {
            if (scaleVal == 0)
            {
                return global::java.lang.String.Wrap(unscaled.ToString(Inv));
            }
            string sign = unscaled.Sign < 0 ? "-" : "";
            string digits = global::System.Numerics.BigInteger.Abs(unscaled).ToString(Inv);
            if (scaleVal < 0)
            {
                return global::java.lang.String.Wrap(sign + digits + new string('0', -scaleVal));
            }
            if (digits.Length <= scaleVal)
            {
                digits = new string('0', scaleVal - digits.Length + 1) + digits;
            }
            int point = digits.Length - scaleVal;
            return global::java.lang.String.Wrap(sign + digits.Substring(0, point) + "." + digits.Substring(point));
        }
    }
}
