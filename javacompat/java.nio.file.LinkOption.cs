namespace java.nio.file
{
    public sealed class LinkOption : global::java.lang.Object, OpenOption, CopyOption
    {
        public static readonly LinkOption NOFOLLOW_LINKS = new LinkOption("NOFOLLOW_LINKS");

        private readonly string name;

        private LinkOption(string n) : base(global::java.lang.RawNew.I) { name = n; }
        public LinkOption(global::java.lang.RawNew r) : base(r) { name = ""; }

        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(name); }
    }
}
