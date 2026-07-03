public class Arith {
    public static void main(String[] args) {
        int a = 17;
        int b = 5;
        System.out.println(a + b);
        System.out.println(a - b);
        System.out.println(a * b);
        System.out.println(a / b);
        System.out.println(a % b);
        System.out.println(-a);

        int min = Integer.MIN_VALUE;
        int negOne = -1;
        if (a > 100) {
            negOne = 1;
        }
        System.out.println(min / negOne);
        System.out.println(min % negOne);

        int big = Integer.MAX_VALUE;
        System.out.println(big + 1);
        System.out.println(min - 1);

        int x = 0x12345678;
        System.out.println(x << 1);
        System.out.println(x >> 4);
        System.out.println(x >>> 4);
        System.out.println(-x >> 8);
        System.out.println(-x >>> 8);
        int sh = 33;
        System.out.println(x << sh);
        int shNeg = -1;
        System.out.println(x >>> shNeg);
        System.out.println(x & 0xFF00);
        System.out.println(x | 0xF);
        System.out.println(x ^ 0xFFFF);

        long lmin = Long.MIN_VALUE;
        long lNegOne = -1L;
        if (b > 100) {
            lNegOne = 1L;
        }
        System.out.println(lmin / lNegOne);
        System.out.println(lmin % lNegOne);
        long lx = 0x123456789ABCDEFL;
        System.out.println(lx << 4);
        System.out.println(lx >> 8);
        System.out.println(lx >>> 8);
        int lsh = 65;
        System.out.println(lx << lsh);

        double u = 1.0;
        double v = 3.0;
        System.out.println(u / v);
        double ten = 10.0;
        double three = 3.0;
        System.out.println(ten % three);
        float f = 1.5f;
        float two = 2.0f;
        System.out.println(f * two);
        System.out.println(12345678.0);
        System.out.println(0.0005);
        System.out.println(-0.0);
        System.out.println(1.0E10);

        char c = 'A';
        int ci = c + 2;
        System.out.println(ci);
        System.out.println((char) (c + 1));

        int wide = 300;
        byte nb = (byte) wide;
        System.out.println(nb);
        short ns = (short) 70000;
        System.out.println(ns);

        double d1 = 3.99;
        System.out.println((int) d1);
        double d2 = -2.5;
        System.out.println((long) d2);
        double huge = 1.0E20;
        System.out.println((int) huge);
        System.out.println((long) huge);
        double zero = 0.0;
        double nan = zero / zero;
        System.out.println((int) nan);
        System.out.println(nan);
        long lv = 123456789012345L;
        System.out.println((int) lv);
        System.out.println((double) lv);
        int iv = 7;
        System.out.println((long) iv);
        System.out.println((double) iv);
        System.out.println((float) iv);
    }
}
