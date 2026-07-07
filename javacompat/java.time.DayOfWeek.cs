namespace java.time
{
    public sealed class DayOfWeek : global::java.lang.Object
    {
        public static readonly DayOfWeek MONDAY = new DayOfWeek(1, "MONDAY");
        public static readonly DayOfWeek TUESDAY = new DayOfWeek(2, "TUESDAY");
        public static readonly DayOfWeek WEDNESDAY = new DayOfWeek(3, "WEDNESDAY");
        public static readonly DayOfWeek THURSDAY = new DayOfWeek(4, "THURSDAY");
        public static readonly DayOfWeek FRIDAY = new DayOfWeek(5, "FRIDAY");
        public static readonly DayOfWeek SATURDAY = new DayOfWeek(6, "SATURDAY");
        public static readonly DayOfWeek SUNDAY = new DayOfWeek(7, "SUNDAY");

        private static readonly DayOfWeek[] All = { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY };

        private readonly int val;
        private readonly string nm;

        private DayOfWeek(int val, string nm) : base(global::java.lang.RawNew.I)
        {
            this.val = val;
            this.nm = nm;
        }

        public DayOfWeek(global::java.lang.RawNew r) : base(r)
        {
        }

        internal static DayOfWeek FromValue(int v)
        {
            return All[v - 1];
        }

        public static DayOfWeek of(int dayOfWeek)
        {
            return All[dayOfWeek - 1];
        }

        public int getValue()
        {
            return val;
        }

        public global::java.lang.String name()
        {
            return global::java.lang.String.Wrap(nm);
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(nm);
        }
    }
}
