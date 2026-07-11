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
        // mark/position/limit/capacity live on the base (as in java.nio.Buffer) so every view over a
        // buffer shares them, and so a single Unsafe offset for e.g. `position` — which LWJGL locates
        // by value-probing a buffer's int fields — applies to every buffer subtype. `address` is a
        // direct buffer's native base (0 for a heap buffer).
        internal int markPos = -1;
        internal int pos;
        internal int lim;
        internal int cap;
        internal long address;

        // The attachment (java.nio's `att`): a view or duplicate keeps a reference to the buffer it
        // was derived from, so the source's storage outlives the view. MemoryUtil locates this field
        // by value-probing (a view's attachment equals its source) and uses the offset to set the
        // parent when it wraps native memory into typed buffers.
        internal global::java.lang.Object att;

        protected Buffer(global::java.lang.RawNew r) : base(r)
        {
        }

        public int isDirect()
        {
            return address != 0 ? 1 : 0;
        }
    }
}
