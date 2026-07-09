import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.util.Locale;

public class TimeText {
    public static void main(String[] args) {
        Instant i = Instant.ofEpochSecond(1_600_000_000L);
        System.out.println(i);
        System.out.println(i.getEpochSecond());
        System.out.println(i.plusSeconds(60).getEpochSecond());
        System.out.println(i.toEpochMilli());
        System.out.println(i.equals(Instant.ofEpochMilli(1600000000000L)));
        System.out.println(i.isBefore(i.plusSeconds(1)));

        LocalTime t = LocalTime.of(14, 30, 15);
        System.out.println(t);
        System.out.println(t.getHour() + ":" + t.getMinute() + ":" + t.getSecond());
        System.out.println(t.plusHours(2));
        System.out.println(LocalTime.of(9, 5));

        Period p = Period.of(1, 2, 3);
        System.out.println(p.getYears() + " " + p.getMonths() + " " + p.getDays());
        System.out.println(Period.ofDays(10).getDays());
        System.out.println(p);

        System.out.println(ZoneId.of("UTC").getId());
        System.out.println(Month.of(3));
        System.out.println(Month.JANUARY.getValue());
        System.out.println(Year.of(2024).isLeap());
        System.out.println(Year.of(2023).isLeap());

        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        System.out.println(nf.format(1234567.5));
        System.out.println(MessageFormat.format("{0} has {1} items", "cart", 3));
    }
}
