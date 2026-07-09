namespace java.lang
{
    public sealed class Character : Object
    {
        public static readonly global::java.lang.Class TYPE = global::java.lang.Class.Of("char");
        public const char MAX_VALUE = char.MaxValue;
        public const char MIN_VALUE = char.MinValue;

        private static readonly Character[] Cache = BuildCache();

        private char value;

        public Character(RawNew r) : base(r)
        {
        }

        public void __init_C_V(char v)
        {
            value = v;
        }

        private Character(char v) : base(RawNew.I)
        {
            value = v;
        }

        private static Character[] BuildCache()
        {
            var cache = new Character[128];
            for (int i = 0; i < 128; i++)
            {
                cache[i] = new Character((char)i);
            }
            return cache;
        }

        public static Character valueOf(char v)
        {
            if (v <= 127)
            {
                return Cache[v];
            }
            return new Character(v);
        }

        public static String toString(char v)
        {
            return String.Wrap(v.ToString());
        }

        public static int isDigit(char c)
        {
            return global::System.Char.IsDigit(c) ? 1 : 0;
        }

        public static int isLetter(char c)
        {
            return global::System.Char.IsLetter(c) ? 1 : 0;
        }

        public static int isLetterOrDigit(char c)
        {
            return global::System.Char.IsLetterOrDigit(c) ? 1 : 0;
        }

        public static int isWhitespace(char c)
        {
            return global::System.Char.IsWhiteSpace(c) ? 1 : 0;
        }

        public static int isUpperCase(char c)
        {
            return global::System.Char.IsUpper(c) ? 1 : 0;
        }

        public static int isLowerCase(char c)
        {
            return global::System.Char.IsLower(c) ? 1 : 0;
        }

        public static char toUpperCase(char c)
        {
            return global::System.Char.ToUpperInvariant(c);
        }

        public static char toLowerCase(char c)
        {
            return global::System.Char.ToLowerInvariant(c);
        }

        public static int getNumericValue(char c)
        {
            if (c >= '0' && c <= '9')
            {
                return c - '0';
            }
            if (c >= 'A' && c <= 'Z')
            {
                return c - 'A' + 10;
            }
            if (c >= 'a' && c <= 'z')
            {
                return c - 'a' + 10;
            }
            return -1;
        }

        public static int digit(char c, int radix)
        {
            int d = -1;
            if (c >= '0' && c <= '9')
            {
                d = c - '0';
            }
            else if (c >= 'A' && c <= 'Z')
            {
                d = c - 'A' + 10;
            }
            else if (c >= 'a' && c <= 'z')
            {
                d = c - 'a' + 10;
            }
            return d >= 0 && d < radix ? d : -1;
        }

        public static int isSpaceChar(char c)
        {
            var cat = global::System.Char.GetUnicodeCategory(c);
            return cat == global::System.Globalization.UnicodeCategory.SpaceSeparator
                    || cat == global::System.Globalization.UnicodeCategory.LineSeparator
                    || cat == global::System.Globalization.UnicodeCategory.ParagraphSeparator ? 1 : 0;
        }

        public static int isAlphabetic(int codePoint)
        {
            if (codePoint < 0 || codePoint > char.MaxValue)
            {
                return 0;
            }
            char c = (char)codePoint;
            return global::System.Char.IsLetter(c)
                    || global::System.Char.GetUnicodeCategory(c) == global::System.Globalization.UnicodeCategory.LetterNumber ? 1 : 0;
        }

        public static char forDigit(int digit, int radix)
        {
            if (radix < 2 || radix > 36 || digit < 0 || digit >= radix)
            {
                return '\0';
            }
            return (char)(digit < 10 ? '0' + digit : 'a' + digit - 10);
        }

        public static int isJavaIdentifierStart(char c)
        {
            return global::System.Char.IsLetter(c) || c == '$' || c == '_'
                    || global::System.Char.GetUnicodeCategory(c) == global::System.Globalization.UnicodeCategory.LetterNumber ? 1 : 0;
        }

        public static int isJavaIdentifierPart(char c)
        {
            if (isJavaIdentifierStart(c) != 0 || global::System.Char.IsDigit(c))
            {
                return 1;
            }
            var cat = global::System.Char.GetUnicodeCategory(c);
            return cat == global::System.Globalization.UnicodeCategory.NonSpacingMark
                    || cat == global::System.Globalization.UnicodeCategory.SpacingCombiningMark
                    || cat == global::System.Globalization.UnicodeCategory.ConnectorPunctuation ? 1 : 0;
        }

        public static int compare(char a, char b) { return a - b; }
        public static int hashCode(char c) { return c; }
        public static int charCount(int codePoint) { return codePoint >= 0x10000 ? 2 : 1; }
        public static String toString(int codePoint) { return String.Wrap(global::System.Char.ConvertFromUtf32(codePoint)); }
        public static int isSurrogate(char c) { return global::System.Char.IsSurrogate(c) ? 1 : 0; }
        public static int isHighSurrogate(char c) { return global::System.Char.IsHighSurrogate(c) ? 1 : 0; }
        public static int isLowSurrogate(char c) { return global::System.Char.IsLowSurrogate(c) ? 1 : 0; }
        public static char[] toChars(int codePoint) { return global::System.Char.ConvertFromUtf32(codePoint).ToCharArray(); }

        public char charValue()
        {
            return value;
        }

        public int compareTo(Character other)
        {
            return value - other.value;
        }

        public override int equals(global::java.lang.Object o)
        {
            return o is Character other && other.value == value ? 1 : 0;
        }

        public override int hashCode()
        {
            return value;
        }

        public override String toString()
        {
            return String.Wrap(value.ToString());
        }
    }
}
