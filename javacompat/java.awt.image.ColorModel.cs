namespace java.awt.image
{
    // Minimal color model: jME's AWTLoader stores a couple of these in static fields but never reads
    // them on the getRGB decode path.
    public class ColorModel : global::java.lang.Object
    {
        public ColorModel(global::java.lang.RawNew r) : base(r)
        {
        }
    }
}
