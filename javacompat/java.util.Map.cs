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

        void clear();

        global::java.lang.Object getOrDefault(global::java.lang.Object key, global::java.lang.Object defaultValue)
        {
            return containsKey(key) != 0 ? get(key) : defaultValue;
        }

        global::java.lang.Object putIfAbsent(global::java.lang.Object key, global::java.lang.Object value)
        {
            var v = get(key);
            if (v == null)
            {
                v = put(key, value);
            }
            return v;
        }

        global::java.lang.Object computeIfAbsent(global::java.lang.Object key,
                global::java.util.function.Function mappingFunction)
        {
            var v = get(key);
            if (v == null)
            {
                var nv = mappingFunction.apply(key);
                if (nv != null)
                {
                    put(key, nv);
                    return nv;
                }
            }
            return v;
        }

        global::java.lang.Object computeIfPresent(global::java.lang.Object key,
                global::java.util.function.BiFunction remappingFunction)
        {
            var old = get(key);
            if (old == null)
            {
                return null;
            }
            var nv = remappingFunction.apply(key, old);
            if (nv != null)
            {
                put(key, nv);
                return nv;
            }
            remove(key);
            return null;
        }

        global::java.lang.Object compute(global::java.lang.Object key,
                global::java.util.function.BiFunction remappingFunction)
        {
            var old = get(key);
            var nv = remappingFunction.apply(key, old);
            if (nv == null)
            {
                if (old != null || containsKey(key) != 0)
                {
                    remove(key);
                }
                return null;
            }
            put(key, nv);
            return nv;
        }

        global::java.lang.Object merge(global::java.lang.Object key, global::java.lang.Object value,
                global::java.util.function.BiFunction remappingFunction)
        {
            var old = get(key);
            var nv = old == null ? value : remappingFunction.apply(old, value);
            if (nv == null)
            {
                remove(key);
            }
            else
            {
                put(key, nv);
            }
            return nv;
        }

        global::java.lang.Object replace(global::java.lang.Object key, global::java.lang.Object value)
        {
            if (containsKey(key) != 0)
            {
                return put(key, value);
            }
            return null;
        }

        void forEach(global::java.util.function.BiConsumer action)
        {
            var it = entrySet().iterator();
            while (it.hasNext() != 0)
            {
                var e = (Map_S_Entry)it.next();
                action.accept(e.getKey(), e.getValue());
            }
        }
    }
}
