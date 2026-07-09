namespace java.util
{
    public sealed class Formatter : global::java.lang.Object
    {
        private readonly global::System.Text.StringBuilder sb = new global::System.Text.StringBuilder();

        public Formatter(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public Formatter format(global::java.lang.String fmt, global::java.lang.Object[] args)
        {
            sb.Append(global::java.lang.JRuntime.Format(fmt == null ? null : fmt.Value, args));
            return this;
        }

        public void close()
        {
        }

        public void flush()
        {
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(sb.ToString());
        }
    }
}
