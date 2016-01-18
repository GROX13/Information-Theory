

public class SimpleRead {
	private static String inputFileName;
	private static String outputFileName;
	private static Reader r;
	private static Writer w;

	public static void main(String[] args) {
		inputFileName = args[0];
		outputFileName = args[1];
		r = new Reader();
		w = new Writer();
		w.writeString(outputFileName, r.getBitsAsString(inputFileName));
	}

}
