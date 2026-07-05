public class EnumAssert {
    enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        Color c = Color.valueOf("GREEN");
        System.out.println(c + " " + c.ordinal());
        for (Color x : Color.values()) {
            System.out.print(x + " ");
        }
        System.out.println();
        int n = compute(5);
        System.out.println(n);
        assert n > 0 : "n must be positive";
        System.out.println("done");
    }

    static int compute(int x) {
        assert x >= 0 : "x negative";
        return x * 2;
    }
}
