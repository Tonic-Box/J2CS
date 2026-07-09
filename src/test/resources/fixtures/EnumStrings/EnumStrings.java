import java.util.EnumMap;
import java.util.EnumSet;
import java.util.stream.Collectors;

public class EnumStrings {
    enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

    public static void main(String[] args) {
        EnumSet<Day> weekend = EnumSet.of(Day.SAT, Day.SUN);
        System.out.println(weekend);
        System.out.println(weekend.contains(Day.SAT));
        System.out.println(weekend.size());
        EnumSet<Day> some = EnumSet.of(Day.WED, Day.MON, Day.FRI);
        System.out.println(some);
        EnumSet<Day> none = EnumSet.noneOf(Day.class);
        none.add(Day.TUE); none.add(Day.MON);
        System.out.println(none);

        EnumMap<Day, String> m = new EnumMap<>(Day.class);
        m.put(Day.FRI, "party");
        m.put(Day.MON, "work");
        m.put(Day.WED, "gym");
        System.out.println(m);
        System.out.println(m.get(Day.MON));
        System.out.println(m.size());
        System.out.println(m.containsKey(Day.TUE));

        System.out.println("  hi  ".stripLeading() + "|");
        System.out.println("|" + "  hi  ".stripTrailing());
        System.out.println("   ".isBlank());
        System.out.println("x".isBlank());
        System.out.println("hello".chars().sum());
        System.out.println("a\nb\nc".lines().collect(Collectors.joining("-")));
        System.out.println("Hi %s, you have %d".formatted("Bob", 3));
    }
}
