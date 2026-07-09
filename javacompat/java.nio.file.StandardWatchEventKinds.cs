namespace java.nio.file
{
    public class StandardWatchEventKinds : global::java.lang.Object
    {
        public static readonly WatchEvent_S_Kind ENTRY_CREATE = new WatchEvent_S_Kind("ENTRY_CREATE");
        public static readonly WatchEvent_S_Kind ENTRY_DELETE = new WatchEvent_S_Kind("ENTRY_DELETE");
        public static readonly WatchEvent_S_Kind ENTRY_MODIFY = new WatchEvent_S_Kind("ENTRY_MODIFY");
        public static readonly WatchEvent_S_Kind OVERFLOW = new WatchEvent_S_Kind("OVERFLOW");

        public StandardWatchEventKinds(global::java.lang.RawNew r) : base(r) { }
    }
}
