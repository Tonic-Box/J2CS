namespace java.lang
{
    public class NoSuchFieldError : Error
    {
        public NoSuchFieldError(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.NoSuchFieldError";
        }
    }
}
