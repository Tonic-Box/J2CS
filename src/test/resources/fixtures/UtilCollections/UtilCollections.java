import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class UtilCollections {
    public static void main(String[] args) {
        Stack<Integer> st = new Stack<>();
        st.push(1); st.push(2); st.push(3);
        System.out.println(st.peek());
        System.out.println(st.pop());
        System.out.println(st.size());
        System.out.println(st.empty());
        System.out.println(st);

        Vector<String> v = new Vector<>();
        v.add("a"); v.addElement("b"); v.add(0, "z");
        System.out.println(v);
        System.out.println(v.elementAt(1));
        System.out.println(v.firstElement() + " " + v.lastElement());

        BitSet bs = new BitSet();
        bs.set(1); bs.set(3); bs.set(5);
        System.out.println(bs.get(3));
        System.out.println(bs.cardinality());
        System.out.println(bs);
        bs.clear(3);
        System.out.println(bs.nextSetBit(2));
        BitSet bs2 = new BitSet(); bs2.set(1); bs2.set(2);
        bs.and(bs2);
        System.out.println(bs);

        Properties props = new Properties();
        props.setProperty("key1", "val1");
        System.out.println(props.getProperty("key1"));
        System.out.println(props.getProperty("missing", "default"));
        System.out.println(props.size());

        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListIterator<Integer> it = list.listIterator();
        while (it.hasNext()) { int x = it.next(); it.set(x * 10); }
        System.out.println(list);
        StringBuilder sb = new StringBuilder();
        while (it.hasPrevious()) { sb.append(it.previous()).append(' '); }
        System.out.println(sb.toString().trim());

        TreeMap<Integer, String> tm = new TreeMap<>();
        tm.put(10, "a"); tm.put(20, "b"); tm.put(30, "c");
        System.out.println(tm.firstKey() + " " + tm.lastKey());
        System.out.println(tm.floorKey(25) + " " + tm.ceilingKey(25));
        System.out.println(tm.higherKey(20) + " " + tm.lowerKey(20));

        TreeSet<Integer> ts = new TreeSet<>(Arrays.asList(5, 1, 3, 9, 7));
        System.out.println(ts);
        System.out.println(ts.first() + " " + ts.last());
        System.out.println(ts.floor(6) + " " + ts.ceiling(6));
        System.out.println(ts.higher(5) + " " + ts.lower(5));
    }
}
