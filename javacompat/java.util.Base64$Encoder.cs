namespace java.util
{
    public class Base64_S_Encoder : global::java.lang.Object
    {
        internal Base64_S_Encoder() : base(global::java.lang.RawNew.I)
        {
        }

        public global::java.lang.String encodeToString(sbyte[] src)
        {
            return global::java.lang.String.Wrap(global::System.Convert.ToBase64String(
                    global::java.lang.JRuntime.UnsignedBytes(src) ?? global::System.Array.Empty<byte>()));
        }
    }
}
