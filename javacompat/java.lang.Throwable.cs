namespace java.lang
{
    public class Throwable : Object
    {
        protected string JavaClassName = "java.lang.Throwable";
        internal global::System.Exception __origin;
        private String message;

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

        public virtual String getMessage()
        {
            return message;
        }

        public override String toString()
        {
            return String.Wrap(message == null ? JavaClassName : JavaClassName + ": " + message.Value);
        }
    }
}
