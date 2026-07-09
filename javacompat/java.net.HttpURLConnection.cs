namespace java.net
{
    public class HttpURLConnection : URLConnection
    {
        private string method = "GET";
        private byte[] body;
        private int status;
        private readonly global::System.Collections.Generic.Dictionary<string, string> headers =
                new global::System.Collections.Generic.Dictionary<string, string>();

        public HttpURLConnection(global::java.lang.RawNew r) : base(r) { }

        internal void SetUrl(string u) { urlStr = u; }

        public void setRequestMethod(global::java.lang.String m) { method = m == null ? "GET" : m.Value; }

        public override void setRequestProperty(global::java.lang.String key, global::java.lang.String value)
        {
            if (key != null) { headers[key.Value] = value == null ? "" : value.Value; }
        }

        private void Send()
        {
            if (body != null) { return; }
            using (var client = new global::System.Net.Http.HttpClient())
            {
                client.Timeout = global::System.TimeSpan.FromSeconds(15);
                var req = new global::System.Net.Http.HttpRequestMessage(new global::System.Net.Http.HttpMethod(method), urlStr);
                foreach (var kv in headers) { req.Headers.TryAddWithoutValidation(kv.Key, kv.Value); }
                var resp = client.SendAsync(req).GetAwaiter().GetResult();
                status = (int)resp.StatusCode;
                body = resp.Content.ReadAsByteArrayAsync().GetAwaiter().GetResult();
            }
        }

        public override void connect() { Send(); }

        public int getResponseCode() { Send(); return status; }

        public override global::java.io.InputStream getInputStream()
        {
            Send();
            var bais = new global::java.io.ByteArrayInputStream(global::java.lang.RawNew.I);
            bais.__init__B_V(global::java.lang.JRuntime.SignedBytes(body ?? global::System.Array.Empty<byte>()));
            return bais;
        }

        public void disconnect() { }
    }
}
