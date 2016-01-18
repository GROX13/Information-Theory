import java.util.HashMap;

public class Decompress {

	private static String fileToRead;
	private static String fileToWrite;
	private static String fileToEncode;

	private static Reader r = new Reader();
	private static Writer w = new Writer();
	private static Normalizer n = new Normalizer();

	private String result;
	private HashMap<String, String> map;

	public Decompress(String fileContent, String fileToDecode) {
		result = "";
		String[] s = fileContent.split("\n");
		map = new HashMap<>();
		for (int i = 0; i < Distribution.GEORGIAN_LETTERS.length; i++) {
			map.put(String.valueOf(s[i]), String.valueOf(Distribution.GEORGIAN_LETTERS[i]));
		}
		String code = "";
		for (int i = 0; i < fileToDecode.length(); i++) {
			code += fileToDecode.charAt(i);
			if (map.containsKey(code)) {
				result += map.get(code);
				code = "";
			}
		}
	}

	public static void main(String[] args) {
		fileToRead = args[0];
		fileToWrite = args[2];
		fileToEncode = args[1];

		Decompress d = new Decompress(r.getString(fileToRead), n.denormalize(r.getBitsAsString(fileToEncode)));
		w.writeString(fileToWrite, d.getResult());

	}

	public String getResult() {
		return result;
	}

}
