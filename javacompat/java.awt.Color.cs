namespace java.awt
{
    public class Color : global::java.lang.Object
    {
        public static readonly Color RED = new Color(255, 0, 0);
        public static readonly Color ORANGE = new Color(255, 200, 0);
        public static readonly Color DARK_GRAY = new Color(64, 64, 64);
        public static readonly Color BLACK = new Color(0, 0, 0);
        public static readonly Color WHITE = new Color(255, 255, 255);
        public static readonly Color GRAY = new Color(128, 128, 128);
        public static readonly Color LIGHT_GRAY = new Color(192, 192, 192);
        public static readonly Color YELLOW = new Color(255, 255, 0);
        public static readonly Color GREEN = new Color(0, 255, 0);
        public static readonly Color BLUE = new Color(0, 0, 255);
        public static readonly Color CYAN = new Color(0, 255, 255);
        public static readonly Color MAGENTA = new Color(255, 0, 255);
        public static readonly Color PINK = new Color(255, 175, 175);

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
