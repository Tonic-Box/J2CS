import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CollectionGaps {
    public static void main(String[] args) {
        collections();
        arraysPrimitive();
        arraysDeep();
        System.out.println("done");
    }

    static void collections() {
        List<Integer> l = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Collections.shuffle(l, new Random(42));
        System.out.println(l);

        List<Integer> sorted = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9, 11));
        System.out.println(Collections.binarySearch(sorted, 7));
        System.out.println(Collections.binarySearch(sorted, 8));
        System.out.println(Collections.binarySearch(sorted, 6, Collections.reverseOrder(Collections.reverseOrder())));

        List<String> sw = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        Collections.swap(sw, 0, 3);
        System.out.println(sw);

        List<Integer> rot = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Collections.rotate(rot, 2);
        System.out.println(rot);
        Collections.rotate(rot, -1);
        System.out.println(rot);

        System.out.println(Collections.nCopies(3, "x"));

        List<String> dst = new ArrayList<>(Arrays.asList("a", "b", "c"));
        System.out.println(Collections.addAll(dst, "d", "e"));
        System.out.println(dst);

        System.out.println(Collections.disjoint(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6)));
        System.out.println(Collections.disjoint(Arrays.asList(1, 2, 3), Arrays.asList(3, 4, 5)));

        List<Integer> fillMe = new ArrayList<>(Arrays.asList(0, 0, 0));
        Collections.fill(fillMe, 7);
        System.out.println(fillMe);

        List<Integer> rep = new ArrayList<>(Arrays.asList(1, 2, 1, 3, 1));
        System.out.println(Collections.replaceAll(rep, 1, 9));
        System.out.println(rep);

        List<Integer> copyDst = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        Collections.copy(copyDst, Arrays.asList(5, 6, 7));
        System.out.println(copyDst);

        System.out.println(Collections.singleton("only"));
        System.out.println(Collections.singletonMap("k", "v"));

        List<Integer> ro = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2));
        Collections.sort(ro, Collections.reverseOrder());
        System.out.println(ro);

        System.out.println(Collections.max(Arrays.asList(3, 1, 4, 1, 5)));
        System.out.println(Collections.min(Arrays.asList(3, 1, 4, 1, 5)));
        System.out.println(Collections.frequency(Arrays.asList(1, 2, 1, 3, 1), 1));
    }

    static void arraysPrimitive() {
        System.out.println(Arrays.toString(new byte[]{-1, 2, 127}));
        System.out.println(Arrays.toString(new short[]{-1, 300, 5}));
        System.out.println(Arrays.toString(new float[]{1.5f, 2.5f, -3.0f}));

        System.out.println(Arrays.hashCode(new int[]{1, 2, 3}));
        System.out.println(Arrays.hashCode(new long[]{1L, 2L, 3L}));
        System.out.println(Arrays.hashCode(new double[]{1.0, 2.0}));
        System.out.println(Arrays.hashCode(new char[]{'a', 'b'}));
        System.out.println(Arrays.hashCode(new byte[]{1, 2}));
        System.out.println(Arrays.hashCode(new short[]{1, 2}));
        System.out.println(Arrays.hashCode(new float[]{1.5f, 2.5f}));
        System.out.println(Arrays.hashCode(new Object[]{"a", "b", null}));

        double[] fd = new double[3];
        Arrays.fill(fd, 2.5);
        System.out.println(Arrays.toString(fd));
        byte[] fb = new byte[3];
        Arrays.fill(fb, (byte) 4);
        System.out.println(Arrays.toString(fb));

        float[] sf = {3.0f, 1.0f, 2.0f};
        Arrays.sort(sf);
        System.out.println(Arrays.toString(sf));
        byte[] sb = {3, 1, 2, -5};
        Arrays.sort(sb);
        System.out.println(Arrays.toString(sb));
        short[] ss = {30, 10, 20};
        Arrays.sort(ss);
        System.out.println(Arrays.toString(ss));

        System.out.println(Arrays.stream(new long[]{1L, 2L, 3L}).sum());
        System.out.println(Arrays.stream(new double[]{1.0, 2.0, 3.0}).sum());

        System.out.println(Arrays.toString(Arrays.copyOf(new long[]{1L, 2L}, 4)));
        System.out.println(Arrays.toString(Arrays.copyOf(new char[]{'a', 'b'}, 3)));
        System.out.println(Arrays.toString(Arrays.copyOfRange(new long[]{1L, 2L, 3L, 4L}, 1, 3)));

        System.out.println(Arrays.equals(new long[]{1L, 2L}, new long[]{1L, 2L}));
        System.out.println(Arrays.equals(new double[]{1.0, 2.0}, new double[]{1.0, 3.0}));
        System.out.println(Arrays.equals(new char[]{'a'}, new char[]{'a'}));

        System.out.println(Arrays.binarySearch(new long[]{1L, 3L, 5L, 7L}, 5L));
        System.out.println(Arrays.binarySearch(new char[]{'a', 'c', 'e'}, 'c'));
        Integer[] oa = {1, 3, 5, 7};
        System.out.println(Arrays.binarySearch(oa, 5));
        System.out.println(Arrays.binarySearch(oa, 4));

        Integer[] setme = new Integer[5];
        Arrays.setAll(setme, i -> i * i);
        System.out.println(Arrays.toString(setme));
    }

    static void arraysDeep() {
        Object[] nested = {new int[]{1, 2}, new String[]{"a", "b"}, new Object[]{new int[]{3}}};
        System.out.println(Arrays.deepToString(nested));

        Object[] p = {new int[]{1, 2}, new int[]{3, 4}};
        Object[] q = {new int[]{1, 2}, new int[]{3, 4}};
        Object[] r = {new int[]{1, 2}, new int[]{9, 4}};
        System.out.println(Arrays.deepEquals(p, q));
        System.out.println(Arrays.deepEquals(p, r));
        System.out.println(Arrays.deepHashCode(p) == Arrays.deepHashCode(q));
        System.out.println(Arrays.deepHashCode(p) == Arrays.deepHashCode(r));
    }
}
