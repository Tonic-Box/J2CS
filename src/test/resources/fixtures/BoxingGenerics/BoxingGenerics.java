public class BoxingGenerics {
    public static void main(String[] args) {
        Box<Integer> ib = new Box<Integer>();
        ib.set(41);
        int n = ib.get();
        System.out.println(n + 1);
        System.out.println(ib.get());

        Box<Double> db = new Box<Double>();
        db.set(1.5);
        double sum = db.get() + db.get();
        System.out.println(sum);

        Box<String> sb = new Box<String>();
        sb.set("boxed");
        System.out.println(sb.get());

        Pair<Integer, String> p = new Pair<Integer, String>(7, "seven");
        System.out.println(p.first() + 3);
        System.out.println(p.second());

        int total = 0;
        Box<Integer> counter = new Box<Integer>();
        for (int i = 0; i < 5; i++) {
            counter.set(i * i);
            total += counter.get();
        }
        System.out.println(total);

        System.out.println(firstOf(makeInts()));
        System.out.println(sumBoxed(3, 4));

        Box<Boolean> flag = new Box<Boolean>();
        flag.set(true);
        if (flag.get()) {
            System.out.println("flag set");
        }
        System.out.println(describe(99));
        System.out.println(describe(100));
    }

    static int firstOf(Box<Integer> b) {
        return b.get();
    }

    static Box<Integer> makeInts() {
        Box<Integer> b = new Box<Integer>();
        b.set(1000);
        return b;
    }

    static int sumBoxed(Integer a, Integer b) {
        return a + b;
    }

    static String describe(Integer value) {
        return "value=" + value + " hash=" + value.hashCode();
    }
}

class Box<T> {
    private T item;

    void set(T item) {
        this.item = item;
    }

    T get() {
        return item;
    }
}

class Pair<A, B> {
    private final A a;
    private final B b;

    Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    A first() {
        return a;
    }

    B second() {
        return b;
    }
}
