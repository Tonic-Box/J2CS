public class StaticsTest {
    public static void main(String[] args) {
        System.out.println(A.x);
        System.out.println(B.y);
        System.out.println(A.x);
        A.x = 99;
        System.out.println(A.x);
        System.out.println(B.y);
        System.out.println(Holder.VALUE);
        System.out.println(Chain.first);
        System.out.println(Chain.second);
    }
}

class A {
    static int x;

    static {
        x = 10;
    }
}

class B {
    static int y;

    static {
        y = A.x + 5;
    }
}

class Holder {
    static final int VALUE;

    static {
        VALUE = compute();
    }

    static int compute() {
        return 7 * 6;
    }
}

class Chain {
    static int first = seed();
    static int second = first * 2;

    static int seed() {
        return 21;
    }
}
