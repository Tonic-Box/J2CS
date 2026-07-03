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
