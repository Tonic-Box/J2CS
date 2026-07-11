namespace java.nio
{
    public sealed class ShortBuffer : global::java.nio.Buffer
    {
        private readonly short[] arr;
        private readonly ByteStore vstore;
        private readonly int voff;
        private readonly bool vlittle;

        public ShortBuffer(global::java.lang.RawNew r) : base(r) { arr = new short[0]; }
        internal ShortBuffer(short[] backing) : base(global::java.lang.RawNew.I) { arr = backing; cap = backing.Length; lim = cap; }
        internal ShortBuffer(ByteStore bytes, int off, int capElems, bool le) : base(global::java.lang.RawNew.I) { vstore = bytes; voff = off; vlittle = le; cap = capElems; lim = capElems; address = bytes.IsDirect ? bytes.Address + off : 0; }

        public static ShortBuffer allocate(int capacity) { return new ShortBuffer(new short[capacity]); }
        public static ShortBuffer wrap(short[] array) { return new ShortBuffer(array); }

        private short ElemGet(int i) { return vstore != null ? ((short)vstore.ReadBytes(voff + i * 2, 2, vlittle)) : arr[i]; }
        private void ElemSet(int i, short v) { if (vstore != null) { vstore.WriteBytes(voff + i * 2, 2, v, vlittle); } else { arr[i] = v; } }

        public int capacity() { return cap; }
        public int position() { return pos; }
        public int limit() { return lim; }
        public int remaining() { return lim - pos; }
        public int hasRemaining() { return pos < lim ? 1 : 0; }
        public ShortBuffer position(int newPosition) { pos = newPosition; if (markPos > pos) { markPos = -1; } return this; }
        public ShortBuffer limit(int newLimit) { lim = newLimit; if (pos > lim) { pos = lim; } if (markPos > lim) { markPos = -1; } return this; }
        public ShortBuffer mark() { markPos = pos; return this; }
        public ShortBuffer reset() { if (markPos < 0) { throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I)); } pos = markPos; return this; }
        public ShortBuffer flip() { lim = pos; pos = 0; markPos = -1; return this; }
        public ShortBuffer clear() { pos = 0; lim = cap; markPos = -1; return this; }
        public ShortBuffer rewind() { pos = 0; markPos = -1; return this; }

        public short get() { return ElemGet(pos++); }
        public short get(int index) { return ElemGet(index); }
        public ShortBuffer put(short v) { ElemSet(pos++, v); return this; }
        public ShortBuffer put(int index, short v) { ElemSet(index, v); return this; }
        public ShortBuffer get(short[] dst) { for (int i = 0; i < dst.Length; i++) { dst[i] = ElemGet(pos++); } return this; }
        public ShortBuffer put(short[] src) { for (int i = 0; i < src.Length; i++) { ElemSet(pos++, src[i]); } return this; }

        public short[] array() { return arr; }
    }
}
