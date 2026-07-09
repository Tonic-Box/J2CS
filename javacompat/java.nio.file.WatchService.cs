namespace java.nio.file
{
    public class WatchService : global::java.lang.Object
    {
        private readonly global::System.Collections.Concurrent.BlockingCollection<WatchKey> signalled =
                new global::System.Collections.Concurrent.BlockingCollection<WatchKey>();
        internal readonly global::System.Collections.Generic.List<global::System.IO.FileSystemWatcher> watchers =
                new global::System.Collections.Generic.List<global::System.IO.FileSystemWatcher>();

        public WatchService(global::java.lang.RawNew r) : base(r) { }

        internal void Signal(WatchKey key) { signalled.Add(key); }

        public WatchKey take() { return signalled.Take(); }
        public WatchKey poll() { return signalled.TryTake(out var k) ? k : null; }

        public void close()
        {
            foreach (var w in watchers) { w.Dispose(); }
        }
    }
}
