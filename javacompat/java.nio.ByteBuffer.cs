namespace java.nio
{
    public sealed class ByteBuffer : global::java.lang.Object
    {
        private readonly sbyte[] buf;
        private readonly int cap;
        private int pos;
        private int lim;
        private int markPos = -1;

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

        public sbyte get() { return buf[pos++]; }
        public sbyte get(int index) { return buf[index]; }
        public ByteBuffer put(sbyte b) { buf[pos++] = b; return this; }
        public ByteBuffer put(int index, sbyte b) { buf[index] = b; return this; }

        public ByteBuffer get(sbyte[] dst)
        {
            for (int i = 0; i < dst.Length; i++) { dst[i] = buf[pos++]; }
            return this;
        }

        public ByteBuffer put(sbyte[] src)
        {
            for (int i = 0; i < src.Length; i++) { buf[pos++] = src[i]; }
            return this;
        }

        public int getInt()
        {
            int v = (buf[pos] & 0xFF) << 24 | (buf[pos + 1] & 0xFF) << 16
                    | (buf[pos + 2] & 0xFF) << 8 | (buf[pos + 3] & 0xFF);
            pos += 4;
            return v;
        }

        public ByteBuffer putInt(int v)
        {
            buf[pos] = (sbyte)(v >> 24);
            buf[pos + 1] = (sbyte)(v >> 16);
            buf[pos + 2] = (sbyte)(v >> 8);
            buf[pos + 3] = (sbyte)v;
            pos += 4;
            return this;
        }

        public long getLong()
        {
            long v = 0;
            for (int i = 0; i < 8; i++) { v = v << 8 | (uint)(buf[pos + i] & 0xFF); }
            pos += 8;
            return v;
        }

        public ByteBuffer putLong(long v)
        {
            for (int i = 0; i < 8; i++) { buf[pos + i] = (sbyte)(v >> (56 - 8 * i)); }
            pos += 8;
            return this;
        }

        public sbyte[] array() { return buf; }
    }
}
