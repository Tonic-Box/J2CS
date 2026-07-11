namespace java.lang
{
    public class ClassNotFoundException : ReflectiveOperationException
    {
        public ClassNotFoundException(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.ClassNotFoundException";
        }
    }
}
