namespace javax.crypto
{
    public class Cipher : global::java.lang.Object
    {
        public const int ENCRYPT_MODE = 1;
        public const int DECRYPT_MODE = 2;
        public const int WRAP_MODE = 3;
        public const int UNWRAP_MODE = 4;

        private string mode = "ECB";
        private string padding = "PKCS5Padding";
        private int opmode;
        private byte[] keyBytes;
        private byte[] ivBytes;

        public Cipher(global::java.lang.RawNew r) : base(r) { }

        public static Cipher getInstance(global::java.lang.String transformation)
        {
            var c = new Cipher(global::java.lang.RawNew.I);
            var parts = (transformation == null ? "AES" : transformation.Value).Split('/');
            if (parts.Length > 1) { c.mode = parts[1]; }
            if (parts.Length > 2) { c.padding = parts[2]; }
            return c;
        }

        public void init(int opmode, global::java.security.Key key)
        {
            this.opmode = opmode;
            keyBytes = global::java.lang.JRuntime.UnsignedBytes(key.getEncoded());
            ivBytes = null;
        }

        public void init(int opmode, global::java.security.Key key,
                global::java.security.spec.AlgorithmParameterSpec paramSpec)
        {
            this.opmode = opmode;
            keyBytes = global::java.lang.JRuntime.UnsignedBytes(key.getEncoded());
            ivBytes = paramSpec is global::javax.crypto.spec.IvParameterSpec iv
                    ? global::java.lang.JRuntime.UnsignedBytes(iv.getIV())
                    : null;
        }

        public sbyte[] doFinal(sbyte[] input)
        {
            var data = global::java.lang.JRuntime.UnsignedBytes(input) ?? global::System.Array.Empty<byte>();
            using (var aes = global::System.Security.Cryptography.Aes.Create())
            {
                aes.Key = keyBytes;
                aes.Mode = mode == "ECB"
                        ? global::System.Security.Cryptography.CipherMode.ECB
                        : global::System.Security.Cryptography.CipherMode.CBC;
                aes.Padding = padding == "NoPadding"
                        ? global::System.Security.Cryptography.PaddingMode.None
                        : global::System.Security.Cryptography.PaddingMode.PKCS7;
                if (ivBytes != null) { aes.IV = ivBytes; }
                using (var t = opmode == ENCRYPT_MODE ? aes.CreateEncryptor() : aes.CreateDecryptor())
                {
                    return global::java.lang.JRuntime.SignedBytes(t.TransformFinalBlock(data, 0, data.Length));
                }
            }
        }

        public int getBlockSize() { return 16; }
    }
}
