public class Exceptions3 {
    public static void main(String[] args) {
        System.out.println(parse("123"));
        System.out.println(parse("abc"));
        System.out.println(parse("-5"));

        Object[] items = new Object[] { "hello", new Object() };
        for (int i = 0; i < items.length; i++) {
            try {
                String s = (String) items[i];
                System.out.println("string:" + s.length());
            } catch (ClassCastException e) {
                System.out.println("not a string");
            }
        }

        int[] data = { 10, 20, 30 };
        System.out.println(safeGet(data, 1));
        System.out.println(safeGet(data, 9));

        System.out.println(anyCatch(0));
        System.out.println(anyCatch(1));
        System.out.println(anyCatch(2));
    }

    static int parse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    static int safeGet(int[] a, int i) {
        try {
            return a[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }

    static String anyCatch(int x) {
        try {
            if (x == 0) {
                throw new RuntimeException("rt");
            }
            if (x == 1) {
                int y = 1 / (x - 1);
                return "y=" + y;
            }
            return "clean";
        } catch (Throwable t) {
            return "caught:" + t.getMessage();
        }
    }
}
