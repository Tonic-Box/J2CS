namespace java.util.concurrent
{
    public interface BlockingQueue : global::java.util.Queue
    {
        void put(global::java.lang.Object e);
        global::java.lang.Object take();
        int remainingCapacity();
    }
}
