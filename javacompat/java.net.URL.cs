namespace java.net
{
    public sealed class URL : global::java.lang.Object
    {
        private string protocol;
        private string host;
        private int port = -1;
        private string path = "";
        private string query;
        private string refPart;
        private string full = "";

        public URL(global::java.lang.RawNew r) : base(r) { }

        public void __init_Ljava_lang_String__V(global::java.lang.String spec)
        {
            Parse(spec == null ? "" : spec.Value);
        }

        private void Parse(string s)
        {
            full = s;
            var m = global::System.Text.RegularExpressions.Regex.Match(s,
                    @"^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?$");
            protocol = m.Groups[1].Success ? m.Groups[2].Value : null;
            string authority = m.Groups[3].Success ? m.Groups[4].Value : null;
            path = m.Groups[5].Value;
            query = m.Groups[6].Success ? m.Groups[7].Value : null;
            refPart = m.Groups[8].Success ? m.Groups[9].Value : null;
            if (authority != null)
            {
                int at = authority.IndexOf('@');
                string hp = at >= 0 ? authority.Substring(at + 1) : authority;
                int colon = hp.LastIndexOf(':');
                if (colon >= 0 && int.TryParse(hp.Substring(colon + 1), out var p)) { port = p; host = hp.Substring(0, colon); }
                else { host = hp; }
            }
        }

        private static global::java.lang.String W(string v) { return v == null ? null : global::java.lang.String.Wrap(v); }

        public global::java.lang.String getProtocol() { return W(protocol); }
        public global::java.lang.String getHost() { return W(host); }
        public int getPort() { return port; }
        public global::java.lang.String getPath() { return W(path); }
        public global::java.lang.String getQuery() { return W(query); }
        public global::java.lang.String getRef() { return W(refPart); }

        public global::java.lang.String getFile()
        {
            return global::java.lang.String.Wrap(query != null ? path + "?" + query : path);
        }

        public global::java.lang.String toExternalForm() { return global::java.lang.String.Wrap(full); }

        public URI toURI()
        {
            var u = new URI(global::java.lang.RawNew.I);
            u.__init_Ljava_lang_String__V(global::java.lang.String.Wrap(full));
            return u;
        }

        public override int equals(global::java.lang.Object o) { return o is URL u && u.full == full ? 1 : 0; }
        public override int hashCode() { return full.GetHashCode(); }
        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(full); }
    }
}
