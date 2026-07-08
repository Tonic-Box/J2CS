namespace java.nio.file
{
    public sealed class FileSystem : global::java.lang.Object
    {
        public FileSystem(global::java.lang.RawNew r) : base(r)
        {
        }

        public global::java.lang.String getSeparator()
        {
            return global::java.lang.String.Wrap(global::System.IO.Path.DirectorySeparatorChar.ToString());
        }

        public Path getPath(global::java.lang.String first, global::java.lang.String[] more)
        {
            return Paths.get(first, more);
        }
    }
}
