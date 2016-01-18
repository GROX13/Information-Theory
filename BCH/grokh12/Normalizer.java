public class Normalizer {
    private static final char ONE = '1';
    private static final char ZERO = '0';

    private String getEnd(int length) {
        String result = String.valueOf(ONE);
        for (int i = 0; i < length - 1; i++)
            result += ZERO;
        return result;
    }

    public String normalize(String bits) {
        int length = bits.length() % Byte.SIZE;
        return bits + getEnd((length == 0) ? Byte.SIZE : Byte.SIZE - length);
    }

    public String denormalize(String bits) {
        return bits.substring(0, bits.lastIndexOf(ONE));
    }

}
