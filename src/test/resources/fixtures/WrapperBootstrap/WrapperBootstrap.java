import java.util.ArrayList;

public class WrapperBootstrap {
    public static void main(String[] args) {
        Integer x = 5;
        int y = x;
        System.out.println(y);
        System.out.println(x);

        Integer a = 100;
        Integer b = 100;
        System.out.println(a == b);
        System.out.println(a.equals(b));

        Integer c = 200;
        Integer d = 200;
        System.out.println(c == d);
        System.out.println(c.equals(d));

        System.out.println(x.hashCode());
        System.out.println(x.intValue());
        System.out.println(x.longValue());
        System.out.println(x.doubleValue());
        System.out.println(a.compareTo(c));
        System.out.println(c.compareTo(a));

        System.out.println(Integer.parseInt("123"));
        System.out.println(Integer.valueOf(42).intValue() + 8);

        Number n = x;
        System.out.println(n.intValue());
        System.out.println(n.doubleValue());
        System.out.println(n.longValue());

        Object o = x;
        System.out.println(o);
        System.out.println(o.toString());

        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        int sum = 0;
        for (int v : list) {
            sum += v;
        }
        System.out.println(sum);
        System.out.println(list);
        System.out.println("done");
    }
}
