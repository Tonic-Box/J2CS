namespace java.lang
{
    public class UnsatisfiedLinkError : LinkageError
    {
        public UnsatisfiedLinkError(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.UnsatisfiedLinkError";
        }
    }
}
