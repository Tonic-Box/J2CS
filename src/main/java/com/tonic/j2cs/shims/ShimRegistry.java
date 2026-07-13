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
        put(m, "java/util/StringJoiner", "java/lang/Object");
        put(m, "java/util/concurrent/ThreadLocalRandom", "java/lang/Object");
        put(m, "java/util/Formatter", "java/lang/Object");
        put(m, "java/util/Currency", "java/lang/Object");
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray", "java/lang/Object");
        put(m, "java/util/concurrent/atomic/AtomicLongArray", "java/lang/Object");
        put(m, "java/util/concurrent/atomic/AtomicReferenceArray", "java/lang/Object");
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
        put(m, "java/lang/StrictMath", "java/lang/Object");
        put(m, "java/lang/Boolean", "java/lang/Object");
        put(m, "java/lang/Void", "java/lang/Object");
        put(m, "java/lang/Character", "java/lang/Object");
    }

    private static void addUtilSupers1(Map<String, String> m) {
        put(m, "java/lang/Iterable", "java/lang/Object");
        put(m, "java/util/Iterator", "java/lang/Object");
        put(m, "java/util/Enumeration", "java/lang/Object");
        put(m, "java/util/Collection", "java/lang/Iterable");
        put(m, "java/util/List", "java/util/Collection");
        put(m, "java/util/ArrayList", "java/util/List");
        put(m, "java/util/Vector", "java/util/ArrayList");
        put(m, "java/util/Stack", "java/util/Vector");
        put(m, "java/util/BitSet", "java/lang/Object");
        put(m, "java/util/Properties", "java/util/HashMap");
        put(m, "java/util/EnumSet", "java/util/Set");
        put(m, "java/util/EnumMap", "java/util/HashMap");
        put(m, "java/util/ListIterator", "java/util/Iterator");
        put(m, "java/util/Set", "java/util/Collection");
        put(m, "java/util/Map", "java/lang/Object");
        put(m, "java/util/Map$Entry", "java/lang/Object");
        put(m, "java/util/HashMap", "java/util/Map");
        put(m, "java/util/HashSet", "java/util/Set");
        put(m, "java/util/TreeSet", "java/util/NavigableSet");
        put(m, "java/util/SortedSet", "java/util/Set");
        put(m, "java/util/SortedMap", "java/util/Map");
        put(m, "java/util/NavigableSet", "java/util/SortedSet");
        put(m, "java/util/NavigableMap", "java/util/SortedMap");
        put(m, "java/util/DescendingTreeMap", "java/util/TreeMap");
        put(m, "java/lang/ThreadLocal", "java/lang/Object");
        put(m, "java/lang/Process", "java/lang/Object");
        put(m, "java/lang/ProcessBuilder", "java/lang/Object");
        put(m, "java/net/URI", "java/lang/Object");
        put(m, "java/net/URL", "java/lang/Object");
        put(m, "java/net/Socket", "java/lang/Object");
        put(m, "java/net/ServerSocket", "java/lang/Object");
        put(m, "java/net/InetAddress", "java/lang/Object");
        put(m, "java/net/URLConnection", "java/lang/Object");
        put(m, "java/net/HttpURLConnection", "java/net/URLConnection");
        put(m, "java/net/http/HttpClient", "java/lang/Object");
        put(m, "java/net/http/HttpClient$Builder", "java/lang/Object");
        put(m, "java/net/http/HttpRequest", "java/lang/Object");
        put(m, "java/net/http/HttpRequest$Builder", "java/lang/Object");
        put(m, "java/net/http/HttpRequest$BodyPublisher", "java/lang/Object");
        put(m, "java/net/http/HttpRequest$BodyPublishers", "java/lang/Object");
        put(m, "java/net/http/HttpResponse", "java/lang/Object");
        put(m, "java/net/http/HttpResponse$BodyHandler", "java/lang/Object");
        put(m, "java/net/http/HttpResponse$BodyHandlers", "java/lang/Object");
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
        put(m, "java/lang/AutoCloseable", "java/lang/Object");
        put(m, "java/io/Closeable", "java/lang/AutoCloseable");
        put(m, "java/util/function/UnaryOperator", "java/util/function/Function");
        put(m, "java/math/BigInteger", "java/lang/Object");
        put(m, "java/math/BigDecimal", "java/lang/Object");
        put(m, "java/math/RoundingMode", "java/lang/Object");
        put(m, "java/util/Scanner", "java/lang/Object");
        put(m, "java/util/StringTokenizer", "java/lang/Object");
        put(m, "java/text/DecimalFormat", "java/lang/Object");
        put(m, "java/time/Instant", "java/lang/Object");
        put(m, "java/time/LocalTime", "java/lang/Object");
        put(m, "java/time/Period", "java/lang/Object");
        put(m, "java/time/ZoneId", "java/lang/Object");
        put(m, "java/time/Month", "java/lang/Object");
        put(m, "java/time/Year", "java/lang/Object");
        put(m, "java/util/Locale", "java/lang/Object");
        put(m, "java/text/NumberFormat", "java/lang/Object");
        put(m, "java/text/MessageFormat", "java/lang/Object");
        put(m, "java/util/Date", "java/lang/Object");
        put(m, "java/util/TimeZone", "java/lang/Object");
        put(m, "java/util/Calendar", "java/lang/Object");
        put(m, "java/util/GregorianCalendar", "java/util/Calendar");
        put(m, "java/text/SimpleDateFormat", "java/lang/Object");
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
        put(m, "java/util/OptionalLong", "java/lang/Object");
        put(m, "java/util/function/LongConsumer", "java/lang/Object");
        put(m, "java/util/function/LongPredicate", "java/lang/Object");
        put(m, "java/util/function/LongUnaryOperator", "java/lang/Object");
        put(m, "java/util/function/LongBinaryOperator", "java/lang/Object");
        put(m, "java/util/function/LongFunction", "java/lang/Object");
        put(m, "java/util/function/LongToIntFunction", "java/lang/Object");
        put(m, "java/util/function/LongToDoubleFunction", "java/lang/Object");
        put(m, "java/util/function/DoubleUnaryOperator", "java/lang/Object");
        put(m, "java/util/function/DoubleBinaryOperator", "java/lang/Object");
        put(m, "java/util/function/DoubleFunction", "java/lang/Object");
        put(m, "java/util/function/DoubleToIntFunction", "java/lang/Object");
        put(m, "java/util/function/DoubleToLongFunction", "java/lang/Object");
        put(m, "java/util/function/IntToLongFunction", "java/lang/Object");
        put(m, "java/util/Random", "java/lang/Object");
    }

    private static void addRegexSupers0(Map<String, String> m) {
        put(m, "java/util/regex/Pattern", "java/lang/Object");
        put(m, "java/util/regex/Matcher", "java/lang/Object");
    }

    private static void addConcurrentSupers0(Map<String, String> m) {
        put(m, "java/util/concurrent/TimeUnit", "java/lang/Object");
        put(m, "java/util/concurrent/BlockingQueue", "java/util/Queue");
        put(m, "java/util/concurrent/LinkedBlockingQueue", "java/util/concurrent/BlockingQueue");
        put(m, "java/util/concurrent/ArrayBlockingQueue", "java/util/concurrent/BlockingQueue");
        put(m, "java/util/concurrent/ConcurrentLinkedQueue", "java/util/Queue");
        put(m, "java/util/concurrent/CountDownLatch", "java/lang/Object");
        put(m, "java/util/concurrent/Semaphore", "java/lang/Object");
        put(m, "java/util/concurrent/CyclicBarrier", "java/lang/Object");
        put(m, "java/util/concurrent/ConcurrentMap", "java/util/Map");
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
        put(m, "java/security/Key", "java/lang/Object");
        put(m, "javax/crypto/SecretKey", "java/security/Key");
        put(m, "javax/crypto/spec/SecretKeySpec", "javax/crypto/SecretKey");
        put(m, "javax/crypto/Mac", "java/lang/Object");
        put(m, "java/security/spec/AlgorithmParameterSpec", "java/lang/Object");
        put(m, "javax/crypto/spec/IvParameterSpec", "java/lang/Object");
        put(m, "javax/crypto/Cipher", "java/lang/Object");
        put(m, "java/security/PublicKey", "java/security/Key");
        put(m, "java/security/PrivateKey", "java/security/Key");
        put(m, "java/security/KeyPair", "java/lang/Object");
        put(m, "java/security/KeyPairGenerator", "java/lang/Object");
        put(m, "java/security/Signature", "java/lang/Object");
        put(m, "java/security/SecureRandom", "java/lang/Object");
    }

    private static void addUtilSupers4(Map<String, String> m) {
        put(m, "java/util/Base64", "java/lang/Object");
        put(m, "java/util/Base64$Encoder", "java/lang/Object");
        put(m, "java/util/Base64$Decoder", "java/lang/Object");
    }

    private static void addNioSupers0(Map<String, String> m) {
        put(m, "java/nio/Buffer", "java/lang/Object");
        put(m, "sun/misc/Unsafe", "java/lang/Object");
        put(m, "java/nio/channels/FileChannel", "java/lang/Object");
        put(m, "java/nio/charset/Charset", "java/lang/Object");
        put(m, "java/nio/charset/StandardCharsets", "java/lang/Object");
        put(m, "java/nio/file/Path", "java/lang/Object");
        put(m, "java/nio/file/Paths", "java/lang/Object");
        put(m, "java/nio/file/Files", "java/lang/Object");
        put(m, "java/nio/file/OpenOption", "java/lang/Object");
        put(m, "java/nio/file/LinkOption", "java/lang/Object");
        put(m, "java/nio/file/attribute/FileAttribute", "java/lang/Object");
        put(m, "java/nio/file/CopyOption", "java/lang/Object");
        put(m, "java/nio/file/WatchService", "java/lang/Object");
        put(m, "java/nio/file/WatchKey", "java/lang/Object");
        put(m, "java/nio/file/WatchEvent", "java/lang/Object");
        put(m, "java/nio/file/WatchEvent$Kind", "java/lang/Object");
        put(m, "java/nio/file/StandardWatchEventKinds", "java/lang/Object");
        put(m, "java/nio/file/FileVisitOption", "java/lang/Object");
        put(m, "java/nio/file/StandardCopyOption", "java/lang/Object");
        put(m, "java/nio/file/StandardOpenOption", "java/lang/Object");
        put(m, "java/nio/ByteBuffer", "java/nio/Buffer");
        put(m, "java/nio/BufferOverflowException", "java/lang/RuntimeException");
        put(m, "java/nio/BufferUnderflowException", "java/lang/RuntimeException");
        put(m, "java/io/File", "java/lang/Object");
        put(m, "java/nio/ByteOrder", "java/lang/Object");
        put(m, "java/nio/IntBuffer", "java/nio/Buffer");
        put(m, "java/nio/LongBuffer", "java/nio/Buffer");
        put(m, "java/nio/DoubleBuffer", "java/nio/Buffer");
        put(m, "java/nio/CharBuffer", "java/nio/Buffer");
        put(m, "java/io/InputStream", "java/lang/Object");
        put(m, "java/io/OutputStream", "java/lang/Object");
        put(m, "java/io/Reader", "java/lang/Object");
        put(m, "java/io/Writer", "java/lang/Object");
        put(m, "java/io/ByteArrayInputStream", "java/io/InputStream");
        put(m, "java/io/FileInputStream", "java/io/InputStream");
        put(m, "java/io/ByteArrayOutputStream", "java/io/OutputStream");
        put(m, "java/io/FileOutputStream", "java/io/OutputStream");
        put(m, "java/io/StringReader", "java/io/Reader");
        put(m, "java/io/InputStreamReader", "java/io/Reader");
        put(m, "java/io/FileReader", "java/io/Reader");
        put(m, "java/io/BufferedReader", "java/io/Reader");
        put(m, "java/io/StringWriter", "java/io/Writer");
        put(m, "java/io/OutputStreamWriter", "java/io/Writer");
        put(m, "java/io/FileWriter", "java/io/Writer");
        put(m, "java/io/BufferedWriter", "java/io/Writer");
        put(m, "java/io/PrintWriter", "java/io/Writer");
        put(m, "java/nio/ShortBuffer", "java/nio/Buffer");
        put(m, "java/nio/FloatBuffer", "java/nio/Buffer");
        put(m, "java/nio/file/FileSystem", "java/lang/Object");
        put(m, "java/nio/file/FileSystems", "java/lang/Object");
        put(m, "java/nio/file/attribute/BasicFileAttributes", "java/lang/Object");
        put(m, "java/util/function/BiPredicate", "java/lang/Object");
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
        put(m, "java/lang/ClassLoader", "java/lang/Object");
        put(m, "java/lang/StringBuffer", "java/lang/Object");
        put(m, "java/lang/NoSuchFieldError", "java/lang/Error");
        put(m, "java/lang/LinkageError", "java/lang/Error");
        put(m, "java/lang/UnsatisfiedLinkError", "java/lang/LinkageError");
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
        put(m, "java/util/logging/Logger", "java/lang/Object");
        put(m, "java/util/logging/Level", "java/lang/Object");
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
        put(m, "java/lang/VirtualMachineError", "java/lang/Error");
        put(m, "java/lang/OutOfMemoryError", "java/lang/VirtualMachineError");
        put(m, "java/lang/NullPointerException", "java/lang/RuntimeException");
        put(m, "java/lang/ArithmeticException", "java/lang/RuntimeException");
        put(m, "java/lang/ClassCastException", "java/lang/RuntimeException");
        put(m, "java/lang/IndexOutOfBoundsException", "java/lang/RuntimeException");
        put(m, "java/lang/ArrayIndexOutOfBoundsException", "java/lang/IndexOutOfBoundsException");
        put(m, "java/lang/ArrayStoreException", "java/lang/RuntimeException");
        put(m, "java/lang/IllegalArgumentException", "java/lang/RuntimeException");
        put(m, "java/lang/NumberFormatException", "java/lang/IllegalArgumentException");
        put(m, "java/lang/IllegalStateException", "java/lang/RuntimeException");
        put(m, "java/lang/UnsupportedOperationException", "java/lang/RuntimeException");
        put(m, "java/lang/CloneNotSupportedException", "java/lang/Exception");
        put(m, "java/lang/ReflectiveOperationException", "java/lang/Exception");
        put(m, "java/lang/ClassNotFoundException", "java/lang/ReflectiveOperationException");
        put(m, "java/io/IOException", "java/lang/Exception");
        put(m, "java/io/UncheckedIOException", "java/lang/RuntimeException");
        put(m, "java/lang/ref/Reference", "java/lang/Object");
        put(m, "java/lang/ref/WeakReference", "java/lang/ref/Reference");
        put(m, "java/lang/ref/ReferenceQueue", "java/lang/Object");
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
            "java/lang/UnsupportedOperationException",
            "java/lang/CloneNotSupportedException",
            "java/io/IOException",
            "java/lang/InterruptedException",
            "java/lang/IllegalAccessException",
            "java/lang/NoSuchFieldException",
            "java/lang/NoSuchMethodException",
            "java/lang/InstantiationException",
            "java/lang/reflect/InvocationTargetException",
            "java/lang/Enum",
            "java/lang/NoSuchFieldError",
            "java/lang/ThreadLocal",
            "java/lang/Thread",
            "java/lang/ref/WeakReference",
            "java/util/HashMap",
            "java/util/ArrayList",
            "java/util/LinkedHashMap",
            "java/util/Random",
            "java/io/InputStream",
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
            Map.entry("done()V", "done"),
            Map.entry("run()V", "run"),
            Map.entry("initialValue()Ljava/lang/Object;", "initialValue"));

    public static final Set<String> EXTENDABLE_MEMBER_NAMES = Set.of(
            "getMessage", "JavaClassName", "message", "__origin",
            "name", "ordinal", "__name", "__ordinal",
            "mouseClicked", "mousePressed", "mouseReleased", "mouseEntered", "mouseExited",
            "mouseDragged", "mouseMoved", "windowOpened", "windowClosing", "windowClosed",
            "windowIconified", "windowDeiconified", "windowActivated", "windowDeactivated",
            "paintComponent", "doInBackground", "process", "done", "run", "initialValue");

    public record WalkResult(String declaringInternal, ShimTarget target) {
    }

        private static final Map<String, ShimTarget> METHODS = buildMethods();

    private static Map<String, ShimTarget> buildMethods() {
        Map<String, ShimTarget> m = new java.util.HashMap<>(2048);
        addLangMethods0(m);
        addNumberMethods0(m);
        addNumberMethods1(m);
        addNumberMethods2(m);
        addNumberMethods3(m);
        addNumberMethods4(m);
        addNumberMethods5(m);
        addStrictMathMethods0(m);
        addStrictMathMethods1(m);
        addTextBuilder0(m);
        addTextBuilder1(m);
        addTextString0(m);
        addTextChar0(m);
        addTextJoiner0(m);
        addArraysGaps0(m);
        addArraysGaps1(m);
        addArraysGaps2(m);
        addCollectionsGaps0(m);
        addMiscGaps0(m);
        addMiscGaps1(m);
        addIoMethods0(m);
        addNioMethods0(m);
        addNioMethods1(m);
        addNioMethods2(m);
        addNioMethods3(m);
        addNioMethods4(m);
        addNioMethods5(m);
        addNioMethods6(m);
        addNioMethods7(m);
        addIoStreams0(m);
        addIoStreams1(m);
        addNioMethods8(m);
        addNioMethods9(m);
        addTier1Methods0(m);
        addTier1Methods1(m);
        addTimeTextMethods0(m);
        addTimeTextMethods1(m);
        addDateCalMethods0(m);
        addUtilColMethods0(m);
        addEnumStrMethods0(m);
        addConcurrentMethods2(m);
        addNavEnumMethods0(m);
        addNetMethods0(m);
        addNetMethods1(m);
        addNetMethods2(m);
        addHttpClientMethods0(m);
        addWatchMethods0(m);
        addCryptoMethods0(m);
        addCryptoMethods1(m);
        addCryptoMethods2(m);
        addNavViewMethods0(m);
        addProcessMethods0(m);
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
        addUtilLoggingMethods(m);
        addRefMethods(m);
        return Map.copyOf(m);
    }

    private static void addRefMethods(Map<String, ShimTarget> m) {
        put(m, "java/lang/ref/Reference.get()Ljava/lang/Object;", instance("get"));
        put(m, "java/lang/ref/Reference.clear()V", instance("clear"));
        put(m, "java/lang/ref/Reference.refersTo(Ljava/lang/Object;)Z", instance("refersTo"));
        put(m, "java/lang/ref/Reference.isEnqueued()Z", instance("isEnqueued"));
        put(m, "java/lang/ref/Reference.enqueue()Z", instance("enqueue"));
        put(m, "java/lang/ref/ReferenceQueue.poll()Ljava/lang/ref/Reference;", instance("poll"));
        put(m, "java/lang/ref/ReferenceQueue.remove()Ljava/lang/ref/Reference;", instance("remove"));
        put(m, "java/lang/ref/ReferenceQueue.remove(J)Ljava/lang/ref/Reference;", instance("remove"));
    }

    private static void addUtilLoggingMethods(Map<String, ShimTarget> m) {
        put(m, "java/util/logging/Logger.getLogger(Ljava/lang/String;)Ljava/util/logging/Logger;", statics("getLogger"));
        put(m, "java/util/logging/Logger.getLogger(Ljava/lang/String;Ljava/lang/String;)Ljava/util/logging/Logger;", statics("getLogger"));
        put(m, "java/util/logging/Logger.getName()Ljava/lang/String;", instance("getName"));
        put(m, "java/util/logging/Logger.setLevel(Ljava/util/logging/Level;)V", instance("setLevel"));
        put(m, "java/util/logging/Logger.getLevel()Ljava/util/logging/Level;", instance("getLevel"));
        put(m, "java/util/logging/Logger.isLoggable(Ljava/util/logging/Level;)Z", instance("isLoggable"));
        put(m, "java/util/logging/Logger.log(Ljava/util/logging/Level;Ljava/lang/String;)V", instance("log"));
        put(m, "java/util/logging/Logger.log(Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Object;)V", instance("log"));
        put(m, "java/util/logging/Logger.log(Ljava/util/logging/Level;Ljava/lang/String;[Ljava/lang/Object;)V", instance("log"));
        put(m, "java/util/logging/Logger.log(Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Throwable;)V", instance("log"));
        put(m, "java/util/logging/Logger.severe(Ljava/lang/String;)V", instance("severe"));
        put(m, "java/util/logging/Logger.warning(Ljava/lang/String;)V", instance("warning"));
        put(m, "java/util/logging/Logger.info(Ljava/lang/String;)V", instance("info"));
        put(m, "java/util/logging/Logger.config(Ljava/lang/String;)V", instance("config"));
        put(m, "java/util/logging/Logger.fine(Ljava/lang/String;)V", instance("fine"));
        put(m, "java/util/logging/Logger.finer(Ljava/lang/String;)V", instance("finer"));
        put(m, "java/util/logging/Logger.finest(Ljava/lang/String;)V", instance("finest"));
        put(m, "java/util/logging/Level.intValue()I", instance("intValue"));
        put(m, "java/util/logging/Level.getName()Ljava/lang/String;", instance("getName"));
    }

    private static void addLangMethods0(Map<String, ShimTarget> m) {
        put(m, "java/lang/Object.toString()Ljava/lang/String;", instance("toString"));
        put(m, "java/lang/Object.hashCode()I", instance("hashCode"));
        put(m, "java/lang/Object.equals(Ljava/lang/Object;)Z", instance("equals"));
        put(m, "java/lang/Object.getClass()Ljava/lang/Class;", instance("getClass"));
        put(m, "java/lang/Object.clone()Ljava/lang/Object;", instance("clone"));
        put(m, "java/lang/String.length()I", instance("length"));
        put(m, "java/lang/String.charAt(I)C", instance("charAt"));
        put(m, "java/lang/CharSequence.length()I", instance("length"));
        put(m, "java/lang/CharSequence.charAt(I)C", instance("charAt"));
        put(m, "java/lang/CharSequence.toString()Ljava/lang/String;", instance("toString"));
        put(m, "java/lang/String.isEmpty()Z", instance("isEmpty"));
        put(m, "java/lang/String.equals(Ljava/lang/Object;)Z", instance("equals"));
        put(m, "java/lang/String.hashCode()I", instance("hashCode"));
        put(m, "java/lang/String.toString()Ljava/lang/String;", instance("toString"));
    }

    private static void addNumberMethods0(Map<String, ShimTarget> m) {
        put(m, "java/util/Objects.requireNonNull(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;", statics("requireNonNull"));
        put(m, "java/util/Objects.requireNonNullElse(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", statics("requireNonNullElse"));
        put(m, "java/util/Objects.requireNonNullElseGet(Ljava/lang/Object;Ljava/util/function/Supplier;)Ljava/lang/Object;", statics("requireNonNullElseGet"));
        put(m, "java/util/Objects.isNull(Ljava/lang/Object;)Z", statics("isNull"));
        put(m, "java/util/Objects.nonNull(Ljava/lang/Object;)Z", statics("nonNull"));
        put(m, "java/util/Objects.equals(Ljava/lang/Object;Ljava/lang/Object;)Z", statics("equals"));
        put(m, "java/util/Objects.deepEquals(Ljava/lang/Object;Ljava/lang/Object;)Z", statics("deepEquals"));
        put(m, "java/util/Objects.hashCode(Ljava/lang/Object;)I", statics("hashCode"));
        put(m, "java/util/Objects.hash([Ljava/lang/Object;)I", statics("hash"));
        put(m, "java/util/Objects.toString(Ljava/lang/Object;)Ljava/lang/String;", statics("toString"));
        put(m, "java/util/Objects.toString(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/String;", statics("toString"));
        put(m, "java/util/Objects.compare(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)I", statics("compare"));
        put(m, "java/lang/System.lineSeparator()Ljava/lang/String;", statics("lineSeparator"));
    }

    private static void addNumberMethods1(Map<String, ShimTarget> m) {
        put(m, "java/lang/Integer.parseInt(Ljava/lang/String;I)I", statics("parseInt"));
        put(m, "java/lang/Integer.parseUnsignedInt(Ljava/lang/String;)I", statics("parseUnsignedInt"));
        put(m, "java/lang/Integer.parseUnsignedInt(Ljava/lang/String;I)I", statics("parseUnsignedInt"));
        put(m, "java/lang/Integer.valueOf(Ljava/lang/String;)Ljava/lang/Integer;", statics("valueOf"));
        put(m, "java/lang/Integer.valueOf(Ljava/lang/String;I)Ljava/lang/Integer;", statics("valueOf"));
        put(m, "java/lang/Integer.decode(Ljava/lang/String;)Ljava/lang/Integer;", statics("decode"));
        put(m, "java/lang/Integer.getInteger(Ljava/lang/String;)Ljava/lang/Integer;", statics("getInteger"));
        put(m, "java/lang/Integer.getInteger(Ljava/lang/String;I)Ljava/lang/Integer;", statics("getInteger"));
        put(m, "java/lang/Integer.getInteger(Ljava/lang/String;Ljava/lang/Integer;)Ljava/lang/Integer;", statics("getInteger"));
        put(m, "java/lang/Integer.compareUnsigned(II)I", statics("compareUnsigned"));
        put(m, "java/lang/Integer.divideUnsigned(II)I", statics("divideUnsigned"));
        put(m, "java/lang/Integer.remainderUnsigned(II)I", statics("remainderUnsigned"));
        put(m, "java/lang/Integer.hashCode(I)I", statics("hashCode"));
        put(m, "java/lang/Integer.toUnsignedLong(I)J", statics("toUnsignedLong"));
        put(m, "java/lang/Integer.toString(II)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Integer.toBinaryString(I)Ljava/lang/String;", statics("toBinaryString"));
        put(m, "java/lang/Integer.toOctalString(I)Ljava/lang/String;", statics("toOctalString"));
        put(m, "java/lang/Integer.toHexString(I)Ljava/lang/String;", statics("toHexString"));
        put(m, "java/lang/Integer.toUnsignedString(I)Ljava/lang/String;", statics("toUnsignedString"));
        put(m, "java/lang/Integer.toUnsignedString(II)Ljava/lang/String;", statics("toUnsignedString"));
        put(m, "java/lang/Integer.bitCount(I)I", statics("bitCount"));
        put(m, "java/lang/Integer.numberOfLeadingZeros(I)I", statics("numberOfLeadingZeros"));
        put(m, "java/lang/Integer.numberOfTrailingZeros(I)I", statics("numberOfTrailingZeros"));
        put(m, "java/lang/Integer.highestOneBit(I)I", statics("highestOneBit"));
        put(m, "java/lang/Integer.lowestOneBit(I)I", statics("lowestOneBit"));
        put(m, "java/lang/Integer.reverse(I)I", statics("reverse"));
        put(m, "java/lang/Integer.reverseBytes(I)I", statics("reverseBytes"));
        put(m, "java/lang/Integer.rotateLeft(II)I", statics("rotateLeft"));
        put(m, "java/lang/Integer.rotateRight(II)I", statics("rotateRight"));
        put(m, "java/lang/Integer.signum(I)I", statics("signum"));
    }

    private static void addNumberMethods2(Map<String, ShimTarget> m) {
        put(m, "java/lang/Long.parseLong(Ljava/lang/String;I)J", statics("parseLong"));
        put(m, "java/lang/Long.parseUnsignedLong(Ljava/lang/String;)J", statics("parseUnsignedLong"));
        put(m, "java/lang/Long.parseUnsignedLong(Ljava/lang/String;I)J", statics("parseUnsignedLong"));
        put(m, "java/lang/Long.valueOf(Ljava/lang/String;)Ljava/lang/Long;", statics("valueOf"));
        put(m, "java/lang/Long.valueOf(Ljava/lang/String;I)Ljava/lang/Long;", statics("valueOf"));
        put(m, "java/lang/Long.compare(JJ)I", statics("compare"));
        put(m, "java/lang/Long.compareUnsigned(JJ)I", statics("compareUnsigned"));
        put(m, "java/lang/Long.divideUnsigned(JJ)J", statics("divideUnsigned"));
        put(m, "java/lang/Long.remainderUnsigned(JJ)J", statics("remainderUnsigned"));
        put(m, "java/lang/Long.hashCode(J)I", statics("hashCode"));
        put(m, "java/lang/Long.toString(JI)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Long.toBinaryString(J)Ljava/lang/String;", statics("toBinaryString"));
        put(m, "java/lang/Long.toOctalString(J)Ljava/lang/String;", statics("toOctalString"));
        put(m, "java/lang/Long.toHexString(J)Ljava/lang/String;", statics("toHexString"));
        put(m, "java/lang/Long.toUnsignedString(J)Ljava/lang/String;", statics("toUnsignedString"));
        put(m, "java/lang/Long.toUnsignedString(JI)Ljava/lang/String;", statics("toUnsignedString"));
        put(m, "java/lang/Long.bitCount(J)I", statics("bitCount"));
        put(m, "java/lang/Long.numberOfLeadingZeros(J)I", statics("numberOfLeadingZeros"));
        put(m, "java/lang/Long.numberOfTrailingZeros(J)I", statics("numberOfTrailingZeros"));
        put(m, "java/lang/Long.highestOneBit(J)J", statics("highestOneBit"));
        put(m, "java/lang/Long.lowestOneBit(J)J", statics("lowestOneBit"));
        put(m, "java/lang/Long.reverse(J)J", statics("reverse"));
        put(m, "java/lang/Long.reverseBytes(J)J", statics("reverseBytes"));
        put(m, "java/lang/Long.rotateLeft(JI)J", statics("rotateLeft"));
        put(m, "java/lang/Long.rotateRight(JI)J", statics("rotateRight"));
        put(m, "java/lang/Long.signum(J)I", statics("signum"));
    }

    private static void addNumberMethods3(Map<String, ShimTarget> m) {
        put(m, "java/lang/Short.parseShort(Ljava/lang/String;)S", statics("parseShort"));
        put(m, "java/lang/Short.parseShort(Ljava/lang/String;I)S", statics("parseShort"));
        put(m, "java/lang/Short.valueOf(Ljava/lang/String;)Ljava/lang/Short;", statics("valueOf"));
        put(m, "java/lang/Short.valueOf(Ljava/lang/String;I)Ljava/lang/Short;", statics("valueOf"));
        put(m, "java/lang/Short.compare(SS)I", statics("compare"));
        put(m, "java/lang/Short.hashCode(S)I", statics("hashCode"));
        put(m, "java/lang/Short.toUnsignedInt(S)I", statics("toUnsignedInt"));
        put(m, "java/lang/Short.toUnsignedLong(S)J", statics("toUnsignedLong"));
        put(m, "java/lang/Short.reverseBytes(S)S", statics("reverseBytes"));
        put(m, "java/lang/Byte.parseByte(Ljava/lang/String;)B", statics("parseByte"));
        put(m, "java/lang/Byte.parseByte(Ljava/lang/String;I)B", statics("parseByte"));
        put(m, "java/lang/Byte.valueOf(Ljava/lang/String;)Ljava/lang/Byte;", statics("valueOf"));
        put(m, "java/lang/Byte.valueOf(Ljava/lang/String;I)Ljava/lang/Byte;", statics("valueOf"));
        put(m, "java/lang/Byte.compare(BB)I", statics("compare"));
        put(m, "java/lang/Byte.hashCode(B)I", statics("hashCode"));
        put(m, "java/lang/Byte.toUnsignedInt(B)I", statics("toUnsignedInt"));
        put(m, "java/lang/Byte.toUnsignedLong(B)J", statics("toUnsignedLong"));
    }

    private static void addNumberMethods4(Map<String, ShimTarget> m) {
        put(m, "java/lang/Double.valueOf(Ljava/lang/String;)Ljava/lang/Double;", statics("valueOf"));
        put(m, "java/lang/Double.isNaN(D)Z", statics("isNaN"));
        put(m, "java/lang/Double.isInfinite(D)Z", statics("isInfinite"));
        put(m, "java/lang/Double.isFinite(D)Z", statics("isFinite"));
        put(m, "java/lang/Double.compare(DD)I", statics("compare"));
        put(m, "java/lang/Double.doubleToLongBits(D)J", statics("doubleToLongBits"));
        put(m, "java/lang/Double.doubleToRawLongBits(D)J", statics("doubleToRawLongBits"));
        put(m, "java/lang/Double.longBitsToDouble(J)D", statics("longBitsToDouble"));
        put(m, "java/lang/Double.hashCode(D)I", statics("hashCode"));
        put(m, "java/lang/Double.toHexString(D)Ljava/lang/String;", statics("toHexString"));
        put(m, "java/lang/Double.isNaN()Z", instance("isNaN"));
        put(m, "java/lang/Double.isInfinite()Z", instance("isInfinite"));
        put(m, "java/lang/Float.valueOf(Ljava/lang/String;)Ljava/lang/Float;", statics("valueOf"));
        put(m, "java/lang/Float.sum(FF)F", statics("sum"));
        put(m, "java/lang/Float.max(FF)F", statics("max"));
        put(m, "java/lang/Float.min(FF)F", statics("min"));
        put(m, "java/lang/Float.isNaN(F)Z", statics("isNaN"));
        put(m, "java/lang/Float.isInfinite(F)Z", statics("isInfinite"));
        put(m, "java/lang/Float.isFinite(F)Z", statics("isFinite"));
        put(m, "java/lang/Float.compare(FF)I", statics("compare"));
        put(m, "java/lang/Float.floatToIntBits(F)I", statics("floatToIntBits"));
        put(m, "java/lang/Float.floatToRawIntBits(F)I", statics("floatToRawIntBits"));
        put(m, "java/lang/Float.intBitsToFloat(I)F", statics("intBitsToFloat"));
        put(m, "java/lang/Float.hashCode(F)I", statics("hashCode"));
        put(m, "java/lang/Float.toHexString(F)Ljava/lang/String;", statics("toHexString"));
        put(m, "java/lang/Float.isNaN()Z", instance("isNaN"));
        put(m, "java/lang/Float.isInfinite()Z", instance("isInfinite"));
    }

    private static void addNumberMethods5(Map<String, ShimTarget> m) {
        put(m, "java/lang/Math.floorDiv(II)I", statics("floorDiv"));
        put(m, "java/lang/Math.floorDiv(JI)J", statics("floorDiv"));
        put(m, "java/lang/Math.floorDiv(JJ)J", statics("floorDiv"));
        put(m, "java/lang/Math.floorMod(II)I", statics("floorMod"));
        put(m, "java/lang/Math.floorMod(JI)I", statics("floorMod"));
        put(m, "java/lang/Math.floorMod(JJ)J", statics("floorMod"));
        put(m, "java/lang/Math.addExact(II)I", statics("addExact"));
        put(m, "java/lang/Math.addExact(JJ)J", statics("addExact"));
        put(m, "java/lang/Math.subtractExact(II)I", statics("subtractExact"));
        put(m, "java/lang/Math.subtractExact(JJ)J", statics("subtractExact"));
        put(m, "java/lang/Math.multiplyExact(II)I", statics("multiplyExact"));
        put(m, "java/lang/Math.multiplyExact(JI)J", statics("multiplyExact"));
        put(m, "java/lang/Math.multiplyExact(JJ)J", statics("multiplyExact"));
        put(m, "java/lang/Math.incrementExact(I)I", statics("incrementExact"));
        put(m, "java/lang/Math.incrementExact(J)J", statics("incrementExact"));
        put(m, "java/lang/Math.decrementExact(I)I", statics("decrementExact"));
        put(m, "java/lang/Math.decrementExact(J)J", statics("decrementExact"));
        put(m, "java/lang/Math.negateExact(I)I", statics("negateExact"));
        put(m, "java/lang/Math.negateExact(J)J", statics("negateExact"));
        put(m, "java/lang/Math.toIntExact(J)I", statics("toIntExact"));
        put(m, "java/lang/Math.expm1(D)D", statics("expm1"));
        put(m, "java/lang/Math.log1p(D)D", statics("log1p"));
        put(m, "java/lang/Math.asin(D)D", statics("asin"));
        put(m, "java/lang/Math.acos(D)D", statics("acos"));
        put(m, "java/lang/Math.atan(D)D", statics("atan"));
        put(m, "java/lang/Math.sinh(D)D", statics("sinh"));
        put(m, "java/lang/Math.cosh(D)D", statics("cosh"));
        put(m, "java/lang/Math.tanh(D)D", statics("tanh"));
        put(m, "java/lang/Math.copySign(DD)D", statics("copySign"));
        put(m, "java/lang/Math.copySign(FF)F", statics("copySign"));
        put(m, "java/lang/Math.nextUp(D)D", statics("nextUp"));
        put(m, "java/lang/Math.nextUp(F)F", statics("nextUp"));
        put(m, "java/lang/Math.nextDown(D)D", statics("nextDown"));
        put(m, "java/lang/Math.nextDown(F)F", statics("nextDown"));
        put(m, "java/lang/Math.nextAfter(DD)D", statics("nextAfter"));
        put(m, "java/lang/Math.nextAfter(FD)F", statics("nextAfter"));
        put(m, "java/lang/Math.ulp(D)D", statics("ulp"));
        put(m, "java/lang/Math.ulp(F)F", statics("ulp"));
        put(m, "java/lang/Math.scalb(DI)D", statics("scalb"));
        put(m, "java/lang/Math.scalb(FI)F", statics("scalb"));
        put(m, "java/lang/Math.getExponent(D)I", statics("getExponent"));
        put(m, "java/lang/Math.getExponent(F)I", statics("getExponent"));
        put(m, "java/lang/Math.rint(D)D", statics("rint"));
        put(m, "java/lang/Math.IEEEremainder(DD)D", statics("IEEEremainder"));
        put(m, "java/lang/Math.signum(F)F", statics("signum"));
    }

    private static void addStrictMathMethods0(Map<String, ShimTarget> m) {
        put(m, "java/lang/StrictMath.abs(I)I", statics("abs"));
        put(m, "java/lang/StrictMath.abs(J)J", statics("abs"));
        put(m, "java/lang/StrictMath.abs(F)F", statics("abs"));
        put(m, "java/lang/StrictMath.abs(D)D", statics("abs"));
        put(m, "java/lang/StrictMath.max(II)I", statics("max"));
        put(m, "java/lang/StrictMath.max(JJ)J", statics("max"));
        put(m, "java/lang/StrictMath.max(FF)F", statics("max"));
        put(m, "java/lang/StrictMath.max(DD)D", statics("max"));
        put(m, "java/lang/StrictMath.min(II)I", statics("min"));
        put(m, "java/lang/StrictMath.min(JJ)J", statics("min"));
        put(m, "java/lang/StrictMath.min(FF)F", statics("min"));
        put(m, "java/lang/StrictMath.min(DD)D", statics("min"));
        put(m, "java/lang/StrictMath.sqrt(D)D", statics("sqrt"));
        put(m, "java/lang/StrictMath.cbrt(D)D", statics("cbrt"));
        put(m, "java/lang/StrictMath.pow(DD)D", statics("pow"));
        put(m, "java/lang/StrictMath.exp(D)D", statics("exp"));
        put(m, "java/lang/StrictMath.expm1(D)D", statics("expm1"));
        put(m, "java/lang/StrictMath.log(D)D", statics("log"));
        put(m, "java/lang/StrictMath.log10(D)D", statics("log10"));
        put(m, "java/lang/StrictMath.log1p(D)D", statics("log1p"));
        put(m, "java/lang/StrictMath.sin(D)D", statics("sin"));
        put(m, "java/lang/StrictMath.cos(D)D", statics("cos"));
        put(m, "java/lang/StrictMath.tan(D)D", statics("tan"));
        put(m, "java/lang/StrictMath.asin(D)D", statics("asin"));
        put(m, "java/lang/StrictMath.acos(D)D", statics("acos"));
        put(m, "java/lang/StrictMath.atan(D)D", statics("atan"));
        put(m, "java/lang/StrictMath.atan2(DD)D", statics("atan2"));
        put(m, "java/lang/StrictMath.sinh(D)D", statics("sinh"));
        put(m, "java/lang/StrictMath.cosh(D)D", statics("cosh"));
        put(m, "java/lang/StrictMath.tanh(D)D", statics("tanh"));
        put(m, "java/lang/StrictMath.toRadians(D)D", statics("toRadians"));
        put(m, "java/lang/StrictMath.toDegrees(D)D", statics("toDegrees"));
        put(m, "java/lang/StrictMath.hypot(DD)D", statics("hypot"));
        put(m, "java/lang/StrictMath.IEEEremainder(DD)D", statics("IEEEremainder"));
        put(m, "java/lang/StrictMath.floor(D)D", statics("floor"));
        put(m, "java/lang/StrictMath.ceil(D)D", statics("ceil"));
        put(m, "java/lang/StrictMath.rint(D)D", statics("rint"));
        put(m, "java/lang/StrictMath.round(D)J", statics("round"));
    }

    private static void addStrictMathMethods1(Map<String, ShimTarget> m) {
        put(m, "java/lang/StrictMath.round(F)I", statics("round"));
        put(m, "java/lang/StrictMath.random()D", statics("random"));
        put(m, "java/lang/StrictMath.signum(D)D", statics("signum"));
        put(m, "java/lang/StrictMath.signum(F)F", statics("signum"));
        put(m, "java/lang/StrictMath.copySign(DD)D", statics("copySign"));
        put(m, "java/lang/StrictMath.copySign(FF)F", statics("copySign"));
        put(m, "java/lang/StrictMath.nextUp(D)D", statics("nextUp"));
        put(m, "java/lang/StrictMath.nextUp(F)F", statics("nextUp"));
        put(m, "java/lang/StrictMath.nextDown(D)D", statics("nextDown"));
        put(m, "java/lang/StrictMath.nextDown(F)F", statics("nextDown"));
        put(m, "java/lang/StrictMath.nextAfter(DD)D", statics("nextAfter"));
        put(m, "java/lang/StrictMath.nextAfter(FD)F", statics("nextAfter"));
        put(m, "java/lang/StrictMath.ulp(D)D", statics("ulp"));
        put(m, "java/lang/StrictMath.ulp(F)F", statics("ulp"));
        put(m, "java/lang/StrictMath.scalb(DI)D", statics("scalb"));
        put(m, "java/lang/StrictMath.scalb(FI)F", statics("scalb"));
        put(m, "java/lang/StrictMath.getExponent(D)I", statics("getExponent"));
        put(m, "java/lang/StrictMath.getExponent(F)I", statics("getExponent"));
        put(m, "java/lang/StrictMath.floorDiv(II)I", statics("floorDiv"));
        put(m, "java/lang/StrictMath.floorDiv(JI)J", statics("floorDiv"));
        put(m, "java/lang/StrictMath.floorDiv(JJ)J", statics("floorDiv"));
        put(m, "java/lang/StrictMath.floorMod(II)I", statics("floorMod"));
        put(m, "java/lang/StrictMath.floorMod(JI)I", statics("floorMod"));
        put(m, "java/lang/StrictMath.floorMod(JJ)J", statics("floorMod"));
        put(m, "java/lang/StrictMath.addExact(II)I", statics("addExact"));
        put(m, "java/lang/StrictMath.addExact(JJ)J", statics("addExact"));
        put(m, "java/lang/StrictMath.subtractExact(II)I", statics("subtractExact"));
        put(m, "java/lang/StrictMath.subtractExact(JJ)J", statics("subtractExact"));
        put(m, "java/lang/StrictMath.multiplyExact(II)I", statics("multiplyExact"));
        put(m, "java/lang/StrictMath.multiplyExact(JI)J", statics("multiplyExact"));
        put(m, "java/lang/StrictMath.multiplyExact(JJ)J", statics("multiplyExact"));
        put(m, "java/lang/StrictMath.incrementExact(I)I", statics("incrementExact"));
        put(m, "java/lang/StrictMath.incrementExact(J)J", statics("incrementExact"));
        put(m, "java/lang/StrictMath.decrementExact(I)I", statics("decrementExact"));
        put(m, "java/lang/StrictMath.decrementExact(J)J", statics("decrementExact"));
        put(m, "java/lang/StrictMath.negateExact(I)I", statics("negateExact"));
        put(m, "java/lang/StrictMath.negateExact(J)J", statics("negateExact"));
        put(m, "java/lang/StrictMath.toIntExact(J)I", statics("toIntExact"));
    }

    private static void addTextBuilder0(Map<String, ShimTarget> m) {
        put(m, "java/lang/StringBuilder.append(I)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(J)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(F)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(D)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(C)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(Z)Ljava/lang/StringBuilder;", instance("append_Z"));
        put(m, "java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(Ljava/lang/Object;)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append([C)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append([CII)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(Ljava/lang/CharSequence;)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.append(Ljava/lang/CharSequence;II)Ljava/lang/StringBuilder;", instance("append"));
        put(m, "java/lang/StringBuilder.appendCodePoint(I)Ljava/lang/StringBuilder;", instance("appendCodePoint"));
        put(m, "java/lang/StringBuilder.insert(ILjava/lang/String;)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(IC)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(II)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(IJ)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(IF)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(ID)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(ILjava/lang/Object;)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(I[C)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(IZ)Ljava/lang/StringBuilder;", instance("insert_Z"));
        put(m, "java/lang/StringBuilder.insert(I[CII)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(ILjava/lang/CharSequence;)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.insert(ILjava/lang/CharSequence;II)Ljava/lang/StringBuilder;", instance("insert"));
        put(m, "java/lang/StringBuilder.delete(II)Ljava/lang/StringBuilder;", instance("delete"));
        put(m, "java/lang/StringBuilder.deleteCharAt(I)Ljava/lang/StringBuilder;", instance("deleteCharAt"));
        put(m, "java/lang/StringBuilder.replace(IILjava/lang/String;)Ljava/lang/StringBuilder;", instance("replace"));
        put(m, "java/lang/StringBuilder.reverse()Ljava/lang/StringBuilder;", instance("reverse"));
        put(m, "java/lang/StringBuilder.setLength(I)V", instance("setLength"));
        put(m, "java/lang/StringBuilder.setCharAt(IC)V", instance("setCharAt"));
        put(m, "java/lang/StringBuilder.charAt(I)C", instance("charAt"));
        put(m, "java/lang/StringBuilder.length()I", instance("length"));
        put(m, "java/lang/StringBuilder.capacity()I", instance("capacity"));
        put(m, "java/lang/StringBuilder.ensureCapacity(I)V", instance("ensureCapacity"));
        put(m, "java/lang/StringBuilder.trimToSize()V", instance("trimToSize"));
        put(m, "java/lang/StringBuilder.indexOf(Ljava/lang/String;)I", instance("indexOf"));
        put(m, "java/lang/StringBuilder.indexOf(Ljava/lang/String;I)I", instance("indexOf"));
        put(m, "java/lang/StringBuilder.lastIndexOf(Ljava/lang/String;)I", instance("lastIndexOf"));
        put(m, "java/lang/StringBuilder.lastIndexOf(Ljava/lang/String;I)I", instance("lastIndexOf"));
        put(m, "java/lang/StringBuilder.substring(I)Ljava/lang/String;", instance("substring"));
        put(m, "java/lang/StringBuilder.substring(II)Ljava/lang/String;", instance("substring"));
        put(m, "java/lang/StringBuilder.subSequence(II)Ljava/lang/CharSequence;", instance("subSequence"));
        put(m, "java/lang/StringBuilder.codePointAt(I)I", instance("codePointAt"));
        put(m, "java/lang/StringBuilder.getChars(II[CI)V", instance("getChars"));
        put(m, "java/lang/StringBuilder.toString()Ljava/lang/String;", instance("toString"));
    }

    private static void addTextBuilder1(Map<String, ShimTarget> m) {
        put(m, "java/lang/StringBuffer.append(I)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append(J)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append(F)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append(D)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append(C)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append(Z)Ljava/lang/StringBuffer;", instance("append_Z"));
        put(m, "java/lang/StringBuffer.append(Ljava/lang/String;)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append(Ljava/lang/Object;)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append([C)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append([CII)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append(Ljava/lang/CharSequence;)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.append(Ljava/lang/CharSequence;II)Ljava/lang/StringBuffer;", instance("append"));
        put(m, "java/lang/StringBuffer.appendCodePoint(I)Ljava/lang/StringBuffer;", instance("appendCodePoint"));
        put(m, "java/lang/StringBuffer.insert(ILjava/lang/String;)Ljava/lang/StringBuffer;", instance("insert"));
        put(m, "java/lang/StringBuffer.insert(IC)Ljava/lang/StringBuffer;", instance("insert"));
        put(m, "java/lang/StringBuffer.insert(II)Ljava/lang/StringBuffer;", instance("insert"));
        put(m, "java/lang/StringBuffer.insert(IJ)Ljava/lang/StringBuffer;", instance("insert"));
        put(m, "java/lang/StringBuffer.insert(IF)Ljava/lang/StringBuffer;", instance("insert"));
        put(m, "java/lang/StringBuffer.insert(ID)Ljava/lang/StringBuffer;", instance("insert"));
        put(m, "java/lang/StringBuffer.insert(ILjava/lang/Object;)Ljava/lang/StringBuffer;", instance("insert"));
        put(m, "java/lang/StringBuffer.insert(I[C)Ljava/lang/StringBuffer;", instance("insert"));
        put(m, "java/lang/StringBuffer.insert(IZ)Ljava/lang/StringBuffer;", instance("insert_Z"));
        put(m, "java/lang/StringBuffer.insert(I[CII)Ljava/lang/StringBuffer;", instance("insert"));
        put(m, "java/lang/StringBuffer.insert(ILjava/lang/CharSequence;)Ljava/lang/StringBuffer;", instance("insert"));
        put(m, "java/lang/StringBuffer.insert(ILjava/lang/CharSequence;II)Ljava/lang/StringBuffer;", instance("insert"));
        put(m, "java/lang/StringBuffer.delete(II)Ljava/lang/StringBuffer;", instance("delete"));
        put(m, "java/lang/StringBuffer.deleteCharAt(I)Ljava/lang/StringBuffer;", instance("deleteCharAt"));
        put(m, "java/lang/StringBuffer.replace(IILjava/lang/String;)Ljava/lang/StringBuffer;", instance("replace"));
        put(m, "java/lang/StringBuffer.reverse()Ljava/lang/StringBuffer;", instance("reverse"));
        put(m, "java/lang/StringBuffer.setLength(I)V", instance("setLength"));
        put(m, "java/lang/StringBuffer.setCharAt(IC)V", instance("setCharAt"));
        put(m, "java/lang/StringBuffer.charAt(I)C", instance("charAt"));
        put(m, "java/lang/StringBuffer.length()I", instance("length"));
        put(m, "java/lang/StringBuffer.capacity()I", instance("capacity"));
        put(m, "java/lang/StringBuffer.ensureCapacity(I)V", instance("ensureCapacity"));
        put(m, "java/lang/StringBuffer.trimToSize()V", instance("trimToSize"));
        put(m, "java/lang/StringBuffer.indexOf(Ljava/lang/String;)I", instance("indexOf"));
        put(m, "java/lang/StringBuffer.indexOf(Ljava/lang/String;I)I", instance("indexOf"));
        put(m, "java/lang/StringBuffer.lastIndexOf(Ljava/lang/String;)I", instance("lastIndexOf"));
        put(m, "java/lang/StringBuffer.lastIndexOf(Ljava/lang/String;I)I", instance("lastIndexOf"));
        put(m, "java/lang/StringBuffer.substring(I)Ljava/lang/String;", instance("substring"));
        put(m, "java/lang/StringBuffer.substring(II)Ljava/lang/String;", instance("substring"));
        put(m, "java/lang/StringBuffer.subSequence(II)Ljava/lang/CharSequence;", instance("subSequence"));
        put(m, "java/lang/StringBuffer.codePointAt(I)I", instance("codePointAt"));
        put(m, "java/lang/StringBuffer.getChars(II[CI)V", instance("getChars"));
        put(m, "java/lang/StringBuffer.toString()Ljava/lang/String;", instance("toString"));
    }

    private static void addTextString0(Map<String, ShimTarget> m) {
        put(m, "java/lang/String.valueOf([CII)Ljava/lang/String;", statics("valueOf"));
        put(m, "java/lang/String.copyValueOf([C)Ljava/lang/String;", statics("copyValueOf"));
        put(m, "java/lang/String.copyValueOf([CII)Ljava/lang/String;", statics("copyValueOf"));
        put(m, "java/lang/String.join(Ljava/lang/CharSequence;Ljava/lang/Iterable;)Ljava/lang/String;", statics("join"));
        put(m, "java/lang/String.intern()Ljava/lang/String;", instance("intern"));
        put(m, "java/lang/String.codePointAt(I)I", instance("codePointAt"));
        put(m, "java/lang/String.codePointBefore(I)I", instance("codePointBefore"));
        put(m, "java/lang/String.codePointCount(II)I", instance("codePointCount"));
        put(m, "java/lang/String.offsetByCodePoints(II)I", instance("offsetByCodePoints"));
        put(m, "java/lang/String.regionMatches(ILjava/lang/String;II)Z", instance("regionMatches"));
        put(m, "java/lang/String.regionMatches(ZILjava/lang/String;II)Z", instance("regionMatches"));
        put(m, "java/lang/String.contentEquals(Ljava/lang/CharSequence;)Z", instance("contentEquals"));
        put(m, "java/lang/String.contentEquals(Ljava/lang/StringBuffer;)Z", instance("contentEquals"));
        put(m, "java/lang/String.getChars(II[CI)V", instance("getChars"));
        put(m, "java/lang/String.indent(I)Ljava/lang/String;", instance("indent"));
        put(m, "java/lang/String.stripIndent()Ljava/lang/String;", instance("stripIndent"));
        put(m, "java/lang/String.translateEscapes()Ljava/lang/String;", instance("translateEscapes"));
    }

    private static void addTextChar0(Map<String, ShimTarget> m) {
        put(m, "java/lang/Character.isSpaceChar(C)Z", statics("isSpaceChar"));
        put(m, "java/lang/Character.isAlphabetic(I)Z", statics("isAlphabetic"));
        put(m, "java/lang/Character.forDigit(II)C", statics("forDigit"));
        put(m, "java/lang/Character.isJavaIdentifierStart(C)Z", statics("isJavaIdentifierStart"));
        put(m, "java/lang/Character.isJavaIdentifierPart(C)Z", statics("isJavaIdentifierPart"));
        put(m, "java/lang/Character.compare(CC)I", statics("compare"));
        put(m, "java/lang/Character.hashCode(C)I", statics("hashCode"));
        put(m, "java/lang/Character.charCount(I)I", statics("charCount"));
        put(m, "java/lang/Character.toString(I)Ljava/lang/String;", statics("toString"));
        put(m, "java/lang/Character.isSurrogate(C)Z", statics("isSurrogate"));
        put(m, "java/lang/Character.isHighSurrogate(C)Z", statics("isHighSurrogate"));
        put(m, "java/lang/Character.isLowSurrogate(C)Z", statics("isLowSurrogate"));
        put(m, "java/lang/Character.toChars(I)[C", statics("toChars"));
    }

    private static void addTextJoiner0(Map<String, ShimTarget> m) {
        put(m, "java/util/StringJoiner.add(Ljava/lang/CharSequence;)Ljava/util/StringJoiner;", instance("add"));
        put(m, "java/util/StringJoiner.toString()Ljava/lang/String;", instance("toString"));
        put(m, "java/util/StringJoiner.length()I", instance("length"));
        put(m, "java/util/StringJoiner.setEmptyValue(Ljava/lang/CharSequence;)Ljava/util/StringJoiner;", instance("setEmptyValue"));
        put(m, "java/util/StringJoiner.merge(Ljava/util/StringJoiner;)Ljava/util/StringJoiner;", instance("merge"));
    }

    private static void addArraysGaps0(Map<String, ShimTarget> m) {
        put(m, "java/util/Arrays.toString([F)Ljava/lang/String;", statics("toString"));
        put(m, "java/util/Arrays.toString([B)Ljava/lang/String;", statics("toString"));
        put(m, "java/util/Arrays.toString([S)Ljava/lang/String;", statics("toString"));
        put(m, "java/util/Arrays.hashCode([I)I", statics("hashCode"));
        put(m, "java/util/Arrays.hashCode([J)I", statics("hashCode"));
        put(m, "java/util/Arrays.hashCode([D)I", statics("hashCode"));
        put(m, "java/util/Arrays.hashCode([C)I", statics("hashCode"));
        put(m, "java/util/Arrays.hashCode([B)I", statics("hashCode"));
        put(m, "java/util/Arrays.hashCode([S)I", statics("hashCode"));
        put(m, "java/util/Arrays.hashCode([F)I", statics("hashCode"));
        put(m, "java/util/Arrays.hashCode([Ljava/lang/Object;)I", statics("hashCode"));
        put(m, "java/util/Arrays.fill([DD)V", statics("fill"));
        put(m, "java/util/Arrays.fill([FF)V", statics("fill"));
        put(m, "java/util/Arrays.fill([BB)V", statics("fill"));
        put(m, "java/util/Arrays.fill([SS)V", statics("fill"));
        put(m, "java/util/Arrays.sort([F)V", statics("sort"));
        put(m, "java/util/Arrays.sort([B)V", statics("sort"));
        put(m, "java/util/Arrays.sort([S)V", statics("sort"));
        put(m, "java/util/Arrays.stream([J)Ljava/util/stream/LongStream;", statics("stream"));
        put(m, "java/util/Arrays.stream([D)Ljava/util/stream/DoubleStream;", statics("stream"));
    }

    private static void addArraysGaps1(Map<String, ShimTarget> m) {
        put(m, "java/util/Arrays.copyOf([JI)[J", statics("copyOf"));
        put(m, "java/util/Arrays.copyOf([DI)[D", statics("copyOf"));
        put(m, "java/util/Arrays.copyOf([CI)[C", statics("copyOf"));
        put(m, "java/util/Arrays.copyOf([BI)[B", statics("copyOf"));
        put(m, "java/util/Arrays.copyOf([SI)[S", statics("copyOf"));
        put(m, "java/util/Arrays.copyOf([FI)[F", statics("copyOf"));
        put(m, "java/util/Arrays.copyOfRange([JII)[J", statics("copyOfRange"));
        put(m, "java/util/Arrays.copyOfRange([DII)[D", statics("copyOfRange"));
        put(m, "java/util/Arrays.copyOfRange([CII)[C", statics("copyOfRange"));
        put(m, "java/util/Arrays.equals([J[J)Z", statics("equals"));
        put(m, "java/util/Arrays.equals([D[D)Z", statics("equals"));
        put(m, "java/util/Arrays.equals([C[C)Z", statics("equals"));
        put(m, "java/util/Arrays.equals([B[B)Z", statics("equals"));
        put(m, "java/util/Arrays.equals([S[S)Z", statics("equals"));
        put(m, "java/util/Arrays.equals([F[F)Z", statics("equals"));
        put(m, "java/util/Arrays.binarySearch([JJ)I", statics("binarySearch"));
        put(m, "java/util/Arrays.binarySearch([DD)I", statics("binarySearch"));
        put(m, "java/util/Arrays.binarySearch([CC)I", statics("binarySearch"));
        put(m, "java/util/Arrays.binarySearch([Ljava/lang/Object;Ljava/lang/Object;)I", statics("binarySearch"));
        put(m, "java/util/Arrays.binarySearch([Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)I", statics("binarySearch"));
    }

    private static void addArraysGaps2(Map<String, ShimTarget> m) {
        put(m, "java/util/Arrays.deepToString([Ljava/lang/Object;)Ljava/lang/String;", statics("deepToString"));
        put(m, "java/util/Arrays.deepEquals([Ljava/lang/Object;[Ljava/lang/Object;)Z", statics("deepEquals"));
        put(m, "java/util/Arrays.deepHashCode([Ljava/lang/Object;)I", statics("deepHashCode"));
        put(m, "java/util/Arrays.setAll([Ljava/lang/Object;Ljava/util/function/IntFunction;)V", statics("setAll"));
    }

    private static void addCollectionsGaps0(Map<String, ShimTarget> m) {
        put(m, "java/util/Collections.swap(Ljava/util/List;II)V", statics("swap"));
        put(m, "java/util/Collections.shuffle(Ljava/util/List;Ljava/util/Random;)V", statics("shuffle"));
        put(m, "java/util/Collections.shuffle(Ljava/util/List;)V", statics("shuffle"));
        put(m, "java/util/Collections.binarySearch(Ljava/util/List;Ljava/lang/Object;)I", statics("binarySearch"));
        put(m, "java/util/Collections.binarySearch(Ljava/util/List;Ljava/lang/Object;Ljava/util/Comparator;)I", statics("binarySearch"));
        put(m, "java/util/Collections.rotate(Ljava/util/List;I)V", statics("rotate"));
        put(m, "java/util/Collections.nCopies(ILjava/lang/Object;)Ljava/util/List;", statics("nCopies"));
        put(m, "java/util/Collections.addAll(Ljava/util/Collection;[Ljava/lang/Object;)Z", statics("addAll"));
        put(m, "java/util/Collections.disjoint(Ljava/util/Collection;Ljava/util/Collection;)Z", statics("disjoint"));
        put(m, "java/util/Collections.fill(Ljava/util/List;Ljava/lang/Object;)V", statics("fill"));
        put(m, "java/util/Collections.replaceAll(Ljava/util/List;Ljava/lang/Object;Ljava/lang/Object;)Z", statics("replaceAll"));
        put(m, "java/util/Collections.copy(Ljava/util/List;Ljava/util/List;)V", statics("copy"));
        put(m, "java/util/Collections.singleton(Ljava/lang/Object;)Ljava/util/Set;", statics("singleton"));
        put(m, "java/util/Collections.singletonMap(Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;", statics("singletonMap"));
        put(m, "java/util/Collections.synchronizedList(Ljava/util/List;)Ljava/util/List;", statics("synchronizedList"));
        put(m, "java/util/Collections.synchronizedSet(Ljava/util/Set;)Ljava/util/Set;", statics("synchronizedSet"));
        put(m, "java/util/Collections.synchronizedMap(Ljava/util/Map;)Ljava/util/Map;", statics("synchronizedMap"));
        put(m, "java/util/Collections.synchronizedCollection(Ljava/util/Collection;)Ljava/util/Collection;", statics("synchronizedCollection"));
        put(m, "java/util/Collections.reverseOrder()Ljava/util/Comparator;", statics("reverseOrder"));
        put(m, "java/util/Collections.reverseOrder(Ljava/util/Comparator;)Ljava/util/Comparator;", statics("reverseOrder"));
    }

    private static void addMiscGaps0(Map<String, ShimTarget> m) {
        put(m, "java/util/concurrent/ThreadLocalRandom.current()Ljava/util/concurrent/ThreadLocalRandom;", statics("current"));
        put(m, "java/util/concurrent/ThreadLocalRandom.nextInt()I", instance("nextInt"));
        put(m, "java/util/concurrent/ThreadLocalRandom.nextInt(I)I", instance("nextInt"));
        put(m, "java/util/concurrent/ThreadLocalRandom.nextInt(II)I", instance("nextInt"));
        put(m, "java/util/concurrent/ThreadLocalRandom.nextLong()J", instance("nextLong"));
        put(m, "java/util/concurrent/ThreadLocalRandom.nextLong(J)J", instance("nextLong"));
        put(m, "java/util/concurrent/ThreadLocalRandom.nextDouble()D", instance("nextDouble"));
        put(m, "java/util/concurrent/ThreadLocalRandom.nextDouble(D)D", instance("nextDouble"));
        put(m, "java/util/concurrent/ThreadLocalRandom.nextFloat()F", instance("nextFloat"));
        put(m, "java/util/concurrent/ThreadLocalRandom.nextBoolean()Z", instance("nextBoolean"));
        put(m, "java/util/Formatter.format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/util/Formatter;", instance("format"));
        put(m, "java/util/Formatter.toString()Ljava/lang/String;", instance("toString"));
        put(m, "java/util/Formatter.close()V", instance("close"));
        put(m, "java/util/Formatter.flush()V", instance("flush"));
        put(m, "java/util/Currency.getInstance(Ljava/lang/String;)Ljava/util/Currency;", statics("getInstance"));
        put(m, "java/util/Currency.getCurrencyCode()Ljava/lang/String;", instance("getCurrencyCode"));
        put(m, "java/util/Currency.getDefaultFractionDigits()I", instance("getDefaultFractionDigits"));
        put(m, "java/util/Currency.getNumericCode()I", instance("getNumericCode"));
        put(m, "java/util/Currency.toString()Ljava/lang/String;", instance("toString"));
    }

    private static void addMiscGaps1(Map<String, ShimTarget> m) {
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.length()I", instance("length"));
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.get(I)I", instance("get"));
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.set(II)V", instance("set"));
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.getAndSet(II)I", instance("getAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.incrementAndGet(I)I", instance("incrementAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.decrementAndGet(I)I", instance("decrementAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.getAndIncrement(I)I", instance("getAndIncrement"));
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.getAndDecrement(I)I", instance("getAndDecrement"));
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.addAndGet(II)I", instance("addAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.getAndAdd(II)I", instance("getAndAdd"));
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.compareAndSet(III)Z", instance("compareAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicIntegerArray.toString()Ljava/lang/String;", instance("toString"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.length()I", instance("length"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.get(I)J", instance("get"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.set(IJ)V", instance("set"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.getAndSet(IJ)J", instance("getAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.incrementAndGet(I)J", instance("incrementAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.decrementAndGet(I)J", instance("decrementAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.getAndIncrement(I)J", instance("getAndIncrement"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.getAndDecrement(I)J", instance("getAndDecrement"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.addAndGet(IJ)J", instance("addAndGet"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.getAndAdd(IJ)J", instance("getAndAdd"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.compareAndSet(IJJ)Z", instance("compareAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicLongArray.toString()Ljava/lang/String;", instance("toString"));
        put(m, "java/util/concurrent/atomic/AtomicReferenceArray.length()I", instance("length"));
        put(m, "java/util/concurrent/atomic/AtomicReferenceArray.get(I)Ljava/lang/Object;", instance("get"));
        put(m, "java/util/concurrent/atomic/AtomicReferenceArray.set(ILjava/lang/Object;)V", instance("set"));
        put(m, "java/util/concurrent/atomic/AtomicReferenceArray.getAndSet(ILjava/lang/Object;)Ljava/lang/Object;", instance("getAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicReferenceArray.compareAndSet(ILjava/lang/Object;Ljava/lang/Object;)Z", instance("compareAndSet"));
        put(m, "java/util/concurrent/atomic/AtomicReferenceArray.toString()Ljava/lang/String;", instance("toString"));
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

    private static void addNioMethods0(Map<String, ShimTarget> m) {
        put(m, "java/nio/file/Paths.get(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;", statics("get"));
        put(m, "java/nio/file/Path.getFileName()Ljava/nio/file/Path;", instance("getFileName"));
        put(m, "java/nio/file/Path.getParent()Ljava/nio/file/Path;", instance("getParent"));
        put(m, "java/nio/file/Path.getNameCount()I", instance("getNameCount"));
        put(m, "java/nio/file/Path.getName(I)Ljava/nio/file/Path;", instance("getName"));
        put(m, "java/nio/file/Path.resolve(Ljava/nio/file/Path;)Ljava/nio/file/Path;", instance("resolve"));
        put(m, "java/nio/file/Path.resolve(Ljava/lang/String;)Ljava/nio/file/Path;", instance("resolve"));
        put(m, "java/nio/file/Path.resolveSibling(Ljava/lang/String;)Ljava/nio/file/Path;", instance("resolveSibling"));
        put(m, "java/nio/file/Path.toAbsolutePath()Ljava/nio/file/Path;", instance("toAbsolutePath"));
        put(m, "java/nio/file/Path.normalize()Ljava/nio/file/Path;", instance("normalize"));
        put(m, "java/nio/file/Path.startsWith(Ljava/nio/file/Path;)Z", instance("startsWith"));
        put(m, "java/nio/file/Path.endsWith(Ljava/nio/file/Path;)Z", instance("endsWith"));
        put(m, "java/nio/file/Path.isAbsolute()Z", instance("isAbsolute"));
        put(m, "java/nio/file/Files.readString(Ljava/nio/file/Path;)Ljava/lang/String;", statics("readString"));
        put(m, "java/nio/file/Files.readString(Ljava/nio/file/Path;Ljava/nio/charset/Charset;)Ljava/lang/String;", statics("readString"));
        put(m, "java/nio/file/Files.writeString(Ljava/nio/file/Path;Ljava/lang/CharSequence;[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;", statics("writeString"));
        put(m, "java/nio/file/Files.writeString(Ljava/nio/file/Path;Ljava/lang/CharSequence;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;", statics("writeString"));
        put(m, "java/nio/file/Files.readAllBytes(Ljava/nio/file/Path;)[B", statics("readAllBytes"));
        put(m, "java/nio/file/Files.write(Ljava/nio/file/Path;[B[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;", statics("write"));
        put(m, "java/nio/file/Files.readAllLines(Ljava/nio/file/Path;)Ljava/util/List;", statics("readAllLines"));
        put(m, "java/nio/file/Files.exists(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", statics("exists"));
        put(m, "java/nio/file/Files.notExists(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", statics("notExists"));
        put(m, "java/nio/file/Files.isDirectory(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", statics("isDirectory"));
        put(m, "java/nio/file/Files.isRegularFile(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", statics("isRegularFile"));
        put(m, "java/nio/file/Files.isReadable(Ljava/nio/file/Path;)Z", statics("isReadable"));
        put(m, "java/nio/file/Files.createDirectories(Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", statics("createDirectories"));
        put(m, "java/nio/file/Files.createDirectory(Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", statics("createDirectory"));
        put(m, "java/nio/file/Files.createFile(Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", statics("createFile"));
        put(m, "java/nio/file/Files.delete(Ljava/nio/file/Path;)V", statics("delete"));
        put(m, "java/nio/file/Files.deleteIfExists(Ljava/nio/file/Path;)Z", statics("deleteIfExists"));
        put(m, "java/nio/file/Files.size(Ljava/nio/file/Path;)J", statics("size"));
        put(m, "java/nio/file/Files.lines(Ljava/nio/file/Path;)Ljava/util/stream/Stream;", statics("lines"));
        put(m, "java/nio/file/Files.list(Ljava/nio/file/Path;)Ljava/util/stream/Stream;", statics("list"));
        put(m, "java/nio/file/Files.copy(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)Ljava/nio/file/Path;", statics("copy"));
        put(m, "java/nio/file/Files.copy(Ljava/io/InputStream;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)J", statics("copy"));
        put(m, "java/nio/file/Files.move(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)Ljava/nio/file/Path;", statics("move"));
        put(m, "java/nio/file/Files.isSameFile(Ljava/nio/file/Path;Ljava/nio/file/Path;)Z", statics("isSameFile"));
        put(m, "java/nio/file/Files.walk(Ljava/nio/file/Path;[Ljava/nio/file/FileVisitOption;)Ljava/util/stream/Stream;", statics("walk"));
        put(m, "java/nio/file/Files.walk(Ljava/nio/file/Path;I[Ljava/nio/file/FileVisitOption;)Ljava/util/stream/Stream;", statics("walk"));
    }

    private static void addNioMethods1(Map<String, ShimTarget> m) {
        put(m, "java/nio/ByteBuffer.allocate(I)Ljava/nio/ByteBuffer;", statics("allocate"));
        put(m, "java/nio/ByteBuffer.allocateDirect(I)Ljava/nio/ByteBuffer;", statics("allocateDirect"));
        put(m, "java/nio/Buffer.isDirect()Z", instance("isDirect"));
        put(m, "java/nio/Buffer.position()I", instance("position"));
        put(m, "java/nio/Buffer.limit()I", instance("limit"));
        put(m, "java/nio/Buffer.capacity()I", instance("capacity"));
        put(m, "java/nio/Buffer.remaining()I", instance("remaining"));
        put(m, "java/nio/Buffer.hasRemaining()Z", instance("hasRemaining"));
        put(m, "sun/misc/Unsafe.objectFieldOffset(Ljava/lang/reflect/Field;)J", instance("objectFieldOffset"));
        put(m, "sun/misc/Unsafe.allocateInstance(Ljava/lang/Class;)Ljava/lang/Object;", instance("allocateInstance"));
        put(m, "sun/misc/Unsafe.pageSize()I", instance("pageSize"));
        put(m, "sun/misc/Unsafe.copyMemory(Ljava/lang/Object;JLjava/lang/Object;JJ)V", instance("copyMemory"));
        put(m, "sun/misc/Unsafe.setMemory(Ljava/lang/Object;JJB)V", instance("setMemory"));
        put(m, "sun/misc/Unsafe.getByte(Ljava/lang/Object;J)B", instance("getByte"));
        put(m, "sun/misc/Unsafe.putByte(Ljava/lang/Object;JB)V", instance("putByte"));
        put(m, "sun/misc/Unsafe.getShort(Ljava/lang/Object;J)S", instance("getShort"));
        put(m, "sun/misc/Unsafe.putShort(Ljava/lang/Object;JS)V", instance("putShort"));
        put(m, "sun/misc/Unsafe.getInt(Ljava/lang/Object;J)I", instance("getInt"));
        put(m, "sun/misc/Unsafe.putInt(Ljava/lang/Object;JI)V", instance("putInt"));
        put(m, "sun/misc/Unsafe.getLong(Ljava/lang/Object;J)J", instance("getLong"));
        put(m, "sun/misc/Unsafe.putLong(Ljava/lang/Object;JJ)V", instance("putLong"));
        put(m, "sun/misc/Unsafe.getFloat(Ljava/lang/Object;J)F", instance("getFloat"));
        put(m, "sun/misc/Unsafe.putFloat(Ljava/lang/Object;JF)V", instance("putFloat"));
        put(m, "sun/misc/Unsafe.getDouble(Ljava/lang/Object;J)D", instance("getDouble"));
        put(m, "sun/misc/Unsafe.putDouble(Ljava/lang/Object;JD)V", instance("putDouble"));
        put(m, "sun/misc/Unsafe.getObject(Ljava/lang/Object;J)Ljava/lang/Object;", instance("getObject"));
        put(m, "sun/misc/Unsafe.putObject(Ljava/lang/Object;JLjava/lang/Object;)V", instance("putObject"));
        put(m, "java/nio/ByteBuffer.wrap([B)Ljava/nio/ByteBuffer;", statics("wrap"));
        put(m, "java/nio/ByteBuffer.capacity()I", instance("capacity"));
        put(m, "java/nio/ByteBuffer.position()I", instance("position"));
        put(m, "java/nio/ByteBuffer.limit()I", instance("limit"));
        put(m, "java/nio/ByteBuffer.remaining()I", instance("remaining"));
        put(m, "java/nio/ByteBuffer.hasRemaining()Z", instance("hasRemaining"));
        put(m, "java/nio/ByteBuffer.position(I)Ljava/nio/ByteBuffer;", instance("position"));
        put(m, "java/nio/ByteBuffer.limit(I)Ljava/nio/ByteBuffer;", instance("limit"));
        put(m, "java/nio/ByteBuffer.position(I)Ljava/nio/Buffer;", instance("position"));
        put(m, "java/nio/ByteBuffer.limit(I)Ljava/nio/Buffer;", instance("limit"));
        put(m, "java/nio/ByteBuffer.mark()Ljava/nio/ByteBuffer;", instance("mark"));
        put(m, "java/nio/ByteBuffer.reset()Ljava/nio/ByteBuffer;", instance("reset"));
        put(m, "java/nio/ByteBuffer.flip()Ljava/nio/ByteBuffer;", instance("flip"));
        put(m, "java/nio/ByteBuffer.clear()Ljava/nio/ByteBuffer;", instance("clear"));
        put(m, "java/nio/ByteBuffer.rewind()Ljava/nio/ByteBuffer;", instance("rewind"));
        put(m, "java/nio/ByteBuffer.get()B", instance("get"));
        put(m, "java/nio/ByteBuffer.get(I)B", instance("get"));
        put(m, "java/nio/ByteBuffer.put(B)Ljava/nio/ByteBuffer;", instance("put"));
        put(m, "java/nio/ByteBuffer.put(IB)Ljava/nio/ByteBuffer;", instance("put"));
        put(m, "java/nio/ByteBuffer.get([B)Ljava/nio/ByteBuffer;", instance("get"));
        put(m, "java/nio/ByteBuffer.get([BII)Ljava/nio/ByteBuffer;", instance("get"));
        put(m, "java/nio/ByteBuffer.put([B)Ljava/nio/ByteBuffer;", instance("put"));
        put(m, "java/nio/ByteBuffer.getInt()I", instance("getInt"));
        put(m, "java/nio/ByteBuffer.putInt(I)Ljava/nio/ByteBuffer;", instance("putInt"));
        put(m, "java/nio/ByteBuffer.getLong()J", instance("getLong"));
        put(m, "java/nio/ByteBuffer.putLong(J)Ljava/nio/ByteBuffer;", instance("putLong"));
        put(m, "java/nio/ByteBuffer.array()[B", instance("array"));
    }

    private static void addNioMethods2(Map<String, ShimTarget> m) {
        put(m, "java/io/File.getName()Ljava/lang/String;", instance("getName"));
        put(m, "java/io/File.getParent()Ljava/lang/String;", instance("getParent"));
        put(m, "java/io/File.getParentFile()Ljava/io/File;", instance("getParentFile"));
        put(m, "java/io/File.getPath()Ljava/lang/String;", instance("getPath"));
        put(m, "java/io/File.getAbsolutePath()Ljava/lang/String;", instance("getAbsolutePath"));
        put(m, "java/io/File.getAbsoluteFile()Ljava/io/File;", instance("getAbsoluteFile"));
        put(m, "java/io/File.getCanonicalPath()Ljava/lang/String;", instance("getCanonicalPath"));
        put(m, "java/io/File.exists()Z", instance("exists"));
        put(m, "java/io/File.isDirectory()Z", instance("isDirectory"));
        put(m, "java/io/File.isFile()Z", instance("isFile"));
        put(m, "java/io/File.isAbsolute()Z", instance("isAbsolute"));
        put(m, "java/io/File.mkdir()Z", instance("mkdir"));
        put(m, "java/io/File.mkdirs()Z", instance("mkdirs"));
        put(m, "java/io/File.createNewFile()Z", instance("createNewFile"));
        put(m, "java/io/File.delete()Z", instance("delete"));
        put(m, "java/io/File.renameTo(Ljava/io/File;)Z", instance("renameTo"));
        put(m, "java/io/File.length()J", instance("length"));
        put(m, "java/io/File.lastModified()J", instance("lastModified"));
        put(m, "java/io/File.setLastModified(J)Z", instance("setLastModified"));
        put(m, "java/io/File.canRead()Z", instance("canRead"));
        put(m, "java/io/File.canWrite()Z", instance("canWrite"));
        put(m, "java/io/File.list()[Ljava/lang/String;", instance("list"));
        put(m, "java/io/File.listFiles()[Ljava/io/File;", instance("listFiles"));
        put(m, "java/io/File.toPath()Ljava/nio/file/Path;", instance("toPath"));
        put(m, "java/nio/file/Path.toFile()Ljava/io/File;", instance("toFile"));
        put(m, "java/nio/file/Path.iterator()Ljava/util/Iterator;", instance("iterator"));
    }

    private static void addNioMethods3(Map<String, ShimTarget> m) {
        put(m, "java/nio/ByteOrder.nativeOrder()Ljava/nio/ByteOrder;", statics("nativeOrder"));
        put(m, "java/nio/ByteBuffer.order()Ljava/nio/ByteOrder;", instance("order"));
        put(m, "java/nio/ByteBuffer.order(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;", instance("order"));
        put(m, "java/nio/ByteBuffer.duplicate()Ljava/nio/ByteBuffer;", instance("duplicate"));
        put(m, "java/nio/ByteBuffer.compact()Ljava/nio/ByteBuffer;", instance("compact"));
        put(m, "java/nio/ByteBuffer.getShort()S", instance("getShort"));
        put(m, "java/nio/ByteBuffer.getShort(I)S", instance("getShort"));
        put(m, "java/nio/ByteBuffer.putShort(S)Ljava/nio/ByteBuffer;", instance("putShort"));
        put(m, "java/nio/ByteBuffer.putShort(IS)Ljava/nio/ByteBuffer;", instance("putShort"));
        put(m, "java/nio/ByteBuffer.getChar()C", instance("getChar"));
        put(m, "java/nio/ByteBuffer.getChar(I)C", instance("getChar"));
        put(m, "java/nio/ByteBuffer.putChar(C)Ljava/nio/ByteBuffer;", instance("putChar"));
        put(m, "java/nio/ByteBuffer.putChar(IC)Ljava/nio/ByteBuffer;", instance("putChar"));
        put(m, "java/nio/ByteBuffer.getInt(I)I", instance("getInt"));
        put(m, "java/nio/ByteBuffer.putInt(II)Ljava/nio/ByteBuffer;", instance("putInt"));
        put(m, "java/nio/ByteBuffer.getLong(I)J", instance("getLong"));
        put(m, "java/nio/ByteBuffer.putLong(IJ)Ljava/nio/ByteBuffer;", instance("putLong"));
        put(m, "java/nio/ByteBuffer.getFloat()F", instance("getFloat"));
        put(m, "java/nio/ByteBuffer.getFloat(I)F", instance("getFloat"));
        put(m, "java/nio/ByteBuffer.putFloat(F)Ljava/nio/ByteBuffer;", instance("putFloat"));
        put(m, "java/nio/ByteBuffer.putFloat(IF)Ljava/nio/ByteBuffer;", instance("putFloat"));
        put(m, "java/nio/ByteBuffer.getDouble()D", instance("getDouble"));
        put(m, "java/nio/ByteBuffer.getDouble(I)D", instance("getDouble"));
        put(m, "java/nio/ByteBuffer.putDouble(D)Ljava/nio/ByteBuffer;", instance("putDouble"));
        put(m, "java/nio/ByteBuffer.putDouble(ID)Ljava/nio/ByteBuffer;", instance("putDouble"));
    }

    private static void addNioMethods4(Map<String, ShimTarget> m) {
        put(m, "java/nio/IntBuffer.allocate(I)Ljava/nio/IntBuffer;", statics("allocate"));
        put(m, "java/nio/IntBuffer.wrap([I)Ljava/nio/IntBuffer;", statics("wrap"));
        put(m, "java/nio/IntBuffer.capacity()I", instance("capacity"));
        put(m, "java/nio/IntBuffer.position()I", instance("position"));
        put(m, "java/nio/IntBuffer.limit()I", instance("limit"));
        put(m, "java/nio/IntBuffer.remaining()I", instance("remaining"));
        put(m, "java/nio/IntBuffer.hasRemaining()Z", instance("hasRemaining"));
        put(m, "java/nio/IntBuffer.position(I)Ljava/nio/IntBuffer;", instance("position"));
        put(m, "java/nio/IntBuffer.limit(I)Ljava/nio/IntBuffer;", instance("limit"));
        put(m, "java/nio/IntBuffer.mark()Ljava/nio/IntBuffer;", instance("mark"));
        put(m, "java/nio/IntBuffer.reset()Ljava/nio/IntBuffer;", instance("reset"));
        put(m, "java/nio/IntBuffer.flip()Ljava/nio/IntBuffer;", instance("flip"));
        put(m, "java/nio/IntBuffer.clear()Ljava/nio/IntBuffer;", instance("clear"));
        put(m, "java/nio/IntBuffer.rewind()Ljava/nio/IntBuffer;", instance("rewind"));
        put(m, "java/nio/IntBuffer.get()I", instance("get"));
        put(m, "java/nio/IntBuffer.get(I)I", instance("get"));
        put(m, "java/nio/IntBuffer.put(I)Ljava/nio/IntBuffer;", instance("put"));
        put(m, "java/nio/IntBuffer.put(II)Ljava/nio/IntBuffer;", instance("put"));
        put(m, "java/nio/IntBuffer.get([I)Ljava/nio/IntBuffer;", instance("get"));
        put(m, "java/nio/IntBuffer.put([I)Ljava/nio/IntBuffer;", instance("put"));
        put(m, "java/nio/IntBuffer.array()[I", instance("array"));
    }

    private static void addNioMethods5(Map<String, ShimTarget> m) {
        put(m, "java/nio/LongBuffer.allocate(I)Ljava/nio/LongBuffer;", statics("allocate"));
        put(m, "java/nio/LongBuffer.wrap([J)Ljava/nio/LongBuffer;", statics("wrap"));
        put(m, "java/nio/LongBuffer.capacity()I", instance("capacity"));
        put(m, "java/nio/LongBuffer.position()I", instance("position"));
        put(m, "java/nio/LongBuffer.limit()I", instance("limit"));
        put(m, "java/nio/LongBuffer.remaining()I", instance("remaining"));
        put(m, "java/nio/LongBuffer.hasRemaining()Z", instance("hasRemaining"));
        put(m, "java/nio/LongBuffer.position(I)Ljava/nio/LongBuffer;", instance("position"));
        put(m, "java/nio/LongBuffer.limit(I)Ljava/nio/LongBuffer;", instance("limit"));
        put(m, "java/nio/LongBuffer.mark()Ljava/nio/LongBuffer;", instance("mark"));
        put(m, "java/nio/LongBuffer.reset()Ljava/nio/LongBuffer;", instance("reset"));
        put(m, "java/nio/LongBuffer.flip()Ljava/nio/LongBuffer;", instance("flip"));
        put(m, "java/nio/LongBuffer.clear()Ljava/nio/LongBuffer;", instance("clear"));
        put(m, "java/nio/LongBuffer.rewind()Ljava/nio/LongBuffer;", instance("rewind"));
        put(m, "java/nio/LongBuffer.get()J", instance("get"));
        put(m, "java/nio/LongBuffer.get(I)J", instance("get"));
        put(m, "java/nio/LongBuffer.put(J)Ljava/nio/LongBuffer;", instance("put"));
        put(m, "java/nio/LongBuffer.put(IJ)Ljava/nio/LongBuffer;", instance("put"));
        put(m, "java/nio/LongBuffer.get([J)Ljava/nio/LongBuffer;", instance("get"));
        put(m, "java/nio/LongBuffer.put([J)Ljava/nio/LongBuffer;", instance("put"));
        put(m, "java/nio/LongBuffer.array()[J", instance("array"));
    }

    private static void addNioMethods6(Map<String, ShimTarget> m) {
        put(m, "java/nio/DoubleBuffer.allocate(I)Ljava/nio/DoubleBuffer;", statics("allocate"));
        put(m, "java/nio/DoubleBuffer.wrap([D)Ljava/nio/DoubleBuffer;", statics("wrap"));
        put(m, "java/nio/DoubleBuffer.capacity()I", instance("capacity"));
        put(m, "java/nio/DoubleBuffer.position()I", instance("position"));
        put(m, "java/nio/DoubleBuffer.limit()I", instance("limit"));
        put(m, "java/nio/DoubleBuffer.remaining()I", instance("remaining"));
        put(m, "java/nio/DoubleBuffer.hasRemaining()Z", instance("hasRemaining"));
        put(m, "java/nio/DoubleBuffer.position(I)Ljava/nio/DoubleBuffer;", instance("position"));
        put(m, "java/nio/DoubleBuffer.limit(I)Ljava/nio/DoubleBuffer;", instance("limit"));
        put(m, "java/nio/DoubleBuffer.mark()Ljava/nio/DoubleBuffer;", instance("mark"));
        put(m, "java/nio/DoubleBuffer.reset()Ljava/nio/DoubleBuffer;", instance("reset"));
        put(m, "java/nio/DoubleBuffer.flip()Ljava/nio/DoubleBuffer;", instance("flip"));
        put(m, "java/nio/DoubleBuffer.clear()Ljava/nio/DoubleBuffer;", instance("clear"));
        put(m, "java/nio/DoubleBuffer.rewind()Ljava/nio/DoubleBuffer;", instance("rewind"));
        put(m, "java/nio/DoubleBuffer.get()D", instance("get"));
        put(m, "java/nio/DoubleBuffer.get(I)D", instance("get"));
        put(m, "java/nio/DoubleBuffer.put(D)Ljava/nio/DoubleBuffer;", instance("put"));
        put(m, "java/nio/DoubleBuffer.put(ID)Ljava/nio/DoubleBuffer;", instance("put"));
        put(m, "java/nio/DoubleBuffer.get([D)Ljava/nio/DoubleBuffer;", instance("get"));
        put(m, "java/nio/DoubleBuffer.put([D)Ljava/nio/DoubleBuffer;", instance("put"));
        put(m, "java/nio/DoubleBuffer.array()[D", instance("array"));
    }

    private static void addNioMethods7(Map<String, ShimTarget> m) {
        put(m, "java/nio/CharBuffer.allocate(I)Ljava/nio/CharBuffer;", statics("allocate"));
        put(m, "java/nio/CharBuffer.wrap([C)Ljava/nio/CharBuffer;", statics("wrap"));
        put(m, "java/nio/CharBuffer.capacity()I", instance("capacity"));
        put(m, "java/nio/CharBuffer.position()I", instance("position"));
        put(m, "java/nio/CharBuffer.limit()I", instance("limit"));
        put(m, "java/nio/CharBuffer.remaining()I", instance("remaining"));
        put(m, "java/nio/CharBuffer.hasRemaining()Z", instance("hasRemaining"));
        put(m, "java/nio/CharBuffer.position(I)Ljava/nio/CharBuffer;", instance("position"));
        put(m, "java/nio/CharBuffer.limit(I)Ljava/nio/CharBuffer;", instance("limit"));
        put(m, "java/nio/CharBuffer.mark()Ljava/nio/CharBuffer;", instance("mark"));
        put(m, "java/nio/CharBuffer.reset()Ljava/nio/CharBuffer;", instance("reset"));
        put(m, "java/nio/CharBuffer.flip()Ljava/nio/CharBuffer;", instance("flip"));
        put(m, "java/nio/CharBuffer.clear()Ljava/nio/CharBuffer;", instance("clear"));
        put(m, "java/nio/CharBuffer.rewind()Ljava/nio/CharBuffer;", instance("rewind"));
        put(m, "java/nio/CharBuffer.get()C", instance("get"));
        put(m, "java/nio/CharBuffer.get(I)C", instance("get"));
        put(m, "java/nio/CharBuffer.put(C)Ljava/nio/CharBuffer;", instance("put"));
        put(m, "java/nio/CharBuffer.put(IC)Ljava/nio/CharBuffer;", instance("put"));
        put(m, "java/nio/CharBuffer.get([C)Ljava/nio/CharBuffer;", instance("get"));
        put(m, "java/nio/CharBuffer.put([C)Ljava/nio/CharBuffer;", instance("put"));
        put(m, "java/nio/CharBuffer.array()[C", instance("array"));
    }


    private static void addLangMethods1(Map<String, ShimTarget> m) {
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
        put(m, "java/lang/Long.sum(JJ)J", statics("sum"));
        put(m, "java/lang/Long.max(JJ)J", statics("max"));
        put(m, "java/lang/Long.min(JJ)J", statics("min"));
        put(m, "java/lang/Double.sum(DD)D", statics("sum"));
        put(m, "java/lang/Double.max(DD)D", statics("max"));
        put(m, "java/lang/Double.min(DD)D", statics("min"));
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
        put(m, "java/lang/Throwable.addSuppressed(Ljava/lang/Throwable;)V", instance("addSuppressed"));
        put(m, "java/nio/channels/FileChannel.close()V", instance("close"));
        put(m, "java/lang/Throwable.getSuppressed()[Ljava/lang/Throwable;", instance("getSuppressed"));
        put(m, "java/lang/AutoCloseable.close()V", instance("close"));
        put(m, "java/lang/Throwable.toString()Ljava/lang/String;", instance("toString"));
        put(m, "java/lang/Throwable.printStackTrace()V", instance("printStackTrace"));
        put(m, "java/lang/Throwable.printStackTrace(Ljava/io/PrintStream;)V", instance("printStackTrace"));
        put(m, "java/lang/String.format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", statics("format"));
        put(m, "java/lang/System.nanoTime()J", statics("nanoTime"));
        put(m, "java/lang/System.currentTimeMillis()J", statics("currentTimeMillis"));
    }

    private static void addLangMethods4(Map<String, ShimTarget> m) {
        put(m, "java/lang/System.exit(I)V", statics("exit"));
        put(m, "java/lang/System.load(Ljava/lang/String;)V", statics("load"));
        put(m, "java/lang/System.loadLibrary(Ljava/lang/String;)V", statics("loadLibrary"));
        put(m, "java/lang/System.mapLibraryName(Ljava/lang/String;)Ljava/lang/String;", statics("mapLibraryName"));
    }

    private static void addUtilMethods0(Map<String, ShimTarget> m) {
        put(m, "java/util/UUID.randomUUID()Ljava/util/UUID;", statics("randomUUID"));
        put(m, "java/util/UUID.toString()Ljava/lang/String;", instance("toString"));
    }

    private static void addLangMethods5(Map<String, ShimTarget> m) {
        put(m, "java/lang/Thread.currentThread()Ljava/lang/Thread;", statics("currentThread"));
        put(m, "java/lang/Thread.getContextClassLoader()Ljava/lang/ClassLoader;", instance("getContextClassLoader"));
        put(m, "java/lang/Thread.setContextClassLoader(Ljava/lang/ClassLoader;)V", instance("setContextClassLoader"));
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
        put(m, "java/lang/Class.forName(Ljava/lang/String;)Ljava/lang/Class;", statics("forName"));
        put(m, "java/lang/Class.getClassLoader()Ljava/lang/ClassLoader;", instance("getClassLoader"));
        put(m, "java/lang/Class.getResourceAsStream(Ljava/lang/String;)Ljava/io/InputStream;", instance("getResourceAsStream"));
        put(m, "java/lang/Class.getResource(Ljava/lang/String;)Ljava/net/URL;", instance("getResource"));
        put(m, "java/lang/ClassLoader.getResource(Ljava/lang/String;)Ljava/net/URL;", instance("getResource"));
        put(m, "java/lang/ClassLoader.getResourceAsStream(Ljava/lang/String;)Ljava/io/InputStream;", instance("getResourceAsStream"));
        put(m, "java/lang/ClassLoader.getResources(Ljava/lang/String;)Ljava/util/Enumeration;", instance("getResources"));
        put(m, "java/util/Enumeration.hasMoreElements()Z", instance("hasMoreElements"));
        put(m, "java/util/Enumeration.nextElement()Ljava/lang/Object;", instance("nextElement"));
        put(m, "java/lang/Class.getName()Ljava/lang/String;", instance("getName"));
        put(m, "java/lang/Class.getSimpleName()Ljava/lang/String;", instance("getSimpleName"));
        put(m, "java/lang/Class.getCanonicalName()Ljava/lang/String;", instance("getCanonicalName"));
        put(m, "java/lang/Class.isArray()Z", instance("isArray"));
        put(m, "java/lang/Class.getComponentType()Ljava/lang/Class;", instance("getComponentType"));
        put(m, "java/lang/Class.getSuperclass()Ljava/lang/Class;", instance("getSuperclass"));
        put(m, "java/lang/Class.getDeclaredFields()[Ljava/lang/reflect/Field;", instance("getDeclaredFields"));
        put(m, "java/lang/Class.getDeclaredMethods()[Ljava/lang/reflect/Method;", instance("getDeclaredMethods"));
        put(m, "java/lang/Class.getDeclaredConstructors()[Ljava/lang/reflect/Constructor;", instance("getDeclaredConstructors"));
        put(m, "java/lang/Class.getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;", instance("getConstructor"));
        put(m, "java/lang/Class.getDeclaredConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;", instance("getDeclaredConstructor"));
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
        put(m, "java/lang/reflect/Field.isSynthetic()Z", instance("isSynthetic"));
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
        put(m, "java/util/function/LongConsumer.accept(J)V", instance("accept"));
        put(m, "java/util/function/LongPredicate.test(J)Z", instance("test"));
        put(m, "java/util/function/LongUnaryOperator.applyAsLong(J)J", instance("applyAsLong"));
        put(m, "java/util/function/LongBinaryOperator.applyAsLong(JJ)J", instance("applyAsLong"));
        put(m, "java/util/function/LongFunction.apply(J)Ljava/lang/Object;", instance("apply"));
        put(m, "java/util/function/LongToIntFunction.applyAsInt(J)I", instance("applyAsInt"));
        put(m, "java/util/function/LongToDoubleFunction.applyAsDouble(J)D", instance("applyAsDouble"));
        put(m, "java/util/function/DoubleUnaryOperator.applyAsDouble(D)D", instance("applyAsDouble"));
        put(m, "java/util/function/DoubleBinaryOperator.applyAsDouble(DD)D", instance("applyAsDouble"));
        put(m, "java/util/function/DoubleFunction.apply(D)Ljava/lang/Object;", instance("apply"));
        put(m, "java/util/function/DoubleToIntFunction.applyAsInt(D)I", instance("applyAsInt"));
        put(m, "java/util/function/DoubleToLongFunction.applyAsLong(D)J", instance("applyAsLong"));
        put(m, "java/util/function/IntToLongFunction.applyAsLong(I)J", instance("applyAsLong"));
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
        put(m, "java/util/OptionalLong.getAsLong()J", instance("getAsLong"));
        put(m, "java/util/OptionalLong.isPresent()Z", instance("isPresent"));
        put(m, "java/util/OptionalLong.isEmpty()Z", instance("isEmpty"));
        put(m, "java/util/OptionalLong.orElse(J)J", instance("orElse"));
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
        put(m, "java/util/Arrays.copyOf([Ljava/lang/Object;ILjava/lang/Class;)[Ljava/lang/Object;", statics("copyOf"));
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
        put(m, "java/util/stream/IntStream.peek(Ljava/util/function/IntConsumer;)Ljava/util/stream/IntStream;", instance("peek"));
        put(m, "java/util/stream/IntStream.mapToLong(Ljava/util/function/IntToLongFunction;)Ljava/util/stream/LongStream;", instance("mapToLong"));
        put(m, "java/util/stream/IntStream.asLongStream()Ljava/util/stream/LongStream;", instance("asLongStream"));
        put(m, "java/util/stream/IntStream.asDoubleStream()Ljava/util/stream/DoubleStream;", instance("asDoubleStream"));
        put(m, "java/util/stream/IntStream.reduce(Ljava/util/function/IntBinaryOperator;)Ljava/util/OptionalInt;", instance("reduce"));
        put(m, "java/util/stream/IntStream.findFirst()Ljava/util/OptionalInt;", instance("findFirst"));
        put(m, "java/util/stream/IntStream.summaryStatistics()Ljava/util/IntSummaryStatistics;", instance("summaryStatistics"));
        put(m, "java/util/stream/LongStream.range(JJ)Ljava/util/stream/LongStream;", statics("range"));
        put(m, "java/util/stream/LongStream.rangeClosed(JJ)Ljava/util/stream/LongStream;", statics("rangeClosed"));
        put(m, "java/util/stream/LongStream.of([J)Ljava/util/stream/LongStream;", statics("of"));
        put(m, "java/util/stream/LongStream.parallel()Ljava/util/stream/LongStream;", instance("parallel"));
        put(m, "java/util/stream/LongStream.sequential()Ljava/util/stream/LongStream;", instance("sequential"));
        put(m, "java/util/stream/LongStream.boxed()Ljava/util/stream/Stream;", instance("boxed"));
        put(m, "java/util/stream/LongStream.mapToObj(Ljava/util/function/LongFunction;)Ljava/util/stream/Stream;", instance("mapToObj"));
        put(m, "java/util/stream/LongStream.mapToInt(Ljava/util/function/LongToIntFunction;)Ljava/util/stream/IntStream;", instance("mapToInt"));
        put(m, "java/util/stream/LongStream.mapToDouble(Ljava/util/function/LongToDoubleFunction;)Ljava/util/stream/DoubleStream;", instance("mapToDouble"));
        put(m, "java/util/stream/LongStream.asDoubleStream()Ljava/util/stream/DoubleStream;", instance("asDoubleStream"));
        put(m, "java/util/stream/LongStream.forEach(Ljava/util/function/LongConsumer;)V", instance("forEach"));
        put(m, "java/util/stream/LongStream.peek(Ljava/util/function/LongConsumer;)Ljava/util/stream/LongStream;", instance("peek"));
        put(m, "java/util/stream/LongStream.filter(Ljava/util/function/LongPredicate;)Ljava/util/stream/LongStream;", instance("filter"));
        put(m, "java/util/stream/LongStream.map(Ljava/util/function/LongUnaryOperator;)Ljava/util/stream/LongStream;", instance("map"));
        put(m, "java/util/stream/LongStream.reduce(JLjava/util/function/LongBinaryOperator;)J", instance("reduce"));
        put(m, "java/util/stream/LongStream.reduce(Ljava/util/function/LongBinaryOperator;)Ljava/util/OptionalLong;", instance("reduce"));
        put(m, "java/util/stream/LongStream.sorted()Ljava/util/stream/LongStream;", instance("sorted"));
        put(m, "java/util/stream/LongStream.distinct()Ljava/util/stream/LongStream;", instance("distinct"));
        put(m, "java/util/stream/LongStream.limit(J)Ljava/util/stream/LongStream;", instance("limit"));
        put(m, "java/util/stream/LongStream.skip(J)Ljava/util/stream/LongStream;", instance("skip"));
        put(m, "java/util/stream/LongStream.anyMatch(Ljava/util/function/LongPredicate;)Z", instance("anyMatch"));
        put(m, "java/util/stream/LongStream.allMatch(Ljava/util/function/LongPredicate;)Z", instance("allMatch"));
        put(m, "java/util/stream/LongStream.noneMatch(Ljava/util/function/LongPredicate;)Z", instance("noneMatch"));
        put(m, "java/util/stream/LongStream.min()Ljava/util/OptionalLong;", instance("min"));
        put(m, "java/util/stream/LongStream.max()Ljava/util/OptionalLong;", instance("max"));
        put(m, "java/util/stream/LongStream.average()Ljava/util/OptionalDouble;", instance("average"));
        put(m, "java/util/stream/LongStream.findFirst()Ljava/util/OptionalLong;", instance("findFirst"));
        put(m, "java/util/stream/LongStream.summaryStatistics()Ljava/util/LongSummaryStatistics;", instance("summaryStatistics"));
        put(m, "java/util/stream/LongStream.sum()J", instance("sum"));
        put(m, "java/util/stream/LongStream.count()J", instance("count"));
        put(m, "java/util/stream/LongStream.toArray()[J", instance("toArray"));
        put(m, "java/util/stream/DoubleStream.of([D)Ljava/util/stream/DoubleStream;", statics("of"));
        put(m, "java/util/stream/DoubleStream.parallel()Ljava/util/stream/DoubleStream;", instance("parallel"));
        put(m, "java/util/stream/DoubleStream.sequential()Ljava/util/stream/DoubleStream;", instance("sequential"));
        put(m, "java/util/stream/DoubleStream.boxed()Ljava/util/stream/Stream;", instance("boxed"));
        put(m, "java/util/stream/DoubleStream.mapToObj(Ljava/util/function/DoubleFunction;)Ljava/util/stream/Stream;", instance("mapToObj"));
        put(m, "java/util/stream/DoubleStream.mapToInt(Ljava/util/function/DoubleToIntFunction;)Ljava/util/stream/IntStream;", instance("mapToInt"));
        put(m, "java/util/stream/DoubleStream.mapToLong(Ljava/util/function/DoubleToLongFunction;)Ljava/util/stream/LongStream;", instance("mapToLong"));
        put(m, "java/util/stream/DoubleStream.forEach(Ljava/util/function/DoubleConsumer;)V", instance("forEach"));
        put(m, "java/util/stream/DoubleStream.peek(Ljava/util/function/DoubleConsumer;)Ljava/util/stream/DoubleStream;", instance("peek"));
        put(m, "java/util/stream/DoubleStream.filter(Ljava/util/function/DoublePredicate;)Ljava/util/stream/DoubleStream;", instance("filter"));
        put(m, "java/util/stream/DoubleStream.map(Ljava/util/function/DoubleUnaryOperator;)Ljava/util/stream/DoubleStream;", instance("map"));
        put(m, "java/util/stream/DoubleStream.reduce(DLjava/util/function/DoubleBinaryOperator;)D", instance("reduce"));
        put(m, "java/util/stream/DoubleStream.reduce(Ljava/util/function/DoubleBinaryOperator;)Ljava/util/OptionalDouble;", instance("reduce"));
        put(m, "java/util/stream/DoubleStream.sorted()Ljava/util/stream/DoubleStream;", instance("sorted"));
        put(m, "java/util/stream/DoubleStream.distinct()Ljava/util/stream/DoubleStream;", instance("distinct"));
        put(m, "java/util/stream/DoubleStream.limit(J)Ljava/util/stream/DoubleStream;", instance("limit"));
        put(m, "java/util/stream/DoubleStream.skip(J)Ljava/util/stream/DoubleStream;", instance("skip"));
        put(m, "java/util/stream/DoubleStream.anyMatch(Ljava/util/function/DoublePredicate;)Z", instance("anyMatch"));
        put(m, "java/util/stream/DoubleStream.allMatch(Ljava/util/function/DoublePredicate;)Z", instance("allMatch"));
        put(m, "java/util/stream/DoubleStream.noneMatch(Ljava/util/function/DoublePredicate;)Z", instance("noneMatch"));
        put(m, "java/util/stream/DoubleStream.min()Ljava/util/OptionalDouble;", instance("min"));
        put(m, "java/util/stream/DoubleStream.max()Ljava/util/OptionalDouble;", instance("max"));
        put(m, "java/util/stream/DoubleStream.average()Ljava/util/OptionalDouble;", instance("average"));
        put(m, "java/util/stream/DoubleStream.findFirst()Ljava/util/OptionalDouble;", instance("findFirst"));
        put(m, "java/util/stream/DoubleStream.summaryStatistics()Ljava/util/DoubleSummaryStatistics;", instance("summaryStatistics"));
        put(m, "java/util/stream/DoubleStream.sum()D", instance("sum"));
        put(m, "java/util/stream/DoubleStream.count()J", instance("count"));
        put(m, "java/util/stream/DoubleStream.toArray()[D", instance("toArray"));
    }

    private static void addUtilMethods5(Map<String, ShimTarget> m) {
        put(m, "java/util/List.clear()V", instance("clear"));
        put(m, "java/util/List.removeIf(Ljava/util/function/Predicate;)Z", instance("removeIf"));
        put(m, "java/util/List.sort(Ljava/util/Comparator;)V", instance("sort"));
    }


    private static void addUtilMethods6(Map<String, ShimTarget> m) {
        put(m, "java/util/Objects.requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;", statics("requireNonNull"));
        put(m, "java/lang/Iterable.iterator()Ljava/util/Iterator;", instance("iterator"));
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
        put(m, "java/util/Collection.addAll(Ljava/util/Collection;)Z", instance("addAll"));
        put(m, "java/util/Collection.containsAll(Ljava/util/Collection;)Z", instance("containsAll"));
        put(m, "java/util/Collection.removeAll(Ljava/util/Collection;)Z", instance("removeAll"));
        put(m, "java/util/Collection.retainAll(Ljava/util/Collection;)Z", instance("retainAll"));
        put(m, "java/util/Collection.toArray()[Ljava/lang/Object;", instance("toArray"));
        put(m, "java/util/Collection.toArray([Ljava/lang/Object;)[Ljava/lang/Object;", instance("toArray"));
        put(m, "java/util/List.get(I)Ljava/lang/Object;", instance("get"));
        put(m, "java/util/List.set(ILjava/lang/Object;)Ljava/lang/Object;", instance("set"));
        put(m, "java/util/List.add(ILjava/lang/Object;)V", instance("add"));
        put(m, "java/util/List.remove(I)Ljava/lang/Object;", instance("remove"));
        put(m, "java/util/List.indexOf(Ljava/lang/Object;)I", instance("indexOf"));
        put(m, "java/util/List.addAll(ILjava/util/Collection;)Z", instance("addAll"));
        put(m, "java/util/Map.put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", instance("put"));
        put(m, "java/util/Map.get(Ljava/lang/Object;)Ljava/lang/Object;", instance("get"));
        put(m, "java/util/Map.containsKey(Ljava/lang/Object;)Z", instance("containsKey"));
        put(m, "java/util/Map.remove(Ljava/lang/Object;)Ljava/lang/Object;", instance("remove"));
        put(m, "java/util/Map.size()I", instance("size"));
        put(m, "java/util/Map.isEmpty()Z", instance("isEmpty"));
        put(m, "java/util/Map.keySet()Ljava/util/Set;", instance("keySet"));
        put(m, "java/util/Map.putAll(Ljava/util/Map;)V", instance("putAll"));
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

    private static void addIoStreams0(Map<String, ShimTarget> m) {
        put(m, "java/io/InputStream.read()I", instance("read"));
        put(m, "java/io/InputStream.read([B)I", instance("read"));
        put(m, "java/io/InputStream.read([BII)I", instance("read"));
        put(m, "java/io/InputStream.available()I", instance("available"));
        put(m, "java/io/InputStream.skip(J)J", instance("skip"));
        put(m, "java/io/InputStream.readAllBytes()[B", instance("readAllBytes"));
        put(m, "java/io/InputStream.close()V", instance("close"));
        put(m, "java/io/OutputStream.write(I)V", instance("write"));
        put(m, "java/io/OutputStream.write([B)V", instance("write"));
        put(m, "java/io/OutputStream.write([BII)V", instance("write"));
        put(m, "java/io/OutputStream.flush()V", instance("flush"));
        put(m, "java/io/OutputStream.close()V", instance("close"));
        put(m, "java/io/Reader.read()I", instance("read"));
        put(m, "java/io/Reader.read([C)I", instance("read"));
        put(m, "java/io/Reader.read([CII)I", instance("read"));
        put(m, "java/io/Reader.close()V", instance("close"));
        put(m, "java/io/Writer.write(I)V", instance("write"));
        put(m, "java/io/Writer.write([C)V", instance("write"));
        put(m, "java/io/Writer.write([CII)V", instance("write"));
        put(m, "java/io/Writer.write(Ljava/lang/String;)V", instance("write"));
        put(m, "java/io/Writer.append(Ljava/lang/CharSequence;)Ljava/io/Writer;", instance("append"));
        put(m, "java/io/Writer.append(C)Ljava/io/Writer;", instance("append"));
        put(m, "java/io/Writer.flush()V", instance("flush"));
        put(m, "java/io/Writer.close()V", instance("close"));
        put(m, "java/io/ByteArrayOutputStream.toByteArray()[B", instance("toByteArray"));
        put(m, "java/io/ByteArrayOutputStream.size()I", instance("size"));
        put(m, "java/io/ByteArrayOutputStream.reset()V", instance("reset"));
        put(m, "java/io/ByteArrayOutputStream.writeTo(Ljava/io/OutputStream;)V", instance("writeTo"));
        put(m, "java/io/BufferedReader.readLine()Ljava/lang/String;", instance("readLine"));
        put(m, "java/io/BufferedReader.lines()Ljava/util/stream/Stream;", instance("lines"));
        put(m, "java/io/BufferedWriter.newLine()V", instance("newLine"));
    }

    private static void addIoStreams1(Map<String, ShimTarget> m) {
        put(m, "java/io/PrintWriter.print(Ljava/lang/String;)V", instance("print"));
        put(m, "java/io/PrintWriter.print(Ljava/lang/Object;)V", instance("print"));
        put(m, "java/io/PrintWriter.print(I)V", instance("print"));
        put(m, "java/io/PrintWriter.print(J)V", instance("print"));
        put(m, "java/io/PrintWriter.print(C)V", instance("print"));
        put(m, "java/io/PrintWriter.print(D)V", instance("print"));
        put(m, "java/io/PrintWriter.print(Z)V", instance("print_Z"));
        put(m, "java/io/PrintWriter.println()V", instance("println"));
        put(m, "java/io/PrintWriter.println(Ljava/lang/String;)V", instance("println"));
        put(m, "java/io/PrintWriter.println(Ljava/lang/Object;)V", instance("println"));
        put(m, "java/io/PrintWriter.println(I)V", instance("println"));
        put(m, "java/io/PrintWriter.println(J)V", instance("println"));
        put(m, "java/io/PrintWriter.println(C)V", instance("println"));
        put(m, "java/io/PrintWriter.println(D)V", instance("println"));
        put(m, "java/io/PrintWriter.println(Z)V", instance("println_Z"));
        put(m, "java/io/PrintWriter.printf(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintWriter;", instance("printf"));
        put(m, "java/io/PrintWriter.format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintWriter;", instance("format"));
    }

    private static void addNioMethods8(Map<String, ShimTarget> m) {
        put(m, "java/nio/ShortBuffer.allocate(I)Ljava/nio/ShortBuffer;", statics("allocate"));
        put(m, "java/nio/ShortBuffer.wrap([S)Ljava/nio/ShortBuffer;", statics("wrap"));
        put(m, "java/nio/ShortBuffer.capacity()I", instance("capacity"));
        put(m, "java/nio/ShortBuffer.position()I", instance("position"));
        put(m, "java/nio/ShortBuffer.limit()I", instance("limit"));
        put(m, "java/nio/ShortBuffer.remaining()I", instance("remaining"));
        put(m, "java/nio/ShortBuffer.hasRemaining()Z", instance("hasRemaining"));
        put(m, "java/nio/ShortBuffer.position(I)Ljava/nio/ShortBuffer;", instance("position"));
        put(m, "java/nio/ShortBuffer.limit(I)Ljava/nio/ShortBuffer;", instance("limit"));
        put(m, "java/nio/ShortBuffer.mark()Ljava/nio/ShortBuffer;", instance("mark"));
        put(m, "java/nio/ShortBuffer.reset()Ljava/nio/ShortBuffer;", instance("reset"));
        put(m, "java/nio/ShortBuffer.flip()Ljava/nio/ShortBuffer;", instance("flip"));
        put(m, "java/nio/ShortBuffer.clear()Ljava/nio/ShortBuffer;", instance("clear"));
        put(m, "java/nio/ShortBuffer.rewind()Ljava/nio/ShortBuffer;", instance("rewind"));
        put(m, "java/nio/ShortBuffer.get()S", instance("get"));
        put(m, "java/nio/ShortBuffer.get(I)S", instance("get"));
        put(m, "java/nio/ShortBuffer.put(S)Ljava/nio/ShortBuffer;", instance("put"));
        put(m, "java/nio/ShortBuffer.put(IS)Ljava/nio/ShortBuffer;", instance("put"));
        put(m, "java/nio/ShortBuffer.get([S)Ljava/nio/ShortBuffer;", instance("get"));
        put(m, "java/nio/ShortBuffer.put([S)Ljava/nio/ShortBuffer;", instance("put"));
        put(m, "java/nio/ShortBuffer.array()[S", instance("array"));
        put(m, "java/nio/FloatBuffer.allocate(I)Ljava/nio/FloatBuffer;", statics("allocate"));
        put(m, "java/nio/FloatBuffer.wrap([F)Ljava/nio/FloatBuffer;", statics("wrap"));
        put(m, "java/nio/FloatBuffer.capacity()I", instance("capacity"));
        put(m, "java/nio/FloatBuffer.position()I", instance("position"));
        put(m, "java/nio/FloatBuffer.limit()I", instance("limit"));
        put(m, "java/nio/FloatBuffer.remaining()I", instance("remaining"));
        put(m, "java/nio/FloatBuffer.hasRemaining()Z", instance("hasRemaining"));
        put(m, "java/nio/FloatBuffer.position(I)Ljava/nio/FloatBuffer;", instance("position"));
        put(m, "java/nio/FloatBuffer.limit(I)Ljava/nio/FloatBuffer;", instance("limit"));
        put(m, "java/nio/FloatBuffer.mark()Ljava/nio/FloatBuffer;", instance("mark"));
        put(m, "java/nio/FloatBuffer.reset()Ljava/nio/FloatBuffer;", instance("reset"));
        put(m, "java/nio/FloatBuffer.flip()Ljava/nio/FloatBuffer;", instance("flip"));
        put(m, "java/nio/FloatBuffer.clear()Ljava/nio/FloatBuffer;", instance("clear"));
        put(m, "java/nio/FloatBuffer.rewind()Ljava/nio/FloatBuffer;", instance("rewind"));
        put(m, "java/nio/FloatBuffer.get()F", instance("get"));
        put(m, "java/nio/FloatBuffer.get(I)F", instance("get"));
        put(m, "java/nio/FloatBuffer.put(F)Ljava/nio/FloatBuffer;", instance("put"));
        put(m, "java/nio/FloatBuffer.put(IF)Ljava/nio/FloatBuffer;", instance("put"));
        put(m, "java/nio/FloatBuffer.get([F)Ljava/nio/FloatBuffer;", instance("get"));
        put(m, "java/nio/FloatBuffer.put([F)Ljava/nio/FloatBuffer;", instance("put"));
        put(m, "java/nio/FloatBuffer.array()[F", instance("array"));
        put(m, "java/nio/ByteBuffer.asIntBuffer()Ljava/nio/IntBuffer;", instance("asIntBuffer"));
        put(m, "java/nio/ByteBuffer.asLongBuffer()Ljava/nio/LongBuffer;", instance("asLongBuffer"));
        put(m, "java/nio/ByteBuffer.asShortBuffer()Ljava/nio/ShortBuffer;", instance("asShortBuffer"));
        put(m, "java/nio/ByteBuffer.asCharBuffer()Ljava/nio/CharBuffer;", instance("asCharBuffer"));
        put(m, "java/nio/ByteBuffer.asFloatBuffer()Ljava/nio/FloatBuffer;", instance("asFloatBuffer"));
        put(m, "java/nio/ByteBuffer.asDoubleBuffer()Ljava/nio/DoubleBuffer;", instance("asDoubleBuffer"));
        put(m, "java/nio/ByteBuffer.slice()Ljava/nio/ByteBuffer;", instance("slice"));
    }

    private static void addNioMethods9(Map<String, ShimTarget> m) {
        put(m, "java/nio/file/Files.newBufferedReader(Ljava/nio/file/Path;)Ljava/io/BufferedReader;", statics("newBufferedReader"));
        put(m, "java/nio/file/Files.newBufferedReader(Ljava/nio/file/Path;Ljava/nio/charset/Charset;)Ljava/io/BufferedReader;", statics("newBufferedReader"));
        put(m, "java/nio/file/Files.newBufferedWriter(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/BufferedWriter;", statics("newBufferedWriter"));
        put(m, "java/nio/file/Files.newBufferedWriter(Ljava/nio/file/Path;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/io/BufferedWriter;", statics("newBufferedWriter"));
        put(m, "java/nio/file/Files.newInputStream(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/InputStream;", statics("newInputStream"));
        put(m, "java/nio/file/Files.newOutputStream(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/OutputStream;", statics("newOutputStream"));
        put(m, "java/nio/file/Files.find(Ljava/nio/file/Path;ILjava/util/function/BiPredicate;[Ljava/nio/file/FileVisitOption;)Ljava/util/stream/Stream;", statics("find"));
        put(m, "java/nio/file/Files.readAttributes(Ljava/nio/file/Path;Ljava/lang/Class;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/BasicFileAttributes;", statics("readAttributes"));
        put(m, "java/nio/file/FileSystem.getSeparator()Ljava/lang/String;", instance("getSeparator"));
        put(m, "java/nio/file/FileSystem.getPath(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;", instance("getPath"));
        put(m, "java/nio/file/FileSystems.getDefault()Ljava/nio/file/FileSystem;", statics("getDefault"));
        put(m, "java/util/function/BiPredicate.test(Ljava/lang/Object;Ljava/lang/Object;)Z", instance("test"));
        put(m, "java/nio/file/attribute/BasicFileAttributes.isRegularFile()Z", instance("isRegularFile"));
        put(m, "java/nio/file/attribute/BasicFileAttributes.isDirectory()Z", instance("isDirectory"));
        put(m, "java/nio/file/attribute/BasicFileAttributes.isSymbolicLink()Z", instance("isSymbolicLink"));
        put(m, "java/nio/file/attribute/BasicFileAttributes.isOther()Z", instance("isOther"));
        put(m, "java/nio/file/attribute/BasicFileAttributes.size()J", instance("size"));
    }

    private static void addTier1Methods0(Map<String, ShimTarget> m) {
        put(m, "java/util/function/UnaryOperator.apply(Ljava/lang/Object;)Ljava/lang/Object;", instance("apply"));
        put(m, "java/util/stream/Stream.of([Ljava/lang/Object;)Ljava/util/stream/Stream;", statics("of"));
        put(m, "java/util/stream/Stream.iterate(Ljava/lang/Object;Ljava/util/function/UnaryOperator;)Ljava/util/stream/Stream;", statics("iterate"));
        put(m, "java/util/stream/Stream.generate(Ljava/util/function/Supplier;)Ljava/util/stream/Stream;", statics("generate"));
        put(m, "java/util/stream/Stream.concat(Ljava/util/stream/Stream;Ljava/util/stream/Stream;)Ljava/util/stream/Stream;", statics("concat"));
        put(m, "java/util/stream/Stream.takeWhile(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;", instance("takeWhile"));
        put(m, "java/util/stream/Stream.dropWhile(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;", instance("dropWhile"));
        put(m, "java/util/stream/Stream.toList()Ljava/util/List;", instance("toList"));
        put(m, "java/util/stream/Stream.findAny()Ljava/util/Optional;", instance("findAny"));
        put(m, "java/math/BigInteger.valueOf(J)Ljava/math/BigInteger;", statics("valueOf"));
        put(m, "java/math/BigInteger.add(Ljava/math/BigInteger;)Ljava/math/BigInteger;", instance("add"));
        put(m, "java/math/BigInteger.subtract(Ljava/math/BigInteger;)Ljava/math/BigInteger;", instance("subtract"));
        put(m, "java/math/BigInteger.multiply(Ljava/math/BigInteger;)Ljava/math/BigInteger;", instance("multiply"));
        put(m, "java/math/BigInteger.divide(Ljava/math/BigInteger;)Ljava/math/BigInteger;", instance("divide"));
        put(m, "java/math/BigInteger.remainder(Ljava/math/BigInteger;)Ljava/math/BigInteger;", instance("remainder"));
        put(m, "java/math/BigInteger.mod(Ljava/math/BigInteger;)Ljava/math/BigInteger;", instance("mod"));
        put(m, "java/math/BigInteger.pow(I)Ljava/math/BigInteger;", instance("pow"));
        put(m, "java/math/BigInteger.gcd(Ljava/math/BigInteger;)Ljava/math/BigInteger;", instance("gcd"));
        put(m, "java/math/BigInteger.abs()Ljava/math/BigInteger;", instance("abs"));
        put(m, "java/math/BigInteger.negate()Ljava/math/BigInteger;", instance("negate"));
        put(m, "java/math/BigInteger.modPow(Ljava/math/BigInteger;Ljava/math/BigInteger;)Ljava/math/BigInteger;", instance("modPow"));
        put(m, "java/math/BigInteger.min(Ljava/math/BigInteger;)Ljava/math/BigInteger;", instance("min"));
        put(m, "java/math/BigInteger.max(Ljava/math/BigInteger;)Ljava/math/BigInteger;", instance("max"));
        put(m, "java/math/BigInteger.compareTo(Ljava/math/BigInteger;)I", instance("compareTo"));
        put(m, "java/math/BigInteger.signum()I", instance("signum"));
        put(m, "java/math/BigInteger.intValue()I", instance("intValue"));
        put(m, "java/math/BigInteger.longValue()J", instance("longValue"));
        put(m, "java/math/BigInteger.doubleValue()D", instance("doubleValue"));
    }

    private static void addTier1Methods1(Map<String, ShimTarget> m) {
        put(m, "java/math/BigDecimal.valueOf(J)Ljava/math/BigDecimal;", statics("valueOf"));
        put(m, "java/math/BigDecimal.valueOf(D)Ljava/math/BigDecimal;", statics("valueOf"));
        put(m, "java/math/BigDecimal.add(Ljava/math/BigDecimal;)Ljava/math/BigDecimal;", instance("add"));
        put(m, "java/math/BigDecimal.subtract(Ljava/math/BigDecimal;)Ljava/math/BigDecimal;", instance("subtract"));
        put(m, "java/math/BigDecimal.multiply(Ljava/math/BigDecimal;)Ljava/math/BigDecimal;", instance("multiply"));
        put(m, "java/math/BigDecimal.divide(Ljava/math/BigDecimal;ILjava/math/RoundingMode;)Ljava/math/BigDecimal;", instance("divide"));
        put(m, "java/math/BigDecimal.divide(Ljava/math/BigDecimal;Ljava/math/RoundingMode;)Ljava/math/BigDecimal;", instance("divide"));
        put(m, "java/math/BigDecimal.setScale(ILjava/math/RoundingMode;)Ljava/math/BigDecimal;", instance("setScale"));
        put(m, "java/math/BigDecimal.scale()I", instance("scale"));
        put(m, "java/math/BigDecimal.abs()Ljava/math/BigDecimal;", instance("abs"));
        put(m, "java/math/BigDecimal.negate()Ljava/math/BigDecimal;", instance("negate"));
        put(m, "java/math/BigDecimal.signum()I", instance("signum"));
        put(m, "java/math/BigDecimal.compareTo(Ljava/math/BigDecimal;)I", instance("compareTo"));
        put(m, "java/math/BigDecimal.toBigInteger()Ljava/math/BigInteger;", instance("toBigInteger"));
        put(m, "java/math/BigDecimal.intValue()I", instance("intValue"));
        put(m, "java/math/BigDecimal.longValue()J", instance("longValue"));
        put(m, "java/math/BigDecimal.doubleValue()D", instance("doubleValue"));
        put(m, "java/util/Scanner.hasNext()Z", instance("hasNext"));
        put(m, "java/util/Scanner.next()Ljava/lang/String;", instance("next"));
        put(m, "java/util/Scanner.hasNextInt()Z", instance("hasNextInt"));
        put(m, "java/util/Scanner.nextInt()I", instance("nextInt"));
        put(m, "java/util/Scanner.hasNextLong()Z", instance("hasNextLong"));
        put(m, "java/util/Scanner.nextLong()J", instance("nextLong"));
        put(m, "java/util/Scanner.hasNextDouble()Z", instance("hasNextDouble"));
        put(m, "java/util/Scanner.nextDouble()D", instance("nextDouble"));
        put(m, "java/util/Scanner.hasNextLine()Z", instance("hasNextLine"));
        put(m, "java/util/Scanner.nextLine()Ljava/lang/String;", instance("nextLine"));
        put(m, "java/util/Scanner.close()V", instance("close"));
        put(m, "java/util/StringTokenizer.countTokens()I", instance("countTokens"));
        put(m, "java/util/StringTokenizer.hasMoreTokens()Z", instance("hasMoreTokens"));
        put(m, "java/util/StringTokenizer.hasMoreElements()Z", instance("hasMoreElements"));
        put(m, "java/util/StringTokenizer.nextToken()Ljava/lang/String;", instance("nextToken"));
        put(m, "java/util/StringTokenizer.nextElement()Ljava/lang/Object;", instance("nextElement"));
        put(m, "java/text/DecimalFormat.format(D)Ljava/lang/String;", instance("format"));
        put(m, "java/text/DecimalFormat.format(J)Ljava/lang/String;", instance("format"));
        put(m, "java/text/DecimalFormat.applyPattern(Ljava/lang/String;)V", instance("applyPattern"));
        put(m, "java/text/DecimalFormat.toPattern()Ljava/lang/String;", instance("toPattern"));
    }

    private static void addTimeTextMethods0(Map<String, ShimTarget> m) {
        put(m, "java/time/Instant.ofEpochSecond(J)Ljava/time/Instant;", statics("ofEpochSecond"));
        put(m, "java/time/Instant.ofEpochSecond(JJ)Ljava/time/Instant;", statics("ofEpochSecond"));
        put(m, "java/time/Instant.ofEpochMilli(J)Ljava/time/Instant;", statics("ofEpochMilli"));
        put(m, "java/time/Instant.getEpochSecond()J", instance("getEpochSecond"));
        put(m, "java/time/Instant.getNano()I", instance("getNano"));
        put(m, "java/time/Instant.toEpochMilli()J", instance("toEpochMilli"));
        put(m, "java/time/Instant.plusSeconds(J)Ljava/time/Instant;", instance("plusSeconds"));
        put(m, "java/time/Instant.minusSeconds(J)Ljava/time/Instant;", instance("minusSeconds"));
        put(m, "java/time/Instant.plusMillis(J)Ljava/time/Instant;", instance("plusMillis"));
        put(m, "java/time/Instant.isBefore(Ljava/time/Instant;)Z", instance("isBefore"));
        put(m, "java/time/Instant.isAfter(Ljava/time/Instant;)Z", instance("isAfter"));
        put(m, "java/time/Instant.compareTo(Ljava/time/Instant;)I", instance("compareTo"));
        put(m, "java/time/LocalTime.of(II)Ljava/time/LocalTime;", statics("of"));
        put(m, "java/time/LocalTime.of(III)Ljava/time/LocalTime;", statics("of"));
        put(m, "java/time/LocalTime.of(IIII)Ljava/time/LocalTime;", statics("of"));
        put(m, "java/time/LocalTime.getHour()I", instance("getHour"));
        put(m, "java/time/LocalTime.getMinute()I", instance("getMinute"));
        put(m, "java/time/LocalTime.getSecond()I", instance("getSecond"));
        put(m, "java/time/LocalTime.getNano()I", instance("getNano"));
        put(m, "java/time/LocalTime.plusHours(J)Ljava/time/LocalTime;", instance("plusHours"));
        put(m, "java/time/LocalTime.plusMinutes(J)Ljava/time/LocalTime;", instance("plusMinutes"));
        put(m, "java/time/LocalTime.isBefore(Ljava/time/LocalTime;)Z", instance("isBefore"));
        put(m, "java/time/LocalTime.isAfter(Ljava/time/LocalTime;)Z", instance("isAfter"));
        put(m, "java/time/LocalTime.compareTo(Ljava/time/LocalTime;)I", instance("compareTo"));
        put(m, "java/time/Period.of(III)Ljava/time/Period;", statics("of"));
        put(m, "java/time/Period.ofYears(I)Ljava/time/Period;", statics("ofYears"));
        put(m, "java/time/Period.ofMonths(I)Ljava/time/Period;", statics("ofMonths"));
        put(m, "java/time/Period.ofWeeks(I)Ljava/time/Period;", statics("ofWeeks"));
        put(m, "java/time/Period.ofDays(I)Ljava/time/Period;", statics("ofDays"));
        put(m, "java/time/Period.getYears()I", instance("getYears"));
        put(m, "java/time/Period.getMonths()I", instance("getMonths"));
        put(m, "java/time/Period.getDays()I", instance("getDays"));
        put(m, "java/time/Period.plusYears(J)Ljava/time/Period;", instance("plusYears"));
        put(m, "java/time/Period.plusMonths(J)Ljava/time/Period;", instance("plusMonths"));
        put(m, "java/time/Period.plusDays(J)Ljava/time/Period;", instance("plusDays"));
        put(m, "java/time/ZoneId.of(Ljava/lang/String;)Ljava/time/ZoneId;", statics("of"));
        put(m, "java/time/ZoneId.systemDefault()Ljava/time/ZoneId;", statics("systemDefault"));
        put(m, "java/time/ZoneId.getId()Ljava/lang/String;", instance("getId"));
        put(m, "java/time/Month.of(I)Ljava/time/Month;", statics("of"));
        put(m, "java/time/Month.values()[Ljava/time/Month;", statics("values"));
        put(m, "java/time/Month.getValue()I", instance("getValue"));
        put(m, "java/time/Year.of(I)Ljava/time/Year;", statics("of"));
        put(m, "java/time/Year.isLeap(J)Z", statics("isLeap"));
        put(m, "java/time/Year.getValue()I", instance("getValue"));
        put(m, "java/time/Year.isLeap()Z", instance("isLeap"));
    }

    private static void addTimeTextMethods1(Map<String, ShimTarget> m) {
        put(m, "java/util/Locale.getDefault()Ljava/util/Locale;", statics("getDefault"));
        put(m, "java/util/Locale.getLanguage()Ljava/lang/String;", instance("getLanguage"));
        put(m, "java/util/Locale.getCountry()Ljava/lang/String;", instance("getCountry"));
        put(m, "java/text/NumberFormat.getInstance()Ljava/text/NumberFormat;", statics("getInstance"));
        put(m, "java/text/NumberFormat.getInstance(Ljava/util/Locale;)Ljava/text/NumberFormat;", statics("getInstance"));
        put(m, "java/text/NumberFormat.getNumberInstance()Ljava/text/NumberFormat;", statics("getNumberInstance"));
        put(m, "java/text/NumberFormat.getIntegerInstance()Ljava/text/NumberFormat;", statics("getIntegerInstance"));
        put(m, "java/text/NumberFormat.format(D)Ljava/lang/String;", instance("format"));
        put(m, "java/text/NumberFormat.format(J)Ljava/lang/String;", instance("format"));
        put(m, "java/text/NumberFormat.setMaximumFractionDigits(I)V", instance("setMaximumFractionDigits"));
        put(m, "java/text/NumberFormat.setMinimumFractionDigits(I)V", instance("setMinimumFractionDigits"));
        put(m, "java/text/NumberFormat.setGroupingUsed(Z)V", instance("setGroupingUsed"));
        put(m, "java/text/NumberFormat.isGroupingUsed()Z", instance("isGroupingUsed"));
        put(m, "java/text/MessageFormat.format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", statics("format"));
        put(m, "java/text/MessageFormat.format([Ljava/lang/Object;)Ljava/lang/String;", instance("format"));
    }

    private static void addDateCalMethods0(Map<String, ShimTarget> m) {
        put(m, "java/util/Date.getTime()J", instance("getTime"));
        put(m, "java/util/Date.setTime(J)V", instance("setTime"));
        put(m, "java/util/Date.before(Ljava/util/Date;)Z", instance("before"));
        put(m, "java/util/Date.after(Ljava/util/Date;)Z", instance("after"));
        put(m, "java/util/Date.compareTo(Ljava/util/Date;)I", instance("compareTo"));
        put(m, "java/util/TimeZone.getTimeZone(Ljava/lang/String;)Ljava/util/TimeZone;", statics("getTimeZone"));
        put(m, "java/util/TimeZone.getDefault()Ljava/util/TimeZone;", statics("getDefault"));
        put(m, "java/util/TimeZone.getID()Ljava/lang/String;", instance("getID"));
        put(m, "java/util/TimeZone.getRawOffset()I", instance("getRawOffset"));
        put(m, "java/util/Calendar.getInstance()Ljava/util/Calendar;", statics("getInstance"));
        put(m, "java/util/Calendar.getInstance(Ljava/util/TimeZone;)Ljava/util/Calendar;", statics("getInstance"));
        put(m, "java/util/Calendar.setTime(Ljava/util/Date;)V", instance("setTime"));
        put(m, "java/util/Calendar.getTime()Ljava/util/Date;", instance("getTime"));
        put(m, "java/util/Calendar.getTimeInMillis()J", instance("getTimeInMillis"));
        put(m, "java/util/Calendar.setTimeInMillis(J)V", instance("setTimeInMillis"));
        put(m, "java/util/Calendar.setTimeZone(Ljava/util/TimeZone;)V", instance("setTimeZone"));
        put(m, "java/util/Calendar.getTimeZone()Ljava/util/TimeZone;", instance("getTimeZone"));
        put(m, "java/util/Calendar.get(I)I", instance("get"));
        put(m, "java/util/Calendar.set(II)V", instance("set"));
        put(m, "java/util/Calendar.set(III)V", instance("set"));
        put(m, "java/util/Calendar.add(II)V", instance("add"));
        put(m, "java/text/SimpleDateFormat.setTimeZone(Ljava/util/TimeZone;)V", instance("setTimeZone"));
        put(m, "java/text/SimpleDateFormat.format(Ljava/util/Date;)Ljava/lang/String;", instance("format"));
        put(m, "java/text/SimpleDateFormat.parse(Ljava/lang/String;)Ljava/util/Date;", instance("parse"));
        put(m, "java/text/SimpleDateFormat.toPattern()Ljava/lang/String;", instance("toPattern"));
    }

    private static void addUtilColMethods0(Map<String, ShimTarget> m) {
        put(m, "java/util/Vector.addElement(Ljava/lang/Object;)V", instance("addElement"));
        put(m, "java/util/Vector.insertElementAt(Ljava/lang/Object;I)V", instance("insertElementAt"));
        put(m, "java/util/Vector.setElementAt(Ljava/lang/Object;I)V", instance("setElementAt"));
        put(m, "java/util/Vector.removeElementAt(I)V", instance("removeElementAt"));
        put(m, "java/util/Vector.removeElement(Ljava/lang/Object;)Z", instance("removeElement"));
        put(m, "java/util/Vector.elementAt(I)Ljava/lang/Object;", instance("elementAt"));
        put(m, "java/util/Vector.firstElement()Ljava/lang/Object;", instance("firstElement"));
        put(m, "java/util/Vector.lastElement()Ljava/lang/Object;", instance("lastElement"));
        put(m, "java/util/Vector.capacity()I", instance("capacity"));
        put(m, "java/util/Stack.push(Ljava/lang/Object;)Ljava/lang/Object;", instance("push"));
        put(m, "java/util/Stack.pop()Ljava/lang/Object;", instance("pop"));
        put(m, "java/util/Stack.peek()Ljava/lang/Object;", instance("peek"));
        put(m, "java/util/Stack.empty()Z", instance("empty"));
        put(m, "java/util/Stack.search(Ljava/lang/Object;)I", instance("search"));
        put(m, "java/util/BitSet.set(I)V", instance("set"));
        put(m, "java/util/BitSet.set(IZ)V", instance("set"));
        put(m, "java/util/BitSet.clear(I)V", instance("clear"));
        put(m, "java/util/BitSet.clear()V", instance("clear"));
        put(m, "java/util/BitSet.flip(I)V", instance("flip"));
        put(m, "java/util/BitSet.get(I)Z", instance("get"));
        put(m, "java/util/BitSet.cardinality()I", instance("cardinality"));
        put(m, "java/util/BitSet.isEmpty()Z", instance("isEmpty"));
        put(m, "java/util/BitSet.length()I", instance("length"));
        put(m, "java/util/BitSet.size()I", instance("size"));
        put(m, "java/util/BitSet.nextSetBit(I)I", instance("nextSetBit"));
        put(m, "java/util/BitSet.and(Ljava/util/BitSet;)V", instance("and"));
        put(m, "java/util/BitSet.or(Ljava/util/BitSet;)V", instance("or"));
        put(m, "java/util/BitSet.xor(Ljava/util/BitSet;)V", instance("xor"));
        put(m, "java/util/BitSet.andNot(Ljava/util/BitSet;)V", instance("andNot"));
        put(m, "java/util/Properties.setProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;", instance("setProperty"));
        put(m, "java/util/Properties.getProperty(Ljava/lang/String;)Ljava/lang/String;", instance("getProperty"));
        put(m, "java/util/Properties.getProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", instance("getProperty"));
        put(m, "java/util/Properties.stringPropertyNames()Ljava/util/Set;", instance("stringPropertyNames"));
        put(m, "java/util/Properties.load(Ljava/io/InputStream;)V", instance("load"));
        put(m, "java/util/List.listIterator()Ljava/util/ListIterator;", instance("listIterator"));
        put(m, "java/util/List.listIterator(I)Ljava/util/ListIterator;", instance("listIterator"));
        put(m, "java/util/ListIterator.hasPrevious()Z", instance("hasPrevious"));
        put(m, "java/util/ListIterator.previous()Ljava/lang/Object;", instance("previous"));
        put(m, "java/util/ListIterator.nextIndex()I", instance("nextIndex"));
        put(m, "java/util/ListIterator.previousIndex()I", instance("previousIndex"));
        put(m, "java/util/ListIterator.set(Ljava/lang/Object;)V", instance("set"));
        put(m, "java/util/ListIterator.add(Ljava/lang/Object;)V", instance("add"));
        put(m, "java/util/ListIterator.remove()V", instance("remove"));
        put(m, "java/util/TreeSet.higher(Ljava/lang/Object;)Ljava/lang/Object;", instance("higher"));
        put(m, "java/util/TreeSet.lower(Ljava/lang/Object;)Ljava/lang/Object;", instance("lower"));
        put(m, "java/util/TreeMap.firstKey()Ljava/lang/Object;", instance("firstKey"));
        put(m, "java/util/TreeMap.lastKey()Ljava/lang/Object;", instance("lastKey"));
        put(m, "java/util/TreeMap.floorKey(Ljava/lang/Object;)Ljava/lang/Object;", instance("floorKey"));
        put(m, "java/util/TreeMap.ceilingKey(Ljava/lang/Object;)Ljava/lang/Object;", instance("ceilingKey"));
        put(m, "java/util/TreeMap.higherKey(Ljava/lang/Object;)Ljava/lang/Object;", instance("higherKey"));
        put(m, "java/util/TreeMap.lowerKey(Ljava/lang/Object;)Ljava/lang/Object;", instance("lowerKey"));
    }

    private static void addEnumStrMethods0(Map<String, ShimTarget> m) {
        put(m, "java/util/EnumMap.put(Ljava/lang/Enum;Ljava/lang/Object;)Ljava/lang/Object;", instance("put"));
        put(m, "java/util/EnumSet.noneOf(Ljava/lang/Class;)Ljava/util/EnumSet;", statics("noneOf"));
        put(m, "java/util/EnumSet.of(Ljava/lang/Enum;)Ljava/util/EnumSet;", statics("of"));
        put(m, "java/util/EnumSet.of(Ljava/lang/Enum;Ljava/lang/Enum;)Ljava/util/EnumSet;", statics("of"));
        put(m, "java/util/EnumSet.of(Ljava/lang/Enum;Ljava/lang/Enum;Ljava/lang/Enum;)Ljava/util/EnumSet;", statics("of"));
        put(m, "java/util/EnumSet.of(Ljava/lang/Enum;Ljava/lang/Enum;Ljava/lang/Enum;Ljava/lang/Enum;)Ljava/util/EnumSet;", statics("of"));
        put(m, "java/util/EnumSet.of(Ljava/lang/Enum;Ljava/lang/Enum;Ljava/lang/Enum;Ljava/lang/Enum;Ljava/lang/Enum;)Ljava/util/EnumSet;", statics("of"));
        put(m, "java/util/EnumSet.of(Ljava/lang/Enum;[Ljava/lang/Enum;)Ljava/util/EnumSet;", statics("of"));
        put(m, "java/util/EnumSet.copyOf(Ljava/util/Collection;)Ljava/util/EnumSet;", statics("copyOf"));
        put(m, "java/lang/String.isBlank()Z", instance("isBlank"));
        put(m, "java/lang/String.stripLeading()Ljava/lang/String;", instance("stripLeading"));
        put(m, "java/lang/String.stripTrailing()Ljava/lang/String;", instance("stripTrailing"));
        put(m, "java/lang/String.chars()Ljava/util/stream/IntStream;", instance("chars"));
        put(m, "java/lang/String.codePoints()Ljava/util/stream/IntStream;", instance("codePoints"));
        put(m, "java/lang/String.lines()Ljava/util/stream/Stream;", instance("lines"));
        put(m, "java/lang/String.formatted([Ljava/lang/Object;)Ljava/lang/String;", instance("formatted"));
    }

    private static void addConcurrentMethods2(Map<String, ShimTarget> m) {
        put(m, "java/util/concurrent/BlockingQueue.put(Ljava/lang/Object;)V", instance("put"));
        put(m, "java/util/concurrent/BlockingQueue.take()Ljava/lang/Object;", instance("take"));
        put(m, "java/util/concurrent/BlockingQueue.remainingCapacity()I", instance("remainingCapacity"));
        put(m, "java/util/concurrent/CountDownLatch.countDown()V", instance("countDown"));
        put(m, "java/util/concurrent/CountDownLatch.getCount()J", instance("getCount"));
        put(m, "java/util/concurrent/CountDownLatch.await()V", instance("await"));
        put(m, "java/util/concurrent/CountDownLatch.await(JLjava/util/concurrent/TimeUnit;)Z", instance("await"));
        put(m, "java/util/concurrent/Semaphore.acquire()V", instance("acquire"));
        put(m, "java/util/concurrent/Semaphore.acquire(I)V", instance("acquire"));
        put(m, "java/util/concurrent/Semaphore.release()V", instance("release"));
        put(m, "java/util/concurrent/Semaphore.release(I)V", instance("release"));
        put(m, "java/util/concurrent/Semaphore.tryAcquire()Z", instance("tryAcquire"));
        put(m, "java/util/concurrent/Semaphore.tryAcquire(JLjava/util/concurrent/TimeUnit;)Z", instance("tryAcquire"));
        put(m, "java/util/concurrent/Semaphore.availablePermits()I", instance("availablePermits"));
        put(m, "java/util/concurrent/CyclicBarrier.await()I", instance("await"));
        put(m, "java/util/concurrent/CyclicBarrier.getParties()I", instance("getParties"));
        put(m, "java/util/concurrent/CyclicBarrier.getNumberWaiting()I", instance("getNumberWaiting"));
    }

    private static void addNavEnumMethods0(Map<String, ShimTarget> m) {
        put(m, "java/lang/Class.getEnumConstants()[Ljava/lang/Object;", instance("getEnumConstants"));
        put(m, "java/util/EnumSet.allOf(Ljava/lang/Class;)Ljava/util/EnumSet;", statics("allOf"));
        put(m, "java/util/EnumSet.range(Ljava/lang/Enum;Ljava/lang/Enum;)Ljava/util/EnumSet;", statics("range"));
        put(m, "java/lang/ThreadLocal.withInitial(Ljava/util/function/Supplier;)Ljava/lang/ThreadLocal;", statics("withInitial"));
        put(m, "java/lang/ThreadLocal.get()Ljava/lang/Object;", instance("get"));
        put(m, "java/lang/ThreadLocal.set(Ljava/lang/Object;)V", instance("set"));
        put(m, "java/lang/ThreadLocal.remove()V", instance("remove"));
        put(m, "java/lang/ThreadLocal.initialValue()Ljava/lang/Object;", instance("initialValue"));
        put(m, "java/util/TreeSet.headSet(Ljava/lang/Object;)Ljava/util/SortedSet;", instance("headSet"));
        put(m, "java/util/TreeSet.tailSet(Ljava/lang/Object;)Ljava/util/SortedSet;", instance("tailSet"));
        put(m, "java/util/TreeSet.subSet(Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/SortedSet;", instance("subSet"));
        put(m, "java/util/TreeMap.headMap(Ljava/lang/Object;)Ljava/util/SortedMap;", instance("headMap"));
        put(m, "java/util/TreeMap.tailMap(Ljava/lang/Object;)Ljava/util/SortedMap;", instance("tailMap"));
        put(m, "java/util/TreeMap.subMap(Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/SortedMap;", instance("subMap"));
    }

    private static void addNetMethods0(Map<String, ShimTarget> m) {
        put(m, "java/net/URI.getScheme()Ljava/lang/String;", instance("getScheme"));
        put(m, "java/net/URI.getAuthority()Ljava/lang/String;", instance("getAuthority"));
        put(m, "java/net/URI.getUserInfo()Ljava/lang/String;", instance("getUserInfo"));
        put(m, "java/net/URI.getHost()Ljava/lang/String;", instance("getHost"));
        put(m, "java/net/URI.getPort()I", instance("getPort"));
        put(m, "java/net/URI.getPath()Ljava/lang/String;", instance("getPath"));
        put(m, "java/net/URI.getQuery()Ljava/lang/String;", instance("getQuery"));
        put(m, "java/net/URI.getFragment()Ljava/lang/String;", instance("getFragment"));
        put(m, "java/net/URI.isAbsolute()Z", instance("isAbsolute"));
        put(m, "java/net/URI.toURL()Ljava/net/URL;", instance("toURL"));
        put(m, "java/net/URL.getProtocol()Ljava/lang/String;", instance("getProtocol"));
        put(m, "java/net/URL.getHost()Ljava/lang/String;", instance("getHost"));
        put(m, "java/net/URL.getPort()I", instance("getPort"));
        put(m, "java/net/URL.getPath()Ljava/lang/String;", instance("getPath"));
        put(m, "java/net/URL.getQuery()Ljava/lang/String;", instance("getQuery"));
        put(m, "java/net/URL.getRef()Ljava/lang/String;", instance("getRef"));
        put(m, "java/net/URL.getFile()Ljava/lang/String;", instance("getFile"));
        put(m, "java/net/URL.toExternalForm()Ljava/lang/String;", instance("toExternalForm"));
        put(m, "java/net/URL.toURI()Ljava/net/URI;", instance("toURI"));
    }

    private static void addCryptoMethods0(Map<String, ShimTarget> m) {
        put(m, "java/security/Key.getAlgorithm()Ljava/lang/String;", instance("getAlgorithm"));
        put(m, "java/security/Key.getEncoded()[B", instance("getEncoded"));
        put(m, "java/security/Key.getFormat()Ljava/lang/String;", instance("getFormat"));
        put(m, "javax/crypto/Mac.getInstance(Ljava/lang/String;)Ljavax/crypto/Mac;", statics("getInstance"));
        put(m, "javax/crypto/Mac.init(Ljava/security/Key;)V", instance("init"));
        put(m, "javax/crypto/Mac.update([B)V", instance("update"));
        put(m, "javax/crypto/Mac.doFinal()[B", instance("doFinal"));
        put(m, "javax/crypto/Mac.doFinal([B)[B", instance("doFinal"));
        put(m, "javax/crypto/Mac.getMacLength()I", instance("getMacLength"));
        put(m, "javax/crypto/Mac.getAlgorithm()Ljava/lang/String;", instance("getAlgorithm"));
        put(m, "javax/crypto/Mac.reset()V", instance("reset"));
        put(m, "java/security/MessageDigest.update([B)V", instance("update"));
        put(m, "java/security/MessageDigest.update(B)V", instance("update"));
        put(m, "java/security/MessageDigest.digest()[B", instance("digest"));
        put(m, "java/security/MessageDigest.getAlgorithm()Ljava/lang/String;", instance("getAlgorithm"));
        put(m, "java/security/MessageDigest.isEqual([B[B)Z", statics("isEqual"));
    }

    private static void addCryptoMethods1(Map<String, ShimTarget> m) {
        put(m, "javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;", statics("getInstance"));
        put(m, "javax/crypto/Cipher.init(ILjava/security/Key;)V", instance("init"));
        put(m, "javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V", instance("init"));
        put(m, "javax/crypto/Cipher.doFinal([B)[B", instance("doFinal"));
        put(m, "javax/crypto/Cipher.getBlockSize()I", instance("getBlockSize"));
        put(m, "javax/crypto/spec/IvParameterSpec.getIV()[B", instance("getIV"));
    }

    private static void addNavViewMethods0(Map<String, ShimTarget> m) {
        put(m, "java/util/TreeSet.headSet(Ljava/lang/Object;Z)Ljava/util/NavigableSet;", instance("headSet"));
        put(m, "java/util/TreeSet.tailSet(Ljava/lang/Object;Z)Ljava/util/NavigableSet;", instance("tailSet"));
        put(m, "java/util/TreeSet.subSet(Ljava/lang/Object;ZLjava/lang/Object;Z)Ljava/util/NavigableSet;", instance("subSet"));
        put(m, "java/util/TreeSet.descendingSet()Ljava/util/NavigableSet;", instance("descendingSet"));
        put(m, "java/util/TreeSet.descendingIterator()Ljava/util/Iterator;", instance("descendingIterator"));
        put(m, "java/util/TreeMap.firstEntry()Ljava/util/Map$Entry;", instance("firstEntry"));
        put(m, "java/util/TreeMap.lastEntry()Ljava/util/Map$Entry;", instance("lastEntry"));
        put(m, "java/util/TreeMap.floorEntry(Ljava/lang/Object;)Ljava/util/Map$Entry;", instance("floorEntry"));
        put(m, "java/util/TreeMap.ceilingEntry(Ljava/lang/Object;)Ljava/util/Map$Entry;", instance("ceilingEntry"));
        put(m, "java/util/TreeMap.higherEntry(Ljava/lang/Object;)Ljava/util/Map$Entry;", instance("higherEntry"));
        put(m, "java/util/TreeMap.lowerEntry(Ljava/lang/Object;)Ljava/util/Map$Entry;", instance("lowerEntry"));
        put(m, "java/util/TreeMap.navigableKeySet()Ljava/util/NavigableSet;", instance("navigableKeySet"));
        put(m, "java/util/TreeMap.descendingKeySet()Ljava/util/NavigableSet;", instance("descendingKeySet"));
        put(m, "java/util/TreeMap.descendingMap()Ljava/util/NavigableMap;", instance("descendingMap"));
        put(m, "java/util/TreeMap.headMap(Ljava/lang/Object;Z)Ljava/util/NavigableMap;", instance("headMap"));
        put(m, "java/util/TreeMap.tailMap(Ljava/lang/Object;Z)Ljava/util/NavigableMap;", instance("tailMap"));
        put(m, "java/util/TreeMap.subMap(Ljava/lang/Object;ZLjava/lang/Object;Z)Ljava/util/NavigableMap;", instance("subMap"));
    }

    private static void addProcessMethods0(Map<String, ShimTarget> m) {
        put(m, "java/lang/ProcessBuilder.redirectErrorStream(Z)Ljava/lang/ProcessBuilder;", instance("redirectErrorStream"));
        put(m, "java/lang/ProcessBuilder.directory(Ljava/io/File;)Ljava/lang/ProcessBuilder;", instance("directory"));
        put(m, "java/lang/ProcessBuilder.start()Ljava/lang/Process;", instance("start"));
        put(m, "java/lang/Process.getInputStream()Ljava/io/InputStream;", instance("getInputStream"));
        put(m, "java/lang/Process.getErrorStream()Ljava/io/InputStream;", instance("getErrorStream"));
        put(m, "java/lang/Process.getOutputStream()Ljava/io/OutputStream;", instance("getOutputStream"));
        put(m, "java/lang/Process.waitFor()I", instance("waitFor"));
        put(m, "java/lang/Process.exitValue()I", instance("exitValue"));
        put(m, "java/lang/Process.isAlive()Z", instance("isAlive"));
        put(m, "java/lang/Process.destroy()V", instance("destroy"));
    }

    private static void addCryptoMethods2(Map<String, ShimTarget> m) {
        put(m, "java/security/KeyPairGenerator.getInstance(Ljava/lang/String;)Ljava/security/KeyPairGenerator;", statics("getInstance"));
        put(m, "java/security/KeyPairGenerator.initialize(I)V", instance("initialize"));
        put(m, "java/security/KeyPairGenerator.generateKeyPair()Ljava/security/KeyPair;", instance("generateKeyPair"));
        put(m, "java/security/KeyPairGenerator.genKeyPair()Ljava/security/KeyPair;", instance("genKeyPair"));
        put(m, "java/security/KeyPair.getPublic()Ljava/security/PublicKey;", instance("getPublic"));
        put(m, "java/security/KeyPair.getPrivate()Ljava/security/PrivateKey;", instance("getPrivate"));
        put(m, "java/security/Signature.getInstance(Ljava/lang/String;)Ljava/security/Signature;", statics("getInstance"));
        put(m, "java/security/Signature.initSign(Ljava/security/PrivateKey;)V", instance("initSign"));
        put(m, "java/security/Signature.initVerify(Ljava/security/PublicKey;)V", instance("initVerify"));
        put(m, "java/security/Signature.update([B)V", instance("update"));
        put(m, "java/security/Signature.sign()[B", instance("sign"));
        put(m, "java/security/Signature.verify([B)Z", instance("verify"));
    }

    private static void addNetMethods1(Map<String, ShimTarget> m) {
        put(m, "java/net/Socket.getInputStream()Ljava/io/InputStream;", instance("getInputStream"));
        put(m, "java/net/Socket.getOutputStream()Ljava/io/OutputStream;", instance("getOutputStream"));
        put(m, "java/net/Socket.getPort()I", instance("getPort"));
        put(m, "java/net/Socket.getLocalPort()I", instance("getLocalPort"));
        put(m, "java/net/Socket.close()V", instance("close"));
        put(m, "java/net/ServerSocket.getLocalPort()I", instance("getLocalPort"));
        put(m, "java/net/ServerSocket.accept()Ljava/net/Socket;", instance("accept"));
        put(m, "java/net/ServerSocket.close()V", instance("close"));
    }

    private static void addNetMethods2(Map<String, ShimTarget> m) {
        put(m, "java/net/InetAddress.getByName(Ljava/lang/String;)Ljava/net/InetAddress;", statics("getByName"));
        put(m, "java/net/InetAddress.getLocalHost()Ljava/net/InetAddress;", statics("getLocalHost"));
        put(m, "java/net/InetAddress.getLoopbackAddress()Ljava/net/InetAddress;", statics("getLoopbackAddress"));
        put(m, "java/net/InetAddress.getHostAddress()Ljava/lang/String;", instance("getHostAddress"));
        put(m, "java/net/InetAddress.getHostName()Ljava/lang/String;", instance("getHostName"));
        put(m, "java/net/InetAddress.isLoopbackAddress()Z", instance("isLoopbackAddress"));
        put(m, "java/net/URL.openConnection()Ljava/net/URLConnection;", instance("openConnection"));
        put(m, "java/net/URLConnection.connect()V", instance("connect"));
        put(m, "java/net/URLConnection.getInputStream()Ljava/io/InputStream;", instance("getInputStream"));
        put(m, "java/net/URLConnection.getLastModified()J", instance("getLastModified"));
        put(m, "java/net/URLConnection.getContentLengthLong()J", instance("getContentLengthLong"));
        put(m, "java/net/URLConnection.getContentLength()I", instance("getContentLength"));
        put(m, "java/net/URLConnection.setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V", instance("setRequestProperty"));
        put(m, "java/net/URLConnection.setDoOutput(Z)V", instance("setDoOutput"));
        put(m, "java/net/URLConnection.setConnectTimeout(I)V", instance("setConnectTimeout"));
        put(m, "java/net/URLConnection.setReadTimeout(I)V", instance("setReadTimeout"));
        put(m, "java/net/HttpURLConnection.setRequestMethod(Ljava/lang/String;)V", instance("setRequestMethod"));
        put(m, "java/net/HttpURLConnection.getResponseCode()I", instance("getResponseCode"));
        put(m, "java/net/HttpURLConnection.disconnect()V", instance("disconnect"));
    }

    private static void addHttpClientMethods0(Map<String, ShimTarget> m) {
        put(m, "java/net/URI.create(Ljava/lang/String;)Ljava/net/URI;", statics("create"));
        put(m, "java/net/http/HttpClient.newHttpClient()Ljava/net/http/HttpClient;", statics("newHttpClient"));
        put(m, "java/net/http/HttpClient.newBuilder()Ljava/net/http/HttpClient$Builder;", statics("newBuilder"));
        put(m, "java/net/http/HttpClient.send(Ljava/net/http/HttpRequest;Ljava/net/http/HttpResponse$BodyHandler;)Ljava/net/http/HttpResponse;", instance("send"));
        put(m, "java/net/http/HttpClient$Builder.connectTimeout(Ljava/time/Duration;)Ljava/net/http/HttpClient$Builder;", instance("connectTimeout"));
        put(m, "java/net/http/HttpClient$Builder.build()Ljava/net/http/HttpClient;", instance("build"));
        put(m, "java/net/http/HttpRequest.newBuilder()Ljava/net/http/HttpRequest$Builder;", statics("newBuilder"));
        put(m, "java/net/http/HttpRequest.newBuilder(Ljava/net/URI;)Ljava/net/http/HttpRequest$Builder;", statics("newBuilder"));
        put(m, "java/net/http/HttpRequest$Builder.uri(Ljava/net/URI;)Ljava/net/http/HttpRequest$Builder;", instance("uri"));
        put(m, "java/net/http/HttpRequest$Builder.GET()Ljava/net/http/HttpRequest$Builder;", instance("GET"));
        put(m, "java/net/http/HttpRequest$Builder.DELETE()Ljava/net/http/HttpRequest$Builder;", instance("DELETE"));
        put(m, "java/net/http/HttpRequest$Builder.POST(Ljava/net/http/HttpRequest$BodyPublisher;)Ljava/net/http/HttpRequest$Builder;", instance("POST"));
        put(m, "java/net/http/HttpRequest$Builder.PUT(Ljava/net/http/HttpRequest$BodyPublisher;)Ljava/net/http/HttpRequest$Builder;", instance("PUT"));
        put(m, "java/net/http/HttpRequest$Builder.header(Ljava/lang/String;Ljava/lang/String;)Ljava/net/http/HttpRequest$Builder;", instance("header"));
        put(m, "java/net/http/HttpRequest$Builder.build()Ljava/net/http/HttpRequest;", instance("build"));
        put(m, "java/net/http/HttpRequest$BodyPublishers.ofString(Ljava/lang/String;)Ljava/net/http/HttpRequest$BodyPublisher;", statics("ofString"));
        put(m, "java/net/http/HttpRequest$BodyPublishers.noBody()Ljava/net/http/HttpRequest$BodyPublisher;", statics("noBody"));
        put(m, "java/net/http/HttpResponse.statusCode()I", instance("statusCode"));
        put(m, "java/net/http/HttpResponse.body()Ljava/lang/Object;", instance("body"));
        put(m, "java/net/http/HttpResponse$BodyHandlers.ofString()Ljava/net/http/HttpResponse$BodyHandler;", statics("ofString"));
        put(m, "java/net/http/HttpResponse$BodyHandlers.ofByteArray()Ljava/net/http/HttpResponse$BodyHandler;", statics("ofByteArray"));
    }

    private static void addWatchMethods0(Map<String, ShimTarget> m) {
        put(m, "java/nio/file/FileSystem.newWatchService()Ljava/nio/file/WatchService;", instance("newWatchService"));
        put(m, "java/nio/file/Path.register(Ljava/nio/file/WatchService;[Ljava/nio/file/WatchEvent$Kind;)Ljava/nio/file/WatchKey;", instance("register"));
        put(m, "java/nio/file/WatchService.take()Ljava/nio/file/WatchKey;", instance("take"));
        put(m, "java/nio/file/WatchService.poll()Ljava/nio/file/WatchKey;", instance("poll"));
        put(m, "java/nio/file/WatchService.close()V", instance("close"));
        put(m, "java/nio/file/WatchKey.pollEvents()Ljava/util/List;", instance("pollEvents"));
        put(m, "java/nio/file/WatchKey.reset()Z", instance("reset"));
        put(m, "java/nio/file/WatchKey.cancel()V", instance("cancel"));
        put(m, "java/nio/file/WatchKey.isValid()Z", instance("isValid"));
        put(m, "java/nio/file/WatchEvent.kind()Ljava/nio/file/WatchEvent$Kind;", instance("kind"));
        put(m, "java/nio/file/WatchEvent.context()Ljava/lang/Object;", instance("context"));
        put(m, "java/nio/file/WatchEvent.count()I", instance("count"));
        put(m, "java/nio/file/WatchEvent$Kind.name()Ljava/lang/String;", instance("name"));
    }

    private static Map<String, ShimTarget> buildFields() {
        Map<String, ShimTarget> m = new java.util.HashMap<>(256);
        addLangFields0(m);
        addNumberFields0(m);
        addConcurrentFields0(m);
        addTimeFields0(m);
        addNioFields0(m);
        addLangFields1(m);
        addAwtFields0(m);
        addRegexFields0(m);
        addAwtFields1(m);
        addSwingFields0(m);
        addAwtFields2(m);
        addUtilLoggingFields(m);
        return Map.copyOf(m);
    }

    private static void addUtilLoggingFields(Map<String, ShimTarget> m) {
        put(m, "java/util/logging/Level.OFF Ljava/util/logging/Level;", statics("OFF"));
        put(m, "java/util/logging/Level.SEVERE Ljava/util/logging/Level;", statics("SEVERE"));
        put(m, "java/util/logging/Level.WARNING Ljava/util/logging/Level;", statics("WARNING"));
        put(m, "java/util/logging/Level.INFO Ljava/util/logging/Level;", statics("INFO"));
        put(m, "java/util/logging/Level.CONFIG Ljava/util/logging/Level;", statics("CONFIG"));
        put(m, "java/util/logging/Level.FINE Ljava/util/logging/Level;", statics("FINE"));
        put(m, "java/util/logging/Level.FINER Ljava/util/logging/Level;", statics("FINER"));
        put(m, "java/util/logging/Level.FINEST Ljava/util/logging/Level;", statics("FINEST"));
        put(m, "java/util/logging/Level.ALL Ljava/util/logging/Level;", statics("ALL"));
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

    private static void addNumberFields0(Map<String, ShimTarget> m) {
        put(m, "java/lang/Short.SIZE I", statics("SIZE"));
        put(m, "java/lang/Short.BYTES I", statics("BYTES"));
        put(m, "java/lang/Byte.SIZE I", statics("SIZE"));
        put(m, "java/lang/Byte.BYTES I", statics("BYTES"));
        put(m, "java/lang/Double.SIZE I", statics("SIZE"));
        put(m, "java/lang/Double.BYTES I", statics("BYTES"));
        put(m, "java/lang/Double.MIN_NORMAL D", statics("MIN_NORMAL"));
        put(m, "java/lang/Double.MAX_EXPONENT I", statics("MAX_EXPONENT"));
        put(m, "java/lang/Double.MIN_EXPONENT I", statics("MIN_EXPONENT"));
        put(m, "java/lang/Float.SIZE I", statics("SIZE"));
        put(m, "java/lang/Float.BYTES I", statics("BYTES"));
        put(m, "java/lang/Float.MIN_NORMAL F", statics("MIN_NORMAL"));
        put(m, "java/lang/Float.MAX_EXPONENT I", statics("MAX_EXPONENT"));
        put(m, "java/lang/Float.MIN_EXPONENT I", statics("MIN_EXPONENT"));
        put(m, "java/lang/Float.POSITIVE_INFINITY F", statics("POSITIVE_INFINITY"));
        put(m, "java/lang/Float.NEGATIVE_INFINITY F", statics("NEGATIVE_INFINITY"));
        put(m, "java/lang/Float.NaN F", statics("NaN"));
        put(m, "java/lang/StrictMath.PI D", statics("PI"));
        put(m, "java/lang/StrictMath.E D", statics("E"));
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
        put(m, "java/nio/file/StandardWatchEventKinds.ENTRY_CREATE Ljava/nio/file/WatchEvent$Kind;", statics("ENTRY_CREATE"));
        put(m, "java/nio/file/StandardWatchEventKinds.ENTRY_DELETE Ljava/nio/file/WatchEvent$Kind;", statics("ENTRY_DELETE"));
        put(m, "java/nio/file/StandardWatchEventKinds.ENTRY_MODIFY Ljava/nio/file/WatchEvent$Kind;", statics("ENTRY_MODIFY"));
        put(m, "java/nio/file/StandardWatchEventKinds.OVERFLOW Ljava/nio/file/WatchEvent$Kind;", statics("OVERFLOW"));
        put(m, "javax/crypto/Cipher.ENCRYPT_MODE I", statics("ENCRYPT_MODE"));
        put(m, "javax/crypto/Cipher.DECRYPT_MODE I", statics("DECRYPT_MODE"));
        put(m, "javax/crypto/Cipher.WRAP_MODE I", statics("WRAP_MODE"));
        put(m, "javax/crypto/Cipher.UNWRAP_MODE I", statics("UNWRAP_MODE"));
        put(m, "java/util/Calendar.ERA I", statics("ERA"));
        put(m, "java/util/Calendar.YEAR I", statics("YEAR"));
        put(m, "java/util/Calendar.MONTH I", statics("MONTH"));
        put(m, "java/util/Calendar.WEEK_OF_YEAR I", statics("WEEK_OF_YEAR"));
        put(m, "java/util/Calendar.WEEK_OF_MONTH I", statics("WEEK_OF_MONTH"));
        put(m, "java/util/Calendar.DATE I", statics("DATE"));
        put(m, "java/util/Calendar.DAY_OF_MONTH I", statics("DAY_OF_MONTH"));
        put(m, "java/util/Calendar.DAY_OF_YEAR I", statics("DAY_OF_YEAR"));
        put(m, "java/util/Calendar.DAY_OF_WEEK I", statics("DAY_OF_WEEK"));
        put(m, "java/util/Calendar.DAY_OF_WEEK_IN_MONTH I", statics("DAY_OF_WEEK_IN_MONTH"));
        put(m, "java/util/Calendar.AM_PM I", statics("AM_PM"));
        put(m, "java/util/Calendar.HOUR I", statics("HOUR"));
        put(m, "java/util/Calendar.HOUR_OF_DAY I", statics("HOUR_OF_DAY"));
        put(m, "java/util/Calendar.MINUTE I", statics("MINUTE"));
        put(m, "java/util/Calendar.SECOND I", statics("SECOND"));
        put(m, "java/util/Calendar.MILLISECOND I", statics("MILLISECOND"));
        put(m, "java/util/Calendar.ZONE_OFFSET I", statics("ZONE_OFFSET"));
        put(m, "java/util/Calendar.DST_OFFSET I", statics("DST_OFFSET"));
        put(m, "java/util/Calendar.JANUARY I", statics("JANUARY"));
        put(m, "java/util/Calendar.FEBRUARY I", statics("FEBRUARY"));
        put(m, "java/util/Calendar.MARCH I", statics("MARCH"));
        put(m, "java/util/Calendar.APRIL I", statics("APRIL"));
        put(m, "java/util/Calendar.MAY I", statics("MAY"));
        put(m, "java/util/Calendar.JUNE I", statics("JUNE"));
        put(m, "java/util/Calendar.JULY I", statics("JULY"));
        put(m, "java/util/Calendar.AUGUST I", statics("AUGUST"));
        put(m, "java/util/Calendar.SEPTEMBER I", statics("SEPTEMBER"));
        put(m, "java/util/Calendar.OCTOBER I", statics("OCTOBER"));
        put(m, "java/util/Calendar.NOVEMBER I", statics("NOVEMBER"));
        put(m, "java/util/Calendar.DECEMBER I", statics("DECEMBER"));
        put(m, "java/util/Calendar.SUNDAY I", statics("SUNDAY"));
        put(m, "java/util/Calendar.MONDAY I", statics("MONDAY"));
        put(m, "java/util/Calendar.TUESDAY I", statics("TUESDAY"));
        put(m, "java/util/Calendar.WEDNESDAY I", statics("WEDNESDAY"));
        put(m, "java/util/Calendar.THURSDAY I", statics("THURSDAY"));
        put(m, "java/util/Calendar.FRIDAY I", statics("FRIDAY"));
        put(m, "java/util/Calendar.SATURDAY I", statics("SATURDAY"));
        put(m, "java/util/Calendar.AM I", statics("AM"));
        put(m, "java/util/Calendar.PM I", statics("PM"));
        put(m, "java/time/Month.JANUARY Ljava/time/Month;", statics("JANUARY"));
        put(m, "java/time/Month.FEBRUARY Ljava/time/Month;", statics("FEBRUARY"));
        put(m, "java/time/Month.MARCH Ljava/time/Month;", statics("MARCH"));
        put(m, "java/time/Month.APRIL Ljava/time/Month;", statics("APRIL"));
        put(m, "java/time/Month.MAY Ljava/time/Month;", statics("MAY"));
        put(m, "java/time/Month.JUNE Ljava/time/Month;", statics("JUNE"));
        put(m, "java/time/Month.JULY Ljava/time/Month;", statics("JULY"));
        put(m, "java/time/Month.AUGUST Ljava/time/Month;", statics("AUGUST"));
        put(m, "java/time/Month.SEPTEMBER Ljava/time/Month;", statics("SEPTEMBER"));
        put(m, "java/time/Month.OCTOBER Ljava/time/Month;", statics("OCTOBER"));
        put(m, "java/time/Month.NOVEMBER Ljava/time/Month;", statics("NOVEMBER"));
        put(m, "java/time/Month.DECEMBER Ljava/time/Month;", statics("DECEMBER"));
        put(m, "java/util/Locale.ROOT Ljava/util/Locale;", statics("ROOT"));
        put(m, "java/util/Locale.ENGLISH Ljava/util/Locale;", statics("ENGLISH"));
        put(m, "java/util/Locale.US Ljava/util/Locale;", statics("US"));
        put(m, "java/util/Locale.UK Ljava/util/Locale;", statics("UK"));
        put(m, "java/math/BigInteger.ZERO Ljava/math/BigInteger;", statics("ZERO"));
        put(m, "java/math/BigInteger.ONE Ljava/math/BigInteger;", statics("ONE"));
        put(m, "java/math/BigInteger.TWO Ljava/math/BigInteger;", statics("TWO"));
        put(m, "java/math/BigInteger.TEN Ljava/math/BigInteger;", statics("TEN"));
        put(m, "java/math/RoundingMode.UP Ljava/math/RoundingMode;", statics("UP"));
        put(m, "java/math/RoundingMode.DOWN Ljava/math/RoundingMode;", statics("DOWN"));
        put(m, "java/math/RoundingMode.CEILING Ljava/math/RoundingMode;", statics("CEILING"));
        put(m, "java/math/RoundingMode.FLOOR Ljava/math/RoundingMode;", statics("FLOOR"));
        put(m, "java/math/RoundingMode.HALF_UP Ljava/math/RoundingMode;", statics("HALF_UP"));
        put(m, "java/math/RoundingMode.HALF_DOWN Ljava/math/RoundingMode;", statics("HALF_DOWN"));
        put(m, "java/math/RoundingMode.HALF_EVEN Ljava/math/RoundingMode;", statics("HALF_EVEN"));
        put(m, "java/math/RoundingMode.UNNECESSARY Ljava/math/RoundingMode;", statics("UNNECESSARY"));
        put(m, "java/nio/charset/StandardCharsets.UTF_8 Ljava/nio/charset/Charset;", statics("UTF_8"));
        put(m, "java/nio/charset/StandardCharsets.US_ASCII Ljava/nio/charset/Charset;", statics("US_ASCII"));
        put(m, "java/nio/charset/StandardCharsets.ISO_8859_1 Ljava/nio/charset/Charset;", statics("ISO_8859_1"));
        put(m, "java/nio/charset/StandardCharsets.UTF_16BE Ljava/nio/charset/Charset;", statics("UTF_16BE"));
        put(m, "java/nio/charset/StandardCharsets.UTF_16LE Ljava/nio/charset/Charset;", statics("UTF_16LE"));
        put(m, "java/nio/charset/StandardCharsets.UTF_16 Ljava/nio/charset/Charset;", statics("UTF_16"));
        put(m, "java/nio/file/StandardCopyOption.REPLACE_EXISTING Ljava/nio/file/StandardCopyOption;", statics("REPLACE_EXISTING"));
        put(m, "java/nio/file/StandardCopyOption.ATOMIC_MOVE Ljava/nio/file/StandardCopyOption;", statics("ATOMIC_MOVE"));
        put(m, "java/nio/file/StandardCopyOption.COPY_ATTRIBUTES Ljava/nio/file/StandardCopyOption;", statics("COPY_ATTRIBUTES"));
        put(m, "java/nio/file/StandardOpenOption.READ Ljava/nio/file/StandardOpenOption;", statics("READ"));
        put(m, "java/nio/file/StandardOpenOption.WRITE Ljava/nio/file/StandardOpenOption;", statics("WRITE"));
        put(m, "java/nio/file/StandardOpenOption.APPEND Ljava/nio/file/StandardOpenOption;", statics("APPEND"));
        put(m, "java/nio/file/StandardOpenOption.CREATE Ljava/nio/file/StandardOpenOption;", statics("CREATE"));
        put(m, "java/nio/file/StandardOpenOption.CREATE_NEW Ljava/nio/file/StandardOpenOption;", statics("CREATE_NEW"));
        put(m, "java/nio/file/StandardOpenOption.TRUNCATE_EXISTING Ljava/nio/file/StandardOpenOption;", statics("TRUNCATE_EXISTING"));
        put(m, "java/nio/file/StandardOpenOption.DELETE_ON_CLOSE Ljava/nio/file/StandardOpenOption;", statics("DELETE_ON_CLOSE"));
        put(m, "java/nio/file/LinkOption.NOFOLLOW_LINKS Ljava/nio/file/LinkOption;", statics("NOFOLLOW_LINKS"));
        put(m, "java/io/File.separator Ljava/lang/String;", statics("separator"));
        put(m, "java/io/File.separatorChar C", statics("separatorChar"));
        put(m, "java/io/File.pathSeparator Ljava/lang/String;", statics("pathSeparator"));
        put(m, "java/io/File.pathSeparatorChar C", statics("pathSeparatorChar"));
        put(m, "java/nio/ByteOrder.BIG_ENDIAN Ljava/nio/ByteOrder;", statics("BIG_ENDIAN"));
        put(m, "java/nio/ByteOrder.LITTLE_ENDIAN Ljava/nio/ByteOrder;", statics("LITTLE_ENDIAN"));
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

    // A concrete shim class can serve as a C# base: any shim type whose C# form is a non-sealed class
    // (not an interface, not a sealed class, not Object). The hand-curated EXTENDABLE set is kept as a
    // union so nothing that was extendable stops being so. Deriving this from the shim's C# shape means
    // an app class extending a shim class inherits its members instead of being dropped to Object.
    public static boolean isExtendable(String internalName) {
        if (EXTENDABLE.contains(internalName)) {
            return true;
        }
        return isShimType(internalName)
                && !internalName.equals("java/lang/Object")
                && !SHIM_INTERFACES.contains(internalName)
                && !SEALED_SHIM_CLASSES.contains(internalName);
    }

    public static boolean isShimInterface(String internalName) {
        return SHIM_INTERFACES.contains(internalName);
    }

    public static Set<String> shimInterfaces() {
        return SHIM_INTERFACES;
    }

    public static Set<String> sealedShimClasses() {
        return SEALED_SHIM_CLASSES;
    }

    // Shim types whose C# form is a sealed class (declared `public sealed class` in javacompat), so
    // they cannot be a C# base — an app class extending one stays dropped to Object. These are final
    // Java types plus a few abstract types shimmed as sealed. Kept in sync by ShimSealedDriftTest.
    static final Set<String> SEALED_SHIM_CLASSES = Set.of(
            "j2cs/reflect/Meta",
            "java/awt/RenderingHints$Key",
            "java/io/File",
            "java/lang/Boolean",
            "java/lang/Byte",
            "java/lang/Character",
            "java/lang/Double",
            "java/lang/Float",
            "java/lang/Integer",
            "java/lang/JThrow",
            "java/lang/Long",
            "java/lang/Math",
            "java/lang/Package",
            "java/lang/RawNew",
            "java/lang/Short",
            "java/lang/StrictMath",
            "java/lang/Void",
            "java/lang/reflect/Array",
            "java/lang/reflect/Constructor",
            "java/lang/reflect/Field",
            "java/lang/reflect/Method",
            "java/lang/reflect/Modifier",
            "java/math/BigDecimal",
            "java/math/BigInteger",
            "java/math/RoundingMode",
            "java/net/URI",
            "java/net/URL",
            "java/nio/ByteOrder",
            "java/nio/CharBuffer",
            "java/nio/DoubleBuffer",
            "java/nio/FloatBuffer",
            "java/nio/IntBuffer",
            "java/nio/LongBuffer",
            "java/nio/ShortBuffer",
            "java/nio/file/FileSystem",
            "java/nio/file/FileSystems",
            "java/nio/file/Files",
            "java/nio/file/LinkOption",
            "java/nio/file/Path",
            "java/nio/file/Paths",
            "java/nio/file/StandardCopyOption",
            "java/nio/file/StandardOpenOption",
            "java/nio/file/attribute/BasicFileAttributes",
            "java/time/DayOfWeek",
            "java/time/Duration",
            "java/time/Instant",
            "java/time/LocalDate",
            "java/time/LocalTime",
            "java/time/Month",
            "java/time/Period",
            "java/time/Year",
            "java/util/Collections",
            "java/util/Currency",
            "java/util/DoubleSummaryStatistics",
            "java/util/EnumSet",
            "java/util/Formatter",
            "java/util/IntSummaryStatistics",
            "java/util/Locale",
            "java/util/LongSummaryStatistics",
            "java/util/Objects",
            "java/util/Optional",
            "java/util/OptionalDouble",
            "java/util/OptionalInt",
            "java/util/OptionalLong",
            "java/util/Scanner",
            "java/util/StringJoiner",
            "java/util/StringTokenizer",
            "java/util/concurrent/CompletableFuture",
            "java/util/concurrent/Executors",
            "java/util/concurrent/ScheduledFuture",
            "java/util/concurrent/ThreadLocalRandom",
            "java/util/regex/Matcher",
            "java/util/regex/Pattern",
            "java/util/stream/Collector",
            "java/util/stream/Collectors",
            "java/util/stream/DoubleStream",
            "java/util/stream/IntStream",
            "java/util/stream/LongStream",
            "java/util/stream/Stream");

    // Shim types whose C# form is an interface (declared `public interface` in javacompat). Like an
    // app interface, a C# interface does not expose the java.lang.Object shim's members, so an
    // Object-member call on a receiver of one of these types must upcast to Object first. Kept in
    // sync with the interface .cs files by ShimInterfaceDriftTest.
    static final Set<String> SHIM_INTERFACES = Set.of(
            "java/awt/KeyEventDispatcher",
            "java/awt/LayoutManager",
            "java/awt/event/ActionListener",
            "java/awt/event/KeyListener",
            "java/awt/event/MouseListener",
            "java/awt/event/MouseMotionListener",
            "java/awt/event/WindowListener",
            "java/io/Closeable",
            "java/lang/AutoCloseable",
            "java/lang/CharSequence",
            "java/lang/Comparable",
            "java/lang/Iterable",
            "java/lang/Runnable",
            "java/lang/annotation/Annotation",
            "java/nio/file/CopyOption",
            "java/nio/file/FileVisitOption",
            "java/nio/file/OpenOption",
            "java/nio/file/attribute/FileAttribute",
            "java/security/Key",
            "java/security/PrivateKey",
            "java/security/PublicKey",
            "java/security/spec/AlgorithmParameterSpec",
            "java/time/chrono/ChronoLocalDate",
            "java/time/chrono/ChronoLocalDateTime",
            "java/time/temporal/Temporal",
            "java/util/Collection",
            "java/util/Comparator",
            "java/util/Deque",
            "java/util/Enumeration",
            "java/util/Iterator",
            "java/util/List",
            "java/util/ListIterator",
            "java/util/Map",
            "java/util/Map$Entry",
            "java/util/NavigableMap",
            "java/util/NavigableSet",
            "java/util/Queue",
            "java/util/Set",
            "java/util/SortedMap",
            "java/util/SortedSet",
            "java/util/concurrent/BlockingQueue",
            "java/util/concurrent/Callable",
            "java/util/concurrent/ConcurrentMap",
            "java/util/concurrent/Executor",
            "java/util/concurrent/Future",
            "java/util/concurrent/ThreadFactory",
            "java/util/concurrent/locks/Lock",
            "java/util/function/BiConsumer",
            "java/util/function/BiFunction",
            "java/util/function/BiPredicate",
            "java/util/function/BinaryOperator",
            "java/util/function/Consumer",
            "java/util/function/DoubleBinaryOperator",
            "java/util/function/DoubleConsumer",
            "java/util/function/DoubleFunction",
            "java/util/function/DoublePredicate",
            "java/util/function/DoubleToIntFunction",
            "java/util/function/DoubleToLongFunction",
            "java/util/function/DoubleUnaryOperator",
            "java/util/function/Function",
            "java/util/function/IntBinaryOperator",
            "java/util/function/IntConsumer",
            "java/util/function/IntFunction",
            "java/util/function/IntPredicate",
            "java/util/function/IntToDoubleFunction",
            "java/util/function/IntToLongFunction",
            "java/util/function/IntUnaryOperator",
            "java/util/function/LongBinaryOperator",
            "java/util/function/LongConsumer",
            "java/util/function/LongFunction",
            "java/util/function/LongPredicate",
            "java/util/function/LongToDoubleFunction",
            "java/util/function/LongToIntFunction",
            "java/util/function/LongUnaryOperator",
            "java/util/function/Predicate",
            "java/util/function/Supplier",
            "java/util/function/ToDoubleFunction",
            "java/util/function/ToIntFunction",
            "java/util/function/ToLongFunction",
            "java/util/function/UnaryOperator",
            "javax/crypto/SecretKey",
            "javax/swing/ListModel",
            "javax/swing/event/ChangeListener");

    public static boolean isThrowableSubtype(String internalName) {
        return isShimSubtype(internalName, "java/lang/Throwable");
    }

    /**
     * Whether {@code sub} is {@code sup} or a shim subtype of it, following the shim class
     * super-chain. Only class supertypes are modeled (the chain is single-parent), so shim
     * interface relationships are not covered.
     */
    public static boolean isShimSubtype(String sub, String sup) {
        String current = sub;
        while (current != null) {
            if (current.equals(sup)) {
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
            "java/util/Collection.addAll(Ljava/util/Collection;)Z",
            "java/util/Collection.containsAll(Ljava/util/Collection;)Z",
            "java/util/Collection.removeAll(Ljava/util/Collection;)Z",
            "java/util/Collection.retainAll(Ljava/util/Collection;)Z",
            "java/util/Collection.toArray()[Ljava/lang/Object;",
            "java/util/Collection.toArray([Ljava/lang/Object;)[Ljava/lang/Object;",
            "java/util/List.addAll(ILjava/util/Collection;)Z",
            "java/util/Map.getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            "java/util/Map.putAll(Ljava/util/Map;)V",
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
            "java/util/List.sort(Ljava/util/Comparator;)V",
            "java/util/List.listIterator()Ljava/util/ListIterator;",
            "java/util/List.listIterator(I)Ljava/util/ListIterator;");

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

    /**
     * Instance virtuals of a shim interface and every shim supertype it inherits — so a class
     * implementing e.g. java/util/List also reserves java/util/Collection.size() and
     * java/util/Iterator.next(), which are declared on the super-interfaces. Walks the single-super
     * chain in SHIM_SUPERS (java.util's interface hierarchy is linear); nearest declaration wins.
     */
    public static Map<String, String> transitiveInstanceVirtualsOf(String owner) {
        Map<String, String> result = new java.util.LinkedHashMap<>();
        String current = owner;
        while (current != null && !current.equals("java/lang/Object")) {
            for (Map.Entry<String, String> entry : instanceVirtualsOf(current).entrySet()) {
                result.putIfAbsent(entry.getKey(), entry.getValue());
            }
            current = SHIM_SUPERS.get(current);
        }
        return result;
    }

    /**
     * Like {@link #transitiveInstanceVirtualsOf} but only the methods a class must actually implement:
     * excludes those the shim interface provides as C# default interface methods. Used to re-declare
     * the still-abstract members as {@code abstract} on an abstract class implementing the interface.
     */
    public static Map<String, String> transitiveAbstractVirtualsOf(String owner) {
        Map<String, String> result = new java.util.LinkedHashMap<>();
        String current = owner;
        while (current != null && !current.equals("java/lang/Object")) {
            String prefix = current + ".";
            for (Map.Entry<String, ShimTarget> entry : METHODS.entrySet()) {
                if (entry.getKey().startsWith(prefix) && !entry.getValue().isStatic()
                        && !DEFAULT_INTERFACE_METHODS.contains(entry.getKey())) {
                    result.putIfAbsent(entry.getKey().substring(prefix.length()), entry.getValue().csMemberName());
                }
            }
            current = SHIM_SUPERS.get(current);
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
