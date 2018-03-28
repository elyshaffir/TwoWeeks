package old1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread{

    public void run() {
        String serverName = "127.0.0.1";
        int port = SocketGiver.PORT;
        try {
            Socket client = new Socket(serverName, port);

            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            int newPort = Integer.valueOf(in.readUTF());
            client.close();

            Socket newClient = new Socket(serverName, newPort);
            OutputStream outToServer = newClient.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            in = new DataInputStream(inFromServer);

            /*
            out.writeUTF("HAAAAAWDIHO!");
            in.readUTF();
            */

            // Communication code!

            newClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}