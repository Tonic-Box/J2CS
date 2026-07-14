namespace java.awt.image
{
    // Backed by a straight-alpha ARGB pixel buffer. Reports TYPE_INT_ARGB so jME's AWTLoader takes its
    // getRGB pixel path; its raster/DataBuffer fast paths are only for TYPE_3BYTE_BGR/4BYTE_ABGR/BYTE_GRAY.
    public class BufferedImage : global::java.lang.Object
    {
        public const int TYPE_INT_ARGB = 2;
        private const int OPAQUE = 1;
        private const int TRANSLUCENT = 3;

        private int width;
        private int height;
        private int[] argb;
        private bool alpha;

        public BufferedImage(global::java.lang.RawNew r) : base(r)
        {
        }

        internal static BufferedImage FromArgb(int width, int height, int[] argb, bool alpha)
        {
            BufferedImage img = new BufferedImage(global::java.lang.RawNew.I);
            img.width = width;
            img.height = height;
            img.argb = argb;
            img.alpha = alpha;
            return img;
        }

        public void __init_III_V(int width, int height, int imageType)
        {
            this.width = width;
            this.height = height;
            this.argb = new int[width * height];
            this.alpha = true;
        }

        public int getWidth()
        {
            return width;
        }

        public int getHeight()
        {
            return height;
        }

        public int getType()
        {
            return TYPE_INT_ARGB;
        }

        public int getRGB(int x, int y)
        {
            return argb[y * width + x];
        }

        public void setRGB(int x, int y, int rgb)
        {
            argb[y * width + x] = rgb;
        }

        public int getTransparency()
        {
            return alpha ? TRANSLUCENT : OPAQUE;
        }
    }
}
