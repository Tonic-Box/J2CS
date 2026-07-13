namespace java.util.concurrent.locks
{
    public class StampedLock : global::java.lang.Object
    {
        private readonly object gate = new object();
        private long stamp;

        public StampedLock(global::java.lang.RawNew r) : base(r)
        {
        }

        public long writeLock()
        {
            global::System.Threading.Monitor.Enter(gate);
            return ++stamp;
        }

        public void unlockWrite(long stampValue)
        {
            global::System.Threading.Monitor.Exit(gate);
        }
    }
}
