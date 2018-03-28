package old1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Random;

public class Server extends Thread {

    private ServerSocket serverSocket;

    Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run() {
        try {

            // serverSocket.getLocalPort();

            Socket server = serverSocket.accept();

            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            DataInputStream in = new DataInputStream(server.getInputStream());

            in.readUTF();

            server.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}