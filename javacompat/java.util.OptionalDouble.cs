namespace java.util
{
    public sealed class OptionalDouble : global::java.lang.Object
    {
        private static readonly OptionalDouble EMPTY = new OptionalDouble(global::java.lang.RawNew.I);
        private readonly double value;
        private readonly bool present;

        public OptionalDouble(global::java.lang.RawNew r) : base(r)
        {
        }

        private OptionalDouble(double v) : base(global::java.lang.RawNew.I)
        {
            value = v;
            present = true;
        }

        internal static OptionalDouble Of(double v)
        {
            return new OptionalDouble(v);
        }

        internal static OptionalDouble Empty()
        {
            return EMPTY;
        }

        public double getAsDouble()
        {
            if (!present)
            {
                throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I));
            }
            return value;
        }

        public int isPresent()
        {
            return present ? 1 : 0;
        }

        public int isEmpty()
        {
            return present ? 0 : 1;
        }

        public double orElse(double other)
        {
            return present ? value : other;
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(present
                    ? "OptionalDouble[" + global::java.lang.JRuntime.Str(value) + "]"
                    : "OptionalDouble.empty");
        }
    }
}
