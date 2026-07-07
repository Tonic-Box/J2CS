public class NumericConstants {
    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.MIN_VALUE);
        System.out.println(Short.MAX_VALUE);
        System.out.println(Short.MIN_VALUE);
        System.out.println(Byte.MAX_VALUE);
        System.out.println(Byte.MIN_VALUE);
        System.out.println((int) Character.MAX_VALUE);
        System.out.println((int) Character.MIN_VALUE);
        System.out.println(Integer.SIZE);
        System.out.println(Long.SIZE);

        System.out.println(Double.MAX_VALUE > 1.0e308);
        System.out.println(Double.MIN_VALUE > 0.0);
        System.out.println(Float.MAX_VALUE > 3.0e38f);

        System.out.println(Math.PI);
        System.out.println(Math.E);
        System.out.println(Math.sqrt(2.0));
        System.out.println(Math.pow(2.0, 10.0));
        System.out.println(Math.abs(-5));
        System.out.println(Math.abs(-5.5));
        System.out.println(Math.max(3, 7));
        System.out.println(Math.min(3, 7));
        System.out.println(Math.floor(3.7));
        System.out.println(Math.ceil(3.2));
        System.out.println(Math.round(3.5));

        long sum = (long) Integer.MAX_VALUE + 1L;
        System.out.println(sum);
    }
}
