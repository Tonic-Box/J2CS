public class ArrayClassInfo {
    static boolean arr(Object o) {
        return o.getClass().isArray();
    }

    static String nameOf(Object o) {
        return o.getClass().getName();
    }

    static String comp(Object o) {
        return o.getClass().getComponentType().getName();
    }

    static String simple(Object o) {
        return o.getClass().getSimpleName();
    }

    public static void main(String[] args) {
        System.out.println(arr(new int[3]));
        System.out.println(nameOf(new int[3]));
        System.out.println(comp(new int[3]));
        System.out.println(simple(new int[3]));

        System.out.println(nameOf(new String[2]));
        System.out.println(comp(new String[2]));
        System.out.println(simple(new String[2]));

        System.out.println(nameOf(new int[1][1]));
        System.out.println(comp(new int[1][1]));
        System.out.println(arr(new int[1][1]));

        System.out.println(arr("hi"));
        System.out.println(nameOf("hi"));
    }
}
