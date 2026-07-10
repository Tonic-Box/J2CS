namespace java.lang
{
    public class CloneNotSupportedException : Exception
    {
        public CloneNotSupportedException(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.CloneNotSupportedException";
        }
    }
}
