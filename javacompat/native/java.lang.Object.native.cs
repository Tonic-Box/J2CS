namespace java.lang
{
    partial class Object
    {
        public virtual int hashCode()
        {
            return global::System.Runtime.CompilerServices.RuntimeHelpers.GetHashCode(this);
        }

        public virtual global::java.lang.String toString()
        {
            return String.Wrap(GetType().FullName + "@" + hashCode().ToString("x"));
        }
    }
}
