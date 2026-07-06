namespace java.util.concurrent
{
    public class Future : global::java.lang.Object
    {
        protected global::java.lang.Object value;

        public Future(global::java.lang.RawNew r) : base(r)
        {
        }

        internal Future(global::java.lang.Object v) : base(global::java.lang.RawNew.I)
        {
            this.value = v;
        }

        public virtual global::java.lang.Object get()
        {
            return value;
        }

        public virtual global::java.lang.Object get(long timeout, global::java.util.concurrent.TimeUnit unit)
        {
            return value;
        }

        public int isDone()
        {
            return 1;
        }

        public int isCancelled()
        {
            return 0;
        }

        public int cancel(int mayInterrupt)
        {
            return 0;
        }
    }
}
