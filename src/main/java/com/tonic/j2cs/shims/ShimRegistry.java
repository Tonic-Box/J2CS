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

    private static final Set<String> TYPES = Set.of(
            "java/lang/Object",
            "java/lang/String",
            "java/lang/StringBuilder",
            "java/lang/System",
            "java/lang/Math",
            "java/lang/Integer",
            "java/io/PrintStream",
            "java/lang/Throwable",
            "java/lang/Exception",
            "java/lang/RuntimeException",
            "java/lang/Error",
            "java/lang/AssertionError",
            "java/lang/NullPointerException",
            "java/lang/ArithmeticException",
            "java/lang/ClassCastException",
            "java/lang/IndexOutOfBoundsException",
            "java/lang/ArrayIndexOutOfBoundsException",
            "java/lang/ArrayStoreException",
            "java/lang/IllegalArgumentException",
            "java/lang/NumberFormatException",
            "java/lang/IllegalStateException",
            "java/util/Objects",
            "java/lang/Number",
            "java/lang/Long",
            "java/lang/Double",
            "java/lang/Float",
            "java/lang/Boolean",
            "java/lang/Character",
            "java/lang/Short",
            "java/lang/Byte",
            "java/util/Iterable",
            "java/util/Iterator",
            "java/util/Collection",
            "java/util/List",
            "java/util/ArrayList",
            "java/util/Set",
            "java/util/Map",
            "java/util/Map$Entry",
            "java/util/HashMap",
            "java/util/HashSet",
            "java/util/UUID",
            "java/lang/InterruptedException",
            "java/lang/Thread",
            "java/util/concurrent/TimeUnit",
            "java/util/concurrent/locks/Lock",
            "java/util/concurrent/locks/ReentrantLock",
            "java/time/LocalDateTime",
            "java/time/format/DateTimeFormatter",
            "java/time/temporal/ChronoUnit",
            "java/time/temporal/Temporal",
            "java/time/chrono/ChronoLocalDateTime",
            "java/security/MessageDigest",
            "java/security/SecureRandom",
            "java/util/Base64",
            "java/util/Base64$Encoder",
            "java/util/Base64$Decoder",
            "java/nio/charset/Charset",
            "java/nio/charset/StandardCharsets",
            "java/lang/Enum",
            "java/lang/Class",
            "java/lang/Runnable",
            "java/util/function/Predicate",
            "java/util/LinkedList",
            "java/util/LinkedHashMap",
            "java/util/TreeMap",
            "java/lang/StringBuffer",
            "java/lang/NoSuchFieldError",
            "java/awt/Component",
            "java/awt/Container",
            "java/awt/Window",
            "java/awt/Frame",
            "javax/swing/JFrame",
            "javax/swing/JDialog",
            "javax/swing/SwingUtilities",
            "java/awt/LayoutManager",
            "java/awt/BorderLayout",
            "java/awt/FlowLayout",
            "java/awt/GridLayout",
            "java/awt/GridBagLayout",
            "java/awt/GridBagConstraints",
            "java/awt/Insets",
            "java/awt/CardLayout",
            "java/awt/Dimension",
            "java/awt/event/ActionListener",
            "java/awt/event/ActionEvent",
            "javax/swing/JPanel",
            "javax/swing/JButton",
            "javax/swing/JLabel",
            "javax/swing/JTextField",
            "javax/swing/JList",
            "javax/swing/JScrollPane",
            "javax/swing/ListModel",
            "javax/swing/DefaultListModel",
            "javax/swing/JPasswordField",
            "javax/swing/JProgressBar",
            "javax/swing/BoxLayout",
            "javax/swing/Box",
            "javax/swing/BorderFactory",
            "javax/swing/border/Border",
            "javax/swing/JOptionPane",
            "java/awt/Color",
            "java/awt/Font",
            "java/awt/event/KeyListener",
            "java/awt/event/KeyAdapter",
            "java/awt/event/KeyEvent",
            "java/util/Arrays");

    private static final Map<String, String> SHIM_SUPERS = Map.<String, String>ofEntries(
            Map.entry("java/lang/String", "java/lang/Object"),
            Map.entry("java/lang/StringBuilder", "java/lang/Object"),
            Map.entry("java/lang/System", "java/lang/Object"),
            Map.entry("java/lang/Math", "java/lang/Object"),
            Map.entry("java/lang/Number", "java/lang/Object"),
            Map.entry("java/lang/Integer", "java/lang/Number"),
            Map.entry("java/lang/Long", "java/lang/Number"),
            Map.entry("java/lang/Double", "java/lang/Number"),
            Map.entry("java/lang/Float", "java/lang/Number"),
            Map.entry("java/lang/Short", "java/lang/Number"),
            Map.entry("java/lang/Byte", "java/lang/Number"),
            Map.entry("java/lang/Boolean", "java/lang/Object"),
            Map.entry("java/lang/Character", "java/lang/Object"),
            Map.entry("java/util/Iterable", "java/lang/Object"),
            Map.entry("java/util/Iterator", "java/lang/Object"),
            Map.entry("java/util/Collection", "java/util/Iterable"),
            Map.entry("java/util/List", "java/util/Collection"),
            Map.entry("java/util/ArrayList", "java/util/List"),
            Map.entry("java/util/Set", "java/util/Collection"),
            Map.entry("java/util/Map", "java/lang/Object"),
            Map.entry("java/util/Map$Entry", "java/lang/Object"),
            Map.entry("java/util/HashMap", "java/util/Map"),
            Map.entry("java/util/HashSet", "java/util/Set"),
            Map.entry("java/util/UUID", "java/lang/Object"),
            Map.entry("java/lang/InterruptedException", "java/lang/Exception"),
            Map.entry("java/lang/Thread", "java/lang/Object"),
            Map.entry("java/util/concurrent/TimeUnit", "java/lang/Object"),
            Map.entry("java/util/concurrent/locks/Lock", "java/lang/Object"),
            Map.entry("java/util/concurrent/locks/ReentrantLock", "java/lang/Object"),
            Map.entry("java/time/LocalDateTime", "java/lang/Object"),
            Map.entry("java/time/format/DateTimeFormatter", "java/lang/Object"),
            Map.entry("java/time/temporal/ChronoUnit", "java/lang/Object"),
            Map.entry("java/time/temporal/Temporal", "java/lang/Object"),
            Map.entry("java/time/chrono/ChronoLocalDateTime", "java/lang/Object"),
            Map.entry("java/security/MessageDigest", "java/lang/Object"),
            Map.entry("java/security/SecureRandom", "java/lang/Object"),
            Map.entry("java/util/Base64", "java/lang/Object"),
            Map.entry("java/util/Base64$Encoder", "java/lang/Object"),
            Map.entry("java/util/Base64$Decoder", "java/lang/Object"),
            Map.entry("java/nio/charset/Charset", "java/lang/Object"),
            Map.entry("java/nio/charset/StandardCharsets", "java/lang/Object"),
            Map.entry("java/lang/Enum", "java/lang/Object"),
            Map.entry("java/lang/Class", "java/lang/Object"),
            Map.entry("java/lang/Runnable", "java/lang/Object"),
            Map.entry("java/util/function/Predicate", "java/lang/Object"),
            Map.entry("java/util/LinkedList", "java/util/ArrayList"),
            Map.entry("java/util/LinkedHashMap", "java/util/HashMap"),
            Map.entry("java/util/TreeMap", "java/util/HashMap"),
            Map.entry("java/lang/StringBuffer", "java/lang/Object"),
            Map.entry("java/lang/NoSuchFieldError", "java/lang/Error"),
            Map.entry("java/awt/Component", "java/lang/Object"),
            Map.entry("java/awt/Container", "java/awt/Component"),
            Map.entry("java/awt/Window", "java/awt/Container"),
            Map.entry("java/awt/Frame", "java/awt/Window"),
            Map.entry("javax/swing/JFrame", "java/awt/Frame"),
            Map.entry("javax/swing/JDialog", "java/awt/Window"),
            Map.entry("javax/swing/SwingUtilities", "java/lang/Object"),
            Map.entry("java/awt/LayoutManager", "java/lang/Object"),
            Map.entry("java/awt/BorderLayout", "java/lang/Object"),
            Map.entry("java/awt/FlowLayout", "java/lang/Object"),
            Map.entry("java/awt/GridLayout", "java/lang/Object"),
            Map.entry("java/awt/GridBagLayout", "java/lang/Object"),
            Map.entry("java/awt/GridBagConstraints", "java/lang/Object"),
            Map.entry("java/awt/Insets", "java/lang/Object"),
            Map.entry("java/awt/CardLayout", "java/lang/Object"),
            Map.entry("java/awt/Dimension", "java/lang/Object"),
            Map.entry("java/awt/event/ActionListener", "java/lang/Object"),
            Map.entry("java/awt/event/ActionEvent", "java/lang/Object"),
            Map.entry("javax/swing/JPanel", "java/awt/Container"),
            Map.entry("javax/swing/JButton", "java/awt/Component"),
            Map.entry("javax/swing/JLabel", "java/awt/Component"),
            Map.entry("javax/swing/JTextField", "java/awt/Component"),
            Map.entry("javax/swing/JList", "java/awt/Component"),
            Map.entry("javax/swing/JScrollPane", "java/awt/Component"),
            Map.entry("javax/swing/ListModel", "java/lang/Object"),
            Map.entry("javax/swing/DefaultListModel", "java/lang/Object"),
            Map.entry("javax/swing/JPasswordField", "javax/swing/JTextField"),
            Map.entry("javax/swing/JProgressBar", "java/awt/Component"),
            Map.entry("javax/swing/BoxLayout", "java/lang/Object"),
            Map.entry("javax/swing/Box", "java/lang/Object"),
            Map.entry("javax/swing/BorderFactory", "java/lang/Object"),
            Map.entry("javax/swing/border/Border", "java/lang/Object"),
            Map.entry("javax/swing/JOptionPane", "java/lang/Object"),
            Map.entry("java/awt/Color", "java/lang/Object"),
            Map.entry("java/awt/Font", "java/lang/Object"),
            Map.entry("java/awt/event/KeyListener", "java/lang/Object"),
            Map.entry("java/awt/event/KeyAdapter", "java/lang/Object"),
            Map.entry("java/awt/event/KeyEvent", "java/lang/Object"),
            Map.entry("java/util/Arrays", "java/lang/Object"),
            Map.entry("java/io/PrintStream", "java/lang/Object"),
            Map.entry("java/lang/Throwable", "java/lang/Object"),
            Map.entry("java/lang/Exception", "java/lang/Throwable"),
            Map.entry("java/lang/RuntimeException", "java/lang/Exception"),
            Map.entry("java/lang/Error", "java/lang/Throwable"),
            Map.entry("java/lang/AssertionError", "java/lang/Error"),
            Map.entry("java/lang/NullPointerException", "java/lang/RuntimeException"),
            Map.entry("java/lang/ArithmeticException", "java/lang/RuntimeException"),
            Map.entry("java/lang/ClassCastException", "java/lang/RuntimeException"),
            Map.entry("java/lang/IndexOutOfBoundsException", "java/lang/RuntimeException"),
            Map.entry("java/lang/ArrayIndexOutOfBoundsException", "java/lang/IndexOutOfBoundsException"),
            Map.entry("java/lang/ArrayStoreException", "java/lang/RuntimeException"),
            Map.entry("java/lang/IllegalArgumentException", "java/lang/RuntimeException"),
            Map.entry("java/lang/NumberFormatException", "java/lang/IllegalArgumentException"),
            Map.entry("java/lang/IllegalStateException", "java/lang/RuntimeException"));

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
            "java/lang/Enum",
            "java/lang/NoSuchFieldError",
            "javax/swing/JFrame",
            "javax/swing/JDialog",
            "java/awt/event/KeyAdapter");

    public static final Map<String, String> EXTENDABLE_VIRTUALS = Map.of(
            "getMessage()Ljava/lang/String;", "getMessage",
            "name()Ljava/lang/String;", "name",
            "ordinal()I", "ordinal",
            "keyPressed(Ljava/awt/event/KeyEvent;)V", "keyPressed",
            "keyReleased(Ljava/awt/event/KeyEvent;)V", "keyReleased",
            "keyTyped(Ljava/awt/event/KeyEvent;)V", "keyTyped");

    public static final Set<String> EXTENDABLE_MEMBER_NAMES = Set.of(
            "getMessage", "JavaClassName", "message", "__origin",
            "name", "ordinal", "__name", "__ordinal");

    public record WalkResult(String declaringInternal, ShimTarget target) {
    }

    private static final Map<String, ShimTarget> METHODS = Map.<String, ShimTarget>ofEntries(
            Map.entry("java/lang/Object.toString()Ljava/lang/String;", instance("toString")),
            Map.entry("java/lang/Object.hashCode()I", instance("hashCode")),
            Map.entry("java/lang/Object.equals(Ljava/lang/Object;)Z", instance("equals")),
            Map.entry("java/lang/String.length()I", instance("length")),
            Map.entry("java/lang/String.charAt(I)C", instance("charAt")),
            Map.entry("java/lang/String.isEmpty()Z", instance("isEmpty")),
            Map.entry("java/lang/String.equals(Ljava/lang/Object;)Z", instance("equals")),
            Map.entry("java/lang/String.hashCode()I", instance("hashCode")),
            Map.entry("java/lang/String.toString()Ljava/lang/String;", instance("toString")),
            Map.entry("java/io/PrintStream.println()V", instance("println")),
            Map.entry("java/io/PrintStream.println(I)V", instance("println")),
            Map.entry("java/io/PrintStream.println(J)V", instance("println")),
            Map.entry("java/io/PrintStream.println(F)V", instance("println")),
            Map.entry("java/io/PrintStream.println(D)V", instance("println")),
            Map.entry("java/io/PrintStream.println(C)V", instance("println")),
            Map.entry("java/io/PrintStream.println(Z)V", instance("println_Z")),
            Map.entry("java/io/PrintStream.println(Ljava/lang/String;)V", instance("println")),
            Map.entry("java/io/PrintStream.println(Ljava/lang/Object;)V", instance("println")),
            Map.entry("java/io/PrintStream.print(I)V", instance("print")),
            Map.entry("java/io/PrintStream.print(J)V", instance("print")),
            Map.entry("java/io/PrintStream.print(F)V", instance("print")),
            Map.entry("java/io/PrintStream.print(D)V", instance("print")),
            Map.entry("java/io/PrintStream.print(C)V", instance("print")),
            Map.entry("java/io/PrintStream.print(Z)V", instance("print_Z")),
            Map.entry("java/io/PrintStream.print(Ljava/lang/String;)V", instance("print")),
            Map.entry("java/io/PrintStream.print(Ljava/lang/Object;)V", instance("print")),
            Map.entry("java/lang/StringBuilder.append(I)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(J)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(F)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(D)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(C)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(Z)Ljava/lang/StringBuilder;", instance("append_Z")),
            Map.entry("java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.append(Ljava/lang/Object;)Ljava/lang/StringBuilder;", instance("append")),
            Map.entry("java/lang/StringBuilder.length()I", instance("length")),
            Map.entry("java/lang/StringBuilder.toString()Ljava/lang/String;", instance("toString")),
            Map.entry("java/lang/String.substring(I)Ljava/lang/String;", instance("substring")),
            Map.entry("java/lang/String.substring(II)Ljava/lang/String;", instance("substring")),
            Map.entry("java/lang/String.indexOf(I)I", instance("indexOf")),
            Map.entry("java/lang/String.indexOf(Ljava/lang/String;)I", instance("indexOf")),
            Map.entry("java/lang/String.startsWith(Ljava/lang/String;)Z", instance("startsWith")),
            Map.entry("java/lang/String.equalsIgnoreCase(Ljava/lang/String;)Z", instance("equalsIgnoreCase")),
            Map.entry("java/lang/Math.abs(I)I", statics("abs")),
            Map.entry("java/lang/Math.abs(D)D", statics("abs")),
            Map.entry("java/lang/Math.max(II)I", statics("max")),
            Map.entry("java/lang/Math.min(II)I", statics("min")),
            Map.entry("java/lang/Math.sqrt(D)D", statics("sqrt")),
            Map.entry("java/lang/Math.log(D)D", statics("log")),
            Map.entry("java/lang/Integer.parseInt(Ljava/lang/String;)I", statics("parseInt")),
            Map.entry("java/lang/Integer.toString(I)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Integer.valueOf(I)Ljava/lang/Integer;", statics("valueOf")),
            Map.entry("java/lang/Integer.compareTo(Ljava/lang/Integer;)I", instance("compareTo")),
            Map.entry("java/lang/Long.valueOf(J)Ljava/lang/Long;", statics("valueOf")),
            Map.entry("java/lang/Long.parseLong(Ljava/lang/String;)J", statics("parseLong")),
            Map.entry("java/lang/Long.toString(J)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Long.compareTo(Ljava/lang/Long;)I", instance("compareTo")),
            Map.entry("java/lang/Double.valueOf(D)Ljava/lang/Double;", statics("valueOf")),
            Map.entry("java/lang/Double.parseDouble(Ljava/lang/String;)D", statics("parseDouble")),
            Map.entry("java/lang/Double.toString(D)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Double.compareTo(Ljava/lang/Double;)I", instance("compareTo")),
            Map.entry("java/lang/Float.valueOf(F)Ljava/lang/Float;", statics("valueOf")),
            Map.entry("java/lang/Float.parseFloat(Ljava/lang/String;)F", statics("parseFloat")),
            Map.entry("java/lang/Float.toString(F)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Float.compareTo(Ljava/lang/Float;)I", instance("compareTo")),
            Map.entry("java/lang/Short.valueOf(S)Ljava/lang/Short;", statics("valueOf")),
            Map.entry("java/lang/Short.toString(S)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Short.compareTo(Ljava/lang/Short;)I", instance("compareTo")),
            Map.entry("java/lang/Byte.valueOf(B)Ljava/lang/Byte;", statics("valueOf")),
            Map.entry("java/lang/Byte.toString(B)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Byte.compareTo(Ljava/lang/Byte;)I", instance("compareTo")),
            Map.entry("java/lang/Boolean.valueOf(Z)Ljava/lang/Boolean;", statics("valueOf")),
            Map.entry("java/lang/Boolean.parseBoolean(Ljava/lang/String;)Z", statics("parseBoolean")),
            Map.entry("java/lang/Boolean.toString(Z)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Boolean.booleanValue()Z", instance("booleanValue")),
            Map.entry("java/lang/Boolean.compareTo(Ljava/lang/Boolean;)I", instance("compareTo")),
            Map.entry("java/lang/Character.valueOf(C)Ljava/lang/Character;", statics("valueOf")),
            Map.entry("java/lang/Character.toString(C)Ljava/lang/String;", statics("toString")),
            Map.entry("java/lang/Character.charValue()C", instance("charValue")),
            Map.entry("java/lang/Character.compareTo(Ljava/lang/Character;)I", instance("compareTo")),
            Map.entry("java/lang/Number.intValue()I", instance("intValue")),
            Map.entry("java/lang/Number.longValue()J", instance("longValue")),
            Map.entry("java/lang/Number.floatValue()F", instance("floatValue")),
            Map.entry("java/lang/Number.doubleValue()D", instance("doubleValue")),
            Map.entry("java/lang/Number.shortValue()S", instance("shortValue")),
            Map.entry("java/lang/Number.byteValue()B", instance("byteValue")),
            Map.entry("java/lang/System.arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V", statics("arraycopy")),
            Map.entry("java/lang/Throwable.getMessage()Ljava/lang/String;", instance("getMessage")),
            Map.entry("java/lang/Throwable.toString()Ljava/lang/String;", instance("toString")),
            Map.entry("java/lang/Throwable.printStackTrace()V", instance("printStackTrace")),
            Map.entry("java/lang/String.format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", statics("format")),
            Map.entry("java/lang/System.nanoTime()J", statics("nanoTime")),
            Map.entry("java/lang/System.currentTimeMillis()J", statics("currentTimeMillis")),
            Map.entry("java/lang/System.exit(I)V", statics("exit")),
            Map.entry("java/util/UUID.randomUUID()Ljava/util/UUID;", statics("randomUUID")),
            Map.entry("java/util/UUID.toString()Ljava/lang/String;", instance("toString")),
            Map.entry("java/lang/Thread.currentThread()Ljava/lang/Thread;", statics("currentThread")),
            Map.entry("java/lang/Thread.interrupt()V", instance("interrupt")),
            Map.entry("java/util/concurrent/locks/Lock.lock()V", instance("@lock")),
            Map.entry("java/util/concurrent/locks/Lock.unlock()V", instance("unlock")),
            Map.entry("java/util/concurrent/TimeUnit.sleep(J)V", instance("sleep")),
            Map.entry("java/util/concurrent/TimeUnit.toMillis(J)J", instance("toMillis")),
            Map.entry("java/time/LocalDateTime.now()Ljava/time/LocalDateTime;", statics("now")),
            Map.entry("java/time/LocalDateTime.minusMinutes(J)Ljava/time/LocalDateTime;", instance("minusMinutes")),
            Map.entry("java/time/LocalDateTime.plusMinutes(J)Ljava/time/LocalDateTime;", instance("plusMinutes")),
            Map.entry("java/time/LocalDateTime.isBefore(Ljava/time/chrono/ChronoLocalDateTime;)Z", instance("isBefore")),
            Map.entry("java/time/LocalDateTime.isAfter(Ljava/time/chrono/ChronoLocalDateTime;)Z", instance("isAfter")),
            Map.entry("java/time/LocalDateTime.format(Ljava/time/format/DateTimeFormatter;)Ljava/lang/String;", instance("format")),
            Map.entry("java/time/format/DateTimeFormatter.ofPattern(Ljava/lang/String;)Ljava/time/format/DateTimeFormatter;", statics("ofPattern")),
            Map.entry("java/time/temporal/ChronoUnit.between(Ljava/time/temporal/Temporal;Ljava/time/temporal/Temporal;)J", instance("between")),
            Map.entry("java/lang/String.getBytes(Ljava/nio/charset/Charset;)[B", instance("getBytes")),
            Map.entry("java/lang/String.trim()Ljava/lang/String;", instance("trim")),
            Map.entry("java/security/MessageDigest.getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;", statics("getInstance")),
            Map.entry("java/security/MessageDigest.digest([B)[B", instance("digest")),
            Map.entry("java/security/MessageDigest.reset()V", instance("reset")),
            Map.entry("java/security/SecureRandom.nextBytes([B)V", instance("nextBytes")),
            Map.entry("java/util/Base64.getEncoder()Ljava/util/Base64$Encoder;", statics("getEncoder")),
            Map.entry("java/util/Base64.getDecoder()Ljava/util/Base64$Decoder;", statics("getDecoder")),
            Map.entry("java/util/Base64$Encoder.encodeToString([B)Ljava/lang/String;", instance("encodeToString")),
            Map.entry("java/util/Base64$Decoder.decode(Ljava/lang/String;)[B", instance("decode")),
            Map.entry("java/lang/Enum.name()Ljava/lang/String;", instance("name")),
            Map.entry("java/lang/Enum.ordinal()I", instance("ordinal")),
            Map.entry("java/lang/Enum.compareTo(Ljava/lang/Enum;)I", instance("compareTo")),
            Map.entry("java/lang/Class.desiredAssertionStatus()Z", instance("desiredAssertionStatus")),
            Map.entry("java/lang/String.valueOf(Ljava/lang/Object;)Ljava/lang/String;", statics("valueOf")),
            Map.entry("java/lang/Class.getName()Ljava/lang/String;", instance("getName")),
            Map.entry("java/lang/Class.getSimpleName()Ljava/lang/String;", instance("getSimpleName")),
            Map.entry("java/lang/Runnable.run()V", instance("run")),
            Map.entry("java/util/function/Predicate.test(Ljava/lang/Object;)Z", instance("test")),
            Map.entry("java/util/List.clear()V", instance("clear")),
            Map.entry("java/util/List.removeIf(Ljava/util/function/Predicate;)Z", instance("removeIf")),
            Map.entry("java/lang/StringBuffer.append(Ljava/lang/String;)Ljava/lang/StringBuffer;", instance("append")),
            Map.entry("java/lang/StringBuffer.append(I)Ljava/lang/StringBuffer;", instance("append")),
            Map.entry("java/lang/StringBuffer.append(Ljava/lang/Object;)Ljava/lang/StringBuffer;", instance("append")),
            Map.entry("java/lang/StringBuffer.length()I", instance("length")),
            Map.entry("java/lang/StringBuffer.toString()Ljava/lang/String;", instance("toString")),
            Map.entry("java/util/Objects.requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;", statics("requireNonNull")),
            Map.entry("java/util/Iterable.iterator()Ljava/util/Iterator;", instance("iterator")),
            Map.entry("java/util/Iterator.hasNext()Z", instance("hasNext")),
            Map.entry("java/util/Iterator.next()Ljava/lang/Object;", instance("next")),
            Map.entry("java/util/Collection.add(Ljava/lang/Object;)Z", instance("add")),
            Map.entry("java/util/Collection.size()I", instance("size")),
            Map.entry("java/util/Collection.isEmpty()Z", instance("isEmpty")),
            Map.entry("java/util/Collection.contains(Ljava/lang/Object;)Z", instance("contains")),
            Map.entry("java/util/Collection.remove(Ljava/lang/Object;)Z", instance("remove")),
            Map.entry("java/util/List.get(I)Ljava/lang/Object;", instance("get")),
            Map.entry("java/util/List.set(ILjava/lang/Object;)Ljava/lang/Object;", instance("set")),
            Map.entry("java/util/List.add(ILjava/lang/Object;)V", instance("add")),
            Map.entry("java/util/List.remove(I)Ljava/lang/Object;", instance("remove")),
            Map.entry("java/util/List.indexOf(Ljava/lang/Object;)I", instance("indexOf")),
            Map.entry("java/util/Map.put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", instance("put")),
            Map.entry("java/util/Map.get(Ljava/lang/Object;)Ljava/lang/Object;", instance("get")),
            Map.entry("java/util/Map.containsKey(Ljava/lang/Object;)Z", instance("containsKey")),
            Map.entry("java/util/Map.remove(Ljava/lang/Object;)Ljava/lang/Object;", instance("remove")),
            Map.entry("java/util/Map.size()I", instance("size")),
            Map.entry("java/util/Map.isEmpty()Z", instance("isEmpty")),
            Map.entry("java/util/Map.keySet()Ljava/util/Set;", instance("keySet")),
            Map.entry("java/util/Map.values()Ljava/util/Collection;", instance("values")),
            Map.entry("java/util/Map.entrySet()Ljava/util/Set;", instance("entrySet")),
            Map.entry("java/util/Map$Entry.getKey()Ljava/lang/Object;", instance("getKey")),
            Map.entry("java/util/Map$Entry.getValue()Ljava/lang/Object;", instance("getValue")),
            Map.entry("java/awt/Window.setVisible(Z)V", instance("setVisible")),
            Map.entry("java/awt/Window.dispose()V", instance("dispose")),
            Map.entry("java/awt/Window.setSize(II)V", instance("setSize")),
            Map.entry("java/awt/Window.setResizable(Z)V", instance("setResizable")),
            Map.entry("java/awt/Window.setLocationRelativeTo(Ljava/awt/Component;)V", instance("setLocationRelativeTo")),
            Map.entry("java/awt/Window.setDefaultCloseOperation(I)V", instance("setDefaultCloseOperation")),
            Map.entry("java/awt/Window.getParent()Ljava/awt/Container;", instance("getParent")),
            Map.entry("javax/swing/SwingUtilities.invokeLater(Ljava/lang/Runnable;)V", statics("invokeLater")),
            Map.entry("java/awt/Window.getContentPane()Ljava/awt/Container;", instance("getContentPane")),
            Map.entry("java/awt/Window.setContentPane(Ljava/awt/Container;)V", instance("setContentPane")),
            Map.entry("java/awt/Container.setLayout(Ljava/awt/LayoutManager;)V", instance("setLayout")),
            Map.entry("java/awt/Container.add(Ljava/awt/Component;)Ljava/awt/Component;", instance("add")),
            Map.entry("java/awt/Container.add(Ljava/awt/Component;Ljava/lang/Object;)V", instance("add")),
            Map.entry("java/awt/Component.setEnabled(Z)V", instance("setEnabled")),
            Map.entry("java/awt/Component.setVisible(Z)V", instance("setVisible")),
            Map.entry("java/awt/Component.setPreferredSize(Ljava/awt/Dimension;)V", instance("setPreferredSize")),
            Map.entry("java/awt/Component.setBounds(IIII)V", instance("setBounds")),
            Map.entry("java/awt/Component.requestFocusInWindow()Z", instance("requestFocusInWindow")),
            Map.entry("java/lang/Thread.sleep(J)V", statics("sleep")),
            Map.entry("javax/swing/JButton.addActionListener(Ljava/awt/event/ActionListener;)V", instance("addActionListener")),
            Map.entry("javax/swing/JLabel.setText(Ljava/lang/String;)V", instance("setText")),
            Map.entry("javax/swing/JTextField.getText()Ljava/lang/String;", instance("getText")),
            Map.entry("javax/swing/JTextField.setText(Ljava/lang/String;)V", instance("setText")),
            Map.entry("javax/swing/JList.getSelectedIndex()I", instance("getSelectedIndex")),
            Map.entry("javax/swing/DefaultListModel.addElement(Ljava/lang/Object;)V", instance("addElement")),
            Map.entry("javax/swing/DefaultListModel.clear()V", instance("clear")),
            Map.entry("java/awt/event/ActionListener.actionPerformed(Ljava/awt/event/ActionEvent;)V", instance("actionPerformed")),
            Map.entry("javax/swing/JPasswordField.addKeyListener(Ljava/awt/event/KeyListener;)V", instance("addKeyListener")),
            Map.entry("javax/swing/JPasswordField.getPassword()[C", instance("getPassword")),
            Map.entry("javax/swing/JPasswordField.setEchoChar(C)V", instance("setEchoChar")),
            Map.entry("javax/swing/JProgressBar.setIndeterminate(Z)V", instance("setIndeterminate")),
            Map.entry("javax/swing/Box.createVerticalStrut(I)Ljava/awt/Component;", statics("createVerticalStrut")),
            Map.entry("javax/swing/BorderFactory.createEmptyBorder(IIII)Ljavax/swing/border/Border;", statics("createEmptyBorder")),
            Map.entry("java/awt/Container.setBorder(Ljavax/swing/border/Border;)V", instance("setBorder")),
            Map.entry("javax/swing/JLabel.setForeground(Ljava/awt/Color;)V", instance("setForeground")),
            Map.entry("javax/swing/JLabel.setFont(Ljava/awt/Font;)V", instance("setFont")),
            Map.entry("javax/swing/JOptionPane.showMessageDialog(Ljava/awt/Component;Ljava/lang/Object;Ljava/lang/String;I)V", statics("showMessageDialog")),
            Map.entry("java/util/Arrays.fill([CC)V", statics("fill")),
            Map.entry("java/awt/event/KeyEvent.getKeyCode()I", instance("getKeyCode")),
            Map.entry("java/awt/CardLayout.show(Ljava/awt/Container;Ljava/lang/String;)V", instance("show")),
            Map.entry("java/awt/CardLayout.first(Ljava/awt/Container;)V", instance("first")),
            Map.entry("java/awt/CardLayout.last(Ljava/awt/Container;)V", instance("last")),
            Map.entry("java/awt/CardLayout.next(Ljava/awt/Container;)V", instance("next")),
            Map.entry("java/awt/CardLayout.previous(Ljava/awt/Container;)V", instance("previous")));

    private static final Map<String, ShimTarget> FIELDS = Map.ofEntries(
            Map.entry("java/lang/System.out Ljava/io/PrintStream;", statics("@out")),
            Map.entry("java/lang/System.err Ljava/io/PrintStream;", statics("err")),
            Map.entry("java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;", statics("MILLISECONDS")),
            Map.entry("java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;", statics("NANOSECONDS")),
            Map.entry("java/time/temporal/ChronoUnit.MINUTES Ljava/time/temporal/ChronoUnit;", statics("MINUTES")),
            Map.entry("java/nio/charset/StandardCharsets.UTF_8 Ljava/nio/charset/Charset;", statics("UTF_8")),
            Map.entry("java/lang/Boolean.TRUE Ljava/lang/Boolean;", statics("TRUE")),
            Map.entry("java/lang/Boolean.FALSE Ljava/lang/Boolean;", statics("FALSE")),
            Map.entry("java/awt/Color.RED Ljava/awt/Color;", statics("RED")),
            Map.entry("java/awt/Color.ORANGE Ljava/awt/Color;", statics("ORANGE")),
            Map.entry("java/awt/Color.DARK_GRAY Ljava/awt/Color;", statics("DARK_GRAY")),
            Map.entry("java/awt/GridBagConstraints.gridx I", instance("gridx")),
            Map.entry("java/awt/GridBagConstraints.gridy I", instance("gridy")),
            Map.entry("java/awt/GridBagConstraints.gridwidth I", instance("gridwidth")),
            Map.entry("java/awt/GridBagConstraints.gridheight I", instance("gridheight")),
            Map.entry("java/awt/GridBagConstraints.weightx D", instance("weightx")),
            Map.entry("java/awt/GridBagConstraints.weighty D", instance("weighty")),
            Map.entry("java/awt/GridBagConstraints.anchor I", instance("anchor")),
            Map.entry("java/awt/GridBagConstraints.fill I", instance("fill")),
            Map.entry("java/awt/GridBagConstraints.ipadx I", instance("ipadx")),
            Map.entry("java/awt/GridBagConstraints.ipady I", instance("ipady")),
            Map.entry("java/awt/GridBagConstraints.insets Ljava/awt/Insets;", instance("insets")));

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

    public static String superOf(String internalName) {
        return SHIM_SUPERS.get(internalName);
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

    /**
     * The single shim supertype of the given internal name (from SHIM_SUPERS), or null when the
     * type is not a shim or has no recorded supertype. Used for shim-side assignability walks.
     */
    public static String shimSuperOf(String internalName) {
        return SHIM_SUPERS.get(internalName);
    }

    private static ShimTarget instance(String csName) {
        return new ShimTarget(csName, false);
    }

    private static ShimTarget statics(String csName) {
        return new ShimTarget(csName, true);
    }
}
