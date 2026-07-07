namespace java.util
{
    /// <summary>Binary min-heap ordered by a Comparator or natural ordering. poll() order matches
    /// Java; iteration/toString order is heap-internal and intentionally not relied upon.</summary>
    public class PriorityQueue : global::java.lang.Object, Queue
    {
        private readonly global::System.Collections.Generic.List<global::java.lang.Object> heap =
                new global::System.Collections.Generic.List<global::java.lang.Object>();
        private Comparator comparator;

        public PriorityQueue(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_I_V(int initialCapacity)
        {
        }

        public void __init_Ljava_util_Comparator__V(Comparator c)
        {
            comparator = c;
        }

        public void __init_ILjava_util_Comparator__V(int initialCapacity, Comparator c)
        {
            comparator = c;
        }

        private int Cmp(global::java.lang.Object a, global::java.lang.Object b)
        {
            if (comparator != null) { return comparator.compare(a, b); }
            return JCollections.NaturalCompare(a, b);
        }

        public int add(global::java.lang.Object e) { return offer(e); }

        public int offer(global::java.lang.Object e)
        {
            heap.Add(e);
            int k = heap.Count - 1;
            while (k > 0)
            {
                int parent = (k - 1) >> 1;
                if (Cmp(heap[k], heap[parent]) >= 0) { break; }
                (heap[k], heap[parent]) = (heap[parent], heap[k]);
                k = parent;
            }
            return 1;
        }

        public global::java.lang.Object poll()
        {
            if (heap.Count == 0) { return null; }
            var root = heap[0];
            int last = heap.Count - 1;
            heap[0] = heap[last];
            heap.RemoveAt(last);
            SiftDown(0);
            return root;
        }

        private void SiftDown(int k)
        {
            int n = heap.Count;
            while (true)
            {
                int l = 2 * k + 1;
                int r = 2 * k + 2;
                int smallest = k;
                if (l < n && Cmp(heap[l], heap[smallest]) < 0) { smallest = l; }
                if (r < n && Cmp(heap[r], heap[smallest]) < 0) { smallest = r; }
                if (smallest == k) { break; }
                (heap[k], heap[smallest]) = (heap[smallest], heap[k]);
                k = smallest;
            }
        }

        public global::java.lang.Object peek() { return heap.Count == 0 ? null : heap[0]; }

        public int size() { return heap.Count; }

        public int isEmpty() { return heap.Count == 0 ? 1 : 0; }

        public int contains(global::java.lang.Object o)
        {
            foreach (var e in heap) { if (JCollections.Eq(o, e)) { return 1; } }
            return 0;
        }

        public int remove(global::java.lang.Object o)
        {
            for (int i = 0; i < heap.Count; i++)
            {
                if (JCollections.Eq(o, heap[i]))
                {
                    int last = heap.Count - 1;
                    heap[i] = heap[last];
                    heap.RemoveAt(last);
                    if (i < heap.Count) { SiftDown(i); }
                    return 1;
                }
            }
            return 0;
        }

        public Iterator iterator()
        {
            return new ShimListIterator(new global::System.Collections.Generic.List<global::java.lang.Object>(heap));
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(JCollections.Render(heap));
        }
    }
}
