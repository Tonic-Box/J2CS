namespace java.util
{
    public class Base64_S_Decoder : global::java.lang.Object
    {
        internal Base64_S_Decoder() : base(global::java.lang.RawNew.I)
        {
        }

        public sbyte[] decode(global::java.lang.String src)
        {
            return global::java.lang.JRuntime.SignedBytes(global::System.Convert.FromBase64String(
                    global::java.lang.JRuntime.Cs(src)));
        }
    }
}
