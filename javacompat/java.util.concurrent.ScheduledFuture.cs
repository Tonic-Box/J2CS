namespace java.util.concurrent
{
    public sealed class ScheduledFuture : global::java.lang.Object, Future
    {
        private readonly global::System.Threading.Timer timer;

        internal ScheduledFuture(global::System.Threading.Timer timer) : base(global::java.lang.RawNew.I)
        {
            this.timer = timer;
        }

        public global::java.lang.Object get() { return null; }
        public global::java.lang.Object get(long timeout, TimeUnit unit) { return null; }
        public int isDone() { return 1; }
        public int isCancelled() { return 0; }

        public int cancel(int mayInterrupt)
        {
            timer?.Dispose();
            return 1;
        }
    }
}
