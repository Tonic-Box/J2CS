import java.lang.reflect.Array;

public class ReflectArray {
    public static void main(String[] args) {
        int[] ia = {5, 6, 7};
        System.out.println(Array.getLength(ia));
        System.out.println(Array.getInt(ia, 1));
        System.out.println(Array.get(ia, 2));
        Array.set(ia, 0, 99);
        System.out.println(ia[0]);
        System.out.println(Array.getLong(ia, 2));

        Object ints = Array.newInstance(int.class, 4);
        Array.set(ints, 0, 42);
        System.out.println(Array.getLength(ints));
        System.out.println(Array.getInt(ints, 0));
        int[] ib = (int[]) ints;
        System.out.println(ib.length + " " + ib[0]);

        Object strs = Array.newInstance(String.class, 3);
        Array.set(strs, 0, "a");
        Array.set(strs, 1, "b");
        System.out.println(Array.get(strs, 0));
        String[] sarr = (String[]) strs;
        System.out.println(sarr[1]);

        Object doubles = Array.newInstance(double.class, 2);
        Array.set(doubles, 0, 1.5);
        System.out.println(Array.getDouble(doubles, 0));
    }
}
