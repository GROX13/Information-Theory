/**
 * პირველი შემავალი ფაილიდან წაიკითხავს ორობითი კოდის
 * მაგენერირებელ მატრიცას და დააბრუნებს ამ კოდის ექვივალენტური
 * კოდის მაგენერირებელ მატრიცას სტანდარტულ ფორმაში.
 * <p>
 * Created by Giorgi on 12/26/2015.
 */
public class StandardForm {

    private static Reader reader = new Reader();
    private static Writer writer = new Writer();
    @SuppressWarnings("unused")
    private static Normalizer normalizer = new Normalizer();

    private int x, y;
    private int[][] matrix;
    private int[] layout;

    private void exchange(int i1, int i2) {
        int x = layout[i1], y = layout[i2];
        layout[i1] = y;
        layout[i2] = x;
        int[] i2arr = matrix[i2];
        matrix[i2] = matrix[i1];
        matrix[i1] = i2arr;
    }

    private void standardise() {
        for (int i = 0; i < y; i++)
            this.standardise(i);
    }

    private void standardise(int index) {
        if (matrix[index][index] != 1)
            for (int j = index; j < y; j++)
                if (matrix[index][j] == 1) {
                    addRows(index, j);
                    break;
                }
        if (matrix[index][index] != 1)
            for (int j = index + 1; j < x; j++)
                if (matrix[j][index] == 1) {
                    exchange(j, index);
                    break;
                }
        standardiseColumn(index);
    }

    private void standardiseColumn(int index) {
        for (int i = 0; i < y; i++) {
            if (i != index) {
                if (matrix[index][i] == 1) {
                    addRows(i, index);
                }
            }
        }
    }

    private void addRows(int rowOne, int rowTwo) {
        for (int i = 0; i < x; i++) {
            matrix[i][rowOne] = (matrix[i][rowOne] + matrix[i][rowTwo]) % 2;
        }
    }

    public StandardForm(int x, int y, int[][] matrix, int[] layout) {
        this.x = x;
        this.y = y;
        this.matrix = matrix;
        this.layout = layout;
        this.standardise();
    }

    public StandardForm(Content content) {
        this(content.getX(), content.getY(), content.getMatrix(), content.getLayout());
    }

    public Content getContent() {
        return new Content(x, y, this.getStandardMatrix(), this.getLayout());
    }

    public int[][] getStandardMatrix() {
        return matrix;
    }

    public int[] getLayout() {
        return layout;
    }

    public static void main(String[] args) {
        String fileToRead = args[0];
        String fileToWrite = args[1];
        StandardForm standardForm = new StandardForm(getContent(reader.getString(fileToRead)));
        writer.writeString(fileToWrite, getResult(standardForm.getContent()));
    }

    public static Content getContent(String text) {
        int x, y;
        int[][] matrix;
        int[] layout;
        text = text.replace("\r", "");
        String[] lines = text.split("\n");
        String[] xy = lines[0].split(" ");
        x = Integer.valueOf(xy[0]);
        y = Integer.valueOf(xy[1]);
        matrix = new int[x][y];
        for (int i = 0; i < y; i++) {
            char[] row = lines[1 + i].toCharArray();
            for (int j = 0; j < x; j++)
                matrix[j][i] = Integer.valueOf(String.valueOf(row[j]));
        }
        layout = new int[x];
        for (int i = 0; i < x; i++) {
            layout[i] = i + 1;
        }
        return new Content(x, y, matrix, layout);
    }

    public static String getResult(Content content) {
        StringBuilder result = new StringBuilder();
        result.append(String.valueOf(content.getX()));
        result.append(" ");
        result.append(String.valueOf(content.getY()));
        result.append("\n");
        int[][] matrix = content.getMatrix();
        for (int i = 0; i < content.getY(); i++) {
            for (int j = 0; j < content.getX(); j++)
                result.append(matrix[j][i]);
            result.append("\n");
        }
        for (int i = 0; i < content.getLayout().length; i++) {
            result.append(content.getLayout()[i]);
            result.append(" ");
        }
        return result.toString();
    }

    public static class Content {
        private int x, y;
        private int[][] matrix;
        private int[] layout;

        public Content(int x, int y, int[][] matrix, int[] layout) {
            this.x = x;
            this.y = y;
            this.matrix = matrix;
            this.layout = layout;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int[][] getMatrix() {
            return matrix;
        }

        public int[] getLayout() {
            return layout;
        }

    }

}
