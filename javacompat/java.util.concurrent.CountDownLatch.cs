namespace java.util.concurrent
{
    public class CountDownLatch : global::java.lang.Object
    {
        private global::System.Threading.CountdownEvent ev;

        public CountDownLatch(global::java.lang.RawNew r) : base(r) { }
        public void __init_I_V(int count) { ev = new global::System.Threading.CountdownEvent(count); }

        public void countDown() { if (ev.CurrentCount > 0) { ev.Signal(); } }
        public long getCount() { return ev.CurrentCount; }
        public void await() { ev.Wait(); }
        public int await(long timeout, TimeUnit unit) { return ev.Wait((int)unit.toMillis(timeout)) ? 1 : 0; }
    }
}
