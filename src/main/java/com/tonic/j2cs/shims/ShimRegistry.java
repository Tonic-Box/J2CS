package com.tonic.j2cs.shims;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The single source of truth for what the hand-written JavaCompat shim implements.
 * Keys are owner internal name + "." + member name + descriptor. A java/javax member
 * missing here makes the calling method Unsupported with a precise report line.
 */
public final class ShimRegistry {

    private static <V> void put(Map<String, V> m, String key, V value) {
        if (m.put(key, value) != null) {
            throw new IllegalStateException("duplicate shim key: " + key);
        }
    }

    private static final Map<String, String> SHIM_SUPERS = buildSupers();

    private static Map<String, String> buildSupers() {
        Map<String, String> m = new java.util.HashMap<>(512);
        addLangSupers0(m);
        addUtilSupers0(m);
        addLangSupers1(m);
        addUtilSupers1(m);
        addLangSupers2(m);
        addUtilSupers2(m);
        addStreamSupers0(m);
        addFunctionSupers0(m);
        addUtilSupers3(m);
        addRegexSupers0(m);
        addConcurrentSupers0(m);
        addTimeSupers0(m);
        addSecuritySupers0(m);
        addUtilSupers4(m);
        addNioSupers0(m);
        addLangSupers3(m);
        addFunctionSupers1(m);
        addUtilSupers5(m);
        addLangSupers4(m);
        addAwtSupers0(m);
        addSwingSupers0(m);
        addAwtSupers1(m);
        addSwingSupers1(m);
        addLangSupers5(m);
        addSwingSupers2(m);
        addAwtSupers2(m);
        addSwingSupers3(m);
        addAwtSupers3(m);
        addUtilSupers6(m);
        addIoSupers0(m);
        addLangSupers6(m);
        return Map.copyOf(m);
    }

    private static void addLangSupers0(Map<String, String> m) {
        put(m, "java/lang/String", "java/lang/Object");
    }

    private static void addUtilSupers0(Map<String, String> m) {
        put(m, "java/util/Objects", "java/lang/Object");
    }

    private static void addLangSupers1(Map<String, String> m) {
        put(m, "java/lang/StringBuilder", "java/lang/Object");
        put(m, "java/lang/System", "java/lang/Object");
        put(m, "java/lang/Math", "java/lang/Object");
        put(m, "java/lang/Number", "java/lang/Object");
        put(m, "java/lang/Integer", "java/lang/Number");
        put(m, "java/lang/Long", "java/lang/Number");
        put(m, "java/lang/Double", "java/lang/Number");
        put(m, "java/lang/Float", "java/lang/Number");
        put(m, "java/lang/Short", "java/lang/Number");
        put(m, "java/lang/Byte", "java/lang/Number");
        put(m, "java/lang/Boolean", "java/lang/Object");
        put(m, "java/lang/Void", "java/lang/Object");
        put(m, "java/lang/Character", "java/lang/Object");
    }

    private static void addUtilSupers1(Map<String, String> m) {
        put(m, "java/util/Iterable", "java/lang/Object");
        put(m, "java/util/Iterator", "java/lang/Object");
        put(m, "java/util/Collection", "java/util/Iterable");
        put(m, "java/util/List", "java/util/Collection");
        put(m, "java/util/ArrayList", "java/util/List");
        put(m, "java/util/Set", "java/util/Collection");
        put(m, "java/util/Map", "java/lang/Object");
        put(m, "java/util/Map$Entry", "java/lang/Object");
        put(m, "java/util/HashMap", "java/util/Map");
        put(m, "java/util/HashSet", "java/util/Set");
        put(m, "java/util/TreeSet", "java/util/Set");
        put(m, "java/util/Queue", "java/util/Collection");
        put(m, "java/util/Deque", "java/util/Queue");
        put(m, "java/util/ArrayDeque", "java/util/Deque");
        put(m, "java/util/PriorityQueue", "java/util/Queue");
        put(m, "java/util/UUID", "java/lang/Object");
    }

    private static void addLangSupers2(Map<String, String> m) {
        put(m, "java/lang/InterruptedException", "java/lang/Exception");
        put(m, "java/lang/IllegalAccessException", "java/lang/Exception");
        put(m, "java/lang/NoSuchFieldException", "java/lang/Exception");
        put(m, "java/lang/NoSuchMethodException", "java/lang/Exception");
        put(m, "java/lang/InstantiationException", "java/lang/Exception");
        put(m, "java/lang/reflect/InvocationTargetException", "java/lang/Exception");
        put(m, "java/lang/Thread", "java/lang/Object");
        put(m, "java/lang/Runtime", "java/lang/Object");
        put(m, "java/lang/CharSequence", "java/lang/Object");
        put(m, "java/lang/Comparable", "java/lang/Object");
    }

    private static void addUtilSupers2(Map<String, String> m) {
        put(m, "java/util/Optional", "java/lang/Object");
        put(m, "java/util/Comparator", "java/lang/Object");
        put(m, "java/util/Collections", "java/lang/Object");
    }

    private static void addStreamSupers0(Map<String, String> m) {
        put(m, "java/util/stream/Stream", "java/lang/Object");
        put(m, "java/util/stream/IntStream", "java/lang/Object");
        put(m, "java/util/stream/LongStream", "java/lang/Object");
        put(m, "java/util/stream/DoubleStream", "java/lang/Object");
        put(m, "java/util/stream/Collector", "java/lang/Object");
        put(m, "java/util/stream/Collectors", "java/lang/Object");
    }

    private static void addFunctionSupers0(Map<String, String> m) {
        put(m, "java/util/function/Function", "java/lang/Object");
        put(m, "java/util/function/BiFunction", "java/lang/Object");
        put(m, "java/util/function/BinaryOperator", "java/util/function/BiFunction");
        put(m, "java/util/function/Consumer", "java/lang/Object");
        put(m, "java/util/function/BiConsumer", "java/lang/Object");
        put(m, "java/util/function/Supplier", "java/lang/Object");
        put(m, "java/util/function/IntFunction", "java/lang/Object");
        put(m, "java/util/function/ToLongFunction", "java/lang/Object");
        put(m, "java/util/function/ToIntFunction", "java/lang/Object");
        put(m, "java/util/function/ToDoubleFunction", "java/lang/Object");
        put(m, "java/util/function/IntToDoubleFunction", "java/lang/Object");
        put(m, "java/util/function/DoublePredicate", "java/lang/Object");
        put(m, "java/util/function/DoubleConsumer", "java/lang/Object");
        put(m, "java/util/function/IntConsumer", "java/lang/Object");
        put(m, "java/util/function/IntPredicate", "java/lang/Object");
        put(m, "java/util/function/IntUnaryOperator", "java/lang/Object");
        put(m, "java/util/function/IntBinaryOperator", "java/lang/Object");
    }

    private static void addUtilSupers3(Map<String, String> m) {
        put(m, "java/util/OptionalInt", "java/lang/Object");
        put(m, "java/util/OptionalDouble", "java/lang/Object");
        put(m, "java/util/Random", "java/lang/Object");
    }

    private static void addRegexSupers0(Map<String, String> m) {
        put(m, "java/util/regex/Pattern", "java/lang/Object");
        put(m, "java/util/regex/Matcher", "java/lang/Object");
    }

    private static void addConcurrentSupers0(Map<String, String> m) {
        put(m, "java/util/concurrent/TimeUnit", "java/lang/Object");
        put(m, "java/util/concurrent/ConcurrentHashMap", "java/util/HashMap");
        put(m, "java/util/concurrent/CopyOnWriteArrayList", "java/util/ArrayList");
        put(m, "java/util/concurrent/atomic/AtomicInteger", "java/lang/Number");
        put(m, "java/util/concurrent/atomic/AtomicLong", "java/lang/Number");
        put(m, "java/util/concurrent/atomic/AtomicBoolean", "java/lang/Object");
        put(m, "java/util/concurrent/atomic/AtomicReference", "java/lang/Object");
        put(m, "java/util/concurrent/Callable", "java/lang/Object");
        put(m, "java/util/concurrent/Executor", "java/lang/Object");
        put(m, "java/util/concurrent/ThreadFactory", "java/lang/Object");
        put(m, "java/util/concurrent/Future", "java/lang/Object");
        put(m, "java/util/concurrent/ExecutorService", "java/lang/Object");
        put(m, "java/util/concurrent/ScheduledExecutorService", "java/util/concurrent/ExecutorService");
        put(m, "java/util/concurrent/ScheduledFuture", "java/util/concurrent/Future");
        put(m, "java/util/concurrent/Executors", "java/lang/Object");
        put(m, "java/util/concurrent/CompletableFuture", "java/util/concurrent/Future");
        put(m, "java/util/concurrent/locks/Lock", "java/lang/Object");
        put(m, "java/util/concurrent/locks/ReentrantLock", "java/lang/Object");
    }

    private static void addTimeSupers0(Map<String, String> m) {
        put(m, "java/time/LocalDateTime", "java/lang/Object");
        put(m, "java/time/LocalDate", "java/lang/Object");
        put(m, "java/time/Duration", "java/lang/Object");
        put(m, "java/time/DayOfWeek", "java/lang/Object");
        put(m, "java/time/format/DateTimeFormatter", "java/lang/Object");
        put(m, "java/time/temporal/ChronoUnit", "java/lang/Object");
        put(m, "java/time/temporal/Temporal", "java/lang/Object");
        put(m, "java/time/chrono/ChronoLocalDateTime", "java/lang/Object");
        put(m, "java/time/chrono/ChronoLocalDate", "java/time/temporal/Temporal");
    }

    private static void addSecuritySupers0(Map<String, String> m) {
        put(m, "java/security/MessageDigest", "java/lang/Object");
        put(m, "java/security/SecureRandom", "java/lang/Object");
    }

    private static void addUtilSupers4(Map<String, String> m) {
        put(m, "java/util/Base64", "java/lang/Object");
        put(m, "java/util/Base64$Encoder", "java/lang/Object");
        put(m, "java/util/Base64$Decoder", "java/lang/Object");
    }

    private static void addNioSupers0(Map<String, String> m) {
        put(m, "java/nio/charset/Charset", "java/lang/Object");
        put(m, "java/nio/charset/StandardCharsets", "java/lang/Object");
    }

    private static void addLangSupers3(Map<String, String> m) {
        put(m, "java/lang/Enum", "java/lang/Object");
        put(m, "java/lang/Class", "java/lang/Object");
        put(m, "java/lang/reflect/Method", "java/lang/Object");
        put(m, "java/lang/reflect/Field", "java/lang/Object");
        put(m, "java/lang/reflect/Constructor", "java/lang/Object");
        put(m, "java/lang/reflect/Array", "java/lang/Object");
        put(m, "java/lang/reflect/Modifier", "java/lang/Object");
        put(m, "java/lang/annotation/Annotation", "java/lang/Object");
        put(m, "java/lang/Runnable", "java/lang/Object");
    }

    private static void addFunctionSupers1(Map<String, String> m) {
        put(m, "java/util/function/Predicate", "java/lang/Object");
    }

    private static void addUtilSupers5(Map<String, String> m) {
        put(m, "java/util/LinkedList", "java/util/ArrayList");
        put(m, "java/util/LinkedHashMap", "java/util/HashMap");
        put(m, "java/util/TreeMap", "java/util/HashMap");
        put(m, "java/util/IntSummaryStatistics", "java/lang/Object");
        put(m, "java/util/LongSummaryStatistics", "java/lang/Object");
        put(m, "java/util/DoubleSummaryStatistics", "java/lang/Object");
    }

    private static void addLangSupers4(Map<String, String> m) {
        put(m, "java/lang/StringBuffer", "java/lang/Object");
        put(m, "java/lang/NoSuchFieldError", "java/lang/Error");
    }

    private static void addAwtSupers0(Map<String, String> m) {
        put(m, "java/awt/Component", "java/lang/Object");
        put(m, "java/awt/Container", "java/awt/Component");
        put(m, "java/awt/Window", "java/awt/Container");
        put(m, "java/awt/Frame", "java/awt/Window");
    }

    private static void addSwingSupers0(Map<String, String> m) {
        put(m, "javax/swing/JFrame", "java/awt/Frame");
        put(m, "javax/swing/JDialog", "java/awt/Window");
        put(m, "javax/swing/SwingUtilities", "java/lang/Object");
    }

    private static void addAwtSupers1(Map<String, String> m) {
        put(m, "java/awt/LayoutManager", "java/lang/Object");
        put(m, "java/awt/BorderLayout", "java/lang/Object");
        put(m, "java/awt/FlowLayout", "java/lang/Object");
        put(m, "java/awt/GridLayout", "java/lang/Object");
        put(m, "java/awt/GridBagLayout", "java/lang/Object");
        put(m, "java/awt/GridBagConstraints", "java/lang/Object");
        put(m, "java/awt/Insets", "java/lang/Object");
        put(m, "java/awt/CardLayout", "java/lang/Object");
        put(m, "java/awt/Dimension", "java/lang/Object");
        put(m, "java/awt/event/ActionListener", "java/lang/Object");
        put(m, "java/awt/event/ActionEvent", "java/lang/Object");
    }

    private static void addSwingSupers1(Map<String, String> m) {
        put(m, "javax/swing/JPanel", "java/awt/Container");
        put(m, "javax/swing/JButton", "java/awt/Component");
        put(m, "javax/swing/JLabel", "java/awt/Component");
        put(m, "javax/swing/JTextField", "java/awt/Component");
        put(m, "javax/swing/JList", "java/awt/Component");
        put(m, "javax/swing/JScrollPane", "java/awt/Component");
        put(m, "javax/swing/ListModel", "java/lang/Object");
        put(m, "javax/swing/DefaultListModel", "java/lang/Object");
        put(m, "javax/swing/JPasswordField", "javax/swing/JTextField");
        put(m, "javax/swing/JProgressBar", "java/awt/Component");
        put(m, "javax/swing/BoxLayout", "java/lang/Object");
        put(m, "javax/swing/Box", "java/lang/Object");
        put(m, "javax/swing/BorderFactory", "java/lang/Object");
        put(m, "javax/swing/border/Border", "java/lang/Object");
        put(m, "javax/swing/border/TitledBorder", "javax/swing/border/Border");
        put(m, "javax/swing/JToolBar", "java/awt/Container");
        put(m, "javax/swing/SwingWorker", "java/lang/Object");
        put(m, "javax/swing/JSlider", "java/awt/Component");
        put(m, "javax/swing/JCheckBox", "java/awt/Component");
    }

    private static void addLangSupers5(Map<String, String> m) {
        put(m, "java/lang/Package", "java/lang/Object");
    }

    private static void addSwingSupers2(Map<String, String> m) {
        put(m, "javax/swing/JFileChooser", "java/awt/Component");
        put(m, "javax/swing/JComboBox", "java/awt/Component");
        put(m, "javax/swing/AbstractButton", "java/awt/Component");
        put(m, "javax/swing/JRadioButton", "javax/swing/AbstractButton");
        put(m, "javax/swing/JTextArea", "java/awt/Component");
        put(m, "javax/swing/JTabbedPane", "java/awt/Component");
        put(m, "javax/swing/ButtonGroup", "java/lang/Object");
        put(m, "javax/swing/JSpinner", "java/awt/Component");
        put(m, "javax/swing/SpinnerModel", "java/lang/Object");
        put(m, "javax/swing/SpinnerNumberModel", "javax/swing/SpinnerModel");
        put(m, "javax/swing/event/ChangeListener", "java/lang/Object");
        put(m, "javax/swing/event/ChangeEvent", "java/lang/Object");
        put(m, "javax/swing/JMenuItem", "java/awt/Component");
        put(m, "javax/swing/JMenu", "javax/swing/JMenuItem");
        put(m, "javax/swing/JMenuBar", "java/awt/Component");
        put(m, "javax/swing/JPopupMenu", "java/awt/Component");
        put(m, "javax/swing/JCheckBoxMenuItem", "javax/swing/JMenuItem");
        put(m, "javax/swing/KeyStroke", "java/lang/Object");
        put(m, "javax/swing/Timer", "java/lang/Object");
    }

    private static void addAwtSupers2(Map<String, String> m) {
        put(m, "java/awt/geom/Point2D", "java/lang/Object");
        put(m, "java/awt/Point", "java/awt/geom/Point2D");
        put(m, "java/awt/Graphics", "java/lang/Object");
        put(m, "java/awt/Graphics2D", "java/awt/Graphics");
        put(m, "java/awt/geom/AffineTransform", "java/lang/Object");
        put(m, "java/awt/RenderingHints", "java/lang/Object");
        put(m, "java/awt/RenderingHints$Key", "java/lang/Object");
        put(m, "java/awt/KeyboardFocusManager", "java/lang/Object");
        put(m, "java/awt/KeyEventDispatcher", "java/lang/Object");
        put(m, "java/awt/event/MouseEvent", "java/lang/Object");
        put(m, "java/awt/event/MouseListener", "java/lang/Object");
        put(m, "java/awt/event/MouseMotionListener", "java/lang/Object");
        put(m, "java/awt/event/MouseAdapter", "java/lang/Object");
        put(m, "java/awt/event/WindowEvent", "java/lang/Object");
        put(m, "java/awt/event/WindowListener", "java/lang/Object");
        put(m, "java/awt/event/WindowAdapter", "java/lang/Object");
    }

    private static void addSwingSupers3(Map<String, String> m) {
        put(m, "javax/swing/JOptionPane", "java/lang/Object");
    }

    private static void addAwtSupers3(Map<String, String> m) {
        put(m, "java/awt/Color", "java/lang/Object");
        put(m, "java/awt/Font", "java/lang/Object");
        put(m, "java/awt/event/KeyListener", "java/lang/Object");
        put(m, "java/awt/event/KeyAdapter", "java/lang/Object");
        put(m, "java/awt/event/KeyEvent", "java/lang/Object");
    }

    private static void addUtilSupers6(Map<String, String> m) {
        put(m, "java/util/Arrays", "java/lang/Object");
    }

    private static void addIoSupers0(Map<String, String> m) {
        put(m, "java/io/PrintStream", "java/lang/Object");
    }

