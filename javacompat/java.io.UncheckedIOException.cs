namespace java.io
{
    public class UncheckedIOException : global::java.lang.RuntimeException
    {
        public UncheckedIOException(global::java.lang.RawNew r) : base(r)
        {
            JavaClassName = "java.io.UncheckedIOException";
        }

        public void __init_Ljava_io_IOException__V(global::java.io.IOException cause)
        {
            __init_Ljava_lang_Throwable__V(cause);
        }

        public void __init_Ljava_lang_String_Ljava_io_IOException__V(global::java.lang.String message, global::java.io.IOException cause)
        {
            __init_Ljava_lang_String_Ljava_lang_Throwable__V(message, cause);
        }
    }
}
