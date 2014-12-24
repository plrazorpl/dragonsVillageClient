package code.threads;

import code.Utils.UtilCommand;
import code.Utils.UtilData;
import code.daos.basic.DaoProvider;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReciveUDPDataThread extends Thread {

    @Override
    public void run() {
        super.run();
        DatagramSocket udpServer = DaoProvider.getConnectionDAO().getUdpServer();
        byte[] buffer = DaoProvider.getConnectionDAO().getBuffer();
        DatagramPacket pocket = new DatagramPacket(buffer,buffer.length);
        while (!udpServer.isClosed()) {
            try {
                udpServer.receive(pocket);
                Object deserializable = UtilData.deserializable(pocket.getData(), pocket.getLength());
                UtilCommand.executeCommand(deserializable);
            } catch (IOException e) {
                //TODO: close all connection when connection reset
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }
}
