namespace java.util.stream
{
    /// <summary>
    /// A minimal, eager Collector: a tagged descriptor built by the Collectors factory and
    /// interpreted by Stream.collect. Covers the reductions used in practice (toList/toSet,
    /// joining, toMap, groupingBy, counting).
    /// </summary>
    public sealed class Collector : global::java.lang.Object
    {
        public enum Kind
        {
            List, Set, Joining, ToMap, GroupingBy, Counting, SummingInt, AveragingInt, Partitioning, Mapping,
            MinBy, MaxBy, Reducing, SummingLong, SummingDouble, AveragingLong, AveragingDouble,
            CollectingAndThen, ToCollection,
            SummarizingInt, SummarizingLong, SummarizingDouble, Filtering, FlatMapping, Teeing
        }

        public Kind kind;
        public string delimiter = "";
        public string prefix = "";
        public string suffix = "";
        public global::java.util.function.Function keyFn;
        public global::java.util.function.Function valueFn;
        public global::java.util.function.BinaryOperator mergeFn;
        public global::java.util.function.ToIntFunction intFn;
        public global::java.util.function.Predicate predicate;
        public Collector downstream;
        public global::java.util.Comparator comparator;
        public global::java.util.function.ToLongFunction longFn;
        public global::java.util.function.ToDoubleFunction doubleFn;
        public global::java.util.function.Supplier supplier;
        public global::java.util.function.Function finisher;
        public global::java.util.function.BinaryOperator reduceOp;
        public global::java.util.function.Function reduceMapper;
        public global::java.lang.Object identity;
        public bool hasIdentity;
        public Collector downstream2;
        public global::java.util.function.BiFunction merger;

        public Collector(global::java.lang.RawNew r) : base(r)
        {
        }

        public static Collector Of(Kind k)
        {
            Collector c = new Collector(global::java.lang.RawNew.I);
            c.kind = k;
            return c;
        }
    }
}
