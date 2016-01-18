import java.util.HashMap;
import java.util.Map;

/**
 * ამ ნაწილში სამი შემომავალი ფაილი გექნებათ.
 * პირველი მათგანი იქნება მეორე ნაწილში მიღებული
 * შემმოწმებელი მატრიცა. მეორე იქნება მესამე
 * ნაწილში მიღებული გაშიფრვისცხრილი. მესამე
 * მათგანი კი იქნება გადმოცემული მონაცემები.
 * <p>
 * Created by Giorgi on 12/27/2015.
 */
public class Decode {
    private static Reader reader = new Reader();
    private static Writer writer = new Writer();
    private static Normalizer normalizer = new Normalizer();
    private final int[][] parityMatrix;
    private StringBuilder result;
    private Map<String, String> decodingTable;

    public Decode(String data, Map<String, String> decodingTable, int[][] parityMatrix) {
        int bytesToRead = parityMatrix.length;
        this.result = new StringBuilder();
        this.parityMatrix = parityMatrix;
        this.decodingTable = decodingTable;
        String victor = "";
        for (int i = 0; i < data.length(); i++) {
            victor += data.charAt(i);
            if (victor.length() == bytesToRead) {
                correct(victor);
                victor = "";
            }
        }
    }

    private void correct(String victor) {
        StringBuilder correct = new StringBuilder();
        String key = vectorToString(multiply(parityMatrix, toVector(victor)));
        String res = decodingTable.get(key);
        for (int i = 0; i < res.length(); i++) {
            if (res.charAt(i) == '1') {
                if (victor.charAt(i) == '1')
                    correct.append('0');
                else
                    correct.append('1');
            } else {
                correct.append(victor.charAt(i));
            }
        }
        result.append(correct);
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

    @SuppressWarnings("Duplicates")
    private int[] multiply(int[][] matrix, int[] vector) {
        int x = matrix.length;
        int y = matrix[0].length;
        if (vector.length != x) throw new RuntimeException("Illegal matrix dimensions.");
        int[] result = new int[y];
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                result[i] += matrix[j][i] * vector[j];

        for (int i = 0; i < result.length; i++)
            result[i] = result[i] % 2;

        return result;
    }

    public String getResult() {
        return result.toString();
    }

    public static void main(String[] args) {
        String parityFile = args[0];
        String decodingFile = args[1];
        String dataFile = args[2];
        String resultFile = args[3];
        Decode decode = new Decode(readData(dataFile), readDecodingTable(decodingFile), readParity(parityFile));
        writer.writeStringAsBits(resultFile, normalizer.normalize(decode.getResult()));
    }

    public static int[][] readParity(String file) {
        String matrix = reader.getString(file);
        return StandardForm.getContent(matrix).getMatrix();
    }

    public static Map<String, String> readDecodingTable(String file) {
        Map<String, String> table = new HashMap<>();
        String[] result = reader.getString(file).split("\n");
        for (String line : result) {
            String[] lineArray = line.split(" ");
            table.put(lineArray[0], lineArray[1]);
        }
        return table;
    }

    public static String readData(String file) {
        String bits = reader.getBitsAsString(file);
        bits = normalizer.denormalize(bits);
        return bits;
    }

}
