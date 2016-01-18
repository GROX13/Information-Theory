


import java.io.FileInputStream;
import java.io.IOException;

public class Reader {

    private int getAtIndex(byte b, int indx) {
        return (b >> indx) & 1;
    }

    private String byteToString(byte b) {
        String byteToString = "";
        for (int i = 7; i >= 0; i--) {
            byteToString += getAtIndex(b, i);
        }
        return byteToString;
    }

    public byte[] getBytes(String file) {
        byte[] result = null;
        try {
            FileInputStream input = new FileInputStream(file);
            result = new byte[input.available()];
            input.read(result);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getString(String file) {
        return new String(getBytes(file));
    }

    public String getBitsAsString(String file) {
        // String result = "";
		StringBuilder r = new StringBuilder();

        byte[] bytes = getBytes(file);
        for (int i = 0; i < bytes.length; i++)
            // result += byteToString(bytes[i]);
            r.append(byteToString(bytes[i]));

        return r.toString();
    }

}
