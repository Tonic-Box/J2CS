namespace java.util
{
    public interface Queue : Collection
    {
        int offer(global::java.lang.Object e);

        global::java.lang.Object poll();

        global::java.lang.Object peek();
    }
}
