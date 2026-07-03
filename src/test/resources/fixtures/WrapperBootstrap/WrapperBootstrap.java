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

        Long lg = 10000000000L;
        long lv = lg;
        System.out.println(lv);
        System.out.println(lg);
        Long la = 42L;
        Long lb = 42L;
        System.out.println(la == lb);
        Long lc = 5000L;
        Long ld = 5000L;
        System.out.println(lc == ld);
        System.out.println(lg.longValue());
        System.out.println(lg.intValue());
        System.out.println(la.compareTo(lc));
        System.out.println(Long.parseLong("999999"));

        Short sh = (short) 300;
        short shv = sh;
        System.out.println(shv);
        System.out.println(sh);
        System.out.println(sh.shortValue());
        System.out.println(sh.intValue());
        Short sa = (short) 10;
        Short sb2 = (short) 10;
        System.out.println(sa == sb2);
        System.out.println(sa.equals(sb2));

        Byte by = (byte) -5;
        byte byv = by;
        System.out.println(byv);
        System.out.println(by);
        System.out.println(by.byteValue());
        Byte ba = (byte) 7;
        Byte bb = (byte) 7;
        System.out.println(ba == bb);
        System.out.println(ba.compareTo(by));
        System.out.println("done");
    }
}
