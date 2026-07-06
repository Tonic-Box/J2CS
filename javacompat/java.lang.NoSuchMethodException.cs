namespace java.lang
{
    public class NoSuchMethodException : Exception
    {
        public NoSuchMethodException(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.NoSuchMethodException";
        }
    }
}
