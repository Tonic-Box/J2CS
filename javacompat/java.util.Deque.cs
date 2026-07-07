namespace java.util
{
    public interface Deque : Queue
    {
        void push(global::java.lang.Object e);

        global::java.lang.Object pop();

        void addFirst(global::java.lang.Object e);

        void addLast(global::java.lang.Object e);

        int offerFirst(global::java.lang.Object e);

        int offerLast(global::java.lang.Object e);

        global::java.lang.Object pollFirst();

        global::java.lang.Object pollLast();

        global::java.lang.Object peekFirst();

        global::java.lang.Object peekLast();

        global::java.lang.Object getFirst();

        global::java.lang.Object getLast();

        global::java.lang.Object removeFirst();

        global::java.lang.Object removeLast();
    }
}
