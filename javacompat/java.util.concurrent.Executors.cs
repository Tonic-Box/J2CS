namespace java.util.concurrent
{
    public sealed class Executors : global::java.lang.Object
    {
        public Executors(global::java.lang.RawNew r) : base(r)
        {
        }

        public static ExecutorService newFixedThreadPool(int nThreads)
        {
            return new ExecutorService(global::java.lang.RawNew.I);
        }

        public static ExecutorService newCachedThreadPool()
        {
            return new ExecutorService(global::java.lang.RawNew.I);
        }

        public static ExecutorService newSingleThreadExecutor()
        {
            return new ExecutorService(global::java.lang.RawNew.I);
        }

        public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
        {
            return new ScheduledExecutorService(global::java.lang.RawNew.I);
        }

        public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory)
        {
            return new ScheduledExecutorService(global::java.lang.RawNew.I);
        }

        public static ScheduledExecutorService newSingleThreadScheduledExecutor()
        {
            return new ScheduledExecutorService(global::java.lang.RawNew.I);
        }

        public static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory)
        {
            return new ScheduledExecutorService(global::java.lang.RawNew.I);
        }

        public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory)
        {
            return new ExecutorService(global::java.lang.RawNew.I);
        }
    }
}
