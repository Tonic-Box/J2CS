namespace java.lang
{
    public class StringBuilder : Object
    {
        private readonly global::System.Text.StringBuilder sb = new global::System.Text.StringBuilder();

        public StringBuilder(RawNew r) : base(r)
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
            sb.Append(JRuntime.Str(s));
        }

        public StringBuilder append(int v)
        {
            sb.Append(JRuntime.Str(v));
            return this;
        }

        public StringBuilder append(long v)
        {
            sb.Append(JRuntime.Str(v));
            return this;
        }

        public StringBuilder append(float v)
        {
            sb.Append(JRuntime.Str(v));
            return this;
        }

        public StringBuilder append(double v)
        {
            sb.Append(JRuntime.Str(v));
            return this;
        }

        public StringBuilder append(char v)
        {
            sb.Append(v);
            return this;
        }

        public StringBuilder append_Z(int v)
        {
            sb.Append(JRuntime.StrZ(v));
            return this;
        }

        public StringBuilder append(String v)
        {
            sb.Append(JRuntime.Str(v));
            return this;
        }

        public StringBuilder append(Object v)
        {
            sb.Append(JRuntime.Str(v));
            return this;
        }

        public StringBuilder append(char[] v)
        {
            sb.Append(v);
            return this;
        }

        public StringBuilder append(global::java.lang.CharSequence v)
        {
            sb.Append(v == null ? "null" : v.toString().Value);
            return this;
        }

        public StringBuilder insert(int offset, String v)
        {
            sb.Insert(offset, JRuntime.Str(v));
            return this;
        }

        public StringBuilder insert(int offset, char v)
        {
            sb.Insert(offset, v);
            return this;
        }

        public StringBuilder insert(int offset, int v)
        {
            sb.Insert(offset, JRuntime.Str(v));
            return this;
        }

        public StringBuilder insert(int offset, Object v)
        {
            sb.Insert(offset, JRuntime.Str(v));
            return this;
        }

        public StringBuilder reverse()
        {
            var chars = sb.ToString().ToCharArray();
            global::System.Array.Reverse(chars);
            sb.Clear();
            sb.Append(chars);
            return this;
        }

        public StringBuilder deleteCharAt(int index)
        {
            sb.Remove(index, 1);
            return this;
        }

        public StringBuilder delete(int start, int end)
        {
            sb.Remove(start, end - start);
            return this;
        }

        public StringBuilder replace(int start, int end, String str)
        {
            sb.Remove(start, end - start);
            sb.Insert(start, JRuntime.Str(str));
            return this;
        }

        public void setLength(int newLength)
        {
            sb.Length = newLength;
        }

        public char charAt(int index)
        {
            return sb[index];
        }

        public void setCharAt(int index, char c)
        {
            sb[index] = c;
        }

        public int indexOf(String str)
        {
            return sb.ToString().IndexOf(JRuntime.Str(str), global::System.StringComparison.Ordinal);
        }

        public int length()
        {
            return sb.Length;
        }

        public override String toString()
        {
            return String.Wrap(sb.ToString());
        }
    }
}