    private static void addLangSupers6(Map<String, String> m) {
        put(m, "java/lang/Throwable", "java/lang/Object");
        put(m, "java/lang/Exception", "java/lang/Throwable");
        put(m, "java/lang/RuntimeException", "java/lang/Exception");
        put(m, "java/lang/Error", "java/lang/Throwable");
        put(m, "java/lang/AssertionError", "java/lang/Error");
        put(m, "java/lang/NullPointerException", "java/lang/RuntimeException");
        put(m, "java/lang/ArithmeticException", "java/lang/RuntimeException");
        put(m, "java/lang/ClassCastException", "java/lang/RuntimeException");
        put(m, "java/lang/IndexOutOfBoundsException", "java/lang/RuntimeException");
        put(m, "java/lang/ArrayIndexOutOfBoundsException", "java/lang/IndexOutOfBoundsException");
        put(m, "java/lang/ArrayStoreException", "java/lang/RuntimeException");
        put(m, "java/lang/IllegalArgumentException", "java/lang/RuntimeException");
        put(m, "java/lang/NumberFormatException", "java/lang/IllegalArgumentException");
        put(m, "java/lang/IllegalStateException", "java/lang/RuntimeException");
    }

    /** All shim internal names: SHIM_SUPERS keys plus the root java/lang/Object. */
    private static final Set<String> TYPES = deriveTypes();

    private static Set<String> deriveTypes() {
        Set<String> types = new java.util.HashSet<>(SHIM_SUPERS.keySet());
        types.add("java/lang/Object");
        return Set.copyOf(types);
    }

    private static final Set<String> EXTENDABLE = Set.of(
            "java/lang/Throwable",
            "java/lang/Exception",
            "java/lang/RuntimeException",
            "java/lang/Error",
            "java/lang/NullPointerException",
            "java/lang/ArithmeticException",
            "java/lang/ClassCastException",
            "java/lang/IndexOutOfBoundsException",
            "java/lang/ArrayIndexOutOfBoundsException",
            "java/lang/ArrayStoreException",
            "java/lang/IllegalArgumentException",
            "java/lang/NumberFormatException",
            "java/lang/IllegalStateException",
            "java/lang/InterruptedException",
            "java/lang/IllegalAccessException",
            "java/lang/NoSuchFieldException",
            "java/lang/NoSuchMethodException",
            "java/lang/InstantiationException",
            "java/lang/reflect/InvocationTargetException",
            "java/lang/Enum",
            "java/lang/NoSuchFieldError",
            "javax/swing/JFrame",
            "javax/swing/JDialog",
            "javax/swing/JPanel",
            "javax/swing/JToolBar",
            "javax/swing/SwingWorker",
            "java/awt/event/KeyAdapter",
            "java/awt/event/MouseAdapter",
            "java/awt/event/WindowAdapter");

    public static final Map<String, String> EXTENDABLE_VIRTUALS = Map.ofEntries(
            Map.entry("getMessage()Ljava/lang/String;", "getMessage"),
            Map.entry("name()Ljava/lang/String;", "name"),
            Map.entry("ordinal()I", "ordinal"),
            Map.entry("keyPressed(Ljava/awt/event/KeyEvent;)V", "keyPressed"),
            Map.entry("keyReleased(Ljava/awt/event/KeyEvent;)V", "keyReleased"),
            Map.entry("keyTyped(Ljava/awt/event/KeyEvent;)V", "keyTyped"),
            Map.entry("mouseClicked(Ljava/awt/event/MouseEvent;)V", "mouseClicked"),
            Map.entry("mousePressed(Ljava/awt/event/MouseEvent;)V", "mousePressed"),
            Map.entry("mouseReleased(Ljava/awt/event/MouseEvent;)V", "mouseReleased"),
            Map.entry("mouseEntered(Ljava/awt/event/MouseEvent;)V", "mouseEntered"),
            Map.entry("mouseExited(Ljava/awt/event/MouseEvent;)V", "mouseExited"),
            Map.entry("mouseDragged(Ljava/awt/event/MouseEvent;)V", "mouseDragged"),
            Map.entry("mouseMoved(Ljava/awt/event/MouseEvent;)V", "mouseMoved"),
            Map.entry("windowOpened(Ljava/awt/event/WindowEvent;)V", "windowOpened"),
            Map.entry("windowClosing(Ljava/awt/event/WindowEvent;)V", "windowClosing"),
            Map.entry("windowClosed(Ljava/awt/event/WindowEvent;)V", "windowClosed"),
            Map.entry("windowIconified(Ljava/awt/event/WindowEvent;)V", "windowIconified"),
            Map.entry("windowDeiconified(Ljava/awt/event/WindowEvent;)V", "windowDeiconified"),
            Map.entry("windowActivated(Ljava/awt/event/WindowEvent;)V", "windowActivated"),
            Map.entry("windowDeactivated(Ljava/awt/event/WindowEvent;)V", "windowDeactivated"),
            Map.entry("paintComponent(Ljava/awt/Graphics;)V", "paintComponent"),
            Map.entry("doInBackground()Ljava/lang/Object;", "doInBackground"),
            Map.entry("process(Ljava/util/List;)V", "process"),
            Map.entry("done()V", "done"));

    public static final Set<String> EXTENDABLE_MEMBER_NAMES = Set.of(
            "getMessage", "JavaClassName", "message", "__origin",
            "name", "ordinal", "__name", "__ordinal",
            "mouseClicked", "mousePressed", "mouseReleased", "mouseEntered", "mouseExited",
            "mouseDragged", "mouseMoved", "windowOpened", "windowClosing", "windowClosed",
            "windowIconified", "windowDeiconified", "windowActivated", "windowDeactivated",
            "paintComponent", "doInBackground", "process", "done");

    public record WalkResult(String declaringInternal, ShimTarget target) {
    }

        private static final Map<String, ShimTarget> METHODS = buildMethods();

    private static Map<String, ShimTarget> buildMethods() {
        Map<String, ShimTarget> m = new java.util.HashMap<>(2048);
        addLangMethods0(m);
        addIoMethods0(m);
        addLangMethods1(m);
        addLangMethods2(m);
        addLangMethods3(m);
        addLangMethods4(m);
        addUtilMethods0(m);
        addLangMethods5(m);
        addConcurrentMethods0(m);
        addConcurrentMethods1(m);
        addTimeMethods0(m);
        addLangMethods6(m);
        addSecurityMethods0(m);
        addUtilMethods1(m);
        addLangMethods7(m);
        addLangMethods8(m);
        addFunctionMethods0(m);
        addRegexMethods0(m);
        addUtilMethods2(m);
        addLangMethods9(m);
        addAwtMethods0(m);
        addUtilMethods3(m);
        addUtilMethods4(m);
        addStreamMethods0(m);
        addStreamMethods1(m);
        addUtilMethods5(m);
        addLangMethods10(m);
        addUtilMethods6(m);
        addSwingMethods0(m);
        addAwtMethods1(m);
        addLangMethods11(m);
        addSwingMethods1(m);
        addUtilMethods7(m);
        addAwtMethods2(m);
        addSwingMethods2(m);
        addAwtMethods3(m);
        addSwingMethods3(m);
        addAwtMethods4(m);
        addSwingMethods4(m);
        addAwtMethods5(m);
        addLangMethods12(m);
        addSwingMethods5(m);
        addAwtMethods6(m);
        addSwingMethods6(m);
        addAwtMethods7(m);
        addSwingMethods7(m);
        addUtilMethods8(m);
        addAwtMethods8(m);
        addLangMethods13(m);
        addAwtMethods9(m);
        return Map.copyOf(m);
    }

    private static void addLangMethods0(Map<String, ShimTarget> m) {
        put(m, "java/lang/Object.toString()Ljava/lang/String;", instance("toString"));
        put(m, "java/lang/Object.hashCode()I", instance("hashCode"));
        put(m, "java/lang/Object.equals(Ljava/lang/Object;)Z", instance("equals"));
        put(m, "java/lang/Object.getClass()Ljava/lang/Class;", instance("getClass"));
        put(m, "java/lang/String.length()I", instance("length"));
        put(m, "java/lang/String.charAt(I)C", instance("charAt"));
        put(m, "java/lang/String.isEmpty()Z", instance("isEmpty"));
        put(m, "java/lang/String.equals(Ljava/lang/Object;)Z", instance("equals"));
        put(m, "java/lang/String.hashCode()I", instance("hashCode"));
        put(m, "java/lang/String.toString()Ljava/lang/String;", instance("toString"));
    }

    private static void addIoMethods0(Map<String, ShimTarget> m) {
        put(m, "java/io/PrintStream.println()V", instance("println"));
        put(m, "java/io/PrintStream.println(I)V", instance("println"));
        put(m, "java/io/PrintStream.println(J)V", instance("println"));
        put(m, "java/io/PrintStream.println(F)V", instance("println"));
        put(m, "java/io/PrintStream.println(D)V", instance("println"));
        put(m, "java/io/PrintStream.println(C)V", instance("println"));
        put(m, "java/io/PrintStream.println(Z)V", instance("println_Z"));
        put(m, "java/io/PrintStream.println(Ljava/lang/String;)V", instance("println"));
        put(m, "java/io/PrintStream.println(Ljava/lang/Object;)V", instance("println"));
        put(m, "java/io/PrintStream.print(I)V", instance("print"));
        put(m, "java/io/PrintStream.print(J)V", instance("print"));
        put(m, "java/io/PrintStream.print(F)V", instance("print"));
        put(m, "java/io/PrintStream.print(D)V", instance("print"));
        put(m, "java/io/PrintStream.print(C)V", instance("print"));
        put(m, "java/io/PrintStream.print(Z)V", instance("print_Z"));
        put(m, "java/io/PrintStream.print(Ljava/lang/String;)V", instance("print"));
        put(m, "java/io/PrintStream.print(Ljava/lang/Object;)V", instance("print"));
        put(m, "java/io/PrintStream.printf(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;", instance("printf"));
        put(m, "java/io/PrintStream.format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;", instance("format"));
    }

    private static void addLangMethods1(Map<String, ShimTarget> m) {
        put(m, "java/lang/StringBuilder.append(I)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(J)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(F)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(D)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(C)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(Z)Ljava/lang/StringBuilder;", instance("append_Z"));
        put(m, "java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(Ljava/lang/Object;)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append([C)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.insert(ILjava/lang/String;)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(IC)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(II)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(ILjava/lang/Object;)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.reverse()Ljava/lang/StringBuilder;", instance("reverse"));
        put(m, "java/lang/StringBuilder.deleteCharAt(I)Ljava/lang/StringBuilder;", instance("deleteCharAt"));
        put(m, "java/lang/StringBuilder.delete(II)Ljava/lang/StringBuilder;", instance("delete"));
        put(m, "java/lang/StringBuilder.replace(IILjava/lang/String;)Ljava/lang/StringBuilder;", instance("replace"));
        put(m, "java/lang/StringBuilder.setLength(I)V", instance("setLength"));
        put(m, "java/lang/StringBuilder.charAt(I)C", instance("charAt"));
        put(m, "java/lang/StringBuilder.setCharAt(IC)V", instance("setCharAt"));
        put(m, "java/lang/StringBuilder.indexOf(Ljava/lang/String;)I", instance("indexOf"));
        put(m, "java/lang/StringBuilder.length()I", instance("length"));
        put(m, "java/lang/StringBuilder.toString()Ljava/lang/String;", instance("toString"));
        put(m, "java/lang/String.substring(I)Ljava/lang/String;", instance("substring"));
        put(m, "java/lang/String.substring(II)Ljava/lang/String;", instance("substring"));
        put(m, "java/lang/String.indexOf(I)I", instance("indexOf"));
        put(m, "java/lang/String.indexOf(Ljava/lang/String;)I", instance("indexOf"));
        put(m, "java/lang/String.startsWith(Ljava/lang/String;)Z", instance("startsWith"));
        put(m, "java/lang/String.endsWith(Ljava/lang/String;)Z", instance("endsWith"));
        put(m, "java/lang/String.equalsIgnoreCase(Ljava/lang/String;)Z", instance("equalsIgnoreCase"));
        put(m, "java/lang/String.contains(Ljava/lang/CharSequence;)Z", instance("contains"));
        put(m, "java/lang/String.toCharArray()[C", instance("toCharArray"));
        put(m, "java/lang/String.toLowerCase()Ljava/lang/String;", instance("toLowerCase"));
        put(m, "java/lang/String.toUpperCase()Ljava/lang/String;", instance("toUpperCase"));
        put(m, "java/lang/String.concat(Ljava/lang/String;)Ljava/lang/String;", instance("concat"));
        put(m, "java/lang/String.strip()Ljava/lang/String;", instance("strip"));
        put(m, "java/lang/String.repeat(I)Ljava/lang/String;", instance("repeat"));
        put(m, "java/lang/String.replace(CC)Ljava/lang/String;", instance("replace"));
        put(m, "java/lang/String.replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;", instance("replace"));
        put(m, "java/lang/String.replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", instance("replaceAll"));
        put(m, "java/lang/String.replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", instance("replaceFirst"));
        put(m, "java/lang/String.matches(Ljava/lang/String;)Z", instance("matches"));
        put(m, "java/lang/String.lastIndexOf(I)I", instance("lastIndexOf"));
        put(m, "java/lang/String.lastIndexOf(Ljava/lang/String;)I", instance("lastIndexOf"));
        put(m, "java/lang/String.indexOf(II)I", instance("indexOf"));
        put(m, "java/lang/String.indexOf(Ljava/lang/String;I)I", instance("indexOf"));
        put(m, "java/lang/String.compareTo(Ljava/lang/String;)I", instance("compareTo"));
        put(m, "java/lang/String.compareToIgnoreCase(Ljava/lang/String;)I", instance("compareToIgnoreCase"));
        put(m, "java/lang/String.split(Ljava/lang/String;)[Ljava/lang/String;", instance("split"));
    }

    private static void addLangMethods2(Map<String, ShimTarget> m) {
        put(m, "java/lang/String.split(Ljava/lang/String;I)[Ljava/lang/String;", instance("split"));
        put(m, "java/lang/String.valueOf(I)Ljava/lang/String;", statics("valueOf"));
        put(m, "java/lang/String.valueOf(J)Ljava/lang/String;", statics("valueOf"));
        put(m, "java/lang/String.valueOf(C)Ljava/lang/String;", statics("valueOf"));
        put(m, "java/lang/String.valueOf(D)Ljava/lang/String;", statics("valueOf"));
        put(m, "java/lang/String.valueOf(F)Ljava/lang/String;", statics("valueOf"));
        put(m, "java/lang/String.valueOf([C)Ljava/lang/String;", statics("valueOf"));
        put(m, "java/lang/String.valueOf(Z)Ljava/lang/String;", statics("valueOfBoolean"));
        put(m, "java/lang/String.join(Ljava/lang/CharSequence;[Ljava/lang/CharSequence;)Ljava/lang/String;", statics("join"));
        put(m, "java/lang/Math.abs(I)I", statics("abs"));
        put(m, "java/lang/Math.abs(D)D", statics("abs"));
        put(m, "java/lang/Math.max(II)I", statics("max"));
        put(m, "java/lang/Math.min(II)I", statics("min"));
        put(m, "java/lang/Math.sqrt(D)D", statics("sqrt"));
        put(m, "java/lang/Math.log(D)D", statics("log"));
        put(m, "java/lang/Math.abs(J)J", statics("abs"));
        put(m, "java/lang/Math.abs(F)F", statics("abs"));
        put(m, "java/lang/Math.max(JJ)J", statics("max"));
        put(m, "java/lang/Math.max(DD)D", statics("max"));
        put(m, "java/lang/Math.max(FF)F", statics("max"));
        put(m, "java/lang/Math.min(JJ)J", statics("min"));
        put(m, "java/lang/Math.min(DD)D", statics("min"));
        put(m, "java/lang/Math.min(FF)F", statics("min"));
        put(m, "java/lang/Math.pow(DD)D", statics("pow"));
        put(m, "java/lang/Math.random()D", statics("random"));
        put(m, "java/lang/Math.sin(D)D", statics("sin"));
        put(m, "java/lang/Math.cos(D)D", statics("cos"));
        put(m, "java/lang/Math.tan(D)D", statics("tan"));
        put(m, "java/lang/Math.atan2(DD)D", statics("atan2"));
        put(m, "java/lang/Math.exp(D)D", statics("exp"));
        put(m, "java/lang/Math.log10(D)D", statics("log10"));
        put(m, "java/lang/Math.cbrt(D)D", statics("cbrt"));
        put(m, "java/lang/Math.hypot(DD)D", statics("hypot"));
        put(m, "java/lang/Math.floor(D)D", statics("floor"));
        put(m, "java/lang/Math.ceil(D)D", statics("ceil"));
        put(m, "java/lang/Math.round(D)J", statics("round"));
        put(m, "java/lang/Math.round(F)I", statics("round"));
        put(m, "java/lang/Math.toRadians(D)D", statics("toRadians"));
        put(m, "java/lang/Math.toDegrees(D)D", statics("toDegrees"));
        put(m, "java/lang/Math.signum(D)D", statics("signum"));
        put(m, "java/lang/Integer.parseInt(Ljava/lang/String;)I", statics("parseInt"));
        put(m, "java/lang/Integer.toString(I)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Integer.valueOf(I)Ljava/lang/Integer;", statics("valueOf"));
        put(m, "java/lang/Integer.compareTo(Ljava/lang/Integer;)I", instance("compareTo"));
        put(m, "java/lang/Integer.sum(II)I", statics("sum"));
        put(m, "java/lang/Integer.max(II)I", statics("max"));
        put(m, "java/lang/Integer.min(II)I", statics("min"));
        put(m, "java/lang/Integer.compare(II)I", statics("compare"));
        put(m, "java/lang/Long.valueOf(J)Ljava/lang/Long;", statics("valueOf"));
        put(m, "java/lang/Long.parseLong(Ljava/lang/String;)J", statics("parseLong"));
    }

