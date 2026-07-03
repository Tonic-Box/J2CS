public class Exceptions1 {
    public static void main(String[] args) {
        int z = 0;
        int keep = 5;
        try {
            keep = 7;
            int r = 100 / z;
            System.out.println(r);
        } catch (ArithmeticException e) {
            System.out.println("caught arith");
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            System.out.println(keep);
        }

        String s = null;
        try {
            System.out.println(s.length());
        } catch (NullPointerException e) {
            System.out.println("caught npe");
        }

        Object o = "text";
        try {
            Integer n = (Integer) o;
            System.out.println(n);
        } catch (ClassCastException e) {
            System.out.println("caught cce");
        }

        int[] arr = new int[3];
        try {
            System.out.println(arr[10]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("caught aioobe");
        }

        try {
            int r = divide(10, 0);
            System.out.println(r);
        } catch (RuntimeException e) {
            System.out.println("caught via supertype");
        }

        System.out.println(safeDiv(20, 4));
        System.out.println(safeDiv(20, 0));
        System.out.println("done");
    }

    static int divide(int a, int b) {
        return a / b;
    }

    static int safeDiv(int a, int b) {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            return -1;
        }
    }
}
