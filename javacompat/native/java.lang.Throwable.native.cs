namespace java.lang
{
    partial class Throwable
    {
        internal global::System.Exception __origin;

        protected string JavaClassName;

        public void __init__V()
        {
            cause = this;
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String p0)
        {
            cause = this;
            detailMessage = p0;
        }

        public void __init_Ljava_lang_String_Ljava_lang_Throwable__V(
                global::java.lang.String p0, global::java.lang.Throwable p1)
        {
            cause = p1;
            detailMessage = p0;
        }

        public void __init_Ljava_lang_Throwable__V(global::java.lang.Throwable p0)
        {
            cause = p0;
            detailMessage = p0 == null ? null : p0.toString();
        }

        public void __init_Ljava_lang_String_Ljava_lang_Throwable_ZZ_V(
                global::java.lang.String p0, global::java.lang.Throwable p1, int p2, int p3)
        {
            cause = p1;
            detailMessage = p0;
        }

        public virtual global::java.lang.Throwable fillInStackTrace()
        {
            return this;
        }

        public override global::java.lang.String toString()
        {
            string n = GetType().FullName;
            if (n.StartsWith("jdefault."))
            {
                n = n.Substring(9);
            }
            return String.Wrap(detailMessage == null ? n : n + ": " + detailMessage.Value);
        }
    }
}
