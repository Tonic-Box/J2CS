namespace java.util
{
    public class TreeMap : HashMap
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
    }
}
