namespace java.util
{
    public interface List : Collection
    {
        global::java.lang.Object get(int index);

        global::java.lang.Object set(int index, global::java.lang.Object element);

        void add(int index, global::java.lang.Object element);

        global::java.lang.Object remove(int index);

        int indexOf(global::java.lang.Object o);

        void clear()
        {
            for (int i = size() - 1; i >= 0; i--)
            {
                remove(i);
            }
        }

        int removeIf(global::java.util.function.Predicate filter)
        {
            int removed = 0;
            for (int i = size() - 1; i >= 0; i--)
            {
                if (filter.test(get(i)) != 0)
                {
                    remove(i);
                    removed = 1;
                }
            }
            return removed;
        }

        void sort(global::java.util.Comparator c)
        {
            global::java.util.Collections.sort(this, c);
        }
    }
}
