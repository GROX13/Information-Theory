import java.util.ArrayList;
import java.util.List;

/**
 * @author გიორგი
 */
public class LZdecompress {

	private static Reader r = new Reader();
	private static Writer w = new Writer();
	private static Normalizer n = new Normalizer();
	private static String fileToRead, fileToWrite;

	private int fileLength;
	private StringBuilder result;

	class DecompressParser implements Parser {

		private List<String> symbols;
		private String text;
		private int index;

		public DecompressParser(String text) {
			symbols = new ArrayList<>();
			symbols.add("0");
			symbols.add("1");
			this.text = text;
			this.index = 0;
		}

		@Override
		public boolean hasMore() {
			return index < text.length();
		}

		@Override
		public String getIndex() {
			int numToRead = (int) Math.ceil(Math.log(symbols.size()) / Math.log(2.0));
			String symbol = this.text.substring(index, index + numToRead);
			this.index += numToRead;
			int i = Integer.parseInt(symbol, 2);
			String value = symbols.get(i);
			symbols.set(i, value + "0");
			symbols.add(value + "1");
			return value;
		}

	}

	public LZdecompress(String demoralize) {
		result = new StringBuilder();
		int len = 0;
		fileLength = 0;
		for (int i = 0; i < demoralize.length(); i++)
			if (demoralize.charAt(i) == '1') {
				len = (2 * i) + 1;
				fileLength = Integer.parseInt(demoralize.substring(i, len), 2);
				break;
			}
		System.out.println(fileLength);
		Parser p = new DecompressParser(demoralize.substring(len));
		while (p.hasMore())
			result = result.append(p.getIndex());
		// this.result = result.substring(0, fileLength * 8);
	}

	public String getResult() {
		return result.toString().substring(0, fileLength * 8);
	}

	@SuppressWarnings("unused")
	private void log() {
		System.out.println(result.substring(fileLength * 8 - 16));
		System.out.println((fileLength * 8) + ":" + result.length());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		fileToRead = args[0];
		fileToWrite = args[1];
		LZdecompress lzd = new LZdecompress(n.denormalize(r.getBitsAsString(fileToRead)));
		w.writeStringAsBits(fileToWrite, lzd.getResult());
		System.out.printf("Done!");
	}

}
