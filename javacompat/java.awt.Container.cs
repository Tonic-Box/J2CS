namespace java.awt
{
    public class Container : Component
    {
        internal static readonly global::Avalonia.Media.IBrush DefaultPanelBg =
            new global::Avalonia.Media.SolidColorBrush(global::Avalonia.Media.Color.FromRgb(0xEE, 0xEE, 0xEE));

        internal global::Avalonia.Controls.Panel AvPanel;
        private LayoutManager layout;
        private global::javax.swing.border.Border border;
        private global::Avalonia.Media.IBrush bgBrush = DefaultPanelBg;

        public Container(global::java.lang.RawNew r) : base(r)
        {
        }

        private void EnsurePanel()
        {
            if (AvPanel == null)
            {
                AvPanel = new global::java.awt.J2csFlowPanel();
                AvPanel.Background = bgBrush;
                RebuildControl();
            }
        }

        private void RebuildControl()
        {
            if (AvPanel == null)
            {
                return;
            }
            AvControl = border != null && border.BKind != global::javax.swing.border.Border.Kind.Empty
                    ? global::java.awt.J2csBorders.Wrap(AvPanel, border, bgBrush)
                    : AvPanel;
        }

        public virtual void setLayout(LayoutManager lm)
        {
            layout = lm;
            AvPanel = lm != null
                    ? lm.J2csCreatePanel()
                    : new global::Avalonia.Controls.Canvas();
            AvPanel.Background = bgBrush;
            RebuildControl();
        }

        public override void setBackground(global::java.awt.Color c)
        {
            if (c == null)
            {
                return;
            }
            bgBrush = c.Brush;
            if (AvPanel != null)
            {
                AvPanel.Background = c.Brush;
            }
            else if (AvControl is global::java.awt.J2csPaintSurface ps)
            {
                ps.Bg = c.Brush;
                ps.InvalidateVisual();
            }
        }

        public virtual global::java.awt.Component add(global::java.awt.Component comp)
        {
            EnsurePanel();
            if (comp != null && comp.AvControl != null)
            {
                if (layout != null)
                {
                    layout.J2csAdd(AvPanel, comp.AvControl, null);
                }
                else
                {
                    AvPanel.Children.Add(comp.AvControl);
                }
            }
            return comp;
        }

        public void setBorder(global::javax.swing.border.Border border)
        {
            EnsurePanel();
            this.border = border;
            if (border != null && border.BKind == global::javax.swing.border.Border.Kind.Empty)
            {
                var insets = new global::Avalonia.Thickness(border.Left, border.Top, border.Right, border.Bottom);
                if (AvPanel is global::java.awt.J2csPanel padded)
                {
                    padded.J2csInsets = insets;
                    padded.InvalidateMeasure();
                }
                else
                {
                    AvPanel.Margin = insets;
                }
            }
            RebuildControl();
        }

        public virtual void add(global::java.awt.Component comp, global::java.lang.Object constraint)
        {
            EnsurePanel();
            if (comp != null && comp.AvControl != null)
            {
                if (layout != null)
                {
                    layout.J2csAdd(AvPanel, comp.AvControl, constraint);
                }
                else
                {
                    AvPanel.Children.Add(comp.AvControl);
                }
            }
        }
    }
}
