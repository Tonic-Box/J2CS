namespace java.util.concurrent
{
    /// <summary>
    /// Synchronous CompletableFuture: async factories run their work inline and complete
    /// immediately, so get/join/whenComplete observe the finished result. Sufficient for
    /// fire-and-wait pipelines; does not model real overlap.
    /// </summary>
    public sealed class CompletableFuture : global::java.lang.Object, Future
    {
        internal global::java.lang.Object value;

        public CompletableFuture(global::java.lang.RawNew r) : base(r)
        {
        }

        private CompletableFuture(global::java.lang.Object v) : base(global::java.lang.RawNew.I)
        {
            this.value = v;
        }

        public global::java.lang.Object get() { return value; }
        public global::java.lang.Object get(long timeout, TimeUnit unit) { return value; }
        public int isDone() { return 1; }
        public int isCancelled() { return 0; }
        public int cancel(int mayInterrupt) { return 0; }

        public static CompletableFuture runAsync(global::java.lang.Runnable action)
        {
            if (action != null)
            {
                action.run();
            }
            return new CompletableFuture((global::java.lang.Object)null);
        }

        public static CompletableFuture runAsync(global::java.lang.Runnable action, Executor executor)
        {
            return runAsync(action);
        }

        public static CompletableFuture supplyAsync(global::java.util.function.Supplier supplier)
        {
            return new CompletableFuture(supplier == null ? null : supplier.get());
        }

        public static CompletableFuture supplyAsync(global::java.util.function.Supplier supplier, Executor executor)
        {
            return supplyAsync(supplier);
        }

        public static CompletableFuture completedFuture(global::java.lang.Object v)
        {
            return new CompletableFuture(v);
        }

        public static CompletableFuture allOf(CompletableFuture[] futures)
        {
            return new CompletableFuture((global::java.lang.Object)null);
        }

        public static CompletableFuture anyOf(CompletableFuture[] futures)
        {
            global::java.lang.Object v = futures != null && futures.Length > 0 ? futures[0].get() : null;
            return new CompletableFuture(v);
        }

        public CompletableFuture whenComplete(global::java.util.function.BiConsumer action)
        {
            if (action != null)
            {
                action.accept(value, null);
            }
            return this;
        }

        public CompletableFuture thenApply(global::java.util.function.Function fn)
        {
            return new CompletableFuture(fn == null ? null : fn.apply(value));
        }

        public CompletableFuture thenAccept(global::java.util.function.Consumer action)
        {
            if (action != null)
            {
                action.accept(value);
            }
            return new CompletableFuture((global::java.lang.Object)null);
        }

        public CompletableFuture thenRun(global::java.lang.Runnable action)
        {
            if (action != null)
            {
                action.run();
            }
            return new CompletableFuture((global::java.lang.Object)null);
        }

        public global::java.lang.Object join()
        {
            return value;
        }
    }
}
