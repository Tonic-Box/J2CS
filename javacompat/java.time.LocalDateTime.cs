namespace java.time
{
    public class LocalDateTime : global::java.lang.Object,
            global::java.time.chrono.ChronoLocalDateTime
    {
        internal readonly global::System.DateTime value;

        public LocalDateTime(global::java.lang.RawNew r) : base(r)
        {
            value = global::System.DateTime.MinValue;
        }

        private LocalDateTime(global::System.DateTime v) : base(global::java.lang.RawNew.I)
        {
            value = v;
        }

        public static LocalDateTime now()
        {
            return new LocalDateTime(global::System.DateTime.Now);
        }

        public LocalDateTime minusMinutes(long minutes)
        {
            return new LocalDateTime(value.AddMinutes(-minutes));
        }

        public LocalDateTime plusMinutes(long minutes)
        {
            return new LocalDateTime(value.AddMinutes(minutes));
        }

        public int isBefore(global::java.time.chrono.ChronoLocalDateTime other)
        {
            return value < ((LocalDateTime)other).value ? 1 : 0;
        }

        public int isAfter(global::java.time.chrono.ChronoLocalDateTime other)
        {
            return value > ((LocalDateTime)other).value ? 1 : 0;
        }

        public global::java.lang.String format(global::java.time.format.DateTimeFormatter formatter)
        {
            return global::java.lang.String.Wrap(value.ToString(formatter.NetPattern,
                    global::System.Globalization.CultureInfo.InvariantCulture));
        }
    }
}
