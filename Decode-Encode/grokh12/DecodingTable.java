import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * შემომავალ მონაცემებად პირველ ფაილში ისევ გადმოგეცემათ
 * კოდის მაგენერირებელი მატრიცა (ისევ არა აუცილებლად
 * სტანდარტულ ფორმაში). წინა ნაწილის გამოყენებით შეგიძლიათ
 * ამ მატრიცის შესაბამისი შემმოწმებელი მატრიცაც იპოვოთ.
 * პროგრამას მეორეარგუმენტად გადმოეცემა ფაილი, რომელშიც
 * მხოლოდ ერთი მთელი რიცხვი e ეწერება.
 * <p>
 * Created by Giorgi on 12/27/2015.
 */
public class DecodingTable {

    private static Reader reader = new Reader();
    private static Writer writer = new Writer();

    private Set<String> permutations;
    private Map<String, String> result;

    public DecodingTable(int e, StandardForm.Content content) {
        this.result = new HashMap<>();
        this.permutations = new HashSet<>();
        StandardForm standardForm = new StandardForm(content);
        ParityCheck parityCheck = new ParityCheck(standardForm.getContent());
        StringBuilder keyString = new StringBuilder(), permutationsString = new StringBuilder();
        for (int i = 0; i < parityCheck.getY(); i++)
            keyString.append("0");
        for (int i = 0; i < parityCheck.getX(); i++)
            permutationsString.append("0");
        result.put(keyString.toString(), permutationsString.toString());
        permutation(e, permutationsString.toString());
        System.out.println("Permutations: " + permutations);
        for (String element : permutations)
            result.put(vectorToString(multiply(parityCheck.getMatrix(), toVector(element))), element);
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

    private void permutation(int e, String string) {
        if (e == 0) {
            permutations.add(string);
            return;
        }
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '0') {
                String perm = string.substring(0, i) + "1" + string.substring(i + 1);
                permutations.add(perm);
                permutation(e - 1, perm);
            }
        }
    }

    @SuppressWarnings("unused")
    private void permutation(String prefix, String string) {
        int n = string.length();
        if (n == 0) {
            permutations.add(prefix);
        } else {
            for (int i = 0; i < n; i++)
                permutation(prefix + string.charAt(i),
                        string.substring(0, i) + string.substring(i + 1));
        }
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        Set<String> keys = result.keySet();
        for (String key : keys) {
            value.append(key);
            value.append(" ");
            value.append(result.get(key));
            value.append("\n");
        }
        return value.toString();
    }

    public static void main(String[] args) {
        String fileToRead = args[0];
        String numberFileToRead = args[1];
        String fileToWrite = args[2];
        int e = Integer.valueOf(reader.getString(numberFileToRead));
        StandardForm.Content content = StandardForm.getContent(reader.getString(fileToRead));
        DecodingTable decodingTable = new DecodingTable(e, content);
        System.out.println(decodingTable);
        writer.writeString(fileToWrite, decodingTable.toString());
    }

}
