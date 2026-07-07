namespace java.util
{
    public sealed class OptionalInt : global::java.lang.Object
    {
        private static readonly OptionalInt EMPTY = new OptionalInt(global::java.lang.RawNew.I);
        private readonly int value;
        private readonly bool present;

        public OptionalInt(global::java.lang.RawNew r) : base(r)
        {
        }

        private OptionalInt(int v) : base(global::java.lang.RawNew.I)
        {
            value = v;
            present = true;
        }

        internal static OptionalInt Of(int v)
        {
            return new OptionalInt(v);
        }

        internal static OptionalInt Empty()
        {
            return EMPTY;
        }

        public int getAsInt()
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

        public int orElse(int other)
        {
            return present ? value : other;
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(present ? "OptionalInt[" + value + "]" : "OptionalInt.empty");
        }
    }
}
