namespace java.time
{
    public sealed class LocalTime : global::java.lang.Object
    {
        private static readonly global::System.Globalization.CultureInfo Inv = global::System.Globalization.CultureInfo.InvariantCulture;

        internal readonly int h, m, s, n;

        internal LocalTime(int hh, int mm, int ss, int nn) : base(global::java.lang.RawNew.I) { h = hh; m = mm; s = ss; n = nn; }
        public LocalTime(global::java.lang.RawNew r) : base(r) { }

        public static LocalTime of(int hour, int minute) { return new LocalTime(hour, minute, 0, 0); }
        public static LocalTime of(int hour, int minute, int second) { return new LocalTime(hour, minute, second, 0); }
        public static LocalTime of(int hour, int minute, int second, int nano) { return new LocalTime(hour, minute, second, nano); }

        public int getHour() { return h; }
        public int getMinute() { return m; }
        public int getSecond() { return s; }
        public int getNano() { return n; }

        public LocalTime plusHours(long hours) { return new LocalTime((int)(((h + hours) % 24 + 24) % 24), m, s, n); }
        public LocalTime plusMinutes(long minutes) { long total = ((h * 60L + m) + minutes) % 1440; total = (total + 1440) % 1440; return new LocalTime((int)(total / 60), (int)(total % 60), s, n); }

        public int isBefore(LocalTime o) { return compareTo(o) < 0 ? 1 : 0; }
        public int isAfter(LocalTime o) { return compareTo(o) > 0 ? 1 : 0; }

        public int compareTo(LocalTime o)
        {
            if (h != o.h) { return h < o.h ? -1 : 1; }
            if (m != o.m) { return m < o.m ? -1 : 1; }
            if (s != o.s) { return s < o.s ? -1 : 1; }
            return n < o.n ? -1 : n > o.n ? 1 : 0;
        }

        public override int equals(global::java.lang.Object o) { return o is LocalTime x && x.h == h && x.m == m && x.s == s && x.n == n ? 1 : 0; }
        public override int hashCode() { return ((h * 60 + m) * 60 + s) * 31 + n; }

        public override global::java.lang.String toString()
        {
            string r = h.ToString("D2", Inv) + ":" + m.ToString("D2", Inv);
            if (s > 0 || n > 0) { r += ":" + s.ToString("D2", Inv); }
            return global::java.lang.String.Wrap(r);
        }
    }
}
