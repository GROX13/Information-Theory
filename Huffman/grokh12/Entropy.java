import java.text.DecimalFormat;
import java.util.List;

public class Entropy {

	private static String fileToRead;
	private static String fileToWrite;

	private static Reader r = new Reader();
	private static Writer w = new Writer();
	private List<Float> letters;
	private List<Float> letterPairs;

	public Entropy(String text) {
		Distribution d = new Distribution(text);
		letters = d.getLetterDistribution();
		letterPairs = d.getLetterPairDistribution();
	}

	public float countLetterEntropy() {
		float result = 0f;
		for (Float probability : letters) {
			if (probability != 0)
				result += -(probability) * (Math.log(probability) / Math.log(2));
		}
		return result;
	}

	public float countLetterPairsEntropy() {
		float result = 0f;
		for (Float probability : letterPairs) {
			if (probability != 0)
				result += -(probability) * (Math.log(probability) / Math.log(2));
		}
		return result;
	}

	public static void main(String[] args) {
		fileToRead = args[0];
		fileToWrite = args[1];
		Entropy e = new Entropy(r.getString(fileToRead));
		DecimalFormat decimalFormat = new DecimalFormat("0.0000000");
		float le = e.countLetterEntropy();
		float lpe = e.countLetterPairsEntropy();
		String result = decimalFormat.format(le) + System.lineSeparator() + decimalFormat.format(lpe)
				+ System.lineSeparator() + decimalFormat.format(lpe - le);
		w.writeString(fileToWrite, result);
	}

}
