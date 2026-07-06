namespace java.lang
{
    public class IllegalAccessException : Exception
    {
        public IllegalAccessException(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.IllegalAccessException";
        }
    }
}
