namespace java.util
{
    public interface Collection : global::java.lang.Iterable
    {
        int add(global::java.lang.Object e);

        int size();

        int isEmpty();

        int contains(global::java.lang.Object o);

        int remove(global::java.lang.Object o);

        global::java.util.stream.Stream stream()
        {
            var list = new global::System.Collections.Generic.List<global::java.lang.Object>();
            var it = iterator();
            while (it.hasNext() != 0)
            {
                list.Add(it.next());
            }
            return global::java.util.stream.Stream.Wrap(list);
        }

        global::java.util.stream.Stream parallelStream()
        {
            return stream();
        }

        void forEach(global::java.util.function.Consumer action)
        {
            var it = iterator();
            while (it.hasNext() != 0)
            {
                action.accept(it.next());
            }
        }

        int addAll(global::java.util.Collection c)
        {
            int modified = 0;
            var it = c.iterator();
            while (it.hasNext() != 0)
            {
                if (add(it.next()) != 0)
                {
                    modified = 1;
                }
            }
            return modified;
        }

        int containsAll(global::java.util.Collection c)
        {
            var it = c.iterator();
            while (it.hasNext() != 0)
            {
                if (contains(it.next()) == 0)
                {
                    return 0;
                }
            }
            return 1;
        }

        int removeAll(global::java.util.Collection c)
        {
            return removeMatching(c, true);
        }

        int retainAll(global::java.util.Collection c)
        {
            return removeMatching(c, false);
        }

        // Snapshot the elements to drop before mutating, so the removal does not disturb an in-progress
        // iteration. removeAll drops elements found in c; retainAll drops those absent from c.
        private int removeMatching(global::java.util.Collection c, bool removeWhenPresent)
        {
            var toRemove = new global::System.Collections.Generic.List<global::java.lang.Object>();
            var it = iterator();
            while (it.hasNext() != 0)
            {
                var e = it.next();
                if ((c.contains(e) != 0) == removeWhenPresent)
                {
                    toRemove.Add(e);
                }
            }
            int modified = 0;
            foreach (var e in toRemove)
            {
                if (remove(e) != 0)
                {
                    modified = 1;
                }
            }
            return modified;
        }

        global::java.lang.Object[] toArray()
        {
            int n = size();
            var r = new global::java.lang.Object[n];
            var it = iterator();
            int i = 0;
            while (it.hasNext() != 0 && i < n)
            {
                r[i++] = it.next();
            }
            return r;
        }

        global::java.lang.Object[] toArray(global::java.lang.Object[] a)
        {
            int n = size();
            global::java.lang.Object[] r = a.Length >= n
                ? a
                : (global::java.lang.Object[])global::System.Array.CreateInstance(a.GetType().GetElementType(), n);
            var it = iterator();
            int i = 0;
            while (it.hasNext() != 0 && i < n)
            {
                r[i++] = it.next();
            }
            if (r.Length > n)
            {
                r[n] = null;
            }
            return r;
        }
    }
}
