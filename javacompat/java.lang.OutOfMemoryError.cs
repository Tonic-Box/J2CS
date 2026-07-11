namespace java.lang
{
    public class OutOfMemoryError : VirtualMachineError
    {
        public OutOfMemoryError(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.OutOfMemoryError";
        }
    }
}
