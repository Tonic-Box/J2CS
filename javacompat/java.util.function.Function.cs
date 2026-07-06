namespace java.util.function
{
    public interface Function
    {
        global::java.lang.Object apply(global::java.lang.Object t);

        Function andThen(Function after)
        {
            return new Composed(this, after);
        }

        Function compose(Function before)
        {
            return new Composed(before, this);
        }

        private sealed class Identity : Function
        {
            public global::java.lang.Object apply(global::java.lang.Object t) { return t; }
        }

        private sealed class Composed : Function
        {
            private readonly Function first;
            private readonly Function second;

            public Composed(Function first, Function second)
            {
                this.first = first;
                this.second = second;
            }

            public global::java.lang.Object apply(global::java.lang.Object t)
            {
                return second.apply(first.apply(t));
            }
        }

        public static Function identity()
        {
            return new Identity();
        }
    }
}
