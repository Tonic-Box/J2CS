namespace java.util.stream
{
    public sealed class LongStream : global::java.lang.Object
    {
        private readonly global::System.Collections.Generic.List<long> items;

        public LongStream(global::java.lang.RawNew r) : base(r)
        {
            this.items = new global::System.Collections.Generic.List<long>();
        }

        internal LongStream(global::System.Collections.Generic.List<long> backing) : base(global::java.lang.RawNew.I)
        {
            this.items = backing;
        }

        public static LongStream range(long startInclusive, long endExclusive)
        {
            var list = new global::System.Collections.Generic.List<long>();
            for (long i = startInclusive; i < endExclusive; i++) { list.Add(i); }
            return new LongStream(list);
        }

        public static LongStream rangeClosed(long startInclusive, long endInclusive)
        {
            var list = new global::System.Collections.Generic.List<long>();
            for (long i = startInclusive; i <= endInclusive; i++) { list.Add(i); }
            return new LongStream(list);
        }

        public static LongStream of(long[] values)
        {
            return new LongStream(new global::System.Collections.Generic.List<long>(values));
        }

        public LongStream parallel() { return this; }
        public LongStream sequential() { return this; }

        public Stream boxed()
        {
            var outp = new global::System.Collections.Generic.List<global::java.lang.Object>(items.Count);
            foreach (var v in items) { outp.Add(global::java.lang.Long.valueOf(v)); }
            return Stream.Wrap(outp);
        }

        public Stream mapToObj(global::java.util.function.LongFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<global::java.lang.Object>(items.Count);
            foreach (var v in items) { outp.Add(mapper.apply(v)); }
            return Stream.Wrap(outp);
        }

        public IntStream mapToInt(global::java.util.function.LongToIntFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<int>(items.Count);
            foreach (var v in items) { outp.Add(mapper.applyAsInt(v)); }
            return new IntStream(outp);
        }

        public DoubleStream mapToDouble(global::java.util.function.LongToDoubleFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<double>(items.Count);
            foreach (var v in items) { outp.Add(mapper.applyAsDouble(v)); }
            return new DoubleStream(outp);
        }

        public DoubleStream asDoubleStream()
        {
            var outp = new global::System.Collections.Generic.List<double>(items.Count);
            foreach (var v in items) { outp.Add((double)v); }
            return new DoubleStream(outp);
        }

        public void forEach(global::java.util.function.LongConsumer action)
        {
            foreach (var v in items) { action.accept(v); }
        }

        public LongStream peek(global::java.util.function.LongConsumer action)
        {
            foreach (var v in items) { action.accept(v); }
            return this;
        }

        public LongStream filter(global::java.util.function.LongPredicate predicate)
        {
            var outp = new global::System.Collections.Generic.List<long>();
            foreach (var v in items) { if (predicate.test(v) != 0) { outp.Add(v); } }
            return new LongStream(outp);
        }

        public LongStream map(global::java.util.function.LongUnaryOperator mapper)
        {
            var outp = new global::System.Collections.Generic.List<long>(items.Count);
            foreach (var v in items) { outp.Add(mapper.applyAsLong(v)); }
            return new LongStream(outp);
        }

        public long reduce(long identity, global::java.util.function.LongBinaryOperator op)
        {
            long acc = identity;
            foreach (var v in items) { acc = op.applyAsLong(acc, v); }
            return acc;
        }

        public global::java.util.OptionalLong reduce(global::java.util.function.LongBinaryOperator op)
        {
            if (items.Count == 0) { return global::java.util.OptionalLong.Empty(); }
            long acc = items[0];
            for (int i = 1; i < items.Count; i++) { acc = op.applyAsLong(acc, items[i]); }
            return global::java.util.OptionalLong.Of(acc);
        }

        public LongStream sorted()
        {
            var outp = new global::System.Collections.Generic.List<long>(items);
            outp.Sort();
            return new LongStream(outp);
        }

        public LongStream distinct()
        {
            var seen = new global::System.Collections.Generic.HashSet<long>();
            var outp = new global::System.Collections.Generic.List<long>();
            foreach (var v in items) { if (seen.Add(v)) { outp.Add(v); } }
            return new LongStream(outp);
        }

        public LongStream limit(long maxSize)
        {
            var outp = new global::System.Collections.Generic.List<long>();
            for (int i = 0; i < items.Count && i < maxSize; i++) { outp.Add(items[i]); }
            return new LongStream(outp);
        }

        public LongStream skip(long n)
        {
            var outp = new global::System.Collections.Generic.List<long>();
            for (int i = n < 0 ? 0 : (int)n; i < items.Count; i++) { outp.Add(items[i]); }
            return new LongStream(outp);
        }

        public int anyMatch(global::java.util.function.LongPredicate predicate)
        {
            foreach (var v in items) { if (predicate.test(v) != 0) { return 1; } }
            return 0;
        }

        public int allMatch(global::java.util.function.LongPredicate predicate)
        {
            foreach (var v in items) { if (predicate.test(v) == 0) { return 0; } }
            return 1;
        }

        public int noneMatch(global::java.util.function.LongPredicate predicate)
        {
            foreach (var v in items) { if (predicate.test(v) != 0) { return 0; } }
            return 1;
        }

        public global::java.util.OptionalLong min()
        {
            if (items.Count == 0) { return global::java.util.OptionalLong.Empty(); }
            long m = items[0];
            foreach (var v in items) { if (v < m) { m = v; } }
            return global::java.util.OptionalLong.Of(m);
        }

        public global::java.util.OptionalLong max()
        {
            if (items.Count == 0) { return global::java.util.OptionalLong.Empty(); }
            long m = items[0];
            foreach (var v in items) { if (v > m) { m = v; } }
            return global::java.util.OptionalLong.Of(m);
        }

        public global::java.util.OptionalDouble average()
        {
            if (items.Count == 0) { return global::java.util.OptionalDouble.Empty(); }
            double s = 0;
            foreach (var v in items) { s += v; }
            return global::java.util.OptionalDouble.Of(s / items.Count);
        }

        public global::java.util.OptionalLong findFirst()
        {
            return items.Count == 0 ? global::java.util.OptionalLong.Empty() : global::java.util.OptionalLong.Of(items[0]);
        }

        public global::java.util.LongSummaryStatistics summaryStatistics()
        {
            var stats = new global::java.util.LongSummaryStatistics(global::java.lang.RawNew.I);
            foreach (var v in items) { stats.Accept(v); }
            return stats;
        }

        public long sum()
        {
            long s = 0;
            foreach (var v in items) { s += v; }
            return s;
        }

        public long count() { return items.Count; }

        public long[] toArray() { return items.ToArray(); }
    }
}
