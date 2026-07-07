namespace java.util.stream
{
    /// <summary>
    /// A minimal, eager Collector: a tagged descriptor built by the Collectors factory and
    /// interpreted by Stream.collect. Covers the reductions used in practice (toList/toSet,
    /// joining, toMap, groupingBy, counting).
    /// </summary>
    public sealed class Collector : global::java.lang.Object
    {
        public enum Kind { List, Set, Joining, ToMap, GroupingBy, Counting, SummingInt, AveragingInt, Partitioning, Mapping }

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
