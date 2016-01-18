import java.util.Arrays;
import java.util.List;

/**
 * ამ ნაწილში გადმოგეცემათ კოდის მაგენერირებელი
 * მატრიცა (არა აუცილებლად სტანდარტულ ფორმაში).
 * თქვენი მიზანია იპოვოთ ამ კოდის შემმოწმებელი მატრიცა.
 * <p>
 * Created by Giorgi on 12/26/2015.
 */
public class ParityCheck {

    private static Reader reader = new Reader();
    private static Writer writer = new Writer();
    @SuppressWarnings("unused")
    private static Normalizer normalizer = new Normalizer();

    private int x, y;
    private int[][] result;
    private int[] layout;

    public ParityCheck(StandardForm.Content content) {
        this(content.getX(), content.getY(), content.getMatrix(), content.getLayout());
    }

    public ParityCheck(int x, int y, int[][] matrix, int[] layout) {
        this.y = x - y;
        this.x = y + this.y;
        this.result = new int[this.x][this.y];
        this.layout = layout;
        int[][] parity = new int[this.y][this.x];

        for (int i = 0; i < this.x - this.y; i++) {
            for (int j = 0; j < this.y; j++) {
                parity[j][i] = matrix[y + j][i];
            }
        }
        parity = transpose(parity);
        for (int i = 0; i < this.y; i++)
            parity[(this.x - this.y) + i][i] = 1;

        result = this.sort(parity);
    }

    private int[][] sort(int[][] transposed) {
        for (int i = 0; i < layout.length; i++) {
            if (layout[i] != i + 1) {
                exchange(i, indexOf(i + 1, layout), transposed);
            }
        }
        return transposed;
    }

    private int indexOf(int index, int[] layout) {
        for (int i = 0; i < layout.length; i++) {
            if (index == layout[i])
                return i;
        }
        return -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] getMatrix() {
        return result;
    }

    private void exchange(int i1, int i2, int[][] matrix) {
        int x = layout[i1], y = layout[i2];
        layout[i1] = y;
        layout[i2] = x;
        int[] i2arr = matrix[i2];
        matrix[i2] = matrix[i1];
        matrix[i1] = i2arr;
    }

    public static void main(String[] args) {
        String fileToRead = args[0];
        String fileToWrite = args[1];
        StandardForm standardForm = new StandardForm(StandardForm.getContent(reader.getString(fileToRead)));
        StandardForm.Content content = standardForm.getContent();
        ParityCheck parityCheck = new ParityCheck(content);
        writer.writeString(fileToWrite, getResult(parityCheck.getX(), parityCheck.getY(), parityCheck.getMatrix()));
    }

    public static int[][] transpose(int[][] matrix) {
        int x = matrix.length;
        int y = matrix[0].length;
        int[][] result = new int[y][x];
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                result[j][i] = matrix[i][j];
        return result;
    }

    private static String getResult(int x, int y, int[][] matrix) {
        StringBuilder result = new StringBuilder();
        result.append(String.valueOf(x));
        result.append(" ");
        result.append(String.valueOf(y));
        result.append("\n");
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++)
                result.append(matrix[j][i]);
            if (i != y - 1)
                result.append("\n");
        }
        return result.toString();
    }

}
