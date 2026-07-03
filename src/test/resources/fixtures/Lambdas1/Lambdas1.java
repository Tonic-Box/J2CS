public class Lambdas1 {
    static IntOp stored = (a, b) -> a * b;

    int factor = 10;

    public static void main(String[] args) {
        IntOp add = (a, b) -> a + b;
        System.out.println(add.apply(3, 4));
        System.out.println(stored.apply(5, 6));

        int base = 100;
        IntOp addBase = (a, b) -> a + b + base;
        System.out.println(addBase.apply(1, 2));

        String prefix = "v=";
        Fn<String, String> label = n -> prefix + n;
        System.out.println(label.apply("7"));

        int[] acc = new int[1];
        IntOp accum = (a, b) -> {
            acc[0] += a + b;
            return acc[0];
        };
        System.out.println(accum.apply(2, 3));
        System.out.println(accum.apply(4, 5));
        System.out.println(acc[0]);

        Lambdas1 self = new Lambdas1();
        System.out.println(self.scaled().apply(2, 3));

        System.out.println(applyOp(add, 10, 20));
        System.out.println(applyOp((a, b) -> a - b, 10, 20));

        Greeter g = () -> "hi";
        System.out.println(g.greet());
        System.out.println(g.loud());

        int sum = 0;
        for (int i = 0; i < 3; i++) {
            int captured = i;
            IntOp op = (a, b) -> a + b + captured;
            sum += op.apply(1, 1);
        }
        System.out.println(sum);

        Fn<String, Fn<String, String>> adder = x -> y -> x + y;
        System.out.println(adder.apply("a").apply("b"));

        System.out.println("done");
    }

    IntOp scaled() {
        return (a, b) -> (a + b) * factor;
    }

    static int applyOp(IntOp op, int a, int b) {
        return op.apply(a, b);
    }
}

interface IntOp {
    int apply(int a, int b);
}

interface Fn<T, R> {
    R apply(T t);
}

interface Greeter {
    String greet();

    default String loud() {
        return greet() + "!";
    }
}
