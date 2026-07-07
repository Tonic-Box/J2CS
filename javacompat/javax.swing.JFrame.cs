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
            AvWindow.Background = global::java.awt.J2csTheme.MetalGray;
            J2csWireCloseExit();
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String title)
        {
            AvWindow = new global::Avalonia.Controls.Window();
            AvWindow.Background = global::java.awt.J2csTheme.MetalGray;
            AvWindow.Title = global::java.lang.JRuntime.Cs(title);
            J2csWireCloseExit();
        }
    }
}
