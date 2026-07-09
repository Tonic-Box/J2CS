namespace java.security
{
    public class MessageDigest : global::java.lang.Object
    {
        private readonly string algorithm;
        private readonly global::System.Collections.Generic.List<byte> buffer =
                new global::System.Collections.Generic.List<byte>();

        public MessageDigest(global::java.lang.RawNew r) : base(r)
        {
            algorithm = "SHA-256";
        }

        private MessageDigest(string algorithm) : base(global::java.lang.RawNew.I)
        {
            this.algorithm = algorithm;
        }

        public static MessageDigest getInstance(global::java.lang.String algorithm)
        {
            return new MessageDigest(algorithm == null ? "SHA-256" : algorithm.Value);
        }

        private byte[] Hash(byte[] data)
        {
            switch (algorithm)
            {
                case "SHA-512": return global::System.Security.Cryptography.SHA512.HashData(data);
                case "SHA-1": return global::System.Security.Cryptography.SHA1.HashData(data);
                case "MD5": return global::System.Security.Cryptography.MD5.HashData(data);
                default: return global::System.Security.Cryptography.SHA256.HashData(data);
            }
        }

        public void update(sbyte[] input)
        {
            var u = global::java.lang.JRuntime.UnsignedBytes(input);
            if (u != null) { buffer.AddRange(u); }
        }

        public void update(sbyte b)
        {
            buffer.Add((byte)b);
        }

        public sbyte[] digest()
        {
            var data = buffer.ToArray();
            buffer.Clear();
            return global::java.lang.JRuntime.SignedBytes(Hash(data));
        }

        public sbyte[] digest(sbyte[] input)
        {
            update(input);
            return digest();
        }

        public void reset()
        {
            buffer.Clear();
        }

        public global::java.lang.String getAlgorithm()
        {
            return global::java.lang.String.Wrap(algorithm);
        }

        public static int isEqual(sbyte[] digestA, sbyte[] digestB)
        {
            if (digestA == null || digestB == null || digestA.Length != digestB.Length)
            {
                return digestA == digestB ? 1 : 0;
            }
            int diff = 0;
            for (int i = 0; i < digestA.Length; i++) { diff |= digestA[i] ^ digestB[i]; }
            return diff == 0 ? 1 : 0;
        }
    }
}
