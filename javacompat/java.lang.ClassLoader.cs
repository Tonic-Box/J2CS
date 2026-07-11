namespace java.lang
{
    /// <summary>
    /// Minimal shim: the transpiled world has no classpath, so resource lookups yield null. Callers
    /// that probe for a bundled resource fall through to their filesystem/library-path branch.
    /// </summary>
    public class ClassLoader : global::java.lang.Object
    {
        internal static readonly ClassLoader SystemClassLoader = new ClassLoader(global::java.lang.RawNew.I);

        public ClassLoader(global::java.lang.RawNew r) : base(r)
        {
        }

        public global::java.net.URL getResource(String name)
        {
            return null;
        }

        public global::java.io.InputStream getResourceAsStream(String name)
        {
            return null;
        }
    }
}
