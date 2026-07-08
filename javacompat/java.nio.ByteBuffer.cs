namespace java.nio
{
    public sealed class ByteBuffer : global::java.lang.Object
    {
        private readonly sbyte[] buf;
        private readonly int offset;
        private readonly int cap;
        private int pos;
        private int lim;
        private int markPos = -1;
        private bool little;

        public ByteBuffer(global::java.lang.RawNew r) : base(r)
        {
            buf = new sbyte[0];
        }

        internal ByteBuffer(sbyte[] backing) : base(global::java.lang.RawNew.I)
        {
            buf = backing;
            cap = backing.Length;
            lim = cap;
        }

        internal ByteBuffer(sbyte[] backing, int off, int capacity, bool le) : base(global::java.lang.RawNew.I)
        {
            buf = backing;
            offset = off;
            cap = capacity;
            lim = capacity;
            little = le;
        }

        internal static long ReadBytes(sbyte[] b, int idx, int n, bool le)
        {
            long v = 0;
            if (le)
            {
                for (int i = n - 1; i >= 0; i--) { v = v << 8 | (uint)(b[idx + i] & 0xFF); }
            }
            else
            {
                for (int i = 0; i < n; i++) { v = v << 8 | (uint)(b[idx + i] & 0xFF); }
            }
            return v;
        }

        internal static void WriteBytes(sbyte[] b, int idx, int n, long v, bool le)
        {
            if (le)
            {
                for (int i = 0; i < n; i++) { b[idx + i] = (sbyte)v; v >>= 8; }
            }
            else
            {
                for (int i = n - 1; i >= 0; i--) { b[idx + i] = (sbyte)v; v >>= 8; }
            }
        }

        public static ByteBuffer allocate(int capacity)
        {
            return new ByteBuffer(new sbyte[capacity]);
        }

        public static ByteBuffer wrap(sbyte[] array)
        {
            return new ByteBuffer(array);
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

        public sbyte get() { return buf[offset + pos++]; }
        public sbyte get(int index) { return buf[offset + index]; }
        public ByteBuffer put(sbyte b) { buf[offset + pos++] = b; return this; }
        public ByteBuffer put(int index, sbyte b) { buf[offset + index] = b; return this; }

        public ByteBuffer get(sbyte[] dst)
        {
            for (int i = 0; i < dst.Length; i++) { dst[i] = buf[offset + pos++]; }
            return this;
        }

        public ByteBuffer put(sbyte[] src)
        {
            for (int i = 0; i < src.Length; i++) { buf[offset + pos++] = src[i]; }
            return this;
        }

        public short getShort() { short v = (short)ReadBytes(buf, offset + pos, 2, little); pos += 2; return v; }
        public short getShort(int index) { return (short)ReadBytes(buf, offset + index, 2, little); }
        public ByteBuffer putShort(short v) { WriteBytes(buf, offset + pos, 2, v, little); pos += 2; return this; }
        public ByteBuffer putShort(int index, short v) { WriteBytes(buf, offset + index, 2, v, little); return this; }

        public char getChar() { char v = (char)ReadBytes(buf, offset + pos, 2, little); pos += 2; return v; }
        public char getChar(int index) { return (char)ReadBytes(buf, offset + index, 2, little); }
        public ByteBuffer putChar(char v) { WriteBytes(buf, offset + pos, 2, v, little); pos += 2; return this; }
        public ByteBuffer putChar(int index, char v) { WriteBytes(buf, offset + index, 2, v, little); return this; }

        public int getInt() { int v = (int)ReadBytes(buf, offset + pos, 4, little); pos += 4; return v; }
        public int getInt(int index) { return (int)ReadBytes(buf, offset + index, 4, little); }
        public ByteBuffer putInt(int v) { WriteBytes(buf, offset + pos, 4, v, little); pos += 4; return this; }
        public ByteBuffer putInt(int index, int v) { WriteBytes(buf, offset + index, 4, v, little); return this; }

        public long getLong() { long v = ReadBytes(buf, offset + pos, 8, little); pos += 8; return v; }
        public long getLong(int index) { return ReadBytes(buf, offset + index, 8, little); }
        public ByteBuffer putLong(long v) { WriteBytes(buf, offset + pos, 8, v, little); pos += 8; return this; }
        public ByteBuffer putLong(int index, long v) { WriteBytes(buf, offset + index, 8, v, little); return this; }

        public float getFloat() { float v = global::System.BitConverter.Int32BitsToSingle((int)ReadBytes(buf, offset + pos, 4, little)); pos += 4; return v; }
        public float getFloat(int index) { return global::System.BitConverter.Int32BitsToSingle((int)ReadBytes(buf, offset + index, 4, little)); }
        public ByteBuffer putFloat(float v) { WriteBytes(buf, offset + pos, 4, global::System.BitConverter.SingleToInt32Bits(v), little); pos += 4; return this; }
        public ByteBuffer putFloat(int index, float v) { WriteBytes(buf, offset + index, 4, global::System.BitConverter.SingleToInt32Bits(v), little); return this; }

        public double getDouble() { double v = global::System.BitConverter.Int64BitsToDouble(ReadBytes(buf, offset + pos, 8, little)); pos += 8; return v; }
        public double getDouble(int index) { return global::System.BitConverter.Int64BitsToDouble(ReadBytes(buf, offset + index, 8, little)); }
        public ByteBuffer putDouble(double v) { WriteBytes(buf, offset + pos, 8, global::System.BitConverter.DoubleToInt64Bits(v), little); pos += 8; return this; }
        public ByteBuffer putDouble(int index, double v) { WriteBytes(buf, offset + index, 8, global::System.BitConverter.DoubleToInt64Bits(v), little); return this; }

        public ByteBuffer slice()
        {
            return new ByteBuffer(buf, offset + pos, lim - pos, little);
        }

        public ByteBuffer duplicate()
        {
            var d = new ByteBuffer(buf, offset, cap, little);
            d.pos = pos;
            d.lim = lim;
            d.markPos = markPos;
            return d;
        }

        public ByteBuffer compact()
        {
            int rem = lim - pos;
            for (int i = 0; i < rem; i++) { buf[offset + i] = buf[offset + pos + i]; }
            pos = rem;
            lim = cap;
            markPos = -1;
            return this;
        }

        public IntBuffer asIntBuffer() { return new IntBuffer(buf, offset + pos, (lim - pos) / 4, little); }
        public LongBuffer asLongBuffer() { return new LongBuffer(buf, offset + pos, (lim - pos) / 8, little); }
        public ShortBuffer asShortBuffer() { return new ShortBuffer(buf, offset + pos, (lim - pos) / 2, little); }
        public CharBuffer asCharBuffer() { return new CharBuffer(buf, offset + pos, (lim - pos) / 2, little); }
        public FloatBuffer asFloatBuffer() { return new FloatBuffer(buf, offset + pos, (lim - pos) / 4, little); }
        public DoubleBuffer asDoubleBuffer() { return new DoubleBuffer(buf, offset + pos, (lim - pos) / 8, little); }

        public sbyte[] array() { return buf; }
    }
}
