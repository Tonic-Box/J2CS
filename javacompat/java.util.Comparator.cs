namespace java.util
{
    public interface Comparator
    {
        int compare(global::java.lang.Object a, global::java.lang.Object b);

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

        private sealed class NaturalOrder : Comparator
        {
            public int compare(global::java.lang.Object a, global::java.lang.Object b) { return Natural(a, b); }
        }

        private sealed class ReverseOrder : Comparator
        {
            public int compare(global::java.lang.Object a, global::java.lang.Object b) { return Natural(b, a); }
        }

        public static Comparator naturalOrder()
        {
            return new NaturalOrder();
        }

        public static Comparator reverseOrder()
        {
            return new ReverseOrder();
        }
    }
}
