namespace java.nio
{
    /// <summary>
    /// Byte-level storage backing a buffer: either a managed <c>sbyte[]</c> (heap buffers from
    /// allocate/wrap) or an unmanaged block at a native address (direct buffers from allocateDirect).
    /// A direct buffer must sit in real native memory so its address can be handed to native code,
    /// and every typed view over a buffer shares the one store. Reads/writes are little- or
    /// big-endian per the owning buffer's order.
    /// </summary>
    internal sealed unsafe class ByteStore
    {
        internal readonly sbyte[] Array;
        internal readonly long Address;
        internal readonly int Length;
        private readonly bool owns;

        internal ByteStore(sbyte[] array)
        {
            Array = array;
            Length = array.Length;
        }

        /// <param name="owns">
        /// True when this store allocated the block and must free it on finalize (mirroring the
        /// JVM Cleaner for allocateDirect); false when it wraps memory owned elsewhere (e.g. a
        /// buffer over a native address LWJGL manages).
        /// </param>
        internal ByteStore(long address, int length, bool owns)
        {
            Address = address;
            Length = length;
            this.owns = owns;
        }

        ~ByteStore()
        {
            if (Array == null && Address != 0 && owns)
            {
                global::System.Runtime.InteropServices.NativeMemory.Free((void*)Address);
            }
        }

        internal bool IsDirect => Array == null;

        internal sbyte this[int i]
        {
            get => Array != null ? Array[i] : (sbyte)((byte*)Address)[i];
            set
            {
                if (Array != null)
                {
                    Array[i] = value;
                }
                else
                {
                    ((byte*)Address)[i] = (byte)value;
                }
            }
        }

        internal long ReadBytes(int idx, int n, bool le)
        {
            long v = 0;
            if (le)
            {
                for (int i = n - 1; i >= 0; i--) { v = v << 8 | (uint)(this[idx + i] & 0xFF); }
            }
            else
            {
                for (int i = 0; i < n; i++) { v = v << 8 | (uint)(this[idx + i] & 0xFF); }
            }
            return v;
        }

        internal void WriteBytes(int idx, int n, long v, bool le)
        {
            if (le)
            {
                for (int i = 0; i < n; i++) { this[idx + i] = (sbyte)v; v >>= 8; }
            }
            else
            {
                for (int i = n - 1; i >= 0; i--) { this[idx + i] = (sbyte)v; v >>= 8; }
            }
        }
    }
}
