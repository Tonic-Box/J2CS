namespace java.security
{
    public class SecureRandom : global::java.lang.Object
    {
        public SecureRandom(global::java.lang.RawNew r) : base(r)
        {
        }

        public void nextBytes(sbyte[] bytes)
        {
            if (bytes == null || bytes.Length == 0)
            {
                return;
            }
            byte[] tmp = new byte[bytes.Length];
            global::System.Security.Cryptography.RandomNumberGenerator.Fill(tmp);
            for (int i = 0; i < bytes.Length; i++)
            {
                bytes[i] = (sbyte)tmp[i];
            }
        }
    }
}
