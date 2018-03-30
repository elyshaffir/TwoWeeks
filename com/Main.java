package com;

public class Main {

    public static void main(String [] args) {

        SocketGiver socketGiver = new SocketGiver();
        Client client0 = new Client();
        Client client1 = new Client("Whale hello there");
        Client client2 = new Client("Hi there boi");
        Client client3 = new Client("How ya doin lady");
        Client client4 = new Client("Waza");

        socketGiver.start();
        client0.start();
        client1.start();
        client2.start();
        client3.start();
        client4.start();

        while (socketGiver.isAlive())
            System.out.print("");

        ClientConnector clientConnector = new ClientConnector(socketGiver.getClients());
        clientConnector.start();

        while (clientConnector.isAlive())
            System.out.print("");

        socketGiver.cleanUp();
    }
}