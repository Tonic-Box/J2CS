import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsOps {
    static final class P {
        final String dept;
        final String name;
        final int salary;
        P(String dept, String name, int salary) { this.dept = dept; this.name = name; this.salary = salary; }
        String getDept() { return dept; }
        String getName() { return name; }
        int getSalary() { return salary; }
        public String toString() { return name; }
    }

    public static void main(String[] args) {
        List<P> ppl = Arrays.asList(
                new P("eng", "alice", 100),
                new P("eng", "bob", 120),
                new P("sales", "carol", 90),
                new P("sales", "dave", 110),
                new P("eng", "erin", 130));

        int totalSalary = ppl.stream().collect(Collectors.summingInt((P p) -> p.getSalary()));
        System.out.println(totalSalary);

        double avg = ppl.stream().collect(Collectors.averagingInt((P p) -> p.getSalary()));
        System.out.println(avg);

        Map<String, Long> countByDept = ppl.stream()
                .collect(Collectors.groupingBy((P p) -> p.getDept(), Collectors.counting()));
        System.out.println(countByDept.get("eng"));
        System.out.println(countByDept.get("sales"));

        Map<String, Integer> salaryByDept = ppl.stream()
                .collect(Collectors.groupingBy((P p) -> p.getDept(), Collectors.summingInt((P p) -> p.getSalary())));
        System.out.println(salaryByDept.get("eng"));
        System.out.println(salaryByDept.get("sales"));

        Map<String, List<String>> namesByDept = ppl.stream()
                .collect(Collectors.groupingBy((P p) -> p.getDept(),
                        Collectors.mapping((P p) -> p.getName(), Collectors.toList())));
        System.out.println(namesByDept.get("eng"));
        System.out.println(namesByDept.get("sales"));

        Map<Boolean, List<P>> byHigh = ppl.stream()
                .collect(Collectors.partitioningBy((P p) -> p.getSalary() >= 110));
        System.out.println(byHigh.get(true));
        System.out.println(byHigh.get(false));
        System.out.println(byHigh.get(true).size());
        System.out.println(byHigh.get(false).size());
    }
}
