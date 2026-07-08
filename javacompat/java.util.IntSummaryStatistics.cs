namespace java.util
{
    public sealed class IntSummaryStatistics : global::java.lang.Object
    {
        private long count;
        private long sum;
        private int min = int.MaxValue;
        private int max = int.MinValue;

        public IntSummaryStatistics(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        internal void Accept(int v)
        {
            count++;
            sum += v;
            if (v < min) { min = v; }
            if (v > max) { max = v; }
        }

        public long getCount() { return count; }
        public long getSum() { return sum; }
        public int getMin() { return min; }
        public int getMax() { return max; }
        public double getAverage() { return count == 0 ? 0.0 : (double)sum / count; }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(string.Format(
                    global::System.Globalization.CultureInfo.InvariantCulture,
                    "IntSummaryStatistics{{count={0}, sum={1}, min={2}, average={3:F6}, max={4}}}",
                    count, sum, min, getAverage(), max));
        }
    }
}
