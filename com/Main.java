package old1;

public class Main {

    public static void main(String [] args) {

        SocketGiver socketGiver = new SocketGiver();
        Client client = new Client();
        Client client1 = new Client();

        socketGiver.start();
        client.start();
        client1.start();

    }
}
