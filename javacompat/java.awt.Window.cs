namespace java.awt
{
    public class Window : Container
    {
        internal global::Avalonia.Controls.Window AvWindow;
        internal int CloseOperation;

        public Window(global::java.lang.RawNew r) : base(r)
        {
        }

        public void setVisible(int visible)
        {
            if (AvWindow == null)
            {
                return;
            }
            if (visible != 0)
            {
                AvWindow.Show();
            }
            else
            {
                AvWindow.Hide();
            }
        }

        public void dispose()
        {
            if (AvWindow != null)
            {
                AvWindow.Close();
            }
        }

        public void setSize(int width, int height)
        {
            if (AvWindow != null)
            {
                AvWindow.Width = width;
                AvWindow.Height = height;
            }
        }

        public void setResizable(int resizable)
        {
            if (AvWindow != null)
            {
                AvWindow.CanResize = resizable != 0;
            }
        }

        public void setLocationRelativeTo(Component c)
        {
            if (AvWindow != null)
            {
                AvWindow.WindowStartupLocation = global::Avalonia.Controls.WindowStartupLocation.CenterScreen;
            }
        }

        public void setDefaultCloseOperation(int operation)
        {
            CloseOperation = operation;
        }

        public Container getParent()
        {
            return null;
        }
    }
}
