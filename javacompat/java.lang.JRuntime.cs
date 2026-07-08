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

        private static readonly global::System.Runtime.CompilerServices.ConditionalWeakTable<global::System.Array, global::java.lang.Object> ArrayBoxes =
                new global::System.Runtime.CompilerServices.ConditionalWeakTable<global::System.Array, global::java.lang.Object>();

        /// <summary>Box a native array as a java.lang.Object (stable per underlying array, so ==
        /// and collection identity match Java). desc is the JVM array descriptor.</summary>
        public static global::java.lang.Object Box(global::System.Array array, string desc)
        {
            if (array == null)
            {
                return null;
            }
            return ArrayBoxes.GetValue(array, a => new global::java.lang.J2csArray(a, desc));
        }

        /// <summary>Recover the native array from a boxed java.lang.Object; the caller casts the
        /// result to the concrete array type. Throws ClassCastException for a non-array Object.</summary>
        public static global::System.Array Unbox(global::java.lang.Object o)
        {
            if (o == null)
            {
                return null;
            }
            if (o is global::java.lang.J2csArray b)
            {
                return b.Value;
            }
            throw global::java.lang.JThrow.of(new global::java.lang.ClassCastException(global::java.lang.RawNew.I));
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

        private static readonly global::System.Globalization.CultureInfo Inv =
            global::System.Globalization.CultureInfo.InvariantCulture;

        /// <summary>
        /// java.util.Formatter-style formatting for the common conversions used by String.format,
        /// PrintStream.printf and String.format-driven concat: %[index$][flags][width][.prec]conv,
        /// conversions s S d f e E g G x X o c b B h H n %. Locale-sensitive output uses the
        /// invariant culture (matches Java's US/root formatting: '.' decimal, ',' grouping).
        /// </summary>
        public static string Format(string fmt, global::java.lang.Object[] args)
        {
            if (fmt == null)
            {
                return "null";
            }
            var sb = new global::System.Text.StringBuilder(fmt.Length);
            int autoArg = 0;
            int i = 0;
            while (i < fmt.Length)
            {
                char c = fmt[i];
                if (c != '%')
                {
                    sb.Append(c);
                    i++;
                    continue;
                }
                i++;
                if (i >= fmt.Length)
                {
                    sb.Append('%');
                    break;
                }
                int argIndex = -1;
                int mark = i;
                int num = 0;
                bool hasNum = false;
                while (i < fmt.Length && fmt[i] >= '0' && fmt[i] <= '9')
                {
                    num = num * 10 + (fmt[i] - '0');
                    hasNum = true;
                    i++;
                }
                if (hasNum && i < fmt.Length && fmt[i] == '$')
                {
                    argIndex = num - 1;
                    i++;
                }
                else
                {
                    i = mark;
                }
                bool left = false, plus = false, space = false, zero = false, group = false, paren = false, alt = false;
                while (i < fmt.Length)
                {
                    char fl = fmt[i];
                    if (fl == '-') { left = true; }
                    else if (fl == '+') { plus = true; }
                    else if (fl == ' ') { space = true; }
                    else if (fl == '0') { zero = true; }
                    else if (fl == ',') { group = true; }
                    else if (fl == '(') { paren = true; }
                    else if (fl == '#') { alt = true; }
                    else { break; }
                    i++;
                }
                int width = 0;
                while (i < fmt.Length && fmt[i] >= '0' && fmt[i] <= '9')
                {
                    width = width * 10 + (fmt[i] - '0');
                    i++;
                }
                int prec = -1;
                if (i < fmt.Length && fmt[i] == '.')
                {
                    i++;
                    prec = 0;
                    while (i < fmt.Length && fmt[i] >= '0' && fmt[i] <= '9')
                    {
                        prec = prec * 10 + (fmt[i] - '0');
                        i++;
                    }
                }
                if (i >= fmt.Length)
                {
                    break;
                }
                char conv = fmt[i];
                i++;
                if (conv == '%')
                {
                    sb.Append('%');
                    continue;
                }
                if (conv == 'n')
                {
                    sb.Append('\n');
                    continue;
                }
                global::java.lang.Object arg = null;
                if (conv != 'n')
                {
                    int idx = argIndex >= 0 ? argIndex : autoArg++;
                    arg = args != null && idx >= 0 && idx < args.Length ? args[idx] : null;
                }
                string body = FormatArg(conv, arg, prec, plus, space, zero, group, paren, alt, width, left);
                Pad(sb, body, width, left, zero && !left && IsNumeric(conv) && arg != null);
            }
            return sb.ToString();
        }

        private static bool IsNumeric(char conv)
        {
            char c = char.ToLowerInvariant(conv);
            return c == 'd' || c == 'f' || c == 'e' || c == 'g' || c == 'x' || c == 'o';
        }

        private static string FormatArg(char conv, global::java.lang.Object arg, int prec,
            bool plus, bool space, bool zero, bool group, bool paren, bool alt, int width, bool left)
        {
            switch (conv)
            {
                case 's':
                case 'S':
                {
                    string s = Str(arg);
                    if (prec >= 0 && s.Length > prec)
                    {
                        s = s.Substring(0, prec);
                    }
                    return conv == 'S' ? s.ToUpperInvariant() : s;
                }
                case 'b':
                case 'B':
                {
                    string s = arg == null ? "false"
                        : arg is global::java.lang.Boolean b ? (b.booleanValue() != 0 ? "true" : "false")
                        : "true";
                    return conv == 'B' ? s.ToUpperInvariant() : s;
                }
                case 'c':
                {
                    if (arg is global::java.lang.Number n) { return ((char)n.intValue()).ToString(); }
                    string s = Str(arg);
                    return s.Length > 0 ? s.Substring(0, 1) : "";
                }
                case 'h':
                case 'H':
                {
                    string s = arg == null ? "null" : arg.hashCode().ToString("x", Inv);
                    return conv == 'H' ? s.ToUpperInvariant() : s;
                }
                case 'd':
                {
                    long v = ((global::java.lang.Number)arg).longValue();
                    string digits = global::System.Math.Abs(v).ToString(group ? "#,0" : "0", Inv);
                    return Sign(v < 0, plus, space, paren, digits);
                }
                case 'x':
                case 'X':
                {
                    long v = ((global::java.lang.Number)arg).longValue();
                    string s = arg is global::java.lang.Integer
                        ? ((uint)(int)v).ToString("x", Inv)
                        : ((ulong)v).ToString("x", Inv);
                    if (alt) { s = "0x" + s; }
                    return conv == 'X' ? s.ToUpperInvariant() : s;
                }
                case 'o':
                {
                    long v = ((global::java.lang.Number)arg).longValue();
                    long u = arg is global::java.lang.Integer ? (uint)(int)v : v;
                    return global::System.Convert.ToString(u, 8);
                }
                case 'f':
                {
                    double v = ((global::java.lang.Number)arg).doubleValue();
                    int p = prec < 0 ? 6 : prec;
                    string digits = global::System.Math.Abs(v).ToString((group ? "#,0." : "0.") + new string('0', p), Inv);
                    return Sign(v < 0, plus, space, paren, digits);
                }
                case 'e':
                case 'E':
                {
                    double v = ((global::java.lang.Number)arg).doubleValue();
                    int p = prec < 0 ? 6 : prec;
                    string digits = global::System.Math.Abs(v).ToString("0." + new string('0', p) + "e+00", Inv);
                    string s = Sign(v < 0, plus, space, paren, digits);
                    return conv == 'E' ? s.ToUpperInvariant() : s;
                }
                case 'g':
                case 'G':
                {
                    double v = ((global::java.lang.Number)arg).doubleValue();
                    int p = prec < 0 ? 6 : (prec == 0 ? 1 : prec);
                    string s = v.ToString("G" + p, Inv).Replace("E", "e");
                    return conv == 'G' ? s.ToUpperInvariant() : s;
                }
                default:
                    return "%" + conv;
            }
        }

        private static string Sign(bool negative, bool plus, bool space, bool paren, string digits)
        {
            if (negative)
            {
                return paren ? "(" + digits + ")" : "-" + digits;
            }
            return plus ? "+" + digits : space ? " " + digits : digits;
        }

        private static void Pad(global::System.Text.StringBuilder sb, string body, int width, bool left, bool zeroPad)
        {
            if (body.Length >= width)
            {
                sb.Append(body);
                return;
            }
            int padLen = width - body.Length;
            if (left)
            {
                sb.Append(body).Append(' ', padLen);
            }
            else if (zeroPad)
            {
                int insert = body.Length > 0 && (body[0] == '-' || body[0] == '+' || body[0] == ' ') ? 1 : 0;
                sb.Append(body, 0, insert).Append('0', padLen).Append(body, insert, body.Length - insert);
            }
            else
            {
                sb.Append(' ', padLen).Append(body);
            }
        }
    }
}
