namespace java.util
{
    public interface Enumeration
    {
        int hasMoreElements();

        global::java.lang.Object nextElement();
    }

    /// <summary>An enumeration with no elements — the transpiled world has no classpath resources.</summary>
    internal sealed class EmptyEnumeration : global::java.lang.Object, Enumeration
    {
        internal static readonly EmptyEnumeration Instance = new EmptyEnumeration();

        private EmptyEnumeration() : base(global::java.lang.RawNew.I)
        {
        }

        public int hasMoreElements()
        {
            return 0;
        }

        public global::java.lang.Object nextElement()
        {
            throw global::java.lang.JThrow.of(new global::java.lang.IllegalStateException(global::java.lang.RawNew.I));
        }
    }
}
