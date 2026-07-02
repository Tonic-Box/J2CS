namespace java.lang
{
    public class Object
    {
        public Object()
        {
        }

        public Object(RawNew r)
        {
        }

        public void __init__V()
        {
        }

        public virtual int hashCode()
        {
            return global::System.Runtime.CompilerServices.RuntimeHelpers.GetHashCode(this);
        }

        public virtual global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(GetType().FullName + "@" + hashCode().ToString("x"));
        }

        public virtual int equals(global::java.lang.Object o)
        {
            return ReferenceEquals(this, o) ? 1 : 0;
        }
    }
}
