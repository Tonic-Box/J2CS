public class Bootstrap {
    public static void main(String[] args) {
        Boolean bt = true;
        Boolean bf = false;
        System.out.println(bt);
        System.out.println(bf);

        boolean pv = bt;
        System.out.println(pv);
        System.out.println(bt.booleanValue());

        System.out.println(Boolean.valueOf(true) == Boolean.valueOf(true));
        System.out.println(bt == Boolean.TRUE);

        System.out.println(bt.hashCode());
        System.out.println(bf.hashCode());

        System.out.println(bt.equals(Boolean.TRUE));
        System.out.println(bt.equals(bf));

        System.out.println(Boolean.parseBoolean("TRUE"));
        System.out.println(Boolean.parseBoolean("nope"));
        System.out.println(Boolean.parseBoolean(null));

        System.out.println(bt.compareTo(bf));
        System.out.println(bf.compareTo(bt));
        System.out.println(bt.compareTo(Boolean.TRUE));

        System.out.println(Boolean.toString(true));
        System.out.println(Boolean.logicalAnd(true, false));
        System.out.println(Boolean.logicalOr(true, false));
        System.out.println(Boolean.logicalXor(true, true));

        Object o = bt;
        System.out.println(o);
        System.out.println(o.toString());

        int count = 0;
        for (int i = 0; i < 3; i++) {
            Boolean flag = i % 2 == 0;
            if (flag) {
                count++;
            }
        }
        System.out.println(count);
        System.out.println("done");
    }
}
