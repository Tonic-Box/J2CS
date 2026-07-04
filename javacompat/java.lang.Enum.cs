namespace java.lang
{
    public class Enum : Object
    {
        protected String __name;
        protected int __ordinal;

        public Enum(RawNew r) : base(r)
        {
        }

        public void __init_Ljava_lang_String_I_V(String name, int ordinal)
        {
            __name = name;
            __ordinal = ordinal;
        }

        public String name()
        {
            return __name;
        }

        public int ordinal()
        {
            return __ordinal;
        }

        public override String toString()
        {
            return __name;
        }

        public virtual int compareTo(Enum other)
        {
            return __ordinal - other.__ordinal;
        }
    }
}
