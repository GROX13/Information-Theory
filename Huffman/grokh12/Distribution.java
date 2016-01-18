import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Distribution {

	static final char[] GEORGIAN_LETTERS = { ' ', 'ა', 'ბ', 'გ', 'დ', 'ე', 'ვ', 'ზ', 'თ', 'ი', 'კ', 'ლ', 'მ', 'ნ', 'ო',
			'პ', 'ჟ', 'რ', 'ს', 'ტ', 'უ', 'ფ', 'ქ', 'ღ', 'ყ', 'შ', 'ჩ', 'ც', 'ძ', 'წ', 'ჭ', 'ხ', 'ჯ', 'ჰ' };

	private String text;
	private HashMap<String, Float> letterValues = new HashMap<>();
	private HashMap<String, Float> letterPairValues = new HashMap<>();
	private DecimalFormat decimalFormat = new DecimalFormat("0.0000000");

	public Distribution(String text) {
		this.text = text;
		instatinateValues();
		countDistribtion();
	}

	public List<Float> getLetterDistribution() {
		ArrayList<Float> result = new ArrayList<>();
		SortedSet<String> keys = new TreeSet<String>(letterValues.keySet());
		for (String key : keys) {
			result.add(letterValues.get(key));
		}
		return result;
	}

	public List<Float> getLetterPairDistribution() {
		ArrayList<Float> result = new ArrayList<>();
		SortedSet<String> keys = new TreeSet<String>(letterPairValues.keySet());
		for (String key : keys) {
			result.add(letterPairValues.get(key));
		}
		return result;
	}

	public List<String> getLetterDistributionAsString() {
		List<Float> lf = getLetterDistribution();
		ArrayList<String> result = new ArrayList<>();
		for (Float f : lf) {
			result.add(decimalFormat.format(f));
		}
		return result;
	}

	public List<String> getLetterPairDistributionAsString() {
		List<Float> lf = getLetterPairDistribution();
		ArrayList<String> result = new ArrayList<>();
		for (Float f : lf) {
			result.add(decimalFormat.format(f));
		}
		return result;
	}

	private void countDistribtion() {
		int length = text.length();
		int pairCount = 0;
		String key;
		for (int i = 0; i < length; i++) {
			key = String.valueOf(text.charAt(i));
			letterValues.put(key, letterValues.get(key) + 1);
			if (i < (length - 1)) {
				pairCount++;
				key = String.valueOf(text.charAt(i)).concat(String.valueOf(text.charAt(i + 1)));
				letterPairValues.put(key, letterPairValues.get(key) + 1);
			}
		}
		normaliseLetters(length);
		normaliseLetterPairs(pairCount);
	}

	private void normaliseLetters(int length) {
		Set<String> keys = letterValues.keySet();
		for (String key : keys) {
			letterValues.put(key, letterValues.get(key) / length);
		}
	}

	private void normaliseLetterPairs(int length) {
		Set<String> keys = letterPairValues.keySet();
		for (String key : keys) {
			letterPairValues.put(key, letterPairValues.get(key) / length);
		}
	}

	private void instatinateValues() {
		for (int i = 0; i < GEORGIAN_LETTERS.length; i++) {
			letterValues.put(String.valueOf(GEORGIAN_LETTERS[i]), 0f);
		}
		for (int i = 0; i < GEORGIAN_LETTERS.length; i++) {
			for (int j = 0; j < GEORGIAN_LETTERS.length; j++) {
				String key = String.valueOf(GEORGIAN_LETTERS[i]).concat(String.valueOf(GEORGIAN_LETTERS[j]));
				letterPairValues.put(key, 0f);
			}
		}
	}

}
