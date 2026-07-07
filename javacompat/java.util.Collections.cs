namespace java.util
{
    public sealed class Collections : global::java.lang.Object
    {
        public Collections(global::java.lang.RawNew r) : base(r)
        {
        }

        public static void sort(List list)
        {
            SortWith(list, null);
        }

        public static void sort(List list, Comparator comparator)
        {
            SortWith(list, comparator);
        }

        public static void reverse(List list)
        {
            int n = list.size();
            for (int i = 0; i < n / 2; i++)
            {
                global::java.lang.Object tmp = list.get(i);
                list.set(i, list.get(n - 1 - i));
                list.set(n - 1 - i, tmp);
            }
        }

        public static List emptyList()
        {
            var l = new ArrayList(global::java.lang.RawNew.I);
            l.__init__V();
            return l;
        }

        public static Map emptyMap()
        {
            var m = new HashMap(global::java.lang.RawNew.I);
            m.__init__V();
            return m;
        }

        public static Set emptySet()
        {
            var s = new HashSet(global::java.lang.RawNew.I);
            s.__init__V();
            return s;
        }

        public static List singletonList(global::java.lang.Object o)
        {
            var l = new ArrayList(global::java.lang.RawNew.I);
            l.__init__V();
            l.add(o);
            return l;
        }

        public static List unmodifiableList(List list)
        {
            return list;
        }

        public static Map unmodifiableMap(Map map)
        {
            return map;
        }

        public static Set unmodifiableSet(Set set)
        {
            return set;
        }

        public static Collection unmodifiableCollection(Collection c)
        {
            return c;
        }

        public static global::java.lang.Object max(Collection coll)
        {
            return Extreme(coll, null, true);
        }

        public static global::java.lang.Object max(Collection coll, Comparator cmp)
        {
            return Extreme(coll, cmp, true);
        }

        public static global::java.lang.Object min(Collection coll)
        {
            return Extreme(coll, null, false);
        }

        public static global::java.lang.Object min(Collection coll, Comparator cmp)
        {
            return Extreme(coll, cmp, false);
        }

        public static int frequency(Collection coll, global::java.lang.Object o)
        {
            int n = 0;
            var it = coll.iterator();
            while (it.hasNext() != 0)
            {
                var e = it.next();
                bool eq = o == null ? e == null : (e != null && o.equals(e) != 0);
                if (eq) { n++; }
            }
            return n;
        }

        private static global::java.lang.Object Extreme(Collection coll, Comparator cmp, bool max)
        {
            var it = coll.iterator();
            if (it.hasNext() == 0)
            {
                throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I));
            }
            var best = it.next();
            while (it.hasNext() != 0)
            {
                var e = it.next();
                int c = cmp != null ? cmp.compare(e, best) : Natural(e, best);
                if (max ? c > 0 : c < 0) { best = e; }
            }
            return best;
        }

        private static void SortWith(List list, Comparator comparator)
        {
            int n = list.size();
            var arr = new global::java.lang.Object[n];
            for (int i = 0; i < n; i++)
            {
                arr[i] = list.get(i);
            }
            global::System.Array.Sort(arr, (a, b) =>
                comparator != null ? comparator.compare(a, b) : Natural(a, b));
            for (int i = 0; i < n; i++)
            {
                list.set(i, arr[i]);
            }
        }

        private static int Natural(global::java.lang.Object a, global::java.lang.Object b)
        {
            if (a is global::java.lang.Number na && b is global::java.lang.Number nb)
            {
                double x = na.doubleValue();
                double y = nb.doubleValue();
                return x < y ? -1 : (x > y ? 1 : 0);
            }
            return global::System.String.CompareOrdinal(
                global::java.lang.JRuntime.Str(a), global::java.lang.JRuntime.Str(b));
        }
    }
}
