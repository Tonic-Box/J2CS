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

        public Stream(global::java.lang.RawNew r) : base(r)
        {
            this.items = new CList();
        }

        internal Stream(CList backing) : base(global::java.lang.RawNew.I)
        {
            this.items = backing;
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
            for (int i = 0; i < items.Count && i < maxSize; i++) { outp.Add(items[i]); }
            return new Stream(outp);
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
                    var map = new global::java.util.HashMap(global::java.lang.RawNew.I);
                    map.__init__V();
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
                    var map = new global::java.util.HashMap(global::java.lang.RawNew.I);
                    map.__init__V();
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
                    return map;
                }
                case Collector.Kind.Counting:
                    return global::java.lang.Long.valueOf(items.Count);
                default:
                    throw new global::System.NotSupportedException("j2cs: unsupported collector");
            }
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
