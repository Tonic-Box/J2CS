namespace java.nio
{
    public sealed class DoubleBuffer : global::java.nio.Buffer
    {
        private readonly double[] arr;
        private readonly ByteStore vstore;
        private readonly int voff;
        private readonly bool vlittle;
        private readonly int cap;
        private int pos;
        private int lim;
        private int markPos = -1;

        public DoubleBuffer(global::java.lang.RawNew r) : base(r) { arr = new double[0]; }
        internal DoubleBuffer(double[] backing) : base(global::java.lang.RawNew.I) { arr = backing; cap = backing.Length; lim = cap; }
        internal DoubleBuffer(ByteStore bytes, int off, int capElems, bool le) : base(global::java.lang.RawNew.I) { vstore = bytes; voff = off; vlittle = le; cap = capElems; lim = capElems; address = bytes.IsDirect ? bytes.Address + off : 0; }

        public static DoubleBuffer allocate(int capacity) { return new DoubleBuffer(new double[capacity]); }
        public static DoubleBuffer wrap(double[] array) { return new DoubleBuffer(array); }

        private double ElemGet(int i) { return vstore != null ? (global::System.BitConverter.Int64BitsToDouble(vstore.ReadBytes(voff + i * 8, 8, vlittle))) : arr[i]; }
        private void ElemSet(int i, double v) { if (vstore != null) { vstore.WriteBytes(voff + i * 8, 8, global::System.BitConverter.DoubleToInt64Bits(v), vlittle); } else { arr[i] = v; } }

        public int capacity() { return cap; }
        public int position() { return pos; }
        public int limit() { return lim; }
        public int remaining() { return lim - pos; }
        public int hasRemaining() { return pos < lim ? 1 : 0; }
        public DoubleBuffer position(int newPosition) { pos = newPosition; if (markPos > pos) { markPos = -1; } return this; }
        public DoubleBuffer limit(int newLimit) { lim = newLimit; if (pos > lim) { pos = lim; } if (markPos > lim) { markPos = -1; } return this; }
        public DoubleBuffer mark() { markPos = pos; return this; }
        public DoubleBuffer reset() { if (markPos < 0) { throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I)); } pos = markPos; return this; }
        public DoubleBuffer flip() { lim = pos; pos = 0; markPos = -1; return this; }
        public DoubleBuffer clear() { pos = 0; lim = cap; markPos = -1; return this; }
        public DoubleBuffer rewind() { pos = 0; markPos = -1; return this; }

        public double get() { return ElemGet(pos++); }
        public double get(int index) { return ElemGet(index); }
        public DoubleBuffer put(double v) { ElemSet(pos++, v); return this; }
        public DoubleBuffer put(int index, double v) { ElemSet(index, v); return this; }
        public DoubleBuffer get(double[] dst) { for (int i = 0; i < dst.Length; i++) { dst[i] = ElemGet(pos++); } return this; }
        public DoubleBuffer put(double[] src) { for (int i = 0; i < src.Length; i++) { ElemSet(pos++, src[i]); } return this; }

        public double[] array() { return arr; }
    }
}
