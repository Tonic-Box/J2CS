namespace java.util.concurrent
{
    public class TimeUnit : global::java.lang.Object
    {
        public static readonly TimeUnit NANOSECONDS = new TimeUnit(1L);
        public static readonly TimeUnit MICROSECONDS = new TimeUnit(1000L);
        public static readonly TimeUnit MILLISECONDS = new TimeUnit(1000000L);
        public static readonly TimeUnit SECONDS = new TimeUnit(1000000000L);

        private readonly long nanosPerUnit;

        private TimeUnit(long nanosPerUnit) : base(global::java.lang.RawNew.I)
        {
            this.nanosPerUnit = nanosPerUnit;
        }

        public long toMillis(long duration)
        {
            return duration * nanosPerUnit / 1000000L;
        }

        public void sleep(long timeout)
        {
            if (timeout > 0)
            {
                long millis = toMillis(timeout);
                global::System.Threading.Thread.Sleep((int)(millis > int.MaxValue ? int.MaxValue : millis));
            }
        }
    }
}
