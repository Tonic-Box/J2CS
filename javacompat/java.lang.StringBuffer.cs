namespace java.lang
{
    public class StringBuffer : Object, global::java.lang.CharSequence
    {
        private readonly global::System.Text.StringBuilder sb = new global::System.Text.StringBuilder();

        public StringBuffer(RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_I_V(int capacity)
        {
        }

        public void __init_Ljava_lang_String__V(String s)
        {
            sb.Append(s == null ? "null" : s.Value);
        }

        public void __init_Ljava_lang_CharSequence__V(global::java.lang.CharSequence s)
        {
            sb.Append(s == null ? "null" : s.toString().Value);
        }

        public StringBuffer append(int v) { sb.Append(JRuntime.Str(v)); return this; }
        public StringBuffer append(long v) { sb.Append(JRuntime.Str(v)); return this; }
        public StringBuffer append(float v) { sb.Append(JRuntime.Str(v)); return this; }
        public StringBuffer append(double v) { sb.Append(JRuntime.Str(v)); return this; }
        public StringBuffer append(char v) { sb.Append(v); return this; }
        public StringBuffer append_Z(int v) { sb.Append(JRuntime.StrZ(v)); return this; }
        public StringBuffer append(String v) { sb.Append(JRuntime.Str(v)); return this; }
        public StringBuffer append(Object v) { sb.Append(JRuntime.Str(v)); return this; }
        public StringBuffer append(char[] v) { sb.Append(v); return this; }

        public StringBuffer append(char[] v, int offset, int len)
        {
            sb.Append(v, offset, len);
            return this;
        }

        public StringBuffer append(global::java.lang.CharSequence v)
        {
            sb.Append(v == null ? "null" : v.toString().Value);
            return this;
        }

        public StringBuffer append(global::java.lang.CharSequence v, int start, int end)
        {
            string s = v == null ? "null" : v.toString().Value;
            sb.Append(s, start, end - start);
            return this;
        }

        public StringBuffer appendCodePoint(int codePoint)
        {
            sb.Append(global::System.Char.ConvertFromUtf32(codePoint));
            return this;
        }

        public StringBuffer insert(int offset, String v) { sb.Insert(offset, JRuntime.Str(v)); return this; }
        public StringBuffer insert(int offset, char v) { sb.Insert(offset, v); return this; }
        public StringBuffer insert(int offset, int v) { sb.Insert(offset, JRuntime.Str(v)); return this; }
        public StringBuffer insert(int offset, long v) { sb.Insert(offset, JRuntime.Str(v)); return this; }
        public StringBuffer insert(int offset, float v) { sb.Insert(offset, JRuntime.Str(v)); return this; }
        public StringBuffer insert(int offset, double v) { sb.Insert(offset, JRuntime.Str(v)); return this; }
        public StringBuffer insert(int offset, Object v) { sb.Insert(offset, JRuntime.Str(v)); return this; }
        public StringBuffer insert(int offset, char[] v) { sb.Insert(offset, v); return this; }
        public StringBuffer insert_Z(int offset, int v) { sb.Insert(offset, JRuntime.StrZ(v)); return this; }

        public StringBuffer insert(int offset, char[] v, int off2, int len)
        {
            sb.Insert(offset, new string(v, off2, len));
            return this;
        }

        public StringBuffer insert(int offset, global::java.lang.CharSequence v)
        {
            sb.Insert(offset, v == null ? "null" : v.toString().Value);
            return this;
        }

        public StringBuffer insert(int offset, global::java.lang.CharSequence v, int start, int end)
        {
            string s = v == null ? "null" : v.toString().Value;
            sb.Insert(offset, s.Substring(start, end - start));
            return this;
        }

        public StringBuffer reverse()
        {
            var chars = sb.ToString().ToCharArray();
            global::System.Array.Reverse(chars);
            sb.Clear();
            sb.Append(chars);
            return this;
        }

        public StringBuffer deleteCharAt(int index) { sb.Remove(index, 1); return this; }
        public StringBuffer delete(int start, int end) { sb.Remove(start, end - start); return this; }

        public StringBuffer replace(int start, int end, String str)
        {
            sb.Remove(start, end - start);
            sb.Insert(start, JRuntime.Str(str));
            return this;
        }

        public void setLength(int newLength)
        {
            if (newLength > sb.Length)
            {
                sb.Append(new string('\0', newLength - sb.Length));
            }
            else
            {
                sb.Length = newLength;
            }
        }

        public char charAt(int index) { return sb[index]; }
        public void setCharAt(int index, char c) { sb[index] = c; }
        public int length() { return sb.Length; }
        public int capacity() { return sb.Capacity; }
        public void ensureCapacity(int minimumCapacity) { sb.EnsureCapacity(minimumCapacity); }
        public void trimToSize() { }

        public int indexOf(String str)
        {
            return sb.ToString().IndexOf(JRuntime.Str(str), global::System.StringComparison.Ordinal);
        }

        public int indexOf(String str, int fromIndex)
        {
            if (fromIndex < 0) { fromIndex = 0; }
            string s = sb.ToString();
            if (fromIndex > s.Length) { return -1; }
            return s.IndexOf(JRuntime.Str(str), fromIndex, global::System.StringComparison.Ordinal);
        }

        public int lastIndexOf(String str)
        {
            return sb.ToString().LastIndexOf(JRuntime.Str(str), global::System.StringComparison.Ordinal);
        }

        public int lastIndexOf(String str, int fromIndex)
        {
            string s = sb.ToString();
            string needle = JRuntime.Str(str);
            if (fromIndex < 0) { return -1; }
            int start = global::System.Math.Min(fromIndex, s.Length - 1);
            if (start < 0) { return needle.Length == 0 ? 0 : -1; }
            for (int i = global::System.Math.Min(start, s.Length - needle.Length); i >= 0; i--)
            {
                if (string.CompareOrdinal(s.Substring(i, needle.Length), needle) == 0) { return i; }
            }
            return needle.Length == 0 ? 0 : -1;
        }

        public String substring(int start) { return String.Wrap(sb.ToString().Substring(start)); }
        public String substring(int start, int end) { return String.Wrap(sb.ToString().Substring(start, end - start)); }
        public global::java.lang.CharSequence subSequence(int start, int end) { return substring(start, end); }

        public int codePointAt(int index)
        {
            char c1 = sb[index];
            if (global::System.Char.IsHighSurrogate(c1) && index + 1 < sb.Length
                    && global::System.Char.IsLowSurrogate(sb[index + 1]))
            {
                return global::System.Char.ConvertToUtf32(c1, sb[index + 1]);
            }
            return c1;
        }

        public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
        {
            sb.CopyTo(srcBegin, dst, dstBegin, srcEnd - srcBegin);
        }

        public override String toString()
        {
            return String.Wrap(sb.ToString());
        }
    }
}
