namespace java.util.function
{
    public interface Function
    {
        global::java.lang.Object apply(global::java.lang.Object t);

        private sealed class Identity : Function
        {
            public global::java.lang.Object apply(global::java.lang.Object t) { return t; }
        }

        public static Function identity()
        {
            return new Identity();
        }
    }
}
