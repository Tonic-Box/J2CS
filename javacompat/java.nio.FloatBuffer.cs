namespace java.nio
{
    public sealed class FloatBuffer : global::java.nio.Buffer
    {
        private readonly float[] arr;
        private readonly ByteStore vstore;
        private readonly int voff;
        private readonly bool vlittle;

        public FloatBuffer(global::java.lang.RawNew r) : base(r) { arr = new float[0]; }
        internal FloatBuffer(float[] backing) : base(global::java.lang.RawNew.I) { arr = backing; cap = backing.Length; lim = cap; }
        internal FloatBuffer(ByteStore bytes, int off, int capElems, bool le) : base(global::java.lang.RawNew.I) { vstore = bytes; voff = off; vlittle = le; cap = capElems; lim = capElems; address = bytes.IsDirect ? bytes.Address + off : 0; }

        public static FloatBuffer allocate(int capacity) { return new FloatBuffer(new float[capacity]); }
        public static FloatBuffer wrap(float[] array) { return new FloatBuffer(array); }

        private float ElemGet(int i) { return vstore != null ? (global::System.BitConverter.Int32BitsToSingle((int)vstore.ReadBytes(voff + i * 4, 4, vlittle))) : arr[i]; }
        private void ElemSet(int i, float v) { if (vstore != null) { vstore.WriteBytes(voff + i * 4, 4, global::System.BitConverter.SingleToInt32Bits(v), vlittle); } else { arr[i] = v; } }

        public int capacity() { return cap; }
        public int position() { return pos; }
        public int limit() { return lim; }
        public int remaining() { return lim - pos; }
        public int hasRemaining() { return pos < lim ? 1 : 0; }
        public FloatBuffer position(int newPosition) { pos = newPosition; if (markPos > pos) { markPos = -1; } return this; }
        public FloatBuffer limit(int newLimit) { lim = newLimit; if (pos > lim) { pos = lim; } if (markPos > lim) { markPos = -1; } return this; }
        public FloatBuffer mark() { markPos = pos; return this; }
        public FloatBuffer reset() { if (markPos < 0) { throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I)); } pos = markPos; return this; }
        public FloatBuffer flip() { lim = pos; pos = 0; markPos = -1; return this; }
        public FloatBuffer clear() { pos = 0; lim = cap; markPos = -1; return this; }
        public FloatBuffer rewind() { pos = 0; markPos = -1; return this; }

        public float get() { return ElemGet(pos++); }
        public float get(int index) { return ElemGet(index); }
        public FloatBuffer put(float v) { ElemSet(pos++, v); return this; }
        public FloatBuffer put(int index, float v) { ElemSet(index, v); return this; }
        public FloatBuffer get(float[] dst) { for (int i = 0; i < dst.Length; i++) { dst[i] = ElemGet(pos++); } return this; }
        public FloatBuffer put(float[] src) { for (int i = 0; i < src.Length; i++) { ElemSet(pos++, src[i]); } return this; }

        public float[] array() { return arr; }
    }
}
