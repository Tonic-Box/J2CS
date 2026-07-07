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

        private sealed class IntKey : Comparator
        {
            private readonly global::java.util.function.ToIntFunction f;
            public IntKey(global::java.util.function.ToIntFunction f) { this.f = f; }
            public int compare(global::java.lang.Object a, global::java.lang.Object b)
            {
                int x = f.applyAsInt(a);
                int y = f.applyAsInt(b);
                return x < y ? -1 : (x > y ? 1 : 0);
            }
        }

        private sealed class LongKey : Comparator
        {
            private readonly global::java.util.function.ToLongFunction f;
            public LongKey(global::java.util.function.ToLongFunction f) { this.f = f; }
            public int compare(global::java.lang.Object a, global::java.lang.Object b)
            {
                long x = f.applyAsLong(a);
                long y = f.applyAsLong(b);
                return x < y ? -1 : (x > y ? 1 : 0);
            }
        }

        private sealed class DoubleKey : Comparator
        {
            private readonly global::java.util.function.ToDoubleFunction f;
            public DoubleKey(global::java.util.function.ToDoubleFunction f) { this.f = f; }
            public int compare(global::java.lang.Object a, global::java.lang.Object b)
            {
                double x = f.applyAsDouble(a);
                double y = f.applyAsDouble(b);
                return x < y ? -1 : (x > y ? 1 : 0);
            }
        }

        private sealed class KeyExtract : Comparator
        {
            private readonly global::java.util.function.Function f;
            private readonly Comparator cmp;
            public KeyExtract(global::java.util.function.Function f, Comparator cmp) { this.f = f; this.cmp = cmp; }
            public int compare(global::java.lang.Object a, global::java.lang.Object b)
            {
                var ka = f.apply(a);
                var kb = f.apply(b);
                return cmp != null ? cmp.compare(ka, kb) : Natural(ka, kb);
            }
        }

        private sealed class Reversed : Comparator
        {
            private readonly Comparator inner;
            public Reversed(Comparator inner) { this.inner = inner; }
            public int compare(global::java.lang.Object a, global::java.lang.Object b) { return inner.compare(b, a); }
        }

        private sealed class Chained : Comparator
        {
            private readonly Comparator first;
            private readonly Comparator second;
            public Chained(Comparator first, Comparator second) { this.first = first; this.second = second; }
            public int compare(global::java.lang.Object a, global::java.lang.Object b)
            {
                int r = first.compare(a, b);
                return r != 0 ? r : second.compare(a, b);
            }
        }

        public static Comparator comparingInt(global::java.util.function.ToIntFunction keyExtractor)
        {
            return new IntKey(keyExtractor);
        }

        public static Comparator comparingLong(global::java.util.function.ToLongFunction keyExtractor)
        {
            return new LongKey(keyExtractor);
        }

        public static Comparator comparingDouble(global::java.util.function.ToDoubleFunction keyExtractor)
        {
            return new DoubleKey(keyExtractor);
        }

        public static Comparator comparing(global::java.util.function.Function keyExtractor)
        {
            return new KeyExtract(keyExtractor, null);
        }

        public static Comparator comparing(global::java.util.function.Function keyExtractor, Comparator keyComparator)
        {
            return new KeyExtract(keyExtractor, keyComparator);
        }

        Comparator reversed()
        {
            return new Reversed(this);
        }

        Comparator thenComparing(Comparator other)
        {
            return new Chained(this, other);
        }

        Comparator thenComparing(global::java.util.function.Function keyExtractor)
        {
            return new Chained(this, new KeyExtract(keyExtractor, null));
        }

        Comparator thenComparingInt(global::java.util.function.ToIntFunction keyExtractor)
        {
            return new Chained(this, new IntKey(keyExtractor));
        }
    }
}
