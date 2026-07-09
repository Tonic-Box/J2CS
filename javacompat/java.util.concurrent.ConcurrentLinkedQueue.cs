namespace java.util.concurrent
{
    public class ConcurrentLinkedQueue : global::java.lang.Object, global::java.util.Queue
    {
        private readonly global::System.Collections.Concurrent.ConcurrentQueue<global::java.lang.Object> q =
                new global::System.Collections.Concurrent.ConcurrentQueue<global::java.lang.Object>();

        public ConcurrentLinkedQueue(global::java.lang.RawNew r) : base(r) { }
        public void __init__V() { }

        public int add(global::java.lang.Object e) { q.Enqueue(e); return 1; }
        public int offer(global::java.lang.Object e) { q.Enqueue(e); return 1; }
        public global::java.lang.Object poll() { return q.TryDequeue(out var v) ? v : null; }
        public global::java.lang.Object peek() { return q.TryPeek(out var v) ? v : null; }
        public int size() { return q.Count; }
        public int isEmpty() { return q.IsEmpty ? 1 : 0; }

        public int contains(global::java.lang.Object o)
        {
            foreach (var x in q) { if (global::java.util.JCollections.Eq(x, o)) { return 1; } }
            return 0;
        }

        public int remove(global::java.lang.Object o) { return 0; }

        public global::java.util.Iterator iterator()
        {
            return new global::java.util.ShimListIterator(new global::System.Collections.Generic.List<global::java.lang.Object>(q));
        }
    }
}
