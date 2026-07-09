namespace java.util
{
    public class Calendar : global::java.lang.Object
    {
        public const int ERA = 0;
        public const int YEAR = 1;
        public const int MONTH = 2;
        public const int WEEK_OF_YEAR = 3;
        public const int WEEK_OF_MONTH = 4;
        public const int DATE = 5;
        public const int DAY_OF_MONTH = 5;
        public const int DAY_OF_YEAR = 6;
        public const int DAY_OF_WEEK = 7;
        public const int DAY_OF_WEEK_IN_MONTH = 8;
        public const int AM_PM = 9;
        public const int HOUR = 10;
        public const int HOUR_OF_DAY = 11;
        public const int MINUTE = 12;
        public const int SECOND = 13;
        public const int MILLISECOND = 14;
        public const int ZONE_OFFSET = 15;
        public const int DST_OFFSET = 16;

        public const int JANUARY = 0;
        public const int FEBRUARY = 1;
        public const int MARCH = 2;
        public const int APRIL = 3;
        public const int MAY = 4;
        public const int JUNE = 5;
        public const int JULY = 6;
        public const int AUGUST = 7;
        public const int SEPTEMBER = 8;
        public const int OCTOBER = 9;
        public const int NOVEMBER = 10;
        public const int DECEMBER = 11;

        public const int SUNDAY = 1;
        public const int MONDAY = 2;
        public const int TUESDAY = 3;
        public const int WEDNESDAY = 4;
        public const int THURSDAY = 5;
        public const int FRIDAY = 6;
        public const int SATURDAY = 7;

        public const int AM = 0;
        public const int PM = 1;

        internal global::System.DateTime local;
        internal TimeZone tz;

        public Calendar(global::java.lang.RawNew r) : base(r)
        {
            tz = TimeZone.getDefault();
            local = new global::System.DateTime(1970, 1, 1, 0, 0, 0, global::System.DateTimeKind.Unspecified);
        }

        internal void InitMillis(long ms, TimeZone zone)
        {
            tz = zone;
            local = zone.ToLocal(ms);
        }

        public static Calendar getInstance()
        {
            return getInstance(TimeZone.getDefault());
        }

        public static Calendar getInstance(TimeZone zone)
        {
            var c = new GregorianCalendar(global::java.lang.RawNew.I);
            c.InitMillis(global::System.DateTimeOffset.UtcNow.ToUnixTimeMilliseconds(), zone);
            return c;
        }

        public void setTime(Date d) { local = tz.ToLocal(d.getTime()); }
        public Date getTime() { var d = new Date(global::java.lang.RawNew.I); d.__init_J_V(getTimeInMillis()); return d; }
        public long getTimeInMillis() { return tz.ToMillis(local); }
        public void setTimeInMillis(long ms) { local = tz.ToLocal(ms); }

        public void setTimeZone(TimeZone zone)
        {
            long ms = getTimeInMillis();
            tz = zone;
            local = zone.ToLocal(ms);
        }

        public TimeZone getTimeZone() { return tz; }

        public int get(int field)
        {
            switch (field)
            {
                case YEAR: return local.Year;
                case MONTH: return local.Month - 1;
                case DATE: return local.Day;
                case DAY_OF_YEAR: return local.DayOfYear;
                case DAY_OF_WEEK: return (int)local.DayOfWeek + 1;
                case HOUR_OF_DAY: return local.Hour;
                case HOUR: return local.Hour % 12;
                case MINUTE: return local.Minute;
                case SECOND: return local.Second;
                case MILLISECOND: return local.Millisecond;
                case AM_PM: return local.Hour < 12 ? 0 : 1;
                default: return 0;
            }
        }

        public void set(int field, int value)
        {
            switch (field)
            {
                case YEAR: local = new global::System.DateTime(value, local.Month, local.Day, local.Hour, local.Minute, local.Second, local.Millisecond); break;
                case MONTH: local = new global::System.DateTime(local.Year, value + 1, local.Day, local.Hour, local.Minute, local.Second, local.Millisecond); break;
                case DATE: local = new global::System.DateTime(local.Year, local.Month, value, local.Hour, local.Minute, local.Second, local.Millisecond); break;
                case HOUR_OF_DAY: local = new global::System.DateTime(local.Year, local.Month, local.Day, value, local.Minute, local.Second, local.Millisecond); break;
                case MINUTE: local = new global::System.DateTime(local.Year, local.Month, local.Day, local.Hour, value, local.Second, local.Millisecond); break;
                case SECOND: local = new global::System.DateTime(local.Year, local.Month, local.Day, local.Hour, local.Minute, value, local.Millisecond); break;
                case MILLISECOND: local = new global::System.DateTime(local.Year, local.Month, local.Day, local.Hour, local.Minute, local.Second, value); break;
            }
        }

        public void set(int year, int month, int day)
        {
            local = new global::System.DateTime(year, month + 1, day, 0, 0, 0);
        }

        public void add(int field, int amount)
        {
            switch (field)
            {
                case YEAR: local = local.AddYears(amount); break;
                case MONTH: local = local.AddMonths(amount); break;
                case DATE: local = local.AddDays(amount); break;
                case DAY_OF_YEAR: local = local.AddDays(amount); break;
                case HOUR_OF_DAY: local = local.AddHours(amount); break;
                case HOUR: local = local.AddHours(amount); break;
                case MINUTE: local = local.AddMinutes(amount); break;
                case SECOND: local = local.AddSeconds(amount); break;
                case MILLISECOND: local = local.AddMilliseconds(amount); break;
            }
        }
    }
}
