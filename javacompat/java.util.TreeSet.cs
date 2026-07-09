namespace java.util
{
    /// <summary>Sorted set backed by an ordered list (Comparator or natural ordering), so iteration
    /// and toString are in ascending order like Java's TreeSet.</summary>
    public class TreeSet : global::java.lang.Object, SortedSet
    {
        private readonly global::System.Collections.Generic.List<global::java.lang.Object> items =
                new global::System.Collections.Generic.List<global::java.lang.Object>();
        private Comparator comparator;

        public TreeSet(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_Ljava_util_Comparator__V(Comparator c)
        {
            comparator = c;
        }

        public void __init_Ljava_util_Collection__V(Collection c)
        {
            var it = c.iterator();
            while (it.hasNext() != 0) { add(it.next()); }
        }

        private int Cmp(global::java.lang.Object a, global::java.lang.Object b)
        {
            if (comparator != null) { return comparator.compare(a, b); }
            return JCollections.NaturalCompare(a, b);
        }

        public int add(global::java.lang.Object e)
        {
            int i = 0;
            while (i < items.Count)
            {
                int c = Cmp(items[i], e);
                if (c == 0) { return 0; }
                if (c > 0) { break; }
                i++;
            }
            items.Insert(i, e);
            return 1;
        }

        public int contains(global::java.lang.Object o)
        {
            foreach (var e in items) { if (Cmp(e, o) == 0) { return 1; } }
            return 0;
        }

        public int remove(global::java.lang.Object o)
        {
            for (int i = 0; i < items.Count; i++)
            {
                if (Cmp(items[i], o) == 0) { items.RemoveAt(i); return 1; }
            }
            return 0;
        }

        public int size() { return items.Count; }

        public int isEmpty() { return items.Count == 0 ? 1 : 0; }

        public global::java.lang.Object first()
        {
            if (items.Count == 0) { throw Empty(); }
            return items[0];
        }

        public global::java.lang.Object last()
        {
            if (items.Count == 0) { throw Empty(); }
            return items[items.Count - 1];
        }

        public global::java.lang.Object pollFirst()
        {
            if (items.Count == 0) { return null; }
            var v = items[0];
            items.RemoveAt(0);
            return v;
        }

        public global::java.lang.Object pollLast()
        {
            if (items.Count == 0) { return null; }
            var v = items[items.Count - 1];
            items.RemoveAt(items.Count - 1);
            return v;
        }

        public global::java.lang.Object floor(global::java.lang.Object e)
        {
            global::java.lang.Object best = null;
            foreach (var x in items) { if (Cmp(x, e) <= 0) { best = x; } else { break; } }
            return best;
        }

        public global::java.lang.Object ceiling(global::java.lang.Object e)
        {
            foreach (var x in items) { if (Cmp(x, e) >= 0) { return x; } }
            return null;
        }

        public global::java.lang.Object lower(global::java.lang.Object e)
        {
            global::java.lang.Object best = null;
            foreach (var x in items) { if (Cmp(x, e) < 0) { best = x; } else { break; } }
            return best;
        }

        public global::java.lang.Object higher(global::java.lang.Object e)
        {
            foreach (var x in items) { if (Cmp(x, e) > 0) { return x; } }
            return null;
        }

        private TreeSet EmptyLike()
        {
            var r = new TreeSet(global::java.lang.RawNew.I);
            r.__init__V();
            r.comparator = comparator;
            return r;
        }

        public SortedSet headSet(global::java.lang.Object toElement)
        {
            var r = EmptyLike();
            foreach (var x in items) { if (Cmp(x, toElement) < 0) { r.add(x); } }
            return r;
        }

        public SortedSet tailSet(global::java.lang.Object fromElement)
        {
            var r = EmptyLike();
            foreach (var x in items) { if (Cmp(x, fromElement) >= 0) { r.add(x); } }
            return r;
        }

        public SortedSet subSet(global::java.lang.Object fromElement, global::java.lang.Object toElement)
        {
            var r = EmptyLike();
            foreach (var x in items) { if (Cmp(x, fromElement) >= 0 && Cmp(x, toElement) < 0) { r.add(x); } }
            return r;
        }

        public Iterator iterator()
        {
            return new ShimListIterator(new global::System.Collections.Generic.List<global::java.lang.Object>(items));
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(JCollections.Render(items));
        }

        private static global::java.lang.JThrow Empty()
        {
            return global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I));
        }
    }
}
