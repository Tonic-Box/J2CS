import java.util.ArrayList;
import java.util.List;

public class Collections1 {
    public static void main(String[] args) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        System.out.println(nums.size());
        System.out.println(nums.get(0) + nums.get(1) + nums.get(2));
        nums.set(1, 20);
        System.out.println(nums);
        System.out.println(nums.isEmpty());
        System.out.println(nums.contains(20));
        System.out.println(nums.contains(99));
        System.out.println(nums.indexOf(3));

        int total = 0;
        for (int n : nums) {
            total += n;
        }
        System.out.println(total);

        nums.remove(0);
        System.out.println(nums);
        System.out.println(nums.size());

        List<String> words = new ArrayList<String>();
        words.add("alpha");
        words.add("beta");
        words.add(0, "gamma");
        for (String w : words) {
            System.out.println(w);
        }
        System.out.println(words);

        ArrayList<Integer> squares = new ArrayList<Integer>();
        for (int i = 1; i <= 4; i++) {
            squares.add(i * i);
        }
        System.out.println(squares);
        int sum = 0;
        for (int i = 0; i < squares.size(); i++) {
            sum += squares.get(i);
        }
        System.out.println(sum);
        System.out.println(sumList(squares));
    }

    static int sumList(List<Integer> list) {
        int s = 0;
        for (int v : list) {
            s += v;
        }
        return s;
    }
}
