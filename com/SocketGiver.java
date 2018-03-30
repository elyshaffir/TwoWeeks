package com;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SocketGiver extends Thread{

    static final int PORT = 5000;

    private List<Server> clients = new ArrayList<>();
    private List<Integer> usedPorts = new ArrayList<>();

    public void run(){
        ServerSocket serverSocket = null;
        usedPorts.add(PORT);
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(10000);
            while (true) {

                Socket server = serverSocket.accept();

                Random r = new Random();
                int newPort = r.nextInt(64411) + 1024;
                while (usedPorts.contains(newPort))
                    newPort = r.nextInt(64411) + 1024;

                usedPorts.add(newPort);

                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF(String.valueOf(newPort));
                Server serverToGive = new Server(newPort);
                serverToGive.run();
                clients.add(serverToGive);

                out.close();
                server.close();
            }
        } catch (SocketTimeoutException e){

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert serverSocket != null;
                serverSocket.close();
            } catch (IOException | AssertionError e) {
                e.printStackTrace();
            }
        }
    }

    List<Server> getClients() {
        return clients;
    }

    void cleanUp(){
        for (Server client:clients){
            try {
                client.getServer().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
