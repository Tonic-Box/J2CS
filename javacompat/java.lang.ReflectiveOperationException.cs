namespace java.lang
{
    public class ReflectiveOperationException : Exception
    {
        public ReflectiveOperationException(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.ReflectiveOperationException";
        }
    }
}
