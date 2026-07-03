namespace java.lang
{
    public sealed class JThrow : global::System.Exception
    {
        public readonly Throwable payload;

        private JThrow(Throwable payload, string message) : base(message)
        {
            this.payload = payload;
        }

        public static JThrow of(Throwable t)
        {
            Throwable p = t;
            if (p == null)
            {
                var npe = new NullPointerException(RawNew.I);
                npe.__init__V();
                p = npe;
            }
            return new JThrow(p, p.toString().Value);
        }
    }
}
