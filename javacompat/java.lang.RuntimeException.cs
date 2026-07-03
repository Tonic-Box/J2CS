namespace java.lang
{
    public class RuntimeException : Exception
    {
        public RuntimeException(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.RuntimeException";
        }
    }
}
