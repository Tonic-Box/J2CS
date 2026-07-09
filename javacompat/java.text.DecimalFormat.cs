namespace java.text
{
    public class DecimalFormat : global::java.lang.Object
    {
        private static readonly global::System.Globalization.CultureInfo Inv = global::System.Globalization.CultureInfo.InvariantCulture;

        private string pattern;

        public DecimalFormat(global::java.lang.RawNew r) : base(r) { pattern = "0"; }

        public void __init__V() { pattern = "0"; }

        public void __init_Ljava_lang_String__V(global::java.lang.String p)
        {
            pattern = p == null ? "0" : p.Value;
        }

        public global::java.lang.String format(double v)
        {
            return global::java.lang.String.Wrap(v.ToString(pattern, Inv));
        }

        public global::java.lang.String format(long v)
        {
            return global::java.lang.String.Wrap(v.ToString(pattern, Inv));
        }

        public void applyPattern(global::java.lang.String p)
        {
            pattern = p == null ? "0" : p.Value;
        }

        public global::java.lang.String toPattern()
        {
            return global::java.lang.String.Wrap(pattern);
        }
    }
}
