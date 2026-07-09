namespace java.util
{
    public class GregorianCalendar : Calendar
    {
        public GregorianCalendar(global::java.lang.RawNew r) : base(r) { }

        public void __init__V()
        {
            tz = TimeZone.getDefault();
            local = tz.ToLocal(global::System.DateTimeOffset.UtcNow.ToUnixTimeMilliseconds());
        }

        public void __init_Ljava_util_TimeZone__V(TimeZone zone)
        {
            tz = zone;
            local = zone.ToLocal(global::System.DateTimeOffset.UtcNow.ToUnixTimeMilliseconds());
        }

        public void __init_III_V(int year, int month, int dayOfMonth)
        {
            tz = TimeZone.getDefault();
            local = new global::System.DateTime(year, month + 1, dayOfMonth, 0, 0, 0);
        }

        public void __init_IIIII_V(int year, int month, int dayOfMonth, int hourOfDay, int minute)
        {
            tz = TimeZone.getDefault();
            local = new global::System.DateTime(year, month + 1, dayOfMonth, hourOfDay, minute, 0);
        }

        public void __init_IIIIII_V(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second)
        {
            tz = TimeZone.getDefault();
            local = new global::System.DateTime(year, month + 1, dayOfMonth, hourOfDay, minute, second);
        }
    }
}
