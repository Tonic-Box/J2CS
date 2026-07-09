import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Concurrency {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(2);
        latch.countDown();
        System.out.println(latch.getCount());
        latch.countDown();
        System.out.println(latch.getCount());
        latch.await();
        System.out.println("latch done");

        Semaphore sem = new Semaphore(2);
        sem.acquire();
        System.out.println(sem.availablePermits());
        System.out.println(sem.tryAcquire());
        System.out.println(sem.availablePermits());
        System.out.println(sem.tryAcquire());
        sem.release();
        System.out.println(sem.availablePermits());

        BlockingQueue<Integer> bq = new LinkedBlockingQueue<>();
        bq.offer(1); bq.offer(2); bq.offer(3);
        System.out.println(bq.size());
        System.out.println(bq.poll());
        System.out.println(bq.peek());
        System.out.println(bq.take());
        System.out.println(bq.size());

        ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<>(2);
        System.out.println(abq.offer("a"));
        System.out.println(abq.offer("b"));
        System.out.println(abq.offer("c"));
        System.out.println(abq.remainingCapacity());

        ConcurrentLinkedQueue<Integer> clq = new ConcurrentLinkedQueue<>();
        clq.offer(10); clq.offer(20);
        System.out.println(clq.poll() + " " + clq.peek());
        System.out.println(clq.size());

        CyclicBarrier barrier = new CyclicBarrier(1);
        System.out.println(barrier.await());
        System.out.println(barrier.getParties());
    }
}
