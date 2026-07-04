namespace java.awt
{
    public class BorderLayout : global::java.lang.Object, LayoutManager
    {
        public BorderLayout(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public global::Avalonia.Controls.Panel J2csCreatePanel()
        {
            return new global::Avalonia.Controls.DockPanel { LastChildFill = true };
        }

        public void J2csAdd(global::Avalonia.Controls.Panel panel, global::Avalonia.Controls.Control child,
                global::java.lang.Object constraint)
        {
            string region = constraint == null ? "Center" : global::java.lang.JRuntime.Str(constraint);
            switch (region)
            {
                case "North":
                    global::Avalonia.Controls.DockPanel.SetDock(child, global::Avalonia.Controls.Dock.Top);
                    break;
                case "South":
                    global::Avalonia.Controls.DockPanel.SetDock(child, global::Avalonia.Controls.Dock.Bottom);
                    break;
                case "East":
                    global::Avalonia.Controls.DockPanel.SetDock(child, global::Avalonia.Controls.Dock.Right);
                    break;
                case "West":
                    global::Avalonia.Controls.DockPanel.SetDock(child, global::Avalonia.Controls.Dock.Left);
                    break;
                default:
                    break;
            }
            panel.Children.Add(child);
        }
    }
}
