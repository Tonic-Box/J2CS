import java.util.ArrayList;
import java.util.HashMap;

public class ObjectBootstrap {
    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();
        System.out.println(a.equals(a));
        System.out.println(a.equals(b));
        System.out.println(a.equals(null));
        System.out.println(a == a);
        System.out.println(a == b);
        System.out.println(a.hashCode() == a.hashCode());

        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        Point p3 = new Point(3, 4);
        System.out.println(p1);
        System.out.println(p1.equals(p2));
        System.out.println(p1.equals(p3));
        System.out.println(p1.equals(a));
        System.out.println(p1.hashCode());
        System.out.println(p1.hashCode() == p2.hashCode());

        HashMap<Point, String> map = new HashMap<Point, String>();
        map.put(p1, "a");
        map.put(p3, "c");
        System.out.println(map.get(p2));
        System.out.println(map.containsKey(p3));
        System.out.println(map.size());

        ArrayList<Point> list = new ArrayList<Point>();
        list.add(p1);
        list.add(p3);
        System.out.println(list.contains(p2));
        System.out.println(list);

        int total = 0;
        for (int i = 0; i < 5; i++) {
            total += i * i;
        }
        System.out.println(total);
        System.out.println("sum=" + total);
        System.out.println("done");
    }
}

class Point {
    private final int x;
    private final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        Point p = (Point) o;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }
}
