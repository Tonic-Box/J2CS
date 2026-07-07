import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class CollectionsExtra {
    public static void main(String[] args) {
        TreeSet<Integer> ts = new TreeSet<>();
        ts.add(5); ts.add(1); ts.add(3); ts.add(1); ts.add(9); ts.add(2);
        System.out.println(ts);
        System.out.println(ts.size());
        System.out.println(ts.first() + " " + ts.last());
        System.out.println(ts.contains(3));
        System.out.println(ts.contains(4));
        System.out.println(ts.floor(4) + " " + ts.ceiling(4));
        ts.remove(5);
        System.out.println(ts);
        System.out.println(ts.pollFirst() + " " + ts.pollLast());
        System.out.println(ts);

        TreeSet<String> rev = new TreeSet<>(Comparator.reverseOrder());
        rev.add("apple"); rev.add("cherry"); rev.add("banana");
        System.out.println(rev);

        Deque<Integer> dq = new ArrayDeque<>();
        dq.push(1); dq.push(2); dq.addLast(3);
        System.out.println(dq);
        System.out.println(dq.peekFirst() + " " + dq.peekLast());
        System.out.println(dq.pop());
        System.out.println(dq.pollLast());
        System.out.println(dq);

        Deque<String> q = new ArrayDeque<>();
        q.offer("a"); q.offer("b"); q.offer("c");
        System.out.println(q.poll() + q.poll());
        System.out.println(q.peek());

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(5); pq.offer(1); pq.offer(3); pq.offer(2); pq.offer(4);
        System.out.println(pq.size());
        StringBuilder sb = new StringBuilder();
        while (pq.isEmpty() == false) {
            sb.append(pq.poll());
        }
        System.out.println(sb);

        PriorityQueue<Integer> maxpq = new PriorityQueue<>(Comparator.reverseOrder());
        maxpq.offer(5); maxpq.offer(1); maxpq.offer(3);
        System.out.println(maxpq.poll() + " " + maxpq.poll());
    }
}
