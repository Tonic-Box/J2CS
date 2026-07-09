import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateCal {
    public static void main(String[] args) throws Exception {
        Date d = new Date(1600000000000L);
        System.out.println(d.getTime());
        Date d2 = new Date(1600000001000L);
        System.out.println(d.before(d2));
        System.out.println(d.after(d2));
        System.out.println(d.compareTo(d2));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(sdf.format(d));
        Date parsed = sdf.parse("2021-01-15 08:30:00");
        System.out.println(parsed.getTime());

        System.out.println(TimeZone.getTimeZone("UTC").getID());

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTime(d);
        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH));
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
        System.out.println(cal.get(Calendar.MINUTE));
        System.out.println(cal.get(Calendar.SECOND));
        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_MONTH, 5);
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.YEAR, 2000);
        System.out.println(cal.get(Calendar.YEAR));

        GregorianCalendar gc = new GregorianCalendar(2024, Calendar.MARCH, 15);
        System.out.println(gc.get(Calendar.YEAR) + "-" + (gc.get(Calendar.MONTH) + 1) + "-" + gc.get(Calendar.DAY_OF_MONTH));
    }
}
