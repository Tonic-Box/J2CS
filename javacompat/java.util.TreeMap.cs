namespace java.util
{
    public class TreeMap : HashMap, SortedMap
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
    }
}
