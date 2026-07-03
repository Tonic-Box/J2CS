public class WrapperBootstrap2 {
    public static void main(String[] args) {
        Double d = 3.5;
        double dv = d;
        System.out.println(dv);
        System.out.println(d);
        System.out.println(d.doubleValue());
        System.out.println(d.intValue());
        System.out.println(d.longValue());

        Double a = 1.5;
        Double b = 1.5;
        System.out.println(a.equals(b));
        System.out.println(a.compareTo(d));
        System.out.println(d.compareTo(a));
        System.out.println(d.hashCode() == a.hashCode());

        System.out.println(Double.parseDouble("2.75"));
        System.out.println(Double.valueOf(6.25).doubleValue() + 0.75);

        double nan = Double.NaN;
        System.out.println(Double.isNaN(nan));
        System.out.println(Double.isInfinite(Double.POSITIVE_INFINITY));
        System.out.println(Double.isNaN(1.0));

        System.out.println(Double.doubleToLongBits(1.0));
        System.out.println(Double.longBitsToDouble(Double.doubleToLongBits(2.5)));
        System.out.println(Double.compare(1.0, 2.0));

        Object o = d;
        System.out.println(o);
        System.out.println(o.toString());

        Float f = 2.5f;
        float fv = f;
        System.out.println(fv);
        System.out.println(f);
        System.out.println(f.floatValue());
        System.out.println(f.doubleValue());
        System.out.println(f.intValue());
        Float fa = 1.25f;
        Float fb = 1.25f;
        System.out.println(fa.equals(fb));
        System.out.println(f.compareTo(fa));
        System.out.println(Float.parseFloat("3.5"));
        System.out.println(Float.floatToIntBits(1.0f));
        System.out.println(Float.intBitsToFloat(Float.floatToIntBits(2.5f)));
        System.out.println(Float.isNaN(Float.NaN));

        Character ch = 'A';
        char cv = ch;
        System.out.println(cv);
        System.out.println(ch);
        System.out.println(ch.charValue());
        Character c2 = 'A';
        System.out.println(ch == c2);
        System.out.println(ch.equals(c2));
        Character z = 'z';
        System.out.println(ch.compareTo(z));

        Integer iv = 7;
        System.out.println(iv + 1);

        Object[] mixed = new Object[] { 1, 2.5, 3.5f, 'X' };
        for (int i = 0; i < mixed.length; i++) {
            System.out.println(mixed[i]);
        }
        System.out.println("done");
    }
}
