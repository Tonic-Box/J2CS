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

        public static void swap(List list, int i, int j)
        {
            global::java.lang.Object tmp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, tmp);
        }

        public static void shuffle(List list, Random rnd)
        {
            for (int i = list.size(); i > 1; i--)
            {
                swap(list, i - 1, rnd.nextInt(i));
            }
        }

        public static void shuffle(List list)
        {
            var r = new Random(global::java.lang.RawNew.I);
            r.__init__V();
            shuffle(list, r);
        }

        public static int binarySearch(List list, global::java.lang.Object key)
        {
            int lo = 0, hi = list.size() - 1;
            while (lo <= hi)
            {
                int mid = (int)((uint)(lo + hi) >> 1);
                int c = Natural(list.get(mid), key);
                if (c < 0) { lo = mid + 1; }
                else if (c > 0) { hi = mid - 1; }
                else { return mid; }
            }
            return -(lo + 1);
        }

        public static int binarySearch(List list, global::java.lang.Object key, Comparator cmp)
        {
            int lo = 0, hi = list.size() - 1;
            while (lo <= hi)
            {
                int mid = (int)((uint)(lo + hi) >> 1);
                int c = cmp != null ? cmp.compare(list.get(mid), key) : Natural(list.get(mid), key);
                if (c < 0) { lo = mid + 1; }
                else if (c > 0) { hi = mid - 1; }
                else { return mid; }
            }
            return -(lo + 1);
        }

        public static void rotate(List list, int distance)
        {
            int size = list.size();
            if (size == 0) { return; }
            distance %= size;
            if (distance < 0) { distance += size; }
            if (distance == 0) { return; }
            for (int cycleStart = 0, nMoved = 0; nMoved != size; cycleStart++)
            {
                global::java.lang.Object displaced = list.get(cycleStart);
                int i = cycleStart;
                do
                {
                    i += distance;
                    if (i >= size) { i -= size; }
                    global::java.lang.Object next = list.get(i);
                    list.set(i, displaced);
                    displaced = next;
                    nMoved++;
                } while (i != cycleStart);
            }
        }

        public static List nCopies(int n, global::java.lang.Object o)
        {
            var l = new ArrayList(global::java.lang.RawNew.I);
            l.__init__V();
            for (int i = 0; i < n; i++) { l.add(o); }
            return l;
        }

        public static int addAll(Collection c, global::java.lang.Object[] elements)
        {
            bool changed = false;
            foreach (var e in elements)
            {
                if (c.add(e) != 0) { changed = true; }
            }
            return changed ? 1 : 0;
        }

        public static int disjoint(Collection c1, Collection c2)
        {
            var it = c1.iterator();
            while (it.hasNext() != 0)
            {
                if (c2.contains(it.next()) != 0) { return 0; }
            }
            return 1;
        }

        public static void fill(List list, global::java.lang.Object o)
        {
            int n = list.size();
            for (int i = 0; i < n; i++) { list.set(i, o); }
        }

        public static int replaceAll(List list, global::java.lang.Object oldVal, global::java.lang.Object newVal)
        {
            bool changed = false;
            int n = list.size();
            for (int i = 0; i < n; i++)
            {
                var e = list.get(i);
                bool eq = oldVal == null ? e == null : (e != null && oldVal.equals(e) != 0);
                if (eq) { list.set(i, newVal); changed = true; }
            }
            return changed ? 1 : 0;
        }

        public static void copy(List dest, List src)
        {
            int n = src.size();
            for (int i = 0; i < n; i++) { dest.set(i, src.get(i)); }
        }

        public static Set singleton(global::java.lang.Object o)
        {
            var s = new HashSet(global::java.lang.RawNew.I);
            s.__init__V();
            s.add(o);
            return s;
        }

        public static Map singletonMap(global::java.lang.Object k, global::java.lang.Object v)
        {
            var mp = new HashMap(global::java.lang.RawNew.I);
            mp.__init__V();
            mp.put(k, v);
            return mp;
        }

        public static List synchronizedList(List list) { return list; }
        public static Set synchronizedSet(Set set) { return set; }
        public static Map synchronizedMap(Map map) { return map; }
        public static Collection synchronizedCollection(Collection c) { return c; }

        public static Comparator reverseOrder()
        {
            return new ReverseComparator(null);
        }

        public static Comparator reverseOrder(Comparator cmp)
        {
            return new ReverseComparator(cmp);
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
