namespace java.util
{
    public sealed class DoubleSummaryStatistics : global::java.lang.Object
    {
        private long count;
        private double sum;
        private double min = double.PositiveInfinity;
        private double max = double.NegativeInfinity;

        public DoubleSummaryStatistics(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        internal void Accept(double v)
        {
            count++;
            sum += v;
            if (v < min) { min = v; }
            if (v > max) { max = v; }
        }

        public long getCount() { return count; }
        public double getSum() { return sum; }
        public double getMin() { return min; }
        public double getMax() { return max; }
        public double getAverage() { return count == 0 ? 0.0 : sum / count; }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(string.Format(
                    global::System.Globalization.CultureInfo.InvariantCulture,
                    "DoubleSummaryStatistics{{count={0}, sum={1:F6}, min={2:F6}, average={3:F6}, max={4:F6}}}",
                    count, sum, min, getAverage(), max));
        }
    }
}
