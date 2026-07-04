namespace java.security
{
    public class MessageDigest : global::java.lang.Object
    {
        private readonly string algorithm;

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

        public sbyte[] digest(sbyte[] input)
        {
            byte[] data = global::java.lang.JRuntime.UnsignedBytes(input) ?? global::System.Array.Empty<byte>();
            byte[] hash;
            switch (algorithm)
            {
                case "SHA-512":
                    hash = global::System.Security.Cryptography.SHA512.HashData(data);
                    break;
                case "SHA-1":
                    hash = global::System.Security.Cryptography.SHA1.HashData(data);
                    break;
                case "MD5":
                    hash = global::System.Security.Cryptography.MD5.HashData(data);
                    break;
                default:
                    hash = global::System.Security.Cryptography.SHA256.HashData(data);
                    break;
            }
            return global::java.lang.JRuntime.SignedBytes(hash);
        }

        public void reset()
        {
        }
    }
}
