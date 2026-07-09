namespace javax.crypto.spec
{
    public class SecretKeySpec : global::java.lang.Object, global::javax.crypto.SecretKey
    {
        private sbyte[] key;
        private string algorithm;

        public SecretKeySpec(global::java.lang.RawNew r) : base(r) { }

        public void __init__BLjava_lang_String__V(sbyte[] key, global::java.lang.String algorithm)
        {
            this.key = (sbyte[])key.Clone();
            this.algorithm = algorithm == null ? "" : algorithm.Value;
        }

        public void __init__BIILjava_lang_String__V(sbyte[] key, int offset, int len, global::java.lang.String algorithm)
        {
            this.key = new sbyte[len];
            global::System.Array.Copy(key, offset, this.key, 0, len);
            this.algorithm = algorithm == null ? "" : algorithm.Value;
        }

        public sbyte[] getEncoded() { return (sbyte[])key.Clone(); }
        public global::java.lang.String getAlgorithm() { return global::java.lang.String.Wrap(algorithm); }
        public global::java.lang.String getFormat() { return global::java.lang.String.Wrap("RAW"); }
    }
}
