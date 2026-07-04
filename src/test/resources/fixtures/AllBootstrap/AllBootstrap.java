import java.util.Objects;

public class AllBootstrap {
    public static void main(String[] args) {
        Integer i = 40;
        Long l = 5000000000L;
        Double d = 2.5;
        Boolean b = true;
        Character c = 'Q';
        System.out.println(i + 2);
        System.out.println(l);
        System.out.println(d);
        System.out.println(b);
        System.out.println(c);
        System.out.println(i.equals(Integer.valueOf(40)));
        System.out.println(Integer.valueOf(50) == Integer.valueOf(50));
        System.out.println(d.doubleValue() + 0.5);

        System.out.println(Objects.equals("a", "a"));
        System.out.println(Objects.equals("a", "b"));
        System.out.println(Objects.hashCode(null));
        System.out.println(Objects.requireNonNull(i).intValue());

        System.out.println(Math.abs(-9));
        System.out.println(Math.max(3, 7));
        System.out.println(Math.sqrt(49.0));
        System.out.println(Math.floorDiv(-7, 2));
        try {
            Math.addExact(Integer.MAX_VALUE, 1);
        } catch (ArithmeticException e) {
            System.out.println("overflow: " + e.getMessage());
        }

        try {
            int z = 10 / zero();
            System.out.println(z);
        } catch (ArithmeticException e) {
            System.out.println("arith: " + e.getMessage());
        }

        String s = null;
        try {
            System.out.println(s.length());
        } catch (NullPointerException e) {
            System.out.println("npe caught");
        }

        try {
            throw new MyException("boom");
        } catch (MyException e) {
            System.out.println(e.getMessage());
            System.out.println(e.toString());
        }

        int[] src = { 1, 2, 3 };
        int[] dst = new int[3];
        System.arraycopy(src, 0, dst, 0, 3);
        System.out.println(dst[0] + dst[1] + dst[2]);

        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        System.out.println(p1.equals(p2));
        System.out.println(p1);

        int sum = 0;
        for (int n = 0; n < 5; n++) {
            sum += Math.abs(n - 2);
        }
        System.out.println(sum);
        System.out.println("done");
    }

    static int zero() {
        return 0;
    }
}

class MyException extends Exception {
    MyException(String message) {
        super(message);
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

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
