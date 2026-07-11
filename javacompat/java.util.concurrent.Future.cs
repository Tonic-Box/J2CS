namespace java.util.concurrent
{
    public interface Future
    {
        global::java.lang.Object get();

        global::java.lang.Object get(long timeout, global::java.util.concurrent.TimeUnit unit);

        int isDone();

        int isCancelled();

        int cancel(int mayInterrupt);
    }
}
