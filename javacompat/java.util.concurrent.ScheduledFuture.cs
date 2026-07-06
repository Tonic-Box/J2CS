namespace java.util.concurrent
{
    public sealed class ScheduledFuture : Future
    {
        private readonly global::System.Threading.Timer timer;

        internal ScheduledFuture(global::System.Threading.Timer timer) : base((global::java.lang.Object)null)
        {
            this.timer = timer;
        }

        public new int cancel(int mayInterrupt)
        {
            timer?.Dispose();
            return 1;
        }
    }
}
