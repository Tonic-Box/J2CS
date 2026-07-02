namespace java.lang
{
    public class String : Object
    {
        private static readonly global::System.Collections.Concurrent.ConcurrentDictionary<string, String> Pool =
                new global::System.Collections.Concurrent.ConcurrentDictionary<string, String>();

        public readonly string Value;

        public String(RawNew r) : base(r)
        {
            Value = "";
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
