
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    private byte[] getBitsFromString(String output) {
        byte[] result = new byte[(output.length() / Byte.SIZE)];
        for (int i = 0; i < result.length; i++) {
            byte resultByte = 0;
            for (int j = 0; j < Byte.SIZE; j++) {
                switch (output.charAt(i * Byte.SIZE + j)) {
                    case '1':
                        byte mask = (byte) (128 >> j);
                        resultByte = (byte) (resultByte | mask);
                        break;

                    default:
                        break;
                }
            }
            result[i] = resultByte;
        }
        return result;
    }

    public void writeBytes(String file, byte[] output) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(output);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeString(String file, String output) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(output);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStringAsBits(String file, String output) {
        writeBytes(file, getBitsFromString(output));
    }

}
