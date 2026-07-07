import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArraysCollections {
    public static void main(String[] args) {
        int[] a = {5, 3, 8, 1, 9, 2};
        int[] b = a.clone();
        Arrays.sort(b);
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.binarySearch(b, 8));
        System.out.println(Arrays.binarySearch(b, 4));

        System.out.println(Arrays.toString(Arrays.copyOf(a, 4)));
        System.out.println(Arrays.toString(Arrays.copyOf(a, 8)));
        System.out.println(Arrays.toString(Arrays.copyOfRange(a, 1, 4)));

        System.out.println(Arrays.equals(a, a.clone()));
        System.out.println(Arrays.equals(a, b));

        int[] f = new int[5];
        Arrays.fill(f, 7);
        System.out.println(Arrays.toString(f));

        System.out.println(Arrays.stream(a).sum());

        String[] names = {"cherry", "apple", "banana"};
        Arrays.sort(names);
        System.out.println(Arrays.toString(names));

        double[] ds = {2.5, 1.5, 3.5};
        Arrays.sort(ds);
        System.out.println(Arrays.toString(ds));

        List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6);
        System.out.println(Collections.max(nums));
        System.out.println(Collections.min(nums));
        System.out.println(Collections.frequency(nums, 1));

        System.out.println(Collections.emptyList().size());
        List<String> single = Collections.singletonList("only");
        System.out.println(single);
        System.out.println(single.size());

        List<Integer> src = new ArrayList<>(Arrays.asList(10, 20, 30));
        System.out.println(Collections.unmodifiableList(src));
        System.out.println(Collections.max(src, Comparator.reverseOrder()));
    }
}
