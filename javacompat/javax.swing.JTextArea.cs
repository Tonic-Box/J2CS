namespace javax.swing
{
    public class JTextArea : global::java.awt.Component
    {
        private global::Avalonia.Controls.TextBox box;

        public JTextArea(global::java.lang.RawNew r) : base(r)
        {
        }

        private void Ensure()
        {
            if (box == null)
            {
                box = new global::Avalonia.Controls.TextBox
                {
                    AcceptsReturn = true,
                    TextWrapping = global::Avalonia.Media.TextWrapping.NoWrap,
                    Foreground = global::java.awt.J2csTheme.MetalText,
                    Background = global::Avalonia.Media.Brushes.White
                };
                AvControl = box;
            }
        }

        public void __init__V()
        {
            Ensure();
        }

        public void __init_Ljava_lang_String__V(global::java.lang.String text)
        {
            Ensure();
            box.Text = global::java.lang.JRuntime.Cs(text);
        }

        public void __init_II_V(int rows, int columns)
        {
            Ensure();
            Size(rows, columns);
        }

        public void __init_Ljava_lang_String_II_V(global::java.lang.String text, int rows, int columns)
        {
            Ensure();
            box.Text = global::java.lang.JRuntime.Cs(text);
            Size(rows, columns);
        }

        private void Size(int rows, int columns)
        {
            if (columns > 0) { box.MinWidth = columns * 8; }
            if (rows > 0) { box.MinHeight = rows * 18; }
        }

        public global::java.lang.String getText()
        {
            return global::java.lang.String.Wrap(box == null ? "" : box.Text ?? "");
        }

        public void setText(global::java.lang.String text)
        {
            Ensure();
            box.Text = global::java.lang.JRuntime.Cs(text);
        }

        public void append(global::java.lang.String text)
        {
            Ensure();
            box.Text = (box.Text ?? "") + global::java.lang.JRuntime.Cs(text);
        }

        public void setEditable(int editable)
        {
            Ensure();
            box.IsReadOnly = editable == 0;
        }

        public void setLineWrap(int wrap)
        {
            Ensure();
            box.TextWrapping = wrap != 0
                    ? global::Avalonia.Media.TextWrapping.Wrap
                    : global::Avalonia.Media.TextWrapping.NoWrap;
        }

        public void setWrapStyleWord(int word)
        {
        }

        public void setRows(int rows)
        {
            Ensure();
            if (rows > 0) { box.MinHeight = rows * 18; }
        }

        public void setColumns(int columns)
        {
            Ensure();
            if (columns > 0) { box.MinWidth = columns * 8; }
        }
    }
}
