namespace java.util
{
    public class ArrayList : global::java.lang.Object, List
    {
        private readonly global::System.Collections.Generic.List<global::java.lang.Object> items =
                new global::System.Collections.Generic.List<global::java.lang.Object>();

        public ArrayList(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_I_V(int capacity)
        {
        }

        public void __init_Ljava_util_Collection__V(Collection c)
        {
            Iterator it = c.iterator();
            while (it.hasNext() != 0)
            {
                items.Add(it.next());
            }
        }

        public int add(global::java.lang.Object e)
        {
            items.Add(e);
            return 1;
        }

        public void clear()
        {
            items.Clear();
        }

        public int removeIf(global::java.util.function.Predicate filter)
        {
            int before = items.Count;
            items.RemoveAll(e => filter.test(e) != 0);
            return items.Count != before ? 1 : 0;
        }

        public void add(int index, global::java.lang.Object element)
        {
            if (index < 0 || index > items.Count)
            {
                throw global::java.lang.JRuntime.Simple(
                        new global::java.lang.IndexOutOfBoundsException(global::java.lang.RawNew.I));
            }
            items.Insert(index, element);
        }

        public global::java.lang.Object get(int index)
        {
            RequireInRange(index);
            return items[index];
        }

        public global::java.lang.Object set(int index, global::java.lang.Object element)
        {
            RequireInRange(index);
            global::java.lang.Object old = items[index];
            items[index] = element;
            return old;
        }

        public global::java.lang.Object remove(int index)
        {
            RequireInRange(index);
            global::java.lang.Object old = items[index];
            items.RemoveAt(index);
            return old;
        }

        public int remove(global::java.lang.Object o)
        {
            int idx = indexOf(o);
            if (idx < 0)
            {
                return 0;
            }
            items.RemoveAt(idx);
            return 1;
        }

        public int indexOf(global::java.lang.Object o)
        {
            for (int i = 0; i < items.Count; i++)
            {
                if (JCollections.Eq(o, items[i]))
                {
                    return i;
                }
            }
            return -1;
        }

        public int contains(global::java.lang.Object o)
        {
            return indexOf(o) >= 0 ? 1 : 0;
        }

        public int size()
        {
            return items.Count;
        }

        public int isEmpty()
        {
            return items.Count == 0 ? 1 : 0;
        }

        public Iterator iterator()
        {
            return new ShimListIterator(items);
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(JCollections.Render(items));
        }

        private void RequireInRange(int index)
        {
            if (index < 0 || index >= items.Count)
            {
                throw global::java.lang.JRuntime.Simple(
                        new global::java.lang.IndexOutOfBoundsException(global::java.lang.RawNew.I));
            }
        }
    }
}
