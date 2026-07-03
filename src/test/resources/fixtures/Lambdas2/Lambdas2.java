public class Lambdas2 {
    public static void main(String[] args) {
        IntOp mul = Lambdas2::mul;
        System.out.println(mul.apply(6, 7));

        Box b = new Box("hello");
        Sup<String> bound = b::get;
        System.out.println(bound.get());

        Fn<Box, String> unbound = Box::get;
        System.out.println(unbound.apply(new Box("world")));

        Fn<String, Box> maker = Box::new;
        System.out.println(maker.apply("made").get());

        ToInt<String> len = String::length;
        System.out.println(len.apply("abcd"));

        IntSup boundLen = "xyz"::length;
        System.out.println(boundLen.get());

        System.out.println("done");
    }

    static int mul(int a, int b) {
        return a * b;
    }

    static void unused() {
        Runnable r = () -> {
        };
        r.run();
    }
}

interface IntOp {
    int apply(int a, int b);
}

interface Sup<R> {
    R get();
}

interface Fn<T, R> {
    R apply(T t);
}

interface ToInt<T> {
    int apply(T t);
}

interface IntSup {
    int get();
}

class Box {
    private final String v;

    Box(String v) {
        this.v = v;
    }

    String get() {
        return v;
    }
}
