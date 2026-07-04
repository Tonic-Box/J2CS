namespace java.time.format
{
    public class DateTimeFormatter : global::java.lang.Object
    {
        internal readonly string NetPattern;

        public DateTimeFormatter(global::java.lang.RawNew r) : base(r)
        {
            NetPattern = "yyyy-MM-dd HH:mm:ss";
        }

        private DateTimeFormatter(string net) : base(global::java.lang.RawNew.I)
        {
            NetPattern = net;
        }

        public static DateTimeFormatter ofPattern(global::java.lang.String pattern)
        {
            return new DateTimeFormatter(pattern == null ? "yyyy-MM-dd HH:mm:ss" : pattern.Value);
        }
    }
}
