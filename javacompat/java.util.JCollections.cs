namespace java.util
{
    internal sealed class ShimListIterator : global::java.lang.Object, Iterator
    {
        private readonly global::System.Collections.Generic.List<global::java.lang.Object> items;
        private int index;

        internal ShimListIterator(global::System.Collections.Generic.List<global::java.lang.Object> items)
                : base(global::java.lang.RawNew.I)
        {
            this.items = items;
        }

        public int hasNext()
        {
            return index < items.Count ? 1 : 0;
        }

        public global::java.lang.Object next()
        {
            return items[index++];
        }
    }

    internal sealed class ShimListIndexIter : global::java.lang.Object, ListIterator
    {
        private readonly List backing;
        private int cursor;
        private int last = -1;

        internal ShimListIndexIter(List backing, int start) : base(global::java.lang.RawNew.I)
        {
            this.backing = backing;
            this.cursor = start;
        }

        public int hasNext() { return cursor < backing.size() ? 1 : 0; }
        public global::java.lang.Object next() { last = cursor; return backing.get(cursor++); }
        public int hasPrevious() { return cursor > 0 ? 1 : 0; }
        public global::java.lang.Object previous() { last = --cursor; return backing.get(cursor); }
        public int nextIndex() { return cursor; }
        public int previousIndex() { return cursor - 1; }
        public void set(global::java.lang.Object e) { backing.set(last, e); }
        public void add(global::java.lang.Object e) { backing.add(cursor++, e); last = -1; }
        public void remove() { backing.remove(--cursor); last = -1; }
    }

    internal static class JCollections
    {
        public static bool Eq(global::java.lang.Object a, global::java.lang.Object b)
        {
            if (a == null)
            {
                return b == null;
            }
            return a.equals(b) != 0;
        }

        public static int Hash(global::java.lang.Object o)
        {
            return o == null ? 0 : o.hashCode();
        }

        public static int NaturalCompare(global::java.lang.Object a, global::java.lang.Object b)
        {
            if (a is global::java.lang.Comparable ca)
            {
                return ca.compareTo(b);
            }
            if (a is global::java.lang.Number na && b is global::java.lang.Number nb)
            {
                double x = na.doubleValue();
                double y = nb.doubleValue();
                return x < y ? -1 : (x > y ? 1 : 0);
            }
            return global::System.String.CompareOrdinal(
                    global::java.lang.JRuntime.Str(a), global::java.lang.JRuntime.Str(b));
        }

        public static string Render(global::System.Collections.Generic.List<global::java.lang.Object> items)
        {
            var sb = new global::System.Text.StringBuilder();
            sb.Append('[');
            for (int i = 0; i < items.Count; i++)
            {
                if (i > 0)
                {
                    sb.Append(", ");
                }
                sb.Append(global::java.lang.JRuntime.Str(items[i]));
            }
            sb.Append(']');
            return sb.ToString();
        }
    }
}
