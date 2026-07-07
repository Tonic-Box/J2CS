public class UnsupportedGraceful {
    public static void main(String[] args) {
        System.out.println("healthy");
        System.out.println(compute(21));
        System.out.println(makeSorted() == null);
    }

    static int compute(int x) {
        return x * 2;
    }

    static java.util.TreeMap<String, String> makeSorted() {
        return null;
    }

    static int risky(int x) {
        try {
            return 100 / x;
        } catch (ArithmeticException e) {
            return -1;
        }
    }

    static int useStack() {
        return new java.util.Stack<String>().size();
    }

    static native int nativeThing();
}

enum Color {
    RED, GREEN
}
