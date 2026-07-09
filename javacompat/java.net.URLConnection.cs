namespace java.net
{
    public class URLConnection : global::java.lang.Object
    {
        protected string urlStr = "";

        public URLConnection(global::java.lang.RawNew r) : base(r) { }

        public virtual void connect() { }
        public virtual global::java.io.InputStream getInputStream() { return null; }
        public virtual void setRequestProperty(global::java.lang.String key, global::java.lang.String value) { }
        public virtual void setDoOutput(int doOutput) { }
        public virtual void setConnectTimeout(int timeout) { }
        public virtual void setReadTimeout(int timeout) { }
    }
}
