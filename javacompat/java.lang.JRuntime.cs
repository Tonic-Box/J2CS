namespace java.lang
{
    /// <summary>
    /// Emitter support helpers: JVM comparison and division semantics, Java-style number
    /// formatting, and string-conversion routines used by concat and println.
    /// Known formatting divergence: relies on .NET's shortest round-trip digits, which can
    /// differ from Java's FloatingDecimal on rare values.
    /// </summary>
    public static class JRuntime
    {
        public static Throwable Normalize(global::System.Exception e)
        {
            if (e is JThrow jt)
            {
                return jt.payload;
            }
            Throwable t;
            string msg = null;
            if (e is global::System.NullReferenceException)
            {
                t = new NullPointerException(RawNew.I);
            }
            else if (e is global::System.DivideByZeroException)
            {
                t = new ArithmeticException(RawNew.I);
                msg = "/ by zero";
            }
            else if (e is global::System.IndexOutOfRangeException)
            {
                t = new ArrayIndexOutOfBoundsException(RawNew.I);
            }
            else if (e is global::System.InvalidCastException)
            {
                t = new ClassCastException(RawNew.I);
            }
            else if (e is global::System.ArrayTypeMismatchException)
            {
                t = new ArrayStoreException(RawNew.I);
            }
            else if (e is global::System.FormatException)
            {
                t = new NumberFormatException(RawNew.I);
                msg = e.Message;
            }
            else
            {
                t = new JForeignError(RawNew.I);
                msg = e.GetType().FullName + ": " + e.Message;
            }
            if (msg == null)
            {
                t.__init__V();
            }
            else
            {
                t.__init_Ljava_lang_String__V(String.Wrap(msg));
            }
            t.__origin = e;
            return t;
        }

        public static JThrow NumberFormat(string message)
        {
            var nfe = new NumberFormatException(RawNew.I);
            nfe.__init_Ljava_lang_String__V(String.Wrap(message));
            return JThrow.of(nfe);
        }

        public static JThrow Simple(Throwable t)
        {
            t.__init__V();
            return JThrow.of(t);
        }

        public static global::java.lang.String[] Args(string[] args)
        {
            var result = new global::java.lang.String[args.Length];
            for (int i = 0; i < args.Length; i++)
            {
                result[i] = global::java.lang.String.Wrap(args[i]);
            }
            return result;
        }

        public static int Lcmp(long a, long b)
        {
            return a < b ? -1 : a > b ? 1 : 0;
        }

        public static int Fcmpl(float a, float b)
        {
            if (float.IsNaN(a) || float.IsNaN(b))
            {
                return -1;
            }
            return a < b ? -1 : a > b ? 1 : 0;
        }

        public static int Fcmpg(float a, float b)
        {
            if (float.IsNaN(a) || float.IsNaN(b))
            {
                return 1;
            }
            return a < b ? -1 : a > b ? 1 : 0;
        }

        public static int Dcmpl(double a, double b)
        {
            if (double.IsNaN(a) || double.IsNaN(b))
            {
                return -1;
            }
            return a < b ? -1 : a > b ? 1 : 0;
        }

        public static int Dcmpg(double a, double b)
        {
            if (double.IsNaN(a) || double.IsNaN(b))
            {
                return 1;
            }
            return a < b ? -1 : a > b ? 1 : 0;
        }

        public static int Idiv(int a, int b)
        {
            return b == -1 ? -a : a / b;
        }

        public static int Irem(int a, int b)
        {
            return b == -1 ? 0 : a % b;
        }

        public static long Ldiv(long a, long b)
        {
            return b == -1L ? -a : a / b;
        }

        public static long Lrem(long a, long b)
        {
            return b == -1L ? 0L : a % b;
        }

        public static int F2I(float v)
        {
            return D2I(v);
        }

        public static long F2L(float v)
        {
            return D2L(v);
        }

        public static int D2I(double v)
        {
            if (double.IsNaN(v))
            {
                return 0;
            }
            if (v >= 2147483647.0)
            {
                return int.MaxValue;
            }
            if (v <= -2147483648.0)
            {
                return int.MinValue;
            }
            return (int)v;
        }

        public static long D2L(double v)
        {
            if (double.IsNaN(v))
            {
                return 0L;
            }
            if (v >= 9223372036854775808.0)
            {
                return long.MaxValue;
            }
            if (v <= -9223372036854775808.0)
            {
                return long.MinValue;
            }
            return (long)v;
        }

        public static string Str(int v)
        {
            return v.ToString(global::System.Globalization.CultureInfo.InvariantCulture);
        }

        public static string Str(long v)
        {
            return v.ToString(global::System.Globalization.CultureInfo.InvariantCulture);
        }

        public static string Str(char v)
        {
            return v.ToString();
        }

        public static string Str(float v)
        {
            return JavaFloatToString(v);
        }

        public static string Str(double v)
        {
            return JavaDoubleToString(v);
        }

        public static string StrZ(int v)
        {
            return v != 0 ? "true" : "false";
        }

        public static string Str(global::java.lang.String s)
        {
            return s == null ? "null" : s.Value;
        }

        /// <summary>Unwraps for widget/text positions, where Java treats null as empty.</summary>
        public static string Cs(global::java.lang.String s)
        {
            return s == null ? "" : s.Value;
        }

        public static string Str(global::java.lang.Object o)
        {
            return o == null ? "null" : Str(o.toString());
        }

        public static byte[] UnsignedBytes(sbyte[] a)
        {
            if (a == null)
            {
                return null;
            }
            var r = new byte[a.Length];
            for (int i = 0; i < a.Length; i++)
            {
                r[i] = (byte)a[i];
            }
            return r;
        }

        public static sbyte[] SignedBytes(byte[] a)
        {
            if (a == null)
            {
                return null;
            }
            var r = new sbyte[a.Length];
            for (int i = 0; i < a.Length; i++)
            {
                r[i] = (sbyte)a[i];
            }
            return r;
        }

        public static string JavaDoubleToString(double d)
        {
            if (double.IsNaN(d))
            {
                return "NaN";
            }
            if (double.IsInfinity(d))
            {
                return d > 0 ? "Infinity" : "-Infinity";
            }
            return FormatJava(d.ToString(global::System.Globalization.CultureInfo.InvariantCulture));
        }

        public static string JavaFloatToString(float f)
        {
            if (float.IsNaN(f))
            {
                return "NaN";
            }
            if (float.IsInfinity(f))
            {
                return f > 0 ? "Infinity" : "-Infinity";
            }
            return FormatJava(f.ToString(global::System.Globalization.CultureInfo.InvariantCulture));
        }

        private static string FormatJava(string shortest)
        {
            bool negative = shortest.StartsWith("-");
            string s = negative ? shortest.Substring(1) : shortest;
            string mantissa = s;
            int exponent = 0;
            int e = s.IndexOfAny(new[] { 'E', 'e' });
            if (e >= 0)
            {
                exponent = int.Parse(s.Substring(e + 1), global::System.Globalization.CultureInfo.InvariantCulture);
                mantissa = s.Substring(0, e);
            }
            int dot = mantissa.IndexOf('.');
            int pointPos = dot >= 0 ? dot : mantissa.Length;
            string digits = dot >= 0 ? mantissa.Remove(dot, 1) : mantissa;
            while (digits.Length > 1 && digits[0] == '0')
            {
                digits = digits.Substring(1);
                pointPos--;
            }
            while (digits.Length > 1 && digits[digits.Length - 1] == '0')
            {
                digits = digits.Substring(0, digits.Length - 1);
            }
            if (digits == "0")
            {
                return negative ? "-0.0" : "0.0";
            }
            int decExp = pointPos + exponent;
            var sb = new global::System.Text.StringBuilder();
            if (negative)
            {
                sb.Append('-');
            }
            if (decExp > 0 && decExp <= 7)
            {
                if (digits.Length <= decExp)
                {
                    sb.Append(digits).Append(new string('0', decExp - digits.Length)).Append(".0");
                }
                else
                {
                    sb.Append(digits, 0, decExp).Append('.').Append(digits.Substring(decExp));
                }
            }
            else if (decExp <= 0 && decExp > -3)
            {
                sb.Append("0.").Append(new string('0', -decExp)).Append(digits);
            }
            else
            {
                sb.Append(digits[0]).Append('.');
                sb.Append(digits.Length > 1 ? digits.Substring(1) : "0");
                sb.Append('E').Append(decExp - 1);
            }
            return sb.ToString();
        }
    }
}
