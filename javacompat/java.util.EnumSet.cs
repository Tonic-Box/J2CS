namespace java.util
{
    public sealed class EnumSet : global::java.lang.Object, Set
    {
        private readonly global::System.Collections.Generic.List<global::java.lang.Object> items =
                new global::System.Collections.Generic.List<global::java.lang.Object>();

        public EnumSet(global::java.lang.RawNew r) : base(r) { }

        private static int Ord(global::java.lang.Object e) { return ((global::java.lang.Enum)e).ordinal(); }

        public int add(global::java.lang.Object e)
        {
            int oe = Ord(e);
            int i = 0;
            while (i < items.Count)
            {
                int o = Ord(items[i]);
                if (o == oe) { return 0; }
                if (o > oe) { break; }
                i++;
            }
            items.Insert(i, e);
            return 1;
        }

        public int contains(global::java.lang.Object o)
        {
            foreach (var x in items) { if (JCollections.Eq(x, o)) { return 1; } }
            return 0;
        }

        public int remove(global::java.lang.Object o)
        {
            for (int i = 0; i < items.Count; i++)
            {
                if (JCollections.Eq(items[i], o)) { items.RemoveAt(i); return 1; }
            }
            return 0;
        }

        public int size() { return items.Count; }
        public int isEmpty() { return items.Count == 0 ? 1 : 0; }
        public void clear() { items.Clear(); }

        public Iterator iterator()
        {
            return new ShimListIterator(new global::System.Collections.Generic.List<global::java.lang.Object>(items));
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(JCollections.Render(items));
        }

        public static EnumSet noneOf(global::java.lang.Class elementType) { return new EnumSet(global::java.lang.RawNew.I); }

        public static EnumSet allOf(global::java.lang.Class elementType)
        {
            var s = new EnumSet(global::java.lang.RawNew.I);
            var vals = elementType.getEnumConstants();
            if (vals != null) { foreach (var e in vals) { s.add(e); } }
            return s;
        }

        public static EnumSet range(global::java.lang.Enum from, global::java.lang.Enum to)
        {
            var s = new EnumSet(global::java.lang.RawNew.I);
            var vals = from.getClass().getEnumConstants();
            int lo = from.ordinal();
            int hi = to.ordinal();
            if (vals != null)
            {
                foreach (var e in vals)
                {
                    int o = ((global::java.lang.Enum)e).ordinal();
                    if (o >= lo && o <= hi) { s.add(e); }
                }
            }
            return s;
        }

        public static EnumSet of(global::java.lang.Enum e)
        {
            var s = new EnumSet(global::java.lang.RawNew.I);
            s.add(e);
            return s;
        }

        public static EnumSet of(global::java.lang.Enum e1, global::java.lang.Enum e2)
        {
            var s = new EnumSet(global::java.lang.RawNew.I);
            s.add(e1); s.add(e2);
            return s;
        }

        public static EnumSet of(global::java.lang.Enum e1, global::java.lang.Enum e2, global::java.lang.Enum e3)
        {
            var s = new EnumSet(global::java.lang.RawNew.I);
            s.add(e1); s.add(e2); s.add(e3);
            return s;
        }

        public static EnumSet of(global::java.lang.Enum e1, global::java.lang.Enum e2, global::java.lang.Enum e3, global::java.lang.Enum e4)
        {
            var s = new EnumSet(global::java.lang.RawNew.I);
            s.add(e1); s.add(e2); s.add(e3); s.add(e4);
            return s;
        }

        public static EnumSet of(global::java.lang.Enum e1, global::java.lang.Enum e2, global::java.lang.Enum e3, global::java.lang.Enum e4, global::java.lang.Enum e5)
        {
            var s = new EnumSet(global::java.lang.RawNew.I);
            s.add(e1); s.add(e2); s.add(e3); s.add(e4); s.add(e5);
            return s;
        }

        public static EnumSet of(global::java.lang.Enum first, global::java.lang.Enum[] rest)
        {
            var s = new EnumSet(global::java.lang.RawNew.I);
            s.add(first);
            foreach (var e in rest) { s.add(e); }
            return s;
        }

        public static EnumSet copyOf(Collection c)
        {
            var s = new EnumSet(global::java.lang.RawNew.I);
            var it = c.iterator();
            while (it.hasNext() != 0) { s.add(it.next()); }
            return s;
        }
    }
}
