import java.util.List;

public class Distrib {

	private static String generateOutput() {
		String result = "";
		List<String> l1 = d.getLetterDistributionAsString();
		List<String> l2 = d.getLetterPairDistributionAsString();
		for (int i = 0; i < l1.size(); i++) {
			if (i == (l1.size() - 1)) {
				result += l1.get(i);
			} else {
				result += l1.get(i) + " ";
			}
		}
		result += System.lineSeparator();
		for (int i = 0; i < l2.size(); i++) {
			if (i == (l2.size() - 1)) {
				result += l2.get(i);
			} else {
				result += l2.get(i) + (" ");
			}
		}
		return result;
	}

	private static Reader r = new Reader();
	private static Writer w = new Writer();
	private static Distribution d;

	public static void main(String[] args) {
		d = new Distribution(r.getString(args[0]));
		String output = generateOutput();
		w.writeString(args[1], output);
	}

}
