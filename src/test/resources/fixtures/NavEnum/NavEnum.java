import java.util.Arrays;
import java.util.EnumSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class NavEnum {
    enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }

    static final ThreadLocal<Integer> TL = ThreadLocal.withInitial(() -> 42);

    public static void main(String[] args) {
        System.out.println(EnumSet.allOf(Suit.class));
        System.out.println(EnumSet.allOf(Suit.class).size());

        System.out.println(TL.get());
        TL.set(100);
        System.out.println(TL.get());

        TreeSet<Integer> ts = new TreeSet<>(Arrays.asList(1, 3, 5, 7, 9));
        System.out.println(ts.headSet(5));
        System.out.println(ts.tailSet(5));
        System.out.println(ts.subSet(3, 8));

        TreeMap<Integer, String> tm = new TreeMap<>();
        tm.put(1, "a"); tm.put(3, "b"); tm.put(5, "c"); tm.put(7, "d");
        System.out.println(tm.headMap(5));
        System.out.println(tm.tailMap(3));
        System.out.println(tm.subMap(2, 6));
    }
}
