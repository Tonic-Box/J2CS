namespace java.nio.file
{
    public class WatchKey : global::java.lang.Object
    {
        internal readonly global::System.Collections.Generic.List<WatchEvent> pending =
                new global::System.Collections.Generic.List<WatchEvent>();
        internal WatchService service;
        internal global::System.IO.FileSystemWatcher fsw;
        private readonly object gate = new object();

        public WatchKey(global::java.lang.RawNew r) : base(r) { }

        internal void Enqueue(WatchEvent ev)
        {
            lock (gate) { pending.Add(ev); }
            service.Signal(this);
        }

        public global::java.util.List pollEvents()
        {
            var list = new global::java.util.ArrayList(global::java.lang.RawNew.I);
            list.__init__V();
            lock (gate)
            {
                foreach (var ev in pending) { list.add(ev); }
                pending.Clear();
            }
            return list;
        }

        public int reset() { return 1; }
        public void cancel() { if (fsw != null) { fsw.Dispose(); } }
        public int isValid() { return 1; }
    }
}
