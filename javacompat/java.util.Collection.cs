namespace java.util
{
    public interface Collection : Iterable
    {
        int add(global::java.lang.Object e);

        int size();

        int isEmpty();

        int contains(global::java.lang.Object o);

        int remove(global::java.lang.Object o);

        global::java.util.stream.Stream stream()
        {
            var list = new global::System.Collections.Generic.List<global::java.lang.Object>();
            var it = iterator();
            while (it.hasNext() != 0)
            {
                list.Add(it.next());
            }
            return global::java.util.stream.Stream.Wrap(list);
        }

        global::java.util.stream.Stream parallelStream()
        {
            return stream();
        }
    }
}
