namespace java.awt
{
    public class Component : global::java.lang.Object
    {
        internal global::Avalonia.Controls.Control AvControl;

        public Component(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public void setEnabled(int enabled)
        {
            if (AvControl != null)
            {
                AvControl.IsEnabled = enabled != 0;
            }
        }

        public void setVisible(int visible)
        {
            if (AvControl != null)
            {
                AvControl.IsVisible = visible != 0;
            }
        }

        public void setPreferredSize(global::java.awt.Dimension d)
        {
            if (AvControl != null && d != null)
            {
                AvControl.Width = d.W;
                AvControl.Height = d.H;
            }
        }

        public void requestFocusInWindow()
        {
            if (AvControl != null)
            {
                AvControl.Focus();
            }
        }
    }
}
