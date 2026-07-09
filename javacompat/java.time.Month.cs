namespace java.time
{
    public sealed class Month : global::java.lang.Object
    {
        public static readonly Month JANUARY = new Month(1, "JANUARY");
        public static readonly Month FEBRUARY = new Month(2, "FEBRUARY");
        public static readonly Month MARCH = new Month(3, "MARCH");
        public static readonly Month APRIL = new Month(4, "APRIL");
        public static readonly Month MAY = new Month(5, "MAY");
        public static readonly Month JUNE = new Month(6, "JUNE");
        public static readonly Month JULY = new Month(7, "JULY");
        public static readonly Month AUGUST = new Month(8, "AUGUST");
        public static readonly Month SEPTEMBER = new Month(9, "SEPTEMBER");
        public static readonly Month OCTOBER = new Month(10, "OCTOBER");
        public static readonly Month NOVEMBER = new Month(11, "NOVEMBER");
        public static readonly Month DECEMBER = new Month(12, "DECEMBER");

        private static readonly Month[] VALUES =
        {
            JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
        };

        internal readonly int val;
        internal readonly string nm;

        private Month(int v, string name) : base(global::java.lang.RawNew.I) { val = v; nm = name; }
        public Month(global::java.lang.RawNew r) : base(r) { nm = ""; }

        public static Month of(int month) { return VALUES[month - 1]; }
        public static Month[] values() { return (Month[])VALUES.Clone(); }
        public int getValue() { return val; }

        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(nm); }
    }
}
