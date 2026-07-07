namespace javax.swing
{
    /// <summary>Minimal JFileChooser: exists so allocation/dialog calls transpile. Avalonia's file
    /// picker is async and not wired here, so the dialogs report CANCEL (no selection).</summary>
    public class JFileChooser : global::java.awt.Component
    {
        public const int CANCEL_OPTION = 1;
        public const int APPROVE_OPTION = 0;
        public const int ERROR_OPTION = -1;

        public JFileChooser(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V()
        {
        }

        public int showOpenDialog(global::java.awt.Component parent)
        {
            return CANCEL_OPTION;
        }

        public int showSaveDialog(global::java.awt.Component parent)
        {
            return CANCEL_OPTION;
        }

        public void setDialogTitle(global::java.lang.String title)
        {
        }
    }
}
