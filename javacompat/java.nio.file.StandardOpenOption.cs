namespace java.nio.file
{
    public sealed class StandardOpenOption : global::java.lang.Object, OpenOption
    {
        public static readonly StandardOpenOption READ = new StandardOpenOption("READ");
        public static readonly StandardOpenOption WRITE = new StandardOpenOption("WRITE");
        public static readonly StandardOpenOption APPEND = new StandardOpenOption("APPEND");
        public static readonly StandardOpenOption CREATE = new StandardOpenOption("CREATE");
        public static readonly StandardOpenOption CREATE_NEW = new StandardOpenOption("CREATE_NEW");
        public static readonly StandardOpenOption TRUNCATE_EXISTING = new StandardOpenOption("TRUNCATE_EXISTING");
        public static readonly StandardOpenOption DELETE_ON_CLOSE = new StandardOpenOption("DELETE_ON_CLOSE");

        private readonly string name;

        private StandardOpenOption(string n) : base(global::java.lang.RawNew.I) { name = n; }
        public StandardOpenOption(global::java.lang.RawNew r) : base(r) { name = ""; }

        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(name); }
    }
}
