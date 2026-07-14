namespace java.util.concurrent
{
    /// <summary>
    /// Concrete scheduled executor. Submit/execute run inline and schedule is timer-backed via the
    /// base; the core-pool-size constructor argument is advisory under the synchronous model.
    /// </summary>
    public class ScheduledThreadPoolExecutor : ScheduledExecutorService
    {
        public ScheduledThreadPoolExecutor(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_I_V(int corePoolSize)
        {
        }
    }
}
