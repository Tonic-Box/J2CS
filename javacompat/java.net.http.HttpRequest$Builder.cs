namespace java.net.http
{
    public class HttpRequest_S_Builder : global::java.lang.Object
    {
        private string method = "GET";
        private string uriStr = "";
        private string bodyText;
        private readonly global::System.Collections.Generic.Dictionary<string, string> headers =
                new global::System.Collections.Generic.Dictionary<string, string>();

        public HttpRequest_S_Builder(global::java.lang.RawNew r) : base(r) { }

        public HttpRequest_S_Builder uri(global::java.net.URI u) { uriStr = global::java.lang.JRuntime.Str(u); return this; }
        public HttpRequest_S_Builder GET() { method = "GET"; return this; }
        public HttpRequest_S_Builder DELETE() { method = "DELETE"; return this; }

        public HttpRequest_S_Builder POST(HttpRequest_S_BodyPublisher bp) { method = "POST"; bodyText = bp.text; return this; }
        public HttpRequest_S_Builder PUT(HttpRequest_S_BodyPublisher bp) { method = "PUT"; bodyText = bp.text; return this; }

        public HttpRequest_S_Builder header(global::java.lang.String k, global::java.lang.String v)
        {
            if (k != null) { headers[k.Value] = v == null ? "" : v.Value; }
            return this;
        }

        public HttpRequest build()
        {
            var r = new HttpRequest(global::java.lang.RawNew.I);
            r.method = method;
            r.uriStr = uriStr;
            r.bodyText = bodyText;
            foreach (var kv in headers) { r.headers[kv.Key] = kv.Value; }
            return r;
        }
    }
}
