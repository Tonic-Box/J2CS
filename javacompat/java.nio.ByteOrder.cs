namespace java.nio
{
    public sealed class ByteOrder : global::java.lang.Object
    {
        public static readonly ByteOrder BIG_ENDIAN = new ByteOrder("BIG_ENDIAN");
        public static readonly ByteOrder LITTLE_ENDIAN = new ByteOrder("LITTLE_ENDIAN");

        private readonly string name;

        private ByteOrder(string n) : base(global::java.lang.RawNew.I) { name = n; }
        public ByteOrder(global::java.lang.RawNew r) : base(r) { name = ""; }

        public static ByteOrder nativeOrder()
        {
            return global::System.BitConverter.IsLittleEndian ? LITTLE_ENDIAN : BIG_ENDIAN;
        }

        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(name); }
    }
}
