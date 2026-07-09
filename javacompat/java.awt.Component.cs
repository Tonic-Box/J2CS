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

        public void setName(global::java.lang.String name)
        {
            if (AvControl != null && name != null)
            {
                // Prefix so the visual-fidelity harness can tell app-tagged controls apart from
                // Fluent template parts (which also carry Names).
                AvControl.Name = "j2cs_" + global::java.lang.JRuntime.Cs(name);
            }
        }

        public global::java.lang.String getName()
        {
            if (AvControl == null || AvControl.Name == null)
            {
                return null;
            }
            var n = AvControl.Name;
            return global::java.lang.String.Wrap(n.StartsWith("j2cs_") ? n.Substring(5) : n);
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
            if (AvControl == null || d == null)
            {
                return;
            }
            // A 0 dimension means "no preference" (e.g. a BorderLayout child that fills). For leaf
            // widgets (buttons, sliders, bars) preferredSize is effectively the size, so set it
            // exactly for a Java-like uniform look; for containers/panels it is a hint the layout
            // may exceed, so set a minimum and let them fill.
            bool container = AvControl is global::Avalonia.Controls.Panel
                    || AvControl is global::Avalonia.Controls.Border
                    || AvControl is global::java.awt.J2csPaintSurface;
            if (d.W > 0)
            {
                if (container) { AvControl.MinWidth = d.W; } else { AvControl.Width = d.W; }
            }
            if (d.H > 0)
            {
                if (container) { AvControl.MinHeight = d.H; } else { AvControl.Height = d.H; }
            }
        }

        public int getWidth()
        {
            return AvControl == null ? 0 : (int)AvControl.Bounds.Width;
        }

        public int getHeight()
        {
            return AvControl == null ? 0 : (int)AvControl.Bounds.Height;
        }

        public int getX()
        {
            return AvControl == null ? 0 : (int)AvControl.Bounds.X;
        }

        public int getY()
        {
            return AvControl == null ? 0 : (int)AvControl.Bounds.Y;
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
            else if (AvControl is global::Avalonia.Controls.TextBlock tb) { tb.Background = c.Brush; }
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
            var weight = (f.Style & 1) != 0
                    ? global::Avalonia.Media.FontWeight.Bold
                    : global::Avalonia.Media.FontWeight.Normal;
            var style = (f.Style & 2) != 0
                    ? global::Avalonia.Media.FontStyle.Italic
                    : global::Avalonia.Media.FontStyle.Normal;
            var family = f.CsFamily();
            if (AvControl is global::Avalonia.Controls.TextBlock tb)
            {
                tb.FontSize = f.PxSize();
                tb.FontWeight = weight;
                tb.FontStyle = style;
                tb.FontFamily = family;
            }
            else if (AvControl is global::Avalonia.Controls.Primitives.TemplatedControl tc)
            {
                tc.FontSize = f.PxSize();
                tc.FontWeight = weight;
                tc.FontStyle = style;
                tc.FontFamily = family;
            }
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

        public void firePropertyChange(global::java.lang.String propertyName,
            global::java.lang.Object oldValue, global::java.lang.Object newValue)
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

        private static global::java.awt.@event.KeyEvent KeyEventFrom(
            global::Avalonia.Input.KeyEventArgs e, int id)
        {
            var ke = new global::java.awt.@event.KeyEvent(global::java.lang.RawNew.I);
            ke.Id = id;
            var mods = e.KeyModifiers;
            ke.Shift = (mods & global::Avalonia.Input.KeyModifiers.Shift) != 0;
            ke.Control = (mods & global::Avalonia.Input.KeyModifiers.Control) != 0;
            ke.Alt = (mods & global::Avalonia.Input.KeyModifiers.Alt) != 0;
            ke.Meta = (mods & global::Avalonia.Input.KeyModifiers.Meta) != 0;
            ke.KeyCode = (int)e.Key;
            return ke;
        }

        public void addKeyListener(global::java.awt.@event.KeyListener l)
        {
            if (AvControl == null || l == null)
            {
                return;
            }
            AvControl.KeyDown += (s, e) => l.keyPressed(KeyEventFrom(e, global::java.awt.@event.KeyEvent.KEY_PRESSED));
            AvControl.KeyUp += (s, e) => l.keyReleased(KeyEventFrom(e, global::java.awt.@event.KeyEvent.KEY_RELEASED));
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
