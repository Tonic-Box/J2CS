import java.util.Arrays;
import java.util.List;

public class VarargsInterface {
    public static void main(String[] args) {
        List<Integer> a = Arrays.asList(1, 2);
        List<Integer> b = Arrays.asList(3, 4);
        List<List<Integer>> nested = Arrays.asList(a, b);
        System.out.println(nested.size());
        System.out.println(nested.get(0).get(0) + nested.get(1).get(1));
        System.out.println(nested);
    }
}
