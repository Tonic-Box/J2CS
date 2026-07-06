namespace java.util.concurrent
{
    /// <summary>
    /// Executor with periodic scheduling backed by a real System.Threading.Timer (periodic work
    /// cannot run inline). Non-scheduled submit/execute stay synchronous via the base class.
    /// </summary>
    public class ScheduledExecutorService : ExecutorService
    {
        private readonly global::System.Collections.Generic.List<global::System.Threading.Timer> timers =
            new global::System.Collections.Generic.List<global::System.Threading.Timer>();

        public ScheduledExecutorService(global::java.lang.RawNew r) : base(r)
        {
        }

        public ScheduledFuture scheduleAtFixedRate(global::java.lang.Runnable command,
            long initialDelay, long period, TimeUnit unit)
        {
            long delayMs = unit.toMillis(initialDelay);
            long periodMs = unit.toMillis(period);
            var timer = new global::System.Threading.Timer(_ =>
            {
                try
                {
                    command.run();
                }
                catch (global::System.Exception)
                {
                }
            }, null, delayMs, periodMs <= 0 ? global::System.Threading.Timeout.Infinite : periodMs);
            lock (timers)
            {
                timers.Add(timer);
            }
            return new ScheduledFuture(timer);
        }

        public ScheduledFuture schedule(global::java.lang.Runnable command, long delay, TimeUnit unit)
        {
            long delayMs = unit.toMillis(delay);
            var timer = new global::System.Threading.Timer(_ =>
            {
                try
                {
                    command.run();
                }
                catch (global::System.Exception)
                {
                }
            }, null, delayMs, global::System.Threading.Timeout.Infinite);
            lock (timers)
            {
                timers.Add(timer);
            }
            return new ScheduledFuture(timer);
        }

        public override void shutdown()
        {
            DisposeTimers();
        }

        public override global::java.util.List shutdownNow()
        {
            DisposeTimers();
            return base.shutdownNow();
        }

        private void DisposeTimers()
        {
            lock (timers)
            {
                foreach (var t in timers)
                {
                    t.Dispose();
                }
                timers.Clear();
            }
        }
    }
}
