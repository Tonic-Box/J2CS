public class MathBootstrap {
    public static void main(String[] args) {
        System.out.println(Math.abs(-7));
        System.out.println(Math.abs(-7L));
        System.out.println(Math.abs(-2.5));
        System.out.println(Math.max(3, 8));
        System.out.println(Math.min(3, 8));
        System.out.println(Math.max(3L, 8L));
        System.out.println(Math.max(2.5, 9.5));
        System.out.println(Math.min(2.5, 9.5));

        System.out.println(Math.sqrt(16.0));
        System.out.println(Math.sqrt(2.0));
        System.out.println(Math.floor(3.7));
        System.out.println(Math.ceil(3.2));
        System.out.println(Math.round(3.5));
        System.out.println(Math.round(2.4));

        System.out.println(Math.floorDiv(7, 2));
        System.out.println(Math.floorDiv(-7, 2));
        System.out.println(Math.floorMod(-7, 3));
        System.out.println(Math.addExact(100, 200));
        System.out.println(Math.multiplyExact(6, 7));
        System.out.println(Math.toIntExact(42L));

        try {
            Math.addExact(Integer.MAX_VALUE, 1);
        } catch (ArithmeticException e) {
            System.out.println("overflow caught");
        }

        double h = Math.sqrt(Math.abs(-9.0)) + Math.floor(1.9);
        System.out.println(h);
        System.out.println("done");
    }
}
