namespace java.lang
{
    public class VirtualMachineError : Error
    {
        public VirtualMachineError(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.VirtualMachineError";
        }
    }
}
