namespace java.util.stream
{
    public sealed class DoubleStream : global::java.lang.Object
    {
        private readonly global::System.Collections.Generic.List<double> items;

        public DoubleStream(global::java.lang.RawNew r) : base(r)
        {
            this.items = new global::System.Collections.Generic.List<double>();
        }

        internal DoubleStream(global::System.Collections.Generic.List<double> backing) : base(global::java.lang.RawNew.I)
        {
            this.items = backing;
        }

        public static DoubleStream of(double[] values)
        {
            return new DoubleStream(new global::System.Collections.Generic.List<double>(values));
        }

        public DoubleStream parallel() { return this; }
        public DoubleStream sequential() { return this; }

        public Stream boxed()
        {
            var outp = new global::System.Collections.Generic.List<global::java.lang.Object>(items.Count);
            foreach (var v in items) { outp.Add(global::java.lang.Double.valueOf(v)); }
            return Stream.Wrap(outp);
        }

        public Stream mapToObj(global::java.util.function.DoubleFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<global::java.lang.Object>(items.Count);
            foreach (var v in items) { outp.Add(mapper.apply(v)); }
            return Stream.Wrap(outp);
        }

        public IntStream mapToInt(global::java.util.function.DoubleToIntFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<int>(items.Count);
            foreach (var v in items) { outp.Add(mapper.applyAsInt(v)); }
            return new IntStream(outp);
        }

        public LongStream mapToLong(global::java.util.function.DoubleToLongFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<long>(items.Count);
            foreach (var v in items) { outp.Add(mapper.applyAsLong(v)); }
            return new LongStream(outp);
        }

        public void forEach(global::java.util.function.DoubleConsumer action)
        {
            foreach (var v in items) { action.accept(v); }
        }

        public DoubleStream peek(global::java.util.function.DoubleConsumer action)
        {
            foreach (var v in items) { action.accept(v); }
            return this;
        }

        public DoubleStream filter(global::java.util.function.DoublePredicate predicate)
        {
            var outp = new global::System.Collections.Generic.List<double>();
            foreach (var v in items) { if (predicate.test(v) != 0) { outp.Add(v); } }
            return new DoubleStream(outp);
        }

        public DoubleStream map(global::java.util.function.DoubleUnaryOperator mapper)
        {
            var outp = new global::System.Collections.Generic.List<double>(items.Count);
            foreach (var v in items) { outp.Add(mapper.applyAsDouble(v)); }
            return new DoubleStream(outp);
        }

        public double reduce(double identity, global::java.util.function.DoubleBinaryOperator op)
        {
            double acc = identity;
            foreach (var v in items) { acc = op.applyAsDouble(acc, v); }
            return acc;
        }

        public global::java.util.OptionalDouble reduce(global::java.util.function.DoubleBinaryOperator op)
        {
            if (items.Count == 0) { return global::java.util.OptionalDouble.Empty(); }
            double acc = items[0];
            for (int i = 1; i < items.Count; i++) { acc = op.applyAsDouble(acc, items[i]); }
            return global::java.util.OptionalDouble.Of(acc);
        }

        public DoubleStream sorted()
        {
            var outp = new global::System.Collections.Generic.List<double>(items);
            outp.Sort();
            return new DoubleStream(outp);
        }

        public DoubleStream distinct()
        {
            var seen = new global::System.Collections.Generic.HashSet<double>();
            var outp = new global::System.Collections.Generic.List<double>();
            foreach (var v in items) { if (seen.Add(v)) { outp.Add(v); } }
            return new DoubleStream(outp);
        }

        public DoubleStream limit(long maxSize)
        {
            var outp = new global::System.Collections.Generic.List<double>();
            for (int i = 0; i < items.Count && i < maxSize; i++) { outp.Add(items[i]); }
            return new DoubleStream(outp);
        }

        public DoubleStream skip(long n)
        {
            var outp = new global::System.Collections.Generic.List<double>();
            for (int i = n < 0 ? 0 : (int)n; i < items.Count; i++) { outp.Add(items[i]); }
            return new DoubleStream(outp);
        }

        public int anyMatch(global::java.util.function.DoublePredicate predicate)
        {
            foreach (var v in items) { if (predicate.test(v) != 0) { return 1; } }
            return 0;
        }

        public int allMatch(global::java.util.function.DoublePredicate predicate)
        {
            foreach (var v in items) { if (predicate.test(v) == 0) { return 0; } }
            return 1;
        }

        public int noneMatch(global::java.util.function.DoublePredicate predicate)
        {
            foreach (var v in items) { if (predicate.test(v) != 0) { return 0; } }
            return 1;
        }

        public global::java.util.OptionalDouble min()
        {
            if (items.Count == 0) { return global::java.util.OptionalDouble.Empty(); }
            double m = items[0];
            foreach (var v in items) { if (v < m) { m = v; } }
            return global::java.util.OptionalDouble.Of(m);
        }

        public global::java.util.OptionalDouble max()
        {
            if (items.Count == 0) { return global::java.util.OptionalDouble.Empty(); }
            double m = items[0];
            foreach (var v in items) { if (v > m) { m = v; } }
            return global::java.util.OptionalDouble.Of(m);
        }

        public global::java.util.OptionalDouble average()
        {
            if (items.Count == 0) { return global::java.util.OptionalDouble.Empty(); }
            double s = 0;
            foreach (var v in items) { s += v; }
            return global::java.util.OptionalDouble.Of(s / items.Count);
        }

        public global::java.util.OptionalDouble findFirst()
        {
            return items.Count == 0 ? global::java.util.OptionalDouble.Empty() : global::java.util.OptionalDouble.Of(items[0]);
        }

        public global::java.util.DoubleSummaryStatistics summaryStatistics()
        {
            var stats = new global::java.util.DoubleSummaryStatistics(global::java.lang.RawNew.I);
            foreach (var v in items) { stats.Accept(v); }
            return stats;
        }

        public double sum()
        {
            double s = 0;
            foreach (var v in items) { s += v; }
            return s;
        }

        public long count() { return items.Count; }

        public double[] toArray() { return items.ToArray(); }
    }
}