    private static void addLangMethods3(Map<String, ShimTarget> m) {
        put(m, "java/lang/Long.toString(J)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Long.compareTo(Ljava/lang/Long;)I", instance("compareTo"));
        put(m, "java/lang/Double.valueOf(D)Ljava/lang/Double;", statics("valueOf"));
        put(m, "java/lang/Double.parseDouble(Ljava/lang/String;)D", statics("parseDouble"));
        put(m, "java/lang/Double.toString(D)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Double.compareTo(Ljava/lang/Double;)I", instance("compareTo"));
        put(m, "java/lang/Float.valueOf(F)Ljava/lang/Float;", statics("valueOf"));
        put(m, "java/lang/Float.parseFloat(Ljava/lang/String;)F", statics("parseFloat"));
        put(m, "java/lang/Float.toString(F)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Float.compareTo(Ljava/lang/Float;)I", instance("compareTo"));
        put(m, "java/lang/Short.valueOf(S)Ljava/lang/Short;", statics("valueOf"));
        put(m, "java/lang/Short.toString(S)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Short.compareTo(Ljava/lang/Short;)I", instance("compareTo"));
        put(m, "java/lang/Byte.valueOf(B)Ljava/lang/Byte;", statics("valueOf"));
        put(m, "java/lang/Byte.toString(B)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Byte.compareTo(Ljava/lang/Byte;)I", instance("compareTo"));
        put(m, "java/lang/Boolean.valueOf(Z)Ljava/lang/Boolean;", statics("valueOf"));
        put(m, "java/lang/Boolean.getBoolean(Ljava/lang/String;)Z", statics("getBoolean"));
        put(m, "java/lang/Boolean.parseBoolean(Ljava/lang/String;)Z", statics("parseBoolean"));
        put(m, "java/lang/Boolean.toString(Z)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Boolean.booleanValue()Z", instance("booleanValue"));
        put(m, "java/lang/Boolean.compareTo(Ljava/lang/Boolean;)I", instance("compareTo"));
        put(m, "java/lang/Character.valueOf(C)Ljava/lang/Character;", statics("valueOf"));
        put(m, "java/lang/Character.toString(C)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Character.charValue()C", instance("charValue"));
        put(m, "java/lang/Character.compareTo(Ljava/lang/Character;)I", instance("compareTo"));
        put(m, "java/lang/Character.isDigit(C)Z", statics("isDigit"));
        put(m, "java/lang/Character.isLetter(C)Z", statics("isLetter"));
        put(m, "java/lang/Character.isLetterOrDigit(C)Z", statics("isLetterOrDigit"));
        put(m, "java/lang/Character.isWhitespace(C)Z", statics("isWhitespace"));
        put(m, "java/lang/Character.isUpperCase(C)Z", statics("isUpperCase"));
        put(m, "java/lang/Character.isLowerCase(C)Z", statics("isLowerCase"));
        put(m, "java/lang/Character.toUpperCase(C)C", statics("toUpperCase"));
        put(m, "java/lang/Character.toLowerCase(C)C", statics("toLowerCase"));
        put(m, "java/lang/Character.getNumericValue(C)I", statics("getNumericValue"));
        put(m, "java/lang/Character.digit(CI)I", statics("digit"));
        put(m, "java/lang/Number.intValue()I", instance("intValue"));
        put(m, "java/lang/Number.longValue()J", instance("longValue"));
        put(m, "java/lang/Number.floatValue()F", instance("floatValue"));
        put(m, "java/lang/Number.doubleValue()D", instance("doubleValue"));
        put(m, "java/lang/Number.shortValue()S", instance("shortValue"));
        put(m, "java/lang/Number.byteValue()B", instance("byteValue"));
        put(m, "java/lang/System.arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V", statics("arraycopy"));
        put(m, "java/lang/Throwable.getMessage()Ljava/lang/String;", instance("getMessage"));
        put(m, "java/lang/Throwable.getCause()Ljava/lang/Throwable;", instance("getCause"));
        put(m, "java/lang/Throwable.toString()Ljava/lang/String;", instance("toString"));
        put(m, "java/lang/Throwable.printStackTrace()V", instance("printStackTrace"));
        put(m, "java/lang/String.format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", statics("format"));
        put(m, "java/lang/System.nanoTime()J", statics("nanoTime"));
        put(m, "java/lang/System.currentTimeMillis()J", statics("currentTimeMillis"));
    }

    private static void addLangMethods4(Map<String, ShimTarget> m) {
        put(m, "java/lang/System.exit(I)V", statics("exit"));
    }

    private static void addUtilMethods0(Map<String, ShimTarget> m) {
        put(m, "java/util/UUID.randomUUID()Ljava/util/UUID;", statics("randomUUID"));
        put(m, "java/util/UUID.toString()Ljava/lang/String;", instance("toString"));
    }

    private static void addLangMethods5(Map<String, ShimTarget> m) {
        put(m, "java/lang/Thread.currentThread()Ljava/lang/Thread;", statics("currentThread"));
        put(m, "java/lang/Thread.interrupt()V", instance("interrupt"));
        put(m, "java/lang/Thread.start()V", instance("start"));
        put(m, "java/lang/Thread.run()V", instance("run"));
        put(m, "java/lang/Thread.join()V", instance("join"));
        put(m, "java/lang/Thread.join(J)V", instance("join"));
        put(m, "java/lang/Thread.setDaemon(Z)V", instance("setDaemon"));
        put(m, "java/lang/Thread.isDaemon()Z", instance("isDaemon"));
        put(m, "java/lang/Thread.setName(Ljava/lang/String;)V", instance("setName"));
        put(m, "java/lang/Thread.getName()Ljava/lang/String;", instance("getName"));
        put(m, "java/lang/Thread.isInterrupted()Z", instance("isInterrupted"));
        put(m, "java/lang/Thread.interrupted()Z", statics("interrupted"));
        put(m, "java/lang/Runtime.getRuntime()Ljava/lang/Runtime;", statics("getRuntime"));
        put(m, "java/lang/Runtime.availableProcessors()I", instance("availableProcessors"));
        put(m, "java/lang/Runtime.maxMemory()J", instance("maxMemory"));
        put(m, "java/lang/Runtime.totalMemory()J", instance("totalMemory"));
        put(m, "java/lang/Runtime.freeMemory()J", instance("freeMemory"));
        put(m, "java/lang/Runtime.gc()V", instance("gc"));
    }

