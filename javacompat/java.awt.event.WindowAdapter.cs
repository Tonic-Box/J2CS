namespace java.awt.@event
{
    public class WindowAdapter : global::java.lang.Object, WindowListener
    {
        public WindowAdapter(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V() { }

        public virtual void windowOpened(WindowEvent e) { }
        public virtual void windowClosing(WindowEvent e) { }
        public virtual void windowClosed(WindowEvent e) { }
        public virtual void windowIconified(WindowEvent e) { }
        public virtual void windowDeiconified(WindowEvent e) { }
        public virtual void windowActivated(WindowEvent e) { }
        public virtual void windowDeactivated(WindowEvent e) { }
    }
}
