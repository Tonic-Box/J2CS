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
