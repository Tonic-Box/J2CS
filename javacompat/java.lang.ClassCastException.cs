namespace java.lang
{
    public class ClassCastException : RuntimeException
    {
        public ClassCastException(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.ClassCastException";
        }
    }
}
