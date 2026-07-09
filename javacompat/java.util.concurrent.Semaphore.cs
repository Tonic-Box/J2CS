namespace java.util.concurrent
{
    public class Semaphore : global::java.lang.Object
    {
        private global::System.Threading.SemaphoreSlim s;

        public Semaphore(global::java.lang.RawNew r) : base(r) { }
        public void __init_I_V(int permits) { s = new global::System.Threading.SemaphoreSlim(permits); }
        public void __init_IZ_V(int permits, int fair) { s = new global::System.Threading.SemaphoreSlim(permits); }

        public void acquire() { s.Wait(); }
        public void acquire(int permits) { for (int i = 0; i < permits; i++) { s.Wait(); } }
        public void release() { s.Release(); }
        public void release(int permits) { s.Release(permits); }
        public int tryAcquire() { return s.Wait(0) ? 1 : 0; }
        public int tryAcquire(long timeout, TimeUnit unit) { return s.Wait((int)unit.toMillis(timeout)) ? 1 : 0; }
        public int availablePermits() { return s.CurrentCount; }
    }
}
