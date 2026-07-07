import java.util.Arrays;
import java.util.List;

public class BoundMethodRef {
    public static void main(String[] args) {
        List<String> chunks = Arrays.asList("a", "b", "c");
        chunks.forEach(System.out::println);

        List<Integer> nums = Arrays.asList(3, 1, 2);
        nums.stream().sorted().forEach(System.out::println);
    }
}
