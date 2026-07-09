namespace java.util
{
    public class Properties : HashMap
    {
        public Properties(global::java.lang.RawNew r) : base(r) { }

        public global::java.lang.Object setProperty(global::java.lang.String key, global::java.lang.String value) { return put(key, value); }

        public global::java.lang.String getProperty(global::java.lang.String key)
        {
            return get(key) as global::java.lang.String;
        }

        public global::java.lang.String getProperty(global::java.lang.String key, global::java.lang.String defaultValue)
        {
            var v = get(key);
            return v != null ? (global::java.lang.String)v : defaultValue;
        }

        public Set stringPropertyNames() { return keySet(); }
    }
}
