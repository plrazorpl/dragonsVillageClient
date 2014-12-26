package code.threads;

import code.daos.basic.DaoProvider;
import code.Utils.UtilCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReciveTCPDataThread extends Thread {



    @Override
    public void run() {
        super.run();
        ObjectInputStream inputStreamTCP = DaoProvider.getConnectionDAO().getInputStreamTCP();
        Socket tcpSocket = DaoProvider.getConnectionDAO().getTcpSocket();
        while (!tcpSocket.isClosed()){
            try {
                Object object = inputStreamTCP.readObject();
                UtilCommand.executeCommand(object);
            } catch (IOException e) {
                //TODO: add close connection
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
