namespace java.util
{
    public interface Map
    {
        global::java.lang.Object put(global::java.lang.Object key, global::java.lang.Object value);

        global::java.lang.Object get(global::java.lang.Object key);

        int containsKey(global::java.lang.Object key);

        global::java.lang.Object remove(global::java.lang.Object key);

        int size();

        int isEmpty();

        Set keySet();

        Collection values();

        Set entrySet();
    }
}
