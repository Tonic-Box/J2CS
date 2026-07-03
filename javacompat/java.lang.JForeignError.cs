namespace java.lang
{
    public class JForeignError : Error
    {
        public JForeignError(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.JForeignError";
        }
    }
}
