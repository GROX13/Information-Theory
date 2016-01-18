
public class Test {

	private static Reader r = new Reader();
	private static Writer w = new Writer();

	private static String count(int length) {
		String result = "";
		String len = Integer.toBinaryString(length);
		for (int i = 1; i < len.length(); i++)
			result += "0";
		result += len;
		return result;
	}

	public static void main(String[] args) {
		String f1 = "Tests/A/001.dat"; // = args[0];
		String f2 = "Tests/A/001.ans"; // = args[1];

		String r1 = "Tests/Read.dat"; // = args[2];
		String r2 = "Tests/Read.ans"; // = args[3];

		System.out.println(count(1000));

		w.writeString(r1, r.getBitsAsString(f1));
		w.writeString(r2, r.getBitsAsString(f2));
	}

}
