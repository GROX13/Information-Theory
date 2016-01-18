import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrefCode {

	public class Index {
		private int length;
		private int index;

		public Index(int length) {
			this.setLength(length);
		}

		public Index(int length, int index) {
			this.setIndex(index);
			this.setLength(length);
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		@Override
		public boolean equals(Object obj) {
			return length == ((Index) obj).getLength();
		}

	}

	private static String fileToRead;
	private static String fileToWrite;
	private static Reader r = new Reader();
	private static Writer w = new Writer();

	private List<Index> order = null;
	private Integer[] lengths = null;
	private String[] result = null;

	public PrefCode(String input) {
		String[] lengthString = input.split(" ");
		lengths = new Integer[lengthString.length];
		result = new String[lengthString.length];
		order = new ArrayList<>();

		for (int i = 0; i < lengthString.length; i++) {
			lengths[i] = Integer.valueOf(lengthString[i]);
			order.add(new Index(Integer.valueOf(lengthString[i]), i));
		}
		if (canBeGenerated()) {
			generateBinaryPrefixCodes();
		} else {
			result = null;
		}
	}

	public String[] getResult() {
		return result;
	}

	private void generateBinaryPrefixCodes() {
		long start = 0;
		Arrays.sort(lengths);
		for (int i = 0; i < lengths.length; i++) {
			if (i != 0)
				if (lengths[i] > lengths[i - 1])
					start = start << (lengths[i] - lengths[i - 1]);

			int index = order.get(order.indexOf(new Index(lengths[i]))).getIndex();
			order.remove(order.indexOf(new Index(lengths[i])));
			result[index] = getLastNOf(start, lengths[i]);
			start += 1;
		}
	}

	private String getLastNOf(long from, int n) {
		String result = "";
		for (int i = n - 1; i >= 0; i--) {
			result += String.valueOf(getAtIndex(from, i));
		}
		return result;
	}

	private int getAtIndex(long b, int indx) {
		return (int) ((b >> indx) & 1);
	}

	private boolean canBeGenerated() {
		Double result = 0.0D;
		for (int i = 0; i < lengths.length; i++) {
			result += 1 / Math.pow(2, lengths[i]);
		}
		return (result <= 1.0D);
	}

	public static void main(String[] args) {
		fileToRead = args[0];
		fileToWrite = args[1];
		PrefCode pc = new PrefCode(r.getString(fileToRead));
		w.writeString(fileToWrite, build(pc.getResult()));
	}

	private static String build(String[] result2) {
		String res = "";
		if (result2 != null)
			for (int i = 0; i < result2.length; i++)
				res += (result2.length - 1) != i ? (result2[i] + "\n") : result2[i];
		return res;
	}

}
