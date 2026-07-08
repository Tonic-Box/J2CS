namespace java.util
{
    public sealed class LongSummaryStatistics : global::java.lang.Object
    {
        private long count;
        private long sum;
        private long min = long.MaxValue;
        private long max = long.MinValue;

        public LongSummaryStatistics(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        internal void Accept(long v)
        {
            count++;
            sum += v;
            if (v < min) { min = v; }
            if (v > max) { max = v; }
        }

        public long getCount() { return count; }
        public long getSum() { return sum; }
        public long getMin() { return min; }
        public long getMax() { return max; }
        public double getAverage() { return count == 0 ? 0.0 : (double)sum / count; }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(string.Format(
                    global::System.Globalization.CultureInfo.InvariantCulture,
                    "LongSummaryStatistics{{count={0}, sum={1}, min={2}, average={3:F6}, max={4}}}",
                    count, sum, min, getAverage(), max));
        }
    }
}
