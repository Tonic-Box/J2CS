public class RuntimeMem {
    public static void main(String[] args) {
        Runtime r = Runtime.getRuntime();
        long total = r.totalMemory();
        long free = r.freeMemory();
        long max = r.maxMemory();
        long used = total - free;
        System.out.println(used >= 0);
        System.out.println(free >= 0);
        System.out.println(used <= total);
        System.out.println(total <= max);
        System.out.println(free <= total);
        System.out.println(total > 0);
    }
}
