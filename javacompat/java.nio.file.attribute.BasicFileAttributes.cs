namespace java.nio.file.attribute
{
    public sealed class BasicFileAttributes : global::java.lang.Object
    {
        internal readonly string path;

        public BasicFileAttributes(global::java.lang.RawNew r) : base(r) { path = ""; }
        internal BasicFileAttributes(string p) : base(global::java.lang.RawNew.I) { path = p; }

        public int isRegularFile() { return global::System.IO.File.Exists(path) ? 1 : 0; }
        public int isDirectory() { return global::System.IO.Directory.Exists(path) ? 1 : 0; }
        public int isSymbolicLink() { return 0; }
        public int isOther() { return 0; }
        public long size() { return global::System.IO.File.Exists(path) ? new global::System.IO.FileInfo(path).Length : 0L; }
    }
}
