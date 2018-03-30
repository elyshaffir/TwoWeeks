package com;

import java.util.ArrayList;
import java.util.List;

public class ClientConnector extends Thread{

    private List<Server> clients;

    ClientConnector(List<Server> clients) {
        this.clients = new ArrayList<>(clients);
    }

    public void run(){
        for (Server client:clients){
            for (Server client1:clients){
                if (client != client1){
                    client.addPendingDataToSend(client1.getPendingData());
                }
            }
            client.sendPendingData();
        }
        for (Server client:clients){
            client.setPendingData(new ArrayList<>());
            client.setPendingDataToSend(new ArrayList<>());
        }
    }
}
