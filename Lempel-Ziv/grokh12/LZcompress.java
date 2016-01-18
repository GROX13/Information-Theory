import java.util.HashMap;
import java.util.Map;

/**
 * @author გიორგი
 */
public class LZcompress {

    private static Reader r = new Reader();
    private static Writer w = new Writer();
    private static Normalizer n = new Normalizer();
    private static String fileToRead, fileToWrite;

    private StringBuilder result;

    class CompressParser implements Parser {
        private String file;
        private String key;
        private int index;

        private Map<String, Integer> dictionary;

        // private List<String> dictionary;

        public CompressParser(String fileAsBits) {
            this.key = "";
            this.index = 0;
            this.file = fileAsBits;
            dictionary = new HashMap<>();
            dictionary.put("0", 0);
            dictionary.put("1", 1);
//            dictionary = new ArrayList<>();
//            dictionary.add("0");
//            dictionary.add("1");
        }

        @Override
        public boolean hasMore() {
            key = "";
            int endIndex = index;
            if (endIndex < file.length()) {
                for (int i = 0; !dictionary.containsKey(key); i++) {
                    int j = endIndex + i;
                    if (j >= file.length())
                        key += "0";
                    else
                        key += file.charAt(j);
                }
                this.index = endIndex + key.length();
                return true;
            }
            return false;
        }

        @Override
        public String getIndex() {
            int ix = (int) Math.ceil(Math.log(dictionary.size()) / Math.log(2.0));
            int indexOf = dictionary.get(key);
            this.update(key);
            return Integer.toBinaryString((1 << ix) | indexOf).substring(1);
        }

        private void update(String i) {
            dictionary.put(i + "1", dictionary.size());
            // dictionary.add(dictionary.get(i) + "1");
            dictionary.put(i + "0", dictionary.remove(i));
        }
    }

    public LZcompress(String fileAsBits) {
        result = new StringBuilder();
        Parser p = new CompressParser(fileAsBits);
        while (p.hasMore())
            result.append(p.getIndex());
        // w.writeString("Tests/Read.bin", result);
    }

    public String getResult() {
        return result.toString();
    }

    private static String prepareResult(int length, String compressed) {
        String result = "";
        String len = Integer.toBinaryString(length);
        for (int i = 1; i < len.length(); i++)
            result += "0";
        result += len;
        result += compressed;
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        fileToRead = args[0];
        fileToWrite = args[1];
        int length = r.getBytes(fileToRead).length;
        System.out.println(length);
        String s = r.getBitsAsString(fileToRead);
        System.out.println("Reading done!");
        LZcompress lzc = new LZcompress(s);
        System.out.println("Compressing done!");
        w.writeStringAsBits(fileToWrite, n.normalize(prepareResult(length, lzc.getResult())));
    }
}
