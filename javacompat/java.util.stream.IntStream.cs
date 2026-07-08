namespace java.util.stream
{
    public sealed class IntStream : global::java.lang.Object
    {
        private readonly global::System.Collections.Generic.List<int> items;

        public IntStream(global::java.lang.RawNew r) : base(r)
        {
            this.items = new global::System.Collections.Generic.List<int>();
        }

        internal IntStream(global::System.Collections.Generic.List<int> backing) : base(global::java.lang.RawNew.I)
        {
            this.items = backing;
        }

        public static IntStream range(int startInclusive, int endExclusive)
        {
            var list = new global::System.Collections.Generic.List<int>();
            for (int i = startInclusive; i < endExclusive; i++) { list.Add(i); }
            return new IntStream(list);
        }

        public static IntStream rangeClosed(int startInclusive, int endInclusive)
        {
            var list = new global::System.Collections.Generic.List<int>();
            for (int i = startInclusive; i <= endInclusive; i++) { list.Add(i); }
            return new IntStream(list);
        }

        public static IntStream of(int[] values)
        {
            return new IntStream(new global::System.Collections.Generic.List<int>(values));
        }

        public IntStream parallel()
        {
            return this;
        }

        public IntStream sequential()
        {
            return this;
        }

        public Stream boxed()
        {
            var outp = new global::System.Collections.Generic.List<global::java.lang.Object>(items.Count);
            foreach (var v in items) { outp.Add(global::java.lang.Integer.valueOf(v)); }
            return Stream.Wrap(outp);
        }

        public Stream mapToObj(global::java.util.function.IntFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<global::java.lang.Object>(items.Count);
            foreach (var v in items) { outp.Add(mapper.apply(v)); }
            return Stream.Wrap(outp);
        }

        public DoubleStream mapToDouble(global::java.util.function.IntToDoubleFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<double>(items.Count);
            foreach (var v in items) { outp.Add(mapper.applyAsDouble(v)); }
            return new DoubleStream(outp);
        }

        public void forEach(global::java.util.function.IntConsumer action)
        {
            foreach (var v in items) { action.accept(v); }
        }

        public IntStream filter(global::java.util.function.IntPredicate predicate)
        {
            var outp = new global::System.Collections.Generic.List<int>();
            foreach (var v in items) { if (predicate.test(v) != 0) { outp.Add(v); } }
            return new IntStream(outp);
        }

        public IntStream map(global::java.util.function.IntUnaryOperator mapper)
        {
            var outp = new global::System.Collections.Generic.List<int>(items.Count);
            foreach (var v in items) { outp.Add(mapper.applyAsInt(v)); }
            return new IntStream(outp);
        }

        public int reduce(int identity, global::java.util.function.IntBinaryOperator op)
        {
            int acc = identity;
            foreach (var v in items) { acc = op.applyAsInt(acc, v); }
            return acc;
        }

        public IntStream sorted()
        {
            var outp = new global::System.Collections.Generic.List<int>(items);
            outp.Sort();
            return new IntStream(outp);
        }

        public IntStream distinct()
        {
            var seen = new global::System.Collections.Generic.HashSet<int>();
            var outp = new global::System.Collections.Generic.List<int>();
            foreach (var v in items) { if (seen.Add(v)) { outp.Add(v); } }
            return new IntStream(outp);
        }

        public IntStream limit(long maxSize)
        {
            var outp = new global::System.Collections.Generic.List<int>();
            for (int i = 0; i < items.Count && i < maxSize; i++) { outp.Add(items[i]); }
            return new IntStream(outp);
        }

        public IntStream skip(long n)
        {
            var outp = new global::System.Collections.Generic.List<int>();
            for (int i = (int)n; i >= 0 && i < items.Count; i++) { outp.Add(items[i]); }
            return new IntStream(outp);
        }

        public int anyMatch(global::java.util.function.IntPredicate predicate)
        {
            foreach (var v in items) { if (predicate.test(v) != 0) { return 1; } }
            return 0;
        }

        public int allMatch(global::java.util.function.IntPredicate predicate)
        {
            foreach (var v in items) { if (predicate.test(v) == 0) { return 0; } }
            return 1;
        }

        public int noneMatch(global::java.util.function.IntPredicate predicate)
        {
            foreach (var v in items) { if (predicate.test(v) != 0) { return 0; } }
            return 1;
        }

        public global::java.util.OptionalInt min()
        {
            if (items.Count == 0) { return global::java.util.OptionalInt.Empty(); }
            int m = items[0];
            foreach (var v in items) { if (v < m) { m = v; } }
            return global::java.util.OptionalInt.Of(m);
        }

        public global::java.util.OptionalInt max()
        {
            if (items.Count == 0) { return global::java.util.OptionalInt.Empty(); }
            int m = items[0];
            foreach (var v in items) { if (v > m) { m = v; } }
            return global::java.util.OptionalInt.Of(m);
        }

        public global::java.util.OptionalDouble average()
        {
            if (items.Count == 0) { return global::java.util.OptionalDouble.Empty(); }
            long s = 0;
            foreach (var v in items) { s += v; }
            return global::java.util.OptionalDouble.Of((double)s / items.Count);
        }

        public int sum()
        {
            int s = 0;
            foreach (var v in items) { s += v; }
            return s;
        }

        public long count()
        {
            return items.Count;
        }

        public int[] toArray()
        {
            return items.ToArray();
        }

        public IntStream peek(global::java.util.function.IntConsumer action)
        {
            foreach (var v in items) { action.accept(v); }
            return this;
        }

        public LongStream mapToLong(global::java.util.function.IntToLongFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<long>(items.Count);
            foreach (var v in items) { outp.Add(mapper.applyAsLong(v)); }
            return new LongStream(outp);
        }

        public LongStream asLongStream()
        {
            var outp = new global::System.Collections.Generic.List<long>(items.Count);
            foreach (var v in items) { outp.Add(v); }
            return new LongStream(outp);
        }

        public DoubleStream asDoubleStream()
        {
            var outp = new global::System.Collections.Generic.List<double>(items.Count);
            foreach (var v in items) { outp.Add(v); }
            return new DoubleStream(outp);
        }

        public global::java.util.OptionalInt reduce(global::java.util.function.IntBinaryOperator op)
        {
            if (items.Count == 0) { return global::java.util.OptionalInt.Empty(); }
            int acc = items[0];
            for (int i = 1; i < items.Count; i++) { acc = op.applyAsInt(acc, items[i]); }
            return global::java.util.OptionalInt.Of(acc);
        }

        public global::java.util.OptionalInt findFirst()
        {
            return items.Count == 0 ? global::java.util.OptionalInt.Empty() : global::java.util.OptionalInt.Of(items[0]);
        }

        public global::java.util.IntSummaryStatistics summaryStatistics()
        {
            var stats = new global::java.util.IntSummaryStatistics(global::java.lang.RawNew.I);
            foreach (var v in items) { stats.Accept(v); }
            return stats;
        }
    }
}
