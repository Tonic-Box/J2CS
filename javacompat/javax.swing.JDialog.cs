namespace javax.swing
{
    public class JDialog : global::java.awt.Window
    {
        internal bool Modal;

        public JDialog(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_awt_Frame_Ljava_lang_String_Z_V(
                global::java.awt.Frame owner, global::java.lang.String title, int modal)
        {
            AvWindow = new global::Avalonia.Controls.Window();
            AvWindow.Title = title == null ? "" : title.Value;
            Modal = modal != 0;
        }
    }
}
