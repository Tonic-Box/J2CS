import java.lang.reflect.*;
import java.util.*;

public class Reflection {
    static class Base {
        int baseValue = 7;
    }

    static class Point extends Base {
        private int x;
        private int y;
        public boolean flag;

        public Point() {
            this.x = 0;
            this.y = 0;
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int sum() {
            return x + y;
        }

        public int scale(int factor) {
            return (x + y) * factor;
        }

        public String describe(String prefix) {
            return prefix + ":" + x + "," + y;
        }
    }

    public static void main(String[] args) throws Exception {
        Class<?> c = Point.class;
        System.out.println(c.getName());
        System.out.println(c.getSimpleName());
        System.out.println(c.getSuperclass().getSimpleName());

        Point p = new Point(3, 4);
        System.out.println(c.isInstance(p));
        System.out.println(c.isAssignableFrom(Point.class));
        System.out.println(Base.class.isAssignableFrom(Point.class));
        System.out.println(Point.class.isAssignableFrom(Base.class));

        Object cast = c.cast(p);
        System.out.println(cast instanceof Point);

        Field[] fields = c.getDeclaredFields();
        List<String> fieldDescs = new ArrayList<>();
        for (Field f : fields) {
            fieldDescs.add(f.getName() + ":" + f.getType().getName() + ":" + Modifier.toString(f.getModifiers()));
        }
        Collections.sort(fieldDescs);
        for (String s : fieldDescs) {
            System.out.println(s);
        }

        Field fx = c.getDeclaredField("x");
        fx.setAccessible(true);
        System.out.println(fx.getInt(p));
        fx.setInt(p, 10);
        System.out.println(p.sum());

        Field ff = c.getDeclaredField("flag");
        ff.setBoolean(p, true);
        System.out.println(p.flag);

        Method[] methods = c.getDeclaredMethods();
        List<String> methodDescs = new ArrayList<>();
        for (Method m : methods) {
            methodDescs.add(m.getName() + "/" + m.getParameterCount() + "/" + m.getReturnType().getName());
        }
        Collections.sort(methodDescs);
        for (String s : methodDescs) {
            System.out.println(s);
        }

        Method scale = c.getDeclaredMethod("scale", int.class);
        System.out.println(scale.invoke(p, 5));

        Method describe = c.getDeclaredMethod("describe", String.class);
        System.out.println(describe.invoke(p, "pt"));

        Constructor<?>[] ctors = c.getDeclaredConstructors();
        List<Integer> counts = new ArrayList<>();
        for (Constructor<?> ct : ctors) {
            counts.add(ct.getParameterCount());
        }
        Collections.sort(counts);
        System.out.println(counts);

        for (Constructor<?> ct : ctors) {
            if (ct.getParameterCount() == 2) {
                Point p2 = (Point) ct.newInstance(8, 9);
                System.out.println(p2.sum());
            }
        }

        System.out.println(Modifier.isPublic(c.getDeclaredMethod("sum").getModifiers()));
    }
}
