public class UnsupportedGraceful {
    public static void main(String[] args) {
        System.out.println("healthy");
        System.out.println(compute(21));
        System.out.println(makeList() == null);
    }

    static int compute(int x) {
        return x * 2;
    }

    static java.util.List<String> makeList() {
        return null;
    }

    static int risky(int x) {
        try {
            return 100 / x;
        } catch (ArithmeticException e) {
            return -1;
        }
    }

    static int useList() {
        return new java.util.ArrayList<String>().size();
    }

    static native int nativeThing();
}

enum Color {
    RED, GREEN
}
