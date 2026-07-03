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
