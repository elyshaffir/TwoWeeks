package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private Socket server;

    private List<String> pendingData = new ArrayList<>();
    private List<String> pendingDataToSend = new ArrayList<>();

    Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run() {
        try {

            // serverSocket.getLocalPort();

            server = serverSocket.accept();

            DataInputStream in = new DataInputStream(server.getInputStream());

            String dataReceived = in.readUTF();
            System.out.println(dataReceived);
            pendingData.add(dataReceived);

            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<String> retrievePendingData(){
        List<String> ret = new ArrayList<>(pendingData);
        pendingData = new ArrayList<>();
        return ret;
    }

    Socket getServer() {
        return server;
    }

    void addPendingDataToSend(List<String> toAdd){
        pendingDataToSend.addAll(toAdd);
    }

    void setPendingDataToSend(List<String> pendingDataToSend) {
        this.pendingDataToSend = pendingDataToSend;
    }

    void sendPendingData(){
        try {
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(pendingDataToSend.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getPendingData() {
        return pendingData;
    }

    public void setPendingData(List<String> pendingData) {
        this.pendingData = pendingData;
    }
}