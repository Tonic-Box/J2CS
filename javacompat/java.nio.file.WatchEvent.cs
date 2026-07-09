namespace java.nio.file
{
    public class WatchEvent : global::java.lang.Object
    {
        internal WatchEvent_S_Kind kindVal;
        internal Path contextVal;

        public WatchEvent(global::java.lang.RawNew r) : base(r) { }
        internal WatchEvent(WatchEvent_S_Kind k, Path ctx) : base(global::java.lang.RawNew.I) { kindVal = k; contextVal = ctx; }

        public WatchEvent_S_Kind kind() { return kindVal; }
        public global::java.lang.Object context() { return contextVal; }
        public int count() { return 1; }
    }
}
