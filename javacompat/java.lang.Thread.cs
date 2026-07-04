namespace java.lang
{
    public class Thread : Object
    {
        private static readonly Thread current = new Thread(RawNew.I);

        public Thread(RawNew r) : base(r)
        {
        }

        public static Thread currentThread()
        {
            return current;
        }

        public void interrupt()
        {
        }
    }
}
