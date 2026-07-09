namespace java.security
{
    public class KeyPairGenerator : global::java.lang.Object
    {
        private int keySize = 2048;

        public KeyPairGenerator(global::java.lang.RawNew r) : base(r) { }

        public static KeyPairGenerator getInstance(global::java.lang.String algorithm)
        {
            return new KeyPairGenerator(global::java.lang.RawNew.I);
        }

        public void initialize(int keysize) { keySize = keysize; }

        public KeyPair generateKeyPair()
        {
            var kp = new KeyPair(global::java.lang.RawNew.I);
            kp.holder = new RsaKeyHolder(global::System.Security.Cryptography.RSA.Create(keySize));
            return kp;
        }

        public KeyPair genKeyPair() { return generateKeyPair(); }
    }
}
