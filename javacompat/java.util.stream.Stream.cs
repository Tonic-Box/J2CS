namespace java.util.stream
{
    using CList = global::System.Collections.Generic.List<global::java.lang.Object>;

    /// <summary>
    /// Eager reference stream: each intermediate operation materializes a new list. Correct for
    /// the finite, single-use pipelines Java programs build; sidesteps lazy-evaluation machinery.
    /// </summary>
    public sealed class Stream : global::java.lang.Object
    {
        private readonly CList items;
        private readonly global::System.Func<global::java.lang.Object> gen;

        public Stream(global::java.lang.RawNew r) : base(r)
        {
            this.items = new CList();
        }

        internal Stream(CList backing) : base(global::java.lang.RawNew.I)
        {
            this.items = backing;
        }

        internal Stream(global::System.Func<global::java.lang.Object> generator) : base(global::java.lang.RawNew.I)
        {
            this.gen = generator;
        }

        public static Stream iterate(global::java.lang.Object seed, global::java.util.function.UnaryOperator f)
        {
            var cur = new global::java.lang.Object[] { seed };
            var started = new bool[] { false };
            return new Stream(() =>
            {
                if (!started[0])
                {
                    started[0] = true;
                    return cur[0];
                }
                cur[0] = f.apply(cur[0]);
                return cur[0];
            });
        }

        public static Stream generate(global::java.util.function.Supplier s)
        {
            return new Stream(() => s.get());
        }

        public static Stream concat(Stream a, Stream b)
        {
            var outp = new CList(a.items);
            outp.AddRange(b.items);
            return new Stream(outp);
        }

        public static Stream Wrap(CList backing)
        {
            return new Stream(backing);
        }

        internal CList Items => items;

        public Stream map(global::java.util.function.Function mapper)
        {
            var outp = new CList(items.Count);
            foreach (var e in items) { outp.Add(mapper.apply(e)); }
            return new Stream(outp);
        }

        public Stream filter(global::java.util.function.Predicate predicate)
        {
            var outp = new CList();
            foreach (var e in items) { if (predicate.test(e) != 0) { outp.Add(e); } }
            return new Stream(outp);
        }

        public Stream peek(global::java.util.function.Consumer action)
        {
            foreach (var e in items) { action.accept(e); }
            return this;
        }

        public Stream distinct()
        {
            var outp = new CList();
            var seen = new global::System.Collections.Generic.HashSet<string>();
            foreach (var e in items)
            {
                if (seen.Add(global::java.lang.JRuntime.Str(e))) { outp.Add(e); }
            }
            return new Stream(outp);
        }

        public Stream limit(long maxSize)
        {
            var outp = new CList();
            if (gen != null)
            {
                for (long i = 0; i < maxSize; i++) { outp.Add(gen()); }
                return new Stream(outp);
            }
            for (int i = 0; i < items.Count && i < maxSize; i++) { outp.Add(items[i]); }
            return new Stream(outp);
        }

        public Stream takeWhile(global::java.util.function.Predicate predicate)
        {
            var outp = new CList();
            foreach (var e in items)
            {
                if (predicate.test(e) == 0) { break; }
                outp.Add(e);
            }
            return new Stream(outp);
        }

        public Stream dropWhile(global::java.util.function.Predicate predicate)
        {
            var outp = new CList();
            bool dropping = true;
            foreach (var e in items)
            {
                if (dropping && predicate.test(e) != 0) { continue; }
                dropping = false;
                outp.Add(e);
            }
            return new Stream(outp);
        }

        public global::java.util.List toList()
        {
            var l = new global::java.util.ArrayList(global::java.lang.RawNew.I);
            l.__init__V();
            foreach (var e in items) { l.add(e); }
            return l;
        }

        public global::java.util.Optional findAny()
        {
            return items.Count > 0 ? global::java.util.Optional.ofNullable(items[0]) : global::java.util.Optional.empty();
        }

        public Stream skip(long n)
        {
            var outp = new CList();
            for (int i = (int)n; i < items.Count; i++) { outp.Add(items[i]); }
            return new Stream(outp);
        }

        public Stream sorted()
        {
            var outp = new CList(items);
            outp.Sort((a, b) => string.CompareOrdinal(global::java.lang.JRuntime.Str(a), global::java.lang.JRuntime.Str(b)));
            return new Stream(outp);
        }

        public Stream sorted(global::java.util.Comparator comparator)
        {
            var outp = new CList(items);
            outp.Sort((a, b) => comparator.compare(a, b));
            return new Stream(outp);
        }

        public Stream flatMap(global::java.util.function.Function mapper)
        {
            var outp = new CList();
            foreach (var e in items)
            {
                var sub = mapper.apply(e) as Stream;
                if (sub != null) { outp.AddRange(sub.items); }
            }
            return new Stream(outp);
        }

        public void forEach(global::java.util.function.Consumer action)
        {
            foreach (var e in items) { action.accept(e); }
        }

        public long count()
        {
            return items.Count;
        }

        public int anyMatch(global::java.util.function.Predicate predicate)
        {
            foreach (var e in items) { if (predicate.test(e) != 0) { return 1; } }
            return 0;
        }

        public int allMatch(global::java.util.function.Predicate predicate)
        {
            foreach (var e in items) { if (predicate.test(e) == 0) { return 0; } }
            return 1;
        }

        public int noneMatch(global::java.util.function.Predicate predicate)
        {
            foreach (var e in items) { if (predicate.test(e) != 0) { return 0; } }
            return 1;
        }

        public global::java.util.Optional findFirst()
        {
            return items.Count > 0 ? global::java.util.Optional.ofNullable(items[0]) : global::java.util.Optional.empty();
        }

        public global::java.util.Optional max(global::java.util.Comparator comparator)
        {
            if (items.Count == 0) { return global::java.util.Optional.empty(); }
            global::java.lang.Object best = items[0];
            for (int i = 1; i < items.Count; i++)
            {
                if (comparator.compare(items[i], best) > 0) { best = items[i]; }
            }
            return global::java.util.Optional.ofNullable(best);
        }

        public global::java.util.Optional min(global::java.util.Comparator comparator)
        {
            if (items.Count == 0) { return global::java.util.Optional.empty(); }
            global::java.lang.Object best = items[0];
            for (int i = 1; i < items.Count; i++)
            {
                if (comparator.compare(items[i], best) < 0) { best = items[i]; }
            }
            return global::java.util.Optional.ofNullable(best);
        }

        public global::java.util.Optional reduce(global::java.util.function.BinaryOperator accumulator)
        {
            if (items.Count == 0) { return global::java.util.Optional.empty(); }
            global::java.lang.Object acc = items[0];
            for (int i = 1; i < items.Count; i++) { acc = accumulator.apply(acc, items[i]); }
            return global::java.util.Optional.ofNullable(acc);
        }

        public global::java.lang.Object reduce(global::java.lang.Object identity,
            global::java.util.function.BinaryOperator accumulator)
        {
            global::java.lang.Object acc = identity;
            foreach (var e in items) { acc = accumulator.apply(acc, e); }
            return acc;
        }

        public global::java.lang.Object[] toArray()
        {
            return items.ToArray();
        }

        public global::java.lang.Object[] toArray(global::java.util.function.IntFunction generator)
        {
            var arr = (global::java.lang.Object[])(object)generator.apply(items.Count);
            for (int i = 0; i < items.Count; i++) { arr[i] = items[i]; }
            return arr;
        }

        public LongStream mapToLong(global::java.util.function.ToLongFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<long>(items.Count);
            foreach (var e in items) { outp.Add(mapper.applyAsLong(e)); }
            return new LongStream(outp);
        }

        public IntStream mapToInt(global::java.util.function.ToIntFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<int>(items.Count);
            foreach (var e in items) { outp.Add(mapper.applyAsInt(e)); }
            return new IntStream(outp);
        }

        public DoubleStream mapToDouble(global::java.util.function.ToDoubleFunction mapper)
        {
            var outp = new global::System.Collections.Generic.List<double>(items.Count);
            foreach (var e in items) { outp.Add(mapper.applyAsDouble(e)); }
            return new DoubleStream(outp);
        }

        public global::java.lang.Object collect(Collector collector)
        {
            return CollectInto(items, collector);
        }

        private static global::java.util.HashMap NewMap(Collector collector)
        {
            global::java.util.HashMap map = collector.concurrent
                    ? new global::java.util.concurrent.ConcurrentHashMap(global::java.lang.RawNew.I)
                    : new global::java.util.HashMap(global::java.lang.RawNew.I);
            map.__init__V();
            return map;
        }

        internal static global::java.lang.Object CollectInto(CList items, Collector collector)
        {
            switch (collector.kind)
            {
                case Collector.Kind.List:
                {
                    var list = new global::java.util.ArrayList(global::java.lang.RawNew.I);
                    list.__init__V();
                    foreach (var e in items) { list.add(e); }
                    return list;
                }
                case Collector.Kind.Set:
                {
                    var set = new global::java.util.HashSet(global::java.lang.RawNew.I);
                    set.__init__V();
                    foreach (var e in items) { set.add(e); }
                    return set;
                }
                case Collector.Kind.Joining:
                {
                    var sb = new global::System.Text.StringBuilder(collector.prefix);
                    for (int i = 0; i < items.Count; i++)
                    {
                        if (i > 0) { sb.Append(collector.delimiter); }
                        sb.Append(global::java.lang.JRuntime.Str(items[i]));
                    }
                    sb.Append(collector.suffix);
                    return global::java.lang.String.Wrap(sb.ToString());
                }
                case Collector.Kind.ToMap:
                {
                    var map = NewMap(collector);
                    foreach (var e in items)
                    {
                        var key = collector.keyFn.apply(e);
                        var val = collector.valueFn.apply(e);
                        var existing = map.get(key);
                        if (existing != null && collector.mergeFn != null)
                        {
                            val = collector.mergeFn.apply(existing, val);
                        }
                        map.put(key, val);
                    }
                    return map;
                }
                case Collector.Kind.GroupingBy:
                {
                    var map = NewMap(collector);
                    foreach (var e in items)
                    {
                        var key = collector.keyFn.apply(e);
                        var bucket = map.get(key) as global::java.util.ArrayList;
                        if (bucket == null)
                        {
                            bucket = new global::java.util.ArrayList(global::java.lang.RawNew.I);
                            bucket.__init__V();
                            map.put(key, bucket);
                        }
                        bucket.add(e);
                    }
                    if (collector.downstream != null) { ApplyDownstream(map, collector.downstream); }
                    return map;
                }
                case Collector.Kind.Counting:
                    return global::java.lang.Long.valueOf(items.Count);
                case Collector.Kind.SummingInt:
                {
                    int sum = 0;
                    foreach (var e in items) { sum += collector.intFn.applyAsInt(e); }
                    return global::java.lang.Integer.valueOf(sum);
                }
                case Collector.Kind.AveragingInt:
                {
                    long sum = 0;
                    foreach (var e in items) { sum += collector.intFn.applyAsInt(e); }
                    return global::java.lang.Double.valueOf(items.Count == 0 ? 0.0 : (double)sum / items.Count);
                }
                case Collector.Kind.Partitioning:
                {
                    var map = new global::java.util.HashMap(global::java.lang.RawNew.I);
                    map.__init__V();
                    var f = new global::java.util.ArrayList(global::java.lang.RawNew.I);
                    f.__init__V();
                    var t = new global::java.util.ArrayList(global::java.lang.RawNew.I);
                    t.__init__V();
                    foreach (var e in items)
                    {
                        if (collector.predicate.test(e) != 0) { t.add(e); } else { f.add(e); }
                    }
                    map.put(global::java.lang.Boolean.valueOf(0), f);
                    map.put(global::java.lang.Boolean.valueOf(1), t);
                    if (collector.downstream != null) { ApplyDownstream(map, collector.downstream); }
                    return map;
                }
                case Collector.Kind.Mapping:
                {
                    var mapped = new CList(items.Count);
                    foreach (var e in items) { mapped.Add(collector.keyFn.apply(e)); }
                    return CollectInto(mapped, collector.downstream);
                }
                case Collector.Kind.MinBy:
                case Collector.Kind.MaxBy:
                {
                    global::java.lang.Object best = null;
                    bool has = false;
                    bool wantMin = collector.kind == Collector.Kind.MinBy;
                    foreach (var e in items)
                    {
                        int cmp = has ? collector.comparator.compare(e, best) : 0;
                        if (!has || (wantMin ? cmp < 0 : cmp > 0)) { best = e; has = true; }
                    }
                    return has ? global::java.util.Optional.ofNullable(best) : global::java.util.Optional.empty();
                }
                case Collector.Kind.Reducing:
                {
                    if (collector.hasIdentity)
                    {
                        global::java.lang.Object acc = collector.identity;
                        foreach (var e in items)
                        {
                            var v = collector.reduceMapper != null ? collector.reduceMapper.apply(e) : e;
                            acc = collector.reduceOp.apply(acc, v);
                        }
                        return acc;
                    }
                    global::java.lang.Object racc = null;
                    bool rhas = false;
                    foreach (var e in items)
                    {
                        racc = rhas ? collector.reduceOp.apply(racc, e) : e;
                        rhas = true;
                    }
                    return rhas ? global::java.util.Optional.ofNullable(racc) : global::java.util.Optional.empty();
                }
                case Collector.Kind.SummingLong:
                {
                    long sum = 0;
                    foreach (var e in items) { sum += collector.longFn.applyAsLong(e); }
                    return global::java.lang.Long.valueOf(sum);
                }
                case Collector.Kind.SummingDouble:
                {
                    double sum = 0;
                    foreach (var e in items) { sum += collector.doubleFn.applyAsDouble(e); }
                    return global::java.lang.Double.valueOf(sum);
                }
                case Collector.Kind.AveragingLong:
                {
                    long sum = 0;
                    foreach (var e in items) { sum += collector.longFn.applyAsLong(e); }
                    return global::java.lang.Double.valueOf(items.Count == 0 ? 0.0 : (double)sum / items.Count);
                }
                case Collector.Kind.AveragingDouble:
                {
                    double sum = 0;
                    foreach (var e in items) { sum += collector.doubleFn.applyAsDouble(e); }
                    return global::java.lang.Double.valueOf(items.Count == 0 ? 0.0 : sum / items.Count);
                }
                case Collector.Kind.CollectingAndThen:
                    return collector.finisher.apply(CollectInto(items, collector.downstream));
                case Collector.Kind.ToCollection:
                {
                    var coll = collector.supplier.get();
                    foreach (var e in items) { ((global::java.util.Collection)coll).add(e); }
                    return coll;
                }
                case Collector.Kind.SummarizingInt:
                {
                    var stats = new global::java.util.IntSummaryStatistics(global::java.lang.RawNew.I);
                    foreach (var e in items) { stats.Accept(collector.intFn.applyAsInt(e)); }
                    return stats;
                }
                case Collector.Kind.SummarizingLong:
                {
                    var stats = new global::java.util.LongSummaryStatistics(global::java.lang.RawNew.I);
                    foreach (var e in items) { stats.Accept(collector.longFn.applyAsLong(e)); }
                    return stats;
                }
                case Collector.Kind.SummarizingDouble:
                {
                    var stats = new global::java.util.DoubleSummaryStatistics(global::java.lang.RawNew.I);
                    foreach (var e in items) { stats.Accept(collector.doubleFn.applyAsDouble(e)); }
                    return stats;
                }
                case Collector.Kind.Filtering:
                {
                    var filtered = new CList(items.Count);
                    foreach (var e in items) { if (collector.predicate.test(e) != 0) { filtered.Add(e); } }
                    return CollectInto(filtered, collector.downstream);
                }
                case Collector.Kind.FlatMapping:
                {
                    var flat = new CList(items.Count);
                    foreach (var e in items)
                    {
                        var sub = collector.keyFn.apply(e) as global::java.util.stream.Stream;
                        if (sub != null) { foreach (var x in sub.Items) { flat.Add(x); } }
                    }
                    return CollectInto(flat, collector.downstream);
                }
                case Collector.Kind.Teeing:
                {
                    var r1 = CollectInto(items, collector.downstream);
                    var r2 = CollectInto(items, collector.downstream2);
                    return collector.merger.apply(r1, r2);
                }
                default:
                    throw new global::System.NotSupportedException("j2cs: unsupported collector");
            }
        }

        private static void ApplyDownstream(global::java.util.HashMap map, Collector downstream)
        {
            var it = map.entrySet().iterator();
            var keys = new CList();
            var results = new CList();
            while (it.hasNext() != 0)
            {
                var en = (global::java.util.Map_S_Entry)it.next();
                var bucket = (global::java.util.List)en.getValue();
                int n = bucket.size();
                var cl = new CList(n);
                for (int i = 0; i < n; i++) { cl.Add(bucket.get(i)); }
                keys.Add(en.getKey());
                results.Add(CollectInto(cl, downstream));
            }
            for (int i = 0; i < keys.Count; i++) { map.put(keys[i], results[i]); }
        }

        public static Stream of(global::java.lang.Object[] values)
        {
            var outp = new CList(values.Length);
            foreach (var v in values) { outp.Add(v); }
            return new Stream(outp);
        }

        public static Stream empty()
        {
            return new Stream(new CList());
        }
    }
}
