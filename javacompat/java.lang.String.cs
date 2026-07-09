namespace java.lang
{
    public class String : Object, global::java.lang.CharSequence
    {
        private static readonly global::System.Collections.Concurrent.ConcurrentDictionary<string, String> Pool =
                new global::System.Collections.Concurrent.ConcurrentDictionary<string, String>();

        public string Value;

        public String(RawNew r) : base(r)
        {
            Value = "";
        }

        public void __init__C_V(char[] value)
        {
            Value = value == null ? "" : new string(value);
        }

        public void __init_Ljava_lang_String__V(String original)
        {
            Value = original == null ? "" : original.Value;
        }

        private String(string value) : base(RawNew.I)
        {
            Value = value;
        }

        public static String Intern(string s)
        {
            return Pool.GetOrAdd(s, v => new String(v));
        }

        public static String Wrap(string s)
        {
            return new String(s);
        }

        public static String valueOf(global::java.lang.Object o)
        {
            return o == null ? Wrap("null") : o.toString();
        }

        public static String valueOf(int v)
        {
            return Wrap(global::java.lang.JRuntime.Str(v));
        }

        public static String valueOf(long v)
        {
            return Wrap(global::java.lang.JRuntime.Str(v));
        }

        public static String valueOf(char v)
        {
            return Wrap(v.ToString());
        }

        public static String valueOf(double v)
        {
            return Wrap(global::java.lang.JRuntime.Str(v));
        }

        public static String valueOf(float v)
        {
            return Wrap(global::java.lang.JRuntime.Str(v));
        }

        public static String valueOf(char[] v)
        {
            return v == null ? Wrap("null") : Wrap(new string(v));
        }

        public static String valueOfBoolean(int v)
        {
            return Wrap(v != 0 ? "true" : "false");
        }

        public static String join(global::java.lang.CharSequence delimiter, global::java.lang.CharSequence[] elements)
        {
            var sep = Cs(delimiter);
            var sb = new global::System.Text.StringBuilder();
            for (int i = 0; i < elements.Length; i++)
            {
                if (i > 0)
                {
                    sb.Append(sep);
                }
                sb.Append(Cs(elements[i]));
            }
            return Wrap(sb.ToString());
        }

        public int length()
        {
            return Value.Length;
        }

        public char charAt(int index)
        {
            return Value[index];
        }

        public int isEmpty()
        {
            return Value.Length == 0 ? 1 : 0;
        }

        public String trim()
        {
            int start = 0;
            int end = Value.Length;
            while (start < end && Value[start] <= ' ')
            {
                start++;
            }
            while (end > start && Value[end - 1] <= ' ')
            {
                end--;
            }
            return Wrap(Value.Substring(start, end - start));
        }

        public sbyte[] getBytes(global::java.nio.charset.Charset charset)
        {
            var encoding = charset == null ? global::System.Text.Encoding.UTF8 : charset.encoding;
            return global::java.lang.JRuntime.SignedBytes(encoding.GetBytes(Value));
        }

        public static String format(String fmt, global::java.lang.Object[] args)
        {
            return Wrap(global::java.lang.JRuntime.Format(fmt == null ? null : fmt.Value, args));
        }

        public String substring(int beginIndex)
        {
            return Wrap(Value.Substring(beginIndex));
        }

        public String substring(int beginIndex, int endIndex)
        {
            return Wrap(Value.Substring(beginIndex, endIndex - beginIndex));
        }

        public int indexOf(int ch)
        {
            return Value.IndexOf((char)ch);
        }

        public int indexOf(String str)
        {
            return Value.IndexOf(str.Value, global::System.StringComparison.Ordinal);
        }

        public int startsWith(String prefix)
        {
            return Value.StartsWith(prefix.Value, global::System.StringComparison.Ordinal) ? 1 : 0;
        }

        public int equalsIgnoreCase(String other)
        {
            return other != null && global::System.String.Equals(Value, other.Value,
                    global::System.StringComparison.OrdinalIgnoreCase) ? 1 : 0;
        }

        public int endsWith(String suffix)
        {
            return Value.EndsWith(suffix.Value, global::System.StringComparison.Ordinal) ? 1 : 0;
        }

        public int contains(global::java.lang.CharSequence s)
        {
            return Value.Contains(Cs(s), global::System.StringComparison.Ordinal) ? 1 : 0;
        }

        public char[] toCharArray()
        {
            return Value.ToCharArray();
        }

        public String toLowerCase()
        {
            return Wrap(Value.ToLowerInvariant());
        }

        public String toUpperCase()
        {
            return Wrap(Value.ToUpperInvariant());
        }

        public String concat(String other)
        {
            return Wrap(Value + other.Value);
        }

        public String strip()
        {
            return Wrap(Value.Trim());
        }

        public String stripLeading()
        {
            return Wrap(Value.TrimStart());
        }

        public String stripTrailing()
        {
            return Wrap(Value.TrimEnd());
        }

        public int isBlank()
        {
            foreach (char c in Value)
            {
                if (!char.IsWhiteSpace(c)) { return 0; }
            }
            return 1;
        }

        public global::java.util.stream.IntStream chars()
        {
            var arr = new int[Value.Length];
            for (int i = 0; i < Value.Length; i++) { arr[i] = Value[i]; }
            return global::java.util.stream.IntStream.of(arr);
        }

        public global::java.util.stream.IntStream codePoints()
        {
            return chars();
        }

        public global::java.util.stream.Stream lines()
        {
            var outp = new global::System.Collections.Generic.List<global::java.lang.Object>();
            foreach (var line in Value.Split('\n'))
            {
                outp.Add(Wrap(line.EndsWith("\r") ? line.Substring(0, line.Length - 1) : line));
            }
            if (outp.Count > 0 && Value.EndsWith("\n"))
            {
                outp.RemoveAt(outp.Count - 1);
            }
            return global::java.util.stream.Stream.Wrap(outp);
        }

        public String formatted(global::java.lang.Object[] args)
        {
            return Wrap(global::java.lang.JRuntime.Format(Value, args));
        }

        public String repeat(int count)
        {
            if (count < 0)
            {
                var ex = new global::java.lang.IllegalArgumentException(RawNew.I);
                ex.__init_Ljava_lang_String__V(Wrap("count is negative: " + count));
                throw global::java.lang.JThrow.of(ex);
            }
            return Wrap(count == 0 ? "" : string.Concat(global::System.Linq.Enumerable.Repeat(Value, count)));
        }

        public String replace(char oldChar, char newChar)
        {
            return Wrap(Value.Replace(oldChar, newChar));
        }

        public String replace(global::java.lang.CharSequence target, global::java.lang.CharSequence replacement)
        {
            return Wrap(Value.Replace(Cs(target), Cs(replacement)));
        }

        public String replaceAll(String regex, String replacement)
        {
            return Wrap(global::System.Text.RegularExpressions.Regex.Replace(Value, regex.Value, replacement.Value));
        }

        public String replaceFirst(String regex, String replacement)
        {
            var re = new global::System.Text.RegularExpressions.Regex(regex.Value);
            return Wrap(re.Replace(Value, replacement.Value, 1));
        }

        public int matches(String regex)
        {
            return global::System.Text.RegularExpressions.Regex.IsMatch(Value, "\\A(?:" + regex.Value + ")\\z") ? 1 : 0;
        }

        public int lastIndexOf(int ch)
        {
            return Value.LastIndexOf((char)ch);
        }

        public int lastIndexOf(String str)
        {
            return Value.Length == 0 ? (str.Value.Length == 0 ? 0 : -1)
                    : Value.LastIndexOf(str.Value, global::System.StringComparison.Ordinal);
        }

        public int indexOf(int ch, int fromIndex)
        {
            if (fromIndex < 0) fromIndex = 0;
            if (fromIndex >= Value.Length) return -1;
            return Value.IndexOf((char)ch, fromIndex);
        }

        public int indexOf(String str, int fromIndex)
        {
            if (fromIndex < 0) fromIndex = 0;
            if (fromIndex > Value.Length) return str.Value.Length == 0 ? Value.Length : -1;
            return Value.IndexOf(str.Value, fromIndex, global::System.StringComparison.Ordinal);
        }

        public int compareTo(String other)
        {
            int len1 = Value.Length;
            int len2 = other.Value.Length;
            int lim = len1 < len2 ? len1 : len2;
            for (int k = 0; k < lim; k++)
            {
                char c1 = Value[k];
                char c2 = other.Value[k];
                if (c1 != c2)
                {
                    return c1 - c2;
                }
            }
            return len1 - len2;
        }

        public int compareToIgnoreCase(String other)
        {
            return Wrap(Value.ToLowerInvariant()).compareTo(Wrap(other.Value.ToLowerInvariant()));
        }

        public String[] split(String regex)
        {
            return split(regex, 0);
        }

        public String[] split(String regex, int limit)
        {
            var re = new global::System.Text.RegularExpressions.Regex(regex.Value);
            if (limit == 0 && !re.IsMatch(Value))
            {
                return new String[] { Wrap(Value) };
            }
            string[] parts = limit > 0 ? re.Split(Value, limit) : re.Split(Value);
            int n = parts.Length;
            if (limit == 0)
            {
                while (n > 0 && parts[n - 1].Length == 0)
                {
                    n--;
                }
            }
            var result = new String[n];
            for (int i = 0; i < n; i++)
            {
                result[i] = Wrap(parts[i]);
            }
            return result;
        }

        private static string Cs(global::java.lang.CharSequence cs)
        {
            return cs == null ? "null" : cs.toString().Value;
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is String other && other.Value == Value ? 1 : 0;
        }

        public override int hashCode()
        {
            int h = 0;
            foreach (char c in Value)
            {
                h = 31 * h + c;
            }
            return h;
        }

        public override String toString()
        {
            return this;
        }
    }
}
