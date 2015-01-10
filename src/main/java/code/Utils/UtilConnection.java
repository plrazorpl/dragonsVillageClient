package code.Utils;

import code.daos.basic.DaoProvider;
import dragonsVillage.Datagrams.requestDatagram.RequestMoveDragonDatagram;
import dragonsVillage.Datagrams.responseDatagram.ResponseMoveDragonDatagram;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class UtilConnection {
    private static BlockingQueue queue = new ArrayBlockingQueue(1);

    static {
        queue.add(new Object());
    }

    public static void sendObject(Object object) throws IOException, InterruptedException {
        Object take = queue.take();
        ObjectOutputStream outputStreamTCP = DaoProvider.getConnectionDAO().getOutputStreamTCP();
        outputStreamTCP.writeObject(object);
        queue.add(take);
    }
}
