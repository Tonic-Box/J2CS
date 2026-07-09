namespace java.util.concurrent
{
    public class ArrayBlockingQueue : global::java.lang.Object, BlockingQueue
    {
        private BlockingQueueCore core = new BlockingQueueCore(int.MaxValue);

        public ArrayBlockingQueue(global::java.lang.RawNew r) : base(r) { }
        public void __init_I_V(int capacity) { core = new BlockingQueueCore(capacity); }
        public void __init_IZ_V(int capacity, int fair) { core = new BlockingQueueCore(capacity); }

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
