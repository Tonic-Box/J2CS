namespace java.lang.reflect
{
    public sealed class Modifier : global::java.lang.Object
    {
        public const int PUBLIC = 0x0001;
        public const int PRIVATE = 0x0002;
        public const int PROTECTED = 0x0004;
        public const int STATIC = 0x0008;
        public const int FINAL = 0x0010;
        public const int SYNCHRONIZED = 0x0020;
        public const int VOLATILE = 0x0040;
        public const int TRANSIENT = 0x0080;
        public const int NATIVE = 0x0100;
        public const int INTERFACE = 0x0200;
        public const int ABSTRACT = 0x0400;
        public const int STRICT = 0x0800;

        public Modifier(global::java.lang.RawNew r) : base(r)
        {
        }

        public static int isPublic(int mod) { return (mod & PUBLIC) != 0 ? 1 : 0; }
        public static int isPrivate(int mod) { return (mod & PRIVATE) != 0 ? 1 : 0; }
        public static int isProtected(int mod) { return (mod & PROTECTED) != 0 ? 1 : 0; }
        public static int isStatic(int mod) { return (mod & STATIC) != 0 ? 1 : 0; }
        public static int isFinal(int mod) { return (mod & FINAL) != 0 ? 1 : 0; }
        public static int isSynchronized(int mod) { return (mod & SYNCHRONIZED) != 0 ? 1 : 0; }
        public static int isVolatile(int mod) { return (mod & VOLATILE) != 0 ? 1 : 0; }
        public static int isTransient(int mod) { return (mod & TRANSIENT) != 0 ? 1 : 0; }
        public static int isNative(int mod) { return (mod & NATIVE) != 0 ? 1 : 0; }
        public static int isInterface(int mod) { return (mod & INTERFACE) != 0 ? 1 : 0; }
        public static int isAbstract(int mod) { return (mod & ABSTRACT) != 0 ? 1 : 0; }
        public static int isStrict(int mod) { return (mod & STRICT) != 0 ? 1 : 0; }

        public static global::java.lang.String toString(int mod)
        {
            var sb = new global::System.Text.StringBuilder();
            Append(sb, (mod & PUBLIC) != 0, "public");
            Append(sb, (mod & PROTECTED) != 0, "protected");
            Append(sb, (mod & PRIVATE) != 0, "private");
            Append(sb, (mod & ABSTRACT) != 0, "abstract");
            Append(sb, (mod & STATIC) != 0, "static");
            Append(sb, (mod & FINAL) != 0, "final");
            Append(sb, (mod & TRANSIENT) != 0, "transient");
            Append(sb, (mod & VOLATILE) != 0, "volatile");
            Append(sb, (mod & SYNCHRONIZED) != 0, "synchronized");
            Append(sb, (mod & NATIVE) != 0, "native");
            Append(sb, (mod & STRICT) != 0, "strictfp");
            Append(sb, (mod & INTERFACE) != 0, "interface");
            return global::java.lang.String.Wrap(sb.ToString());
        }

        private static void Append(global::System.Text.StringBuilder sb, bool present, string word)
        {
            if (present)
            {
                if (sb.Length > 0)
                {
                    sb.Append(' ');
                }
                sb.Append(word);
            }
        }
    }
}
