namespace javax.crypto.spec
{
    public class IvParameterSpec : global::java.lang.Object, global::java.security.spec.AlgorithmParameterSpec
    {
        private sbyte[] iv;

        public IvParameterSpec(global::java.lang.RawNew r) : base(r) { }

        public void __init__B_V(sbyte[] iv) { this.iv = (sbyte[])iv.Clone(); }

        public void __init__BII_V(sbyte[] iv, int offset, int len)
        {
            this.iv = new sbyte[len];
            global::System.Array.Copy(iv, offset, this.iv, 0, len);
        }

        public sbyte[] getIV() { return (sbyte[])iv.Clone(); }
    }
}
