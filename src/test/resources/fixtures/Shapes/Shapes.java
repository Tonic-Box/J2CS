public class Shapes {
    public static void main(String[] args) {
        Circle c = new Circle(5);
        Rect r = new Rect(3, 4);
        System.out.println(c.area());
        System.out.println(r.area());
        System.out.println(Circle.count);
        Circle c2 = new Circle(2);
        System.out.println(Circle.count);
        System.out.println(c2.area());
        System.out.println(c.radius);
        c.radius = 10;
        System.out.println(c.area());
        System.out.println(c);
        System.out.println(r.describe());
        System.out.println(new Rect(4, 9).describe());
        Rect sq = new Rect(6);
        System.out.println(sq.area());
        System.out.println(Counter.next());
        System.out.println(Counter.next());
        System.out.println(Counter.next());

        Object o = c;
        System.out.println(o instanceof Circle);
        Circle back = (Circle) o;
        System.out.println(back.area());
        System.out.println(c.equals(back));
        System.out.println(c.equals(r));
    }
}

class Circle {
    static int count;
    int radius;

    Circle(int radius) {
        this.radius = radius;
        count++;
    }

    int area() {
        return radius * radius * 3;
    }

    public String toString() {
        return "circle";
    }
}

class Rect {
    int w;
    int h;

    Rect(int w, int h) {
        this.w = w;
        this.h = h;
    }

    Rect(int s) {
        this(s, s);
    }

    int area() {
        return w * h;
    }

    String describe() {
        return w > h ? "wide" : "tall";
    }
}

class Counter {
    static int value;

    static int next() {
        value += 1;
        return value;
    }
}
