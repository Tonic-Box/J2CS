namespace java.util
{
    public sealed class Currency : global::java.lang.Object
    {
        private readonly string code;
        private readonly int fractionDigits;
        private readonly int numericCode;

        private Currency(string code, int fractionDigits, int numericCode) : base(global::java.lang.RawNew.I)
        {
            this.code = code;
            this.fractionDigits = fractionDigits;
            this.numericCode = numericCode;
        }

        private static readonly global::System.Collections.Generic.Dictionary<string, Currency> Table = Build();

        private static global::System.Collections.Generic.Dictionary<string, Currency> Build()
        {
            var t = new global::System.Collections.Generic.Dictionary<string, Currency>();
            t["USD"] = new Currency("USD", 2, 840);
            t["EUR"] = new Currency("EUR", 2, 978);
            t["GBP"] = new Currency("GBP", 2, 826);
            t["JPY"] = new Currency("JPY", 0, 392);
            t["CNY"] = new Currency("CNY", 2, 156);
            t["INR"] = new Currency("INR", 2, 356);
            t["CHF"] = new Currency("CHF", 2, 756);
            t["CAD"] = new Currency("CAD", 2, 124);
            t["AUD"] = new Currency("AUD", 2, 36);
            t["KRW"] = new Currency("KRW", 0, 410);
            return t;
        }

        public static Currency getInstance(global::java.lang.String currencyCode)
        {
            string c = currencyCode == null ? null : currencyCode.Value;
            if (c == null || !Table.TryGetValue(c, out var cur))
            {
                var ex = new global::java.lang.IllegalArgumentException(global::java.lang.RawNew.I);
                ex.__init_Ljava_lang_String__V(global::java.lang.String.Wrap(c));
                throw global::java.lang.JThrow.of(ex);
            }
            return cur;
        }

        public global::java.lang.String getCurrencyCode()
        {
            return global::java.lang.String.Wrap(code);
        }

        public int getDefaultFractionDigits()
        {
            return fractionDigits;
        }

        public int getNumericCode()
        {
            return numericCode;
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(code);
        }
    }
}
