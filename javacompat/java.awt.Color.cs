namespace java.awt
{
    public class Color : global::java.lang.Object
    {
        public static readonly Color RED = new Color(255, 0, 0);
        public static readonly Color ORANGE = new Color(255, 200, 0);
        public static readonly Color DARK_GRAY = new Color(64, 64, 64);

        internal global::Avalonia.Media.IBrush Brush;

        public Color(global::java.lang.RawNew r) : base(r)
        {
            Brush = global::Avalonia.Media.Brushes.Black;
        }

        private Color(int red, int green, int blue) : base(global::java.lang.RawNew.I)
        {
            Brush = new global::Avalonia.Media.SolidColorBrush(
                    global::Avalonia.Media.Color.FromRgb((byte)red, (byte)green, (byte)blue));
        }

        public void __init_III_V(int red, int green, int blue)
        {
            Brush = new global::Avalonia.Media.SolidColorBrush(
                    global::Avalonia.Media.Color.FromRgb((byte)red, (byte)green, (byte)blue));
        }
    }
}
