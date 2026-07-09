namespace java.awt
{
    public class Window : Container
    {
        internal global::Avalonia.Controls.Window AvWindow;
        internal int CloseOperation;
        internal Container __contentPane;
        internal global::Avalonia.Threading.DispatcherFrame __modalFrame;
        internal global::javax.swing.JMenuBar __menuBar;
        internal double __reqW = -1;
        internal double __reqH = -1;
        private bool __sizeCompensated;

        public void setTitle(global::java.lang.String title)
        {
            if (AvWindow != null)
            {
                AvWindow.Title = global::java.lang.JRuntime.Cs(title);
            }
        }

        public void setJMenuBar(global::javax.swing.JMenuBar menuBar)
        {
            __menuBar = menuBar;
        }

        public void pack()
        {
            if (AvWindow != null)
            {
                AvWindow.SizeToContent = global::Avalonia.Controls.SizeToContent.WidthAndHeight;
            }
        }

        public void toFront()
        {
            AvWindow?.Activate();
        }

        public void setMinimumSize(global::java.awt.Dimension d)
        {
            if (AvWindow != null && d != null)
            {
                AvWindow.MinWidth = d.W;
                AvWindow.MinHeight = d.H;
            }
        }

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
                global::Avalonia.Controls.Control content =
                    __contentPane != null ? __contentPane.AvControl : null;
                if (__menuBar != null && __menuBar.AvControl != null)
                {
                    var dock = new global::Avalonia.Controls.DockPanel();
                    global::Avalonia.Controls.DockPanel.SetDock(__menuBar.AvControl,
                        global::Avalonia.Controls.Dock.Top);
                    dock.Children.Add(__menuBar.AvControl);
                    if (content != null)
                    {
                        dock.Children.Add(content);
                    }
                    AvWindow.Content = dock;
                }
                else if (content != null)
                {
                    AvWindow.Content = content;
                }
                AvWindow.Show();
                J2csCompensateSize();
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
            // Swing's setSize is the OUTER window size; Avalonia's Width/Height set the client
            // (content) area. Remember the requested outer size and, once the window is shown and
            // its decorations are known, shrink the client so the outer frame matches Swing.
            __reqW = width;
            __reqH = height;
            if (AvWindow != null)
            {
                AvWindow.Width = width;
                AvWindow.Height = height;
            }
        }

        internal void J2csCompensateSize()
        {
            if (__sizeCompensated || AvWindow == null || __reqW <= 0)
            {
                return;
            }
            if (AvWindow.SizeToContent != global::Avalonia.Controls.SizeToContent.Manual)
            {
                return;
            }
            var frame = AvWindow.FrameSize;
            if (frame == null)
            {
                return;
            }
            double dw = frame.Value.Width - AvWindow.ClientSize.Width;
            double dh = frame.Value.Height - AvWindow.ClientSize.Height;
            if (dw > 0 || dh > 0)
            {
                AvWindow.Width = __reqW - dw;
                AvWindow.Height = __reqH - dh;
                __sizeCompensated = true;
            }
        }

        public Insets getInsets()
        {
            var ins = new Insets(global::java.lang.RawNew.I);
            if (AvWindow == null || AvWindow.FrameSize == null)
            {
                ins.__init_IIII_V(0, 0, 0, 0);
                return ins;
            }
            var frame = AvWindow.FrameSize.Value;
            int dw = (int)global::System.Math.Round(frame.Width - AvWindow.ClientSize.Width);
            int dh = (int)global::System.Math.Round(frame.Height - AvWindow.ClientSize.Height);
            int border = dw / 2;
            ins.__init_IIII_V(dh - border, border, border, border);
            return ins;
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

        public void addWindowListener(global::java.awt.@event.WindowListener l)
        {
            if (AvWindow != null && l != null)
            {
                AvWindow.Closed += (sender, e) =>
                {
                    l.windowClosing(new global::java.awt.@event.WindowEvent(global::java.lang.RawNew.I));
                    l.windowClosed(new global::java.awt.@event.WindowEvent(global::java.lang.RawNew.I));
                };
            }
        }
    }
}
