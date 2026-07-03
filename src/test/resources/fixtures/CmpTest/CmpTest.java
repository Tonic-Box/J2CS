public class CmpTest {
    public static void main(String[] args) {
        long a = 5000000000L;
        long b = -3L;
        System.out.println(a > b);
        System.out.println(a < b);
        System.out.println(cmpLong(a, b));
        System.out.println(cmpLong(b, a));
        System.out.println(cmpLong(a, a));

        double nan = Double.NaN;
        double one = 1.0;
        System.out.println(nan < one);
        System.out.println(nan > one);
        System.out.println(nan == nan);
        System.out.println(nan != one);
        if (nan < one) {
            System.out.println("d-lt");
        } else {
            System.out.println("d-not-lt");
        }
        if (nan > one) {
            System.out.println("d-gt");
        } else {
            System.out.println("d-not-gt");
        }
        if (one <= nan) {
            System.out.println("d-le");
        } else {
            System.out.println("d-not-le");
        }

        float fnan = Float.NaN;
        float fone = 1.0f;
        System.out.println(fnan < fone);
        System.out.println(fnan > fone);
        if (fnan < fone) {
            System.out.println("f-lt");
        } else {
            System.out.println("f-not-lt");
        }
        if (fnan >= fone) {
            System.out.println("f-ge");
        } else {
            System.out.println("f-not-ge");
        }

        double pz = 0.0;
        double nz = -0.0;
        System.out.println(pz == nz);
        System.out.println(pz < nz);
        System.out.println(pz > nz);
        System.out.println(cmpDouble(pz, nz));
        System.out.println(cmpDouble(nan, one));
        System.out.println(cmpDouble(one, nan));

        System.out.println(fcmp(fnan, fone));
        System.out.println(fcmp(fone, 2.0f));
    }

    static int cmpLong(long x, long y) {
        return x > y ? 1 : (x < y ? -1 : 0);
    }

    static int cmpDouble(double x, double y) {
        return x > y ? 1 : (x < y ? -1 : 0);
    }

    static int fcmp(float x, float y) {
        return x > y ? 1 : (x < y ? -1 : 0);
    }
}
