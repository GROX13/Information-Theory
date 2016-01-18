

public class SimpleWrite {
	private static String inputFileName;
	private static String outputFileName;
	private static Reader r;
	private static Writer w;

	public static void main(String[] args) {
		inputFileName = args[0];
		outputFileName = args[1];
		r = new Reader();
		w = new Writer();
		w.writeStringAsBits(outputFileName, r.getString(inputFileName));
	}

}
