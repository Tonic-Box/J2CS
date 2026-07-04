import java.util.Objects;

public class ObjectsBootstrap {
    public static void main(String[] args) {
        String a = "x";
        String b = "x";
        String c = "y";
        System.out.println(Objects.equals(a, b));
        System.out.println(Objects.equals(a, c));
        System.out.println(Objects.equals(null, a));
        System.out.println(Objects.equals(null, null));
        System.out.println(Objects.hashCode(a));
        System.out.println(Objects.hashCode(null));
        System.out.println(Objects.isNull(null));
        System.out.println(Objects.isNull(a));
        System.out.println(Objects.nonNull(a));
        System.out.println(Objects.requireNonNull(a) == a);

        try {
            Objects.requireNonNull(null);
        } catch (NullPointerException e) {
            System.out.println("npe1");
        }

        try {
            Objects.requireNonNull(null, "must not be null");
        } catch (NullPointerException e) {
            System.out.println("npe2");
            System.out.println(e.getMessage());
        }
        System.out.println("done");
    }
}
