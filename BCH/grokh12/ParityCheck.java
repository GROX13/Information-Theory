/**
 * მრავალწევრის შემოწმება და შესაბამისი
 * შემმოწმებელი მრავალწევრის პოვნა
 * <p>
 * Created by Giorgi on 12/28/2015.
 */
public class ParityCheck {

    private static Reader reader = new Reader();
    private static Writer writer = new Writer();

    public static void main(String[] args) {
        String fileToRead = args[0];
        String fileToWrite = args[1];
        String data = reader.getString(fileToRead);
        String[] lines = data.split("\n");
        int p = Integer.valueOf(lines[0]);
        int n = Integer.valueOf(lines[1]);
        int[] checker = new int[n + 1];
        checker[0] = 1;
        checker[n] = -1;
        int[] polynomial = new int[n];
        String[] coefficients = lines[2].split(" ");
        for (int i = 0; i < coefficients.length; i++)
            polynomial[n - i - 1] = Integer.valueOf(coefficients[i]);
        int[] result = Polynomial.divide(checker, Polynomial.clear(polynomial), p);
        result = Polynomial.field(result, p);
        int[] remainder = Polynomial.module();
        if (remainder.length == 0) {
            String text = "YES\n";
            for (int i = result.length - 1; i >= 0; i--)
                text += (String.valueOf(result[i]) + " ");
            for (int i = 0; i < (n - result.length); i++)
                text += "0 ";
            writer.writeString(fileToWrite, text);
        } else {
            writer.writeString(fileToWrite, "NO");
        }
    }

}
