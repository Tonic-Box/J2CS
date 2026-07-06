namespace java.awt
{
    public class Point : global::java.lang.Object
    {
        public int x;
        public int y;

        public Point(global::java.lang.RawNew r) : base(r)
        {
        }

        public void __init__V() { }

        public void __init_II_V(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public void __init_Ljava_awt_Point__V(Point p)
        {
            if (p != null)
            {
                this.x = p.x;
                this.y = p.y;
            }
        }

        public double getX() { return x; }
        public double getY() { return y; }
    }
}
