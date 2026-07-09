namespace java.util
{
    public sealed class Locale : global::java.lang.Object
    {
        public static readonly Locale ROOT = new Locale("", "");
        public static readonly Locale ENGLISH = new Locale("en", "");
        public static readonly Locale US = new Locale("en", "US");
        public static readonly Locale UK = new Locale("en", "GB");

        private string lang;
        private string country;

        internal Locale(string l, string c) : base(global::java.lang.RawNew.I) { lang = l; country = c; }
        public Locale(global::java.lang.RawNew r) : base(r) { lang = ""; country = ""; }

        public void __init_Ljava_lang_String__V(global::java.lang.String language)
        {
            lang = language == null ? "" : language.Value;
            country = "";
        }

        public void __init_Ljava_lang_String_Ljava_lang_String__V(global::java.lang.String language, global::java.lang.String cty)
        {
            lang = language == null ? "" : language.Value;
            country = cty == null ? "" : cty.Value;
        }

        public static Locale getDefault() { return US; }
        public global::java.lang.String getLanguage() { return global::java.lang.String.Wrap(lang); }
        public global::java.lang.String getCountry() { return global::java.lang.String.Wrap(country); }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(country.Length > 0 ? lang + "_" + country : lang);
        }
    }
}
