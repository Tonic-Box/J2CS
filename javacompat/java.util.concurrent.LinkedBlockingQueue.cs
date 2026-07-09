namespace java.util.concurrent
{
    internal sealed class BlockingQueueCore
    {
        private readonly object gate = new object();
        private readonly global::System.Collections.Generic.Queue<global::java.lang.Object> q =
                new global::System.Collections.Generic.Queue<global::java.lang.Object>();
        private readonly int cap;

        internal BlockingQueueCore(int capacity) { cap = capacity; }

        internal int Offer(global::java.lang.Object e)
        {
            lock (gate)
            {
                if (q.Count >= cap) { return 0; }
                q.Enqueue(e);
                global::System.Threading.Monitor.PulseAll(gate);
                return 1;
            }
        }

        internal global::java.lang.Object Poll()
        {
            lock (gate)
            {
                if (q.Count == 0) { return null; }
                var v = q.Dequeue();
                global::System.Threading.Monitor.PulseAll(gate);
                return v;
            }
        }

        internal global::java.lang.Object Peek()
        {
            lock (gate) { return q.Count == 0 ? null : q.Peek(); }
        }

        internal void Put(global::java.lang.Object e)
        {
            lock (gate)
            {
                while (q.Count >= cap) { global::System.Threading.Monitor.Wait(gate); }
                q.Enqueue(e);
                global::System.Threading.Monitor.PulseAll(gate);
            }
        }

        internal global::java.lang.Object Take()
        {
            lock (gate)
            {
                while (q.Count == 0) { global::System.Threading.Monitor.Wait(gate); }
                var v = q.Dequeue();
                global::System.Threading.Monitor.PulseAll(gate);
                return v;
            }
        }

        internal int Size() { lock (gate) { return q.Count; } }
        internal int RemainingCapacity() { lock (gate) { return cap - q.Count; } }

        internal int Contains(global::java.lang.Object o)
        {
            lock (gate)
            {
                foreach (var x in q) { if (global::java.util.JCollections.Eq(x, o)) { return 1; } }
                return 0;
            }
        }

        internal int Remove(global::java.lang.Object o)
        {
            lock (gate)
            {
                var kept = new global::System.Collections.Generic.List<global::java.lang.Object>();
                bool removed = false;
                foreach (var x in q)
                {
                    if (!removed && global::java.util.JCollections.Eq(x, o)) { removed = true; continue; }
                    kept.Add(x);
                }
                if (removed) { q.Clear(); foreach (var x in kept) { q.Enqueue(x); } }
                return removed ? 1 : 0;
            }
        }

        internal global::java.util.Iterator Iterator()
        {
            lock (gate)
            {
                return new global::java.util.ShimListIterator(new global::System.Collections.Generic.List<global::java.lang.Object>(q));
            }
        }
    }

    public class LinkedBlockingQueue : global::java.lang.Object, BlockingQueue
    {
        private BlockingQueueCore core = new BlockingQueueCore(int.MaxValue);

        public LinkedBlockingQueue(global::java.lang.RawNew r) : base(r) { }
        public void __init__V() { }
        public void __init_I_V(int capacity) { core = new BlockingQueueCore(capacity); }

        public int add(global::java.lang.Object e)
        {
            if (core.Offer(e) == 0) { throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I)); }
            return 1;
        }

        public int offer(global::java.lang.Object e) { return core.Offer(e); }
        public global::java.lang.Object poll() { return core.Poll(); }
        public global::java.lang.Object peek() { return core.Peek(); }
        public void put(global::java.lang.Object e) { core.Put(e); }
        public global::java.lang.Object take() { return core.Take(); }
        public int size() { return core.Size(); }
        public int isEmpty() { return core.Size() == 0 ? 1 : 0; }
        public int remainingCapacity() { return core.RemainingCapacity(); }
        public int contains(global::java.lang.Object o) { return core.Contains(o); }
        public int remove(global::java.lang.Object o) { return core.Remove(o); }
        public global::java.util.Iterator iterator() { return core.Iterator(); }
    }
}
