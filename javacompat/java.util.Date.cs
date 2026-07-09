namespace java.util
{
    public class Date : global::java.lang.Object
    {
        internal long millis;

        public Date(global::java.lang.RawNew r) : base(r) { }

        public void __init__V() { millis = global::System.DateTimeOffset.UtcNow.ToUnixTimeMilliseconds(); }
        public void __init_J_V(long t) { millis = t; }

        public long getTime() { return millis; }
        public void setTime(long t) { millis = t; }

        public int before(Date o) { return millis < o.millis ? 1 : 0; }
        public int after(Date o) { return millis > o.millis ? 1 : 0; }
        public int compareTo(Date o) { return millis < o.millis ? -1 : millis > o.millis ? 1 : 0; }

        public override int equals(global::java.lang.Object o) { return o is Date d && d.millis == millis ? 1 : 0; }
        public override int hashCode() { return (int)(millis ^ (long)((ulong)millis >> 32)); }

        public override global::java.lang.String toString()
        {
            var utc = global::System.DateTimeOffset.FromUnixTimeMilliseconds(millis).UtcDateTime;
            return global::java.lang.String.Wrap(utc.ToString("ddd MMM dd HH:mm:ss 'UTC' yyyy",
                    global::System.Globalization.CultureInfo.InvariantCulture));
        }
    }
}
