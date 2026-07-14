namespace java.util.concurrent
{
    /// <summary>
    /// Thrown by Future.get when the computation threw. The synchronous executor surfaces failures at
    /// submit time so this is rarely raised, but callers catch it around get() and it must be a real
    /// (extendable) shim type for those handlers to compile.
    /// </summary>
    public class ExecutionException : global::java.lang.Exception
    {
        public ExecutionException(global::java.lang.RawNew r) : base(r)
        {
            JavaClassName = "java.util.concurrent.ExecutionException";
        }
    }
}
