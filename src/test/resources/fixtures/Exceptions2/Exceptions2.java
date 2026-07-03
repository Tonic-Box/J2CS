public class Exceptions2 {
    public static void main(String[] args) {
        try {
            throw new MyException("boom");
        } catch (MyException e) {
            System.out.println(e.getMessage());
            System.out.println(e.code());
        }

        try {
            level1(3);
        } catch (MyException e) {
            System.out.println("propagated: " + e.getMessage());
        }

        try {
            try {
                throw new MyException("inner");
            } finally {
                System.out.println("finally ran");
            }
        } catch (Exception e) {
            System.out.println("outer caught: " + e.getMessage());
        }

        System.out.println(classify(0));
        System.out.println(classify(1));
        System.out.println(classify(2));

        int total = 0;
        for (int i = 0; i < 5; i++) {
            try {
                if (i == 2) {
                    throw new MyException("skip");
                }
                if (i == 4) {
                    break;
                }
                total += i;
            } catch (MyException e) {
                continue;
            }
        }
        System.out.println(total);

        try {
            rethrower();
        } catch (MyException e) {
            System.out.println("rethrown: " + e.getMessage());
        }

        System.out.println(multiCatch(0));
        System.out.println(multiCatch(1));
        System.out.println(multiCatch(2));
        System.out.println("done");
    }

    static void level1(int n) throws MyException {
        level2(n);
    }

    static void level2(int n) throws MyException {
        if (n > 0) {
            throw new MyException("deep " + n);
        }
    }

    static String classify(int x) {
        try {
            if (x == 0) {
                throw new MyException("zero");
            }
            if (x == 1) {
                throw new IllegalStateException("one");
            }
            return "none";
        } catch (MyException e) {
            return "my:" + e.getMessage();
        } catch (RuntimeException e) {
            return "rt:" + e.getMessage();
        }
    }

    static void rethrower() throws MyException {
        try {
            throw new MyException("original");
        } catch (MyException e) {
            throw e;
        }
    }

    static int multiCatch(int x) {
        try {
            if (x == 0) {
                throw new IllegalStateException("s");
            }
            if (x == 1) {
                throw new NumberFormatException("n");
            }
            return 100;
        } catch (IllegalStateException | NumberFormatException e) {
            return -1;
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
