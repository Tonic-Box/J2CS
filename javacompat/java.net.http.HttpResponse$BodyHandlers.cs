namespace java.net.http
{
    public class HttpResponse_S_BodyHandlers : global::java.lang.Object
    {
        public HttpResponse_S_BodyHandlers(global::java.lang.RawNew r) : base(r) { }

        public static HttpResponse_S_BodyHandler ofString()
        {
            var h = new HttpResponse_S_BodyHandler(global::java.lang.RawNew.I);
            h.kind = 0;
            return h;
        }

        public static HttpResponse_S_BodyHandler ofByteArray()
        {
            var h = new HttpResponse_S_BodyHandler(global::java.lang.RawNew.I);
            h.kind = 1;
            return h;
        }
    }
}
