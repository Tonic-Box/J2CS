namespace java.net
{
    public class URLConnection : global::java.lang.Object
    {
        protected string urlStr = "";

        public URLConnection(global::java.lang.RawNew r) : base(r) { }

        internal void SetUrl(string u) { urlStr = u; }

        public virtual void connect() { }

        public virtual global::java.io.InputStream getInputStream()
        {
            string path = FilePath(urlStr);
            if (path != null && global::System.IO.File.Exists(path))
            {
                var bais = new global::java.io.ByteArrayInputStream(global::java.lang.RawNew.I);
                bais.__init__B_V(global::java.lang.JRuntime.SignedBytes(global::System.IO.File.ReadAllBytes(path)));
                return bais;
            }
            return null;
        }

        // A read-only connection has no real output; jME's natives-hash cleanup closes both streams,
        // so hand back a throwaway sink rather than null.
        public virtual global::java.io.OutputStream getOutputStream()
        {
            var baos = new global::java.io.ByteArrayOutputStream(global::java.lang.RawNew.I);
            baos.__init__V();
            return baos;
        }

        public virtual long getLastModified()
        {
            string path = FilePath(urlStr);
            if (path != null && global::System.IO.File.Exists(path))
            {
                return new global::System.DateTimeOffset(
                        global::System.IO.File.GetLastWriteTimeUtc(path)).ToUnixTimeMilliseconds();
            }
            return 0L;
        }

        public virtual long getContentLengthLong()
        {
            string path = FilePath(urlStr);
            return path != null && global::System.IO.File.Exists(path)
                    ? new global::System.IO.FileInfo(path).Length : -1L;
        }

        public virtual int getContentLength()
        {
            long n = getContentLengthLong();
            return n > int.MaxValue ? -1 : (int)n;
        }

        public virtual void setRequestProperty(global::java.lang.String key, global::java.lang.String value) { }
        public virtual void setDoOutput(int doOutput) { }
        public virtual void setConnectTimeout(int timeout) { }
        public virtual void setReadTimeout(int timeout) { }

        /// <summary>Local filesystem path of a file: URL, or null for any other scheme.</summary>
        protected static string FilePath(string url)
        {
            if (url == null || !url.StartsWith("file:"))
            {
                return null;
            }
            try
            {
                return new global::System.Uri(url).LocalPath;
            }
            catch (global::System.Exception)
            {
                return null;
            }
        }
    }
}
