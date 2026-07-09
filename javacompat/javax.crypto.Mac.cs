namespace javax.crypto
{
    public class Mac : global::java.lang.Object
    {
        private string algorithm;
        private byte[] keyBytes;
        private readonly global::System.Collections.Generic.List<byte> buffer =
                new global::System.Collections.Generic.List<byte>();

        public Mac(global::java.lang.RawNew r) : base(r) { }

        public static Mac getInstance(global::java.lang.String algorithm)
        {
            var m = new Mac(global::java.lang.RawNew.I);
            m.algorithm = algorithm == null ? "HmacSHA256" : algorithm.Value;
            return m;
        }

        public void init(global::java.security.Key key)
        {
            keyBytes = global::java.lang.JRuntime.UnsignedBytes(key.getEncoded());
            buffer.Clear();
        }

        private global::System.Security.Cryptography.HMAC CreateHmac()
        {
            switch (algorithm)
            {
                case "HmacSHA1": return new global::System.Security.Cryptography.HMACSHA1(keyBytes);
                case "HmacSHA512": return new global::System.Security.Cryptography.HMACSHA512(keyBytes);
                case "HmacMD5": return new global::System.Security.Cryptography.HMACMD5(keyBytes);
                default: return new global::System.Security.Cryptography.HMACSHA256(keyBytes);
            }
        }

        public void update(sbyte[] input)
        {
            var u = global::java.lang.JRuntime.UnsignedBytes(input);
            if (u != null) { buffer.AddRange(u); }
        }

        public sbyte[] doFinal()
        {
            using (var h = CreateHmac())
            {
                var result = h.ComputeHash(buffer.ToArray());
                buffer.Clear();
                return global::java.lang.JRuntime.SignedBytes(result);
            }
        }

        public sbyte[] doFinal(sbyte[] input)
        {
            update(input);
            return doFinal();
        }

        public int getMacLength()
        {
            using (var h = CreateHmac()) { return h.HashSize / 8; }
        }

        public global::java.lang.String getAlgorithm() { return global::java.lang.String.Wrap(algorithm); }

        public void reset() { buffer.Clear(); }
    }
}
