namespace javax.swing
{
    public class JFrame : global::java.awt.Frame
    {
        public JFrame(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
            AvWindow = new global::Avalonia.Controls.Window();
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String title)
        {
            AvWindow = new global::Avalonia.Controls.Window();
            AvWindow.Title = title == null ? "" : title.Value;
        }
    }
}
