namespace java.nio
{
    public sealed unsafe class ByteBuffer : global::java.nio.Buffer
    {
        private readonly ByteStore store;
        private readonly int offset;
        private readonly int cap;
        private int pos;
        private int lim;
        private int markPos = -1;
        private bool little;

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

        public sbyte get() { return store[offset + pos++]; }
        public sbyte get(int index) { return store[offset + index]; }
        public ByteBuffer put(sbyte b) { store[offset + pos++] = b; return this; }
        public ByteBuffer put(int index, sbyte b) { store[offset + index] = b; return this; }

        public ByteBuffer get(sbyte[] dst)
        {
            for (int i = 0; i < dst.Length; i++) { dst[i] = store[offset + pos++]; }
            return this;
        }

        public ByteBuffer put(sbyte[] src)
        {
            for (int i = 0; i < src.Length; i++) { store[offset + pos++] = src[i]; }
            return this;
        }

        public short getShort() { short v = (short)store.ReadBytes(offset + pos, 2, little); pos += 2; return v; }
        public short getShort(int index) { return (short)store.ReadBytes(offset + index, 2, little); }
        public ByteBuffer putShort(short v) { store.WriteBytes(offset + pos, 2, v, little); pos += 2; return this; }
        public ByteBuffer putShort(int index, short v) { store.WriteBytes(offset + index, 2, v, little); return this; }

        public char getChar() { char v = (char)store.ReadBytes(offset + pos, 2, little); pos += 2; return v; }
        public char getChar(int index) { return (char)store.ReadBytes(offset + index, 2, little); }
        public ByteBuffer putChar(char v) { store.WriteBytes(offset + pos, 2, v, little); pos += 2; return this; }
        public ByteBuffer putChar(int index, char v) { store.WriteBytes(offset + index, 2, v, little); return this; }

        public int getInt() { int v = (int)store.ReadBytes(offset + pos, 4, little); pos += 4; return v; }
        public int getInt(int index) { return (int)store.ReadBytes(offset + index, 4, little); }
        public ByteBuffer putInt(int v) { store.WriteBytes(offset + pos, 4, v, little); pos += 4; return this; }
        public ByteBuffer putInt(int index, int v) { store.WriteBytes(offset + index, 4, v, little); return this; }

        public long getLong() { long v = store.ReadBytes(offset + pos, 8, little); pos += 8; return v; }
        public long getLong(int index) { return store.ReadBytes(offset + index, 8, little); }
        public ByteBuffer putLong(long v) { store.WriteBytes(offset + pos, 8, v, little); pos += 8; return this; }
        public ByteBuffer putLong(int index, long v) { store.WriteBytes(offset + index, 8, v, little); return this; }

        public float getFloat() { float v = global::System.BitConverter.Int32BitsToSingle((int)store.ReadBytes(offset + pos, 4, little)); pos += 4; return v; }
        public float getFloat(int index) { return global::System.BitConverter.Int32BitsToSingle((int)store.ReadBytes(offset + index, 4, little)); }
        public ByteBuffer putFloat(float v) { store.WriteBytes(offset + pos, 4, global::System.BitConverter.SingleToInt32Bits(v), little); pos += 4; return this; }
        public ByteBuffer putFloat(int index, float v) { store.WriteBytes(offset + index, 4, global::System.BitConverter.SingleToInt32Bits(v), little); return this; }

        public double getDouble() { double v = global::System.BitConverter.Int64BitsToDouble(store.ReadBytes(offset + pos, 8, little)); pos += 8; return v; }
        public double getDouble(int index) { return global::System.BitConverter.Int64BitsToDouble(store.ReadBytes(offset + index, 8, little)); }
        public ByteBuffer putDouble(double v) { store.WriteBytes(offset + pos, 8, global::System.BitConverter.DoubleToInt64Bits(v), little); pos += 8; return this; }
        public ByteBuffer putDouble(int index, double v) { store.WriteBytes(offset + index, 8, global::System.BitConverter.DoubleToInt64Bits(v), little); return this; }

        public ByteBuffer slice()
        {
            return new ByteBuffer(store, offset + pos, lim - pos, little);
        }

        public ByteBuffer duplicate()
        {
            var d = new ByteBuffer(store, offset, cap, little);
            d.pos = pos;
            d.lim = lim;
            d.markPos = markPos;
            return d;
        }

        public ByteBuffer compact()
        {
            int rem = lim - pos;
            for (int i = 0; i < rem; i++) { store[offset + i] = store[offset + pos + i]; }
            pos = rem;
            lim = cap;
            markPos = -1;
            return this;
        }

        public IntBuffer asIntBuffer() { return new IntBuffer(store, offset + pos, (lim - pos) / 4, little); }
        public LongBuffer asLongBuffer() { return new LongBuffer(store, offset + pos, (lim - pos) / 8, little); }
        public ShortBuffer asShortBuffer() { return new ShortBuffer(store, offset + pos, (lim - pos) / 2, little); }
        public CharBuffer asCharBuffer() { return new CharBuffer(store, offset + pos, (lim - pos) / 2, little); }
        public FloatBuffer asFloatBuffer() { return new FloatBuffer(store, offset + pos, (lim - pos) / 4, little); }
        public DoubleBuffer asDoubleBuffer() { return new DoubleBuffer(store, offset + pos, (lim - pos) / 8, little); }

        public sbyte[] array()
        {
            if (store.IsDirect)
            {
                throw global::java.lang.JThrow.of(new global::java.lang.UnsupportedOperationException(global::java.lang.RawNew.I));
            }
            return store.Array;
        }

        /// <summary>Native base address of a direct buffer (0 for a heap buffer). Used by MemoryUtil.</summary>
        internal long DirectAddress => address;
    }
}
