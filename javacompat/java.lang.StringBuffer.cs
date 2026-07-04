namespace java.lang
{
    public class StringBuffer : Object
    {
        private readonly global::System.Text.StringBuilder sb =
                new global::System.Text.StringBuilder();

        public StringBuffer(RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public StringBuffer append(String s)
        {
            sb.Append(s == null ? "null" : s.Value);
            return this;
        }

        public StringBuffer append(int v)
        {
            sb.Append(v);
            return this;
        }

        public StringBuffer append(Object o)
        {
            sb.Append(JRuntime.Str(o));
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
