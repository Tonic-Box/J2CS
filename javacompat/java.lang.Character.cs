namespace java.lang
{
    public sealed class Character : Object
    {
        private static readonly Character[] Cache = BuildCache();

        private readonly char value;

        public Character(RawNew r) : base(r)
        {
        }

        private Character(char v) : base(RawNew.I)
        {
            value = v;
        }

        private static Character[] BuildCache()
        {
            var cache = new Character[128];
            for (int i = 0; i < 128; i++)
            {
                cache[i] = new Character((char)i);
            }
            return cache;
        }

        public static Character valueOf(char v)
        {
            if (v <= 127)
            {
                return Cache[v];
            }
            return new Character(v);
        }

        public static String toString(char v)
        {
            return String.Wrap(v.ToString());
        }

        public char charValue()
        {
            return value;
        }

        public int compareTo(Character other)
        {
            return value - other.value;
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Character other && other.value == value ? 1 : 0;
        }

        public override int hashCode()
        {
            return value;
        }

        public override String toString()
        {
            return String.Wrap(value.ToString());
        }
    }
}
