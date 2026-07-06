namespace java.lang
{
    public class NoSuchFieldException : Exception
    {
        public NoSuchFieldException(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.NoSuchFieldException";
        }
    }
}
