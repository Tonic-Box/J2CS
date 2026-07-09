namespace java.security
{
    public class Signature : global::java.lang.Object
    {
        private string algorithm = "SHA256withRSA";
        private global::System.Security.Cryptography.RSA rsa;
        private readonly global::System.Collections.Generic.List<byte> buffer =
                new global::System.Collections.Generic.List<byte>();

        public Signature(global::java.lang.RawNew r) : base(r) { }

        public static Signature getInstance(global::java.lang.String algorithm)
        {
            var s = new Signature(global::java.lang.RawNew.I);
            s.algorithm = algorithm == null ? "SHA256withRSA" : algorithm.Value;
            return s;
        }

        private global::System.Security.Cryptography.HashAlgorithmName HashAlgo()
        {
            string a = algorithm.ToUpperInvariant();
            if (a.StartsWith("SHA512")) { return global::System.Security.Cryptography.HashAlgorithmName.SHA512; }
            if (a.StartsWith("SHA1")) { return global::System.Security.Cryptography.HashAlgorithmName.SHA1; }
            return global::System.Security.Cryptography.HashAlgorithmName.SHA256;
        }

        public void initSign(PrivateKey key) { rsa = ((RsaKeyHolder)key).rsa; buffer.Clear(); }
        public void initVerify(PublicKey key) { rsa = ((RsaKeyHolder)key).rsa; buffer.Clear(); }

        public void update(sbyte[] data)
        {
            var u = global::java.lang.JRuntime.UnsignedBytes(data);
            if (u != null) { buffer.AddRange(u); }
        }

        public sbyte[] sign()
        {
            var sig = rsa.SignData(buffer.ToArray(), HashAlgo(), global::System.Security.Cryptography.RSASignaturePadding.Pkcs1);
            buffer.Clear();
            return global::java.lang.JRuntime.SignedBytes(sig);
        }

        public int verify(sbyte[] signature)
        {
            bool ok = rsa.VerifyData(buffer.ToArray(), global::java.lang.JRuntime.UnsignedBytes(signature),
                    HashAlgo(), global::System.Security.Cryptography.RSASignaturePadding.Pkcs1);
            buffer.Clear();
            return ok ? 1 : 0;
        }
    }
}
