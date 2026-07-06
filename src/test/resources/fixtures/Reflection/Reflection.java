import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class Reflection {
    enum Level { LOW, HIGH }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
    @interface Info {
        String value();
        int order() default 0;
        boolean active() default true;
        double weight() default 1.5;
        Level level() default Level.LOW;
        String[] tags() default {};
    }

    static class Base {
        int baseValue = 7;
    }

    @Info(value = "point-class", order = 3, level = Level.HIGH, tags = {"a", "b"})
    static class Point extends Base {
        @Info("x-field")
        private int x;
        private int y;

        public Point() {
            this.x = 0;
            this.y = 0;
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Info(value = "sum-method", order = 1, active = false, weight = 2.25)
        public int sum() {
            return x + y;
        }

        public int scale(int factor) {
            return (x + y) * factor;
        }
    }

    public static void main(String[] args) throws Exception {
        Class<?> c = Point.class;

        Info ci = c.getAnnotation(Info.class);
        System.out.println(c.isAnnotationPresent(Info.class));
        System.out.println(ci.value() + "/" + ci.order() + "/" + ci.level() + "/" + ci.active() + "/" + ci.weight());
        System.out.println(Arrays.toString(ci.tags()));
        System.out.println(Base.class.isAnnotationPresent(Info.class));

        Field fx = c.getDeclaredField("x");
        System.out.println(fx.isAnnotationPresent(Info.class));
        System.out.println(fx.getAnnotation(Info.class).value());
        Field fy = c.getDeclaredField("y");
        System.out.println(fy.isAnnotationPresent(Info.class));

        Method sum = c.getDeclaredMethod("sum");
        Info mi = sum.getAnnotation(Info.class);
        System.out.println(mi.value() + "/" + mi.order() + "/" + mi.active() + "/" + mi.weight());
        System.out.println(mi.tags().length);
        Method scale = c.getDeclaredMethod("scale", int.class);
        System.out.println(scale.isAnnotationPresent(Info.class));

        int annotatedMethods = 0;
        for (Method m : c.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Info.class)) {
                annotatedMethods++;
            }
        }
        System.out.println(annotatedMethods);

        Point p = new Point(3, 4);
        System.out.println(sum.invoke(p));
    }
}
