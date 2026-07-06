namespace java.awt
{
    public class Graphics2D : Graphics
    {
        public Graphics2D(global::java.lang.RawNew r) : base(r)
        {
        }

        public void setRenderingHint(global::java.lang.Object key, global::java.lang.Object value)
        {
        }

        public void setStroke(global::java.lang.Object stroke)
        {
        }

        public void translate(int x, int y)
        {
            Transform = global::Avalonia.Matrix.CreateTranslation(x, y) * Transform;
        }

        public void translate(double x, double y)
        {
            Transform = global::Avalonia.Matrix.CreateTranslation(x, y) * Transform;
        }

        public void scale(double sx, double sy)
        {
            Transform = global::Avalonia.Matrix.CreateScale(sx, sy) * Transform;
        }

        public void rotate(double theta)
        {
            Transform = global::Avalonia.Matrix.CreateRotation(theta) * Transform;
        }

        public global::java.awt.geom.AffineTransform getTransform()
        {
            var a = new global::java.awt.geom.AffineTransform(global::java.lang.RawNew.I);
            a.M = Transform;
            return a;
        }

        public void setTransform(global::java.awt.geom.AffineTransform t)
        {
            if (t != null)
            {
                Transform = t.M;
            }
        }
    }
}
