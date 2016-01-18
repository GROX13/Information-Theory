import java.util.ArrayList;
import java.util.List;

/**
 * ციკლური კოდით შენახული ინფორმაციის ამოღება
 * <p>
 * Created by Giorgi on 12/29/2015.
 */
public class Decode {

    private static Reader reader = new Reader();
    private static Writer writer = new Writer();

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
            int[] currentData = Polynomial.clear(Encode.reverse(Encode.take(input, n)));
            input = Encode.removeFront(input, n);
            int[] res = Polynomial.divide(currentData, polynomial, p);
            res = Encode.reverse(Polynomial.fill(res, dl));
            for (int re : res) result.add(re);
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

}
