/**
 * მინიმალური მრავალწევრის პოვნა
 * <p>
 * Created by Giorgi on 12/29/2015.
 */
public class MinimalPolynomial {

    private static Reader reader = new Reader();
    private static Writer writer = new Writer();

    public static void main(String[] args) {
        String datFile = args[0];
        String pwrFile = args[1];
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

        String power = reader.getString(pwrFile);
        int i = Integer.valueOf(power);

        int[] pol = findPolynomial(polynomial, i, p);
        int[] minimal = findMinimal(pol, Polynomial.field(polynomial, p), p);

        StringBuilder result = new StringBuilder();
        result.append(p);
        result.append("\n");
        result.append(minimal.length - 1);
        result.append("\n");
        for (int j = minimal.length - 1; j >= 0; j--) {
            result.append(minimal[j]);
            result.append(" ");
        }
        result.append("\n");
        writer.writeString(ansFile, result.toString());
    }

    public static int[] multiply(int[] p1, int x) {
        int[] res = new int[p1.length];
        for (int i = 0; i < p1.length; i++)
            res[i] = p1[i] * x;
        return res;
    }

    public static int[] power(int[] p1, int x) {
        int[] res = new int[p1.length];
        System.arraycopy(p1, 0, res, 0, p1.length);
        for (int i = 1; i < x; i++)
            res = Polynomial.multiply(res, p1);
        return res;
    }

    public static int[] evaluate(int[] p1, int[] p2) {
        int[] result = {};
        int[] dest = new int[p1.length];
        // System.arraycopy(p1, 0, dest, 0, p1.length);
        for (int i = 0; i < p1.length - 1; i++)
            result = Polynomial.plus(
                    result, multiply(power(p2, p1.length - 1 - i), p1[i]));
        if (result.length == 0 && p1.length > 0)
            result = new int[1];
        if (p1.length > 0)
            result[result.length - 1] += p1[p1.length - 1];
        return result;
    }

    public static int[] findMinimal(int[] pol, int[] field, int p) {
        for (int i = 2; true; i++) {
            String temp = Integer.toString(i, p);
            int[] current = new int[temp.length()];
            for (int j = 0; j < temp.length(); j++)
                current[j] = Integer.valueOf(String.valueOf(temp.charAt(j)));

            int[] test = Polynomial.divide(evaluate(current, pol), field, p);
            if (Polynomial.clear(Polynomial.module()).length == 0)
                return current;
        }
    }

    public static int[] findPolynomial(int[] polynomial, int i, int p) {
        int[] temp = new int[i + 1];
        temp[0] = 1;
        Polynomial.divide(temp, Polynomial.field(polynomial, p), p);
        return Polynomial.module();
    }

}
