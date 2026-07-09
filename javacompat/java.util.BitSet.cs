namespace java.util
{
    public class BitSet : global::java.lang.Object
    {
        private readonly global::System.Collections.Generic.SortedSet<int> bits = new global::System.Collections.Generic.SortedSet<int>();

        public BitSet(global::java.lang.RawNew r) : base(r) { }
        public void __init__V() { }
        public void __init_I_V(int nbits) { }

        public void set(int bitIndex) { bits.Add(bitIndex); }
        public void set(int bitIndex, int value) { if (value != 0) { bits.Add(bitIndex); } else { bits.Remove(bitIndex); } }
        public void clear(int bitIndex) { bits.Remove(bitIndex); }
        public void clear() { bits.Clear(); }
        public void flip(int bitIndex) { if (!bits.Remove(bitIndex)) { bits.Add(bitIndex); } }
        public int get(int bitIndex) { return bits.Contains(bitIndex) ? 1 : 0; }
        public int cardinality() { return bits.Count; }
        public int isEmpty() { return bits.Count == 0 ? 1 : 0; }
        public int length() { return bits.Count == 0 ? 0 : bits.Max + 1; }
        public int size() { return 64; }

        public int nextSetBit(int fromIndex)
        {
            foreach (int b in bits) { if (b >= fromIndex) { return b; } }
            return -1;
        }

        public void and(BitSet o) { bits.IntersectWith(o.bits); }
        public void or(BitSet o) { bits.UnionWith(o.bits); }
        public void xor(BitSet o) { bits.SymmetricExceptWith(o.bits); }
        public void andNot(BitSet o) { bits.ExceptWith(o.bits); }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap("{" + string.Join(", ", bits) + "}");
        }
    }
}
