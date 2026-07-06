namespace java.lang
{
    public class Throwable : Object
    {
        protected string JavaClassName = "java.lang.Throwable";
        internal global::System.Exception __origin;
        private String message;
        private Throwable cause;

        public Throwable(RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void __init_Ljava_lang_String__V(String p0)
        {
            message = p0;
        }

        public void __init_Ljava_lang_Throwable__V(Throwable p0)
        {
            cause = p0;
            message = p0 == null ? null : p0.toString();
        }

        public void __init_Ljava_lang_String_Ljava_lang_Throwable__V(String p0, Throwable p1)
        {
            message = p0;
            cause = p1;
        }

        public virtual String getMessage()
        {
            return message;
        }

        public virtual Throwable getCause()
        {
            return cause;
        }

        public override String toString()
        {
            return String.Wrap(message == null ? JavaClassName : JavaClassName + ": " + message.Value);
        }

        public virtual void printStackTrace()
        {
            global::System.Console.Error.WriteLine(toString().Value);
        }
    }
}
