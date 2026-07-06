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

        public virtual void setVisible(int visible)
        {
            if (AvControl != null)
            {
                AvControl.IsVisible = visible != 0;
            }
        }

        public void setBounds(int x, int y, int width, int height)
        {
            if (AvControl != null)
            {
                global::Avalonia.Controls.Canvas.SetLeft(AvControl, x);
                global::Avalonia.Controls.Canvas.SetTop(AvControl, y);
                AvControl.Width = width;
                AvControl.Height = height;
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

        public int requestFocusInWindow()
        {
            if (AvControl != null)
            {
                AvControl.Focus();
            }
            return 1;
        }

        public virtual void setBackground(global::java.awt.Color c)
        {
            if (c == null)
            {
                return;
            }
            if (AvControl is global::java.awt.J2csPaintSurface ps) { ps.Bg = c.Brush; ps.InvalidateVisual(); }
            else if (AvControl is global::Avalonia.Controls.Panel p) { p.Background = c.Brush; }
            else if (AvControl is global::Avalonia.Controls.Border b) { b.Background = c.Brush; }
            else if (AvControl is global::Avalonia.Controls.Primitives.TemplatedControl tc) { tc.Background = c.Brush; }
        }

        public virtual void setForeground(global::java.awt.Color c)
        {
            if (c == null)
            {
                return;
            }
            if (AvControl is global::Avalonia.Controls.TextBlock tb) { tb.Foreground = c.Brush; }
            else if (AvControl is global::Avalonia.Controls.Primitives.TemplatedControl tc) { tc.Foreground = c.Brush; }
        }

        public virtual void setFont(global::java.awt.Font f)
        {
            if (f == null || AvControl == null)
            {
                return;
            }
            if (AvControl is global::Avalonia.Controls.TextBlock tb) { tb.FontSize = f.Size; }
            else if (AvControl is global::Avalonia.Controls.Primitives.TemplatedControl tc) { tc.FontSize = f.Size; }
        }

        public void setOpaque(int opaque)
        {
        }

        public void setDoubleBuffered(int enabled)
        {
        }

        public void registerKeyboardAction(global::java.awt.@event.ActionListener l,
            global::javax.swing.KeyStroke keyStroke, int condition)
        {
        }

        public void putClientProperty(global::java.lang.Object key, global::java.lang.Object value)
        {
        }

        public void setBorderPainted(int painted)
        {
        }

        public void setFocusPainted(int painted)
        {
        }

        public void setContentAreaFilled(int filled)
        {
        }

        public void setFocusable(int focusable)
        {
            if (AvControl != null)
            {
                AvControl.Focusable = focusable != 0;
            }
        }

        public void setToolTipText(global::java.lang.String text)
        {
            if (AvControl != null)
            {
                global::Avalonia.Controls.ToolTip.SetTip(AvControl, global::java.lang.JRuntime.Cs(text));
            }
        }

        public void repaint()
        {
            AvControl?.InvalidateVisual();
        }

        public void revalidate()
        {
            AvControl?.InvalidateMeasure();
        }


        private static global::java.awt.@event.MouseEvent MouseEventAt(
            global::Avalonia.Input.PointerEventArgs e, global::Avalonia.Controls.Control control, int clicks)
        {
            var pos = e.GetPosition(control);
            var me = new global::java.awt.@event.MouseEvent(global::java.lang.RawNew.I);
            me.X = (int)pos.X;
            me.Y = (int)pos.Y;
            me.Button = 1;
            me.ClickCount = clicks;
            var mods = e.KeyModifiers;
            me.Shift = (mods & global::Avalonia.Input.KeyModifiers.Shift) != 0;
            me.Control = (mods & global::Avalonia.Input.KeyModifiers.Control) != 0;
            me.Alt = (mods & global::Avalonia.Input.KeyModifiers.Alt) != 0;
            me.Meta = (mods & global::Avalonia.Input.KeyModifiers.Meta) != 0;
            return me;
        }

        public void addMouseListener(global::java.awt.@event.MouseListener l)
        {
            if (AvControl == null || l == null)
            {
                return;
            }
            AvControl.PointerPressed += (s, e) => l.mousePressed(MouseEventAt(e, AvControl, 1));
            AvControl.PointerReleased += (s, e) =>
            {
                l.mouseReleased(MouseEventAt(e, AvControl, 1));
                l.mouseClicked(MouseEventAt(e, AvControl, 1));
            };
            AvControl.PointerEntered += (s, e) => l.mouseEntered(MouseEventAt(e, AvControl, 0));
            AvControl.PointerExited += (s, e) => l.mouseExited(MouseEventAt(e, AvControl, 0));
        }

        public void addKeyListener(global::java.awt.@event.KeyListener l)
        {
            if (AvControl == null || l == null)
            {
                return;
            }
            AvControl.KeyDown += (s, e) => l.keyPressed(new global::java.awt.@event.KeyEvent(global::java.lang.RawNew.I));
            AvControl.KeyUp += (s, e) => l.keyReleased(new global::java.awt.@event.KeyEvent(global::java.lang.RawNew.I));
        }

        public void addMouseMotionListener(global::java.awt.@event.MouseMotionListener l)
        {
            if (AvControl == null || l == null)
            {
                return;
            }
            AvControl.PointerMoved += (s, e) =>
            {
                var me = MouseEventAt(e, AvControl, 0);
                if (e.GetCurrentPoint(AvControl).Properties.IsLeftButtonPressed)
                {
                    l.mouseDragged(me);
                }
                else
                {
                    l.mouseMoved(me);
                }
            };
        }
    }
}
