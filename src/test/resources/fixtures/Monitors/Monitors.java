public class Monitors {
    static int counter = 0;
    static final Object LOCK = new Object();

    public static void main(String[] args) {
        synchronized (LOCK) {
            counter += 5;
        }
        System.out.println(counter);

        Monitors m = new Monitors();
        System.out.println(m.addSync(3));

        synchronized (m) {
            int local = counter * 2;
            if (local > 10) {
                counter = local;
            }
        }
        System.out.println(counter);
        System.out.println(nestedSync());
    }

    synchronized int addSync(int x) {
        counter += x;
        return counter;
    }

    static int nestedSync() {
        int result = 0;
        synchronized (LOCK) {
            for (int i = 0; i < 4; i++) {
                synchronized (Monitors.class) {
                    result += i;
                }
            }
        }
        return result;
    }
}
