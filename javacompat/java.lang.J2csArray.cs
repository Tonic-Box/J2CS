namespace java.lang
{
    /// <summary>
    /// Boxes a native C# array as a java.lang.Object so Java arrays can flow through Object slots,
    /// collections, and reflection. Identity is preserved by JRuntime's box cache (one wrapper per
    /// underlying array), so == and collection membership keep Java array semantics (default
    /// identity equals/hashCode inherited from Object). Desc is the JVM array descriptor (e.g. "[I").
    /// </summary>
    internal sealed class J2csArray : global::java.lang.Object
    {
        internal readonly global::System.Array Value;
        internal readonly string Desc;

        internal J2csArray(global::System.Array value, string desc) : base(global::java.lang.RawNew.I)
        {
            Value = value;
            Desc = desc;
        }

        public override global::java.lang.String toString()
        {
            return global::java.lang.String.Wrap(Desc + "@"
                    + global::System.Convert.ToString(hashCode(), 16));
        }
    }
}
