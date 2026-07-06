namespace java.awt.geom
{
    public class Point2D : global::java.lang.Object
    {
        public Point2D(global::java.lang.RawNew r) : base(r)
        {
        }

        public virtual double getX() { return 0; }
        public virtual double getY() { return 0; }

        public double distance(Point2D other)
        {
            double dx = getX() - other.getX();
            double dy = getY() - other.getY();
            return global::System.Math.Sqrt(dx * dx + dy * dy);
        }

        public double distance(double px, double py)
        {
            double dx = getX() - px;
            double dy = getY() - py;
            return global::System.Math.Sqrt(dx * dx + dy * dy);
        }
    }
}
