namespace java.net.http
{
    public class HttpClient : global::java.lang.Object
    {
        public HttpClient(global::java.lang.RawNew r) : base(r) { }

        public static HttpClient newHttpClient() { return new HttpClient(global::java.lang.RawNew.I); }
        public static HttpClient_S_Builder newBuilder() { return new HttpClient_S_Builder(global::java.lang.RawNew.I); }

        public HttpResponse send(HttpRequest request, HttpResponse_S_BodyHandler handler)
        {
            using (var client = new global::System.Net.Http.HttpClient())
            {
                client.Timeout = global::System.TimeSpan.FromSeconds(15);
                var req = new global::System.Net.Http.HttpRequestMessage(
                        new global::System.Net.Http.HttpMethod(request.method), request.uriStr);
                if (request.bodyText != null)
                {
                    req.Content = new global::System.Net.Http.StringContent(request.bodyText, global::System.Text.Encoding.UTF8);
                }
                foreach (var kv in request.headers) { req.Headers.TryAddWithoutValidation(kv.Key, kv.Value); }
                var resp = client.SendAsync(req).GetAwaiter().GetResult();
                var r = new HttpResponse(global::java.lang.RawNew.I);
                r.status = (int)resp.StatusCode;
                r.bodyBytes = resp.Content.ReadAsByteArrayAsync().GetAwaiter().GetResult();
                r.kind = handler.kind;
                return r;
            }
        }
    }
}
