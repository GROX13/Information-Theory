import java.util.HashMap;

public class Compress {

	private static String fileToRead;
	private static String fileToWrite;
	private static String fileToEncode;

	private static Reader r = new Reader();
	private static Writer w = new Writer();

	private HashMap<String, String> map;
	private String result;

	public Compress(String fileContent, String fileToEncode) {
		result = "";
		String[] s = fileContent.split("\n");
		map = new HashMap<>();
		for (int i = 0; i < Distribution.GEORGIAN_LETTERS.length; i++) {
			map.put(String.valueOf(Distribution.GEORGIAN_LETTERS[i]), String.valueOf(s[i]));
		}
		for (int i = 0; i < fileToEncode.length(); i++) {
			result += map.get(String.valueOf(fileToEncode.charAt(i)));
			// System.out.println("|-" +
			// String.valueOf(fileToEncode.charAt(i)));
			// System.out.println(map.get(String.valueOf(fileToEncode.charAt(i))));
		}
	}

	public String getResult() {
		return result;
	}

	public static void main(String[] args) {
		fileToRead = args[0];
		fileToWrite = args[2];
		fileToEncode = args[1];
		Compress c = new Compress(r.getString(fileToRead), r.getString(fileToEncode));
		Normalizer n = new Normalizer();

		w.writeStringAsBits(fileToWrite, n.normalize(c.getResult()));
	}

}
