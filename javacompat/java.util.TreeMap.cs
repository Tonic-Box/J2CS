namespace java.util
{
    public class TreeMap : HashMap, NavigableMap
    {
        public TreeMap(global::java.lang.RawNew r) : base(r)
        {
        }

        private global::System.Collections.Generic.List<global::java.lang.Object> SortedKeys()
        {
            var s = new global::System.Collections.Generic.List<global::java.lang.Object>();
            var it = keySet().iterator();
            while (it.hasNext() != 0) { s.Add(it.next()); }
            s.Sort((a, b) => JCollections.NaturalCompare(a, b));
            return s;
        }

        public global::java.lang.Object firstKey()
        {
            var s = SortedKeys();
            if (s.Count == 0) { throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I)); }
            return s[0];
        }

        public global::java.lang.Object lastKey()
        {
            var s = SortedKeys();
            if (s.Count == 0) { throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I)); }
            return s[s.Count - 1];
        }

        public global::java.lang.Object floorKey(global::java.lang.Object key)
        {
            global::java.lang.Object best = null;
            foreach (var k in SortedKeys()) { if (JCollections.NaturalCompare(k, key) <= 0) { best = k; } else { break; } }
            return best;
        }

        public global::java.lang.Object ceilingKey(global::java.lang.Object key)
        {
            foreach (var k in SortedKeys()) { if (JCollections.NaturalCompare(k, key) >= 0) { return k; } }
            return null;
        }

        public global::java.lang.Object lowerKey(global::java.lang.Object key)
        {
            global::java.lang.Object best = null;
            foreach (var k in SortedKeys()) { if (JCollections.NaturalCompare(k, key) < 0) { best = k; } else { break; } }
            return best;
        }

        public global::java.lang.Object higherKey(global::java.lang.Object key)
        {
            foreach (var k in SortedKeys()) { if (JCollections.NaturalCompare(k, key) > 0) { return k; } }
            return null;
        }

        public SortedMap headMap(global::java.lang.Object toKey)
        {
            var r = new TreeMap(global::java.lang.RawNew.I);
            r.__init__V();
            foreach (var k in SortedKeys()) { if (JCollections.NaturalCompare(k, toKey) < 0) { r.put(k, get(k)); } }
            return r;
        }

        public SortedMap tailMap(global::java.lang.Object fromKey)
        {
            var r = new TreeMap(global::java.lang.RawNew.I);
            r.__init__V();
            foreach (var k in SortedKeys()) { if (JCollections.NaturalCompare(k, fromKey) >= 0) { r.put(k, get(k)); } }
            return r;
        }

        public SortedMap subMap(global::java.lang.Object fromKey, global::java.lang.Object toKey)
        {
            var r = new TreeMap(global::java.lang.RawNew.I);
            r.__init__V();
            foreach (var k in SortedKeys())
            {
                if (JCollections.NaturalCompare(k, fromKey) >= 0 && JCollections.NaturalCompare(k, toKey) < 0) { r.put(k, get(k)); }
            }
            return r;
        }

        private Map_S_Entry Entry(global::java.lang.Object k)
        {
            return k == null ? null : new ShimMapEntry(k, get(k));
        }

        public Map_S_Entry firstEntry()
        {
            var s = SortedKeys();
            return s.Count == 0 ? null : Entry(s[0]);
        }

        public Map_S_Entry lastEntry()
        {
            var s = SortedKeys();
            return s.Count == 0 ? null : Entry(s[s.Count - 1]);
        }

        public Map_S_Entry floorEntry(global::java.lang.Object key) { return Entry(floorKey(key)); }
        public Map_S_Entry ceilingEntry(global::java.lang.Object key) { return Entry(ceilingKey(key)); }
        public Map_S_Entry higherEntry(global::java.lang.Object key) { return Entry(higherKey(key)); }
        public Map_S_Entry lowerEntry(global::java.lang.Object key) { return Entry(lowerKey(key)); }

        public NavigableSet navigableKeySet()
        {
            var r = new TreeSet(global::java.lang.RawNew.I);
            r.__init__V();
            foreach (var k in SortedKeys()) { r.add(k); }
            return r;
        }

        public NavigableSet descendingKeySet()
        {
            var r = new TreeSet(global::java.lang.RawNew.I);
            r.__init_Ljava_util_Comparator__V(new ReverseComparator(null));
            foreach (var k in SortedKeys()) { r.add(k); }
            return r;
        }

        public NavigableMap headMap(global::java.lang.Object toKey, int inclusive)
        {
            var r = new TreeMap(global::java.lang.RawNew.I);
            r.__init__V();
            foreach (var k in SortedKeys())
            {
                int c = JCollections.NaturalCompare(k, toKey);
                if (c < 0 || (inclusive != 0 && c == 0)) { r.put(k, get(k)); }
            }
            return r;
        }

        public NavigableMap tailMap(global::java.lang.Object fromKey, int inclusive)
        {
            var r = new TreeMap(global::java.lang.RawNew.I);
            r.__init__V();
            foreach (var k in SortedKeys())
            {
                int c = JCollections.NaturalCompare(k, fromKey);
                if (c > 0 || (inclusive != 0 && c == 0)) { r.put(k, get(k)); }
            }
            return r;
        }

        public NavigableMap subMap(global::java.lang.Object fromKey, int fromInclusive,
                global::java.lang.Object toKey, int toInclusive)
        {
            var r = new TreeMap(global::java.lang.RawNew.I);
            r.__init__V();
            foreach (var k in SortedKeys())
            {
                int cf = JCollections.NaturalCompare(k, fromKey);
                int ct = JCollections.NaturalCompare(k, toKey);
                bool lo = cf > 0 || (fromInclusive != 0 && cf == 0);
                bool hi = ct < 0 || (toInclusive != 0 && ct == 0);
                if (lo && hi) { r.put(k, get(k)); }
            }
            return r;
        }
    }
}
