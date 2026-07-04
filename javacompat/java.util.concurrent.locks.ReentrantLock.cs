namespace java.util.concurrent.locks
{
    public class ReentrantLock : global::java.lang.Object, Lock
    {
        private readonly object gate = new object();

        public ReentrantLock(global::java.lang.RawNew r) : base(r)
        {
        }

        public void @lock()
        {
            global::System.Threading.Monitor.Enter(gate);
        }

        public void unlock()
        {
            global::System.Threading.Monitor.Exit(gate);
        }
    }
}
