namespace java.nio.file
{
    public sealed class StandardCopyOption : global::java.lang.Object, CopyOption
    {
        public static readonly StandardCopyOption REPLACE_EXISTING = new StandardCopyOption("REPLACE_EXISTING");
        public static readonly StandardCopyOption ATOMIC_MOVE = new StandardCopyOption("ATOMIC_MOVE");
        public static readonly StandardCopyOption COPY_ATTRIBUTES = new StandardCopyOption("COPY_ATTRIBUTES");

        private readonly string name;

        private StandardCopyOption(string n) : base(global::java.lang.RawNew.I) { name = n; }
        public StandardCopyOption(global::java.lang.RawNew r) : base(r) { name = ""; }

        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(name); }
    }
}
