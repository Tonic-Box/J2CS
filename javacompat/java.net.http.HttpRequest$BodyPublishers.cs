namespace java.net.http
{
    public class HttpRequest_S_BodyPublishers : global::java.lang.Object
    {
        public HttpRequest_S_BodyPublishers(global::java.lang.RawNew r) : base(r) { }

        public static HttpRequest_S_BodyPublisher ofString(global::java.lang.String s)
        {
            var b = new HttpRequest_S_BodyPublisher(global::java.lang.RawNew.I);
            b.text = s == null ? "" : s.Value;
            return b;
        }

        public static HttpRequest_S_BodyPublisher noBody()
        {
            return new HttpRequest_S_BodyPublisher(global::java.lang.RawNew.I);
        }
    }
}
