import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;

public class TimeTest {
    public static void main(String[] args) {
        LocalDate d = LocalDate.of(2024, 3, 15);
        System.out.println(d);
        System.out.println(d.getYear() + " " + d.getMonthValue() + " " + d.getDayOfMonth());
        System.out.println(d.getDayOfWeek());
        System.out.println(d.getDayOfWeek().getValue());
        System.out.println(d.getDayOfYear());
        System.out.println(d.plusDays(20));
        System.out.println(d.minusDays(15));
        System.out.println(d.plusMonths(11));
        System.out.println(d.plusYears(1));
        System.out.println(d.plusWeeks(2));
        System.out.println(d.isLeapYear());
        System.out.println(d.lengthOfMonth());

        LocalDate d2 = LocalDate.of(2024, 5, 1);
        System.out.println(d.isBefore(d2));
        System.out.println(d.isAfter(d2));
        System.out.println(d.compareTo(d2));
        System.out.println(d.equals(LocalDate.of(2024, 3, 15)));

        Duration dur = Duration.ofMinutes(90);
        System.out.println(dur.toHours() + "h" + (dur.toMinutes() % 60) + "m");
        System.out.println(dur.toSeconds());
        System.out.println(dur.toMillis());
        System.out.println(Duration.ofHours(2).plus(Duration.ofMinutes(30)).toMinutes());
        System.out.println(Duration.ofSeconds(3661).toString());
        System.out.println(Duration.ofDays(1).minus(Duration.ofHours(1)).toHours());

        DayOfWeek dow = DayOfWeek.of(3);
        System.out.println(dow);
        System.out.println(dow.getValue());
    }
}
