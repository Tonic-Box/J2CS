namespace java.lang
{
    public sealed class Integer : Object
    {
        private Integer() : base(RawNew.I)
        {
        }

        public static int parseInt(global::java.lang.String s)
        {
            if (s == null)
            {
                throw JRuntime.NumberFormat("Cannot parse null string");
            }
            int result;
            bool ok = int.TryParse(s.Value,
                    global::System.Globalization.NumberStyles.AllowLeadingSign,
                    global::System.Globalization.CultureInfo.InvariantCulture,
                    out result);
            if (!ok)
            {
                throw JRuntime.NumberFormat("For input string: \"" + s.Value + "\"");
            }
            return result;
        }

        public static global::java.lang.String toString(int v)
        {
            return String.Wrap(JRuntime.Str(v));
        }
    }
}
