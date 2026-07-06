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

        internal global::Avalonia.Media.Color AvColor;
        internal global::Avalonia.Media.IBrush Brush;

        public Color(global::java.lang.RawNew r) : base(r)
        {
            SetRgba(0, 0, 0, 255);
        }

        private Color(int red, int green, int blue) : base(global::java.lang.RawNew.I)
        {
            SetRgba((byte)red, (byte)green, (byte)blue, 255);
        }

        private void SetRgba(byte red, byte green, byte blue, byte alpha)
        {
            AvColor = global::Avalonia.Media.Color.FromArgb(alpha, red, green, blue);
            Brush = new global::Avalonia.Media.SolidColorBrush(AvColor);
        }

        private static byte Clamp(float v)
        {
            int i = (int)(v * 255.0f + 0.5f);
            return (byte)(i < 0 ? 0 : (i > 255 ? 255 : i));
        }

        public void __init_III_V(int red, int green, int blue)
        {
            SetRgba((byte)red, (byte)green, (byte)blue, 255);
        }

        public void __init_IIII_V(int red, int green, int blue, int alpha)
        {
            SetRgba((byte)red, (byte)green, (byte)blue, (byte)alpha);
        }

        public void __init_FFF_V(float red, float green, float blue)
        {
            SetRgba(Clamp(red), Clamp(green), Clamp(blue), 255);
        }

        public void __init_FFFF_V(float red, float green, float blue, float alpha)
        {
            SetRgba(Clamp(red), Clamp(green), Clamp(blue), Clamp(alpha));
        }

        public int getRed() { return AvColor.R; }
        public int getGreen() { return AvColor.G; }
        public int getBlue() { return AvColor.B; }
        public int getAlpha() { return AvColor.A; }

        public int getRGB()
        {
            return (AvColor.A << 24) | (AvColor.R << 16) | (AvColor.G << 8) | AvColor.B;
        }

        public static Color getHSBColor(float h, float s, float b)
        {
            var rgb = HSBtoRGB(h, s, b);
            var c = new Color(global::java.lang.RawNew.I);
            c.SetRgba(rgb.Item1, rgb.Item2, rgb.Item3, 255);
            return c;
        }

        private static (byte, byte, byte) HSBtoRGB(float hue, float saturation, float brightness)
        {
            float r = 0, g = 0, b = 0;
            if (saturation == 0)
            {
                r = g = b = brightness;
            }
            else
            {
                float h = (hue - (float)global::System.Math.Floor(hue)) * 6.0f;
                float f = h - (float)global::System.Math.Floor(h);
                float p = brightness * (1.0f - saturation);
                float q = brightness * (1.0f - saturation * f);
                float t = brightness * (1.0f - saturation * (1.0f - f));
                switch ((int)h)
                {
                    case 0: r = brightness; g = t; b = p; break;
                    case 1: r = q; g = brightness; b = p; break;
                    case 2: r = p; g = brightness; b = t; break;
                    case 3: r = p; g = q; b = brightness; break;
                    case 4: r = t; g = p; b = brightness; break;
                    default: r = brightness; g = p; b = q; break;
                }
            }
            return (Clamp(r), Clamp(g), Clamp(b));
        }
    }
}
