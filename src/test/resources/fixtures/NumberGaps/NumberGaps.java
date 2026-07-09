import java.util.Objects;
import java.util.Comparator;

public class NumberGaps {
    static void p(double d) {
        System.out.println(Double.doubleToLongBits(d));
    }

    static void r(double d) {
        System.out.println(Math.round(d * 1000000.0));
    }

    public static void main(String[] args) {
        objects();
        integers();
        longs();
        smallNums();
        floating();
        mathExact();
        mathTranscendental();
        mathBits();
        System.out.println("done");
    }

    static void objects() {
        System.out.println(Objects.equals("a", "a"));
        System.out.println(Objects.equals("a", "b"));
        System.out.println(Objects.equals(null, null));
        System.out.println(Objects.equals(null, "a"));
        System.out.println(Objects.isNull(null));
        System.out.println(Objects.nonNull("x"));
        System.out.println(Objects.hashCode(null));
        System.out.println(Objects.hashCode("abc"));
        System.out.println(Objects.hash("a", "b", "c"));
        System.out.println(Objects.hash());
        System.out.println(Objects.toString(null));
        System.out.println(Objects.toString("v"));
        System.out.println(Objects.toString(null, "def"));
        System.out.println(Objects.deepEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3}));
        System.out.println(Objects.deepEquals(new int[]{1, 2}, new int[]{1, 3}));
        System.out.println(Objects.requireNonNullElse(null, "fallback"));
        System.out.println(Objects.requireNonNullElseGet(null, () -> "supplied"));
        System.out.println(Objects.requireNonNullElse("present", "fallback"));
        Comparator<String> c = Comparator.naturalOrder();
        System.out.println(Objects.compare("a", "b", c));
        System.out.println(Objects.compare("a", "a", c));
        try {
            Objects.requireNonNull(null, "boom");
        } catch (NullPointerException e) {
            System.out.println("npe:" + e.getMessage());
        }
    }

    static void integers() {
        System.out.println(Integer.parseInt("ff", 16));
        System.out.println(Integer.parseInt("-101", 2));
        System.out.println(Integer.parseInt("+42"));
        System.out.println(Integer.parseUnsignedInt("4294967295"));
        System.out.println(Integer.valueOf("123"));
        System.out.println(Integer.valueOf("7f", 16));
        System.out.println(Integer.toString(255, 16));
        System.out.println(Integer.toString(-255, 16));
        System.out.println(Integer.toString(10, 2));
        System.out.println(Integer.toBinaryString(10));
        System.out.println(Integer.toHexString(-1));
        System.out.println(Integer.toOctalString(64));
        System.out.println(Integer.toUnsignedString(-1));
        System.out.println(Integer.toUnsignedString(-1, 16));
        System.out.println(Integer.bitCount(255));
        System.out.println(Integer.bitCount(-1));
        System.out.println(Integer.numberOfLeadingZeros(1));
        System.out.println(Integer.numberOfLeadingZeros(0));
        System.out.println(Integer.numberOfTrailingZeros(8));
        System.out.println(Integer.numberOfTrailingZeros(0));
        System.out.println(Integer.highestOneBit(100));
        System.out.println(Integer.highestOneBit(0));
        System.out.println(Integer.lowestOneBit(12));
        System.out.println(Integer.reverse(1));
        System.out.println(Integer.reverseBytes(0x01020304));
        System.out.println(Integer.rotateLeft(1, 4));
        System.out.println(Integer.rotateRight(16, 4));
        System.out.println(Integer.signum(-42));
        System.out.println(Integer.signum(0));
        System.out.println(Integer.signum(42));
        System.out.println(Integer.compareUnsigned(-1, 1));
        System.out.println(Integer.divideUnsigned(-2, 2));
        System.out.println(Integer.remainderUnsigned(-1, 3));
        System.out.println(Integer.hashCode(99));
        System.out.println(Integer.toUnsignedLong(-1));
    }

    static void longs() {
        System.out.println(Long.parseLong("ff", 16));
        System.out.println(Long.parseUnsignedLong("18446744073709551615"));
        System.out.println(Long.valueOf("123"));
        System.out.println(Long.valueOf("7f", 16));
        System.out.println(Long.toString(255L, 16));
        System.out.println(Long.toBinaryString(10L));
        System.out.println(Long.toHexString(-1L));
        System.out.println(Long.toOctalString(64L));
        System.out.println(Long.toUnsignedString(-1L));
        System.out.println(Long.bitCount(-1L));
        System.out.println(Long.numberOfLeadingZeros(1L));
        System.out.println(Long.numberOfTrailingZeros(8L));
        System.out.println(Long.highestOneBit(100L));
        System.out.println(Long.lowestOneBit(12L));
        System.out.println(Long.reverse(1L));
        System.out.println(Long.reverseBytes(0x0102030405060708L));
        System.out.println(Long.rotateLeft(1L, 4));
        System.out.println(Long.rotateRight(16L, 4));
        System.out.println(Long.signum(-42L));
        System.out.println(Long.compare(3L, 5L));
        System.out.println(Long.compareUnsigned(-1L, 1L));
        System.out.println(Long.divideUnsigned(-2L, 2L));
        System.out.println(Long.remainderUnsigned(-1L, 3L));
        System.out.println(Long.hashCode(0x1_0000_0001L));
    }

    static void smallNums() {
        System.out.println(Short.parseShort("123"));
        System.out.println(Short.parseShort("ff", 16));
        System.out.println(Short.valueOf("42"));
        System.out.println(Short.compare((short) 1, (short) 5));
        System.out.println(Short.hashCode((short) 7));
        System.out.println(Short.toUnsignedInt((short) -1));
        System.out.println(Short.toUnsignedLong((short) -1));
        System.out.println(Short.reverseBytes((short) 0x0102));
        System.out.println(Byte.parseByte("-12"));
        System.out.println(Byte.parseByte("7f", 16));
        System.out.println(Byte.valueOf("42"));
        System.out.println(Byte.compare((byte) 1, (byte) 5));
        System.out.println(Byte.hashCode((byte) 7));
        System.out.println(Byte.toUnsignedInt((byte) -1));
        System.out.println(Byte.toUnsignedLong((byte) -1));
    }

    static void floating() {
        System.out.println(Double.isNaN(Double.NaN));
        System.out.println(Double.isNaN(1.0));
        System.out.println(Double.isInfinite(Double.POSITIVE_INFINITY));
        System.out.println(Double.isFinite(1.0));
        System.out.println(Double.compare(1.0, 2.0));
        System.out.println(Double.compare(0.0, -0.0));
        System.out.println(Double.doubleToLongBits(1.0));
        System.out.println(Double.doubleToRawLongBits(-0.0));
        System.out.println(Double.longBitsToDouble(4607182418800017408L));
        System.out.println(Double.hashCode(1.5));
        System.out.println(Double.toHexString(1.0));
        System.out.println(Double.toHexString(0.5));
        System.out.println(Double.toHexString(3.0));
        System.out.println(Double.toHexString(0.0));
        System.out.println(Double.toHexString(-2.0));
        System.out.println(Double.valueOf("3.5"));
        System.out.println(Double.valueOf(2.0).isNaN());
        System.out.println(Double.MIN_NORMAL == 0x1.0p-1022);
        System.out.println(Double.MAX_EXPONENT);
        System.out.println(Double.MIN_EXPONENT);
        System.out.println(Double.SIZE);

        System.out.println(Float.isNaN(Float.NaN));
        System.out.println(Float.isInfinite(Float.POSITIVE_INFINITY));
        System.out.println(Float.isFinite(1.0f));
        System.out.println(Float.compare(1.0f, 2.0f));
        System.out.println(Float.floatToIntBits(1.0f));
        System.out.println(Float.floatToRawIntBits(-0.0f));
        System.out.println(Float.intBitsToFloat(1065353216));
        System.out.println(Float.hashCode(1.5f));
        System.out.println(Float.toHexString(1.0f));
        System.out.println(Float.toHexString(3.0f));
        System.out.println(Float.sum(1.5f, 2.5f));
        System.out.println(Float.max(1.5f, 2.5f));
        System.out.println(Float.SIZE);
        System.out.println(Float.MAX_EXPONENT);
    }

    static void mathExact() {
        System.out.println(Math.floorDiv(7, 2));
        System.out.println(Math.floorDiv(-7, 2));
        System.out.println(Math.floorDiv(7L, 2));
        System.out.println(Math.floorDiv(-7L, 2L));
        System.out.println(Math.floorMod(-7, 3));
        System.out.println(Math.floorMod(7, -3));
        System.out.println(Math.floorMod(-7L, 3));
        System.out.println(Math.floorMod(-7L, 3L));
        System.out.println(Math.addExact(100, 200));
        System.out.println(Math.addExact(100L, 200L));
        System.out.println(Math.subtractExact(5, 9));
        System.out.println(Math.subtractExact(5L, 9L));
        System.out.println(Math.multiplyExact(6, 7));
        System.out.println(Math.multiplyExact(6L, 7));
        System.out.println(Math.multiplyExact(6L, 7L));
        System.out.println(Math.incrementExact(41));
        System.out.println(Math.incrementExact(41L));
        System.out.println(Math.decrementExact(43));
        System.out.println(Math.decrementExact(43L));
        System.out.println(Math.negateExact(42));
        System.out.println(Math.negateExact(42L));
        System.out.println(Math.toIntExact(42L));
        checkOverflow(() -> Math.addExact(Integer.MAX_VALUE, 1));
        checkOverflow(() -> Math.multiplyExact(Long.MAX_VALUE, 2L));
        checkOverflow(() -> Math.toIntExact(Long.MAX_VALUE));
        checkOverflow(() -> Math.negateExact(Integer.MIN_VALUE));
    }

    interface Op {
        void run();
    }

    static void checkOverflow(Op op) {
        try {
            op.run();
            System.out.println("no-overflow");
        } catch (ArithmeticException e) {
            System.out.println("overflow:" + e.getMessage());
        }
    }

    static void mathTranscendental() {
        r(Math.asin(0.5));
        r(Math.acos(0.5));
        r(Math.atan(1.0));
        r(Math.sinh(1.0));
        r(Math.cosh(1.0));
        r(Math.tanh(1.0));
        r(Math.expm1(1.0));
        r(Math.log1p(1.0));
    }

    static void mathBits() {
        p(Math.copySign(3.0, -1.0));
        System.out.println(Math.copySign(3.0f, -1.0f));
        p(Math.signum(-4.0));
        System.out.println(Math.signum(-4.0f));
        p(Math.rint(2.5));
        p(Math.rint(3.5));
        p(Math.nextUp(1.0));
        p(Math.nextDown(1.0));
        p(Math.nextAfter(1.0, 2.0));
        p(Math.ulp(1.0));
        p(Math.scalb(1.0, 3));
        System.out.println(Math.getExponent(8.0));
        System.out.println(Math.getExponent(1.0f));
        p(Math.IEEEremainder(5.0, 3.0));
        System.out.println(Math.floorMod(10, 3));
    }
}
