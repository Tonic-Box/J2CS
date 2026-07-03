namespace java.lang
{
    public class ArrayStoreException : RuntimeException
    {
        public ArrayStoreException(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.ArrayStoreException";
        }
    }
}
