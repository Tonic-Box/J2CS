namespace java.security
{
    internal sealed class RsaKeyHolder : global::java.lang.Object, PublicKey, PrivateKey
    {
        internal readonly global::System.Security.Cryptography.RSA rsa;

        internal RsaKeyHolder(global::System.Security.Cryptography.RSA rsa) : base(global::java.lang.RawNew.I) { this.rsa = rsa; }

        public global::java.lang.String getAlgorithm() { return global::java.lang.String.Wrap("RSA"); }
        public global::java.lang.String getFormat() { return global::java.lang.String.Wrap("X.509"); }
        public sbyte[] getEncoded() { return global::java.lang.JRuntime.SignedBytes(rsa.ExportSubjectPublicKeyInfo()); }
    }

    public class KeyPair : global::java.lang.Object
    {
        internal RsaKeyHolder holder;

        public KeyPair(global::java.lang.RawNew r) : base(r) { }

        public PublicKey getPublic() { return holder; }
        public PrivateKey getPrivate() { return holder; }
    }
}
