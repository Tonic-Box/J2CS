namespace java.util
{
    public sealed class StringJoiner : global::java.lang.Object
    {
        private string prefix = "";
        private string suffix = "";
        private string delimiter = "";
        private string emptyValue;
        private global::System.Text.StringBuilder value;

        public StringJoiner(global::java.lang.RawNew r) : base(r)
        {
        }

        private static string Cs(global::java.lang.CharSequence cs)
        {
            return cs == null ? "null" : cs.toString().Value;
        }

        public void __init_Ljava_lang_CharSequence__V(global::java.lang.CharSequence delimiter)
        {
            this.delimiter = Cs(delimiter);
            this.emptyValue = this.prefix + this.suffix;
        }

        public void __init_Ljava_lang_CharSequence_Ljava_lang_CharSequence_Ljava_lang_CharSequence__V(
                global::java.lang.CharSequence delimiter,
                global::java.lang.CharSequence prefix,
                global::java.lang.CharSequence suffix)
        {
            this.prefix = Cs(prefix);
            this.delimiter = Cs(delimiter);
            this.suffix = Cs(suffix);
            this.emptyValue = this.prefix + this.suffix;
        }

        public StringJoiner setEmptyValue(global::java.lang.CharSequence emptyValue)
        {
            this.emptyValue = Cs(emptyValue);
            return this;
        }

        private global::System.Text.StringBuilder Prepare()
        {
            if (value == null)
            {
                value = new global::System.Text.StringBuilder().Append(prefix);
            }
            else
            {
                value.Append(delimiter);
            }
            return value;
        }

        public StringJoiner add(global::java.lang.CharSequence newElement)
        {
            Prepare().Append(Cs(newElement));
            return this;
        }

        public StringJoiner merge(StringJoiner other)
        {
            if (other.value != null)
            {
                int start = other.prefix.Length;
                string part = other.value.ToString().Substring(start);
                Prepare().Append(part);
            }
            return this;
        }

        public int length()
        {
            return value == null
                    ? emptyValue.Length
                    : value.Length + suffix.Length;
        }

        public override global::java.lang.String toString()
        {
            if (value == null)
            {
                return global::java.lang.String.Wrap(emptyValue);
            }
            if (suffix.Length == 0)
            {
                return global::java.lang.String.Wrap(value.ToString());
            }
            return global::java.lang.String.Wrap(value.ToString() + suffix);
        }
    }
}
