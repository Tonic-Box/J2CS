namespace java.nio
{
    public sealed unsafe class ByteBuffer : global::java.nio.Buffer
    {
        private ByteStore store;
        private readonly int offset;
        private bool little;

        /// <summary>
        /// The element storage. Normally set in a constructor, but LWJGL builds direct buffers with
        /// <c>Unsafe.allocateInstance</c> (bypassing the constructor) and then pokes <c>address</c> and
        /// <c>capacity</c> through reflected field offsets — leaving this field null. In that case the
        /// backing is materialized on first access as a non-owning view over the poked native address.
        /// </summary>
        private ByteStore Store()
        {
            return store ??= new ByteStore(address, cap, false);
        }

        public ByteBuffer(global::java.lang.RawNew r) : base(r)
        {
            store = new ByteStore(new sbyte[0]);
        }

        internal ByteBuffer(ByteStore backing) : this(backing, 0, backing.Length, false)
        {
        }

        internal ByteBuffer(ByteStore backing, int off, int capacity, bool le) : base(global::java.lang.RawNew.I)
        {
            store = backing;
            offset = off;
            cap = capacity;
            lim = capacity;
            little = le;
            address = backing.IsDirect ? backing.Address + off : 0;
        }

        public static ByteBuffer allocate(int capacity)
        {
            return new ByteBuffer(new ByteStore(new sbyte[capacity]));
        }

        public static ByteBuffer allocateDirect(int capacity)
        {
            void* mem = global::System.Runtime.InteropServices.NativeMemory.AllocZeroed((nuint)capacity);
            return new ByteBuffer(new ByteStore((long)mem, capacity, true));
        }

        public static ByteBuffer wrap(sbyte[] array)
        {
            return new ByteBuffer(new ByteStore(array));
        }

        public int capacity() { return cap; }
        public int position() { return pos; }
        public int limit() { return lim; }
        public int remaining() { return lim - pos; }
        public int hasRemaining() { return pos < lim ? 1 : 0; }

        public ByteOrder order() { return little ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN; }

        public ByteBuffer order(ByteOrder bo)
        {
            little = ReferenceEquals(bo, ByteOrder.LITTLE_ENDIAN);
            return this;
        }

        public ByteBuffer position(int newPosition)
        {
            pos = newPosition;
            if (markPos > pos) { markPos = -1; }
            return this;
        }

        public ByteBuffer limit(int newLimit)
        {
            lim = newLimit;
            if (pos > lim) { pos = lim; }
            if (markPos > lim) { markPos = -1; }
            return this;
        }

        public ByteBuffer mark() { markPos = pos; return this; }

        public ByteBuffer reset()
        {
            if (markPos < 0)
            {
                throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I));
            }
            pos = markPos;
            return this;
        }

        public ByteBuffer flip() { lim = pos; pos = 0; markPos = -1; return this; }
        public ByteBuffer clear() { pos = 0; lim = cap; markPos = -1; return this; }
        public ByteBuffer rewind() { pos = 0; markPos = -1; return this; }

        public sbyte get() { return Store()[offset + pos++]; }
        public sbyte get(int index) { return Store()[offset + index]; }
        public ByteBuffer put(sbyte b) { Store()[offset + pos++] = b; return this; }
        public ByteBuffer put(int index, sbyte b) { Store()[offset + index] = b; return this; }

        public ByteBuffer get(sbyte[] dst)
        {
            for (int i = 0; i < dst.Length; i++) { dst[i] = Store()[offset + pos++]; }
            return this;
        }

        public ByteBuffer get(sbyte[] dst, int off, int len)
        {
            for (int i = 0; i < len; i++) { dst[off + i] = Store()[offset + pos++]; }
            return this;
        }

        public ByteBuffer put(sbyte[] src)
        {
            for (int i = 0; i < src.Length; i++) { Store()[offset + pos++] = src[i]; }
            return this;
        }

        public ByteBuffer put(ByteBuffer src)
        {
            int n = src.remaining();
            for (int i = 0; i < n; i++) { Store()[offset + pos++] = src.get(); }
            return this;
        }

        public short getShort() { short v = (short)Store().ReadBytes(offset + pos, 2, little); pos += 2; return v; }
        public short getShort(int index) { return (short)Store().ReadBytes(offset + index, 2, little); }
        public ByteBuffer putShort(short v) { Store().WriteBytes(offset + pos, 2, v, little); pos += 2; return this; }
        public ByteBuffer putShort(int index, short v) { Store().WriteBytes(offset + index, 2, v, little); return this; }

        public char getChar() { char v = (char)Store().ReadBytes(offset + pos, 2, little); pos += 2; return v; }
        public char getChar(int index) { return (char)Store().ReadBytes(offset + index, 2, little); }
        public ByteBuffer putChar(char v) { Store().WriteBytes(offset + pos, 2, v, little); pos += 2; return this; }
        public ByteBuffer putChar(int index, char v) { Store().WriteBytes(offset + index, 2, v, little); return this; }

        public int getInt() { int v = (int)Store().ReadBytes(offset + pos, 4, little); pos += 4; return v; }
        public int getInt(int index) { return (int)Store().ReadBytes(offset + index, 4, little); }
        public ByteBuffer putInt(int v) { Store().WriteBytes(offset + pos, 4, v, little); pos += 4; return this; }
        public ByteBuffer putInt(int index, int v) { Store().WriteBytes(offset + index, 4, v, little); return this; }

        public long getLong() { long v = Store().ReadBytes(offset + pos, 8, little); pos += 8; return v; }
        public long getLong(int index) { return Store().ReadBytes(offset + index, 8, little); }
        public ByteBuffer putLong(long v) { Store().WriteBytes(offset + pos, 8, v, little); pos += 8; return this; }
        public ByteBuffer putLong(int index, long v) { Store().WriteBytes(offset + index, 8, v, little); return this; }

        public float getFloat() { float v = global::System.BitConverter.Int32BitsToSingle((int)Store().ReadBytes(offset + pos, 4, little)); pos += 4; return v; }
        public float getFloat(int index) { return global::System.BitConverter.Int32BitsToSingle((int)Store().ReadBytes(offset + index, 4, little)); }
        public ByteBuffer putFloat(float v) { Store().WriteBytes(offset + pos, 4, global::System.BitConverter.SingleToInt32Bits(v), little); pos += 4; return this; }
        public ByteBuffer putFloat(int index, float v) { Store().WriteBytes(offset + index, 4, global::System.BitConverter.SingleToInt32Bits(v), little); return this; }

        public double getDouble() { double v = global::System.BitConverter.Int64BitsToDouble(Store().ReadBytes(offset + pos, 8, little)); pos += 8; return v; }
        public double getDouble(int index) { return global::System.BitConverter.Int64BitsToDouble(Store().ReadBytes(offset + index, 8, little)); }
        public ByteBuffer putDouble(double v) { Store().WriteBytes(offset + pos, 8, global::System.BitConverter.DoubleToInt64Bits(v), little); pos += 8; return this; }
        public ByteBuffer putDouble(int index, double v) { Store().WriteBytes(offset + index, 8, global::System.BitConverter.DoubleToInt64Bits(v), little); return this; }

        public ByteBuffer slice()
        {
            var s = new ByteBuffer(Store(), offset + pos, lim - pos, little);
            s.att = this;
            return s;
        }

        public ByteBuffer duplicate()
        {
            var d = new ByteBuffer(Store(), offset, cap, little);
            d.pos = pos;
            d.lim = lim;
            d.markPos = markPos;
            d.att = this;
            return d;
        }

        public ByteBuffer compact()
        {
            int rem = lim - pos;
            for (int i = 0; i < rem; i++) { Store()[offset + i] = Store()[offset + pos + i]; }
            pos = rem;
            lim = cap;
            markPos = -1;
            return this;
        }

        public IntBuffer asIntBuffer() { var v = new IntBuffer(Store(), offset + pos, (lim - pos) / 4, little); v.att = this; return v; }
        public LongBuffer asLongBuffer() { var v = new LongBuffer(Store(), offset + pos, (lim - pos) / 8, little); v.att = this; return v; }
        public ShortBuffer asShortBuffer() { var v = new ShortBuffer(Store(), offset + pos, (lim - pos) / 2, little); v.att = this; return v; }
        public CharBuffer asCharBuffer() { var v = new CharBuffer(Store(), offset + pos, (lim - pos) / 2, little); v.att = this; return v; }
        public FloatBuffer asFloatBuffer() { var v = new FloatBuffer(Store(), offset + pos, (lim - pos) / 4, little); v.att = this; return v; }
        public DoubleBuffer asDoubleBuffer() { var v = new DoubleBuffer(Store(), offset + pos, (lim - pos) / 8, little); v.att = this; return v; }

        public sbyte[] array()
        {
            if (Store().IsDirect)
            {
                throw global::java.lang.JThrow.of(new global::java.lang.UnsupportedOperationException(global::java.lang.RawNew.I));
            }
            return Store().Array;
        }

        /// <summary>Native base address of a direct buffer (0 for a heap buffer). Used by MemoryUtil.</summary>
        internal long DirectAddress => address;

        internal int DirectCapacity => cap;

        /// <summary>
        /// Wraps an existing native block as a direct buffer without owning it (the JNIEnv's
        /// NewDirectByteBuffer, used when LWJGL hands a native address to Java). The memory belongs
        /// to the caller, so it is not freed when this buffer is collected.
        /// </summary>
        internal static ByteBuffer __wrapDirect(long address, int capacity)
        {
            return new ByteBuffer(new ByteStore(address, capacity, false));
        }
    }
}
