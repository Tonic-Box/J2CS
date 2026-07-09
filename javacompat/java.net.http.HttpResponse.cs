namespace java.net.http
{
    public class HttpResponse : global::java.lang.Object
    {
        internal int status;
        internal byte[] bodyBytes;
        internal int kind;

        public HttpResponse(global::java.lang.RawNew r) : base(r) { }

        public int statusCode() { return status; }

        public global::java.lang.Object body()
        {
            var b = bodyBytes ?? global::System.Array.Empty<byte>();
            if (kind == 1)
            {
                return global::java.lang.JRuntime.Box(global::java.lang.JRuntime.SignedBytes(b), "[B");
            }
            return global::java.lang.String.Wrap(new global::System.Text.UTF8Encoding(false).GetString(b));
        }
    }
}
