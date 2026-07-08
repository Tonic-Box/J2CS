namespace java.util
{
    public sealed class OptionalLong : global::java.lang.Object
    {
        private static readonly OptionalLong EMPTY = new OptionalLong(global::java.lang.RawNew.I);
        private readonly long value;
        private readonly bool present;

        public OptionalLong(global::java.lang.RawNew r) : base(r)
        {
        }

        private OptionalLong(long v) : base(global::java.lang.RawNew.I)
        {
            value = v;
            present = true;
        }

        internal static OptionalLong Of(long v) { return new OptionalLong(v); }
        internal static OptionalLong Empty() { return EMPTY; }

        public long getAsLong()
        {
            if (!present)
            {
                throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I));
            }
            return value;
        }

        public int isPresent() { return present ? 1 : 0; }
        public int isEmpty() { return present ? 0 : 1; }
        public long orElse(long other) { return present ? value : other; }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(present ? "OptionalLong[" + value + "]" : "OptionalLong.empty");
        }
    }
}
