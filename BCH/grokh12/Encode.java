import java.util.ArrayList;
import java.util.List;

/**
 * ციკლური კოდით ინფორმაციის კოდირება
 * <p>
 * Created by Giorgi on 12/29/2015.
 */
public class Encode {

    private static Reader reader = new Reader();
    private static Writer writer = new Writer();

    public static int[] take(int[] from, int n) {
        int[] result = new int[n];
        System.arraycopy(from, 0, result, 0, result.length);
        return result;
    }

    public static int[] removeFront(int[] from, int n) {
        int[] result = new int[from.length - n];
        System.arraycopy(from, n, result, 0, result.length);
        return result;
    }

    public static void main(String[] args) {
        String polFile = args[0];
        String datFile = args[1];
        String ansFile = args[2];

        String pol = reader.getString(polFile);
        String[] lines = pol.split("\n");
        int p = Integer.valueOf(lines[0]);
        int n = Integer.valueOf(lines[1]);
        int[] polynomial = new int[n];
        String[] coefficients = lines[2].split(" ");
        for (int i = 0; i < coefficients.length; i++)
            polynomial[n - i - 1] = Integer.valueOf(coefficients[i]);

        String data = reader.getString(datFile);
        lines = data.split("\n");
        int nd = Integer.valueOf(lines[0]);
        int[] input = new int[nd];
        String[] inputCoefficients = lines[1].split(" ");
        for (int i = 0; i < inputCoefficients.length; i++)
            input[i] = Integer.valueOf(inputCoefficients[i]);

        polynomial = Polynomial.clear(polynomial);
        int dl = n - Polynomial.degree(polynomial);
        List<Integer> result = new ArrayList<>();
        while (input.length > 0) {
            int[] currentData = reverse(take(input, dl));
            input = removeFront(input, dl);
            int[] res = Polynomial.fill(Polynomial.field(Polynomial.multiply(polynomial, currentData), p), n);
            // res = reverse(res);
            for (int i = res.length - 1; i >= 0; i--)
                result.add(res[i]);
        }

        StringBuilder res = new StringBuilder();
        res.append(result.size());
        res.append("\n");
        for (Integer a : result) {
            res.append(a);
            res.append(" ");
        }
        writer.writeString(ansFile, res.toString());
    }

    public static int[] reverse(int[] field) {
        int[] result = new int[field.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = field[field.length - i - 1];
        }
        return result;
    }

}
