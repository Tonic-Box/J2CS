namespace java.awt
{
    public class Window : Container
    {
        internal global::Avalonia.Controls.Window AvWindow;
        internal int CloseOperation;
        internal Container __contentPane;
        internal global::Avalonia.Threading.DispatcherFrame __modalFrame;

        internal void J2csEndModal()
        {
            if (__modalFrame != null)
            {
                __modalFrame.Continue = false;
            }
        }

        public Window(global::java.lang.RawNew r) : base(r)
        {
        }

        public Container getContentPane()
        {
            if (__contentPane == null)
            {
                __contentPane = new Container(global::java.lang.RawNew.I);
                var bl = new global::java.awt.BorderLayout(global::java.lang.RawNew.I);
                bl.__init__V();
                __contentPane.setLayout(bl);
            }
            return __contentPane;
        }

        public void setContentPane(Container pane)
        {
            __contentPane = pane;
        }

        public override void setLayout(LayoutManager lm)
        {
            getContentPane().setLayout(lm);
        }

        public override Component add(Component comp)
        {
            return getContentPane().add(comp);
        }

        public override void add(Component comp, global::java.lang.Object constraint)
        {
            getContentPane().add(comp, constraint);
        }

        public override void setVisible(int visible)
        {
            if (AvWindow == null)
            {
                return;
            }
            if (visible != 0)
            {
                if (__contentPane != null && __contentPane.AvControl != null)
                {
                    AvWindow.Content = __contentPane.AvControl;
                }
                AvWindow.Show();
            }
            else
            {
                J2csEndModal();
                AvWindow.Hide();
            }
        }

        public void dispose()
        {
            J2csEndModal();
            if (AvWindow != null)
            {
                AvWindow.Hide();
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

        internal void J2csWireCloseExit()
        {
            if (AvWindow != null)
            {
                AvWindow.Closed += (sender, e) =>
                {
                    J2csEndModal();
                    if (CloseOperation == 3)
                    {
                        global::System.Environment.Exit(0);
                    }
                };
            }
        }

        public Container getParent()
        {
            return null;
        }
    }
}
