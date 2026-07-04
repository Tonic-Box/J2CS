namespace javax.swing
{
    public class JScrollPane : global::java.awt.Component
    {
        public JScrollPane(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init_Ljava_awt_Component__V(global::java.awt.Component view)
        {
            AvControl = new global::Avalonia.Controls.ScrollViewer
            {
                Content = view == null ? null : view.AvControl
            };
        }
    }
}
