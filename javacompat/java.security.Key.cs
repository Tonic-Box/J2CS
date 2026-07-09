namespace java.security
{
    public interface Key
    {
        global::java.lang.String getAlgorithm();
        sbyte[] getEncoded();
        global::java.lang.String getFormat();
    }
}
