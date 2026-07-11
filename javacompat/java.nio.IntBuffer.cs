namespace java.nio
{
    public sealed class IntBuffer : global::java.nio.Buffer
    {
        private readonly int[] arr;
        private readonly ByteStore vstore;
        private readonly int voff;
        private readonly bool vlittle;
        private readonly int cap;
        private int pos;
        private int lim;
        private int markPos = -1;

        public IntBuffer(global::java.lang.RawNew r) : base(r) { arr = new int[0]; }
        internal IntBuffer(int[] backing) : base(global::java.lang.RawNew.I) { arr = backing; cap = backing.Length; lim = cap; }
        internal IntBuffer(ByteStore bytes, int off, int capElems, bool le) : base(global::java.lang.RawNew.I) { vstore = bytes; voff = off; vlittle = le; cap = capElems; lim = capElems; address = bytes.IsDirect ? bytes.Address + off : 0; }

        public static IntBuffer allocate(int capacity) { return new IntBuffer(new int[capacity]); }
        public static IntBuffer wrap(int[] array) { return new IntBuffer(array); }

        private int ElemGet(int i) { return vstore != null ? ((int)vstore.ReadBytes(voff + i * 4, 4, vlittle)) : arr[i]; }
        private void ElemSet(int i, int v) { if (vstore != null) { vstore.WriteBytes(voff + i * 4, 4, v, vlittle); } else { arr[i] = v; } }

        public int capacity() { return cap; }
        public int position() { return pos; }
        public int limit() { return lim; }
        public int remaining() { return lim - pos; }
        public int hasRemaining() { return pos < lim ? 1 : 0; }
        public IntBuffer position(int newPosition) { pos = newPosition; if (markPos > pos) { markPos = -1; } return this; }
        public IntBuffer limit(int newLimit) { lim = newLimit; if (pos > lim) { pos = lim; } if (markPos > lim) { markPos = -1; } return this; }
        public IntBuffer mark() { markPos = pos; return this; }
        public IntBuffer reset() { if (markPos < 0) { throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I)); } pos = markPos; return this; }
        public IntBuffer flip() { lim = pos; pos = 0; markPos = -1; return this; }
        public IntBuffer clear() { pos = 0; lim = cap; markPos = -1; return this; }
        public IntBuffer rewind() { pos = 0; markPos = -1; return this; }

        public int get() { return ElemGet(pos++); }
        public int get(int index) { return ElemGet(index); }
        public IntBuffer put(int v) { ElemSet(pos++, v); return this; }
        public IntBuffer put(int index, int v) { ElemSet(index, v); return this; }
        public IntBuffer get(int[] dst) { for (int i = 0; i < dst.Length; i++) { dst[i] = ElemGet(pos++); } return this; }
        public IntBuffer put(int[] src) { for (int i = 0; i < src.Length; i++) { ElemSet(pos++, src[i]); } return this; }

        public int[] array() { return arr; }
    }
}
