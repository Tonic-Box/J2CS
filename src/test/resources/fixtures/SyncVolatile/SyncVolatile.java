public class SyncVolatile {
    private volatile int counter;
    private volatile boolean flag;
    private volatile long total;
    private static volatile int shared;

    synchronized void bump(int n) {
        counter += n;
        total += n;
        flag = !flag;
    }

    static synchronized void addShared(int n) {
        shared += n;
    }

    synchronized int snapshot() {
        return counter;
    }

    public static void main(String[] args) {
        SyncVolatile s = new SyncVolatile();
        for (int i = 0; i < 5; i++) {
            s.bump(i);
            addShared(i);
        }
        System.out.println("counter=" + s.snapshot());
        System.out.println("total=" + s.total);
        System.out.println("flag=" + s.flag);
        System.out.println("shared=" + shared);
    }
}