    private static void addConcurrentMethods0(Map<String, ShimTarget> m) {
        put(m, "java/util/concurrent/locks/Lock.lock()V", instance("@lock"));
        put(m, "java/util/concurrent/locks/Lock.unlock()V", instance("unlock"));
        put(m, "java/util/concurrent/TimeUnit.sleep(J)V", instance("sleep"));
        put(m, "java/util/concurrent/TimeUnit.toMillis(J)J", instance("toMillis"));
        put(m, "java/util/concurrent/Callable.call()Ljava/lang/Object;", instance("call"));
        put(m, "java/util/concurrent/atomic/AtomicInteger.get()I", instance("get"));
        put(m, "java/util/concurrent/atomic/AtomicInteger.set(I)V", instance("set"));
        put(m, "java/util/concurrent/atomic/AtomicInteger.getAndSet(I)I", instance("getAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicInteger.incrementAndGet()I", instance("incrementAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicInteger.decrementAndGet()I", instance("decrementAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicInteger.getAndIncrement()I", instance("getAndIncrement"));
        put(m, "java/util/concurrent/atomic/AtomicInteger.getAndDecrement()I", instance("getAndDecrement"));
        put(m, "java/util/concurrent/atomic/AtomicInteger.addAndGet(I)I", instance("addAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicInteger.getAndAdd(I)I", instance("getAndAdd"));
        put(m, "java/util/concurrent/atomic/AtomicInteger.compareAndSet(II)Z", instance("compareAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicLong.get()J", instance("get"));
        put(m, "java/util/concurrent/atomic/AtomicLong.set(J)V", instance("set"));
        put(m, "java/util/concurrent/atomic/AtomicLong.getAndSet(J)J", instance("getAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicLong.incrementAndGet()J", instance("incrementAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicLong.decrementAndGet()J", instance("decrementAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicLong.getAndIncrement()J", instance("getAndIncrement"));
        put(m, "java/util/concurrent/atomic/AtomicLong.getAndDecrement()J", instance("getAndDecrement"));
        put(m, "java/util/concurrent/atomic/AtomicLong.addAndGet(J)J", instance("addAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicLong.getAndAdd(J)J", instance("getAndAdd"));
        put(m, "java/util/concurrent/atomic/AtomicLong.compareAndSet(JJ)Z", instance("compareAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicBoolean.get()Z", instance("get"));
        put(m, "java/util/concurrent/atomic/AtomicBoolean.set(Z)V", instance("set"));
        put(m, "java/util/concurrent/atomic/AtomicBoolean.getAndSet(Z)Z", instance("getAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicBoolean.compareAndSet(ZZ)Z", instance("compareAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicReference.get()Ljava/lang/Object;", instance("get"));
        put(m, "java/util/concurrent/atomic/AtomicReference.set(Ljava/lang/Object;)V", instance("set"));
        put(m, "java/util/concurrent/atomic/AtomicReference.getAndSet(Ljava/lang/Object;)Ljava/lang/Object;", instance("getAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicReference.compareAndSet(Ljava/lang/Object;Ljava/lang/Object;)Z", instance("compareAndSet"));
        put(m, "java/util/concurrent/Future.get()Ljava/lang/Object;", instance("get"));
        put(m, "java/util/concurrent/Future.get(JLjava/util/concurrent/TimeUnit;)Ljava/lang/Object;", instance("get"));
        put(m, "java/util/concurrent/Future.isDone()Z", instance("isDone"));
        put(m, "java/util/concurrent/Future.isCancelled()Z", instance("isCancelled"));
        put(m, "java/util/concurrent/Future.cancel(Z)Z", instance("cancel"));
        put(m, "java/util/concurrent/ExecutorService.execute(Ljava/lang/Runnable;)V", instance("execute"));
        put(m, "java/util/concurrent/ExecutorService.submit(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;", instance("submit"));
        put(m, "java/util/concurrent/ExecutorService.submit(Ljava/util/concurrent/Callable;)Ljava/util/concurrent/Future;", instance("submit"));
        put(m, "java/util/concurrent/ExecutorService.shutdown()V", instance("shutdown"));
        put(m, "java/util/concurrent/ExecutorService.shutdownNow()Ljava/util/List;", instance("shutdownNow"));
        put(m, "java/util/concurrent/ExecutorService.awaitTermination(JLjava/util/concurrent/TimeUnit;)Z", instance("awaitTermination"));
        put(m, "java/util/concurrent/ExecutorService.isShutdown()Z", instance("isShutdown"));
        put(m, "java/util/concurrent/ExecutorService.isTerminated()Z", instance("isTerminated"));
        put(m, "java/util/concurrent/ScheduledExecutorService.scheduleAtFixedRate(Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Ljava/util/concurrent/ScheduledFuture;", instance("scheduleAtFixedRate"));
        put(m, "java/util/concurrent/ScheduledExecutorService.schedule(Ljava/lang/Runnable;JLjava/util/concurrent/TimeUnit;)Ljava/util/concurrent/ScheduledFuture;", instance("schedule"));
        put(m, "java/util/concurrent/Executors.newFixedThreadPool(I)Ljava/util/concurrent/ExecutorService;", statics("newFixedThreadPool"));
        put(m, "java/util/concurrent/Executors.newCachedThreadPool()Ljava/util/concurrent/ExecutorService;", statics("newCachedThreadPool"));
    }

    private static void addConcurrentMethods1(Map<String, ShimTarget> m) {
        put(m, "java/util/concurrent/Executors.newSingleThreadExecutor()Ljava/util/concurrent/ExecutorService;", statics("newSingleThreadExecutor"));
        put(m, "java/util/concurrent/Executors.newScheduledThreadPool(I)Ljava/util/concurrent/ScheduledExecutorService;", statics("newScheduledThreadPool"));
        put(m, "java/util/concurrent/Executors.newSingleThreadScheduledExecutor()Ljava/util/concurrent/ScheduledExecutorService;", statics("newSingleThreadScheduledExecutor"));
        put(m, "java/util/concurrent/Executor.execute(Ljava/lang/Runnable;)V", instance("execute"));
        put(m, "java/util/concurrent/ThreadFactory.newThread(Ljava/lang/Runnable;)Ljava/lang/Thread;", instance("newThread"));
        put(m, "java/util/concurrent/Executors.newScheduledThreadPool(ILjava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ScheduledExecutorService;", statics("newScheduledThreadPool"));
        put(m, "java/util/concurrent/Executors.newSingleThreadScheduledExecutor(Ljava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ScheduledExecutorService;", statics("newSingleThreadScheduledExecutor"));
        put(m, "java/util/concurrent/Executors.newFixedThreadPool(ILjava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ExecutorService;", statics("newFixedThreadPool"));
        put(m, "java/util/concurrent/CompletableFuture.runAsync(Ljava/lang/Runnable;)Ljava/util/concurrent/CompletableFuture;", statics("runAsync"));
        put(m, "java/util/concurrent/CompletableFuture.runAsync(Ljava/lang/Runnable;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;", statics("runAsync"));
        put(m, "java/util/concurrent/CompletableFuture.supplyAsync(Ljava/util/function/Supplier;)Ljava/util/concurrent/CompletableFuture;", statics("supplyAsync"));
        put(m, "java/util/concurrent/CompletableFuture.supplyAsync(Ljava/util/function/Supplier;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;", statics("supplyAsync"));
        put(m, "java/util/concurrent/CompletableFuture.completedFuture(Ljava/lang/Object;)Ljava/util/concurrent/CompletableFuture;", statics("completedFuture"));
        put(m, "java/util/concurrent/CompletableFuture.allOf([Ljava/util/concurrent/CompletableFuture;)Ljava/util/concurrent/CompletableFuture;", statics("allOf"));
        put(m, "java/util/concurrent/CompletableFuture.anyOf([Ljava/util/concurrent/CompletableFuture;)Ljava/util/concurrent/CompletableFuture;", statics("anyOf"));
        put(m, "java/util/concurrent/CompletableFuture.whenComplete(Ljava/util/function/BiConsumer;)Ljava/util/concurrent/CompletableFuture;", instance("whenComplete"));
        put(m, "java/util/concurrent/CompletableFuture.thenApply(Ljava/util/function/Function;)Ljava/util/concurrent/CompletableFuture;", instance("thenApply"));
        put(m, "java/util/concurrent/CompletableFuture.thenAccept(Ljava/util/function/Consumer;)Ljava/util/concurrent/CompletableFuture;", instance("thenAccept"));
        put(m, "java/util/concurrent/CompletableFuture.thenRun(Ljava/lang/Runnable;)Ljava/util/concurrent/CompletableFuture;", instance("thenRun"));
        put(m, "java/util/concurrent/CompletableFuture.join()Ljava/lang/Object;", instance("join"));
    }

    private static void addTimeMethods0(Map<String, ShimTarget> m) {
        put(m, "java/time/LocalDate.of(III)Ljava/time/LocalDate;", statics("of"));
        put(m, "java/time/LocalDate.now()Ljava/time/LocalDate;", statics("now"));
        put(m, "java/time/LocalDate.plusDays(J)Ljava/time/LocalDate;", instance("plusDays"));
        put(m, "java/time/LocalDate.minusDays(J)Ljava/time/LocalDate;", instance("minusDays"));
        put(m, "java/time/LocalDate.plusWeeks(J)Ljava/time/LocalDate;", instance("plusWeeks"));
        put(m, "java/time/LocalDate.minusWeeks(J)Ljava/time/LocalDate;", instance("minusWeeks"));
        put(m, "java/time/LocalDate.plusMonths(J)Ljava/time/LocalDate;", instance("plusMonths"));
        put(m, "java/time/LocalDate.minusMonths(J)Ljava/time/LocalDate;", instance("minusMonths"));
        put(m, "java/time/LocalDate.plusYears(J)Ljava/time/LocalDate;", instance("plusYears"));
        put(m, "java/time/LocalDate.minusYears(J)Ljava/time/LocalDate;", instance("minusYears"));
        put(m, "java/time/LocalDate.getYear()I", instance("getYear"));
        put(m, "java/time/LocalDate.getMonthValue()I", instance("getMonthValue"));
        put(m, "java/time/LocalDate.getDayOfMonth()I", instance("getDayOfMonth"));
        put(m, "java/time/LocalDate.getDayOfYear()I", instance("getDayOfYear"));
        put(m, "java/time/LocalDate.lengthOfMonth()I", instance("lengthOfMonth"));
        put(m, "java/time/LocalDate.isLeapYear()Z", instance("isLeapYear"));
        put(m, "java/time/LocalDate.getDayOfWeek()Ljava/time/DayOfWeek;", instance("getDayOfWeek"));
        put(m, "java/time/LocalDate.isBefore(Ljava/time/chrono/ChronoLocalDate;)Z", instance("isBefore"));
        put(m, "java/time/LocalDate.isAfter(Ljava/time/chrono/ChronoLocalDate;)Z", instance("isAfter"));
        put(m, "java/time/LocalDate.isEqual(Ljava/time/chrono/ChronoLocalDate;)Z", instance("isEqual"));
        put(m, "java/time/LocalDate.compareTo(Ljava/time/chrono/ChronoLocalDate;)I", instance("compareTo"));
        put(m, "java/time/LocalDate.format(Ljava/time/format/DateTimeFormatter;)Ljava/lang/String;", instance("format"));
        put(m, "java/time/Duration.ofDays(J)Ljava/time/Duration;", statics("ofDays"));
        put(m, "java/time/Duration.ofHours(J)Ljava/time/Duration;", statics("ofHours"));
        put(m, "java/time/Duration.ofMinutes(J)Ljava/time/Duration;", statics("ofMinutes"));
        put(m, "java/time/Duration.ofSeconds(J)Ljava/time/Duration;", statics("ofSeconds"));
        put(m, "java/time/Duration.ofMillis(J)Ljava/time/Duration;", statics("ofMillis"));
        put(m, "java/time/Duration.toDays()J", instance("toDays"));
        put(m, "java/time/Duration.toHours()J", instance("toHours"));
        put(m, "java/time/Duration.toMinutes()J", instance("toMinutes"));
        put(m, "java/time/Duration.toSeconds()J", instance("toSeconds"));
        put(m, "java/time/Duration.getSeconds()J", instance("getSeconds"));
        put(m, "java/time/Duration.toMillis()J", instance("toMillis"));
        put(m, "java/time/Duration.toNanos()J", instance("toNanos"));
        put(m, "java/time/Duration.plus(Ljava/time/Duration;)Ljava/time/Duration;", instance("plus"));
        put(m, "java/time/Duration.minus(Ljava/time/Duration;)Ljava/time/Duration;", instance("minus"));
        put(m, "java/time/Duration.compareTo(Ljava/time/Duration;)I", instance("compareTo"));
        put(m, "java/time/DayOfWeek.of(I)Ljava/time/DayOfWeek;", statics("of"));
        put(m, "java/time/DayOfWeek.getValue()I", instance("getValue"));
        put(m, "java/time/DayOfWeek.name()Ljava/lang/String;", instance("name"));
        put(m, "java/time/LocalDateTime.now()Ljava/time/LocalDateTime;", statics("now"));
        put(m, "java/time/LocalDateTime.minusMinutes(J)Ljava/time/LocalDateTime;", instance("minusMinutes"));
        put(m, "java/time/LocalDateTime.plusMinutes(J)Ljava/time/LocalDateTime;", instance("plusMinutes"));
        put(m, "java/time/LocalDateTime.isBefore(Ljava/time/chrono/ChronoLocalDateTime;)Z", instance("isBefore"));
        put(m, "java/time/LocalDateTime.isAfter(Ljava/time/chrono/ChronoLocalDateTime;)Z", instance("isAfter"));
        put(m, "java/time/LocalDateTime.format(Ljava/time/format/DateTimeFormatter;)Ljava/lang/String;", instance("format"));
        put(m, "java/time/format/DateTimeFormatter.ofPattern(Ljava/lang/String;)Ljava/time/format/DateTimeFormatter;", statics("ofPattern"));
        put(m, "java/time/temporal/ChronoUnit.between(Ljava/time/temporal/Temporal;Ljava/time/temporal/Temporal;)J", instance("between"));
    }

    private static void addLangMethods6(Map<String, ShimTarget> m) {
        put(m, "java/lang/String.getBytes(Ljava/nio/charset/Charset;)[B", instance("getBytes"));
        put(m, "java/lang/String.trim()Ljava/lang/String;", instance("trim"));
    }

    private static void addSecurityMethods0(Map<String, ShimTarget> m) {
        put(m, "java/security/MessageDigest.getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;", statics("getInstance"));
        put(m, "java/security/MessageDigest.digest([B)[B", instance("digest"));
        put(m, "java/security/MessageDigest.reset()V", instance("reset"));
        put(m, "java/security/SecureRandom.nextBytes([B)V", instance("nextBytes"));
    }

    private static void addUtilMethods1(Map<String, ShimTarget> m) {
        put(m, "java/util/Base64.getEncoder()Ljava/util/Base64$Encoder;", statics("getEncoder"));
        put(m, "java/util/Base64.getDecoder()Ljava/util/Base64$Decoder;", statics("getDecoder"));
        put(m, "java/util/Base64$Encoder.encodeToString([B)Ljava/lang/String;", instance("encodeToString"));
        put(m, "java/util/Base64$Decoder.decode(Ljava/lang/String;)[B", instance("decode"));
    }

    private static void addLangMethods7(Map<String, ShimTarget> m) {
        put(m, "java/lang/Enum.name()Ljava/lang/String;", instance("name"));
        put(m, "java/lang/Enum.ordinal()I", instance("ordinal"));
        put(m, "java/lang/Enum.compareTo(Ljava/lang/Enum;)I", instance("compareTo"));
        put(m, "java/lang/Class.desiredAssertionStatus()Z", instance("desiredAssertionStatus"));
        put(m, "java/lang/String.valueOf(Ljava/lang/Object;)Ljava/lang/String;", statics("valueOf"));
        put(m, "java/lang/Class.getName()Ljava/lang/String;", instance("getName"));
        put(m, "java/lang/Class.getSimpleName()Ljava/lang/String;", instance("getSimpleName"));
        put(m, "java/lang/Class.isArray()Z", instance("isArray"));
        put(m, "java/lang/Class.getComponentType()Ljava/lang/Class;", instance("getComponentType"));
        put(m, "java/lang/Class.getSuperclass()Ljava/lang/Class;", instance("getSuperclass"));
        put(m, "java/lang/Class.getDeclaredFields()[Ljava/lang/reflect/Field;", instance("getDeclaredFields"));
        put(m, "java/lang/Class.getDeclaredMethods()[Ljava/lang/reflect/Method;", instance("getDeclaredMethods"));
        put(m, "java/lang/Class.getDeclaredConstructors()[Ljava/lang/reflect/Constructor;", instance("getDeclaredConstructors"));
        put(m, "java/lang/Class.getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;", instance("getDeclaredField"));
        put(m, "java/lang/Class.getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", instance("getDeclaredMethod"));
        put(m, "java/lang/Class.isInstance(Ljava/lang/Object;)Z", instance("isInstance"));
        put(m, "java/lang/Class.isAssignableFrom(Ljava/lang/Class;)Z", instance("isAssignableFrom"));
        put(m, "java/lang/Class.cast(Ljava/lang/Object;)Ljava/lang/Object;", instance("cast"));
        put(m, "java/lang/Class.isAnnotationPresent(Ljava/lang/Class;)Z", instance("isAnnotationPresent"));
        put(m, "java/lang/Class.getAnnotation(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;", instance("getAnnotation"));
        put(m, "java/lang/Class.getAnnotationsByType(Ljava/lang/Class;)[Ljava/lang/annotation/Annotation;", instance("getAnnotationsByType"));
        put(m, "java/lang/Class.getDeclaredAnnotations()[Ljava/lang/annotation/Annotation;", instance("getDeclaredAnnotations"));
        put(m, "java/lang/annotation/Annotation.annotationType()Ljava/lang/Class;", instance("annotationType"));
        put(m, "java/lang/reflect/Method.isAnnotationPresent(Ljava/lang/Class;)Z", instance("isAnnotationPresent"));
        put(m, "java/lang/reflect/Method.getAnnotation(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;", instance("getAnnotation"));
        put(m, "java/lang/reflect/Method.getAnnotationsByType(Ljava/lang/Class;)[Ljava/lang/annotation/Annotation;", instance("getAnnotationsByType"));
        put(m, "java/lang/reflect/Method.getDeclaredAnnotations()[Ljava/lang/annotation/Annotation;", instance("getDeclaredAnnotations"));
        put(m, "java/lang/reflect/Field.isAnnotationPresent(Ljava/lang/Class;)Z", instance("isAnnotationPresent"));
        put(m, "java/lang/reflect/Field.getAnnotation(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;", instance("getAnnotation"));
        put(m, "java/lang/reflect/Field.getAnnotationsByType(Ljava/lang/Class;)[Ljava/lang/annotation/Annotation;", instance("getAnnotationsByType"));
        put(m, "java/lang/reflect/Field.getDeclaredAnnotations()[Ljava/lang/annotation/Annotation;", instance("getDeclaredAnnotations"));
        put(m, "java/lang/reflect/Method.invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", instance("invoke"));
        put(m, "java/lang/reflect/Method.getName()Ljava/lang/String;", instance("getName"));
        put(m, "java/lang/reflect/Method.getReturnType()Ljava/lang/Class;", instance("getReturnType"));
        put(m, "java/lang/reflect/Method.getParameterTypes()[Ljava/lang/Class;", instance("getParameterTypes"));
        put(m, "java/lang/reflect/Method.getParameterCount()I", instance("getParameterCount"));
        put(m, "java/lang/reflect/Method.getModifiers()I", instance("getModifiers"));
        put(m, "java/lang/reflect/Method.getDeclaringClass()Ljava/lang/Class;", instance("getDeclaringClass"));
        put(m, "java/lang/reflect/Method.setAccessible(Z)V", instance("setAccessible"));
        put(m, "java/lang/reflect/Field.get(Ljava/lang/Object;)Ljava/lang/Object;", instance("get"));
        put(m, "java/lang/reflect/Field.set(Ljava/lang/Object;Ljava/lang/Object;)V", instance("set"));
        put(m, "java/lang/reflect/Field.getInt(Ljava/lang/Object;)I", instance("getInt"));
        put(m, "java/lang/reflect/Field.getLong(Ljava/lang/Object;)J", instance("getLong"));
        put(m, "java/lang/reflect/Field.getDouble(Ljava/lang/Object;)D", instance("getDouble"));
        put(m, "java/lang/reflect/Field.getFloat(Ljava/lang/Object;)F", instance("getFloat"));
        put(m, "java/lang/reflect/Field.getBoolean(Ljava/lang/Object;)Z", instance("getBoolean"));
        put(m, "java/lang/reflect/Field.getChar(Ljava/lang/Object;)C", instance("getChar"));
        put(m, "java/lang/reflect/Field.setInt(Ljava/lang/Object;I)V", instance("setInt"));
        put(m, "java/lang/reflect/Field.setLong(Ljava/lang/Object;J)V", instance("setLong"));
        put(m, "java/lang/reflect/Field.setDouble(Ljava/lang/Object;D)V", instance("setDouble"));
    }

    private static void addLangMethods8(Map<String, ShimTarget> m) {
        put(m, "java/lang/reflect/Field.setFloat(Ljava/lang/Object;F)V", instance("setFloat"));
        put(m, "java/lang/reflect/Field.setBoolean(Ljava/lang/Object;Z)V", instance("setBoolean"));
        put(m, "java/lang/reflect/Field.setChar(Ljava/lang/Object;C)V", instance("setChar"));
        put(m, "java/lang/reflect/Field.getName()Ljava/lang/String;", instance("getName"));
        put(m, "java/lang/reflect/Field.getType()Ljava/lang/Class;", instance("getType"));
        put(m, "java/lang/reflect/Field.getModifiers()I", instance("getModifiers"));
        put(m, "java/lang/reflect/Field.getDeclaringClass()Ljava/lang/Class;", instance("getDeclaringClass"));
        put(m, "java/lang/reflect/Field.setAccessible(Z)V", instance("setAccessible"));
        put(m, "java/lang/reflect/Array.getLength(Ljava/lang/Object;)I", statics("getLength"));
        put(m, "java/lang/reflect/Array.get(Ljava/lang/Object;I)Ljava/lang/Object;", statics("get"));
        put(m, "java/lang/reflect/Array.set(Ljava/lang/Object;ILjava/lang/Object;)V", statics("set"));
        put(m, "java/lang/reflect/Array.newInstance(Ljava/lang/Class;I)Ljava/lang/Object;", statics("newInstance"));
        put(m, "java/lang/reflect/Array.getInt(Ljava/lang/Object;I)I", statics("getInt"));
        put(m, "java/lang/reflect/Array.getLong(Ljava/lang/Object;I)J", statics("getLong"));
        put(m, "java/lang/reflect/Array.getDouble(Ljava/lang/Object;I)D", statics("getDouble"));
        put(m, "java/lang/reflect/Array.getFloat(Ljava/lang/Object;I)F", statics("getFloat"));
        put(m, "java/lang/reflect/Constructor.newInstance([Ljava/lang/Object;)Ljava/lang/Object;", instance("newInstance"));
        put(m, "java/lang/reflect/Constructor.getParameterTypes()[Ljava/lang/Class;", instance("getParameterTypes"));
        put(m, "java/lang/reflect/Constructor.getParameterCount()I", instance("getParameterCount"));
        put(m, "java/lang/reflect/Constructor.getModifiers()I", instance("getModifiers"));
        put(m, "java/lang/reflect/Constructor.getDeclaringClass()Ljava/lang/Class;", instance("getDeclaringClass"));
        put(m, "java/lang/reflect/Constructor.setAccessible(Z)V", instance("setAccessible"));
        put(m, "java/lang/reflect/Modifier.isPublic(I)Z", statics("isPublic"));
        put(m, "java/lang/reflect/Modifier.isPrivate(I)Z", statics("isPrivate"));
        put(m, "java/lang/reflect/Modifier.isProtected(I)Z", statics("isProtected"));
        put(m, "java/lang/reflect/Modifier.isStatic(I)Z", statics("isStatic"));
        put(m, "java/lang/reflect/Modifier.isFinal(I)Z", statics("isFinal"));
        put(m, "java/lang/reflect/Modifier.isAbstract(I)Z", statics("isAbstract"));
        put(m, "java/lang/reflect/Modifier.isSynchronized(I)Z", statics("isSynchronized"));
        put(m, "java/lang/reflect/Modifier.isVolatile(I)Z", statics("isVolatile"));
        put(m, "java/lang/reflect/Modifier.isTransient(I)Z", statics("isTransient"));
        put(m, "java/lang/reflect/Modifier.isNative(I)Z", statics("isNative"));
        put(m, "java/lang/reflect/Modifier.isInterface(I)Z", statics("isInterface"));
        put(m, "java/lang/reflect/Modifier.isStrict(I)Z", statics("isStrict"));
        put(m, "java/lang/reflect/Modifier.toString(I)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Runnable.run()V", instance("run"));
    }

    private static void addFunctionMethods0(Map<String, ShimTarget> m) {
        put(m, "java/util/function/Predicate.test(Ljava/lang/Object;)Z", instance("test"));
        put(m, "java/util/function/Function.apply(Ljava/lang/Object;)Ljava/lang/Object;", instance("apply"));
        put(m, "java/util/function/Function.identity()Ljava/util/function/Function;", statics("identity"));
        put(m, "java/util/function/Function.andThen(Ljava/util/function/Function;)Ljava/util/function/Function;", instance("andThen"));
        put(m, "java/util/function/Function.compose(Ljava/util/function/Function;)Ljava/util/function/Function;", instance("compose"));
        put(m, "java/util/function/BiFunction.apply(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", instance("apply"));
        put(m, "java/util/function/BinaryOperator.apply(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", instance("apply"));
        put(m, "java/util/function/Consumer.accept(Ljava/lang/Object;)V", instance("accept"));
        put(m, "java/util/function/BiConsumer.accept(Ljava/lang/Object;Ljava/lang/Object;)V", instance("accept"));
        put(m, "java/util/function/Supplier.get()Ljava/lang/Object;", instance("get"));
        put(m, "java/util/function/IntFunction.apply(I)Ljava/lang/Object;", instance("apply"));
        put(m, "java/util/function/ToLongFunction.applyAsLong(Ljava/lang/Object;)J", instance("applyAsLong"));
        put(m, "java/util/function/ToIntFunction.applyAsInt(Ljava/lang/Object;)I", instance("applyAsInt"));
        put(m, "java/util/function/ToDoubleFunction.applyAsDouble(Ljava/lang/Object;)D", instance("applyAsDouble"));
        put(m, "java/util/function/IntToDoubleFunction.applyAsDouble(I)D", instance("applyAsDouble"));
        put(m, "java/util/function/DoublePredicate.test(D)Z", instance("test"));
        put(m, "java/util/function/DoubleConsumer.accept(D)V", instance("accept"));
        put(m, "java/util/function/IntConsumer.accept(I)V", instance("accept"));
        put(m, "java/util/function/IntPredicate.test(I)Z", instance("test"));
        put(m, "java/util/function/IntUnaryOperator.applyAsInt(I)I", instance("applyAsInt"));
        put(m, "java/util/function/IntBinaryOperator.applyAsInt(II)I", instance("applyAsInt"));
    }

    private static void addRegexMethods0(Map<String, ShimTarget> m) {
        put(m, "java/util/regex/Pattern.compile(Ljava/lang/String;)Ljava/util/regex/Pattern;", statics("compile"));
        put(m, "java/util/regex/Pattern.compile(Ljava/lang/String;I)Ljava/util/regex/Pattern;", statics("compile"));
        put(m, "java/util/regex/Pattern.matcher(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;", instance("matcher"));
        put(m, "java/util/regex/Pattern.pattern()Ljava/lang/String;", instance("pattern"));
        put(m, "java/util/regex/Pattern.split(Ljava/lang/CharSequence;)[Ljava/lang/String;", instance("split"));
        put(m, "java/util/regex/Pattern.matches(Ljava/lang/String;Ljava/lang/CharSequence;)Z", statics("matches"));
        put(m, "java/util/regex/Pattern.quote(Ljava/lang/String;)Ljava/lang/String;", statics("quote"));
        put(m, "java/util/regex/Matcher.matches()Z", instance("matches"));
        put(m, "java/util/regex/Matcher.lookingAt()Z", instance("lookingAt"));
        put(m, "java/util/regex/Matcher.find()Z", instance("find"));
        put(m, "java/util/regex/Matcher.find(I)Z", instance("find"));
        put(m, "java/util/regex/Matcher.group()Ljava/lang/String;", instance("group"));
        put(m, "java/util/regex/Matcher.group(I)Ljava/lang/String;", instance("group"));
        put(m, "java/util/regex/Matcher.groupCount()I", instance("groupCount"));
        put(m, "java/util/regex/Matcher.start()I", instance("start"));
        put(m, "java/util/regex/Matcher.start(I)I", instance("start"));
        put(m, "java/util/regex/Matcher.end()I", instance("end"));
        put(m, "java/util/regex/Matcher.end(I)I", instance("end"));
        put(m, "java/util/regex/Matcher.replaceAll(Ljava/lang/String;)Ljava/lang/String;", instance("replaceAll"));
        put(m, "java/util/regex/Matcher.replaceFirst(Ljava/lang/String;)Ljava/lang/String;", instance("replaceFirst"));
        put(m, "java/util/regex/Matcher.reset()Ljava/util/regex/Matcher;", instance("reset"));
        put(m, "java/util/regex/Matcher.reset(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;", instance("reset"));
    }

    private static void addUtilMethods2(Map<String, ShimTarget> m) {
        put(m, "java/util/Random.setSeed(J)V", instance("setSeed"));
        put(m, "java/util/Random.nextInt()I", instance("nextInt"));
        put(m, "java/util/Random.nextInt(I)I", instance("nextInt"));
        put(m, "java/util/Random.nextLong()J", instance("nextLong"));
        put(m, "java/util/Random.nextBoolean()Z", instance("nextBoolean"));
        put(m, "java/util/Random.nextDouble()D", instance("nextDouble"));
        put(m, "java/util/Random.nextFloat()F", instance("nextFloat"));
        put(m, "java/util/OptionalInt.getAsInt()I", instance("getAsInt"));
        put(m, "java/util/OptionalInt.isPresent()Z", instance("isPresent"));
        put(m, "java/util/OptionalInt.isEmpty()Z", instance("isEmpty"));
        put(m, "java/util/OptionalInt.orElse(I)I", instance("orElse"));
        put(m, "java/util/OptionalDouble.getAsDouble()D", instance("getAsDouble"));
        put(m, "java/util/OptionalDouble.isPresent()Z", instance("isPresent"));
        put(m, "java/util/OptionalDouble.isEmpty()Z", instance("isEmpty"));
        put(m, "java/util/OptionalDouble.orElse(D)D", instance("orElse"));
        put(m, "java/util/Collections.sort(Ljava/util/List;)V", statics("sort"));
        put(m, "java/util/Collections.sort(Ljava/util/List;Ljava/util/Comparator;)V", statics("sort"));
        put(m, "java/util/Collections.reverse(Ljava/util/List;)V", statics("reverse"));
        put(m, "java/util/Collections.emptyList()Ljava/util/List;", statics("emptyList"));
        put(m, "java/util/Collections.emptyMap()Ljava/util/Map;", statics("emptyMap"));
        put(m, "java/util/Collections.emptySet()Ljava/util/Set;", statics("emptySet"));
        put(m, "java/util/Collections.singletonList(Ljava/lang/Object;)Ljava/util/List;", statics("singletonList"));
        put(m, "java/util/Collections.unmodifiableList(Ljava/util/List;)Ljava/util/List;", statics("unmodifiableList"));
        put(m, "java/util/Collections.unmodifiableMap(Ljava/util/Map;)Ljava/util/Map;", statics("unmodifiableMap"));
        put(m, "java/util/Collections.unmodifiableSet(Ljava/util/Set;)Ljava/util/Set;", statics("unmodifiableSet"));
        put(m, "java/util/Collections.unmodifiableCollection(Ljava/util/Collection;)Ljava/util/Collection;", statics("unmodifiableCollection"));
        put(m, "java/util/Collections.max(Ljava/util/Collection;)Ljava/lang/Object;", statics("max"));
        put(m, "java/util/Collections.max(Ljava/util/Collection;Ljava/util/Comparator;)Ljava/lang/Object;", statics("max"));
        put(m, "java/util/Collections.min(Ljava/util/Collection;)Ljava/lang/Object;", statics("min"));
        put(m, "java/util/Collections.min(Ljava/util/Collection;Ljava/util/Comparator;)Ljava/lang/Object;", statics("min"));
        put(m, "java/util/Collections.frequency(Ljava/util/Collection;Ljava/lang/Object;)I", statics("frequency"));
    }

    private static void addLangMethods9(Map<String, ShimTarget> m) {
        put(m, "java/lang/Comparable.compareTo(Ljava/lang/Object;)I", instance("compareTo"));
    }

    private static void addAwtMethods0(Map<String, ShimTarget> m) {
        put(m, "java/awt/Color.getHSBColor(FFF)Ljava/awt/Color;", statics("getHSBColor"));
        put(m, "java/awt/Color.getRed()I", instance("getRed"));
        put(m, "java/awt/Color.getGreen()I", instance("getGreen"));
        put(m, "java/awt/Color.getBlue()I", instance("getBlue"));
        put(m, "java/awt/Color.getAlpha()I", instance("getAlpha"));
        put(m, "java/awt/Color.getRGB()I", instance("getRGB"));
    }

    private static void addUtilMethods3(Map<String, ShimTarget> m) {
        put(m, "java/util/Comparator.compare(Ljava/lang/Object;Ljava/lang/Object;)I", instance("compare"));
        put(m, "java/util/Comparator.naturalOrder()Ljava/util/Comparator;", statics("naturalOrder"));
        put(m, "java/util/Comparator.reverseOrder()Ljava/util/Comparator;", statics("reverseOrder"));
        put(m, "java/util/Comparator.comparingInt(Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;", statics("comparingInt"));
        put(m, "java/util/Comparator.comparingLong(Ljava/util/function/ToLongFunction;)Ljava/util/Comparator;", statics("comparingLong"));
        put(m, "java/util/Comparator.comparingDouble(Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;", statics("comparingDouble"));
        put(m, "java/util/Comparator.comparing(Ljava/util/function/Function;)Ljava/util/Comparator;", statics("comparing"));
        put(m, "java/util/Comparator.comparing(Ljava/util/function/Function;Ljava/util/Comparator;)Ljava/util/Comparator;", statics("comparing"));
        put(m, "java/util/Comparator.reversed()Ljava/util/Comparator;", instance("reversed"));
        put(m, "java/util/Comparator.thenComparing(Ljava/util/Comparator;)Ljava/util/Comparator;", instance("thenComparing"));
        put(m, "java/util/Comparator.thenComparing(Ljava/util/function/Function;)Ljava/util/Comparator;", instance("thenComparing"));
        put(m, "java/util/Comparator.thenComparingInt(Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;", instance("thenComparingInt"));
        put(m, "java/util/Optional.empty()Ljava/util/Optional;", statics("empty"));
        put(m, "java/util/Optional.of(Ljava/lang/Object;)Ljava/util/Optional;", statics("of"));
        put(m, "java/util/Optional.ofNullable(Ljava/lang/Object;)Ljava/util/Optional;", statics("ofNullable"));
        put(m, "java/util/Optional.isPresent()Z", instance("isPresent"));
        put(m, "java/util/Optional.isEmpty()Z", instance("isEmpty"));
        put(m, "java/util/Optional.get()Ljava/lang/Object;", instance("get"));
        put(m, "java/util/Optional.orElse(Ljava/lang/Object;)Ljava/lang/Object;", instance("orElse"));
        put(m, "java/util/Optional.orElseGet(Ljava/util/function/Supplier;)Ljava/lang/Object;", instance("orElseGet"));
        put(m, "java/util/Optional.orElseThrow(Ljava/util/function/Supplier;)Ljava/lang/Object;", instance("orElseThrow"));
        put(m, "java/util/Optional.map(Ljava/util/function/Function;)Ljava/util/Optional;", instance("map"));
        put(m, "java/util/Optional.filter(Ljava/util/function/Predicate;)Ljava/util/Optional;", instance("filter"));
        put(m, "java/util/Optional.ifPresent(Ljava/util/function/Consumer;)V", instance("ifPresent"));
        put(m, "java/util/Arrays.stream([Ljava/lang/Object;)Ljava/util/stream/Stream;", statics("stream"));
        put(m, "java/util/Arrays.toString([Ljava/lang/Object;)Ljava/lang/String;", statics("toString"));
        put(m, "java/util/Arrays.asList([Ljava/lang/Object;)Ljava/util/List;", statics("asList"));
        put(m, "java/util/Arrays.sort([I)V", statics("sort"));
        put(m, "java/util/Arrays.sort([J)V", statics("sort"));
        put(m, "java/util/Arrays.sort([D)V", statics("sort"));
        put(m, "java/util/Arrays.sort([C)V", statics("sort"));
        put(m, "java/util/Arrays.sort([Ljava/lang/Object;)V", statics("sort"));
        put(m, "java/util/Arrays.sort([Ljava/lang/Object;Ljava/util/Comparator;)V", statics("sort"));
        put(m, "java/util/Arrays.copyOf([II)[I", statics("copyOf"));
        put(m, "java/util/Arrays.copyOf([Ljava/lang/Object;I)[Ljava/lang/Object;", statics("copyOf"));
        put(m, "java/util/Arrays.copyOfRange([III)[I", statics("copyOfRange"));
        put(m, "java/util/Arrays.copyOfRange([Ljava/lang/Object;II)[Ljava/lang/Object;", statics("copyOfRange"));
        put(m, "java/util/Arrays.equals([I[I)Z", statics("equals"));
        put(m, "java/util/Arrays.equals([Ljava/lang/Object;[Ljava/lang/Object;)Z", statics("equals"));
        put(m, "java/util/Arrays.binarySearch([II)I", statics("binarySearch"));
        put(m, "java/util/Arrays.fill([II)V", statics("fill"));
        put(m, "java/util/Arrays.fill([JJ)V", statics("fill"));
        put(m, "java/util/Arrays.fill([Ljava/lang/Object;Ljava/lang/Object;)V", statics("fill"));
        put(m, "java/util/Arrays.stream([I)Ljava/util/stream/IntStream;", statics("stream"));
        put(m, "java/util/Arrays.toString([I)Ljava/lang/String;", statics("toString"));
        put(m, "java/util/Arrays.toString([J)Ljava/lang/String;", statics("toString"));
        put(m, "java/util/Arrays.toString([D)Ljava/lang/String;", statics("toString"));
        put(m, "java/util/Arrays.toString([C)Ljava/lang/String;", statics("toString"));
        put(m, "java/util/Collection.stream()Ljava/util/stream/Stream;", instance("stream"));
        put(m, "java/util/Collection.parallelStream()Ljava/util/stream/Stream;", instance("parallelStream"));
    }

    private static void addUtilMethods4(Map<String, ShimTarget> m) {
        put(m, "java/util/Collection.forEach(Ljava/util/function/Consumer;)V", instance("forEach"));
    }

    private static void addStreamMethods0(Map<String, ShimTarget> m) {
        put(m, "java/util/stream/Collectors.toList()Ljava/util/stream/Collector;", statics("toList"));
        put(m, "java/util/stream/Collectors.toSet()Ljava/util/stream/Collector;", statics("toSet"));
        put(m, "java/util/stream/Collectors.joining()Ljava/util/stream/Collector;", statics("joining"));
        put(m, "java/util/stream/Collectors.joining(Ljava/lang/CharSequence;)Ljava/util/stream/Collector;", statics("joining"));
        put(m, "java/util/stream/Collectors.joining(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/util/stream/Collector;", statics("joining"));
        put(m, "java/util/stream/Collectors.toMap(Ljava/util/function/Function;Ljava/util/function/Function;)Ljava/util/stream/Collector;", statics("toMap"));
        put(m, "java/util/stream/Collectors.toMap(Ljava/util/function/Function;Ljava/util/function/Function;Ljava/util/function/BinaryOperator;)Ljava/util/stream/Collector;", statics("toMap"));
        put(m, "java/util/stream/Collectors.groupingBy(Ljava/util/function/Function;)Ljava/util/stream/Collector;", statics("groupingBy"));
        put(m, "java/util/stream/Collectors.groupingBy(Ljava/util/function/Function;Ljava/util/stream/Collector;)Ljava/util/stream/Collector;", statics("groupingBy"));
        put(m, "java/util/stream/Collectors.counting()Ljava/util/stream/Collector;", statics("counting"));
        put(m, "java/util/stream/Collectors.summingInt(Ljava/util/function/ToIntFunction;)Ljava/util/stream/Collector;", statics("summingInt"));
        put(m, "java/util/stream/Collectors.averagingInt(Ljava/util/function/ToIntFunction;)Ljava/util/stream/Collector;", statics("averagingInt"));
        put(m, "java/util/stream/Collectors.partitioningBy(Ljava/util/function/Predicate;)Ljava/util/stream/Collector;", statics("partitioningBy"));
        put(m, "java/util/stream/Collectors.mapping(Ljava/util/function/Function;Ljava/util/stream/Collector;)Ljava/util/stream/Collector;", statics("mapping"));
        put(m, "java/util/stream/Collectors.minBy(Ljava/util/Comparator;)Ljava/util/stream/Collector;", statics("minBy"));
        put(m, "java/util/stream/Collectors.maxBy(Ljava/util/Comparator;)Ljava/util/stream/Collector;", statics("maxBy"));
        put(m, "java/util/stream/Collectors.reducing(Ljava/lang/Object;Ljava/util/function/BinaryOperator;)Ljava/util/stream/Collector;", statics("reducing"));
        put(m, "java/util/stream/Collectors.reducing(Ljava/util/function/BinaryOperator;)Ljava/util/stream/Collector;", statics("reducing"));
        put(m, "java/util/stream/Collectors.reducing(Ljava/lang/Object;Ljava/util/function/Function;Ljava/util/function/BinaryOperator;)Ljava/util/stream/Collector;", statics("reducing"));
        put(m, "java/util/stream/Collectors.summingLong(Ljava/util/function/ToLongFunction;)Ljava/util/stream/Collector;", statics("summingLong"));
        put(m, "java/util/stream/Collectors.summingDouble(Ljava/util/function/ToDoubleFunction;)Ljava/util/stream/Collector;", statics("summingDouble"));
        put(m, "java/util/stream/Collectors.averagingLong(Ljava/util/function/ToLongFunction;)Ljava/util/stream/Collector;", statics("averagingLong"));
        put(m, "java/util/stream/Collectors.averagingDouble(Ljava/util/function/ToDoubleFunction;)Ljava/util/stream/Collector;", statics("averagingDouble"));
        put(m, "java/util/stream/Collectors.collectingAndThen(Ljava/util/stream/Collector;Ljava/util/function/Function;)Ljava/util/stream/Collector;", statics("collectingAndThen"));
        put(m, "java/util/stream/Collectors.toCollection(Ljava/util/function/Supplier;)Ljava/util/stream/Collector;", statics("toCollection"));
        put(m, "java/util/stream/Collectors.summarizingInt(Ljava/util/function/ToIntFunction;)Ljava/util/stream/Collector;", statics("summarizingInt"));
        put(m, "java/util/stream/Collectors.summarizingLong(Ljava/util/function/ToLongFunction;)Ljava/util/stream/Collector;", statics("summarizingLong"));
        put(m, "java/util/stream/Collectors.summarizingDouble(Ljava/util/function/ToDoubleFunction;)Ljava/util/stream/Collector;", statics("summarizingDouble"));
        put(m, "java/util/stream/Collectors.toUnmodifiableList()Ljava/util/stream/Collector;", statics("toUnmodifiableList"));
        put(m, "java/util/stream/Collectors.toUnmodifiableSet()Ljava/util/stream/Collector;", statics("toUnmodifiableSet"));
        put(m, "java/util/stream/Collectors.toUnmodifiableMap(Ljava/util/function/Function;Ljava/util/function/Function;)Ljava/util/stream/Collector;", statics("toUnmodifiableMap"));
        put(m, "java/util/stream/Collectors.filtering(Ljava/util/function/Predicate;Ljava/util/stream/Collector;)Ljava/util/stream/Collector;", statics("filtering"));
        put(m, "java/util/stream/Collectors.flatMapping(Ljava/util/function/Function;Ljava/util/stream/Collector;)Ljava/util/stream/Collector;", statics("flatMapping"));
        put(m, "java/util/stream/Collectors.teeing(Ljava/util/stream/Collector;Ljava/util/stream/Collector;Ljava/util/function/BiFunction;)Ljava/util/stream/Collector;", statics("teeing"));
        put(m, "java/util/stream/Collectors.toConcurrentMap(Ljava/util/function/Function;Ljava/util/function/Function;)Ljava/util/stream/Collector;", statics("toConcurrentMap"));
        put(m, "java/util/stream/Collectors.toConcurrentMap(Ljava/util/function/Function;Ljava/util/function/Function;Ljava/util/function/BinaryOperator;)Ljava/util/stream/Collector;", statics("toConcurrentMap"));
        put(m, "java/util/stream/Collectors.groupingByConcurrent(Ljava/util/function/Function;)Ljava/util/stream/Collector;", statics("groupingByConcurrent"));
        put(m, "java/util/stream/Collectors.groupingByConcurrent(Ljava/util/function/Function;Ljava/util/stream/Collector;)Ljava/util/stream/Collector;", statics("groupingByConcurrent"));
        put(m, "java/util/IntSummaryStatistics.getCount()J", instance("getCount"));
        put(m, "java/util/IntSummaryStatistics.getSum()J", instance("getSum"));
        put(m, "java/util/IntSummaryStatistics.getMin()I", instance("getMin"));
        put(m, "java/util/IntSummaryStatistics.getMax()I", instance("getMax"));
        put(m, "java/util/IntSummaryStatistics.getAverage()D", instance("getAverage"));
        put(m, "java/util/LongSummaryStatistics.getCount()J", instance("getCount"));
        put(m, "java/util/LongSummaryStatistics.getSum()J", instance("getSum"));
        put(m, "java/util/LongSummaryStatistics.getMin()J", instance("getMin"));
        put(m, "java/util/LongSummaryStatistics.getMax()J", instance("getMax"));
        put(m, "java/util/LongSummaryStatistics.getAverage()D", instance("getAverage"));
        put(m, "java/util/DoubleSummaryStatistics.getCount()J", instance("getCount"));
        put(m, "java/util/DoubleSummaryStatistics.getSum()D", instance("getSum"));
        put(m, "java/util/DoubleSummaryStatistics.getMin()D", instance("getMin"));
        put(m, "java/util/DoubleSummaryStatistics.getMax()D", instance("getMax"));
        put(m, "java/util/DoubleSummaryStatistics.getAverage()D", instance("getAverage"));
        put(m, "java/util/stream/Stream.map(Ljava/util/function/Function;)Ljava/util/stream/Stream;", instance("map"));
        put(m, "java/util/stream/Stream.filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;", instance("filter"));
        put(m, "java/util/stream/Stream.peek(Ljava/util/function/Consumer;)Ljava/util/stream/Stream;", instance("peek"));
        put(m, "java/util/stream/Stream.distinct()Ljava/util/stream/Stream;", instance("distinct"));
        put(m, "java/util/stream/Stream.sorted()Ljava/util/stream/Stream;", instance("sorted"));
        put(m, "java/util/stream/Stream.sorted(Ljava/util/Comparator;)Ljava/util/stream/Stream;", instance("sorted"));
        put(m, "java/util/stream/Stream.limit(J)Ljava/util/stream/Stream;", instance("limit"));
        put(m, "java/util/stream/Stream.skip(J)Ljava/util/stream/Stream;", instance("skip"));
        put(m, "java/util/stream/Stream.flatMap(Ljava/util/function/Function;)Ljava/util/stream/Stream;", instance("flatMap"));
        put(m, "java/util/stream/Stream.forEach(Ljava/util/function/Consumer;)V", instance("forEach"));
        put(m, "java/util/stream/Stream.collect(Ljava/util/stream/Collector;)Ljava/lang/Object;", instance("collect"));
        put(m, "java/util/stream/Stream.count()J", instance("count"));
        put(m, "java/util/stream/Stream.toArray()[Ljava/lang/Object;", instance("toArray"));
        put(m, "java/util/stream/Stream.toArray(Ljava/util/function/IntFunction;)[Ljava/lang/Object;", instance("toArray"));
        put(m, "java/util/stream/Stream.reduce(Ljava/util/function/BinaryOperator;)Ljava/util/Optional;", instance("reduce"));
        put(m, "java/util/stream/Stream.reduce(Ljava/lang/Object;Ljava/util/function/BinaryOperator;)Ljava/lang/Object;", instance("reduce"));
        put(m, "java/util/stream/Stream.max(Ljava/util/Comparator;)Ljava/util/Optional;", instance("max"));
        put(m, "java/util/stream/Stream.min(Ljava/util/Comparator;)Ljava/util/Optional;", instance("min"));
        put(m, "java/util/stream/Stream.findFirst()Ljava/util/Optional;", instance("findFirst"));
        put(m, "java/util/stream/Stream.anyMatch(Ljava/util/function/Predicate;)Z", instance("anyMatch"));
        put(m, "java/util/stream/Stream.allMatch(Ljava/util/function/Predicate;)Z", instance("allMatch"));
        put(m, "java/util/stream/Stream.noneMatch(Ljava/util/function/Predicate;)Z", instance("noneMatch"));
        put(m, "java/util/stream/Stream.mapToLong(Ljava/util/function/ToLongFunction;)Ljava/util/stream/LongStream;", instance("mapToLong"));
        put(m, "java/util/stream/Stream.mapToInt(Ljava/util/function/ToIntFunction;)Ljava/util/stream/IntStream;", instance("mapToInt"));
        put(m, "java/util/stream/Stream.mapToDouble(Ljava/util/function/ToDoubleFunction;)Ljava/util/stream/DoubleStream;", instance("mapToDouble"));
        put(m, "java/util/stream/IntStream.of([I)Ljava/util/stream/IntStream;", statics("of"));
        put(m, "java/util/stream/IntStream.range(II)Ljava/util/stream/IntStream;", statics("range"));
        put(m, "java/util/stream/IntStream.rangeClosed(II)Ljava/util/stream/IntStream;", statics("rangeClosed"));
        put(m, "java/util/stream/IntStream.parallel()Ljava/util/stream/IntStream;", instance("parallel"));
        put(m, "java/util/stream/IntStream.sequential()Ljava/util/stream/IntStream;", instance("sequential"));
        put(m, "java/util/stream/IntStream.boxed()Ljava/util/stream/Stream;", instance("boxed"));
        put(m, "java/util/stream/IntStream.mapToObj(Ljava/util/function/IntFunction;)Ljava/util/stream/Stream;", instance("mapToObj"));
        put(m, "java/util/stream/IntStream.mapToDouble(Ljava/util/function/IntToDoubleFunction;)Ljava/util/stream/DoubleStream;", instance("mapToDouble"));
        put(m, "java/util/stream/IntStream.forEach(Ljava/util/function/IntConsumer;)V", instance("forEach"));
        put(m, "java/util/stream/IntStream.filter(Ljava/util/function/IntPredicate;)Ljava/util/stream/IntStream;", instance("filter"));
        put(m, "java/util/stream/IntStream.map(Ljava/util/function/IntUnaryOperator;)Ljava/util/stream/IntStream;", instance("map"));
    }

    private static void addStreamMethods1(Map<String, ShimTarget> m) {
        put(m, "java/util/stream/IntStream.reduce(ILjava/util/function/IntBinaryOperator;)I", instance("reduce"));
        put(m, "java/util/stream/IntStream.sorted()Ljava/util/stream/IntStream;", instance("sorted"));
        put(m, "java/util/stream/IntStream.distinct()Ljava/util/stream/IntStream;", instance("distinct"));
        put(m, "java/util/stream/IntStream.limit(J)Ljava/util/stream/IntStream;", instance("limit"));
        put(m, "java/util/stream/IntStream.skip(J)Ljava/util/stream/IntStream;", instance("skip"));
        put(m, "java/util/stream/IntStream.anyMatch(Ljava/util/function/IntPredicate;)Z", instance("anyMatch"));
        put(m, "java/util/stream/IntStream.allMatch(Ljava/util/function/IntPredicate;)Z", instance("allMatch"));
        put(m, "java/util/stream/IntStream.noneMatch(Ljava/util/function/IntPredicate;)Z", instance("noneMatch"));
        put(m, "java/util/stream/IntStream.min()Ljava/util/OptionalInt;", instance("min"));
        put(m, "java/util/stream/IntStream.max()Ljava/util/OptionalInt;", instance("max"));
        put(m, "java/util/stream/IntStream.average()Ljava/util/OptionalDouble;", instance("average"));
        put(m, "java/util/stream/IntStream.sum()I", instance("sum"));
        put(m, "java/util/stream/IntStream.count()J", instance("count"));
        put(m, "java/util/stream/IntStream.toArray()[I", instance("toArray"));
        put(m, "java/util/stream/LongStream.sum()J", instance("sum"));
        put(m, "java/util/stream/LongStream.count()J", instance("count"));
        put(m, "java/util/stream/DoubleStream.filter(Ljava/util/function/DoublePredicate;)Ljava/util/stream/DoubleStream;", instance("filter"));
        put(m, "java/util/stream/DoubleStream.forEach(Ljava/util/function/DoubleConsumer;)V", instance("forEach"));
        put(m, "java/util/stream/DoubleStream.sum()D", instance("sum"));
        put(m, "java/util/stream/DoubleStream.count()J", instance("count"));
    }

    private static void addUtilMethods5(Map<String, ShimTarget> m) {
        put(m, "java/util/List.clear()V", instance("clear"));
        put(m, "java/util/List.removeIf(Ljava/util/function/Predicate;)Z", instance("removeIf"));
        put(m, "java/util/List.sort(Ljava/util/Comparator;)V", instance("sort"));
    }

    private static void addLangMethods10(Map<String, ShimTarget> m) {
        put(m, "java/lang/StringBuffer.append(Ljava/lang/String;)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append(I)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append(Ljava/lang/Object;)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.length()I", instance("length"));
        put(m, "java/lang/StringBuffer.toString()Ljava/lang/String;", instance("toString"));
    }

    private static void addUtilMethods6(Map<String, ShimTarget> m) {
        put(m, "java/util/Objects.requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;", statics("requireNonNull"));
        put(m, "java/util/Iterable.iterator()Ljava/util/Iterator;", instance("iterator"));
        put(m, "java/util/Iterator.hasNext()Z", instance("hasNext"));
        put(m, "java/util/Iterator.next()Ljava/lang/Object;", instance("next"));
        put(m, "java/util/Queue.offer(Ljava/lang/Object;)Z", instance("offer"));
        put(m, "java/util/Queue.poll()Ljava/lang/Object;", instance("poll"));
        put(m, "java/util/Queue.peek()Ljava/lang/Object;", instance("peek"));
        put(m, "java/util/Deque.push(Ljava/lang/Object;)V", instance("push"));
        put(m, "java/util/Deque.pop()Ljava/lang/Object;", instance("pop"));
        put(m, "java/util/Deque.addFirst(Ljava/lang/Object;)V", instance("addFirst"));
        put(m, "java/util/Deque.addLast(Ljava/lang/Object;)V", instance("addLast"));
        put(m, "java/util/Deque.offerFirst(Ljava/lang/Object;)Z", instance("offerFirst"));
        put(m, "java/util/Deque.offerLast(Ljava/lang/Object;)Z", instance("offerLast"));
        put(m, "java/util/Deque.pollFirst()Ljava/lang/Object;", instance("pollFirst"));
        put(m, "java/util/Deque.pollLast()Ljava/lang/Object;", instance("pollLast"));
        put(m, "java/util/Deque.peekFirst()Ljava/lang/Object;", instance("peekFirst"));
        put(m, "java/util/Deque.peekLast()Ljava/lang/Object;", instance("peekLast"));
        put(m, "java/util/Deque.getFirst()Ljava/lang/Object;", instance("getFirst"));
        put(m, "java/util/Deque.getLast()Ljava/lang/Object;", instance("getLast"));
        put(m, "java/util/Deque.removeFirst()Ljava/lang/Object;", instance("removeFirst"));
        put(m, "java/util/Deque.removeLast()Ljava/lang/Object;", instance("removeLast"));
        put(m, "java/util/TreeSet.first()Ljava/lang/Object;", instance("first"));
        put(m, "java/util/TreeSet.last()Ljava/lang/Object;", instance("last"));
        put(m, "java/util/TreeSet.pollFirst()Ljava/lang/Object;", instance("pollFirst"));
        put(m, "java/util/TreeSet.pollLast()Ljava/lang/Object;", instance("pollLast"));
        put(m, "java/util/TreeSet.floor(Ljava/lang/Object;)Ljava/lang/Object;", instance("floor"));
        put(m, "java/util/TreeSet.ceiling(Ljava/lang/Object;)Ljava/lang/Object;", instance("ceiling"));
    }

    private static void addSwingMethods0(Map<String, ShimTarget> m) {
        put(m, "javax/swing/JComboBox.addItem(Ljava/lang/Object;)V", instance("addItem"));
        put(m, "javax/swing/JComboBox.getItemAt(I)Ljava/lang/Object;", instance("getItemAt"));
        put(m, "javax/swing/JComboBox.getItemCount()I", instance("getItemCount"));
        put(m, "javax/swing/JComboBox.getSelectedIndex()I", instance("getSelectedIndex"));
        put(m, "javax/swing/JComboBox.getSelectedItem()Ljava/lang/Object;", instance("getSelectedItem"));
        put(m, "javax/swing/JComboBox.setSelectedIndex(I)V", instance("setSelectedIndex"));
        put(m, "javax/swing/JComboBox.removeAllItems()V", instance("removeAllItems"));
        put(m, "javax/swing/JComboBox.addActionListener(Ljava/awt/event/ActionListener;)V", instance("addActionListener"));
        put(m, "javax/swing/JRadioButton.isSelected()Z", instance("isSelected"));
        put(m, "javax/swing/JRadioButton.setSelected(Z)V", instance("setSelected"));
        put(m, "javax/swing/JRadioButton.setText(Ljava/lang/String;)V", instance("setText"));
        put(m, "javax/swing/JRadioButton.addActionListener(Ljava/awt/event/ActionListener;)V", instance("addActionListener"));
        put(m, "javax/swing/ButtonGroup.add(Ljavax/swing/AbstractButton;)V", instance("add"));
        put(m, "javax/swing/JTextArea.getText()Ljava/lang/String;", instance("getText"));
        put(m, "javax/swing/JTextArea.setText(Ljava/lang/String;)V", instance("setText"));
        put(m, "javax/swing/JTextArea.append(Ljava/lang/String;)V", instance("append"));
        put(m, "javax/swing/JTextArea.setEditable(Z)V", instance("setEditable"));
        put(m, "javax/swing/JTextArea.setLineWrap(Z)V", instance("setLineWrap"));
        put(m, "javax/swing/JTextArea.setWrapStyleWord(Z)V", instance("setWrapStyleWord"));
        put(m, "javax/swing/JTextArea.setRows(I)V", instance("setRows"));
        put(m, "javax/swing/JTextArea.setColumns(I)V", instance("setColumns"));
        put(m, "javax/swing/JTabbedPane.addTab(Ljava/lang/String;Ljava/awt/Component;)V", instance("addTab"));
        put(m, "javax/swing/JTabbedPane.getSelectedIndex()I", instance("getSelectedIndex"));
        put(m, "javax/swing/JTabbedPane.setSelectedIndex(I)V", instance("setSelectedIndex"));
        put(m, "javax/swing/JTabbedPane.getTabCount()I", instance("getTabCount"));
    }

    private static void addAwtMethods1(Map<String, ShimTarget> m) {
        put(m, "java/awt/Component.setName(Ljava/lang/String;)V", instance("setName"));
        put(m, "java/awt/Component.getName()Ljava/lang/String;", instance("getName"));
        put(m, "java/awt/Component.getWidth()I", instance("getWidth"));
        put(m, "java/awt/Component.getHeight()I", instance("getHeight"));
        put(m, "java/awt/Component.getX()I", instance("getX"));
        put(m, "java/awt/Component.getY()I", instance("getY"));
        put(m, "java/awt/Component.firePropertyChange(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", instance("firePropertyChange"));
    }

    private static void addLangMethods11(Map<String, ShimTarget> m) {
        put(m, "java/lang/Package.getName()Ljava/lang/String;", instance("getName"));
        put(m, "java/lang/Class.getPackage()Ljava/lang/Package;", instance("getPackage"));
    }

    private static void addSwingMethods1(Map<String, ShimTarget> m) {
        put(m, "javax/swing/JFileChooser.showOpenDialog(Ljava/awt/Component;)I", instance("showOpenDialog"));
        put(m, "javax/swing/JFileChooser.showSaveDialog(Ljava/awt/Component;)I", instance("showSaveDialog"));
        put(m, "javax/swing/JFileChooser.setDialogTitle(Ljava/lang/String;)V", instance("setDialogTitle"));
    }

    private static void addUtilMethods7(Map<String, ShimTarget> m) {
        put(m, "java/util/Collection.add(Ljava/lang/Object;)Z", instance("add"));
        put(m, "java/util/Collection.size()I", instance("size"));
        put(m, "java/util/Collection.isEmpty()Z", instance("isEmpty"));
        put(m, "java/util/Collection.contains(Ljava/lang/Object;)Z", instance("contains"));
        put(m, "java/util/Collection.remove(Ljava/lang/Object;)Z", instance("remove"));
        put(m, "java/util/List.get(I)Ljava/lang/Object;", instance("get"));
        put(m, "java/util/List.set(ILjava/lang/Object;)Ljava/lang/Object;", instance("set"));
        put(m, "java/util/List.add(ILjava/lang/Object;)V", instance("add"));
        put(m, "java/util/List.remove(I)Ljava/lang/Object;", instance("remove"));
        put(m, "java/util/List.indexOf(Ljava/lang/Object;)I", instance("indexOf"));
        put(m, "java/util/Map.put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", instance("put"));
        put(m, "java/util/Map.get(Ljava/lang/Object;)Ljava/lang/Object;", instance("get"));
        put(m, "java/util/Map.containsKey(Ljava/lang/Object;)Z", instance("containsKey"));
        put(m, "java/util/Map.remove(Ljava/lang/Object;)Ljava/lang/Object;", instance("remove"));
        put(m, "java/util/Map.size()I", instance("size"));
        put(m, "java/util/Map.isEmpty()Z", instance("isEmpty"));
        put(m, "java/util/Map.keySet()Ljava/util/Set;", instance("keySet"));
        put(m, "java/util/Map.values()Ljava/util/Collection;", instance("values"));
        put(m, "java/util/Map.entrySet()Ljava/util/Set;", instance("entrySet"));
        put(m, "java/util/Map.clear()V", instance("clear"));
        put(m, "java/util/HashMap.clear()V", instance("clear"));
        put(m, "java/util/Map.getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", instance("getOrDefault"));
        put(m, "java/util/Map.putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", instance("putIfAbsent"));
        put(m, "java/util/Map.computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;", instance("computeIfAbsent"));
        put(m, "java/util/Map.computeIfPresent(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", instance("computeIfPresent"));
        put(m, "java/util/Map.compute(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", instance("compute"));
        put(m, "java/util/Map.merge(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", instance("merge"));
        put(m, "java/util/Map.replace(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", instance("replace"));
        put(m, "java/util/Map.forEach(Ljava/util/function/BiConsumer;)V", instance("forEach"));
        put(m, "java/util/Map$Entry.getKey()Ljava/lang/Object;", instance("getKey"));
        put(m, "java/util/Map$Entry.getValue()Ljava/lang/Object;", instance("getValue"));
    }

    private static void addAwtMethods2(Map<String, ShimTarget> m) {
        put(m, "java/awt/Window.setVisible(Z)V", instance("setVisible"));
        put(m, "java/awt/Window.dispose()V", instance("dispose"));
        put(m, "java/awt/Window.setSize(II)V", instance("setSize"));
        put(m, "java/awt/Window.setResizable(Z)V", instance("setResizable"));
        put(m, "java/awt/Window.setLocationRelativeTo(Ljava/awt/Component;)V", instance("setLocationRelativeTo"));
        put(m, "java/awt/Window.setDefaultCloseOperation(I)V", instance("setDefaultCloseOperation"));
        put(m, "java/awt/Window.getParent()Ljava/awt/Container;", instance("getParent"));
    }

    private static void addSwingMethods2(Map<String, ShimTarget> m) {
        put(m, "javax/swing/SwingUtilities.invokeLater(Ljava/lang/Runnable;)V", statics("invokeLater"));
    }

    private static void addAwtMethods3(Map<String, ShimTarget> m) {
        put(m, "java/awt/Window.getContentPane()Ljava/awt/Container;", instance("getContentPane"));
        put(m, "java/awt/Window.setContentPane(Ljava/awt/Container;)V", instance("setContentPane"));
        put(m, "java/awt/Window.setTitle(Ljava/lang/String;)V", instance("setTitle"));
        put(m, "java/awt/Window.setJMenuBar(Ljavax/swing/JMenuBar;)V", instance("setJMenuBar"));
        put(m, "java/awt/Window.pack()V", instance("pack"));
        put(m, "java/awt/Window.toFront()V", instance("toFront"));
        put(m, "java/awt/Window.setMinimumSize(Ljava/awt/Dimension;)V", instance("setMinimumSize"));
        put(m, "java/awt/Window.addWindowListener(Ljava/awt/event/WindowListener;)V", instance("addWindowListener"));
        put(m, "java/awt/Component.setBackground(Ljava/awt/Color;)V", instance("setBackground"));
        put(m, "java/awt/Component.setForeground(Ljava/awt/Color;)V", instance("setForeground"));
        put(m, "java/awt/Component.setFont(Ljava/awt/Font;)V", instance("setFont"));
        put(m, "java/awt/Component.setOpaque(Z)V", instance("setOpaque"));
        put(m, "java/awt/Component.setDoubleBuffered(Z)V", instance("setDoubleBuffered"));
        put(m, "java/awt/Component.registerKeyboardAction(Ljava/awt/event/ActionListener;Ljavax/swing/KeyStroke;I)V", instance("registerKeyboardAction"));
        put(m, "java/awt/Component.putClientProperty(Ljava/lang/Object;Ljava/lang/Object;)V", instance("putClientProperty"));
        put(m, "java/awt/Component.setBorderPainted(Z)V", instance("setBorderPainted"));
        put(m, "java/awt/Component.setFocusPainted(Z)V", instance("setFocusPainted"));
        put(m, "java/awt/Component.setContentAreaFilled(Z)V", instance("setContentAreaFilled"));
        put(m, "java/awt/Component.setFocusable(Z)V", instance("setFocusable"));
        put(m, "java/awt/Component.setToolTipText(Ljava/lang/String;)V", instance("setToolTipText"));
        put(m, "java/awt/Component.repaint()V", instance("repaint"));
        put(m, "java/awt/Component.revalidate()V", instance("revalidate"));
        put(m, "java/awt/Component.addKeyListener(Ljava/awt/event/KeyListener;)V", instance("addKeyListener"));
        put(m, "java/awt/Component.addMouseListener(Ljava/awt/event/MouseListener;)V", instance("addMouseListener"));
        put(m, "java/awt/Component.addMouseMotionListener(Ljava/awt/event/MouseMotionListener;)V", instance("addMouseMotionListener"));
    }

    private static void addSwingMethods3(Map<String, ShimTarget> m) {
        put(m, "javax/swing/Timer.addActionListener(Ljava/awt/event/ActionListener;)V", instance("addActionListener"));
        put(m, "javax/swing/Timer.setDelay(I)V", instance("setDelay"));
        put(m, "javax/swing/Timer.setRepeats(Z)V", instance("setRepeats"));
        put(m, "javax/swing/Timer.isRunning()Z", instance("isRunning"));
        put(m, "javax/swing/Timer.start()V", instance("start"));
        put(m, "javax/swing/Timer.stop()V", instance("stop"));
        put(m, "javax/swing/Timer.restart()V", instance("restart"));
        put(m, "javax/swing/KeyStroke.getKeyStroke(II)Ljavax/swing/KeyStroke;", statics("getKeyStroke"));
        put(m, "javax/swing/KeyStroke.getKeyStroke(Ljava/lang/String;)Ljavax/swing/KeyStroke;", statics("getKeyStroke"));
        put(m, "javax/swing/KeyStroke.getKeyStroke(C)Ljavax/swing/KeyStroke;", statics("getKeyStroke"));
        put(m, "javax/swing/JMenuItem.setText(Ljava/lang/String;)V", instance("setText"));
        put(m, "javax/swing/JMenuItem.addActionListener(Ljava/awt/event/ActionListener;)V", instance("addActionListener"));
        put(m, "javax/swing/JMenuItem.setAccelerator(Ljavax/swing/KeyStroke;)V", instance("setAccelerator"));
        put(m, "javax/swing/JMenuItem.setMnemonic(I)V", instance("setMnemonic"));
        put(m, "javax/swing/JMenuItem.setActionCommand(Ljava/lang/String;)V", instance("setActionCommand"));
        put(m, "javax/swing/JMenuItem.setToolTipText(Ljava/lang/String;)V", instance("setToolTipText"));
        put(m, "javax/swing/JMenu.add(Ljavax/swing/JMenuItem;)Ljavax/swing/JMenuItem;", instance("add"));
        put(m, "javax/swing/JMenu.add(Ljava/lang/String;)Ljavax/swing/JMenuItem;", instance("add"));
        put(m, "javax/swing/JMenu.addSeparator()V", instance("addSeparator"));
        put(m, "javax/swing/JToolBar.addSeparator()V", instance("addSeparator"));
        put(m, "javax/swing/JToolBar.setFloatable(Z)V", instance("setFloatable"));
        put(m, "javax/swing/JToolBar.setRollover(Z)V", instance("setRollover"));
        put(m, "javax/swing/JToolBar.setBorderPainted(Z)V", instance("setBorderPainted"));
        put(m, "javax/swing/JMenuBar.add(Ljavax/swing/JMenu;)Ljavax/swing/JMenu;", instance("add"));
        put(m, "javax/swing/JPopupMenu.add(Ljavax/swing/JMenuItem;)Ljavax/swing/JMenuItem;", instance("add"));
        put(m, "javax/swing/JPopupMenu.addSeparator()V", instance("addSeparator"));
        put(m, "javax/swing/JPopupMenu.show(Ljava/awt/Component;II)V", instance("show"));
        put(m, "javax/swing/JCheckBoxMenuItem.isSelected()Z", instance("isSelected"));
        put(m, "javax/swing/JCheckBoxMenuItem.setSelected(Z)V", instance("setSelected"));
    }

    private static void addAwtMethods4(Map<String, ShimTarget> m) {
        put(m, "java/awt/event/MouseEvent.getX()I", instance("getX"));
        put(m, "java/awt/event/MouseEvent.getY()I", instance("getY"));
        put(m, "java/awt/event/MouseEvent.getButton()I", instance("getButton"));
        put(m, "java/awt/event/MouseEvent.getClickCount()I", instance("getClickCount"));
        put(m, "java/awt/event/MouseEvent.getPoint()Ljava/awt/Point;", instance("getPoint"));
        put(m, "java/awt/event/MouseEvent.isShiftDown()Z", instance("isShiftDown"));
        put(m, "java/awt/event/MouseEvent.isControlDown()Z", instance("isControlDown"));
        put(m, "java/awt/event/MouseEvent.isAltDown()Z", instance("isAltDown"));
        put(m, "java/awt/event/MouseEvent.isMetaDown()Z", instance("isMetaDown"));
        put(m, "java/awt/Point.getX()D", instance("getX"));
        put(m, "java/awt/Point.getY()D", instance("getY"));
        put(m, "java/awt/geom/Point2D.distance(Ljava/awt/geom/Point2D;)D", instance("distance"));
        put(m, "java/awt/geom/Point2D.distance(DD)D", instance("distance"));
        put(m, "java/awt/geom/Point2D.getX()D", instance("getX"));
        put(m, "java/awt/geom/Point2D.getY()D", instance("getY"));
        put(m, "java/awt/Graphics.create()Ljava/awt/Graphics;", instance("create"));
        put(m, "java/awt/Graphics.dispose()V", instance("dispose"));
        put(m, "java/awt/Graphics.setColor(Ljava/awt/Color;)V", instance("setColor"));
        put(m, "java/awt/Graphics.setFont(Ljava/awt/Font;)V", instance("setFont"));
        put(m, "java/awt/Graphics.fillRect(IIII)V", instance("fillRect"));
        put(m, "java/awt/Graphics.drawRect(IIII)V", instance("drawRect"));
        put(m, "java/awt/Graphics.fillOval(IIII)V", instance("fillOval"));
        put(m, "java/awt/Graphics.drawOval(IIII)V", instance("drawOval"));
        put(m, "java/awt/Graphics.drawLine(IIII)V", instance("drawLine"));
        put(m, "java/awt/Graphics.drawString(Ljava/lang/String;II)V", instance("drawString"));
        put(m, "java/awt/Graphics2D.setRenderingHint(Ljava/awt/RenderingHints$Key;Ljava/lang/Object;)V", instance("setRenderingHint"));
        put(m, "java/awt/Graphics2D.translate(II)V", instance("translate"));
        put(m, "java/awt/Graphics2D.translate(DD)V", instance("translate"));
        put(m, "java/awt/Graphics2D.scale(DD)V", instance("scale"));
        put(m, "java/awt/Graphics2D.rotate(D)V", instance("rotate"));
        put(m, "java/awt/Graphics2D.getTransform()Ljava/awt/geom/AffineTransform;", instance("getTransform"));
        put(m, "java/awt/Graphics2D.setTransform(Ljava/awt/geom/AffineTransform;)V", instance("setTransform"));
    }

    private static void addSwingMethods4(Map<String, ShimTarget> m) {
        put(m, "javax/swing/JPanel.paintComponent(Ljava/awt/Graphics;)V", instance("paintComponent"));
        put(m, "javax/swing/SwingWorker.doInBackground()Ljava/lang/Object;", instance("doInBackground"));
        put(m, "javax/swing/SwingWorker.process(Ljava/util/List;)V", instance("process"));
        put(m, "javax/swing/SwingWorker.done()V", instance("done"));
        put(m, "javax/swing/SwingWorker.publish([Ljava/lang/Object;)V", instance("publish"));
        put(m, "javax/swing/SwingWorker.execute()V", instance("execute"));
        put(m, "javax/swing/SwingWorker.get()Ljava/lang/Object;", instance("get"));
        put(m, "javax/swing/SwingWorker.isDone()Z", instance("isDone"));
        put(m, "javax/swing/SwingWorker.cancel(Z)Z", instance("cancel"));
    }

    private static void addAwtMethods5(Map<String, ShimTarget> m) {
        put(m, "java/awt/KeyboardFocusManager.getCurrentKeyboardFocusManager()Ljava/awt/KeyboardFocusManager;", statics("getCurrentKeyboardFocusManager"));
        put(m, "java/awt/KeyboardFocusManager.addKeyEventDispatcher(Ljava/awt/KeyEventDispatcher;)V", instance("addKeyEventDispatcher"));
        put(m, "java/awt/KeyboardFocusManager.removeKeyEventDispatcher(Ljava/awt/KeyEventDispatcher;)V", instance("removeKeyEventDispatcher"));
        put(m, "java/awt/KeyEventDispatcher.dispatchKeyEvent(Ljava/awt/event/KeyEvent;)Z", instance("dispatchKeyEvent"));
        put(m, "java/awt/Container.setLayout(Ljava/awt/LayoutManager;)V", instance("setLayout"));
        put(m, "java/awt/Container.add(Ljava/awt/Component;)Ljava/awt/Component;", instance("add"));
        put(m, "java/awt/Container.add(Ljava/awt/Component;Ljava/lang/Object;)V", instance("add"));
        put(m, "java/awt/Component.setEnabled(Z)V", instance("setEnabled"));
        put(m, "java/awt/Component.setVisible(Z)V", instance("setVisible"));
        put(m, "java/awt/Component.setPreferredSize(Ljava/awt/Dimension;)V", instance("setPreferredSize"));
        put(m, "java/awt/Component.setBounds(IIII)V", instance("setBounds"));
        put(m, "java/awt/Component.requestFocusInWindow()Z", instance("requestFocusInWindow"));
    }

    private static void addLangMethods12(Map<String, ShimTarget> m) {
        put(m, "java/lang/Thread.sleep(J)V", statics("sleep"));
    }

    private static void addSwingMethods5(Map<String, ShimTarget> m) {
        put(m, "javax/swing/JButton.addActionListener(Ljava/awt/event/ActionListener;)V", instance("addActionListener"));
        put(m, "javax/swing/JLabel.setText(Ljava/lang/String;)V", instance("setText"));
        put(m, "javax/swing/JLabel.setHorizontalAlignment(I)V", instance("setHorizontalAlignment"));
        put(m, "javax/swing/JSlider.getValue()I", instance("getValue"));
        put(m, "javax/swing/JSlider.setValue(I)V", instance("setValue"));
        put(m, "javax/swing/JSlider.getMinimum()I", instance("getMinimum"));
        put(m, "javax/swing/JSlider.getMaximum()I", instance("getMaximum"));
        put(m, "javax/swing/JSlider.setMinimum(I)V", instance("setMinimum"));
        put(m, "javax/swing/JSlider.setMaximum(I)V", instance("setMaximum"));
        put(m, "javax/swing/JSlider.setMajorTickSpacing(I)V", instance("setMajorTickSpacing"));
        put(m, "javax/swing/JSlider.setMinorTickSpacing(I)V", instance("setMinorTickSpacing"));
        put(m, "javax/swing/JSlider.setPaintTicks(Z)V", instance("setPaintTicks"));
        put(m, "javax/swing/JSlider.setPaintLabels(Z)V", instance("setPaintLabels"));
        put(m, "javax/swing/JSlider.addChangeListener(Ljavax/swing/event/ChangeListener;)V", instance("addChangeListener"));
        put(m, "javax/swing/JCheckBox.isSelected()Z", instance("isSelected"));
        put(m, "javax/swing/JCheckBox.setSelected(Z)V", instance("setSelected"));
        put(m, "javax/swing/JCheckBox.setText(Ljava/lang/String;)V", instance("setText"));
        put(m, "javax/swing/JCheckBox.addActionListener(Ljava/awt/event/ActionListener;)V", instance("addActionListener"));
        put(m, "javax/swing/JCheckBox.setToolTipText(Ljava/lang/String;)V", instance("setToolTipText"));
        put(m, "javax/swing/JSpinner.getValue()Ljava/lang/Object;", instance("getValue"));
        put(m, "javax/swing/JSpinner.setValue(Ljava/lang/Object;)V", instance("setValue"));
        put(m, "javax/swing/JSpinner.addChangeListener(Ljavax/swing/event/ChangeListener;)V", instance("addChangeListener"));
        put(m, "javax/swing/event/ChangeListener.stateChanged(Ljavax/swing/event/ChangeEvent;)V", instance("stateChanged"));
        put(m, "javax/swing/JTextField.getText()Ljava/lang/String;", instance("getText"));
        put(m, "javax/swing/JTextField.setText(Ljava/lang/String;)V", instance("setText"));
        put(m, "javax/swing/JList.getSelectedIndex()I", instance("getSelectedIndex"));
        put(m, "javax/swing/DefaultListModel.addElement(Ljava/lang/Object;)V", instance("addElement"));
        put(m, "javax/swing/DefaultListModel.clear()V", instance("clear"));
    }

    private static void addAwtMethods6(Map<String, ShimTarget> m) {
        put(m, "java/awt/event/ActionListener.actionPerformed(Ljava/awt/event/ActionEvent;)V", instance("actionPerformed"));
    }

    private static void addSwingMethods6(Map<String, ShimTarget> m) {
        put(m, "javax/swing/JPasswordField.addKeyListener(Ljava/awt/event/KeyListener;)V", instance("addKeyListener"));
        put(m, "javax/swing/JPasswordField.getPassword()[C", instance("getPassword"));
        put(m, "javax/swing/JPasswordField.setEchoChar(C)V", instance("setEchoChar"));
        put(m, "javax/swing/JProgressBar.setIndeterminate(Z)V", instance("setIndeterminate"));
        put(m, "javax/swing/JProgressBar.setValue(I)V", instance("setValue"));
        put(m, "javax/swing/JProgressBar.getValue()I", instance("getValue"));
        put(m, "javax/swing/JProgressBar.setMinimum(I)V", instance("setMinimum"));
        put(m, "javax/swing/JProgressBar.setMaximum(I)V", instance("setMaximum"));
        put(m, "javax/swing/JProgressBar.setStringPainted(Z)V", instance("setStringPainted"));
        put(m, "javax/swing/JProgressBar.setString(Ljava/lang/String;)V", instance("setString"));
        put(m, "javax/swing/Box.createVerticalStrut(I)Ljava/awt/Component;", statics("createVerticalStrut"));
        put(m, "javax/swing/Box.createHorizontalStrut(I)Ljava/awt/Component;", statics("createHorizontalStrut"));
        put(m, "javax/swing/Box.createVerticalGlue()Ljava/awt/Component;", statics("createVerticalGlue"));
        put(m, "javax/swing/Box.createHorizontalGlue()Ljava/awt/Component;", statics("createHorizontalGlue"));
        put(m, "javax/swing/Box.createGlue()Ljava/awt/Component;", statics("createGlue"));
        put(m, "javax/swing/Box.createRigidArea(Ljava/awt/Dimension;)Ljava/awt/Component;", statics("createRigidArea"));
        put(m, "javax/swing/BorderFactory.createEmptyBorder(IIII)Ljavax/swing/border/Border;", statics("createEmptyBorder"));
        put(m, "javax/swing/BorderFactory.createTitledBorder(Ljava/lang/String;)Ljavax/swing/border/TitledBorder;", statics("createTitledBorder"));
        put(m, "javax/swing/BorderFactory.createTitledBorder(Ljavax/swing/border/Border;Ljava/lang/String;)Ljavax/swing/border/TitledBorder;", statics("createTitledBorder"));
        put(m, "javax/swing/BorderFactory.createLineBorder(Ljava/awt/Color;I)Ljavax/swing/border/Border;", statics("createLineBorder"));
        put(m, "javax/swing/BorderFactory.createLineBorder(Ljava/awt/Color;)Ljavax/swing/border/Border;", statics("createLineBorder"));
        put(m, "javax/swing/BorderFactory.createEtchedBorder()Ljavax/swing/border/Border;", statics("createEtchedBorder"));
        put(m, "javax/swing/BorderFactory.createLoweredBevelBorder()Ljavax/swing/border/Border;", statics("createLoweredBevelBorder"));
        put(m, "javax/swing/BorderFactory.createRaisedBevelBorder()Ljavax/swing/border/Border;", statics("createRaisedBevelBorder"));
        put(m, "javax/swing/BorderFactory.createCompoundBorder(Ljavax/swing/border/Border;Ljavax/swing/border/Border;)Ljavax/swing/border/Border;", statics("createCompoundBorder"));
    }

    private static void addAwtMethods7(Map<String, ShimTarget> m) {
        put(m, "java/awt/Container.setBorder(Ljavax/swing/border/Border;)V", instance("setBorder"));
    }

    private static void addSwingMethods7(Map<String, ShimTarget> m) {
        put(m, "javax/swing/JLabel.setForeground(Ljava/awt/Color;)V", instance("setForeground"));
        put(m, "javax/swing/JLabel.setFont(Ljava/awt/Font;)V", instance("setFont"));
        put(m, "javax/swing/JOptionPane.showMessageDialog(Ljava/awt/Component;Ljava/lang/Object;Ljava/lang/String;I)V", statics("showMessageDialog"));
        put(m, "javax/swing/JOptionPane.showConfirmDialog(Ljava/awt/Component;Ljava/lang/Object;Ljava/lang/String;I)I", statics("showConfirmDialog"));
        put(m, "javax/swing/JOptionPane.showConfirmDialog(Ljava/awt/Component;Ljava/lang/Object;)I", statics("showConfirmDialog"));
    }

    private static void addUtilMethods8(Map<String, ShimTarget> m) {
        put(m, "java/util/Arrays.fill([CC)V", statics("fill"));
    }

    private static void addAwtMethods8(Map<String, ShimTarget> m) {
        put(m, "java/awt/event/KeyEvent.getKeyCode()I", instance("getKeyCode"));
        put(m, "java/awt/event/KeyEvent.getID()I", instance("getID"));
        put(m, "java/awt/event/KeyEvent.getKeyChar()C", instance("getKeyChar"));
        put(m, "java/awt/event/KeyEvent.isShiftDown()Z", instance("isShiftDown"));
        put(m, "java/awt/event/KeyEvent.isControlDown()Z", instance("isControlDown"));
        put(m, "java/awt/event/KeyEvent.isAltDown()Z", instance("isAltDown"));
        put(m, "java/awt/event/KeyEvent.isMetaDown()Z", instance("isMetaDown"));
        put(m, "java/awt/event/ActionEvent.getSource()Ljava/lang/Object;", instance("getSource"));
        put(m, "java/awt/event/ActionEvent.getActionCommand()Ljava/lang/String;", instance("getActionCommand"));
    }

    private static void addLangMethods13(Map<String, ShimTarget> m) {
        put(m, "java/lang/System.getProperty(Ljava/lang/String;)Ljava/lang/String;", statics("getProperty"));
        put(m, "java/lang/System.getProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", statics("getProperty"));
        put(m, "java/lang/System.setProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", statics("setProperty"));
    }

    private static void addAwtMethods9(Map<String, ShimTarget> m) {
        put(m, "java/awt/CardLayout.show(Ljava/awt/Container;Ljava/lang/String;)V", instance("show"));
        put(m, "java/awt/CardLayout.first(Ljava/awt/Container;)V", instance("first"));
        put(m, "java/awt/CardLayout.last(Ljava/awt/Container;)V", instance("last"));
        put(m, "java/awt/CardLayout.next(Ljava/awt/Container;)V", instance("next"));
        put(m, "java/awt/CardLayout.previous(Ljava/awt/Container;)V", instance("previous"));
    }

        private static final Map<String, ShimTarget> FIELDS = buildFields();

    private static Map<String, ShimTarget> buildFields() {
        Map<String, ShimTarget> m = new java.util.HashMap<>(256);
        addLangFields0(m);
        addConcurrentFields0(m);
        addTimeFields0(m);
        addNioFields0(m);
        addLangFields1(m);
        addAwtFields0(m);
        addRegexFields0(m);
        addAwtFields1(m);
        addSwingFields0(m);
        addAwtFields2(m);
        return Map.copyOf(m);
    }

    private static void addLangFields0(Map<String, ShimTarget> m) {
        put(m, "java/lang/System.out Ljava/io/PrintStream;", statics("@out"));
        put(m, "java/lang/Integer.TYPE Ljava/lang/Class;", statics("TYPE"));
        put(m, "java/lang/Long.TYPE Ljava/lang/Class;", statics("TYPE"));
        put(m, "java/lang/Double.TYPE Ljava/lang/Class;", statics("TYPE"));
        put(m, "java/lang/Float.TYPE Ljava/lang/Class;", statics("TYPE"));
        put(m, "java/lang/Short.TYPE Ljava/lang/Class;", statics("TYPE"));
        put(m, "java/lang/Byte.TYPE Ljava/lang/Class;", statics("TYPE"));
        put(m, "java/lang/Character.TYPE Ljava/lang/Class;", statics("TYPE"));
        put(m, "java/lang/Boolean.TYPE Ljava/lang/Class;", statics("TYPE"));
        put(m, "java/lang/Void.TYPE Ljava/lang/Class;", statics("TYPE"));
        put(m, "java/lang/System.err Ljava/io/PrintStream;", statics("err"));
    }

    private static void addConcurrentFields0(Map<String, ShimTarget> m) {
        put(m, "java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;", statics("MILLISECONDS"));
        put(m, "java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;", statics("NANOSECONDS"));
        put(m, "java/util/concurrent/TimeUnit.SECONDS Ljava/util/concurrent/TimeUnit;", statics("SECONDS"));
        put(m, "java/util/concurrent/TimeUnit.MICROSECONDS Ljava/util/concurrent/TimeUnit;", statics("MICROSECONDS"));
    }

    private static void addTimeFields0(Map<String, ShimTarget> m) {
        put(m, "java/time/temporal/ChronoUnit.MINUTES Ljava/time/temporal/ChronoUnit;", statics("MINUTES"));
    }

    private static void addNioFields0(Map<String, ShimTarget> m) {
        put(m, "java/nio/charset/StandardCharsets.UTF_8 Ljava/nio/charset/Charset;", statics("UTF_8"));
    }

    private static void addLangFields1(Map<String, ShimTarget> m) {
        put(m, "java/lang/Boolean.TRUE Ljava/lang/Boolean;", statics("TRUE"));
        put(m, "java/lang/Boolean.FALSE Ljava/lang/Boolean;", statics("FALSE"));
        put(m, "java/lang/Integer.MAX_VALUE I", statics("MAX_VALUE"));
        put(m, "java/lang/Integer.MIN_VALUE I", statics("MIN_VALUE"));
        put(m, "java/lang/Integer.SIZE I", statics("SIZE"));
        put(m, "java/lang/Integer.BYTES I", statics("BYTES"));
        put(m, "java/lang/Long.MAX_VALUE J", statics("MAX_VALUE"));
        put(m, "java/lang/Long.MIN_VALUE J", statics("MIN_VALUE"));
        put(m, "java/lang/Long.SIZE I", statics("SIZE"));
        put(m, "java/lang/Long.BYTES I", statics("BYTES"));
        put(m, "java/lang/Short.MAX_VALUE S", statics("MAX_VALUE"));
        put(m, "java/lang/Short.MIN_VALUE S", statics("MIN_VALUE"));
        put(m, "java/lang/Byte.MAX_VALUE B", statics("MAX_VALUE"));
        put(m, "java/lang/Byte.MIN_VALUE B", statics("MIN_VALUE"));
        put(m, "java/lang/Character.MAX_VALUE C", statics("MAX_VALUE"));
        put(m, "java/lang/Character.MIN_VALUE C", statics("MIN_VALUE"));
        put(m, "java/lang/Double.MAX_VALUE D", statics("MAX_VALUE"));
        put(m, "java/lang/Double.MIN_VALUE D", statics("MIN_VALUE"));
        put(m, "java/lang/Double.POSITIVE_INFINITY D", statics("POSITIVE_INFINITY"));
        put(m, "java/lang/Double.NEGATIVE_INFINITY D", statics("NEGATIVE_INFINITY"));
        put(m, "java/lang/Double.NaN D", statics("NaN"));
        put(m, "java/lang/Float.MAX_VALUE F", statics("MAX_VALUE"));
        put(m, "java/lang/Float.MIN_VALUE F", statics("MIN_VALUE"));
        put(m, "java/lang/Math.PI D", statics("PI"));
        put(m, "java/lang/Math.E D", statics("E"));
    }

    private static void addAwtFields0(Map<String, ShimTarget> m) {
        put(m, "java/awt/event/KeyEvent.KEY_TYPED I", statics("KEY_TYPED"));
        put(m, "java/awt/event/KeyEvent.KEY_PRESSED I", statics("KEY_PRESSED"));
        put(m, "java/awt/event/KeyEvent.KEY_RELEASED I", statics("KEY_RELEASED"));
    }

    private static void addRegexFields0(Map<String, ShimTarget> m) {
        put(m, "java/util/regex/Pattern.CASE_INSENSITIVE I", statics("CASE_INSENSITIVE"));
        put(m, "java/util/regex/Pattern.MULTILINE I", statics("MULTILINE"));
        put(m, "java/util/regex/Pattern.DOTALL I", statics("DOTALL"));
        put(m, "java/util/regex/Pattern.COMMENTS I", statics("COMMENTS"));
        put(m, "java/util/regex/Pattern.UNIX_LINES I", statics("UNIX_LINES"));
        put(m, "java/util/regex/Pattern.LITERAL I", statics("LITERAL"));
        put(m, "java/util/regex/Pattern.UNICODE_CASE I", statics("UNICODE_CASE"));
    }

    private static void addAwtFields1(Map<String, ShimTarget> m) {
        put(m, "java/awt/Point.x I", instance("x"));
        put(m, "java/awt/Point.y I", instance("y"));
        put(m, "java/awt/RenderingHints.KEY_ANTIALIASING Ljava/awt/RenderingHints$Key;", statics("KEY_ANTIALIASING"));
        put(m, "java/awt/RenderingHints.KEY_RENDERING Ljava/awt/RenderingHints$Key;", statics("KEY_RENDERING"));
        put(m, "java/awt/RenderingHints.KEY_TEXT_ANTIALIASING Ljava/awt/RenderingHints$Key;", statics("KEY_TEXT_ANTIALIASING"));
        put(m, "java/awt/RenderingHints.VALUE_ANTIALIAS_ON Ljava/lang/Object;", statics("VALUE_ANTIALIAS_ON"));
        put(m, "java/awt/RenderingHints.VALUE_ANTIALIAS_OFF Ljava/lang/Object;", statics("VALUE_ANTIALIAS_OFF"));
        put(m, "java/awt/RenderingHints.VALUE_RENDER_QUALITY Ljava/lang/Object;", statics("VALUE_RENDER_QUALITY"));
        put(m, "java/awt/RenderingHints.VALUE_RENDER_SPEED Ljava/lang/Object;", statics("VALUE_RENDER_SPEED"));
        put(m, "java/awt/RenderingHints.VALUE_TEXT_ANTIALIAS_ON Ljava/lang/Object;", statics("VALUE_TEXT_ANTIALIAS_ON"));
        put(m, "java/awt/BorderLayout.CENTER Ljava/lang/String;", statics("CENTER"));
        put(m, "java/awt/BorderLayout.NORTH Ljava/lang/String;", statics("NORTH"));
        put(m, "java/awt/BorderLayout.SOUTH Ljava/lang/String;", statics("SOUTH"));
        put(m, "java/awt/BorderLayout.EAST Ljava/lang/String;", statics("EAST"));
        put(m, "java/awt/BorderLayout.WEST Ljava/lang/String;", statics("WEST"));
        put(m, "java/awt/BorderLayout.PAGE_START Ljava/lang/String;", statics("PAGE_START"));
        put(m, "java/awt/BorderLayout.PAGE_END Ljava/lang/String;", statics("PAGE_END"));
        put(m, "java/awt/BorderLayout.LINE_START Ljava/lang/String;", statics("LINE_START"));
        put(m, "java/awt/BorderLayout.LINE_END Ljava/lang/String;", statics("LINE_END"));
        put(m, "java/awt/Font.MONOSPACED Ljava/lang/String;", statics("MONOSPACED"));
        put(m, "java/awt/Font.SANS_SERIF Ljava/lang/String;", statics("SANS_SERIF"));
        put(m, "java/awt/Font.SERIF Ljava/lang/String;", statics("SERIF"));
        put(m, "java/awt/Font.DIALOG Ljava/lang/String;", statics("DIALOG"));
    }

    private static void addSwingFields0(Map<String, ShimTarget> m) {
        put(m, "javax/swing/JOptionPane.YES_OPTION I", statics("YES_OPTION"));
        put(m, "javax/swing/JOptionPane.OK_OPTION I", statics("OK_OPTION"));
        put(m, "javax/swing/JOptionPane.NO_OPTION I", statics("NO_OPTION"));
        put(m, "javax/swing/JOptionPane.CANCEL_OPTION I", statics("CANCEL_OPTION"));
        put(m, "javax/swing/JOptionPane.YES_NO_OPTION I", statics("YES_NO_OPTION"));
        put(m, "javax/swing/JOptionPane.YES_NO_CANCEL_OPTION I", statics("YES_NO_CANCEL_OPTION"));
        put(m, "javax/swing/JOptionPane.OK_CANCEL_OPTION I", statics("OK_CANCEL_OPTION"));
        put(m, "javax/swing/JOptionPane.ERROR_MESSAGE I", statics("ERROR_MESSAGE"));
        put(m, "javax/swing/JOptionPane.INFORMATION_MESSAGE I", statics("INFORMATION_MESSAGE"));
        put(m, "javax/swing/JOptionPane.WARNING_MESSAGE I", statics("WARNING_MESSAGE"));
        put(m, "javax/swing/JOptionPane.QUESTION_MESSAGE I", statics("QUESTION_MESSAGE"));
        put(m, "javax/swing/JOptionPane.PLAIN_MESSAGE I", statics("PLAIN_MESSAGE"));
        put(m, "javax/swing/JFileChooser.APPROVE_OPTION I", statics("APPROVE_OPTION"));
        put(m, "javax/swing/JFileChooser.CANCEL_OPTION I", statics("CANCEL_OPTION"));
        put(m, "javax/swing/JFileChooser.ERROR_OPTION I", statics("ERROR_OPTION"));
    }

    private static void addAwtFields2(Map<String, ShimTarget> m) {
        put(m, "java/awt/Font.PLAIN I", statics("PLAIN"));
        put(m, "java/awt/Font.BOLD I", statics("BOLD"));
        put(m, "java/awt/Font.ITALIC I", statics("ITALIC"));
        put(m, "java/awt/Color.RED Ljava/awt/Color;", statics("RED"));
        put(m, "java/awt/Color.ORANGE Ljava/awt/Color;", statics("ORANGE"));
        put(m, "java/awt/Color.DARK_GRAY Ljava/awt/Color;", statics("DARK_GRAY"));
        put(m, "java/awt/Color.BLACK Ljava/awt/Color;", statics("BLACK"));
        put(m, "java/awt/Color.WHITE Ljava/awt/Color;", statics("WHITE"));
        put(m, "java/awt/Color.GRAY Ljava/awt/Color;", statics("GRAY"));
        put(m, "java/awt/Color.LIGHT_GRAY Ljava/awt/Color;", statics("LIGHT_GRAY"));
        put(m, "java/awt/Color.YELLOW Ljava/awt/Color;", statics("YELLOW"));
        put(m, "java/awt/Color.GREEN Ljava/awt/Color;", statics("GREEN"));
        put(m, "java/awt/Color.BLUE Ljava/awt/Color;", statics("BLUE"));
        put(m, "java/awt/Color.CYAN Ljava/awt/Color;", statics("CYAN"));
        put(m, "java/awt/Color.MAGENTA Ljava/awt/Color;", statics("MAGENTA"));
        put(m, "java/awt/Color.PINK Ljava/awt/Color;", statics("PINK"));
        put(m, "java/awt/GridBagConstraints.gridx I", instance("gridx"));
        put(m, "java/awt/GridBagConstraints.gridy I", instance("gridy"));
        put(m, "java/awt/GridBagConstraints.gridwidth I", instance("gridwidth"));
        put(m, "java/awt/GridBagConstraints.gridheight I", instance("gridheight"));
        put(m, "java/awt/GridBagConstraints.weightx D", instance("weightx"));
        put(m, "java/awt/GridBagConstraints.weighty D", instance("weighty"));
        put(m, "java/awt/GridBagConstraints.anchor I", instance("anchor"));
        put(m, "java/awt/GridBagConstraints.fill I", instance("fill"));
        put(m, "java/awt/GridBagConstraints.ipadx I", instance("ipadx"));
        put(m, "java/awt/GridBagConstraints.ipady I", instance("ipady"));
        put(m, "java/awt/GridBagConstraints.insets Ljava/awt/Insets;", instance("insets"));
    }

    private ShimRegistry() {
    }

    public static boolean isShimType(String internalName) {
        return TYPES.contains(internalName);
    }

    public static Set<String> types() {
        return TYPES;
    }

    public static boolean isExtendable(String internalName) {
        return EXTENDABLE.contains(internalName);
    }

    public static boolean isThrowableSubtype(String internalName) {
        String current = internalName;
        while (current != null) {
            if (current.equals("java/lang/Throwable")) {
                return true;
            }
            current = SHIM_SUPERS.get(current);
        }
        return false;
    }

    public static Optional<WalkResult> resolveMethodWalking(String owner, String name, String descriptor) {
        String current = owner;
        while (current != null) {
            Optional<ShimTarget> target = method(current, name, descriptor);
            if (target.isPresent()) {
                return Optional.of(new WalkResult(current, target.get()));
            }
            current = SHIM_SUPERS.get(current);
        }
        return Optional.empty();
    }

    /**
     * Instance shim methods that are C# default interface methods (they have a body in the shim
     * interface). C# only dispatches a default interface method through an interface-typed
     * reference, so a caller holding a concrete implementor (e.g. a synthetic lambda class) must
     * cast the receiver to the declaring interface first.
     */
    private static final Set<String> DEFAULT_INTERFACE_METHODS = Set.of(
            "java/util/function/Function.andThen(Ljava/util/function/Function;)Ljava/util/function/Function;",
            "java/util/function/Function.compose(Ljava/util/function/Function;)Ljava/util/function/Function;",
            "java/util/Collection.stream()Ljava/util/stream/Stream;",
            "java/util/Collection.parallelStream()Ljava/util/stream/Stream;",
            "java/util/Collection.forEach(Ljava/util/function/Consumer;)V",
            "java/util/Map.getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            "java/util/Map.putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            "java/util/Map.computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;",
            "java/util/Map.computeIfPresent(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;",
            "java/util/Map.compute(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;",
            "java/util/Map.merge(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;",
            "java/util/Map.replace(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            "java/util/Map.forEach(Ljava/util/function/BiConsumer;)V",
            "java/util/Comparator.reversed()Ljava/util/Comparator;",
            "java/util/Comparator.thenComparing(Ljava/util/Comparator;)Ljava/util/Comparator;",
            "java/util/Comparator.thenComparing(Ljava/util/function/Function;)Ljava/util/Comparator;",
            "java/util/Comparator.thenComparingInt(Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;",
            "java/util/List.sort(Ljava/util/Comparator;)V");

    public static boolean isDefaultInterfaceMethod(String ownerInternal, String name, String desc) {
        return DEFAULT_INTERFACE_METHODS.contains(ownerInternal + "." + name + desc);
    }

    public static Map<String, ShimTarget> methods() {
        return METHODS;
    }

    /**
     * The instance (non-static) methods a shim type declares, as (name+descriptor) → C# name.
     * A bootstrapped class implementing a shim interface adopts these so its overrides keep the
     * interface's member name and a colliding field yields instead of hijacking it.
     */
    public static Map<String, String> instanceVirtualsOf(String owner) {
        Map<String, String> result = new java.util.LinkedHashMap<>();
        String prefix = owner + ".";
        for (Map.Entry<String, ShimTarget> entry : METHODS.entrySet()) {
            if (entry.getKey().startsWith(prefix) && !entry.getValue().isStatic()) {
                result.put(entry.getKey().substring(prefix.length()), entry.getValue().csMemberName());
            }
        }
        return result;
    }

    public static Map<String, ShimTarget> fields() {
        return FIELDS;
    }

    public static Optional<ShimTarget> method(String owner, String name, String descriptor) {
        return Optional.ofNullable(METHODS.get(owner + "." + name + descriptor));
    }

    public static Optional<ShimTarget> field(String owner, String name, String descriptor) {
        return Optional.ofNullable(FIELDS.get(owner + "." + name + " " + descriptor));
    }

    private static ShimTarget instance(String csName) {
        return new ShimTarget(csName, false);
    }

    private static ShimTarget statics(String csName) {
        return new ShimTarget(csName, true);
    }
}
