public class Boxing2 {
    public static void main(String[] args) {
        Long lng = 10000000000L;
        long lv = lng;
        System.out.println(lng);
        System.out.println(lv);
        System.out.println(lng.hashCode());
        Long la = 42L;
        Long lb = 42L;
        System.out.println(la == lb);
        Long lc = 5000L;
        Long ld = 5000L;
        System.out.println(lc == ld);
        System.out.println(lc.equals(ld));

        Double dbl = 3.5;
        double dv = dbl;
        System.out.println(dbl);
        System.out.println(dv);
        Double da = 1.0;
        Double db = 1.0;
        System.out.println(da == db);
        System.out.println(da.equals(db));
        System.out.println(dbl.intValue());

        Float flt = 2.5f;
        float fv = flt;
        System.out.println(flt);
        System.out.println(fv);
        System.out.println(flt.doubleValue());

        Boolean bt = true;
        boolean bv = bt;
        System.out.println(bt);
        System.out.println(bv);
        Boolean bt2 = true;
        System.out.println(bt == bt2);
        System.out.println(bt.hashCode());
        System.out.println(Boolean.valueOf(false).hashCode());

        Character ch = 'Q';
        char cv = ch;
        System.out.println(ch);
        System.out.println(cv);
        Character ch2 = 'Q';
        System.out.println(ch == ch2);
        System.out.println(ch.charValue());

        Short sh = (short) 300;
        short sv = sh;
        System.out.println(sh);
        System.out.println(sv);

        Byte by = (byte) -5;
        byte byv = by;
        System.out.println(by);
        System.out.println(byv);

        System.out.println(Long.parseLong("123456789"));
        System.out.println(Double.parseDouble("2.75"));
        System.out.println(Boolean.parseBoolean("true"));

        Object[] mixed = new Object[] { 1, 2L, 3.0, true, 'x' };
        for (int i = 0; i < mixed.length; i++) {
            System.out.println(mixed[i]);
        }
        System.out.println("done");
    }
}
