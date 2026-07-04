namespace java.util
{
    public class Base64 : global::java.lang.Object
    {
        public Base64(global::java.lang.RawNew r) : base(r)
        {
        }

        public static Base64_S_Encoder getEncoder()
        {
            return new Base64_S_Encoder();
        }

        public static Base64_S_Decoder getDecoder()
        {
            return new Base64_S_Decoder();
        }
    }
}
