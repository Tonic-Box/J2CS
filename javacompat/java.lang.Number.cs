namespace java.lang
{
    public abstract class Number : Object
    {
        public Number(RawNew r) : base(r)
        {
        }

        public abstract int intValue();

        public abstract long longValue();

        public abstract float floatValue();

        public abstract double doubleValue();

        public virtual short shortValue()
        {
            return (short)intValue();
        }

        public virtual sbyte byteValue()
        {
            return (sbyte)intValue();
        }
    }
}
