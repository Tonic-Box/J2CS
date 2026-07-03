public class StringsTest {
    public static void main(String[] args) {
        String a = "hello";
        String b = "hello";
        System.out.println(a == b);
        String c = concat(a, " world");
        System.out.println(c);
        System.out.println(c == "hello world");
        System.out.println(c.equals("hello world"));
        System.out.println(a.length());
        System.out.println(c.charAt(6));
        System.out.println(a.isEmpty());
        System.out.println("".isEmpty());

        String n = null;
        if (a.length() > 100) {
            n = "x";
        }
        System.out.println(concat("val=", n));

        StringBuilder sb = new StringBuilder();
        sb.append("n=").append(42).append(", d=").append(2.5)
                .append(", c=").append('x').append(", b=").append(true)
                .append(", l=").append(123456789012L);
        System.out.println(sb.toString());
        System.out.println(sb.length());

        System.out.println(a.hashCode());
        System.out.println("".hashCode());
        System.out.println(a.equals(b));
        System.out.println(a.equals(c));

        Object o = a;
        System.out.println(o.equals("hello"));

        int num = 7;
        double dv = 1.5;
        char ch = 'Q';
        boolean flag = false;
        long big = 9999999999L;
        System.out.println("mix " + num + " " + dv + " " + ch + " " + flag + " " + big + " end");
        Object shape = null;
        System.out.println("obj=" + shape);
    }

    static String concat(String x, String y) {
        return x + y;
    }
}
