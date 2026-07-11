namespace java.nio
{
    /// <summary>
    /// Base of the NIO buffer hierarchy. <c>address</c> is the native address of this buffer's
    /// element zero for a direct buffer (zero for a heap buffer); MemoryUtil reads it reflectively
    /// through sun.misc.Unsafe — <c>objectFieldOffset(Buffer.class.getDeclaredField("address"))</c>
    /// then <c>getLong(buffer, offset)</c> — to hand the buffer's memory to native code. Subclasses
    /// keep their own position/limit/capacity and element storage.
    /// </summary>
    public abstract class Buffer : global::java.lang.Object
    {
        internal long address;

        protected Buffer(global::java.lang.RawNew r) : base(r)
        {
        }

        public int isDirect()
        {
            return address != 0 ? 1 : 0;
        }
    }
}
