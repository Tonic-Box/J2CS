namespace java.lang
{
    public class AssertionError : Error
    {
        public AssertionError(RawNew r) : base(r)
        {
            JavaClassName = "java.lang.AssertionError";
        }

        public void __init__V()
        {
        }

        public void __init_Ljava_lang_Object__V(Object detail)
        {
            __init_Ljava_lang_String__V(detail == null ? null : detail.toString());
        }

        public void __init_Z_V(int detail)
        {
            __init_Ljava_lang_String__V(String.Wrap(detail != 0 ? "true" : "false"));
        }

        public void __init_C_V(char detail)
        {
            __init_Ljava_lang_String__V(String.valueOf(detail));
        }

        public void __init_I_V(int detail)
        {
            __init_Ljava_lang_String__V(String.valueOf(detail));
        }

        public void __init_J_V(long detail)
        {
            __init_Ljava_lang_String__V(String.valueOf(detail));
        }

        public void __init_F_V(float detail)
        {
            __init_Ljava_lang_String__V(String.valueOf(detail));
        }

        public void __init_D_V(double detail)
        {
            __init_Ljava_lang_String__V(String.valueOf(detail));
        }
    }
}
