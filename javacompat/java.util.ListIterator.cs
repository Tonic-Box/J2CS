namespace java.util
{
    public interface ListIterator : Iterator
    {
        int hasPrevious();
        global::java.lang.Object previous();
        int nextIndex();
        int previousIndex();
        void set(global::java.lang.Object e);
        void add(global::java.lang.Object e);
        void remove();
    }
}
