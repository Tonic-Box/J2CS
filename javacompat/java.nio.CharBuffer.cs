namespace java.nio
{
    public sealed class CharBuffer : global::java.lang.Object
    {
        private readonly char[] arr;
        private readonly sbyte[] vbuf;
        private readonly int voff;
        private readonly bool vlittle;
        private readonly int cap;
        private int pos;
        private int lim;
        private int markPos = -1;

        public CharBuffer(global::java.lang.RawNew r) : base(r) { arr = new char[0]; }
        internal CharBuffer(char[] backing) : base(global::java.lang.RawNew.I) { arr = backing; cap = backing.Length; lim = cap; }
        internal CharBuffer(sbyte[] bytes, int off, int capElems, bool le) : base(global::java.lang.RawNew.I) { vbuf = bytes; voff = off; vlittle = le; cap = capElems; lim = capElems; }

        public static CharBuffer allocate(int capacity) { return new CharBuffer(new char[capacity]); }
        public static CharBuffer wrap(char[] array) { return new CharBuffer(array); }

        private char ElemGet(int i) { return vbuf != null ? ((char)global::java.nio.ByteBuffer.ReadBytes(vbuf, voff + i * 2, 2, vlittle)) : arr[i]; }
        private void ElemSet(int i, char v) { if (vbuf != null) { global::java.nio.ByteBuffer.WriteBytes(vbuf, voff + i * 2, 2, v, vlittle); } else { arr[i] = v; } }

        public int capacity() { return cap; }
        public int position() { return pos; }
        public int limit() { return lim; }
        public int remaining() { return lim - pos; }
        public int hasRemaining() { return pos < lim ? 1 : 0; }
        public CharBuffer position(int newPosition) { pos = newPosition; if (markPos > pos) { markPos = -1; } return this; }
        public CharBuffer limit(int newLimit) { lim = newLimit; if (pos > lim) { pos = lim; } if (markPos > lim) { markPos = -1; } return this; }
        public CharBuffer mark() { markPos = pos; return this; }
        public CharBuffer reset() { if (markPos < 0) { throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I)); } pos = markPos; return this; }
        public CharBuffer flip() { lim = pos; pos = 0; markPos = -1; return this; }
        public CharBuffer clear() { pos = 0; lim = cap; markPos = -1; return this; }
        public CharBuffer rewind() { pos = 0; markPos = -1; return this; }

        public char get() { return ElemGet(pos++); }
        public char get(int index) { return ElemGet(index); }
        public CharBuffer put(char v) { ElemSet(pos++, v); return this; }
        public CharBuffer put(int index, char v) { ElemSet(index, v); return this; }
        public CharBuffer get(char[] dst) { for (int i = 0; i < dst.Length; i++) { dst[i] = ElemGet(pos++); } return this; }
        public CharBuffer put(char[] src) { for (int i = 0; i < src.Length; i++) { ElemSet(pos++, src[i]); } return this; }

        public char[] array() { return arr; }

        public override global::java.lang.String toString()
        {
            var sb = new global::System.Text.StringBuilder();
            for (int i = pos; i < lim; i++) { sb.Append(ElemGet(i)); }
            return global::java.lang.String.Wrap(sb.ToString());
        }
    }
}
