import java.util.ArrayList;
import java.util.List;

public class CollectionsBootstrap {
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
        for (String w : words) {
            System.out.println(w);
        }
        System.out.println(words);

        ArrayList<Integer> sq = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++) {
            sq.add(i * i);
        }
        System.out.println(sq);
        System.out.println(sq.size());
        int sum = 0;
        for (int i = 0; i < sq.size(); i++) {
            sum += sq.get(i);
        }
        System.out.println(sum);
        System.out.println("done");
    }
}
