namespace java.util
{
    public interface List : Collection
    {
        global::java.lang.Object get(int index);

        global::java.lang.Object set(int index, global::java.lang.Object element);

        void add(int index, global::java.lang.Object element);

        global::java.lang.Object remove(int index);

        int indexOf(global::java.lang.Object o);
    }
}
