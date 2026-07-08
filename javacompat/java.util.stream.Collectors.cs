namespace java.util.stream
{
    public sealed class Collectors : global::java.lang.Object
    {
        public Collectors(global::java.lang.RawNew r) : base(r)
        {
        }

        public static Collector toList()
        {
            return Collector.Of(Collector.Kind.List);
        }

        public static Collector toSet()
        {
            return Collector.Of(Collector.Kind.Set);
        }

        public static Collector joining()
        {
            return Collector.Of(Collector.Kind.Joining);
        }

        private static string Cs(global::java.lang.CharSequence cs)
        {
            return cs == null ? "" : cs.toString().Value;
        }

        public static Collector joining(global::java.lang.CharSequence delimiter)
        {
            Collector c = Collector.Of(Collector.Kind.Joining);
            c.delimiter = Cs(delimiter);
            return c;
        }

        public static Collector joining(global::java.lang.CharSequence delimiter,
            global::java.lang.CharSequence prefix, global::java.lang.CharSequence suffix)
        {
            Collector c = Collector.Of(Collector.Kind.Joining);
            c.delimiter = Cs(delimiter);
            c.prefix = Cs(prefix);
            c.suffix = Cs(suffix);
            return c;
        }

        public static Collector toMap(global::java.util.function.Function keyMapper,
            global::java.util.function.Function valueMapper)
        {
            Collector c = Collector.Of(Collector.Kind.ToMap);
            c.keyFn = keyMapper;
            c.valueFn = valueMapper;
            return c;
        }

        public static Collector toMap(global::java.util.function.Function keyMapper,
            global::java.util.function.Function valueMapper, global::java.util.function.BinaryOperator mergeFunction)
        {
            Collector c = Collector.Of(Collector.Kind.ToMap);
            c.keyFn = keyMapper;
            c.valueFn = valueMapper;
            c.mergeFn = mergeFunction;
            return c;
        }

        public static Collector groupingBy(global::java.util.function.Function classifier)
        {
            Collector c = Collector.Of(Collector.Kind.GroupingBy);
            c.keyFn = classifier;
            return c;
        }

        public static Collector counting()
        {
            return Collector.Of(Collector.Kind.Counting);
        }

        public static Collector groupingBy(global::java.util.function.Function classifier, Collector downstream)
        {
            Collector c = Collector.Of(Collector.Kind.GroupingBy);
            c.keyFn = classifier;
            c.downstream = downstream;
            return c;
        }

        public static Collector summingInt(global::java.util.function.ToIntFunction mapper)
        {
            Collector c = Collector.Of(Collector.Kind.SummingInt);
            c.intFn = mapper;
            return c;
        }

        public static Collector averagingInt(global::java.util.function.ToIntFunction mapper)
        {
            Collector c = Collector.Of(Collector.Kind.AveragingInt);
            c.intFn = mapper;
            return c;
        }

        public static Collector partitioningBy(global::java.util.function.Predicate predicate)
        {
            Collector c = Collector.Of(Collector.Kind.Partitioning);
            c.predicate = predicate;
            return c;
        }

        public static Collector mapping(global::java.util.function.Function mapper, Collector downstream)
        {
            Collector c = Collector.Of(Collector.Kind.Mapping);
            c.keyFn = mapper;
            c.downstream = downstream;
            return c;
        }

        public static Collector minBy(global::java.util.Comparator comparator)
        {
            Collector c = Collector.Of(Collector.Kind.MinBy);
            c.comparator = comparator;
            return c;
        }

        public static Collector maxBy(global::java.util.Comparator comparator)
        {
            Collector c = Collector.Of(Collector.Kind.MaxBy);
            c.comparator = comparator;
            return c;
        }

        public static Collector reducing(global::java.lang.Object identity,
                global::java.util.function.BinaryOperator op)
        {
            Collector c = Collector.Of(Collector.Kind.Reducing);
            c.identity = identity;
            c.hasIdentity = true;
            c.reduceOp = op;
            return c;
        }

        public static Collector reducing(global::java.util.function.BinaryOperator op)
        {
            Collector c = Collector.Of(Collector.Kind.Reducing);
            c.reduceOp = op;
            return c;
        }

        public static Collector reducing(global::java.lang.Object identity,
                global::java.util.function.Function mapper, global::java.util.function.BinaryOperator op)
        {
            Collector c = Collector.Of(Collector.Kind.Reducing);
            c.identity = identity;
            c.hasIdentity = true;
            c.reduceMapper = mapper;
            c.reduceOp = op;
            return c;
        }

        public static Collector summingLong(global::java.util.function.ToLongFunction mapper)
        {
            Collector c = Collector.Of(Collector.Kind.SummingLong);
            c.longFn = mapper;
            return c;
        }

        public static Collector summingDouble(global::java.util.function.ToDoubleFunction mapper)
        {
            Collector c = Collector.Of(Collector.Kind.SummingDouble);
            c.doubleFn = mapper;
            return c;
        }

        public static Collector averagingLong(global::java.util.function.ToLongFunction mapper)
        {
            Collector c = Collector.Of(Collector.Kind.AveragingLong);
            c.longFn = mapper;
            return c;
        }

        public static Collector averagingDouble(global::java.util.function.ToDoubleFunction mapper)
        {
            Collector c = Collector.Of(Collector.Kind.AveragingDouble);
            c.doubleFn = mapper;
            return c;
        }

        public static Collector collectingAndThen(Collector downstream,
                global::java.util.function.Function finisher)
        {
            Collector c = Collector.Of(Collector.Kind.CollectingAndThen);
            c.downstream = downstream;
            c.finisher = finisher;
            return c;
        }

        public static Collector toCollection(global::java.util.function.Supplier supplier)
        {
            Collector c = Collector.Of(Collector.Kind.ToCollection);
            c.supplier = supplier;
            return c;
        }
    }
}
