package gameCom;

public class Tester {
    public static void main(String args[]){
        Client client = new Client(1);
        Client client1 = new Client(2);
        Client client2 = new Client(3);

        Server server = new Server();
        server.start();
        client.start();
        client1.start();
        client2.start();

        while (client.isAlive() || client1.isAlive() || client2.isAlive()){ // first one to send is first one to die!
            System.out.println(client.isAlive() + "1");
            System.out.println(client1.isAlive() + "2");
            System.out.println(client2.isAlive() + "3");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("DEAD");

    }

    }
