namespace java.awt
{
    public interface LayoutManager
    {
        global::Avalonia.Controls.Panel J2csCreatePanel();

        void J2csAdd(global::Avalonia.Controls.Panel panel, global::Avalonia.Controls.Control child,
                global::java.lang.Object constraint);
    }
}
