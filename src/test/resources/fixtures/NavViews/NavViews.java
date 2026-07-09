import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

public class NavViews {
    public static void main(String[] args) {
        TreeSet<Integer> ts = new TreeSet<>(Arrays.asList(1, 3, 5, 7, 9));
        System.out.println(ts.descendingSet());
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> di = ts.descendingIterator();
        while (di.hasNext()) { sb.append(di.next()).append(' '); }
        System.out.println(sb.toString().trim());
        System.out.println(ts.headSet(5, true));
        System.out.println(ts.tailSet(5, false));
        System.out.println(ts.subSet(3, true, 7, true));

        TreeMap<Integer, String> tm = new TreeMap<>();
        tm.put(1, "a"); tm.put(3, "b"); tm.put(5, "c"); tm.put(7, "d");
        System.out.println(tm.descendingKeySet());
        System.out.println(tm.navigableKeySet());
        System.out.println(tm.firstEntry());
        System.out.println(tm.lastEntry());
        System.out.println(tm.floorEntry(4));
        System.out.println(tm.ceilingEntry(4));
        System.out.println(tm.higherEntry(3));
        System.out.println(tm.lowerEntry(3));
        System.out.println(tm.headMap(5, true));
        System.out.println(tm.tailMap(3, false));
    }
}
