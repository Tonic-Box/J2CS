namespace java.util
{
    public sealed class Optional : global::java.lang.Object
    {
        private static readonly Optional EMPTY = new Optional(global::java.lang.RawNew.I);
        private readonly global::java.lang.Object value;
        private readonly bool present;

        public Optional(global::java.lang.RawNew r) : base(r)
        {
        }

        private Optional(global::java.lang.Object v) : base(global::java.lang.RawNew.I)
        {
            this.value = v;
            this.present = true;
        }

        public static Optional empty()
        {
            return EMPTY;
        }

        public static Optional of(global::java.lang.Object v)
        {
            if (v == null)
            {
                throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I));
            }
            return new Optional(v);
        }

        public static Optional ofNullable(global::java.lang.Object v)
        {
            return v == null ? EMPTY : new Optional(v);
        }

        public int isPresent()
        {
            return present ? 1 : 0;
        }

        public int isEmpty()
        {
            return present ? 0 : 1;
        }

        public global::java.lang.Object get()
        {
            if (!present)
            {
                throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I));
            }
            return value;
        }

        public global::java.lang.Object orElse(global::java.lang.Object other)
        {
            return present ? value : other;
        }

        public global::java.lang.Object orElseGet(global::java.util.function.Supplier other)
        {
            return present ? value : other.get();
        }

        public Optional map(global::java.util.function.Function mapper)
        {
            return present ? ofNullable(mapper.apply(value)) : EMPTY;
        }

        public Optional filter(global::java.util.function.Predicate predicate)
        {
            return present && predicate.test(value) != 0 ? this : EMPTY;
        }

        public void ifPresent(global::java.util.function.Consumer action)
        {
            if (present)
            {
                action.accept(value);
            }
        }

        public global::java.lang.Object orElseThrow(global::java.util.function.Supplier exceptionSupplier)
        {
            if (present)
            {
                return value;
            }
            throw global::java.lang.JThrow.of((global::java.lang.Throwable)exceptionSupplier.get());
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(present
                ? "Optional[" + global::java.lang.JRuntime.Str(value) + "]"
                : "Optional.empty");
        }
    }
}
