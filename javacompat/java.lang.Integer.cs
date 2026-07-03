namespace java.lang
{
    public sealed class Integer : Object
    {
        private Integer() : base(RawNew.I)
        {
        }

        public static int parseInt(global::java.lang.String s)
        {
            return int.Parse(s.Value,
                    global::System.Globalization.NumberStyles.AllowLeadingSign,
                    global::System.Globalization.CultureInfo.InvariantCulture);
        }

        public static global::java.lang.String toString(int v)
        {
            return String.Wrap(JRuntime.Str(v));
        }
    }
}
