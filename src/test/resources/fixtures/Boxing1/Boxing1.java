public class Boxing1 {
    public static void main(String[] args) {
        Integer x = 5;
        int y = x;
        System.out.println(y);
        System.out.println(x);

        Object o = x;
        System.out.println(o);

        Integer a = 100;
        Integer b = 100;
        System.out.println(a == b);
        System.out.println(a.equals(b));

        Integer c = 200;
        Integer d = 200;
        System.out.println(c == d);
        System.out.println(c.equals(d));

        System.out.println(a.hashCode());
        System.out.println(c.hashCode());

        Integer built = Integer.valueOf(42);
        System.out.println(built.intValue());
        System.out.println(built + 8);

        Number n = x;
        System.out.println(n.doubleValue());
        System.out.println(n.longValue());
        System.out.println(n.intValue());

        int sum = 0;
        for (int i = 0; i < 4; i++) {
            Integer boxed = i;
            sum += boxed;
        }
        System.out.println(sum);

        System.out.println(unbox(Integer.valueOf(-7)));
        System.out.println(identity(x));

        Integer neg = -128;
        Integer neg2 = -128;
        System.out.println(neg == neg2);
        Integer low = -129;
        Integer low2 = -129;
        System.out.println(low == low2);
    }

    static int unbox(Integer i) {
        return i;
    }

    static Integer identity(Integer i) {
        return i;
    }
}
