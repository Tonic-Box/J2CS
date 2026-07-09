namespace java.util
{
    public class TimeZone : global::java.lang.Object
    {
        internal readonly string id;
        internal readonly global::System.TimeZoneInfo info;

        internal TimeZone(string zoneId, global::System.TimeZoneInfo tz) : base(global::java.lang.RawNew.I) { id = zoneId; info = tz; }
        public TimeZone(global::java.lang.RawNew r) : base(r) { id = "UTC"; info = global::System.TimeZoneInfo.Utc; }

        public static TimeZone getTimeZone(global::java.lang.String zoneId)
        {
            string s = zoneId == null ? "UTC" : zoneId.Value;
            global::System.TimeZoneInfo tz;
            if (s.Length == 0 || s == "UTC" || s == "GMT" || s == "Etc/UTC")
            {
                tz = global::System.TimeZoneInfo.Utc;
            }
            else
            {
                try { tz = global::System.TimeZoneInfo.FindSystemTimeZoneById(s); }
                catch (global::System.Exception) { tz = global::System.TimeZoneInfo.Utc; }
            }
            return new TimeZone(s, tz);
        }

        public static TimeZone getDefault() { return new TimeZone("UTC", global::System.TimeZoneInfo.Utc); }

        public global::java.lang.String getID() { return global::java.lang.String.Wrap(id); }
        public int getRawOffset() { return (int)info.BaseUtcOffset.TotalMilliseconds; }

        internal global::System.DateTime ToLocal(long ms)
        {
            var utc = global::System.DateTimeOffset.FromUnixTimeMilliseconds(ms).UtcDateTime;
            return global::System.TimeZoneInfo.ConvertTimeFromUtc(utc, info);
        }

        internal long ToMillis(global::System.DateTime local)
        {
            var unspec = global::System.DateTime.SpecifyKind(local, global::System.DateTimeKind.Unspecified);
            var off = info.GetUtcOffset(unspec);
            return new global::System.DateTimeOffset(unspec, off).ToUnixTimeMilliseconds();
        }
    }
}
