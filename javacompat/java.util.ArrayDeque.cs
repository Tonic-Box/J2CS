namespace java.util
{
    public class ArrayDeque : global::java.lang.Object, Deque
    {
        private readonly global::System.Collections.Generic.LinkedList<global::java.lang.Object> items =
                new global::System.Collections.Generic.LinkedList<global::java.lang.Object>();

        public ArrayDeque(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_I_V(int numElements)
        {
        }

        public void __init_Ljava_util_Collection__V(Collection c)
        {
            var it = c.iterator();
            while (it.hasNext() != 0) { items.AddLast(it.next()); }
        }

        public void addFirst(global::java.lang.Object e) { items.AddFirst(e); }

        public void addLast(global::java.lang.Object e) { items.AddLast(e); }

        public int offerFirst(global::java.lang.Object e) { items.AddFirst(e); return 1; }

        public int offerLast(global::java.lang.Object e) { items.AddLast(e); return 1; }

        public void push(global::java.lang.Object e) { items.AddFirst(e); }

        public int add(global::java.lang.Object e) { items.AddLast(e); return 1; }

        public int offer(global::java.lang.Object e) { items.AddLast(e); return 1; }

        public global::java.lang.Object pop() { return RemoveFirst(); }

        public global::java.lang.Object removeFirst() { return RemoveFirst(); }

        public global::java.lang.Object removeLast()
        {
            if (items.Count == 0) { throw Empty(); }
            var v = items.Last.Value;
            items.RemoveLast();
            return v;
        }

        private global::java.lang.Object RemoveFirst()
        {
            if (items.Count == 0) { throw Empty(); }
            var v = items.First.Value;
            items.RemoveFirst();
            return v;
        }

        public global::java.lang.Object poll()
        {
            return pollFirst();
        }

        public global::java.lang.Object pollFirst()
        {
            if (items.Count == 0) { return null; }
            var v = items.First.Value;
            items.RemoveFirst();
            return v;
        }

        public global::java.lang.Object pollLast()
        {
            if (items.Count == 0) { return null; }
            var v = items.Last.Value;
            items.RemoveLast();
            return v;
        }

        public global::java.lang.Object peek() { return peekFirst(); }

        public global::java.lang.Object peekFirst() { return items.Count == 0 ? null : items.First.Value; }

        public global::java.lang.Object peekLast() { return items.Count == 0 ? null : items.Last.Value; }

        public global::java.lang.Object getFirst()
        {
            if (items.Count == 0) { throw Empty(); }
            return items.First.Value;
        }

        public global::java.lang.Object getLast()
        {
            if (items.Count == 0) { throw Empty(); }
            return items.Last.Value;
        }

        public int size() { return items.Count; }

        public int isEmpty() { return items.Count == 0 ? 1 : 0; }

        public int contains(global::java.lang.Object o)
        {
            foreach (var e in items) { if (JCollections.Eq(o, e)) { return 1; } }
            return 0;
        }

        public int remove(global::java.lang.Object o)
        {
            var node = items.First;
            while (node != null)
            {
                if (JCollections.Eq(o, node.Value)) { items.Remove(node); return 1; }
                node = node.Next;
            }
            return 0;
        }

        public Iterator iterator()
        {
            var snapshot = new global::System.Collections.Generic.List<global::java.lang.Object>(items);
            return new ShimListIterator(snapshot);
        }

        public override global::java.lang.String toString()
        {
            var snapshot = new global::System.Collections.Generic.List<global::java.lang.Object>(items);
            return global::java.lang.String.Wrap(JCollections.Render(snapshot));
        }

        private static global::java.lang.JThrow Empty()
        {
            return global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I));
        }
    }
}
