package code.listeners;

import code.Utils.UtilData;
import code.Utils.UtilView;
import code.daos.ConnectionDAO;
import code.daos.WindowDAO;
import code.daos.basic.DaoProvider;
import code.threads.ReciveTCPDataThread;
import code.threads.ReciveUDPDataThread;
import code.view.component.LoginPanel;
import dragonsVillage.dtos.LoginUserDTO;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginButtonListener implements MouseListener {

    private static final String SEPARATOR = ";";
    public static final String ACK = "ACK";
    private WindowDAO windowDAO = DaoProvider.getWindowDAO();
    private ConnectionDAO connectionDAO = DaoProvider.getConnectionDAO();

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(validateDataSend()){
            try {
                //TODO: close unused connection
                Socket socketToServer = new Socket(connectionDAO.getServerIP(),connectionDAO.getServerPort());
                ServerSocket tcpServer = new ServerSocket(connectionDAO.getTcpPort());
                sendLoginToServer(socketToServer);
                if(reciveLoginDataFromServer(tcpServer)){
                    UtilView.setErrorMessage("login correct");
                    DaoProvider.getConnectionDAO().setServerSocket(socketToServer);

                    byte[] buf = DaoProvider.getConnectionDAO().getBuffer();
                    DatagramPacket pocket = new DatagramPacket(buf, buf.length);
                    DatagramSocket udpServer = new DatagramSocket(DaoProvider.getConnectionDAO().getUdpPort());

                    sendACK(socketToServer);

                    udpServer.receive(pocket);
                    if(ACK.equals(UtilData.deserializable(pocket.getData(), pocket.getLength()))){
                        DaoProvider.getConnectionDAO().setPocket(pocket);
                        DaoProvider.getConnectionDAO().setUdpServer(udpServer);
                        sendUDPack(socketToServer);
                        startReciveThreads();
                        startGameWindow();
                    } else {
                        //TODO: Exception
                    }
                } else {
                    socketToServer.close();
                    tcpServer.close();
                    UtilView.setErrorMessage("login incorrect");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void startGameWindow() {
        DaoProvider.getWindowDAO().getLoginWindow().setVisible(false);
        DaoProvider.getWindowDAO().getLoginWindow().dispose();
        DaoProvider.getWindowDAO().getGameWindow().setVisible(true);
        UtilView.startGameEngines();
    }

    private void startReciveThreads() {
        ReciveTCPDataThread reciveTCPDataThread = new ReciveTCPDataThread();
        ReciveUDPDataThread reciveUDPDataThread = new ReciveUDPDataThread();

        reciveTCPDataThread.start();
        reciveUDPDataThread.start();

        DaoProvider.getConnectionDAO().setReciveTCPDataThread(reciveTCPDataThread);
        DaoProvider.getConnectionDAO().setReciveUDPDataThread(reciveUDPDataThread);
    }

    private void sendUDPack(Socket socketToServer) throws IOException {
        ObjectOutputStream outputStreamTCP = DaoProvider.getConnectionDAO().getOutputStreamTCP();
        outputStreamTCP.writeObject("UDP_ACK");
    }

    private void sendACK(Socket socketToServer) throws IOException {
        ObjectOutputStream outputStreamTCP = DaoProvider.getConnectionDAO().getOutputStreamTCP();
        outputStreamTCP.writeObject(ACK);
    }

    private boolean reciveLoginDataFromServer(ServerSocket tcpServer) throws IOException, ClassNotFoundException {
        Socket tcpSocket = tcpServer.accept();
        ObjectInputStream data = new ObjectInputStream(tcpSocket.getInputStream());
        Object getedElement = data.readObject();
        if(getedElement != null) {
            LoginUserDTO loginUserDTO = (LoginUserDTO) getedElement;
            DaoProvider.getLoggedUserDAO().setLoginUserDTO(loginUserDTO);
            DaoProvider.getConnectionDAO().setTcpSocket(tcpSocket);
            DaoProvider.getConnectionDAO().setInputStreamTCP(data);
            return true;
        }
        tcpSocket.close();
        return false;
    }

    private void sendLoginToServer(Socket socketToServer) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketToServer.getOutputStream());
        objectOutputStream.writeObject(getLoginDatagram());
        DaoProvider.getConnectionDAO().setOutputStreamTCP(objectOutputStream);
    }

    private String getLoginDatagram() {
        StringBuilder sb = new StringBuilder();
        sb.append(windowDAO.getLoginPanel().getLoginTextField().getText());
        sb.append(SEPARATOR);
        sb.append(windowDAO.getLoginPanel().getPasswordTextField().getText());
        sb.append(SEPARATOR);
        sb.append(connectionDAO.getTcpPort());
        sb.append(SEPARATOR);
        sb.append(connectionDAO.getUdpPort());
        return sb.toString();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private boolean validateDataSend() {
        StringBuilder sb = new StringBuilder();
        LoginPanel loginPanel = windowDAO.getLoginPanel();
        boolean correct = true;
        if(loginPanel.getLoginTextField().getText().isEmpty()){
            correct = false;
            loginPanel.getLoginLabel().setForeground(Color.RED);
            sb.append("Login can not be empty! ");
        } else {
            loginPanel.getLoginLabel().setForeground(Color.BLACK);
        }
        if(loginPanel.getPasswordTextField().getText().isEmpty()){
            correct = false;
            loginPanel.getPasswordLabel().setForeground(Color.RED);
            sb.append("Password can not be empty");
        } else {
            loginPanel.getPasswordLabel().setForeground(Color.BLACK);
        }
        UtilView.setErrorMessage(sb.toString());
        return correct;
    }
}
