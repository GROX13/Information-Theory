/**
 * Polynomials with integer coefficients.
 * <p>
 * Created by გიორგი on 12/28/2015.
 */
public class Polynomial {

    private static int[] module = new int[]{};

    public static int[] clear(int[] p) {
        int[] result;
        if (p.length == 0)
            return new int[]{};
        if (p[0] == 0) {
            int offset = 0;
            for (int aP : p)
                if (aP == 0) offset++;
                else break;
            result = new int[p.length - offset];
            System.arraycopy(p, offset, result, 0, result.length);
        } else result = p;

        return result;
    }

    private static int[] add(int[] p1, int[] p2) {
        int[] result;
        result = new int[p1.length];
        System.arraycopy(p1, 0, result, 0, p1.length);
        int difference = p1.length - p2.length;
        for (int i = 0; i < p2.length; i++)
            result[difference + i] += p2[i];
        return result;
    }

    /**
     * @param p polynomial
     * @return degree of given polynomial
     */
    public static int degree(int[] p) {
        for (int i = 0; i < p.length; i++)
            if (p[i] != 0) return p.length - (i + 1);
        return 0;
    }

    /**
     * @param p1 first polynomial
     * @param p2 second polynomial
     * @return result
     */
    public static int[] plus(int[] p1, int[] p2) {
        int[] result;
        if (p1.length > p2.length) result = add(p1, p2);
        else result = add(p2, p1);
        return clear(result);
    }

    /**
     * @param p1 first polynomial
     * @param p2 second polynomial
     * @return result
     */
    public static int[] minus(int[] p1, int[] p2) {
        int[] result;
        if (p1.length > p2.length) result = new int[p1.length];
        else result = new int[p2.length];
        System.arraycopy(p1, 0, result, result.length - p1.length, p1.length);
        int difference = p1.length - p2.length;
        if (difference > 0) for (int i = 0; i < p2.length; i++) result[difference + i] -= p2[i];
        else for (int i = 0; i < p2.length; i++) result[i] -= p2[i];
        return clear(result);
    }

    /**
     * @param p1 first polynomial
     * @param p2 second polynomial
     * @return result
     */
    public static int[] multiply(int[] p1, int[] p2) {
        int[] result = new int[p1.length + p2.length - 1];
        for (int i = 0; i < p1.length; i++)
            for (int j = 0; j < p2.length; j++)
                result[i + j] += p1[i] * p2[j];
        return clear(result);
    }

    /**
     * @param p1 first polynomial
     * @param p2 second polynomial
     * @return result
     */
    public static int[] divide(int[] p1, int[] p2, int p) {
        int[] result = new int[p1.length];
        while (degree(p1) >= degree(p2)) {
            int degree = degree(p1) - degree(p2);
            int res = p1[0] / p2[0];
            // if (res == 0) break;
            int[] temp = new int[degree + 1];
            temp[0] = res;
            p1 = field(minus(p1, multiply(p2, temp)), p);
            result[result.length - (degree + 1)] = res;
        }
        module = p1;
        return clear(result);
    }

    /**
     * @param p1 polynomial
     * @param p  field
     * @return result polynomial
     */
    public static int[] field(int[] p1, int p) {
        for (int i = 0; i < p1.length; i++) {
            p1[i] = p1[i] % p;
            if (p1[i] < 0) p1[i] += p;
        }
        return p1;
    }

    /**
     * @return polynomial left after division
     */
    public static int[] module() {
        return module;
    }

    /**
     * @param p array
     * @param n length of array
     * @return result
     */
    public static int[] fill(int[] p, int n) {
        int[] result = new int[n];
        int offset = n - p.length;
        System.arraycopy(p, 0, result, offset, p.length);
        return result;
    }

    public static void main(String[] args) {
        int[] a = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1};
        int[] b = {1, 0, 0, 0, 0, 1};
        int[] r = divide(a, b, 2);
        for (int v : r) {
            System.out.print(v);
            System.out.print(" ");
        }
        System.out.println();
        r = module();
        for (int v : r) {
            System.out.print(v);
            System.out.print(" ");
        }
    }

}
