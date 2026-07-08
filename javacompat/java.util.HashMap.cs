namespace java.util
{
    public class HashMap : global::java.lang.Object, Map
    {
        private sealed class Node
        {
            internal int hash;
            internal global::java.lang.Object key;
            internal global::java.lang.Object value;
            internal Node next;

            internal Node(int hash, global::java.lang.Object key, global::java.lang.Object value, Node next)
            {
                this.hash = hash;
                this.key = key;
                this.value = value;
                this.next = next;
            }
        }

        private Node[] table;
        private int count;
        private int threshold;

        // Insertion order of keys. Base HashMap iterates the table in bucket order (matching the JVM)
        // and ignores this; ordered subclasses (LinkedHashMap insertion-order, TreeMap sorted-order)
        // override ForEachEntry to use it. Tracking keys (not Nodes) stays valid across Resize.
        protected readonly global::System.Collections.Generic.List<global::java.lang.Object> keyOrder =
                new global::System.Collections.Generic.List<global::java.lang.Object>();

        public HashMap(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_I_V(int initialCapacity)
        {
            threshold = TableSizeFor(initialCapacity);
        }

        private static int Spread(global::java.lang.Object key)
        {
            if (key == null)
            {
                return 0;
            }
            int h = key.hashCode();
            return h ^ (int)((uint)h >> 16);
        }

        private static bool SameKey(global::java.lang.Object a, global::java.lang.Object b)
        {
            if (ReferenceEquals(a, b))
            {
                return true;
            }
            return a != null && b != null && a.equals(b) != 0;
        }

        private static int TableSizeFor(int cap)
        {
            int n = cap - 1;
            n |= (int)((uint)n >> 1);
            n |= (int)((uint)n >> 2);
            n |= (int)((uint)n >> 4);
            n |= (int)((uint)n >> 8);
            n |= (int)((uint)n >> 16);
            return n < 0 ? 1 : n + 1;
        }

        private void Resize()
        {
            Node[] oldTab = table;
            int oldCap = oldTab == null ? 0 : oldTab.Length;
            int oldThr = threshold;
            int newCap;
            int newThr = 0;
            if (oldCap > 0)
            {
                newCap = oldCap << 1;
                newThr = oldThr << 1;
            }
            else if (oldThr > 0)
            {
                newCap = oldThr;
            }
            else
            {
                newCap = 16;
                newThr = 12;
            }
            if (newThr == 0)
            {
                newThr = (int)(newCap * 0.75);
            }
            threshold = newThr;
            Node[] newTab = new Node[newCap];
            table = newTab;
            if (oldTab == null)
            {
                return;
            }
            for (int j = 0; j < oldCap; j++)
            {
                Node e = oldTab[j];
                if (e == null)
                {
                    continue;
                }
                oldTab[j] = null;
                if (e.next == null)
                {
                    newTab[e.hash & (newCap - 1)] = e;
                    continue;
                }
                Node loHead = null;
                Node loTail = null;
                Node hiHead = null;
                Node hiTail = null;
                while (e != null)
                {
                    Node next = e.next;
                    if ((e.hash & oldCap) == 0)
                    {
                        if (loTail == null)
                        {
                            loHead = e;
                        }
                        else
                        {
                            loTail.next = e;
                        }
                        loTail = e;
                    }
                    else
                    {
                        if (hiTail == null)
                        {
                            hiHead = e;
                        }
                        else
                        {
                            hiTail.next = e;
                        }
                        hiTail = e;
                    }
                    e = next;
                }
                if (loTail != null)
                {
                    loTail.next = null;
                    newTab[j] = loHead;
                }
                if (hiTail != null)
                {
                    hiTail.next = null;
                    newTab[j + oldCap] = hiHead;
                }
            }
        }

        public global::java.lang.Object put(global::java.lang.Object key, global::java.lang.Object value)
        {
            if (table == null || table.Length == 0)
            {
                Resize();
            }
            int hash = Spread(key);
            int i = (table.Length - 1) & hash;
            Node p = table[i];
            if (p == null)
            {
                table[i] = new Node(hash, key, value, null);
                keyOrder.Add(key);
            }
            else
            {
                while (true)
                {
                    if (p.hash == hash && SameKey(p.key, key))
                    {
                        global::java.lang.Object old = p.value;
                        p.value = value;
                        return old;
                    }
                    if (p.next == null)
                    {
                        p.next = new Node(hash, key, value, null);
                        keyOrder.Add(key);
                        break;
                    }
                    p = p.next;
                }
            }
            if (++count > threshold)
            {
                Resize();
            }
            return null;
        }

        public global::java.lang.Object get(global::java.lang.Object key)
        {
            Node e = FindNode(key);
            return e == null ? null : e.value;
        }

        public int containsKey(global::java.lang.Object key)
        {
            return FindNode(key) != null ? 1 : 0;
        }

        private Node FindNode(global::java.lang.Object key)
        {
            if (table == null)
            {
                return null;
            }
            int hash = Spread(key);
            Node e = table[(table.Length - 1) & hash];
            while (e != null)
            {
                if (e.hash == hash && SameKey(e.key, key))
                {
                    return e;
                }
                e = e.next;
            }
            return null;
        }

        public global::java.lang.Object remove(global::java.lang.Object key)
        {
            if (table == null)
            {
                return null;
            }
            int hash = Spread(key);
            int i = (table.Length - 1) & hash;
            Node prev = null;
            Node e = table[i];
            while (e != null)
            {
                if (e.hash == hash && SameKey(e.key, key))
                {
                    if (prev == null)
                    {
                        table[i] = e.next;
                    }
                    else
                    {
                        prev.next = e.next;
                    }
                    count--;
                    keyOrder.Remove(e.key);
                    return e.value;
                }
                prev = e;
                e = e.next;
            }
            return null;
        }

        public int size()
        {
            return count;
        }

        public int isEmpty()
        {
            return count == 0 ? 1 : 0;
        }

        public void clear()
        {
            table = null;
            count = 0;
            keyOrder.Clear();
        }

        public Set keySet()
        {
            var keys = new global::System.Collections.Generic.List<global::java.lang.Object>();
            ForEachEntry((k, v) => keys.Add(k));
            return new ShimListView(keys);
        }

        public Collection values()
        {
            var vals = new global::System.Collections.Generic.List<global::java.lang.Object>();
            ForEachEntry((k, v) => vals.Add(v));
            return new ShimListView(vals);
        }

        public Set entrySet()
        {
            var entries = new global::System.Collections.Generic.List<global::java.lang.Object>();
            ForEachEntry((k, v) => entries.Add(new ShimMapEntry(k, v)));
            return new ShimListView(entries);
        }

        // Iteration order: LinkedHashMap by insertion, TreeMap by natural key order, plain HashMap by
        // table (bucket) order to match the JVM. Dispatching on the runtime type here keeps
        // LinkedHashMap/TreeMap as empty subclasses, so they also compile against a bootstrapped
        // HashMap (which lacks these shim-only members) instead of overriding a missing method.
        private void ForEachEntry(
                global::System.Action<global::java.lang.Object, global::java.lang.Object> action)
        {
            if (this is global::java.util.TreeMap)
            {
                var sorted = new global::System.Collections.Generic.List<global::java.lang.Object>(keyOrder);
                sorted.Sort((a, b) => global::java.util.JCollections.NaturalCompare(a, b));
                foreach (global::java.lang.Object k in sorted)
                {
                    action(k, get(k));
                }
                return;
            }
            if (this is global::java.util.LinkedHashMap)
            {
                foreach (global::java.lang.Object k in keyOrder)
                {
                    action(k, get(k));
                }
                return;
            }
            if (table == null)
            {
                return;
            }
            for (int j = 0; j < table.Length; j++)
            {
                Node e = table[j];
                while (e != null)
                {
                    action(e.key, e.value);
                    e = e.next;
                }
            }
        }

        public override global::java.lang.String toString()
        {
            var sb = new global::System.Text.StringBuilder();
            sb.Append('{');
            bool first = true;
            ForEachEntry((k, v) =>
            {
                if (!first)
                {
                    sb.Append(", ");
                }
                first = false;
                sb.Append(global::java.lang.JRuntime.Str(k)).Append('=')
                        .Append(global::java.lang.JRuntime.Str(v));
            });
            sb.Append('}');
            return global::java.lang.String.Wrap(sb.ToString());
        }
    }

    internal sealed class ShimMapEntry : global::java.lang.Object, Map_S_Entry
    {
        private readonly global::java.lang.Object key;
        private readonly global::java.lang.Object value;

        internal ShimMapEntry(global::java.lang.Object key, global::java.lang.Object value)
                : base(global::java.lang.RawNew.I)
        {
            this.key = key;
            this.value = value;
        }

        public global::java.lang.Object getKey()
        {
            return key;
        }

        public global::java.lang.Object getValue()
        {
            return value;
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(
                    global::java.lang.JRuntime.Str(key) + "=" + global::java.lang.JRuntime.Str(value));
        }
    }

    internal sealed class ShimListView : global::java.lang.Object, Set
    {
        private readonly global::System.Collections.Generic.List<global::java.lang.Object> items;

        internal ShimListView(global::System.Collections.Generic.List<global::java.lang.Object> items)
                : base(global::java.lang.RawNew.I)
        {
            this.items = items;
        }

        public int add(global::java.lang.Object e)
        {
            items.Add(e);
            return 1;
        }

        public int size()
        {
            return items.Count;
        }

        public int isEmpty()
        {
            return items.Count == 0 ? 1 : 0;
        }

        public int contains(global::java.lang.Object o)
        {
            foreach (global::java.lang.Object item in items)
            {
                if (JCollections.Eq(o, item))
                {
                    return 1;
                }
            }
            return 0;
        }

        public int remove(global::java.lang.Object o)
        {
            for (int i = 0; i < items.Count; i++)
            {
                if (JCollections.Eq(o, items[i]))
                {
                    items.RemoveAt(i);
                    return 1;
                }
            }
            return 0;
        }

        public Iterator iterator()
        {
            return new ShimListIterator(items);
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(JCollections.Render(items));
        }
    }
}
