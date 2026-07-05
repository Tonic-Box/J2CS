public class EnumSwitch {
    enum Level { INFO, SUCCESS, WARNING, ERROR }

    static String color(Level level) {
        switch (level) {
            case ERROR:
                return "red";
            case WARNING:
                return "orange";
            case SUCCESS:
                return "green";
            case INFO:
            default:
                return "gray";
        }
    }

    public static void main(String[] args) {
        for (Level level : Level.values()) {
            System.out.println(level + "=" + color(level));
        }
        // Exercise it via a lambda too — the shape that regressed in the demo.
        Runnable r = () -> {
            for (Level level : Level.values()) {
                System.out.print(color(level).charAt(0));
            }
            System.out.println();
        };
        r.run();
    }
}
