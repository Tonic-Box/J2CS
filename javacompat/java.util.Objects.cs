namespace java.util
{
    public sealed class Objects : global::java.lang.Object
    {
        private Objects() : base(global::java.lang.RawNew.I)
        {
        }

        public static global::java.lang.Object requireNonNull(global::java.lang.Object o)
        {
            if (o == null)
            {
                throw global::java.lang.JRuntime.Simple(
                        new global::java.lang.NullPointerException(global::java.lang.RawNew.I));
            }
            return o;
        }
    }
}
