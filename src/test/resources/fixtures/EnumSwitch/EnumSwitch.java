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

    static int severity(Level level) {
        int score = 0;
        switch (level) {
            case ERROR:
                score += 100;
            case WARNING:
                score += 10;
                break;
            case SUCCESS:
            case INFO:
                score += 1;
                break;
        }
        return score;
    }

    public static void main(String[] args) {
        for (Level level : Level.values()) {
            System.out.println(level + "=" + color(level) + ":" + severity(level));
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
