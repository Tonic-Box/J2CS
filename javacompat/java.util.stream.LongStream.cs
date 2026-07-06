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

        public long sum()
        {
            long s = 0;
            foreach (var v in items) { s += v; }
            return s;
        }

        public long count()
        {
            return items.Count;
        }

        public void forEach(global::java.util.function.DoubleConsumer action)
        {
            foreach (var v in items) { action.accept(v); }
        }
    }
}
