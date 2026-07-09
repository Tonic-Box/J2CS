namespace java.time
{
    public sealed class Year : global::java.lang.Object
    {
        internal readonly int val;

        internal Year(int v) : base(global::java.lang.RawNew.I) { val = v; }
        public Year(global::java.lang.RawNew r) : base(r) { }

        public static Year of(int isoYear) { return new Year(isoYear); }
        public int getValue() { return val; }
        public int isLeap() { return IsLeapYear(val) ? 1 : 0; }
        public static int isLeap(long year) { return IsLeapYear(year) ? 1 : 0; }

        private static bool IsLeapYear(long y) { return (y % 4 == 0 && y % 100 != 0) || y % 400 == 0; }

        public override int equals(global::java.lang.Object o) { return o is Year x && x.val == val ? 1 : 0; }
        public override int hashCode() { return val; }
        public override global::java.lang.String toString() { return global::java.lang.String.Wrap(val.ToString(global::System.Globalization.CultureInfo.InvariantCulture)); }
    }
}
