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

        public void __init__CII_V(char[] value, int offset, int count)
        {
            Value = value == null ? "" : new string(value, offset, count);
        }

        public void __init_Ljava_lang_String__V(String original)
        {
            Value = original == null ? "" : original.Value;
        }

        public void __init__B_V(sbyte[] bytes)
        {
            Value = bytes == null ? "" : new global::System.Text.UTF8Encoding(false).GetString(global::java.lang.JRuntime.UnsignedBytes(bytes));
        }

        public void __init__BLjava_nio_charset_Charset__V(sbyte[] bytes, global::java.nio.charset.Charset cs)
        {
            var enc = cs == null ? new global::System.Text.UTF8Encoding(false) : cs.encoding;
            Value = bytes == null ? "" : enc.GetString(global::java.lang.JRuntime.UnsignedBytes(bytes));
        }

        public void __init__BII_V(sbyte[] bytes, int offset, int length)
        {
            Value = new global::System.Text.UTF8Encoding(false).GetString(global::java.lang.JRuntime.UnsignedBytes(bytes), offset, length);
        }

        public void __init__BIILjava_nio_charset_Charset__V(sbyte[] bytes, int offset, int length, global::java.nio.charset.Charset cs)
        {
            var enc = cs == null ? new global::System.Text.UTF8Encoding(false) : cs.encoding;
            Value = enc.GetString(global::java.lang.JRuntime.UnsignedBytes(bytes), offset, length);
        }

        public void __init__BIII_V(sbyte[] ascii, int hibyte, int offset, int count)
        {
            char[] chars = new char[count];
            int hi = (hibyte & 0xFF) << 8;
            for (int i = 0; i < count; i++)
            {
                chars[i] = (char)(hi | (ascii[offset + i] & 0xFF));
            }
            Value = new string(chars);
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

        public sbyte[] getBytes(global::java.lang.String charsetName)
        {
            global::System.Text.Encoding encoding;
            try
            {
                encoding = charsetName == null
                    ? new global::System.Text.UTF8Encoding(false)
                    : global::System.Text.Encoding.GetEncoding(charsetName.Value);
            }
            catch (global::System.Exception)
            {
                encoding = new global::System.Text.UTF8Encoding(false);
            }
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

        public global::java.lang.CharSequence subSequence(int beginIndex, int endIndex)
        {
            return substring(beginIndex, endIndex);
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
            return Wrap(global::System.Text.RegularExpressions.Regex.Replace(
                    Value, global::java.lang.JRuntime.TranslateJavaRegex(regex.Value), replacement.Value));
        }

        public String replaceFirst(String regex, String replacement)
        {
            var re = new global::System.Text.RegularExpressions.Regex(
                    global::java.lang.JRuntime.TranslateJavaRegex(regex.Value));
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
            var re = new global::System.Text.RegularExpressions.Regex(
                    global::java.lang.JRuntime.TranslateJavaRegex(regex.Value));
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

        public static String valueOf(char[] data, int offset, int count)
        {
            return Wrap(new string(data, offset, count));
        }

        public static String copyValueOf(char[] data)
        {
            return Wrap(new string(data));
        }

        public static String copyValueOf(char[] data, int offset, int count)
        {
            return Wrap(new string(data, offset, count));
        }

        public static String join(global::java.lang.CharSequence delimiter, global::java.lang.Iterable elements)
        {
            var sep = Cs(delimiter);
            var sb = new global::System.Text.StringBuilder();
            var it = elements.iterator();
            bool first = true;
            while (it.hasNext() != 0)
            {
                var e = it.next();
                if (!first) { sb.Append(sep); }
                first = false;
                sb.Append(e == null ? "null" : ((global::java.lang.CharSequence)e).toString().Value);
            }
            return Wrap(sb.ToString());
        }

        public String intern()
        {
            return Intern(Value);
        }

        public int codePointAt(int index)
        {
            char c1 = Value[index];
            if (char.IsHighSurrogate(c1) && index + 1 < Value.Length && char.IsLowSurrogate(Value[index + 1]))
            {
                return char.ConvertToUtf32(c1, Value[index + 1]);
            }
            return c1;
        }

        public int codePointBefore(int index)
        {
            char c2 = Value[index - 1];
            if (char.IsLowSurrogate(c2) && index - 2 >= 0 && char.IsHighSurrogate(Value[index - 2]))
            {
                return char.ConvertToUtf32(Value[index - 2], c2);
            }
            return c2;
        }

        public int codePointCount(int beginIndex, int endIndex)
        {
            int n = 0;
            for (int i = beginIndex; i < endIndex; )
            {
                char c = Value[i];
                if (char.IsHighSurrogate(c) && i + 1 < endIndex && char.IsLowSurrogate(Value[i + 1]))
                {
                    i += 2;
                }
                else
                {
                    i++;
                }
                n++;
            }
            return n;
        }

        public int offsetByCodePoints(int index, int codePointOffset)
        {
            int i = index;
            if (codePointOffset >= 0)
            {
                for (int k = 0; k < codePointOffset; k++)
                {
                    if (char.IsHighSurrogate(Value[i]) && i + 1 < Value.Length && char.IsLowSurrogate(Value[i + 1]))
                    {
                        i += 2;
                    }
                    else
                    {
                        i++;
                    }
                }
            }
            else
            {
                for (int k = codePointOffset; k < 0; k++)
                {
                    i--;
                    if (char.IsLowSurrogate(Value[i]) && i - 1 >= 0 && char.IsHighSurrogate(Value[i - 1]))
                    {
                        i--;
                    }
                }
            }
            return i;
        }

        public int regionMatches(int toffset, String other, int ooffset, int len)
        {
            if (ooffset < 0 || toffset < 0 || len < 0
                    || toffset > Value.Length - len || ooffset > other.Value.Length - len)
            {
                return 0;
            }
            return string.CompareOrdinal(Value.Substring(toffset, len), other.Value.Substring(ooffset, len)) == 0 ? 1 : 0;
        }

        public int regionMatches(int ignoreCase, int toffset, String other, int ooffset, int len)
        {
            if (ooffset < 0 || toffset < 0 || len < 0
                    || toffset > Value.Length - len || ooffset > other.Value.Length - len)
            {
                return 0;
            }
            var cmp = ignoreCase != 0
                    ? global::System.StringComparison.OrdinalIgnoreCase
                    : global::System.StringComparison.Ordinal;
            return string.Equals(Value.Substring(toffset, len), other.Value.Substring(ooffset, len), cmp) ? 1 : 0;
        }

        public int contentEquals(global::java.lang.CharSequence cs)
        {
            return string.Equals(Value, cs.toString().Value, global::System.StringComparison.Ordinal) ? 1 : 0;
        }

        public int contentEquals(global::java.lang.StringBuffer buffer)
        {
            return string.Equals(Value, buffer.toString().Value, global::System.StringComparison.Ordinal) ? 1 : 0;
        }

        public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
        {
            Value.CopyTo(srcBegin, dst, dstBegin, srcEnd - srcBegin);
        }

        private static int IndexOfNonWhitespace(string s)
        {
            int i = 0;
            while (i < s.Length && char.IsWhiteSpace(s[i])) { i++; }
            return i;
        }

        private static int LastIndexOfNonWhitespace(string s)
        {
            int i = s.Length;
            while (i > 0 && char.IsWhiteSpace(s[i - 1])) { i--; }
            return i;
        }

        private global::System.Collections.Generic.List<string> LinesList()
        {
            var outp = new global::System.Collections.Generic.List<string>();
            foreach (var line in Value.Split('\n'))
            {
                outp.Add(line.EndsWith("\r") ? line.Substring(0, line.Length - 1) : line);
            }
            if (outp.Count > 0 && Value.EndsWith("\n"))
            {
                outp.RemoveAt(outp.Count - 1);
            }
            return outp;
        }

        public String indent(int n)
        {
            if (Value.Length == 0) { return Wrap(""); }
            var sb = new global::System.Text.StringBuilder();
            foreach (var line in LinesList())
            {
                string outLine;
                if (n > 0)
                {
                    outLine = new string(' ', n) + line;
                }
                else if (n == int.MinValue)
                {
                    outLine = line.Substring(IndexOfNonWhitespace(line));
                }
                else if (n < 0)
                {
                    outLine = line.Substring(global::System.Math.Min(-n, IndexOfNonWhitespace(line)));
                }
                else
                {
                    outLine = line;
                }
                sb.Append(outLine).Append('\n');
            }
            return Wrap(sb.ToString());
        }

        public String stripIndent()
        {
            int length = Value.Length;
            if (length == 0) { return Wrap(""); }
            char lastChar = Value[length - 1];
            bool optOut = lastChar == '\n' || lastChar == '\r';
            var lines = LinesList();
            int outdent = 0;
            if (!optOut)
            {
                outdent = int.MaxValue;
                foreach (var line in lines)
                {
                    int lead = IndexOfNonWhitespace(line);
                    if (lead != line.Length)
                    {
                        outdent = global::System.Math.Min(outdent, lead);
                    }
                }
                string lastLine = lines[lines.Count - 1];
                bool blank = IndexOfNonWhitespace(lastLine) == lastLine.Length;
                if (blank)
                {
                    outdent = global::System.Math.Min(outdent, lastLine.Length);
                }
                if (outdent == int.MaxValue) { outdent = 0; }
            }
            var sb = new global::System.Text.StringBuilder();
            for (int li = 0; li < lines.Count; li++)
            {
                string line = lines[li];
                int firstNon = IndexOfNonWhitespace(line);
                int lastNon = LastIndexOfNonWhitespace(line);
                string mapped = firstNon > lastNon
                        ? ""
                        : line.Substring(global::System.Math.Min(outdent, firstNon), lastNon - global::System.Math.Min(outdent, firstNon));
                sb.Append(mapped);
                if (li < lines.Count - 1) { sb.Append('\n'); }
            }
            if (optOut) { sb.Append('\n'); }
            return Wrap(sb.ToString());
        }

        public String translateEscapes()
        {
            if (Value.Length == 0) { return Wrap(""); }
            char[] chars = Value.ToCharArray();
            int length = chars.Length;
            int from = 0;
            int to = 0;
            while (from < length)
            {
                char ch = chars[from++];
                if (ch == '\\')
                {
                    ch = from < length ? chars[from++] : '\0';
                    switch (ch)
                    {
                        case 'b': ch = '\b'; break;
                        case 'f': ch = '\f'; break;
                        case 'n': ch = '\n'; break;
                        case 'r': ch = '\r'; break;
                        case 's': ch = ' '; break;
                        case 't': ch = '\t'; break;
                        case '\'':
                        case '\"':
                        case '\\':
                            break;
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        {
                            int limit = global::System.Math.Min(from + (ch <= '3' ? 2 : 1), length);
                            int code = ch - '0';
                            while (from < limit)
                            {
                                ch = chars[from];
                                if (ch < '0' || '7' < ch) { break; }
                                from++;
                                code = (code << 3) | (ch - '0');
                            }
                            ch = (char)code;
                            break;
                        }
                        case '\n':
                            continue;
                        case '\r':
                            if (from < length && chars[from] == '\n') { from++; }
                            continue;
                        default:
                        {
                            var ex = new global::java.lang.IllegalArgumentException(RawNew.I);
                            ex.__init_Ljava_lang_String__V(Wrap("Invalid escape sequence: \\" + ch));
                            throw global::java.lang.JThrow.of(ex);
                        }
                    }
                }
                chars[to++] = ch;
            }
            return Wrap(new string(chars, 0, to));
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
