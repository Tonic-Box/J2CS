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

        public DoubleStream filter(global::java.util.function.DoublePredicate predicate)
        {
            var outp = new global::System.Collections.Generic.List<double>();
            foreach (var v in items) { if (predicate.test(v) != 0) { outp.Add(v); } }
            return new DoubleStream(outp);
        }

        public void forEach(global::java.util.function.DoubleConsumer action)
        {
            foreach (var v in items) { action.accept(v); }
        }

        public double sum()
        {
            double s = 0;
            foreach (var v in items) { s += v; }
            return s;
        }

        public long count()
        {
            return items.Count;
        }
    }
}
