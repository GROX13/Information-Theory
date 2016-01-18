

public class CompleteWrite {
	private static String inputFileName;
	private static String outputFileName;
	private static Normalizer n;
	private static Reader r;
	private static Writer w;

	public static void main(String[] args) {
		inputFileName = args[0];
		outputFileName = args[1];
		r = new Reader();
		w = new Writer();
		n = new Normalizer();
		w.writeStringAsBits(outputFileName, 
				n.normalize(r.getString(inputFileName)));
	}

}
