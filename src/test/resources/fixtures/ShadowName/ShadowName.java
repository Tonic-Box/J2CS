public class ShadowName {
    public static void main(String[] args) {
        // A user type whose simple name collides with a shim type (java.util.Random). The
        // using-rewriter must keep both references global:: rather than mis-shorten either.
        Random own = new Random(5);
        System.out.println(own.tag());

        java.util.Random shim = new java.util.Random(42);
        System.out.println(shim.nextInt(100));
        System.out.println(shim.nextInt(100));

        System.out.println("done");
    }
}

class Random {
    private final int seed;

    Random(int seed) {
        this.seed = seed;
    }

    int tag() {
        return seed * 3 + 1;
    }
}
