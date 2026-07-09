namespace java.time
{
    public sealed class Period : global::java.lang.Object
    {
        internal readonly int y, mo, d;

        internal Period(int yy, int mm, int dd) : base(global::java.lang.RawNew.I) { y = yy; mo = mm; d = dd; }
        public Period(global::java.lang.RawNew r) : base(r) { }

        public static Period of(int years, int months, int days) { return new Period(years, months, days); }
        public static Period ofYears(int years) { return new Period(years, 0, 0); }
        public static Period ofMonths(int months) { return new Period(0, months, 0); }
        public static Period ofWeeks(int weeks) { return new Period(0, 0, weeks * 7); }
        public static Period ofDays(int days) { return new Period(0, 0, days); }

        public int getYears() { return y; }
        public int getMonths() { return mo; }
        public int getDays() { return d; }

        public Period plusYears(long years) { return new Period((int)(y + years), mo, d); }
        public Period plusMonths(long months) { return new Period(y, (int)(mo + months), d); }
        public Period plusDays(long days) { return new Period(y, mo, (int)(d + days)); }

        public override int equals(global::java.lang.Object o) { return o is Period p && p.y == y && p.mo == mo && p.d == d ? 1 : 0; }
        public override int hashCode() { return y + 31 * mo + 961 * d; }

        public override global::java.lang.String toString()
        {
            if (y == 0 && mo == 0 && d == 0) { return global::java.lang.String.Wrap("P0D"); }
            var sb = new global::System.Text.StringBuilder("P");
            if (y != 0) { sb.Append(y).Append('Y'); }
            if (mo != 0) { sb.Append(mo).Append('M'); }
            if (d != 0) { sb.Append(d).Append('D'); }
            return global::java.lang.String.Wrap(sb.ToString());
        }
    }
}
