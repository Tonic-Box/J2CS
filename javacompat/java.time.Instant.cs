namespace java.time
{
    public sealed class Instant : global::java.lang.Object
    {
        private static readonly global::System.Globalization.CultureInfo Inv = global::System.Globalization.CultureInfo.InvariantCulture;

        internal readonly long secs;
        internal readonly int nanos;

        internal Instant(long s, int n) : base(global::java.lang.RawNew.I) { secs = s; nanos = n; }
        public Instant(global::java.lang.RawNew r) : base(r) { }

        public static Instant ofEpochSecond(long s) { return new Instant(s, 0); }

        public static Instant ofEpochSecond(long s, long nanoAdjustment)
        {
            long extra = nanoAdjustment / 1000000000L;
            int n = (int)(nanoAdjustment % 1000000000L);
            if (n < 0) { n += 1000000000; extra -= 1; }
            return new Instant(s + extra, n);
        }

        public static Instant ofEpochMilli(long ms)
        {
            long s = ms / 1000L;
            int n = (int)(ms % 1000L) * 1000000;
            if (n < 0) { n += 1000000000; s -= 1; }
            return new Instant(s, n);
        }

        public long getEpochSecond() { return secs; }
        public int getNano() { return nanos; }
        public long toEpochMilli() { return secs * 1000L + nanos / 1000000L; }
        public Instant plusSeconds(long s) { return new Instant(secs + s, nanos); }
        public Instant minusSeconds(long s) { return new Instant(secs - s, nanos); }
        public Instant plusMillis(long ms) { return ofEpochMilli(toEpochMilli() + ms); }

        public int isBefore(Instant o) { return secs < o.secs || (secs == o.secs && nanos < o.nanos) ? 1 : 0; }
        public int isAfter(Instant o) { return secs > o.secs || (secs == o.secs && nanos > o.nanos) ? 1 : 0; }

        public int compareTo(Instant o)
        {
            if (secs != o.secs) { return secs < o.secs ? -1 : 1; }
            return nanos < o.nanos ? -1 : nanos > o.nanos ? 1 : 0;
        }

        public override int equals(global::java.lang.Object o) { return o is Instant x && x.secs == secs && x.nanos == nanos ? 1 : 0; }
        public override int hashCode() { return (int)(secs ^ (long)((ulong)secs >> 32)) + 51 * nanos; }

        public override global::java.lang.String toString()
        {
            string s = global::System.DateTimeOffset.FromUnixTimeSeconds(secs).UtcDateTime.ToString("yyyy-MM-dd'T'HH:mm:ss", Inv);
            return global::java.lang.String.Wrap(s + "Z");
        }
    }
}
