namespace java.lang
{
    /// <summary>
    /// Resolves resources from the packaged resources/ directory beside the assembly (the input jar's
    /// non-class entries, extracted at transpile time). Names are classpath-absolute here; a leading
    /// slash is tolerated. Class.getResource* resolves package-relative names before delegating.
    /// </summary>
    public class ClassLoader : global::java.lang.Object
    {
        internal static readonly ClassLoader SystemClassLoader = new ClassLoader(global::java.lang.RawNew.I);

        public ClassLoader(global::java.lang.RawNew r) : base(r)
        {
        }

        public global::java.net.URL getResource(String name)
        {
            return ResourceUrl(Absolute(name));
        }

        public global::java.io.InputStream getResourceAsStream(String name)
        {
            return OpenResource(Absolute(name));
        }

        public global::java.util.Enumeration getResources(String name)
        {
            return global::java.util.EmptyEnumeration.Instance;
        }

        private static readonly string ResourceRoot =
            global::System.IO.Path.Combine(global::System.AppContext.BaseDirectory, "resources");

        internal static string Absolute(String name)
        {
            string n = name == null ? "" : name.Value;
            return n.StartsWith("/") ? n.Substring(1) : n;
        }

        private static string PathOf(string absolute)
        {
            return global::System.IO.Path.Combine(
                ResourceRoot, absolute.Replace('/', global::System.IO.Path.DirectorySeparatorChar));
        }

        internal static global::java.io.InputStream OpenResource(string absolute)
        {
            if (absolute.Length == 0)
            {
                return null;
            }
            string path = PathOf(absolute);
            if (!global::System.IO.File.Exists(path))
            {
                return null;
            }
            byte[] bytes = global::System.IO.File.ReadAllBytes(path);
            var signed = new sbyte[bytes.Length];
            global::System.Buffer.BlockCopy(bytes, 0, signed, 0, bytes.Length);
            var stream = new global::java.io.ByteArrayInputStream(global::java.lang.RawNew.I);
            stream.__init__B_V(signed);
            return stream;
        }

        internal static global::java.net.URL ResourceUrl(string absolute)
        {
            if (absolute.Length == 0)
            {
                return null;
            }
            string path = PathOf(absolute);
            if (!global::System.IO.File.Exists(path))
            {
                return null;
            }
            var url = new global::java.net.URL(global::java.lang.RawNew.I);
            url.__init_Ljava_lang_String__V(global::java.lang.String.Wrap(
                    new global::System.Uri(global::System.IO.Path.GetFullPath(path)).AbsoluteUri));
            return url;
        }
    }
}
