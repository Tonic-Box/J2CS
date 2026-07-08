namespace java.nio.file
{
    public sealed class FileSystems : global::java.lang.Object
    {
        private static readonly FileSystem DEFAULT = new FileSystem(global::java.lang.RawNew.I);

        public FileSystems(global::java.lang.RawNew r) : base(r)
        {
        }

        public static FileSystem getDefault()
        {
            return DEFAULT;
        }
    }
}
