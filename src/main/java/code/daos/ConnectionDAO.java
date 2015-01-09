package code.daos;

import code.threads.ReciveTCPDataThread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

import code.threads.ReciveUDPDataThread;

public class ConnectionDAO {
    private static final String IP_ADDRESS = "127.0.0.1";    //"25.166.5.129";
    private static final int SERVER_PORT = 12000;
    private static final int SELF_TCP_PORT = 13000;
    private static final int SELF_UDP_PORT = 14000;
    private static final int BUFFER_SIZE = 1024;

    private String serverIP = IP_ADDRESS;
    private int serverPort = SERVER_PORT;
    private int tcpPort = SELF_TCP_PORT;
    private int udpPort = SELF_UDP_PORT;

    private Socket serverSocket;
    private Socket tcpSocket;

    private ObjectOutputStream outputStreamTCP;
    private ObjectInputStream inputStreamTCP;

    private byte[] buffer = new byte[BUFFER_SIZE];
    private DatagramPacket pocket;
    private DatagramSocket udpServer;

    private ReciveTCPDataThread reciveTCPDataThread;
    private ReciveUDPDataThread reciveUDPDataThread;

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public Socket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getTcpSocket() {
        return tcpSocket;
    }

    public void setTcpSocket(Socket tcpSocket) {
        this.tcpSocket = tcpSocket;
    }

    public ObjectOutputStream getOutputStreamTCP() {
        return outputStreamTCP;
    }

    public void setOutputStreamTCP(ObjectOutputStream outputStreamTCP) {
        this.outputStreamTCP = outputStreamTCP;
    }

    public ObjectInputStream getInputStreamTCP() {
        return inputStreamTCP;
    }

    public void setInputStreamTCP(ObjectInputStream inputStreamTCP) {
        this.inputStreamTCP = inputStreamTCP;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public DatagramPacket getPocket() {
        return pocket;
    }

    public void setPocket(DatagramPacket pocket) {
        this.pocket = pocket;
    }

    public DatagramSocket getUdpServer() {
        return udpServer;
    }

    public void setUdpServer(DatagramSocket udpServer) {
        this.udpServer = udpServer;
    }

    public ReciveTCPDataThread getReciveTCPDataThread() {
        return reciveTCPDataThread;
    }

    public void setReciveTCPDataThread(ReciveTCPDataThread reciveTCPDataThread) {
        this.reciveTCPDataThread = reciveTCPDataThread;
    }

    public ReciveUDPDataThread getReciveUDPDataThread() {
        return reciveUDPDataThread;
    }

    public void setReciveUDPDataThread(ReciveUDPDataThread reciveUDPDataThread) {
        this.reciveUDPDataThread = reciveUDPDataThread;
    }
}
