/**
 * ამ ნაწილში თქვენი შემავალი მონაცემები ორი
 * ფაილისგან შედგება. ამ ფაილების სახელები
 * პირველ და მეორე არგუმენტებად გადმოგეცემათ,
 * ხოლო პასუხი სადაც უნდა ჩაწეროთ იმფაილის
 * სახელი მესამე არგუმენტად.
 * <p>
 * Created by Giorgi on 12/27/2015.
 */
public class Encode {

    private static Reader reader = new Reader();
    private static Writer writer = new Writer();
    private static Normalizer normalizer = new Normalizer();
    private StringBuilder encodedBits;
    private int numberToRead;

    public Encode(String bitsToEncode, int[][] standardMatrix) {
        numberToRead = standardMatrix[0].length;
        encodedBits = new StringBuilder();
        String vec = "";
        for (int i = 0; i < bitsToEncode.length(); i++) {
            vec += bitsToEncode.charAt(i);
            if (vec.length() == numberToRead) {
                encodedBits.append(vectorToString(multiply(toVector(vec), standardMatrix)));
                vec = "";
            }
        }
    }

    private int[] toVector(String vectorString) {
        int[] vector = new int[vectorString.length()];
        for (int i = 0; i < vectorString.length(); i++)
            vector[i] = Integer.valueOf(String.valueOf(vectorString.charAt(i)));
        return vector;
    }

    private String vectorToString(int[] vector) {
        StringBuilder result = new StringBuilder();
        for (int aVector : vector) result.append(String.valueOf(aVector));
        return result.toString();
    }

    private int[] multiply(int[] vector, int[][] matrix) {
        int x = matrix.length;
        int y = matrix[0].length;
        if (vector.length != y) throw new RuntimeException("Illegal matrix dimensions.");
        int[] result = new int[x];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                result[i] += vector[j] * matrix[i][j];
            }
        }
        for (int i = 0; i < result.length; i++)
            result[i] = result[i] % 2;

        return result;
    }

    public String getEncodedBits() {
        return encodedBits.toString();
    }

    public static void main(String[] args) {
        String datFile = args[1];
        String ansFile = args[2];
        String codeFile = args[0];
        String bitsToEncode = reader.getBitsAsString(datFile);
        // StandardForm standardForm = new StandardForm(StandardForm.getContent(reader.getString(codeFile)));
        Encode encode = new Encode(bitsToEncode, StandardForm.getContent(reader.getString(codeFile)).getMatrix());
        // standardForm.getStandardMatrix());
        writer.writeStringAsBits(ansFile, normalizer.normalize(encode.getEncodedBits()));
    }

}
