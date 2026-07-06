namespace java.lang
{
    public abstract class Number : Object, Comparable
    {
        public Number(RawNew r) : base(r)
        {
        }

        public virtual int compareTo(global::java.lang.Object o)
        {
            double a = doubleValue();
            double b = ((Number)o).doubleValue();
            return a < b ? -1 : (a > b ? 1 : 0);
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
