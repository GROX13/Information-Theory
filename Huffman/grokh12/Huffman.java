import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class Huffman {

	private static String fileToRead;
	private static String fileToWrite;
	private static Reader r = new Reader();
	private static Writer w = new Writer();
	private String[] results;
	private TreeSet<Node> set = new TreeSet<Node>();

	private class Node implements Comparable<Node> {

		private double prob;
		private List<Integer> index;

		public Node(double prob, int index) {
			this.prob = prob;
			this.index = new ArrayList<>();
			this.index.add(index);
		}

		public double getProb() {
			return prob;
		}

		public void setProb(double prob) {
			this.prob = prob;
		}

		public List<Integer> getIndex() {
			return index;
		}

		public void addIndex(List<Integer> index) {
			this.index.addAll(index);
		}

		// compare, based on probability
		public int compareTo(Node that) {
			if (this.prob > that.prob)
				return 1;
			// if (this.prob < that.prob)
			return -1;
			// return 0;
		}

		@Override
		public String toString() {
			return String.valueOf(prob) + index.toString();
		}

	}

	public Huffman(String file) {
		String[] values = file.split("[\\s,;\\n\\t]+");
		int size = Integer.valueOf(values[0]);
		results = new String[size];

		for (int i = 0; i < size; i++) {
			Double prob = Double.valueOf(values[i + 1]);
			int index = i;
			results[index] = "";
			set.add(new Node(prob, index));
		}

		// System.err.println(set.size());

		while (set.size() > 1) {
			Node f0 = set.pollFirst();
			Node f1 = set.pollFirst();

			// log(f0, f1, results);

			List<Integer> tmp = f0.getIndex();
			for (int i = 0; i < tmp.size(); i++) {
				results[tmp.get(i)] = "0" + results[tmp.get(i)];
			}
			tmp = f1.getIndex();
			for (int i = 0; i < tmp.size(); i++) {
				results[tmp.get(i)] = "1" + results[tmp.get(i)];
			}

			f1.setProb(f0.getProb() + f1.getProb());
			f1.addIndex(f0.getIndex());
			set.add(f1);
			// System.out.println(new ArrayList<>(set));
		}
	}

	public String[] getResult() {
		return results;
	}

	public static void main(String[] args) {
		fileToRead = args[0];
		fileToWrite = args[1];
		Huffman h = new Huffman(r.getString(fileToRead));
		w.writeString(fileToWrite, generate(h.getResult()));
	}

	@SuppressWarnings("unused")
	private static void log(Node f0, Node f1, String[] result) {
		System.out.println("Index: ");
		System.out.println("0: " + f0.getProb() + ", " + f0.getIndex());
		System.out.println("1: " + f1.getProb() + ", " + f1.getIndex());
		System.out.println(Arrays.asList(result));

	}

	private static String generate(String[] r) {
		String result = "";
		for (int i = 0; i < r.length; i++) {
			result += (r.length - 1) != i ? (r[i] + "\n") : r[i];
		}
		return result;
	}

}
