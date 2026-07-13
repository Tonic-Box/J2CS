namespace java.lang
{
    public sealed class Package : global::java.lang.Object
    {
        private readonly string name;

        public Package(global::java.lang.RawNew r) : base(r)
        {
            name = "";
        }

        internal Package(string n) : base(global::java.lang.RawNew.I)
        {
            name = n ?? "";
        }

        public global::java.lang.String getName()
        {
            return global::java.lang.String.Wrap(name);
        }

        // No jar manifest backs a package in the transpiled world; callers treat null as "unknown"
        // and fall back to a default (e.g. LWJGL's "SNAPSHOT").
        public global::java.lang.String getSpecificationVersion()
        {
            return null;
        }

        public global::java.lang.String getImplementationVersion()
        {
            return null;
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap("package " + name);
        }
    }
}
