namespace java.nio.channels
{
    /// <summary>
    /// Minimal shim: only close() is exercised (via a try-with-resources whose branch is not taken
    /// when the native library is found on the library path). Instances originate from transpiled
    /// app code, so no factory/open surface is needed here.
    /// </summary>
    public class FileChannel : global::java.lang.Object
    {
        public FileChannel(global::java.lang.RawNew r) : base(r)
        {
        }

        public void close()
        {
        }
    }
}
