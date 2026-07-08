import java.util.function.Function;
import java.util.function.IntFunction;

public class LambdaArrayAdapt {
    static Object head(Object[] a) {
        return a[0];
    }

    public static void main(String[] args) {
        Function<Object[], Object> f = LambdaArrayAdapt::head;
        System.out.println(f.apply(new Object[]{"x", "y"}));

        IntFunction<String[]> ctor = String[]::new;
        String[] arr = ctor.apply(3);
        arr[0] = "a";
        System.out.println(arr.length + " " + arr[0]);

        Function<int[], Integer> len = z -> z.length;
        System.out.println(len.apply(new int[]{5, 6, 7, 8}));
    }
}
