namespace java.net
{
    public sealed class URI : global::java.lang.Object
    {
        private string scheme;
        private string authority;
        private string userInfo;
        private string host;
        private int port = -1;
        private string path = "";
        private string query;
        private string fragment;
        private string full = "";

        public URI(global::java.lang.RawNew r) : base(r) { }

        public void __init_Ljava_lang_String__V(global::java.lang.String str)
        {
            Parse(str == null ? "" : str.Value);
        }

        private void Parse(string s)
        {
            full = s;
            var m = global::System.Text.RegularExpressions.Regex.Match(s,
                    @"^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?$");
            scheme = m.Groups[1].Success ? m.Groups[2].Value : null;
            authority = m.Groups[3].Success ? m.Groups[4].Value : null;
            path = m.Groups[5].Value;
            query = m.Groups[6].Success ? m.Groups[7].Value : null;
            fragment = m.Groups[8].Success ? m.Groups[9].Value : null;
            if (authority != null) { ParseAuthority(authority); }
        }

        private void ParseAuthority(string a)
        {
            string rest = a;
            int at = rest.IndexOf('@');
            if (at >= 0) { userInfo = rest.Substring(0, at); rest = rest.Substring(at + 1); }
            int colon = rest.LastIndexOf(':');
            if (colon >= 0 && int.TryParse(rest.Substring(colon + 1), out var p))
            {
                port = p;
                host = rest.Substring(0, colon);
            }
            else
            {
                host = rest;
            }
        }

        private static global::java.lang.String W(string v) { return v == null ? null : global::java.lang.String.Wrap(v); }

        public global::java.lang.String getScheme() { return W(scheme); }
        public global::java.lang.String getAuthority() { return W(authority); }
        public global::java.lang.String getUserInfo() { return W(userInfo); }
        public global::java.lang.String getHost() { return W(host); }
        public int getPort() { return port; }
        public global::java.lang.String getPath() { return W(path); }
        public global::java.lang.String getQuery() { return W(query); }
        public global::java.lang.String getFragment() { return W(fragment); }
        public int isAbsolute() { return scheme != null ? 1 : 0; }

        public URL toURL()
        {
            var u = new URL(global::java.lang.RawNew.I);
            u.__init_Ljava_lang_String__V(global::java.lang.String.Wrap(full));
            return u;
        }

        public override int equals(global::java.lang.Object o) { return o is URI u && u.full == full ? 1 : 0; }
        public override int hashCode() { return full.GetHashCode(); }
        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(full); }
    }
}
