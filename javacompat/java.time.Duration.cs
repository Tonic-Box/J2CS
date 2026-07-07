namespace java.time
{
    public sealed class Duration : global::java.lang.Object
    {
        private readonly long seconds;
        private readonly int nanos;

        public Duration(global::java.lang.RawNew r) : base(r)
        {
        }

        private Duration(long seconds, int nanos) : base(global::java.lang.RawNew.I)
        {
            this.seconds = seconds;
            this.nanos = nanos;
        }

        public static Duration ofDays(long days) { return new Duration(days * 86400L, 0); }
        public static Duration ofHours(long hours) { return new Duration(hours * 3600L, 0); }
        public static Duration ofMinutes(long minutes) { return new Duration(minutes * 60L, 0); }
        public static Duration ofSeconds(long secs) { return new Duration(secs, 0); }

        public static Duration ofMillis(long millis)
        {
            long s = (long)global::System.Math.Floor(millis / 1000.0);
            int n = (int)(millis - s * 1000L) * 1000000;
            return new Duration(s, n);
        }

        public long toDays() { return seconds / 86400L; }
        public long toHours() { return seconds / 3600L; }
        public long toMinutes() { return seconds / 60L; }
        public long toSeconds() { return seconds; }
        public long getSeconds() { return seconds; }
        public long toMillis() { return seconds * 1000L + nanos / 1000000L; }
        public long toNanos() { return seconds * 1000000000L + nanos; }

        public Duration plus(Duration other)
        {
            long s = seconds + other.seconds;
            long n = nanos + other.nanos;
            s += n / 1000000000L;
            n %= 1000000000L;
            return new Duration(s, (int)n);
        }

        public Duration minus(Duration other)
        {
            long s = seconds - other.seconds;
            long n = nanos - other.nanos;
            s += (long)global::System.Math.Floor(n / 1000000000.0);
            n -= (long)global::System.Math.Floor(n / 1000000000.0) * 1000000000L;
            return new Duration(s, (int)n);
        }

        public int compareTo(Duration other)
        {
            if (seconds != other.seconds) { return seconds < other.seconds ? -1 : 1; }
            return nanos < other.nanos ? -1 : (nanos > other.nanos ? 1 : 0);
        }

        public override global::java.lang.String toString()
        {
            if (seconds == 0 && nanos == 0)
            {
                return global::java.lang.String.Wrap("PT0S");
            }
            long hours = seconds / 3600L;
            int minutes = (int)((seconds % 3600L) / 60L);
            int secs = (int)(seconds % 60L);
            var sb = new global::System.Text.StringBuilder("PT");
            if (hours != 0) { sb.Append(hours).Append('H'); }
            if (minutes != 0) { sb.Append(minutes).Append('M'); }
            if (secs != 0 || nanos != 0)
            {
                sb.Append(secs);
                sb.Append('S');
            }
            return global::java.lang.String.Wrap(sb.ToString());
        }
    }
}
