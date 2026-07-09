namespace java.nio.file
{
    public class WatchEvent_S_Kind : global::java.lang.Object
    {
        internal readonly string kindName;

        internal WatchEvent_S_Kind(string n) : base(global::java.lang.RawNew.I) { kindName = n; }
        public WatchEvent_S_Kind(global::java.lang.RawNew r) : base(r) { kindName = ""; }

        public global::java.lang.String name() { return global::java.lang.String.Wrap(kindName); }
        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(kindName); }
    }
}
