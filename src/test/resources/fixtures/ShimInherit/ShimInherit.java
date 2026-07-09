public class ShimInherit {
    public static void main(String[] args) {
        Boom b = new Boom("kaboom");
        System.out.println(b.describe());
        System.out.println(b.getMessage());
        try {
            throw new Boom("thrown");
        } catch (RuntimeException e) {
            System.out.println("caught: " + e.getMessage());
        }
    }
}

class Boom extends RuntimeException {
    Boom(String msg) {
        super(msg);
    }

    String describe() {
        return "Boom[" + getMessage() + "]";
    }
}
