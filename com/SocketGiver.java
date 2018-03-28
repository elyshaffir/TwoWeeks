package old1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SocketGiver extends Thread{

    static final int PORT = 5000;

    public void run(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(10000);
            while (true){

                // serverSocket.getLocalPort()

                Socket server = serverSocket.accept();

                Random r = new Random();
                int newPort = r.nextInt(64411) + 1024;

                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF(String.valueOf(newPort));
                Server serverToGive = new Server(newPort);
                serverToGive.run();
                out.close();
                server.close();
            }
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
}
