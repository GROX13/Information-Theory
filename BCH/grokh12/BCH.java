import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * მოცემული BCH კოდის მაგენერირებელი მრავალწევრის პოვნა
 * <p>
 * Created by Giorgi on 12/29/2015.
 */
public class BCH {

    private static Reader reader = new Reader();
    private static Writer writer = new Writer();

    public static void main(String[] args) {
        String datFile = args[0];
        String dstFile = args[1];
        String ansFile = args[2];

        String data = reader.getString(datFile);
        String[] lines = data.split("\n");
        int p = Integer.valueOf(lines[0]);
        int n = Integer.valueOf(lines[1]);
        int[] polynomial = new int[n + 1];
        String[] coefficients = lines[2].split(" ");
        for (int i = 0; i < coefficients.length; i++)
            polynomial[n - i] = Integer.valueOf(coefficients[i]);
        polynomial = Polynomial.clear(polynomial);

        String sigma = reader.getString(dstFile);
        int s = Integer.valueOf(sigma);

        int ml = 0;
        int[][] minimals = new int[2 * s][];

        for (int i = 1; i < s; i++) {
            int[] pol = Polynomial.field(MinimalPolynomial.findPolynomial(polynomial, i, p), p);
            int[] min = MinimalPolynomial.findMinimal(pol, polynomial, p);
            minimals[ml] = min;
            ml++;
        }

        Set<List<Integer>> clean = removeDuplicates(minimals, ml);
        int[] result = {1};
        for (List<Integer> a : clean) {
            int[] t = toArray(a);
            result = Polynomial.field(Polynomial.multiply(result, t), p);
        }

        StringBuilder res = new StringBuilder();
        res.append(p);
        res.append("\n");
        res.append(new Double(Math.pow(p, n)).intValue() - 1);
        res.append("\n");
        for (int i = result.length - 1; i >= -(Math.pow(p, n) - 1 - result.length); i--) {
            if (i < 0) res.append("0");
            else res.append(result[i]);
            res.append(" ");
        }

        res.append("\n");

        writer.writeString(ansFile, res.toString());
    }

    private static int[] toArray(List<Integer> a) {
        int[] result = new int[a.size()];
        for (int i = 0; i < a.size(); i++) {
            result[i] = a.get(i);
        }
        return result;
    }

    private static Set<List<Integer>> removeDuplicates(int[][] minimals, int ml) {
        Set<List<Integer>> result = new HashSet<>();
        for (int i = 0; i < ml; i++) {
            int[] temp = minimals[i];
            List<Integer> t = new ArrayList<>();
            for (int aTemp : temp) t.add(aTemp);
            result.add(t);
        }

        return result;
    }

}
