namespace javax.imageio
{
    // Decodes images through SkiaSharp (bundled with Avalonia) into a straight-alpha ARGB BufferedImage.
    public static class ImageIO
    {
        public static global::java.awt.image.BufferedImage read(global::java.io.InputStream input)
        {
            if (input == null)
            {
                return null;
            }
            byte[] bytes = global::java.lang.JRuntime.UnsignedBytes(input.readAllBytes());
            if (bytes == null || bytes.Length == 0)
            {
                return null;
            }
            using global::SkiaSharp.SKBitmap bmp = global::SkiaSharp.SKBitmap.Decode(bytes);
            if (bmp == null)
            {
                return null;
            }
            int w = bmp.Width;
            int h = bmp.Height;
            bool alpha = bmp.Info.AlphaType != global::SkiaSharp.SKAlphaType.Opaque;
            global::SkiaSharp.SKColor[] px = bmp.Pixels;
            int[] argb = new int[px.Length];
            for (int i = 0; i < px.Length; i++)
            {
                global::SkiaSharp.SKColor c = px[i];
                argb[i] = (c.Alpha << 24) | (c.Red << 16) | (c.Green << 8) | c.Blue;
            }
            return global::java.awt.image.BufferedImage.FromArgb(w, h, argb, alpha);
        }

        public static void setUseCache(int useCache)
        {
        }

        // jME only null-checks the result to validate the suffix; a non-null iterator marks "supported".
        public static global::java.util.Iterator getImageReadersBySuffix(global::java.lang.String suffix)
        {
            global::java.util.ArrayList list = new global::java.util.ArrayList(global::java.lang.RawNew.I);
            list.__init__V();
            return list.iterator();
        }
    }
}
