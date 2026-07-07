namespace java.time
{
    public sealed class LocalDate : global::java.lang.Object, global::java.time.chrono.ChronoLocalDate
    {
        internal readonly global::System.DateOnly value;

        public LocalDate(global::java.lang.RawNew r) : base(r)
        {
            value = global::System.DateOnly.MinValue;
        }

        private LocalDate(global::System.DateOnly v) : base(global::java.lang.RawNew.I)
        {
            value = v;
        }

        public static LocalDate of(int year, int month, int dayOfMonth)
        {
            return new LocalDate(new global::System.DateOnly(year, month, dayOfMonth));
        }

        public static LocalDate now()
        {
            return new LocalDate(global::System.DateOnly.FromDateTime(global::System.DateTime.Now));
        }

        public LocalDate plusDays(long days) { return new LocalDate(value.AddDays((int)days)); }
        public LocalDate minusDays(long days) { return new LocalDate(value.AddDays(-(int)days)); }
        public LocalDate plusWeeks(long weeks) { return new LocalDate(value.AddDays((int)(weeks * 7))); }
        public LocalDate minusWeeks(long weeks) { return new LocalDate(value.AddDays(-(int)(weeks * 7))); }
        public LocalDate plusMonths(long months) { return new LocalDate(value.AddMonths((int)months)); }
        public LocalDate minusMonths(long months) { return new LocalDate(value.AddMonths(-(int)months)); }
        public LocalDate plusYears(long years) { return new LocalDate(value.AddYears((int)years)); }
        public LocalDate minusYears(long years) { return new LocalDate(value.AddYears(-(int)years)); }

        public int getYear() { return value.Year; }
        public int getMonthValue() { return value.Month; }
        public int getDayOfMonth() { return value.Day; }
        public int getDayOfYear() { return value.DayOfYear; }
        public int lengthOfMonth() { return global::System.DateTime.DaysInMonth(value.Year, value.Month); }
        public int isLeapYear() { return global::System.DateTime.IsLeapYear(value.Year) ? 1 : 0; }

        public DayOfWeek getDayOfWeek()
        {
            int net = (int)value.DayOfWeek;
            return DayOfWeek.FromValue(net == 0 ? 7 : net);
        }

        public int isBefore(global::java.time.chrono.ChronoLocalDate other) { return value < ((LocalDate)other).value ? 1 : 0; }
        public int isAfter(global::java.time.chrono.ChronoLocalDate other) { return value > ((LocalDate)other).value ? 1 : 0; }
        public int isEqual(global::java.time.chrono.ChronoLocalDate other) { return value == ((LocalDate)other).value ? 1 : 0; }

        public int compareTo(global::java.time.chrono.ChronoLocalDate o)
        {
            var other = (LocalDate)o;
            int c = value.Year - other.value.Year;
            if (c == 0)
            {
                c = value.Month - other.value.Month;
                if (c == 0)
                {
                    c = value.Day - other.value.Day;
                }
            }
            return c;
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is LocalDate other && other.value == value ? 1 : 0;
        }

        public override int hashCode()
        {
            return value.GetHashCode();
        }

        public global::java.lang.String format(global::java.time.format.DateTimeFormatter formatter)
        {
            return global::java.lang.String.Wrap(value.ToString(formatter.NetPattern,
                    global::System.Globalization.CultureInfo.InvariantCulture));
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(value.ToString("yyyy-MM-dd",
                    global::System.Globalization.CultureInfo.InvariantCulture));
        }
    }
}
