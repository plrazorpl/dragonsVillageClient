package code.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class UtilData {
    public static Object deserializable(byte[] bytes, int length) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(getBytes(bytes,length)));
        return objectInputStream.readObject();
    }

    private static byte[] getBytes(byte[] bytes, int length) {
        byte[] result = new byte[length];
        for(int i=0;i<length;i++){
            result[i] = bytes[i];
        }
        return result;
    }
}
