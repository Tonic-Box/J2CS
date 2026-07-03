namespace java.lang
{
    public class Error : Throwable
    {
        public Error(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.Error";
        }
    }
}
