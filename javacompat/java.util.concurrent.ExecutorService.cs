namespace java.util.concurrent
{
    /// <summary>
    /// Synchronous executor: submitted work runs inline on submit/execute, so the pool is always
    /// idle by shutdown/awaitTermination. Deterministic and safe against the plain-dictionary
    /// collection shims (no real concurrent mutation).
    /// </summary>
    public class ExecutorService : global::java.lang.Object, Executor
    {
        public ExecutorService(global::java.lang.RawNew r) : base(r)
        {
        }

        public virtual void execute(global::java.lang.Runnable command)
        {
            if (command != null)
            {
                command.run();
            }
        }

        public virtual Future submit(global::java.lang.Runnable task)
        {
            if (task != null)
            {
                task.run();
            }
            return new Future((global::java.lang.Object)null);
        }

        public virtual Future submit(Callable task)
        {
            global::java.lang.Object result = task == null ? null : task.call();
            return new Future(result);
        }

        public virtual void shutdown()
        {
        }

        public virtual global::java.util.List shutdownNow()
        {
            var empty = new global::java.util.ArrayList(global::java.lang.RawNew.I);
            empty.__init__V();
            return empty;
        }

        public virtual int awaitTermination(long timeout, TimeUnit unit)
        {
            return 1;
        }

        public virtual int isShutdown()
        {
            return 1;
        }

        public virtual int isTerminated()
        {
            return 1;
        }
    }
}
