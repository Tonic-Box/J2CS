namespace java.time.temporal
{
    public class ChronoUnit : global::java.lang.Object
    {
        public static readonly ChronoUnit SECONDS = new ChronoUnit(1L);
        public static readonly ChronoUnit MINUTES = new ChronoUnit(60L);
        public static readonly ChronoUnit HOURS = new ChronoUnit(3600L);
        public static readonly ChronoUnit DAYS = new ChronoUnit(86400L);

        private readonly long secondsPerUnit;

        private ChronoUnit(long secondsPerUnit) : base(global::java.lang.RawNew.I)
        {
            this.secondsPerUnit = secondsPerUnit;
        }

        public long between(Temporal start, Temporal end)
        {
            var a = (global::java.time.LocalDateTime)start;
            var b = (global::java.time.LocalDateTime)end;
            return (long)((b.value - a.value).TotalSeconds / secondsPerUnit);
        }
    }
}
