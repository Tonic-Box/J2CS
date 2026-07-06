namespace javax.swing
{
    public class SpinnerNumberModel : SpinnerModel
    {
        public SpinnerNumberModel(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_IIII_V(int value, int min, int max, int step)
        {
            Value = value;
            Min = min;
            Max = max;
            Step = step;
        }

        public void __init_DDDD_V(double value, double min, double max, double step)
        {
            Value = value;
            Min = min;
            Max = max;
            Step = step;
        }
    }
}
