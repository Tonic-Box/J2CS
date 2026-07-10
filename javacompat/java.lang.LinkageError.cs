namespace java.lang
{
    public class LinkageError : Error
    {
        public LinkageError(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.LinkageError";
        }
    }
}
