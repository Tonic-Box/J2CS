namespace java.util
{
    public class UUID : global::java.lang.Object
    {
        private readonly global::System.Guid value;

        public UUID(global::java.lang.RawNew r) : base(r)
        {
            value = global::System.Guid.Empty;
        }

        private UUID(global::System.Guid g) : base(global::java.lang.RawNew.I)
        {
            value = g;
        }

        public static UUID randomUUID()
        {
            return new UUID(global::System.Guid.NewGuid());
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(value.ToString("D"));
        }
    }
}
