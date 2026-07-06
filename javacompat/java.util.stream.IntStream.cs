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
    }
}
