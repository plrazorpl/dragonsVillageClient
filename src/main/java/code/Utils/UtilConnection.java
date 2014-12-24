package code.Utils;

import code.daos.basic.DaoProvider;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class UtilConnection {
    public static void sendObject(Object object) throws IOException {
        ObjectOutputStream outputStreamTCP = DaoProvider.getConnectionDAO().getOutputStreamTCP();
        outputStreamTCP.writeObject(object);
    }
}
