public class ExceptionBootstrap {
    public static void main(String[] args) {
        try {
            int x = 10 / zero();
            System.out.println(x);
        } catch (ArithmeticException e) {
            System.out.println("caught arith");
            System.out.println(e.getMessage());
            System.out.println(e.toString());
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

        int[] arr = new int[2];
        try {
            System.out.println(arr[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("caught aioobe");
        }

        try {
            Integer.parseInt("nope");
        } catch (NumberFormatException e) {
            System.out.println("caught nfe");
            System.out.println(e.getMessage());
        }

        try {
            throw new MyException("boom");
        } catch (MyException e) {
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            System.out.println(e.code());
        }

        try {
            level(2);
        } catch (RuntimeException e) {
            System.out.println("caught via supertype: " + e.getMessage());
        }

        System.out.println(classify(0));
        System.out.println(classify(1));
        System.out.println("done");
    }

    static int zero() {
        return 0;
    }

    static void level(int n) {
        throw new IllegalStateException("state " + n);
    }

    static String classify(int x) {
        try {
            if (x == 0) {
                throw new MyException("zero");
            }
            throw new IllegalArgumentException("arg");
        } catch (MyException e) {
            return "my:" + e.getMessage();
        } catch (RuntimeException e) {
            return "rt:" + e.getMessage();
        }
    }
}

class MyException extends Exception {
    private final int code;

    MyException(String message) {
        super(message);
        this.code = message.length();
    }

    int code() {
        return code;
    }
}
