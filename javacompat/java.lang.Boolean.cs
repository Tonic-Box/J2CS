namespace java.lang
{
    public sealed class Boolean : Object
    {
        public static readonly global::java.lang.Class TYPE = global::java.lang.Class.Of("boolean");

        public static readonly Boolean TRUE = new Boolean(1);
        public static readonly Boolean FALSE = new Boolean(0);

        private readonly int value;

        public Boolean(RawNew r) : base(r)
        {
        }

        private Boolean(int v) : base(RawNew.I)
        {
            value = v;
        }

        public static Boolean valueOf(int v)
        {
            return v != 0 ? TRUE : FALSE;
        }

        public static int getBoolean(String name)
        {
            string v = name == null ? null : global::System.Environment.GetEnvironmentVariable(name.Value);
            return v != null && v.Equals("true", global::System.StringComparison.OrdinalIgnoreCase) ? 1 : 0;
        }

        public static int parseBoolean(String s)
        {
            return s != null && s.Value.Equals("true", global::System.StringComparison.OrdinalIgnoreCase) ? 1 : 0;
        }

        public static String toString(int v)
        {
            return String.Wrap(v != 0 ? "true" : "false");
        }

        public int booleanValue()
        {
            return value;
        }

        public int compareTo(Boolean other)
        {
            return value == other.value ? 0 : value == 0 ? -1 : 1;
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Boolean other && other.value == value ? 1 : 0;
        }

        public override int hashCode()
        {
            return value != 0 ? 1231 : 1237;
        }

        public override String toString()
        {
            return String.Wrap(value != 0 ? "true" : "false");
        }
    }
}
