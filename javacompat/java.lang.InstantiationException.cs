namespace java.lang
{
    public class InstantiationException : Exception
    {
        public InstantiationException(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.InstantiationException";
        }
    }
}
