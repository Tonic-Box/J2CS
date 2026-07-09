namespace java.net.http
{
    public class HttpRequest : global::java.lang.Object
    {
        internal string method = "GET";
        internal string uriStr = "";
        internal string bodyText;
        internal readonly global::System.Collections.Generic.Dictionary<string, string> headers =
                new global::System.Collections.Generic.Dictionary<string, string>();

        public HttpRequest(global::java.lang.RawNew r) : base(r) { }

        public static HttpRequest_S_Builder newBuilder() { return new HttpRequest_S_Builder(global::java.lang.RawNew.I); }

        public static HttpRequest_S_Builder newBuilder(global::java.net.URI uri)
        {
            var bld = new HttpRequest_S_Builder(global::java.lang.RawNew.I);
            bld.uri(uri);
            return bld;
        }
    }
}
